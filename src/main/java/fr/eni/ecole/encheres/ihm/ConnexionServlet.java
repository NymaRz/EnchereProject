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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("email");
		String mdp = request.getParameter("mdp");

		Utilisateur user = UtilisateurManager.getInstance().login(email, mdp);

		if (user == null) {

			request.setAttribute("error", "Email ou mot de passe éronné");
			doGet(request, response);
		} else {

			HttpSession session = request.getSession();
			user.setMdp("");

			session.setAttribute("utilisateur", user);
			response.sendRedirect(request.getContextPath() + "/accueil");// ACCUEIL CONNECTER A CHANGER
			System.out.println(session.getAttribute("utilisateur"));
		}
	}
}
