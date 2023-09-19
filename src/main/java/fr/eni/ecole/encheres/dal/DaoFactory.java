package fr.eni.ecole.encheres.dal;

import fr.eni.ecole.encheres.dal.jdbc.AdresseDaoJdbcImpl;
import fr.eni.ecole.encheres.dal.jdbc.ArticleVenduJdbcImpl;
import fr.eni.ecole.encheres.dal.jdbc.CategorieDaoJdbcImpl;
import fr.eni.ecole.encheres.dal.jdbc.RetraitDaoJdbcImpl;
import fr.eni.ecole.encheres.dal.jdbc.UtilisateurDaoJdbcImpl;

public class DaoFactory {

	private DaoFactory() {}
	
	public static ArticleVenduDao getArticleVenduDao() {
		//return new GameMockDaoImpl();
		return new ArticleVenduJdbcImpl();
	} 
	
	public static AdresseDao getAdresseDao() {
		return new AdresseDaoJdbcImpl();
	}
	
	public static UtilisateurDao getUtilisateurDao() {
		return new UtilisateurDaoJdbcImpl();
	}
	
	public static RetraitDao getRetraitDao() {
		return new RetraitDaoJdbcImpl();
	}
	
	public static CategorieDAO getCategorieDao() {
		return new CategorieDaoJdbcImpl();
	}
	
//	public static UserDao getUserDao() {
//		return new UserDaoJdbcImpl();
//	}
//	
//	public static ForgetPasswordDao getForgetPasswordDao() {
//		return new ForgetPasswordDaoJdbcImpl();
//	}
	
}
