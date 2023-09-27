package fr.eni.ecole.encheres.dal;

import java.util.List;

import fr.eni.ecole.encheres.bo.ArticleVendu;
import fr.eni.ecole.encheres.bo.Utilisateur;

public interface ArticleVenduDao {
	// CRUD
	void save(ArticleVendu articlevendu);

	ArticleVendu findOne(int noArticle);

	List<ArticleVendu> findAll();

	void modify(ArticleVendu articlevendu);

	void remove(int noArticle);

	List<ArticleVendu> findByName(String query);

	List<ArticleVendu> recupTousLEsArticlesDeCategorie(int noCategorie);

	List<ArticleVendu> rechercheArticlesDeCategorieByName(int noCategorie, String query);



	List<ArticleVendu> recupTousLesArticlesSelonEtatVente(String etatVente);

	// retrouver les articles sur lequel l'utilisateur a enchéri
	List<ArticleVendu> recupArticlesEncheresParUtilisateur(Utilisateur utilisateur);

	List<ArticleVendu> recupArticlesEncherisParUtilisateurEtCategorie(Utilisateur utilisateur, int noCategorie);

	List<ArticleVendu> recupUnArticleEncheriParutilisateurEtCategorie(Utilisateur utilisateur, int noCategorie,
			String q);

	List<ArticleVendu> recupUnArticleEncheriParUtilisateur(Utilisateur utilisateur, String q);

	// fin retrouver les articles sur lequel l'utilisateur a enchéri
//
	// retrouver les ventes en cours de l'utilisateur
	List<ArticleVendu> recupArticlesVendusParUtilisateurSelonEtatVente(Utilisateur utilisateur, String etatVente);
	List<ArticleVendu> recupUnArticlesVendusParUtilisateurSelonEtatVenteEtCategorie(Utilisateur utilisateur,
			int noCategorie, String q, String etatVente);

	List<ArticleVendu> recupArticlesVendusParUtilisateurSelonEtatVenteEtCategorie(Utilisateur utilisateur,
			int noCategorie, String etatVente);

	List<ArticleVendu> recupUnArticlesVendusParUtilisateurSelonEtatVente(Utilisateur utilisateur, String q, String etatVente);
	// fin retrouver les ventes en cours de l'utilisateur

	void modifyGagnant(ArticleVendu articleVendu);
	
	ArticleVendu recupParEnchereLaPlusHaute(ArticleVendu articleVendu);
}
