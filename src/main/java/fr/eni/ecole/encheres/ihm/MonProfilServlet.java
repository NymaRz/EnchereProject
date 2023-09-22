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

@WebServlet("/Mon-profil")
public class MonProfilServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			HttpSession session = request.getSession();
			Utilisateur userSession = (Utilisateur) session.getAttribute("utilisateur");
			// récupérer le param dans url
			// récupérer l'objet game
			Utilisateur user = UtilisateurManager.getInstance().recupUnUtilisateur(userSession.getNoUtilisateur());
			// transmettre l'objet vers la jsp
			request.setAttribute("utilisateur", user);
			// forward
			request.getRequestDispatcher("/WEB-INF/pages/MonProfil.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(404);
		}

	}
}