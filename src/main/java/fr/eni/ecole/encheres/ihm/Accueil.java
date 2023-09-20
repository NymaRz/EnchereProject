package fr.eni.ecole.encheres.ihm;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import fr.eni.ecole.encheres.bll.ArticleVenduManager;
import fr.eni.ecole.encheres.bll.CategorieManager;
import fr.eni.ecole.encheres.bo.ArticleVendu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/accueil")
public class Accueil extends HttpServlet {
	private static final long serialVersionUID = 1L;
//modif
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<ArticleVendu> articlesVendus = null;
		request.setAttribute("categories", CategorieManager.getInstance().recupTouteCategories());
<<<<<<< HEAD
		
=======

>>>>>>> a7c37600868203e67b905c48c9a8610137992db9
		if (request.getParameter("categorie") != null) {
			if (request.getParameter("q") != null) {
				articlesVendus = ArticleVenduManager.getInstance().rechercherUnArticleVenduByCategorie(CategorieManager
						.getInstance().recupUneCategorie(Integer.parseInt(request.getParameter("categorie"))), "q");
			} else {
				articlesVendus = ArticleVenduManager.getInstance().findArticlesVendusByCategorie(CategorieManager
						.getInstance().recupUneCategorie(Integer.parseInt(request.getParameter("categorie"))));
			}
		} else if (request.getParameter("q") != null) {
			articlesVendus = ArticleVenduManager.getInstance().rechercheUnArticleVendu(request.getParameter("q"));
		} else {
			articlesVendus = ArticleVenduManager.getInstance().recupTousLesArticlesVendus();
		}

		request.setAttribute("articlesVendus", articlesVendus);
		request.setAttribute("annee", LocalDate.now().getYear());

		request.getRequestDispatcher("WEB-INF/pages/accueil.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
