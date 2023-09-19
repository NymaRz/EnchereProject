package fr.eni.ecole.encheres.dal;

import java.util.List;

import fr.eni.ecole.encheres.bo.Enchere;

public interface EnchereDao {

	void save(Enchere enchere);

	Enchere findOne(int id_enchere);

	List<Enchere> findAll();

	void modify(Enchere enchere);

	void remove(int id_enchere);

	List<Enchere> FindByEnchere(String query);

}
