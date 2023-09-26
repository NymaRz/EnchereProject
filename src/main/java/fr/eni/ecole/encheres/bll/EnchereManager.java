package fr.eni.ecole.encheres.bll;

import java.util.List;

import fr.eni.ecole.encheres.bo.ArticleVendu;
import fr.eni.ecole.encheres.bo.Enchere;
import fr.eni.ecole.encheres.dal.DaoFactory;
import fr.eni.ecole.encheres.dal.EnchereDao;

public class EnchereManager {

	// Début Singleton
	private static EnchereManager instance;

	private EnchereManager() {
		// Constructeur privé pour empêcher l'instanciation directe depuis l'extérieur.
	}

	public static EnchereManager getInstance() {
		if (instance == null) {
			instance = new EnchereManager();
		}
		return instance;
	}
	// Fin Singleton

	private EnchereDao enchereDao = DaoFactory.getEnchereDao();

	// Début de la logique métier

	public Enchere recupUneEnchere(int id) {
		return enchereDao.findOne(id);
	}

	public List<Enchere> recupToutesLesEncheres() {
		return enchereDao.findAll();
	}

	public void modifierUneEnchere(Enchere enchere) {
		// Ajoutez ici la logique de validation des données si nécessaire.
		enchereDao.modify(enchere);
	}

	public void ajouterUneEnchere(Enchere enchere) {
		// Ajoutez ici la logique de validation des données si nécessaire.
		enchereDao.save(enchere);
	}

	public void supprimerUneEnchere(int id) {
		enchereDao.remove(id);
	}

	public List<Enchere> rechercheUneEnchere(String query) {
		return enchereDao.FindByEnchere(query);
	}

	public Enchere recupEnchereLaPlusHaute(ArticleVendu articleVendu) {
		return enchereDao.finHigherEnchere(articleVendu);
	}
	
	public Enchere recupLeGagnantEnchere(ArticleVendu articleVendu) {
		return enchereDao.findEnchereWinner(articleVendu);
	}
	// Fin de la logique métier
}
