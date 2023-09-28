package fr.eni.ecole.encheres.ihm;

import java.io.IOException;

import fr.eni.ecole.encheres.bll.UtilisateurManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/supprimerprofil")
public class SupprimerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

//modif
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			
			
			// récupérer le paramètre "id" depuis l'URL
			int id = Integer.parseInt(request.getParameter("id"));
			HttpSession utilisateur = request.getSession(false); // Ne crée pas de nouvelle session si elle n'existe pas

			if (utilisateur != null) {
				// Invalider la session (déconnexion)
				utilisateur.invalidate();
			}
			// supprimer un utilisateur
			UtilisateurManager.getInstance().supprimerUnUtilisateur(id);
			// redirection vers la page d'accueil
			response.sendRedirect(request.getContextPath() + "/accueil");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
