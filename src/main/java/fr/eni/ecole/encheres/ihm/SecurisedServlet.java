package fr.eni.ecole.encheres.ihm;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("")
public class SecurisedServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false); // Ne crée pas de nouvelle session si elle n'existe pas
		if (session != null) {
			Long startTime = (Long) session.getAttribute("startTime");
			if (startTime != null) {
				long currentTime = System.currentTimeMillis();
				long sessionDuration = (currentTime - startTime) / 1000; // Durée en secondes
				if (sessionDuration > 6000) { // 300 secondes (5 minutes)
					// La session a expiré, vous pouvez rediriger l'utilisateur vers la page de
					// déconnexion
					session.invalidate(); // Invalide la session
                    response.sendRedirect("accueil.jsp"); // Remplacez "accueil.jsp" par le chemin de votre page d'accueil
				}
			}
		}

	}

}
