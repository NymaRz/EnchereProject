package fr.eni.ecole.encheres.ihm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import fr.eni.ecole.encheres.bll.AdresseManager;
import fr.eni.ecole.encheres.bll.UtilisateurManager;
import fr.eni.ecole.encheres.bo.Adresse;
import fr.eni.ecole.encheres.bo.Utilisateur;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet("/profil/modifierprofil")
public class ModifierMonProfilServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
			int telephone = Integer.parseInt(telephoneStr);
			int credit = Integer.parseInt(creditStr);

			// Récupérer l'utilisateur depuis la session
			HttpSession session = request.getSession();
			Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");

			// Mettre à jour les champs de l'utilisateur
			utilisateur.setPseudo(pseudo);
			utilisateur.setNom(nom);
			utilisateur.setPrenom(prenom);
			utilisateur.setEmail(email);
			utilisateur.setTelephone(telephoneStr);
			utilisateur.setCredit(credit);
			utilisateur.setMdp(mdp);

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
			UtilisateurManager.getInstance().modifierUnUtilisateur(utilisateur);
			HttpSession sessions = request.getSession();
			sessions.setAttribute("success", "Modification du profil réussie");

			// Redirection
			response.sendRedirect(request.getContextPath() + "/profil/modifierprofil");
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

		}

		// Récupérez le fichier téléchargé
		Part filePart = request.getPart("photo");

		// Obtenez le chemin où vous souhaitez enregistrer le fichier sur le serveur
		String savePath = getServletContext().getRealPath("/uploads"); // Vous pouvez spécifier le chemin de sauvegarde
																		// souhaité

		// Obtenez le nom de fichier
		String fileName = filePart.getSubmittedFileName();

		// Enregistrez le fichier sur le serveur
		File file = new File(savePath, fileName);
		try (InputStream input = filePart.getInputStream(); OutputStream output = new FileOutputStream(file)) {
			byte[] buffer = new byte[1024];
			int length;
			while ((length = input.read(buffer)) > 0) {
				output.write(buffer, 0, length);
			}
		}

		// Enregistrez le chemin du fichier dans une variable de session ou dans votre
		// base de données
		String filePath = "/uploads/" + fileName;
		request.getSession().setAttribute("uploadedPhotoPath", filePath);

		// Redirigez l'utilisateur vers la page de profil
		response.sendRedirect(request.getContextPath() + "/profil");
	}
}