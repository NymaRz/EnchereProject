package fr.eni.ecole.encheres.bll;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fr.eni.ecole.encheres.bo.ArticleVendu;
import fr.eni.ecole.encheres.bo.Utilisateur;
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

	public ArticleVendu recupUnArticleVendu(int noArticle) {
		return articleVenduDao.findOne(noArticle);
	}

	public ArticleVendu recupArticleVenduEnchereNC(int noArticle) {
		ArticleVendu articleVendu = articleVenduDao.findOne(noArticle);
		if (articleVendu.getDateDebutEncheres().isAfter(LocalDate.now()))
			return articleVendu;
		return null;

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
	public List<ArticleVendu> rechercherUnArticleVenduByCategorie(int noCategorie, String query) {
		return articleVenduDao.rechercheArticlesDeCategorieByName(noCategorie, query);
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

	// articles d'une catégorie dont les enchères sont ouvertes
	public List<ArticleVendu> recupArticlesCategorieEO(int noCategorie) {
		List<ArticleVendu> articlesVendus = ArticleVenduManager.getInstance()
				.findArticlesVendusByCategorie(noCategorie);
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
	public List<ArticleVendu> recupUnArticleVenduByCategorieEO(int noCategorie, String query) {
		List<ArticleVendu> articlesVendus = ArticleVenduManager.getInstance()
				.rechercherUnArticleVenduByCategorie(noCategorie, query);
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

	public List<ArticleVendu> recupArticlesEncheresParUtilisateur(Utilisateur utilisateur) {

		return articleVenduDao.recupArticlesEncheresParUtilisateur(utilisateur);
	}

	public List<ArticleVendu> recupArticlesVendusParUtilisateurSelonEtatVente(Utilisateur utilisateur,
			String etatVente) {

		return articleVenduDao.recupArticlesVendusParUtilisateurSelonEtatVente(utilisateur, etatVente);
	}

	public void updateAllArticles() {
		List<ArticleVendu> articles = ArticleVenduManager.getInstance().recupTousLesArticlesVendus();
		for (ArticleVendu articleVendu : articles) {
			if (articleVendu.getDateDebutEncheres().isAfter(LocalDate.now())
					&& !articleVendu.getEtatVente().equalsIgnoreCase("av")) {
				articleVendu.setEtatVente("av");
				ArticleVenduManager.getInstance().modifierUnArticleVendu(articleVendu);
			}
			if ((articleVendu.getDateDebutEncheres().isBefore(LocalDate.now())
					|| articleVendu.getDateDebutEncheres().isEqual(LocalDate.now()))
					&& articleVendu.getDateFinEncheres().isAfter(LocalDate.now())
					&& !articleVendu.getEtatVente().equalsIgnoreCase("v")) {
				articleVendu.setEtatVente("v");
				ArticleVenduManager.getInstance().modifierUnArticleVendu(articleVendu);
			}
			if (articleVendu.getDateFinEncheres().isBefore(LocalDate.now())
					&& !articleVendu.getEtatVente().equalsIgnoreCase("vf")) {
				articleVendu.setEtatVente("vf");
				ArticleVenduManager.getInstance().modifierUnArticleVendu(articleVendu);
			}
		}
	}

	// Fin de la logique métier
}
