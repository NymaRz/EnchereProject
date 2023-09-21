package fr.eni.ecole.encheres.dal;

import java.util.List;

import fr.eni.ecole.encheres.bo.Categorie;

public interface CategorieDAO {
	void save(Categorie categorie);

	Categorie findOne(int no_Categorie);

	List<Categorie> findAll();

	void modify(Categorie categorie);

	void remove(int no_Categorie);

	List<Categorie> FindByCategorie(String query);
	
	

}
