package fr.eni.ecole.encheres.bll;

import java.util.ArrayList;
import java.util.List;

import fr.eni.ecole.encheres.bo.ArticleVendu;
import fr.eni.ecole.encheres.bo.Categorie;
import fr.eni.ecole.encheres.dal.ArticleVenduDao;
import fr.eni.ecole.encheres.dal.DaoFactory;

public class ArticleVenduManager {
	// Début Singleton
	private static ArticleVenduManager instance;

	private ArticleVenduManager() {
		// Constructeur privé pour empêcher l'instanciation directe depuis l'extérieur.
	}

	public static ArticleVenduManager getInstance() {
		if (instance == null) {
			instance = new ArticleVenduManager();
		}
		return instance;
	}
	// Fin Singleton

	private ArticleVenduDao articleVenduDao = DaoFactory.getArticleVenduDao();

	// Début de la logique métier

	public ArticleVendu recupUnArticleVendu(int id) {
		return articleVenduDao.findOne(id);
	}

	public List<ArticleVendu> recupTousLesArticlesVendus() {
		return articleVenduDao.findAll();
	}

	public void modifierUnArticleVendu(ArticleVendu articleVendu) {
		// Ajoutez ici la logique de validation des données si nécessaire.
		articleVenduDao.modify(articleVendu);
	}

	public void ajouterUnArticleVendu(ArticleVendu articleVendu) {
		// Ajoutez ici la logique de validation des données si nécessaire.
		articleVenduDao.save(articleVendu);
	}

	public void supprimerUnArticleVendu(int id) {
		articleVenduDao.remove(id);
	}

	public List<ArticleVendu> rechercheUnArticleVendu(String query) {
		return articleVenduDao.findByName(query);
	}

	public List<ArticleVendu> findArticlesVendusByCategorie(Categorie categorie) {
		List<ArticleVendu> articlesVendus = ArticleVenduManager.getInstance().recupTousLesArticlesVendus();
		List<ArticleVendu> articlesVendusOfCategorie = new ArrayList<ArticleVendu>();
		for (ArticleVendu articleVendu : articlesVendus) {
			if (articleVendu.getCategorieArticle() == categorie)
				;
			articlesVendusOfCategorie.add(articleVendu);
		}
		return articlesVendusOfCategorie;
	}

	public List<ArticleVendu> rechercherUnArticleVenduByCategorie(Categorie categorie, String query) {
		List<ArticleVendu> articlesVendus = ArticleVenduManager.getInstance().rechercheUnArticleVendu(query);
		List<ArticleVendu> articlesVendusOfCategorie = new ArrayList<ArticleVendu>();
		for (ArticleVendu articleVendu : articlesVendus) {
			if (articleVendu.getCategorieArticle() == categorie)
				;
			articlesVendusOfCategorie.add(articleVendu);
		}
		return articlesVendusOfCategorie;
	}

	// Fin de la logique métier
}
