package fr.eni.ecole.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.eni.ecole.encheres.bo.ForgetPassword;
import fr.eni.ecole.encheres.bo.Utilisateur;
import fr.eni.ecole.encheres.dal.ForgetPasswordDao;

public class ForgetPasswordDaoJdbcImpl implements ForgetPasswordDao {

	private static final String INSERT_FORGET_PASSWORD = "INSERT INTO forget_passwords (code, no_utilisateur) VALUES (?, ?)";
	private static final String SELECT_FOR_RESET_PASSWORD = "SELECT TOP 1 * FROM UTILISATEURS u "
			+ "INNER JOIN forget_passwords fp ON u.no_utilisateur = fp.no_utilisateur " + "WHERE u.email = ? "
			+ "ORDER BY fp.date_created DESC";

	@Override
	public void save(ForgetPassword fp) {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(INSERT_FORGET_PASSWORD);) {
			pstmt.setString(1, fp.getCode());
			pstmt.setInt(2, fp.getUser().getNoUtilisateur());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ForgetPassword resetPassword(String email) {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(SELECT_FOR_RESET_PASSWORD);) {
			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				Utilisateur utilisateur = new Utilisateur(rs.getInt("no_utilisateur"), rs.getString("mot_de_passe"));

				return new ForgetPassword(rs.getString("code"), utilisateur);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
