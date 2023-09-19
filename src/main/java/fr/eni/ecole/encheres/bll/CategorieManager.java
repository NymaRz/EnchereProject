package fr.eni.ecole.encheres.bll;

import java.util.List;

import fr.eni.ecole.encheres.bo.Categorie;
import fr.eni.ecole.encheres.dal.CategorieDAO;
import fr.eni.ecole.encheres.dal.DaoFactory;

public class CategorieManager {
	// singleton
	private static CategorieManager instance;

	private CategorieManager() {
	}

	public static CategorieManager getInstance() {
		if (instance == null)
			instance = new CategorieManager();
		return instance;
	}

	// fin singleton
	private CategorieDAO categorieDao = DaoFactory.getCategorieDao();

	// début logique métier

	public Categorie recupUneCategorie(int no_Categorie) {
		return categorieDao.findOne(no_Categorie);
	}

	public List<Categorie> recupTouteCategories() {
		return categorieDao.findAll();
	}

	public void modifierUneCategorie(Categorie categorie) {
		// Ajoutez ici la logique de validation des données si nécessaire.
		categorieDao.modify(categorie);
	}

	public void ajouterUneCategorie(Categorie categorie) {
		// Ajoutez ici la logique de validation des données si nécessaire.
		categorieDao.save(categorie);
	}

	public void supprimerUneCategorie(int no_Categorie) {
		categorieDao.remove(no_Categorie);
	}

	public List<Categorie> rechercheUneCategorie(String query) {
		return categorieDao.FindByCategorie(query);
	}

}
