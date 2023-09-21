package fr.eni.ecole.encheres.ihm;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import fr.eni.ecole.encheres.bll.ArticleVenduManager;
import fr.eni.ecole.encheres.bo.ArticleVendu;

@WebServlet("/encherir")

public class EncherirServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			// récupérer le param dans url
			int id = Integer.parseInt(request.getParameter("noArticle"));
			// récupérer l'objet game
			ArticleVendu article = ArticleVenduManager.getInstance().recupUnArticleVendu(id);
			// transmettre l'objet vers la jsp
			request.setAttribute("article", article);
			// forward
			request.getRequestDispatcher("/WEB-INF/pages/encherie.jsp").forward(request, response);
		} catch (Exception e) {

			response.sendError(404);

		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//
		}

}
