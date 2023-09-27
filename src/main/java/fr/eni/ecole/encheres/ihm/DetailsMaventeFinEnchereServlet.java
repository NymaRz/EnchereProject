package fr.eni.ecole.encheres.ihm;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import fr.eni.ecole.encheres.bll.ArticleVenduManager;
import fr.eni.ecole.encheres.bll.CategorieManager;
import fr.eni.ecole.encheres.bll.EnchereManager;
import fr.eni.ecole.encheres.bo.ArticleVendu;
import fr.eni.ecole.encheres.bo.Enchere;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/detailsmaventefinenchere")
public class DetailsMaventeFinEnchereServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

//modif
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<ArticleVendu> articlesVendus = null;
		request.setAttribute("categories", CategorieManager.getInstance().recupTouteCategories());
		String q = request.getParameter("q");
		// ne sélectionner que les articles dont les enchères ont été ouvertes
		// vérifier si une catégorie a été sélectionnée
		if (request.getParameter("categorie") != null && Integer.parseInt(request.getParameter("categorie")) > 0) {
			int noCategorie = Integer.parseInt(request.getParameter("categorie"));
			// si en plus de la catégorie une recherche par nom existe
			if (request.getParameter("q") != null) {
				articlesVendus = ArticleVenduManager.getInstance().recupUnArticleVenduByCategorieEO(noCategorie, q);
			}
			// si seulement une catégorie est sélectionnée
			else {
				articlesVendus = ArticleVenduManager.getInstance().recupArticlesCategorieEO(noCategorie);
			}
		}
		// traitement si aucune catégorie sélectionnée
		else {
			// si recherche par nom q
			if (request.getParameter("q") != null) {
				articlesVendus = ArticleVenduManager.getInstance().recupUnArticleVenduEncheresOuvertes(q);
			} else {
				articlesVendus = ArticleVenduManager.getInstance().recupArticlesVendusSelonEtatVente("v");
			}
		}

		// Ajoutez le code pour rechercher l'article vendu avec l'état "vf"
		ArticleVendu articleVenduTermine = null;
		for (ArticleVendu article : articlesVendus) {
			if ("vf".equals(article.getEtatVente())) {
				articleVenduTermine = article;
				break; // Sortir de la boucle dès qu'on a trouvé un article vendu
			}
		}

		// ----------------- À l'intérieur de la méthode doGet après avoir obtenu
		// l'article vendu terminé
		if (articleVenduTermine != null) {
			// Calcul du pourcentage d'enchère
			int prixInitial = articleVenduTermine.getMiseAPrix();
			int prixVente = articleVenduTermine.getPrixVente();

			// Vérifiez que le prix initial n'est pas égal à zéro pour éviter une division
			// par zéro
			double pourcentageEnchere = (prixInitial != 0) ? ((double) (prixVente - prixInitial) / prixInitial) * 100
					: 0.0;

			// Ajoutez le pourcentage d'enchère à la requête
			request.setAttribute("pourcentageEnchere", pourcentageEnchere);
// --------------------------------------- 

			// Récupérez l'enchère gagnante de l'article vendu
			Enchere enchereGagnante = EnchereManager.getInstance().recupLeGagnantEnchere(articleVenduTermine);

			if (enchereGagnante != null) {
				// Accédez à l'acheteur de l'enchère gagnante
				String acheteurEnchere = enchereGagnante.getAcquereur().getEmail();

				// Ajoutez l'acheteur de l'enchère à la requête
				request.setAttribute("acheteurEnchere", acheteurEnchere);
			}
		}
// -----------------------------------------------
		if (articlesVendus == null) {
			System.out.println("pas d'article à afficher");
		}

		// Ajoutez l'article vendu à la requête s'il existe
		request.setAttribute("articleVenduTermine", articleVenduTermine);

		// Ajoutez le prix de vente initial à la requête s'il existe
		if (articleVenduTermine != null) {
			request.setAttribute("prixInitial", articleVenduTermine.getMiseAPrix());
		}

		request.setAttribute("articlesVendus", articlesVendus);
		request.setAttribute("annee", LocalDate.now().getYear());

		request.getRequestDispatcher("WEB-INF/pages/detailsmaventefinenchere.jsp").forward(request, response);
	}
}