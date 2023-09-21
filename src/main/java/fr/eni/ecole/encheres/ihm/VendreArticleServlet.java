package fr.eni.ecole.encheres.ihm;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet ("/vendre-article")

public class VendreArticleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/pages/vendre-article.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}


//<h1>${ articleVendu.nomArticle }</h1>
//
//
//
//<label for="Description" class="form-label">Description :</label>
//
//<input readonly="readonly" value="${ articleVendu.description }" />
//
// 
//
//<label for="MeilleurOffre" class="form-label">Meilleure offre :</label>
//
//<input readonly="readonly" value="${ enchere.montant_enchere }" />
//
// 
//
//<label for="Retrait" class="form-label">Retrait :</label>
//
//<input readonly="readonly" value="${ articleVendu.miseAPrix }" />
//
// 
//
//<label for="Retrait" class="form-label">Retrait :</label>
//
//<input readonly="readonly" value="${ articleVendu.lieuRetrait }" />
//
// 
//
//<label for="Vendeur" class="form-label">Vendeur :</label>
//
//<input readonly="readonly" value="${ articleVendu.utilisateur }" />
//
// 
//
//<label for="Tel" class="form-label">Tel :</label>
//
//<input readonly="readonly" value="${ utilisateur.telephone }" />
//
// 
//
//<a href="${ pageContext.request.contextPath }/">Précédent</a>
//
//</main>