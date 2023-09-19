package fr.eni.ecole.encheres.bll;

import java.util.List;

import fr.eni.ecole.encheres.bo.ArticleVendu;
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

	public ArticleVendu recupUnJeu(int id) {
		return articleVenduDao.findOne(id);
	}

<<<<<<< HEAD
	public List<ArticleVendu> recupTousLesJeux() {
		return articleVenduDao.findAll();
	}
=======
<<<<<<< HEAD
	public List<ArticleVendu> recupTousLesJeux() {
		return articleVenduDao.findAll();
	}
=======
>>>>>>> 760dd2c18af59be04e4afcf7ac9e462273dc1606
    public void modifierUnJeu(ArticleVendu articleVendu) {
        // Ajoutez ici la logique de validation des données si nécessaire.
        articleVenduDao.modify(articleVendu);
    }
>>>>>>> a16528686e5f272c3cca71edfde83808b0427bca

	public void ajouterUnJeu(ArticleVendu articleVendu) {
		// Ajoutez ici la logique de validation des données si nécessaire.
		articleVenduDao.save(articleVendu);
	}

	public void modifierUnJeu(ArticleVendu articleVendu) {
		// Ajoutez ici la logique de validation des données si nécessaire.
		articleVenduDao.modify(articleVendu);
	}

	public void supprimerUnJeu(int id) {
		articleVenduDao.remove(id);
	}

	public List<ArticleVendu> rechercheUnJeu(String query) {
		return articleVenduDao.findByName(query);
	}

	// Fin de la logique métier
}
