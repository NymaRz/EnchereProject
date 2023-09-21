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

		
		
		if(request.getParameter("categorie")!=null &&Integer.parseInt(request.getParameter("categorie"))>0) {
			articlesVendus = ArticleVenduManager.getInstance().findArticlesVendusByCategorie(Integer.parseInt(request.getParameter("categorie")));
		} else {
			articlesVendus = ArticleVenduManager.getInstance().recupArticlesVendusEncheresOuvertes();
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
