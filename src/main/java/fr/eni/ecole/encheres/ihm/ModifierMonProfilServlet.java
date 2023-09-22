package fr.eni.ecole.encheres.ihm;

import java.io.IOException;

import fr.eni.ecole.encheres.bll.UtilisateurManager;
import fr.eni.ecole.encheres.bo.Adresse;
import fr.eni.ecole.encheres.bo.Utilisateur;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/profil/modifierprofil")
public class ModifierMonProfilServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			if (request.getSession().getAttribute("utilisateur") == null) {
				response.sendRedirect(request.getContextPath() + "/connexion");
				return;
			}
			HttpSession session = request.getSession();
			Utilisateur userSession = (Utilisateur) session.getAttribute("utilisateur");
			// récupérer le param dans url
			// récupérer l'objet game
			Utilisateur user = UtilisateurManager.getInstance().recupUnUtilisateur(userSession.getNoUtilisateur());
			// transmettre l'objet vers la jsp
			request.setAttribute("utilisateur", user);
			// forward
			request.getRequestDispatcher("/WEB-INF/pages/ModifierProfil.jsp").forward(request, response);
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
			String mdp = request.getParameter("mdp");
			String creditStr = request.getParameter("credit");

			// Conversion des types si nécessaire
			int telephone = Integer.parseInt(telephoneStr);
			int credit = Integer.parseInt(creditStr);

			// Création d'une instance Adresse
			Adresse adresse = new Adresse();
			adresse.setRue(request.getParameter("rue"));
			adresse.setCodePostal(request.getParameter("code_postal"));
			adresse.setVille(request.getParameter("ville"));

			// Création de l'instance Utilisateur
			Utilisateur user = new Utilisateur();
			user.setPseudo(pseudo);
			user.setNom(nom);
			user.setPrenom(prenom);
			user.setEmail(email);
			user.setTelephone(telephoneStr); // Utilisation de la chaîne de caractères
			user.setAdresse(adresse);
			user.setMdp(mdp);
			user.setCredit(credit);

			// Modifier l'utilisateur
			UtilisateurManager.getInstance().modifierUnUtilisateur(user);

			// Redirection
			response.sendRedirect(request.getContextPath() + "/profil/modifierprofil");
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
}
