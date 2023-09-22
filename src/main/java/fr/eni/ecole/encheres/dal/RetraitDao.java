
package fr.eni.ecole.encheres.dal;

import java.util.List;

import fr.eni.ecole.encheres.bo.Adresse;
import fr.eni.ecole.encheres.bo.Retrait;

public interface RetraitDao {

	void save(Retrait retrait);

	Retrait findOne(int idRetrait);

	List<Retrait> findAll();

	void modify(Retrait retrait);

	void remove(int idRetrait);

	List<Retrait> findByRetrait(String query);
	
	Retrait findRetraitByAdresse(Adresse adresse);

}