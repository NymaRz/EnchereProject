package fr.eni.ecole.encheres.ihm;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fr.eni.ecole.encheres.bll.ArticleVenduManager;
import fr.eni.ecole.encheres.bll.CategorieManager;
import fr.eni.ecole.encheres.bll.EnchereManager;
import fr.eni.ecole.encheres.bo.ArticleVendu;
import fr.eni.ecole.encheres.bo.Categorie;
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
		List<Categorie> categories = new ArrayList<Categorie>();
		;

		String q = request.getParameter("q");

		ArticleVenduManager.getInstance().updateAllArticles();
		List<ArticleVendu> articlesAvecGagnant = ArticleVenduManager.getInstance().recupAndUpdateAllGagnants();

		if (utilisateur == null) {

			if (request.getParameter("categorie") != null && Integer.parseInt(request.getParameter("categorie")) > 0) {
				int noCategorie = Integer.parseInt(request.getParameter("categorie"));
				// si en plus de la catégorie une recherche par nom existe
				if (request.getParameter("q") != null && !request.getParameter("q").isBlank()) {
					articlesVendus = ArticleVenduManager.getInstance().recupUnArticleVenduByCategorieEO(noCategorie, q);
				} else
					// si que catégorie selectionné
					articlesVendus = ArticleVenduManager.getInstance().recupArticlesCategorieEO(noCategorie);

			}
			// traitement si aucune catégorie selectionnée
			else {
				// si recherche par nom q
				if (request.getParameter("q") != null && !request.getParameter("q").isBlank()) {
					articlesVendus = ArticleVenduManager.getInstance().recupUnArticleVenduEncheresOuvertes(q);
				} else {
					articlesVendus = null;
					categories = CategorieManager.getInstance().recupTouteCategories();
					for (Categorie categorie : categories) {
						categorie.setArticlesOfCategorie(
								ArticleVenduManager.getInstance().recupArticlesCategorieEO(categorie.getNoCategorie()));
						System.out.println(categorie.getArticlesOfCategorie());
					}
					request.setAttribute("categories", categories);
				}
			}

		}

		else {
			String checkListeEncheres = request.getParameter("listeEncheres");
			if (checkListeEncheres == null) {
				articlesVendus = null;
				categories = CategorieManager.getInstance().recupTouteCategories();
				for (Categorie categorie : categories) {
					categorie.setArticlesOfCategorie(
							ArticleVenduManager.getInstance().recupArticlesCategorieEO(categorie.getNoCategorie()));
				}
				request.setAttribute("categories", categories);

			} else {
				request.setAttribute("categories", null);
				if (checkListeEncheres.equals("encheresOuvertes")) {
					request.setAttribute("nomListe", "Toutes les enchères en cours");
					if (request.getParameter("categorie") != null
							&& Integer.parseInt(request.getParameter("categorie")) > 0) {

						int noCategorie = Integer.parseInt(request.getParameter("categorie"));

						// si en plus de la catégorie une recherche par nom existe

						if (request.getParameter("q") != null && !request.getParameter("q").isBlank()) {
							articlesVendus = ArticleVenduManager.getInstance()
									.recupUnArticleVenduByCategorieEO(noCategorie, q);

						} else
							// si que catégorie selectionné
							articlesVendus = ArticleVenduManager.getInstance().recupArticlesCategorieEO(noCategorie);
					}
					// traitement si aucune catégorie selectionnée
					else {
						// si recherche par nom q
						if (request.getParameter("q") != null && !request.getParameter("q").isBlank())
							articlesVendus = ArticleVenduManager.getInstance().recupUnArticleVenduEncheresOuvertes(q);

						else
							articlesVendus = ArticleVenduManager.getInstance().recupArticlesVendusSelonEtatVente("v");

					}
				}
				if (checkListeEncheres.equals("encheresEnCours")) {

					request.setAttribute("nomListe", "Mes enchères en cours");

					if (request.getParameter("categorie") != null
							&& Integer.parseInt(request.getParameter("categorie")) > 0) {

						int noCategorie = Integer.parseInt(request.getParameter("categorie"));

						// si en plus de la catégorie une recherche par nom existe

						if (request.getParameter("q") != null && !request.getParameter("q").isBlank()) {
							List<ArticleVendu> articlesVendusSansTri = ArticleVenduManager.getInstance()
									.recupUnArticleEncheriParutilisateurEtCategorie(utilisateur, noCategorie, q);
							articlesVendus = ArticleVenduManager.getInstance().removeDuplicates(articlesVendusSansTri);
						} else {
							// si que catégorie selectionné
							List<ArticleVendu> articlesVendusSansTri = ArticleVenduManager.getInstance()
									.recupArticlesEncherisParUtilisateurEtCategorie(utilisateur, noCategorie);
							articlesVendus = ArticleVenduManager.getInstance().removeDuplicates(articlesVendusSansTri);
						}

					}
					// traitement si aucune catégorie selectionnée
					else {
						// si recherche par nom q
						if (request.getParameter("q") != null && !request.getParameter("q").isBlank()) {
							List<ArticleVendu> articlesVendusSansTri = ArticleVenduManager.getInstance()
									.recupUnArticleEncheriParUtilisateur(utilisateur, q);
							articlesVendus = ArticleVenduManager.getInstance().removeDuplicates(articlesVendusSansTri);
						} else {
							List<ArticleVendu> articlesVendusSansTri = ArticleVenduManager.getInstance()
									.recupArticlesEncheresParUtilisateur(utilisateur);
							articlesVendus = ArticleVenduManager.getInstance().removeDuplicates(articlesVendusSansTri);
						}
					}

				}

				if (checkListeEncheres.equals("encheresRemportees")) {
					request.setAttribute("nomListe", "Mes enchères remportées");
					if (request.getParameter("categorie") != null
							&& Integer.parseInt(request.getParameter("categorie")) > 0) {

						int noCategorie = Integer.parseInt(request.getParameter("categorie"));

						// si en plus de la catégorie une recherche par nom existe

						if (request.getParameter("q") != null && !request.getParameter("q").isBlank()) {

							articlesVendus = ArticleVenduManager.getInstance()
									.recupUneEnchereRemporteeParUtilisateurParCategorie(utilisateur, noCategorie, q);

						} else {
							articlesVendus = ArticleVenduManager.getInstance()
									.recupEncheresRemporteesParUtilisateurParCategorie(utilisateur, noCategorie);
						}

					}
					// si aucune catégorie selectionnée
					else {
						// si recherche par nom q
						if (request.getParameter("q") != null && !request.getParameter("q").isBlank()) {

							articlesVendus = ArticleVenduManager.getInstance()
									.recupUneEnchereRemporteeParUtilisateur(utilisateur, q);
						} else {
							articlesVendus = ArticleVenduManager.getInstance()
									.recupEncheresRemporteesParUtilisateur(utilisateur);
						}
					}

				}
				if (checkListeEncheres.equals("ventesEnCours")) {
					request.setAttribute("nomListe", "Mes ventes en cours");
					if (request.getParameter("categorie") != null
							&& Integer.parseInt(request.getParameter("categorie")) > 0) {

						int noCategorie = Integer.parseInt(request.getParameter("categorie"));

						// si en plus de la catégorie une recherche par nom existe

						if (request.getParameter("q") != null && !request.getParameter("q").isBlank()) {
							articlesVendus = ArticleVenduManager.getInstance()
									.recupUnArticlesVendusParUtilisateurSelonEtatVenteEtCategorie(utilisateur,
											noCategorie, q, "v");

						} else
							// si que catégorie selectionné
							articlesVendus = ArticleVenduManager.getInstance()
									.recupArticlesVendusParUtilisateurSelonEtatVenteEtCategorie(utilisateur,
											noCategorie, "v");
					}
					// traitement si aucune catégorie selectionnée
					else {
						// si recherche par nom q
						if (request.getParameter("q") != null && !request.getParameter("q").isBlank()) {
							articlesVendus = ArticleVenduManager.getInstance()
									.recupUnArticlesVendusParUtilisateurSelonEtatVente(utilisateur, q, "v");
						}

						else {
							articlesVendus = ArticleVenduManager.getInstance()
									.recupArticlesVendusParUtilisateurSelonEtatVente(utilisateur, "v");

						}
					}

				}
				if (checkListeEncheres.equals("ventesNonDebutees")) {
					request.setAttribute("nomListe", "Mes ventes programmées");
					if (request.getParameter("categorie") != null
							&& Integer.parseInt(request.getParameter("categorie")) > 0) {

						int noCategorie = Integer.parseInt(request.getParameter("categorie"));

						// si en plus de la catégorie une recherche par nom existe

						if (request.getParameter("q") != null && !request.getParameter("q").isBlank()) {
							articlesVendus = ArticleVenduManager.getInstance()
									.recupUnArticlesVendusParUtilisateurSelonEtatVenteEtCategorie(utilisateur,
											noCategorie, q, "av");

						} else
							// si que catégorie selectionné
							articlesVendus = ArticleVenduManager.getInstance()
									.recupArticlesVendusParUtilisateurSelonEtatVenteEtCategorie(utilisateur,
											noCategorie, "av");
					}
					// traitement si aucune catégorie selectionnée
					else {
						// si recherche par nom q
						if (request.getParameter("q") != null && !request.getParameter("q").isBlank()) {
							articlesVendus = ArticleVenduManager.getInstance()
									.recupUnArticlesVendusParUtilisateurSelonEtatVente(utilisateur, q, "av");
						}

						else {
							articlesVendus = ArticleVenduManager.getInstance()
									.recupArticlesVendusParUtilisateurSelonEtatVente(utilisateur, "av");

						}
					}

				}
				if (checkListeEncheres.equals("ventesTerminees")) {
					request.setAttribute("nomListe", "Mes ventes terminées");
					if (request.getParameter("categorie") != null
							&& Integer.parseInt(request.getParameter("categorie")) > 0) {

						int noCategorie = Integer.parseInt(request.getParameter("categorie"));

						// si en plus de la catégorie une recherche par nom existe

						if (request.getParameter("q") != null && !request.getParameter("q").isBlank()) {
							articlesVendus = ArticleVenduManager.getInstance()
									.recupUnArticlesVendusParUtilisateurSelonEtatVenteEtCategorie(utilisateur,
											noCategorie, q, "vf");

						} else
							// si que catégorie selectionné
							articlesVendus = ArticleVenduManager.getInstance()
									.recupArticlesVendusParUtilisateurSelonEtatVenteEtCategorie(utilisateur,
											noCategorie, "vf");
					}
					// traitement si aucune catégorie selectionnée
					else {
						// si recherche par nom q
						if (request.getParameter("q") != null && !request.getParameter("q").isBlank()) {
							articlesVendus = ArticleVenduManager.getInstance()
									.recupUnArticlesVendusParUtilisateurSelonEtatVente(utilisateur, q, "vf");
						}

						else {
							articlesVendus = ArticleVenduManager.getInstance()
									.recupArticlesVendusParUtilisateurSelonEtatVente(utilisateur, "vf");

						}
					}

				}

			}

		}
		if (categories == null) {
			categories = CategorieManager.getInstance().recupTouteCategories();
		}
		request.setAttribute("categories", categories);

		request.setAttribute("articlesVendus", articlesVendus);
		List<Categorie> categoriesMenu = CategorieManager.getInstance().recupTouteCategories();
		request.setAttribute("categoriesMenu", categoriesMenu);
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
