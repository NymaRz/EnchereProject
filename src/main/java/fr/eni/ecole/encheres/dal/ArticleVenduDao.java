package fr.eni.ecole.encheres.dal;

import java.util.List;

import fr.eni.ecole.encheres.bo.ArticleVendu;

public interface ArticleVenduDao {
	// CRUD
	void save(ArticleVendu articlevendu);

	ArticleVendu findOne(int noArticle);

	List<ArticleVendu> findAll();

	void modify(ArticleVendu articlevendu);

	void remove(int noArticle);

	List<ArticleVendu> findByName(String query);
}
