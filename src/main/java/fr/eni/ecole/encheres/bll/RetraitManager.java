package fr.eni.ecole.encheres.bll;
import java.util.List;

import fr.eni.ecole.encheres.bo.ArticleVendu;
import fr.eni.ecole.encheres.bo.Retrait;
import fr.eni.ecole.encheres.dal.DaoFactory;
import fr.eni.ecole.encheres.dal.RetraitDao;


public class RetraitManager {

	// Début Singleton
	private static RetraitManager instance;

	private RetraitManager() {
		// Constructeur privé pour empêcher l'instanciation directe depuis l'extérieur.
	}

	public static RetraitManager getInstance() {
		if (instance == null) {
			instance = new RetraitManager();
		}
		return instance;
	}
	// Fin Singleton

	private RetraitDao retraitDao = DaoFactory.getRetraitDao();

	// Début de la logique métier

	public Retrait recupUnRetrait(int id) {
		return retraitDao.findOne(id);
	}


	public List<Retrait> recupTousLesRetraits() {
		return retraitDao.findAll();
	}

    public void modifierUnRetrait(Retrait retrait) {
        // Ajoutez ici la logique de validation des données si nécessaire.
        retraitDao.modify(retrait);
    }


	public void ajouterUnRetrait(Retrait retrait) {
		// Ajoutez ici la logique de validation des données si nécessaire.
		retraitDao.save(retrait);
	}


	public void supprimerUnRetrait(int id) {
		retraitDao.remove(id);
	}

	public List<Retrait> rechercheUnRetrait(String query) {
		return retraitDao.findByRetrait(query);
	}

	// Fin de la logique métier
}
