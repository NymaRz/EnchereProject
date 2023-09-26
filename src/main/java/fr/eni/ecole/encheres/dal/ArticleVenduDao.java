package fr.eni.ecole.encheres.dal;

import java.util.List;

import fr.eni.ecole.encheres.bo.ArticleVendu;
import fr.eni.ecole.encheres.bo.Utilisateur;

public interface ArticleVenduDao{
	// CRUD
	void save(ArticleVendu articlevendu);

	ArticleVendu findOne(int noArticle);

	List<ArticleVendu> findAll();

	void modify(ArticleVendu articlevendu);

	void remove(int noArticle);

	List<ArticleVendu> findByName(String query);
	
	List<ArticleVendu> recupTousLEsArticlesDeCategorie(int noCategorie);
	List<ArticleVendu> rechercheArticlesDeCategorieByName(int noCategorie,String query);
	List<ArticleVendu> recupArticlesEncheresParUtilisateur(Utilisateur utilisateur);
	List<ArticleVendu> recupArticlesRemporteesParUtilisateur(Utilisateur utilisateur);
}
