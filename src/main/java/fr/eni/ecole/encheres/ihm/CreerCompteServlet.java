package fr.eni.ecole.encheres.ihm;

import java.io.File;
import java.io.IOException;

import fr.eni.ecole.encheres.bll.AdresseManager;
import fr.eni.ecole.encheres.bll.UtilisateurManager;
import fr.eni.ecole.encheres.bll.exception.BLLException;
import fr.eni.ecole.encheres.bo.Adresse;
import fr.eni.ecole.encheres.bo.Utilisateur;
import fr.eni.ecole.encheres.dal.jdbc.exception.JDBCException;
import fr.eni.ecole.encheres.ihm.exception.EmailExisteDejaException;
import fr.eni.ecole.encheres.ihm.exception.PseudoExisteDejaException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet("/inscription")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class CreerCompteServlet extends HttpServlet {
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
		request.getRequestDispatcher("/WEB-INF/pages/creercompte.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String email = request.getParameter("email");
			String pseudo = request.getParameter("pseudo");

			// Vérifier si l'adresse e-mail existe déjà dans la base de données
			if (UtilisateurManager.getInstance().emailExisteDeja(email)) {
				throw new EmailExisteDejaException("Cette adresse e-mail est déjà utilisée par un autre utilisateur.");
			}

			// Vérifier si le pseudo existe déjà dans la base de données
			if (UtilisateurManager.getInstance().pseudoExisteDeja(pseudo)) {
				throw new PseudoExisteDejaException("Ce pseudo est déjà utilisé par un autre utilisateur.");
			}
			// permet de récupérer l'image
			String fileName = null;
			for (Part part : request.getParts()) {
				fileName = getFileName(part);
				String fullPath = uploadPath + File.separator + fileName;
				part.write(fullPath);
				if (!fileName.equals("Default.file"))
					break;
			}

			Adresse adresse = new Adresse();
			adresse.setRue(request.getParameter("rue"));
			adresse.setCodePostal(request.getParameter("code_postal"));
			adresse.setVille(request.getParameter("ville"));
			AdresseManager.getInstance().ajouterUneAdresse(adresse);
			Adresse adresseInBDD = AdresseManager.getInstance().recupAdresseParRueCPVille(adresse.getRue(),
					adresse.getCodePostal(), adresse.getVille());

			Utilisateur utilisateur = new Utilisateur();
			utilisateur.setPseudo(pseudo); // Utiliser le pseudo vérifié
			utilisateur.setMdp(request.getParameter("mdp"));
			utilisateur.setNom(request.getParameter("nom"));
			utilisateur.setPrenom(request.getParameter("prenom"));
			utilisateur.setEmail(email); // Utiliser l'e-mail vérifié
			utilisateur.setTelephone(request.getParameter("telephone"));
			utilisateur.setAdresse(adresseInBDD);
			utilisateur.setPhoto(fileName);
			utilisateur.setCredit(500);
			UtilisateurManager.getInstance().inscription(utilisateur);

			// Flash
			request.getSession().setAttribute("success", "Le compte a bien été créé!");
			response.sendRedirect(request.getContextPath() + "/connexion");
		} catch (JDBCException | BLLException e) {
			e.printStackTrace();
			request.setAttribute("error",e.getMessage());
			request.getRequestDispatcher("/WEB-INF/pages/creercompte.jsp").forward(request, response);
		} catch (EmailExisteDejaException e) {
			e.printStackTrace();
			// Gérez spécifiquement l'exception EmailExisteDejaException ici, par exemple :
			request.setAttribute("emailError", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/pages/creercompte.jsp").forward(request, response);
		} catch (PseudoExisteDejaException e) {
			e.printStackTrace();
			// Gérez spécifiquement l'exception PseudoExisteDejaException ici, par exemple :
			request.setAttribute("pseudoError", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/pages/creercompte.jsp").forward(request, response);
		}

	}

	/*
	 * Récupération du nom du fichier dans la requête.
	 */
	private String getFileName(Part part) {
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf("=") + 2, content.length() - 1);
			}
		}
		return "DefaultProfile.file";
	}
}
