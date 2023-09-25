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

@WebServlet("/profil")
public class ProfilServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			String idStr = request.getParameter("id");
			int idUser = 0;
			if (idStr == null) {
				// Si l'ID n'est pas fourni dans l'URL, utilisez l'ID de l'utilisateur de la
				// session
				HttpSession session = request.getSession();
				Utilisateur userSession = (Utilisateur) session.getAttribute("utilisateur");

				if (userSession != null) {
					idUser = userSession.getNoUtilisateur();
				} else {
					// Gérez le cas où il n'y a pas d'utilisateur en session (à vous de décider
					// comment)
					response.sendError(401, "Unauthorized"); // Statut 401 pour non autorisé
					return;
				}

			} else {
				idUser = Integer.parseInt(idStr);
			}

			// Récupérez l'objet utilisateur
			Utilisateur user = UtilisateurManager.getInstance().recupUnUtilisateur(idUser);

			if (user != null) {
				// Transmettez l'objet utilisateur à la JSP
				request.setAttribute("utilisateur", user);

				// Utilisez le dispatcher pour transférer la requête à la page JSP
				// correspondante
				request.getRequestDispatcher("/WEB-INF/pages/profil.jsp").forward(request, response);
			} else {
				// Gérez le cas où l'utilisateur n'a pas été trouvé (à vous de décider comment)
				response.sendError(404, "Utilisateur non trouvé"); // Statut 404 
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(500, "Erreur interne du serveur"); // Statut 500 pour erreur interne du serveur
		}
	}
}
