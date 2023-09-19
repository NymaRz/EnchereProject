package fr.eni.ecole.encheres.dal;

import java.util.List;

import fr.eni.ecole.encheres.bo.Adresse;

public interface AdresseDao {

	void save(Adresse adresse);

	Adresse findOne(int idAdresse); // j'ai rajouté un id à adresse

	List<Adresse> findAll();

	void modify(Adresse adresse);

	void remove(int idAdresse);

	List<Adresse> findByRue(String query);
	
	List<Adresse> findByVille(String query);

}
