package fr.eni.ecole.encheres.ihm;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fr.eni.ecole.encheres.bll.ArticleVenduManager;
import fr.eni.ecole.encheres.bll.CategorieManager;
import fr.eni.ecole.encheres.bll.EnchereManager;
import fr.eni.ecole.encheres.bo.ArticleVendu;
import fr.eni.ecole.encheres.bo.Utilisateur;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/accueil")
public class Accueil extends HttpServlet {
	private static final long serialVersionUID = 1L;

//modif
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
		List<ArticleVendu> articlesVendus = new ArrayList<ArticleVendu>();
		ArticleVenduManager.getInstance().updateAllArticles();
		String checkListeEncheres = request.getParameter("listeEncheres");
		if (checkListeEncheres == null) {
			articlesVendus = ArticleVenduManager.getInstance().recupTousLesArticlesVendus();
		} else {
			if (checkListeEncheres.equals("encheresOuvertes")) {
				articlesVendus = ArticleVenduManager.getInstance().recupArticlesVendusEncheresOuvertes();
			}
			if (checkListeEncheres.equals("encheresEnCours")) {

				articlesVendus = ArticleVenduManager.getInstance().recupArticlesEncheresParUtilisateur(utilisateur);
			}
			if (checkListeEncheres.equals("encheresRemportees")) {

				List<ArticleVendu> articlesEncheris = ArticleVenduManager.getInstance()
						.recupArticlesEncheresParUtilisateur(utilisateur);

				for (ArticleVendu articleVendu : articlesEncheris) {

					if (EnchereManager.getInstance().recupLeGagnantEnchere(articleVendu) != null && EnchereManager
							.getInstance().recupLeGagnantEnchere(articleVendu).getAcquereur().equals(utilisateur))
						articlesVendus.add(articleVendu);
				}
			}
			if (checkListeEncheres.equals("ventesEnCours")) {
				System.out.println("11111111111111111111111111111");
				articlesVendus = ArticleVenduManager.getInstance()
						.recupArticlesVendusParUtilisateurSelonEtatVente(utilisateur, "v");
				System.out.println("444444444444444444444");
			}
			if (checkListeEncheres.equals("ventesNonDebutees")) {
				System.out.println("2222222222222222222222");
				articlesVendus = ArticleVenduManager.getInstance()
						.recupArticlesVendusParUtilisateurSelonEtatVente(utilisateur, "av");
			}
			if (checkListeEncheres.equals("ventesTerminees")) {
				System.out.println("3333333333333333333333333");
				articlesVendus = ArticleVenduManager.getInstance()
						.recupArticlesVendusParUtilisateurSelonEtatVente(utilisateur, "vf");
			}
		}

		request.setAttribute("articlesVendus", articlesVendus);

//		request.setAttribute("categories", CategorieManager.getInstance().recupTouteCategories());
//		String q = request.getParameter("q");
//		// ne sélectionner que les articles dont les enchères ont été ouvertes
//		// vérifier si une catégorie a été selectionnée
//		if (request.getParameter("categorie") != null && Integer.parseInt(request.getParameter("categorie")) > 0) {
//			int noCategorie = Integer.parseInt(request.getParameter("categorie"));
//			// si en plus de la catégorie une recherche par nom existe
//			if (request.getParameter("q") != null) {
//				articlesVendus = ArticleVenduManager.getInstance().recupUnArticleVenduByCategorieEO(noCategorie, q);
//			}
//			// si que catégorie selectionné
//
//			articlesVendus = ArticleVenduManager.getInstance().recupArticlesCategorieEO(noCategorie);
//		}
//		// traitement si aucune catégorie selectionnée
//		else {
//			// si recherche par nom q
//			if (request.getParameter("q") != null) {
//				articlesVendus = ArticleVenduManager.getInstance().recupUnArticleVendyEncheresOuvertes(q);
//			} else {
//				articlesVendus = ArticleVenduManager.getInstance().recupArticlesVendusEncheresOuvertes();
//			}
//		}
////		if (articlesVendus == null) {
////			System.out.println("pas d'article à afficher");
////		}
//		request.setAttribute("articlesVendus", articlesVendus);
		request.setAttribute("annee", LocalDate.now().getYear());

		request.getRequestDispatcher("WEB-INF/pages/accueil.jsp").forward(request, response);
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
