package fr.eni.ecole.encheres.ihm;

import java.io.IOException;

import fr.eni.ecole.encheres.bll.AdresseManager;
import fr.eni.ecole.encheres.bll.UtilisateurManager;
import fr.eni.ecole.encheres.bo.Adresse;
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
			
			String idStr = request.getParameter("no_utilisateur");
			int idUser = 0;
			if(idStr==null) {				
				HttpSession session = request.getSession();
				Utilisateur userSession = (Utilisateur) session.getAttribute("utilisateur");
				idUser = userSession.getNoUtilisateur();
			}else {
				idUser = Integer.parseInt(idStr);
			}
			// récupérer le param dans url
			// récupérer l'objet game
			Utilisateur user = UtilisateurManager.getInstance().recupUnUtilisateur(idUser);
//			Adresse adresse = AdresseManager.getInstance().recupUneAdresse(userSession.IdAdresse());
			// transmettre l'objet vers la jsp
			request.setAttribute("utilisateur", user);
//			request.setAttribute("adresse", adresse);
			// forward
			request.getRequestDispatcher("/WEB-INF/pages/profil.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(404);
		}

	}
}