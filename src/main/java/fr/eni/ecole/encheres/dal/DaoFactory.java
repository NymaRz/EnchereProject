package fr.eni.ecole.encheres.dal;

import fr.eni.ecole.dal.jbdc.ArticleVenduJdbcImpl;

public class DaoFactory {

	private DaoFactory() {}
	
	public static ArticleVenduJdbcImpl getArticleVendu() {
		//return new GameMockDaoImpl();
		return new ArticleVenduJdbcImpl();
	} 
	
//	public static UserDao getUserDao() {
//		return new UserDaoJdbcImpl();
//	}
//	
//	public static ForgetPasswordDao getForgetPasswordDao() {
//		return new ForgetPasswordDaoJdbcImpl();
//	}
	
}
