package fr.eni.ecole.encheres.bll;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
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

//tri par catégorie
	public List<ArticleVendu> findArticlesVendusByCategorie(int noCategorie) {
		return articleVenduDao.recupTousLEsArticlesDeCategorie(noCategorie);
	}

//tri par catégorie + query
	public List<ArticleVendu> rechercherUnArticleVenduByCategorie(Categorie categorie, String query) {
		List<ArticleVendu> articlesVendusOfCategorie = CategorieManager.getInstance().recupUneCategorie(categorie.getNoCategorie()).getArticlesOfCategorie();
		List<ArticleVendu> articlesVendusOfQuery = ArticleVenduManager.getInstance().rechercheUnArticleVendu(query);
		List<ArticleVendu> articlesOfCategorieAndQuery = new ArrayList<ArticleVendu>();
		for (ArticleVendu articleVenduOfCategorie : articlesVendusOfCategorie) {
			for (ArticleVendu articleVenduOfQuery : articlesVendusOfQuery) {
				if (articleVenduOfCategorie.equals(articleVenduOfQuery)) {
					articlesOfCategorieAndQuery.add(articleVenduOfCategorie);
				}
			}
		}
		return articlesOfCategorieAndQuery;
	}

	// seulement les articles dont la date d'ouverture aux enchères est ouverte, et
	// la date de fin d'enchère n'est pas terminée

	public List<ArticleVendu> recupArticlesVendusEncheresOuvertes() {
		List<ArticleVendu> articlesVendus = ArticleVenduManager.getInstance().recupTousLesArticlesVendus();
		List<ArticleVendu> articlesVendusOuvertsAuxEncheres = new ArrayList<ArticleVendu>();
		LocalDate dateNow = LocalDate.now();
		for (ArticleVendu articleVendu : articlesVendus) {
			if (articleVendu.getDateDebutEncheres().isBefore(dateNow)
					|| articleVendu.getDateDebutEncheres().isEqual(dateNow)
					|| articleVendu.getDateFinEncheres().isAfter(dateNow))
				articlesVendusOuvertsAuxEncheres.add(articleVendu);
		}
		return articlesVendusOuvertsAuxEncheres;
	}

	// idem mais avec query
	public List<ArticleVendu> recupUnArticleVendyEncheresOuvertes(String query) {
		List<ArticleVendu> articlesVendus = ArticleVenduManager.getInstance().rechercheUnArticleVendu(query);
		List<ArticleVendu> articlesVendusOuvertsAuxEncheres = new ArrayList<ArticleVendu>();
		LocalDate dateNow = LocalDate.now();
		for (ArticleVendu articleVendu : articlesVendus) {
			if (articleVendu.getDateDebutEncheres().isBefore(dateNow)
					|| articleVendu.getDateDebutEncheres().isEqual(dateNow)
					|| articleVendu.getDateFinEncheres().isAfter(dateNow))
				articlesVendusOuvertsAuxEncheres.add(articleVendu);
		}
		return articlesVendusOuvertsAuxEncheres;
	}

	// Fin de la logique métier
}
