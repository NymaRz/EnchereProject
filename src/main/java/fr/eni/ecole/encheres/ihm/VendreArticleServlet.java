package fr.eni.ecole.encheres.ihm;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import fr.eni.ecole.encheres.bll.AdresseManager;
import fr.eni.ecole.encheres.bll.ArticleVenduManager;
import fr.eni.ecole.encheres.bll.CategorieManager;
import fr.eni.ecole.encheres.bll.RetraitManager;
import fr.eni.ecole.encheres.bo.Adresse;
import fr.eni.ecole.encheres.bo.ArticleVendu;
import fr.eni.ecole.encheres.bo.Categorie;
import fr.eni.ecole.encheres.bo.Retrait;
import fr.eni.ecole.encheres.bo.Utilisateur;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet("/vendre-article")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class VendreArticleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*
	 * Chemin dans lequel les images seront sauvegardées.
	 */
	public static final String IMAGES_FOLDER = "/Images";
	public String uploadPath;

	/*
	 * Si le dossier de sauvegarde de l'image n'existe pas, on demande sa création.
	 */
	@Override
	public void init() throws ServletException {
		uploadPath = getServletContext().getRealPath(IMAGES_FOLDER);
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists())
			uploadDir.mkdir();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();

		// récupérer l'utilisateur
		Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
		if (utilisateur == null)
			response.sendRedirect(request.getContextPath() + "/connexion");
		else {
			request.setAttribute("adresse", utilisateur.getAdresse());
			request.setAttribute("categories", CategorieManager.getInstance().recupTouteCategories());
			request.getRequestDispatcher("/WEB-INF/pages/vendre-article.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String nomArticle = request.getParameter("nomArticle");
			String description = request.getParameter("description");
			Categorie categorie = CategorieManager.getInstance()
					.recupUneCategorie(Integer.parseInt(request.getParameter("categorie")));
			int miseAPrix = Integer.parseInt(request.getParameter("miseAPrix"));

			// permet de récupérer l'image
			String fileName = null;
			for (Part part : request.getParts()) {
				if(part.getContentType()!=null && !part.getContentType().equals("application/octet-stream") ) {					
					fileName = getFileName(part);
					String fullPath = uploadPath + File.separator + fileName;
					part.write(fullPath);			
				}
			}

			LocalDate dateDebutEncheres;
			if (request.getParameter("dateDebutEncheres").isBlank()) {
				dateDebutEncheres = LocalDate.now();
			} else {
				dateDebutEncheres = LocalDate.parse(request.getParameter("dateDebutEncheres"));
			}
			LocalDate dateFinEncheres = LocalDate.parse(request.getParameter("dateFinEncheres"));
			if (dateFinEncheres.isBefore(dateDebutEncheres) || dateFinEncheres.isEqual(dateDebutEncheres)) {
				throw new ServletException();
			}
			HttpSession session = request.getSession();

			// récupérer l'utilisateur
			Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
			

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
					miseAPrix, categorie, retraitBDD, utilisateur, fileName);
			if (dateDebutEncheres.isAfter(LocalDate.now())) {
				articleVendu.setEtatVente("av");
			} else {
				articleVendu.setEtatVente("v");
			}
			articleVendu.setPrixVente(miseAPrix);

			ArticleVenduManager.getInstance().ajouterUnArticleVendu(articleVendu);
			response.sendRedirect(request.getContextPath() + "/accueil");

		} catch (ServletException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getFileName(Part part) {
		for (String content : part.getHeader("content-disposition").split(";")) {			
			if (content.trim().startsWith("filename")) {				
				return content.substring(content.indexOf("=") + 2, content.length() - 1);
			}
		}
		return "Default.file";
	}
}