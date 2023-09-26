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

			int noArticle = Integer.parseInt(request.getParameter("id"));
			// récupérer l'objet game
			ArticleVendu article = ArticleVenduManager.getInstance().recupUnArticleVendu(noArticle);
			System.out.println(article);
			// transmettre l'objet vers la jsp
			request.setAttribute("articleVendu", article);

			Enchere enchereMax = EnchereManager.getInstance().recupEnchereLaPlusHaute(article);
			System.out.println(enchereMax);
			if (enchereMax == null) {

				request.setAttribute("enchere", null);
			} else {

				request.setAttribute("enchere", enchereMax);
			}
			// forward

			request.getRequestDispatcher("/WEB-INF/pages/encherir.jsp").forward(request, response);

		} catch (Exception e) {

			response.sendError(404);

		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");

			if (utilisateur == null) {
				response.sendRedirect(request.getContextPath() + "/connexion");
			} else {
				// récupérer le param dans url
				int noArticle = Integer.parseInt(request.getParameter("id"));

				ArticleVendu article = ArticleVenduManager.getInstance().recupUnArticleVendu(noArticle);

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
				System.out.println("mon enchere :" + newEnchere);
				Enchere enchereMax = EnchereManager.getInstance().recupEnchereLaPlusHaute(article);
				System.out.println("enchere max : " + enchereMax);
				// redirect
				response.sendRedirect(request.getContextPath() + "/accueil");
			}
		} catch (Exception e) {
			response.sendError(404);
		}
	}

}
