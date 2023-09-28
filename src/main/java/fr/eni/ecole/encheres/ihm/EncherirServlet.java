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
			// transmettre l'objet vers la jsp
			request.setAttribute("articleVendu", article);

			Enchere enchereMax = EnchereManager.getInstance().recupEnchereLaPlusHaute(article);

			String message = (String) request.getSession().getAttribute("success");
			request.getSession().removeAttribute("success");
			request.setAttribute("success", message);
			String messageError = (String) request.getSession().getAttribute("error");
			request.getSession().removeAttribute("error");
			request.setAttribute("error", messageError);

			int minEnchere;
			if (enchereMax == null) {
				minEnchere = article.getMiseAPrix() + article.getEnchereMin();
				request.setAttribute("enchere", null);
				request.setAttribute("minEnchere", minEnchere);

			} else {
				minEnchere = enchereMax.getMontant_enchere() + article.getEnchereMin();
				request.setAttribute("enchere", enchereMax);
				request.setAttribute("minEnchere", minEnchere);

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
			int noArticle = Integer.parseInt(request.getParameter("id"));

			ArticleVendu article = ArticleVenduManager.getInstance().recupUnArticleVendu(noArticle);
			if (utilisateur == null) {
				request.getSession().setAttribute("error", "Vous devez vous connecter pour enchérir sur un article");
				response.sendRedirect(request.getContextPath() + "/connexion");
			} else if (utilisateur.getNoUtilisateur() == article.getUtilisateur().getNoUtilisateur()) {
	
				request.getSession().setAttribute("error", "Vous ne pouvez pas enchérir sur vos propres articles");

				doGet(request, response);
			}

			else {

				// récupérer le param dans url

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

				Enchere enchereMax = EnchereManager.getInstance().recupEnchereLaPlusHaute(article);

				request.setAttribute("enchere", enchereMax);

				// redirect


				request.getSession().setAttribute("success", "Votre enchère a été prise en compte !");

				doGet(request, response);

			}
		} catch (Exception e) {
			response.sendError(404);
		}
	}

}
