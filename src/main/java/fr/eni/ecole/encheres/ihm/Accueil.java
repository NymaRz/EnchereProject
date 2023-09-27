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

		request.setAttribute("categories", CategorieManager.getInstance().recupTouteCategories());
		String q = request.getParameter("q");

		ArticleVenduManager.getInstance().updateAllArticles();

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
					articlesVendus = ArticleVenduManager.getInstance().recupArticlesVendusSelonEtatVente("v");
				}
			}
			
			

			if (articlesVendus == null)
				articlesVendus = ArticleVenduManager.getInstance().recupTousLesArticlesVendus();

		}

		else {
			String checkListeEncheres = request.getParameter("listeEncheres");
			if (checkListeEncheres == null) {
				articlesVendus = ArticleVenduManager.getInstance().recupTousLesArticlesVendus();
			} else {
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
							articlesVendus = ArticleVenduManager.getInstance()
									.recupUnArticleEncheriParutilisateurEtCategorie(utilisateur, noCategorie, q);

						} else
							// si que catégorie selectionné
							articlesVendus = ArticleVenduManager.getInstance()
									.recupArticlesEncherisParUtilisateurEtCategorie(utilisateur, noCategorie);
					}
					// traitement si aucune catégorie selectionnée
					else {
						// si recherche par nom q
						if (request.getParameter("q") != null && !request.getParameter("q").isBlank())
							articlesVendus = ArticleVenduManager.getInstance()
									.recupUnArticleEncheriParUtilisateur(utilisateur, q);

						else
							articlesVendus = ArticleVenduManager.getInstance()
									.recupArticlesEncheresParUtilisateur(utilisateur);
					}

				}

				if (checkListeEncheres.equals("encheresRemportees")) {
					request.setAttribute("nomListe", "Mes enchères remportées");
					if (request.getParameter("categorie") != null
							&& Integer.parseInt(request.getParameter("categorie")) > 0) {

						int noCategorie = Integer.parseInt(request.getParameter("categorie"));

						// si en plus de la catégorie une recherche par nom existe

						if (request.getParameter("q") != null && !request.getParameter("q").isBlank()) {
							List<ArticleVendu> articlesEncheris = ArticleVenduManager.getInstance()
									.recupUnArticleEncheriParutilisateurEtCategorie(utilisateur, noCategorie, q);

							for (ArticleVendu articleVendu : articlesEncheris) {

								if (EnchereManager.getInstance().recupLeGagnantEnchere(articleVendu) != null
										&& EnchereManager.getInstance().recupLeGagnantEnchere(articleVendu)
												.getAcquereur().equals(utilisateur))
									articlesVendus.add(articleVendu);
							}

						} else {
							// si que catégorie selectionné
							List<ArticleVendu> articlesEncheris = ArticleVenduManager.getInstance()
									.recupArticlesEncherisParUtilisateurEtCategorie(utilisateur, noCategorie);

							for (ArticleVendu articleVendu : articlesEncheris) {

								if (EnchereManager.getInstance().recupLeGagnantEnchere(articleVendu) != null
										&& EnchereManager.getInstance().recupLeGagnantEnchere(articleVendu)
												.getAcquereur().equals(utilisateur))
									articlesVendus.add(articleVendu);
							}

						}
					}
					// traitement si aucune catégorie selectionnée
					else {
						// si recherche par nom q
						if (request.getParameter("q") != null && !request.getParameter("q").isBlank()) {

							List<ArticleVendu> articlesEncheris = ArticleVenduManager.getInstance()
									.recupUnArticleEncheriParUtilisateur(utilisateur, q);

							for (ArticleVendu articleVendu : articlesEncheris) {

								if (EnchereManager.getInstance().recupLeGagnantEnchere(articleVendu) != null
										&& EnchereManager.getInstance().recupLeGagnantEnchere(articleVendu)
												.getAcquereur().equals(utilisateur))
									articlesVendus.add(articleVendu);
							}

						}

						else {
							List<ArticleVendu> articlesEncheris = ArticleVenduManager.getInstance()
									.recupArticlesEncheresParUtilisateur(utilisateur);

							for (ArticleVendu articleVendu : articlesEncheris) {

								if (EnchereManager.getInstance().recupLeGagnantEnchere(articleVendu) != null
										&& EnchereManager.getInstance().recupLeGagnantEnchere(articleVendu)
												.getAcquereur().equals(utilisateur))
									articlesVendus.add(articleVendu);
							}
						}
					}
//					
//					
//					

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

		System.out.println(articlesVendus);
		request.setAttribute("articlesVendus", articlesVendus);
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
