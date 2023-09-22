package fr.eni.ecole.encheres.ihm;

import java.io.IOException;

import fr.eni.ecole.encheres.bll.ArticleVenduManager;
import fr.eni.ecole.encheres.bll.EnchereManager;
import fr.eni.ecole.encheres.bo.ArticleVendu;
import fr.eni.ecole.encheres.bo.Enchere;
import fr.eni.ecole.encheres.bo.Utilisateur;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/encherir")

public class EncherirServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			// récupérer le param dans url
			System.out.println("111111111111111111111");
			int noArticle = Integer.parseInt(request.getParameter("noArticle"));
			// récupérer l'objet game
			ArticleVendu article = ArticleVenduManager.getInstance().recupUnArticleVendu(noArticle);
			System.out.println(article);
			// transmettre l'objet vers la jsp
			request.setAttribute("articleVendu", article);
			System.out.println("33333333333333333333333333");
			Enchere enchereMax = EnchereManager.getInstance().recupEnchereLaPlusHaute();
			System.out.println(enchereMax);
			if (enchereMax == null) {
				request.setAttribute("enchere", "pas d'enchère");
			} else {
				request.setAttribute("enchere", enchereMax);
			}
			// forward
			System.out.println("2222222222222222222222222");
			request.getRequestDispatcher("/WEB-INF/pages/Encherir.jsp").forward(request, response);

		} catch (Exception e) {

			response.sendError(404);

		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// récupérer le param dans url
			int noArticle = Integer.parseInt(request.getParameter("noArticle"));
			// supprimer un jeu
			ArticleVendu article = ArticleVenduManager.getInstance().recupUnArticleVendu(noArticle);
			HttpSession session = request.getSession();
			Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
			int montantEnchere = Integer.parseInt(request.getParameter("montantEnchere"));
			// comparer avec l'enchère précédente OU bloquer la possibilité d'enchérir moins
			// dans le form directement
			Enchere newEnchere = new Enchere();
			newEnchere.setArticleEncheri(article);
			newEnchere.setMontant_enchere(montantEnchere);
			newEnchere.setAcquereur(utilisateur);
			// définir ce nouveau montant comme le nouveau prix de vente de l'article. Ou ça
			// va le faire tout seul car c'est devenu l'enchère max ?
			EnchereManager.getInstance().ajouterUneEnchere(newEnchere);
			// redirect
			response.sendRedirect(request.getContextPath() + "/");
		} catch (Exception e) {
			response.sendError(404);
		}
	}

}
