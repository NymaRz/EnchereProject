package fr.eni.ecole.encheres.bll;

import java.util.List;
import java.util.Random;

import fr.eni.ecole.encheres.bll.exception.BLLException;
import fr.eni.ecole.encheres.bo.ForgetPassword;
import fr.eni.ecole.encheres.bo.Utilisateur;
import fr.eni.ecole.encheres.dal.DaoFactory;
import fr.eni.ecole.encheres.dal.UtilisateurDao;
import fr.eni.ecole.encheres.dal.jdbc.exception.JDBCException;
import fr.eni.ecole.encheres.ihm.exception.EmailExisteDejaException;
import fr.eni.ecole.encheres.ihm.exception.PseudoExisteDejaException;

public class UtilisateurManager {
    // singleton
    private static UtilisateurManager instance;

    private UtilisateurManager() {
    }

    public static UtilisateurManager getInstance() {
        if (instance == null)
            instance = new UtilisateurManager();
        return instance;
    }

    // fin singleton
    private UtilisateurDao utilisateurDao = DaoFactory.getUtilisateurDao();

    // début logique métier

    public Utilisateur recupUnUtilisateur(int idUtilisateur) {
        return utilisateurDao.findOne(idUtilisateur);
    }

    public List<Utilisateur> recupTousLesUtilisateurs() {
        return utilisateurDao.findAll();
    }

    public void modifierUnUtilisateur(Utilisateur utilisateur) {
        // Ajoutez ici la logique de validation des données si nécessaire.
        utilisateurDao.modify(utilisateur);
    }

    public void modifierUnUtilisateurSansPhoto(Utilisateur utilisateur) {
        // Ajoutez ici la logique de validation des données si nécessaire.
        utilisateurDao.modifynopic(utilisateur);
    }

    public void ajouterUnUtilisateur(Utilisateur utilisateur)
            throws EmailExisteDejaException, PseudoExisteDejaException {
        // Ajoutez ici la logique de validation des données si nécessaire.
        utilisateurDao.save(utilisateur);
    }

    public void supprimerUnUtilisateur(int idUtilisateur) {
        utilisateurDao.remove(idUtilisateur);
    }

    public Utilisateur rechercheUnUtilisateur(String query) {
        return utilisateurDao.findByPseudo(query);
    }

    private UtilisateurDao userDao = DaoFactory.getUtilisateurDao();
    private Random rd = new Random();

    public void inscription(Utilisateur utilisateur)
            throws JDBCException, BLLException, EmailExisteDejaException, PseudoExisteDejaException {
        // validation !!!!!!!!
        checkFields(utilisateur);
        userDao.save(utilisateur);
    }

    private void checkFields(Utilisateur utilisateur) throws BLLException {
        if (utilisateur == null)
            throw new BLLException("User est null");

        if (utilisateur.getPseudo().isBlank())
            throw new BLLException("Le champ Pseudo est obligatoire!");
        if (utilisateur.getEmail().isBlank())
            throw new BLLException("Le champ Email est obligatoire!");
        // vérifier la syntaxe de l'e-mail
        if (utilisateur.getMdp().isBlank())
            throw new BLLException("Le champ Mot de passe est obligatoire!");
        if (utilisateur.getMdp().length() < 8 || utilisateur.getMdp().length() > 35)
            throw new BLLException("La taille du mot de passe doit être entre 8 et 35");
        // if(!utilisateur.getPassword().equals(utilisateur.getConfirmpassword))
    }

    public Utilisateur login(String email, String mdp) {
        Utilisateur utilisateur = (Utilisateur) utilisateurDao.findByEmail(email);
        if (utilisateur != null && utilisateur.getEmail().equals(email) && utilisateur.getMdp().equals(mdp)) {
            return utilisateur;
        }
        return null;
    }

    public ForgetPassword checkEmail(String email) throws BLLException {

        Utilisateur utilisateur = userDao.findByEmail(email);
        if (utilisateur == null)
            throw new BLLException("Erreur : l'e-mail n'existe pas");
        // Générer le code
        String code = rd.nextLong(1, 9999999999L) + "";
        ForgetPassword fp = new ForgetPassword(code, utilisateur);

        // save
        DaoFactory.getForgetPasswordDao().save(fp);
        // envoi mail

        // par SMS API
        System.out.println(code);

        return fp;
    }

    public void resetPassword(String email, String code, String newPassword) throws BLLException {
        try {
            ForgetPassword fp = DaoFactory.getForgetPasswordDao().findByEmail(email);

            if (fp == null)
                throw new BLLException("Aucune demande de réinitialisation de mot de passe trouvée pour cet e-mail.");

            if (!fp.getCode().equals(code))
                throw new BLLException("Le code de réinitialisation est incorrect!");

            Utilisateur utilisateur = fp.getUser();
            utilisateur.setMdp(newPassword);

            userDao.modify(utilisateur);
            DaoFactory.getForgetPasswordDao().remove(fp.getId());
        } catch (Exception e) {
            throw new BLLException("Erreur lors de la réinitialisation du mot de passe.", e);
        }
    }

    public Utilisateur findByEmail(String email) throws BLLException {
        return utilisateurDao.findByEmail(email);
    }

    public boolean pseudoExisteDeja(String pseudo) {
        // Ajoutez la logique pour vérifier si le pseudo existe déjà en base de données
        return false;
    }

    public boolean emailExisteDeja(String email) {
        // Ajoutez la logique pour vérifier si l'email existe déjà en base de données
        return false;
    }

    public boolean checkPassword(String email, String password) {
        try {
            Utilisateur utilisateur = findByEmail(email);
            if (utilisateur != null && utilisateur.getMdp().equals(password)) {
                return true;
            }
        } catch (BLLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void updatePassword(String email, String newPassword) throws BLLException {
        try {
            Utilisateur utilisateur = findByEmail(email);
            if (utilisateur != null) {
                utilisateur.setMdp(newPassword);
                userDao.modify(utilisateur);
            } else {
                throw new BLLException("Utilisateur introuvable pour l'e-mail spécifié.");
            }
        } catch (Exception e) {
            throw new BLLException("Erreur lors de la mise à jour du mot de passe.", e);
        }
    }
}
