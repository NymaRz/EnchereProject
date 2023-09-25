package fr.eni.ecole.encheres.ihm;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/deconnexion")
public class DeconnexionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Récupérer la session en cours
		HttpSession utilisateur = request.getSession(false); // Ne crée pas de nouvelle session si elle n'existe pas

		if (utilisateur != null) {
			// Invalider la session (déconnexion)
			utilisateur.invalidate();
		}

		// Rediriger vers la page d'accueil ou une page de confirmation de déconnexion
		response.sendRedirect(request.getContextPath() + "/accueil");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Cette servlet gère uniquement les déconnexions via la méthode GET
		doGet(request, response);
	}
}
