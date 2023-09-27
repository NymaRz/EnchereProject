package fr.eni.ecole.encheres.ihm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.eni.ecole.encheres.bll.ArticleVenduManager;
import fr.eni.ecole.encheres.bll.EnchereManager;
import fr.eni.ecole.encheres.bo.ArticleVendu;
import fr.eni.ecole.encheres.bo.Utilisateur;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/recherche-avancee")
public class RechercheAvanceeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
		List<ArticleVendu> articlesVendus = new ArrayList<ArticleVendu>();
		String checkListeEncheres = request.getParameter("listeEncheres");
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
				if (EnchereManager.getInstance().recupLeGagnantEnchere(articleVendu).getAcquereur().equals(utilisateur))
					articlesVendus.add(articleVendu);
			}
		}
		if (checkListeEncheres.equals("ventesEnCours")) {
			System.out.println("11111111111111111111111111111");
			articlesVendus = ArticleVenduManager.getInstance()
					.recupArticlesVendusParUtilisateurSelonEtatVente(utilisateur, "v");
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
		request.setAttribute("articlesVendus", articlesVendus);
		response.sendRedirect(request.getContextPath()+"/accueil");
	}
}
	
	
	
	
	
	
	
	
	
	














