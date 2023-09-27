package fr.eni.ecole.encheres.ihm;

import java.io.IOException;

import fr.eni.ecole.encheres.bll.UtilisateurManager;
import fr.eni.ecole.encheres.bll.exception.BLLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/forget-password")
public class ResetPasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/forget-password.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String email = request.getParameter("email");
            String password = request.getParameter("mdp");
            String newPassword = request.getParameter("newPassword");
            
            // Vérifier si l'ancien mot de passe est valide
            boolean isPasswordValid = UtilisateurManager.getInstance().checkPassword(email, password);

            if (isPasswordValid) {
                // Mettre à jour le mot de passe en BDD
                UtilisateurManager.getInstance().updatePassword(email, newPassword);
                response.sendRedirect(request.getContextPath() + "/connexion");
                return; // Ajout pour éviter l'exécution du code ci-dessous
            } else {
                // Ancien mot de passe invalide, afficher un message d'erreur
                request.setAttribute("error", "L'ancien mot de passe est incorrect.");
                request.getRequestDispatcher("/WEB-INF/pages/forget-password.jsp").forward(request, response);
            }

        } catch (BLLException e) {
            // Gérer les exceptions BLL
            e.printStackTrace();
            // Rediriger vers une page d'erreur spécifique aux exceptions BLL
            request.setAttribute("error", "Une erreur s'est produite lors de la réinitialisation du mot de passe.");
            request.getRequestDispatcher("/WEB-INF/pages/error404.jsp").forward(request, response);
        }
    }
}
