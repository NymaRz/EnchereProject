package fr.eni.ecole.encheres.ihm;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import fr.eni.ecole.encheres.bll.ArticleVenduManager;
import fr.eni.ecole.encheres.bll.CategorieManager;
import fr.eni.ecole.encheres.bo.ArticleVendu;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/acquisition")
public class AcquisitionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

//modif
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<ArticleVendu> articlesVendus = null;
		request.setAttribute("categories", CategorieManager.getInstance().recupTouteCategories());
		String q = request.getParameter("q");
		// ne sélectionner que les articles dont les enchères ont été ouvertes
		// vérifier si une catégorie a été selectionnée
		if (request.getParameter("categorie") != null && Integer.parseInt(request.getParameter("categorie")) > 0) {
			int noCategorie = Integer.parseInt(request.getParameter("categorie"));
			// si en plus de la catégorie une recherche par nom existe
			if (request.getParameter("q") != null) {
				articlesVendus = ArticleVenduManager.getInstance().recupUnArticleVenduByCategorieEO(noCategorie, q);
			}
			// si que catégorie selectionné

			articlesVendus = ArticleVenduManager.getInstance().recupArticlesCategorieEO(noCategorie);
		}
		// traitement si aucune catégorie selectionnée
		else {
			// si recherche par nom q
			if (request.getParameter("q") != null) {
				articlesVendus = ArticleVenduManager.getInstance().recupUnArticleVendyEncheresOuvertes(q);
			} else {
				articlesVendus = ArticleVenduManager.getInstance().recupArticlesVendusEncheresOuvertes();
			}
		}
		if (articlesVendus == null) {
			System.out.println("pas d'article à afficher");
		}
		request.setAttribute("articlesVendus", articlesVendus);
		request.setAttribute("annee", LocalDate.now().getYear());

		request.getRequestDispatcher("WEB-INF/pages/acquisition.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Vérifier si le cookie "rememberMe" est présent
		Cookie[] cookies = request.getCookies();
		boolean loggedIn = false;

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("rememberMe") && cookie.getValue().equals("true")) {
					// Connecter automatiquement l'utilisateur
					loggedIn = true;
					break;
				}
			}
		}
		if (loggedIn) {
			// L'utilisateur est connecté automatiquement
			// Afficher la page d'accueil
		} else {
			response.sendRedirect(request.getContextPath() + "/connexion");
		}
	}

}
