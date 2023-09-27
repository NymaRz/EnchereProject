package fr.eni.ecole.encheres.ihm;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import fr.eni.ecole.encheres.bll.ArticleVenduManager;
import fr.eni.ecole.encheres.bll.CategorieManager;
import fr.eni.ecole.encheres.bo.ArticleVendu;
import fr.eni.ecole.encheres.bo.Utilisateur;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/acquisition")
public class AcquisitionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");

		// Vérifier si l'utilisateur connecté est null
		if (utilisateur != null) {
			// Récupérer la liste des catégories
			request.setAttribute("categories", CategorieManager.getInstance().recupTouteCategories());

			String q = request.getParameter("q");
			List<ArticleVendu> articlesVendus = null;

			// Ne sélectionner que les articles dont les enchères ont été ouvertes
			int noCategorie = -1; // Initialisez à une valeur négative par défaut
			if (request.getParameter("categorie") != null) {
				noCategorie = Integer.parseInt(request.getParameter("categorie"));
			}

			// Récupérez l'enchère remportée par l'utilisateur (si existante)
			ArticleVendu articleVenduRemporte = ArticleVenduManager.getInstance()
					.recupDernierArticleRemporte(utilisateur.getNoUtilisateur());

			if (noCategorie > 0 && q != null) {
				// Si une catégorie est sélectionnée et une recherche par nom existe
				articlesVendus = ArticleVenduManager.getInstance().recupUnArticleVenduByCategorieEO(noCategorie, q);
			} else if (noCategorie > 0) {
				// Si seulement une catégorie est sélectionnée
				articlesVendus = ArticleVenduManager.getInstance().recupArticlesCategorieEO(noCategorie);
			} else if (q != null) {
				// Si recherche par nom q
				articlesVendus = ArticleVenduManager.getInstance().recupUnArticleVenduEncheresOuvertes(q);
			} else {
				// Si aucune catégorie sélectionnée ni de recherche par nom
				articlesVendus = ArticleVenduManager.getInstance().recupArticlesVendusSelonEtatVente("v");
			}

			request.setAttribute("articleVenduRemporte", articleVenduRemporte);
			request.setAttribute("articlesVendus", articlesVendus);
			request.setAttribute("annee", LocalDate.now().getYear());
			request.getRequestDispatcher("WEB-INF/pages/acquisition.jsp").forward(request, response);
		} else {
			// L'utilisateur connecté est null, gérer cette situation (par exemple,
			// rediriger vers une page de connexion)
			response.sendRedirect(request.getContextPath() + "/connexion");
		}
	}
}
