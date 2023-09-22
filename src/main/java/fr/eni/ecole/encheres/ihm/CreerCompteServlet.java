package fr.eni.ecole.encheres.ihm;

import java.io.IOException;

import fr.eni.ecole.encheres.bll.AdresseManager;
import fr.eni.ecole.encheres.bll.UtilisateurManager;
import fr.eni.ecole.encheres.bll.exception.BLLException;
import fr.eni.ecole.encheres.bo.Adresse;
import fr.eni.ecole.encheres.bo.Utilisateur;
import fr.eni.ecole.encheres.dal.jdbc.exception.JDBCException;
import fr.eni.ecole.encheres.ihm.exception.EmailExisteDejaException;
import fr.eni.ecole.encheres.ihm.exception.PseudoExisteDejaException;
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
			String email = request.getParameter("email");
			String pseudo = request.getParameter("pseudo");

			// Vérifier si l'adresse e-mail existe déjà dans la base de données
			if (UtilisateurManager.getInstance().emailExisteDeja(email)) {
				throw new EmailExisteDejaException("Cette adresse e-mail est déjà utilisée par un autre utilisateur.");
			}

			// Vérifier si le pseudo existe déjà dans la base de données
			if (UtilisateurManager.getInstance().pseudoExisteDeja(pseudo)) {
				throw new PseudoExisteDejaException("Ce pseudo est déjà utilisé par un autre utilisateur.");
			}

			Adresse adresse = new Adresse();
			adresse.setRue(request.getParameter("rue"));
			adresse.setCodePostal(request.getParameter("code_postal"));
			adresse.setVille(request.getParameter("ville"));
			AdresseManager.getInstance().ajouterUneAdresse(adresse);
			Adresse adresseInBDD = AdresseManager.getInstance().recupAdresseParRueCPVille(adresse.getRue(),
					adresse.getCodePostal(), adresse.getVille());

			Utilisateur utilisateur = new Utilisateur();
			utilisateur.setPseudo(pseudo); // Utiliser le pseudo vérifié
			utilisateur.setMdp(request.getParameter("mdp"));
			utilisateur.setNom(request.getParameter("nom"));
			utilisateur.setPrenom(request.getParameter("prenom"));
			utilisateur.setEmail(email); // Utiliser l'e-mail vérifié
			utilisateur.setTelephone(request.getParameter("telephone"));
			utilisateur.setAdresse(adresseInBDD);
			UtilisateurManager.getInstance().inscription(utilisateur);

			// Flash
			request.getSession().setAttribute("success", "Le compte a bien été créé!");
			response.sendRedirect(request.getContextPath() + "/connexion");
		} catch (JDBCException | BLLException e) {
			e.printStackTrace();
			request.setAttribute("error",e.getMessage());
			request.getRequestDispatcher("/WEB-INF/pages/CreerCompte.jsp").forward(request, response);
		} catch (EmailExisteDejaException e) {
			e.printStackTrace();
			// Gérez spécifiquement l'exception EmailExisteDejaException ici, par exemple :
			request.setAttribute("emailError", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/pages/CreerCompte.jsp").forward(request, response);
		} catch (PseudoExisteDejaException e) {
			e.printStackTrace();
			// Gérez spécifiquement l'exception PseudoExisteDejaException ici, par exemple :
			request.setAttribute("pseudoError", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/pages/CreerCompte.jsp").forward(request, response);
		}

	}
}
