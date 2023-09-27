package fr.eni.ecole.encheres.ihm;

import java.io.File;
import java.io.IOException;

import fr.eni.ecole.encheres.bll.AdresseManager;
import fr.eni.ecole.encheres.bll.UtilisateurManager;
import fr.eni.ecole.encheres.bo.Adresse;
import fr.eni.ecole.encheres.bo.Utilisateur;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet("/modifierprofil")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class ModifierMonProfilServlet extends HttpServlet {
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

		if (request.getSession().getAttribute("utilisateur") == null) {
			response.sendRedirect(request.getContextPath() + "/modifierprofil");
			return;
		}
		try {
			HttpSession session = request.getSession();
			Utilisateur userSession = (Utilisateur) session.getAttribute("utilisateur");
			Utilisateur user = UtilisateurManager.getInstance().recupUnUtilisateur(userSession.getNoUtilisateur());
			request.setAttribute("utilisateur", user);
			request.getRequestDispatcher("/WEB-INF/pages/modifierprofil.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// Récupérer les paramètres depuis le formulaire
			String pseudo = request.getParameter("pseudo");
			String nom = request.getParameter("nom");
			String prenom = request.getParameter("prenom");
			String email = request.getParameter("email");
			String telephoneStr = request.getParameter("telephone");
			String adresseStr = request.getParameter("adresse");
			String creditStr = request.getParameter("credit");
			String mdp = request.getParameter("mdp");
			String photo = request.getParameter("photo");
			int telephone = Integer.parseInt(telephoneStr);
			int credit = Integer.parseInt(creditStr);

			// Récupérer l'utilisateur depuis la session
			HttpSession session = request.getSession();
			Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");

			// permet de récupérer l'image

			String fileName = null;
			for (Part part : request.getParts()) {
				if (part.getContentType() != null && !part.getContentType().equals("application/octet-stream")) {
					fileName = getFileName(part);
					String fullPath = uploadPath + File.separator + fileName;
					part.write(fullPath);
					if (!fileName.equals("Default.file"))
						break;
				}
			}

			// Mettre à jour les champs de l'utilisateur
			utilisateur.setPseudo(pseudo);
			utilisateur.setNom(nom);
			utilisateur.setPrenom(prenom);
			utilisateur.setEmail(email);
			utilisateur.setTelephone(telephoneStr);
			utilisateur.setCredit(credit);
			utilisateur.setMdp(mdp);
			utilisateur.setPhoto(fileName);

			// Mettre à jour l'adresse de l'utilisateur
			Adresse adresse = new Adresse();
			adresse.setRue(request.getParameter("rue"));
			adresse.setCodePostal(request.getParameter("codePostal"));
			adresse.setVille(request.getParameter("ville"));
			Adresse testAdresseBdd = AdresseManager.getInstance().recupAdresseParRueCPVille(request.getParameter("rue"),
					request.getParameter("codePostal"), request.getParameter("ville"));
			if (testAdresseBdd == null) {
				AdresseManager.getInstance().ajouterUneAdresse(adresse);
			}
			Adresse adresseBDD = AdresseManager.getInstance().recupAdresseParRueCPVille(request.getParameter("rue"),
					request.getParameter("codePostal"), request.getParameter("ville"));
			utilisateur.setAdresse(adresseBDD);

			// Modifier l'utilisateur
			if (fileName == null)
				UtilisateurManager.getInstance().modifierUnUtilisateurSansPhoto(utilisateur);
			else
				UtilisateurManager.getInstance().modifierUnUtilisateur(utilisateur);
			HttpSession sessions = request.getSession();
			sessions.setAttribute("success", "Modification du profil réussie");

			// Redirection
			response.sendRedirect(request.getContextPath() + "/profil");
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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