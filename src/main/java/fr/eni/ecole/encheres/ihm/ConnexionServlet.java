package fr.eni.ecole.encheres.ihm;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import fr.eni.ecole.encheres.bll.UtilisateurManager;
import fr.eni.ecole.encheres.bo.Passport;
import fr.eni.ecole.encheres.bo.Utilisateur;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
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
		request.getRequestDispatcher("/WEB-INF/pages/connexion.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("email");
		String mdp = request.getParameter("mdp");
		boolean rememberMe = request.getParameter("remember_me") != null;

		try {
			Utilisateur user = UtilisateurManager.getInstance().login(email, mdp);

			if (user == null) {
				request.setAttribute("error", "Email ou mot de passe erroné");
				doGet(request, response);
			} else {
				HttpSession session = request.getSession();
				user.setMdp(""); // Assurez-vous que le mot de passe ne soit pas stocké dans la session

				// Définir la durée de validité de la session à 5 minutes
				session.setMaxInactiveInterval(300); // 300 secondes (5 minutes)
				session.setAttribute("utilisateur", user);

				if (rememberMe) {
					String token = UUID.randomUUID().toString();
					Cookie cookieSession = new Cookie("rememberMe", token);
					cookieSession.setMaxAge(60 * 60 * 24 * 30); // 30 jours
					cookieSession.setHttpOnly(true);

					Map<String, Passport> sessions = (Map<String, Passport>) this.getServletContext()
							.getAttribute("sessions");
					sessions.put(token, new Passport(request.getRemoteAddr(), request.getHeader("User-Agent"), user));
					response.addCookie(cookieSession);
				}
				response.sendRedirect(request.getContextPath() + "/accueil");
			}
		} catch (Exception e) {
			// Gérer les exceptions ici (par exemple, journalisation)
			e.printStackTrace();
			request.setAttribute("error", "Une erreur s'est produite lors de la connexion.");
			doGet(request, response);
		}
	}
}
