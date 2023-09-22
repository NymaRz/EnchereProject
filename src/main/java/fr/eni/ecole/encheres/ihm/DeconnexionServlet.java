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
		// Récupérer la session de l'utilisateur
		HttpSession session = request.getSession();

		// Invalider la session (déconnexion de l'utilisateur)
		session.invalidate();

		// Rediriger vers la page d'accueil de l'application
		response.sendRedirect(request.getContextPath() + "/accueil");
	}
}
