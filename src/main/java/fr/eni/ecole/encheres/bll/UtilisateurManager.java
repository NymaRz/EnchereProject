package fr.eni.ecole.encheres.bll;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import fr.eni.ecole.encheres.bll.exception.BLLException;
import fr.eni.ecole.encheres.bo.ForgetPassword;
import fr.eni.ecole.encheres.bo.Utilisateur;
import fr.eni.ecole.encheres.dal.DaoFactory;
import fr.eni.ecole.encheres.dal.UtilisateurDao;
import fr.eni.ecole.encheres.dal.jdbc.ConnectionProvider;
import fr.eni.ecole.encheres.dal.jdbc.exception.JDBCException;

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

	public void ajouterUnUtilisateur(Utilisateur utilisateur) {
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

	public void inscription(Utilisateur utilisateur) throws JDBCException, BLLException {
		// validation !!!!!!!!
		checkFields(utilisateur);
		userDao.save(utilisateur);
	}

	private void checkFields(Utilisateur utilisateur) throws BLLException {
		if (utilisateur == null)
			throw new BLLException("User est null");

		if (utilisateur.getPrenom().isBlank())
			throw new BLLException("Le champs Pseudo est obligatoire!");
		if (utilisateur.getEmail().isBlank())
			throw new BLLException("Le champs Email est obligatoire!");
		// verifier la syntaxe de l'email
		if (utilisateur.getMdp().isBlank())
			throw new BLLException("Le champs Mot de passe est obligatoire!");
		if (utilisateur.getMdp().length() < 8 || utilisateur.getMdp().length() > 35)
			throw new BLLException("La taille du mot de passe doit etre entre 8 et 35");
		// if(!utilisateur.getPassword().equals(utilisateur.getConfirmpassword))
	}

	public Utilisateur login(String email, String mdp) {
		Utilisateur utilisateur = (Utilisateur) utilisateurDao.findByPseudo(email);
		if (utilisateur != null && utilisateur.getEmail().equals(email) && utilisateur.getMdp().equals(mdp)) {
			return utilisateur;
		}
		return null;
	}

	public ForgetPassword checkEmail(String email) throws BLLException {

		Utilisateur utilisateur = userDao.findByEmail(email);
		if (utilisateur == null)
			throw new BLLException("Erreur: l'email n'existe pas");
		// Générer le code
		String code = rd.nextLong(1, 9999999999L) + "";
		ForgetPassword fp = new ForgetPassword(code, utilisateur);

		// save
		DaoFactory.getForgetPasswordDao().save(fp);
		// envoi mail

		// par sms API
		System.out.println(code);

		return fp;
	}

	public void resetPassword(String email, String code, String newPassword) throws BLLException {

		ForgetPassword fp = DaoFactory.getForgetPasswordDao().resetPassword(email);

		if (!fp.getCode().equals(code))
			throw new BLLException("Le code est érroné!");

		Utilisateur utilisateur = fp.getUser();

		utilisateur.setMdp(newPassword);

		userDao.modify(utilisateur);

	}
//
//	public Utilisateur findByEmail(String email) {
//		return userDao.findByEmail(email);
//	}

	public Utilisateur findByEmail(String email) throws BLLException {
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Utilisateur utilisateur = null;

	    try(Connection connection =ConnectionProvider.getConnection();
	    		) {
	         // Obtenez votre connexion à la base de données ici.

	        // Écrivez la requête SQL pour rechercher un utilisateur par e-mail
	        String sql = "SELECT * FROM utilisateurs WHERE email = ?";
	        preparedStatement = connection.prepareStatement(sql);
	        preparedStatement.setString(1, email);

	        resultSet = preparedStatement.executeQuery();

	        // Si un utilisateur correspondant est trouvé, créez un objet Utilisateur et retournez-le
	        if (resultSet.next()) {
	            utilisateur = new Utilisateur();
				utilisateur.setNoUtilisateur(0);
	            utilisateur.setPseudo(resultSet.getString("pseudo"));
	            // Continuez à extraire les autres attributs de l'utilisateur ici
	        }
	    } catch (SQLException e) {
	        throw new BLLException("Erreur lors de la recherche de l'utilisateur par e-mail");
	    } finally {
	        // Fermez les ressources (ResultSet, PreparedStatement, Connection) ici dans un bloc finally.
	        // Assurez-vous de gérer les exceptions appropriées lors de la fermeture.
	    }

	    return utilisateur; // Retournez l'utilisateur trouvé ou null s'il n'existe pas.
	}


	public boolean pseudoExisteDeja(String pseudo) {
		return false;
	}

}
