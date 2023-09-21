package fr.eni.ecole.encheres.ihm;

import java.io.IOException;

import fr.eni.ecole.encheres.bll.UtilisateurManager;
import fr.eni.ecole.encheres.bll.exception.BLLException;
import fr.eni.ecole.encheres.bo.Adresse;
import fr.eni.ecole.encheres.bo.Utilisateur;
import fr.eni.ecole.encheres.dal.jdbc.exception.JDBCException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/inscription")
public class CreerCompteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/pages/CreerCompte.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			Adresse adresse = new Adresse();
			adresse.setRue(request.getParameter("rue"));
			adresse.setCodePostal(request.getParameter("code_postal"));
			adresse.setVille(request.getParameter("ville"));

			Utilisateur utilisateur = new Utilisateur();
			utilisateur.setPseudo(request.getParameter("pseudo"));
			utilisateur.setMdp(request.getParameter("mdp"));
			utilisateur.setNom(request.getParameter("nom"));
			utilisateur.setPrenom(request.getParameter("prenom"));
			utilisateur.setEmail(request.getParameter("email"));
			utilisateur.setTelephone(request.getParameter("telephone"));
			utilisateur.setAdresse(adresse);
			UtilisateurManager.getInstance().inscription(utilisateur);
			// Flash
			request.getSession().setAttribute("success", "Le compte a bien été créé!");
			response.sendRedirect(request.getContextPath() + "/connexion");
		} catch (JDBCException | BLLException e) {
			e.printStackTrace();
			request.setAttribute("error", e.getMessage());
			doGet(request, response);
		}
	}
}
