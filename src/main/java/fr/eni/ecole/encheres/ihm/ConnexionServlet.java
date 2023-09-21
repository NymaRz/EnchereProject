package fr.eni.ecole.encheres.ihm;

import java.io.IOException;

import fr.eni.ecole.encheres.bll.UtilisateurManager;
import fr.eni.ecole.encheres.bo.Utilisateur;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/connexion")
public class ConnexionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String message = (String) request.getSession().getAttribute("success");
		request.getSession().removeAttribute("success");
		request.setAttribute("success", message);
		request.getRequestDispatcher("/WEB-INF/pages/Connexion.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// Simulez une authentification réussie en vérifiant les informations
			// d'identification
			String username = request.getParameter("username");
			String password = request.getParameter("password");

			// Vérifiez les informations d'identification (vous devrez implémenter cette
			// partie)
			// Si l'authentification réussit, obtenez l'objet Utilisateur correspondant

			// Exemple simplifié : supposons que l'authentification réussit et vous avez un
			// objet Utilisateur
			Utilisateur utilisateurAuthentifie = getUtilisateurFromDatabase(username);

			// Stockez l'utilisateur dans la session
			HttpSession session = request.getSession();
			session.setAttribute("utilisateur", utilisateurAuthentifie);

			// Redirigez l'utilisateur vers la page de profil
			response.sendRedirect(request.getContextPath() + "/Mon-profil");
		} catch (Exception e) {
			e.printStackTrace(); // Gérer les exceptions correctement
			response.sendError(500); // Envoyer une erreur 500 en cas d'erreur
		}
	}

	private Utilisateur getUtilisateurFromDatabase(String username) {
		return null;
	}
}
