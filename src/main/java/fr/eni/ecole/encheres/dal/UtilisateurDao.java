package fr.eni.ecole.encheres.dal;

import java.util.List;

import fr.eni.ecole.encheres.bo.Utilisateur;
import fr.eni.ecole.encheres.ihm.exception.EmailExisteDejaException;
import fr.eni.ecole.encheres.ihm.exception.PseudoExisteDejaException;

public interface UtilisateurDao {
	void save(Utilisateur utilisateur) throws EmailExisteDejaException, PseudoExisteDejaException;

	Utilisateur findOne(int idUtilisateur); // j'ai rajouté un id à utilisateur

	List<Utilisateur> findAll();

	void modify(Utilisateur utilisateur);

	void modifynopic(Utilisateur utilisateur);

	void remove(int idUtilisateur);

	Utilisateur findByPseudo(String query);

	Utilisateur findByEmail(String email);

}
