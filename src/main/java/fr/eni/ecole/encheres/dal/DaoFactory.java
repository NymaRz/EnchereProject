package fr.eni.ecole.encheres.dal;

import fr.eni.ecole.encheres.dal.jdbc.*;

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
	
	public static EnchereDao getEnchereDao() {
		return new EnchereDaoJdbcImpl();
	}
	
	
//	public static UserDao getUserDao() {
//		return new UserDaoJdbcImpl();
//	}
//	
//	public static ForgetPasswordDao getForgetPasswordDao() {
//		return new ForgetPasswordDaoJdbcImpl();
//	}
	
}
