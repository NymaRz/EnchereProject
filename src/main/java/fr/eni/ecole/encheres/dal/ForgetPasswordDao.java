package fr.eni.ecole.encheres.dal;

import fr.eni.ecole.encheres.bo.ForgetPassword;

public interface ForgetPasswordDao extends DAO<ForgetPassword> {
	// Réinitialise le mot de passe associé à l'email spécifié
	ForgetPassword resetPassword(String email);

	// Supprime une demande de réinitialisation de mot de passe par son identifiant
	void remove(int id);

	// Recherche une demande de réinitialisation de mot de passe par son email
	ForgetPassword findByEmail(String email);
}
