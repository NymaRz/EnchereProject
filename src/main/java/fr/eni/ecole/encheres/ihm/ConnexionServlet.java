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
	private static final String SESSION_EMAIL_ATTRIBUTE = "email";
	private static final String SUCCESS_MESSAGE_ATTRIBUTE = "success";
	private static final String ERROR_MESSAGE_ATTRIBUTE = "error";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String message = (String) request.getSession().getAttribute(SUCCESS_MESSAGE_ATTRIBUTE);
		request.getSession().removeAttribute(SUCCESS_MESSAGE_ATTRIBUTE);
		request.setAttribute(SUCCESS_MESSAGE_ATTRIBUTE, message);
		request.getRequestDispatcher("/WEB-INF/pages/Connexion.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("mdp");

		try {
			Utilisateur utilisateur = UtilisateurManager.getInstance().login(email, password);

			if (utilisateur != null) {
				request.setAttribute(ERROR_MESSAGE_ATTRIBUTE, "L'email ou le mot de passe est incorrect");
				doGet(request, response);
			} else {
				HttpSession session = request.getSession();
				utilisateur.setMdp(""); // Ne pas stocker le mot de passe en session
				session.setAttribute(SESSION_EMAIL_ATTRIBUTE, email);
				response.sendRedirect(request.getContextPath() + "/accueil");
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ERROR_MESSAGE_ATTRIBUTE, "Une erreur s'est produite lors de la connexion.");
			doGet(request, response);
		}
	}
}
