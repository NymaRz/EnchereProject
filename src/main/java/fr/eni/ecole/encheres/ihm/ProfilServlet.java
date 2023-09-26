package fr.eni.ecole.encheres.ihm;

import java.io.IOException;
import java.util.List;

import fr.eni.ecole.encheres.bll.ArticleVenduManager;
import fr.eni.ecole.encheres.bll.UtilisateurManager;
import fr.eni.ecole.encheres.bo.ArticleVendu;
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
					response.sendError(401, "Unauthorized");
					return;
				}
			} else {
				idUser = Integer.parseInt(idStr);
			}

			// Récupérez l'objet utilisateur
			Utilisateur user = UtilisateurManager.getInstance().recupUnUtilisateur(idUser);

			if (user != null) {
				// Récupérez la liste des articles vendus par l'utilisateur
				List<ArticleVendu> articlesVendus = ArticleVenduManager.getInstance().recupTousLesArticlesVendus();

				// Transmettez l'objet utilisateur et la liste des articles vendus à la JSP
				request.setAttribute("utilisateur", user);
				request.setAttribute("articlesVendus", articlesVendus);

				// Utilisez le dispatcher pour transférer la requête à la page JSP
				// correspondante
				request.getRequestDispatcher("/WEB-INF/pages/profil.jsp").forward(request, response);
			} else {
				response.sendError(404, "Utilisateur non trouvé");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(500, "Erreur interne du serveur");
		}
	}
}
