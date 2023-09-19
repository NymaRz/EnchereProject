package fr.eni.ecole.encheres.dal;

import java.util.List;

import fr.eni.ecole.encheres.bo.Utilisateur;

public interface UtilisateurDao {
	void save(Utilisateur utilisateur);

	Utilisateur findOne(int idUtilisateur); // j'ai rajouté un id à utilisateur

	List<Utilisateur> findAll();

	void modify(Utilisateur utilisateur);

	void remove(int idUtilisateur);

	List<Utilisateur> findByPseudo(String query);
	
	
}
