package fr.eni.ecole.encheres.bll;

import java.util.List;

import fr.eni.ecole.encheres.bo.Adresse;
import fr.eni.ecole.encheres.dal.AdresseDao;
import fr.eni.ecole.encheres.dal.DaoFactory;

public class AdresseManager {

	// début Singleton
	private static AdresseManager instance;

	private AdresseManager() {
	}

	public static AdresseManager getInstance() {
		if (instance == null) {
			instance = new AdresseManager();
		}
		return instance;
	}
	// Fin Singleton

	// début logique métier
	
	private AdresseDao adresseDao = DaoFactory.getAdresseDao();
	
	public Adresse recupUneAdresse(int idAdresse) {
		return adresseDao.findOne(idAdresse);
	}


	public List<Adresse> recupToutesLesAdresses() {
		return adresseDao.findAll();
	}

    public void modifierUneAdresse(Adresse adresse) {
        // Ajoutez ici la logique de validation des données si nécessaire.
    	adresseDao.modify(adresse);
    }


	public void ajouterUneAdresse(Adresse adresse) {
		// Ajoutez ici la logique de validation des données si nécessaire.
		adresseDao.save(adresse);
	}


	public void supprimerUneAdresse(int idAdresse) {
		adresseDao.remove(idAdresse);
	}

	public List<Adresse> rechercheUneAdresseParRue(String query) {
		return adresseDao.findByRue(query);
	}
	public List<Adresse> rechercheUneAdresseParVille(String query) {
		return adresseDao.findByVille(query);
	}

	// Fin de la logique métier
	

}
