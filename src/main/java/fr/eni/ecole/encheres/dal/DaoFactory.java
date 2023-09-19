package fr.eni.ecole.encheres.dal;

import fr.eni.ecole.encheres.dal.jdbc.ArticleVenduJdbcImpl;

public class DaoFactory {

	private DaoFactory() {}
	
	public static ArticleVenduDao getArticleVenduDao() {
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
