package fr.eni.ecole.encheres.ihm;

import java.io.IOException;

import fr.eni.ecole.encheres.bll.ArticleVenduManager;
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

		// vérif session ? pas forcément besoin, à voir
//		HttpSession session = request.getSession();
//
//		String email = (String) session.getAttribute("email");
//		Utilisateur utilisateur;
//		try {
//			utilisateur = UtilisateurManager.getInstance().findByEmail(email);
//			request.setAttribute("adresse", utilisateur.getAdresse());
//		} catch (BLLException e) {
//			e.printStackTrace();
//		}

		// quand un utilisateur clique sur un de ses articles ça renvoie l'id
		try {
			int noArticleVendu = Integer.parseInt(request.getParameter("noArticleVendu"));
			ArticleVendu articleVendu = ArticleVenduManager.getInstance().recupUnArticleVendu(noArticleVendu);
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
