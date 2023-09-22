package fr.eni.ecole.encheres.ihm;

import java.io.IOException;
import java.time.LocalDate;

import fr.eni.ecole.encheres.bll.AdresseManager;
import fr.eni.ecole.encheres.bll.ArticleVenduManager;
import fr.eni.ecole.encheres.bll.CategorieManager;
import fr.eni.ecole.encheres.bll.RetraitManager;
import fr.eni.ecole.encheres.bll.UtilisateurManager;
import fr.eni.ecole.encheres.bo.Adresse;
import fr.eni.ecole.encheres.bo.ArticleVendu;
import fr.eni.ecole.encheres.bo.Categorie;
import fr.eni.ecole.encheres.bo.Retrait;
import fr.eni.ecole.encheres.bo.Utilisateur;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/vendre-article")

public class VendreArticleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();

		String email = (String) session.getAttribute("email");
		Utilisateur utilisateur = UtilisateurManager.getInstance().findByEmail(email);
		request.setAttribute("adresse", utilisateur.getAdresse());

		request.getRequestDispatcher("/WEB-INF/pages/vendre-article.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String nomArticle = request.getParameter("nomArticle");
			String description = request.getParameter("description");
			Categorie categorie = CategorieManager.getInstance()
					.recupUneCategorie(Integer.parseInt(request.getParameter("categorie")));
			int miseAPrix = Integer.parseInt(request.getParameter("miseAPrix"));
			LocalDate dateDebutEncheres;
			if (request.getParameter("dateDebutEncheres") == null) {
				dateDebutEncheres = LocalDate.now();
			} else {
				dateDebutEncheres = LocalDate.parse(request.getParameter("dateDebutEncheres"));
			}
			LocalDate dateFinEncheres = LocalDate.parse(request.getParameter("dateFinEncheres"));
			if (dateFinEncheres.isBefore(dateDebutEncheres) || dateFinEncheres.isEqual(dateDebutEncheres)) {
				throw new ServletException();
			}
			HttpSession session = request.getSession();

			String email = (String) session.getAttribute("email");

			Utilisateur utilisateur = UtilisateurManager.getInstance().findByEmail(email);

			// prévoir de récupérer l'adresse de l'utilisateur par défaut

			// le bout de code suivant risque de ne pas marcher, à revoir
			String rue = request.getParameter("rue");
			String codePostal = request.getParameter("codePostal");
			String ville = request.getParameter("ville");
			// vérifier si l'adresse est déjà dans la BDD
			Adresse adresseVerifBDD = AdresseManager.getInstance().recupAdresseParRueCPVille(rue, codePostal, ville);
			if (adresseVerifBDD == null) {
				Adresse adresse = new Adresse(rue, codePostal, ville);
				AdresseManager.getInstance().ajouterUneAdresse(adresse);
			}
			Adresse adresseBDD = AdresseManager.getInstance().recupAdresseParRueCPVille(rue, codePostal, ville);
			Retrait retrait = new Retrait(adresseBDD);
			RetraitManager.getInstance().ajouterUnRetrait(retrait);
			Retrait retraitBDD = RetraitManager.getInstance().recupRetraitParAdresse(adresseBDD);

			// fin code à revoir

			ArticleVendu articleVendu = new ArticleVendu(0, nomArticle, description, dateDebutEncheres, dateFinEncheres,
					miseAPrix, categorie, retraitBDD, utilisateur);
			ArticleVenduManager.getInstance().ajouterUnArticleVendu(articleVendu);
			response.sendRedirect(request.getContextPath() + "/accueil");

		} catch (ServletException e) {
			e.printStackTrace();
		}
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