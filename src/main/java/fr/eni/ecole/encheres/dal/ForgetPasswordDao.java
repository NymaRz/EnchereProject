package fr.eni.ecole.encheres.dal;

import fr.eni.ecole.encheres.bo.ForgetPassword;

public interface ForgetPasswordDao extends DAO<ForgetPassword> {
	ForgetPassword resetPassword(String email);
}
