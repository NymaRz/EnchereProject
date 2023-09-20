package fr.eni.ecole.encheres.bll;

import java.util.List;

import fr.eni.ecole.encheres.bo.Utilisateur;
import fr.eni.ecole.encheres.dal.DaoFactory;
import fr.eni.ecole.encheres.dal.UtilisateurDao;

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

	public List<Utilisateur> rechercheUnUtilisateur(String query) {
		return utilisateurDao.findByPseudo(query);
	}

//	private UtilisateurDao userDao = DaoFactory.getUtilisateurDao();
//	private Random rd = new Random();
//	
//	public void inscription(Utilisateur user) throws JDBCException, BLLException {
//		// validation !!!!!!!!
//		checkFields(user);
//		userDao.save(user);		
//	}
//	
//	
//	private void checkFields(Utilisateur user) throws BLLException {
//		if( user == null ) throw new BLLException("User est null");
//		
//		if( user.getUsername().isBlank() ) throw new BLLException("Le champs Username est obligatoire!");
//		if( user.getEmail().isBlank() ) throw new BLLException("Le champs Email est obligatoire!");
//		// verifier la syntaxe de l'email
//		if( user.getPassword().isBlank() ) throw new BLLException("Le champs Mot de passe est obligatoire!");
//		if( user.getPassword().length() < 8 ||  user.getPassword().length() > 35 )throw new BLLException("La taille du mot de passe doit etre entre 8 et 35");
//		//if(!user.getPassword().equals(user.getConfirmpassword))
//	}
//
//	public Utilisateur login(String username,String password) {
//		Utilisateur user = userDao.findByUsername(username);		
//		if(user!=null 
//				&& user.getUsername().equals(username)
//				&& user.getPassword().equals(password) ) {
//			return user;
//		}		
//		return null;
//	}
//
//	public ForgetPassword checkEmail(String email) throws BLLException {
//		
//		Utilisateur user = userDao.findByEmail(email);
//		if(user == null) throw new BLLException("Erreur: l'email n'existe pas");			
//		// Générer le code
//		String code = rd.nextLong(1,9999999999L)+"";
//		ForgetPassword fp = new ForgetPassword(code, user);
//
//		// save
//		DaoFactory.getForgetPasswordDao()
//				  .save(fp);
//		// envoi mail
//		
//		// par sms API 
//		System.out.println(code);
//
//		return fp;
//	}
//
//	public void resetPassword(String email, String code, String newPassword) throws BLLException {
//		
//		ForgetPassword fp = DaoFactory.getForgetPasswordDao().resetPassword(email);
//		
//		if(!fp.getCode().equals(code)) throw new BLLException("Le code est érroné!");
//		
//		Utilisateur user = fp.getUser();
//		
//		user.setPassword(newPassword);
//		
//		userDao.modify(user);
//		
//	}

}
