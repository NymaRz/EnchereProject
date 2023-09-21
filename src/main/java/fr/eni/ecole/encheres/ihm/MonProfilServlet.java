package fr.eni.ecole.encheres.ihm;

import java.io.IOException;

import fr.eni.ecole.encheres.bll.UtilisateurManager;
import fr.eni.ecole.encheres.bo.Utilisateur;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Mon-profil")
public class MonProfilServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// Récupérer l'utilisateur connecté (vous devez gérer l'authentification
			// ailleurs)
			// Supposons que vous avez un attribut "utilisateur" dans la session
			Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("utilisateur");

			// Vérifiez si l'utilisateur est connecté
			if (utilisateur == null) {
				// Transmettre l'utilisateur connecté vers la JSP
				request.setAttribute("utilisateur", utilisateur);
				// Forward vers la JSP
				request.getRequestDispatcher("/WEB-INF/pages/monprofil.jsp").forward(request, response);
			} else {
				// Redirigez l'utilisateur vers une page de connexion s'il n'est pas connecté
				response.sendRedirect(request.getContextPath() + "/Connexion.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace(); // Gérer les exceptions correctement
			response.sendError(500); // Envoyer une erreur 500 en cas d'erreur
		}
	}

	// Modifier un profil
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// Récupérer l'utilisateur connecté (vous devez gérer l'authentification
			// ailleurs)
			// Supposons que vous avez un attribut "utilisateurConnecte" dans la session
			Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("utilisateur");

			// Vérifiez si l'utilisateur est connecté
			if (utilisateur == null) {
				// Modifier les données de l'utilisateur à partir des paramètres du formulaire
				utilisateur.setNom(request.getParameter("nom"));
				utilisateur.setEmail(request.getParameter("email"));
				// Ajoutez d'autres modifications d'utilisateur si nécessaire

				// Utilisez le gestionnaire UtilisateurManager pour enregistrer les
				// modifications
				UtilisateurManager.getInstance().modifierUnUtilisateur(utilisateur);

				// Redirigez l'utilisateur vers la page de profil
				response.sendRedirect(request.getContextPath() + "/modifiermonprofil");
			} else {
				// Redirigez l'utilisateur vers une page de connexion s'il n'est pas connecté
				response.sendRedirect(request.getContextPath() + "/Connexion");
			}
		} catch (Exception e) {
			e.printStackTrace(); // Gérer les exceptions correctement
			response.sendError(500); // Envoyer une erreur 500 en cas d'erreur
		}
	}
}
