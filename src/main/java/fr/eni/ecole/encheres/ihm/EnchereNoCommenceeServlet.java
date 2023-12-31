package fr.eni.ecole.encheres.ihm;

import java.io.IOException;

import fr.eni.ecole.encheres.bll.ArticleVenduManager;
import fr.eni.ecole.encheres.bll.exception.BLLException;
import fr.eni.ecole.encheres.bo.ArticleVendu;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/enchere-programmee")

public class EnchereNoCommenceeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			int noArticleVendu = Integer.parseInt(request.getParameter("noArticleVendu"));
			ArticleVendu articleVendu = ArticleVenduManager.getInstance().recupArticleVenduEnchereNC(noArticleVendu);
			if (articleVendu == null)
				response.sendRedirect(request.getContextPath() + "/listeencheresmesventes.jsp");
			request.setAttribute("articleVendu", articleVendu);
			request.getRequestDispatcher("/WEB-INF/pages/encherenoncommencee.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
