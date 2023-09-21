package fr.eni.ecole.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.ecole.encheres.bo.Adresse;
import fr.eni.ecole.encheres.dal.AdresseDao;

public class AdresseDaoJdbcImpl implements AdresseDao {

	private static final String SAVE_ADRESSE = "INSERT INTO ADRESSES (rue,code_postal,ville) VALUES	(?,?,?)";
	private static final String FIND_ADRESSE_BY_ID = "SELECT * FROM ADRESSES WHERE id_adresse=?";
	private static final String SELECT_ALL_ADRESSES = "SELECT * FROM ADRESSES";
	private static final String UPDATE_ADRESSE = "UPDATE ADRESSES SET rue=?, code_postal=?,ville=? WHERE id_adresse=?";
	private static final String DELETE_ADRESSE = "DELETE FROM ADRESSES WHERE id_adresse=?";
	private static final String FIND_BY_RUE_ADRESSE = "SELECT * FROM ADRESSES WHERE rue LIKE ?";
	private static final String FIND_BY_VILLE_ADRESSE = "SELECT * FROM ADRESSES WHERE ville LIKE ?";
	private static final String FIND_BY_RUE_CP_VILLE_ADRESSE = "SELECT * FROM ADRESSES WHERE rue=? AND code_postal=? AND ville=?";

	@Override
	public void save(Adresse adresse) {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(SAVE_ADRESSE)) {
			pstmt.setString(1, adresse.getRue());
			pstmt.setString(2, adresse.getCodePostal());
			pstmt.setString(3, adresse.getVille());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public Adresse findOne(int idAdresse) {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(FIND_ADRESSE_BY_ID)) {
			pstmt.setInt(1, idAdresse);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return new Adresse(rs.getInt("id_adresse"), rs.getString("rue"), rs.getString("code_postal"),
						rs.getString("ville"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<Adresse> findAll() {
		try (Connection connection = ConnectionProvider.getConnection();
				Statement stmt = connection.createStatement();) {
			List<Adresse> adresses = new ArrayList<Adresse>();
			ResultSet rs = stmt.executeQuery(SELECT_ALL_ADRESSES);
			while (rs.next()) {
				Adresse adresse = new Adresse(rs.getInt("id_adresse"), rs.getString("rue"), rs.getString("code_postal"),
						rs.getString("ville"));
				// Récupérer les données du ResultSet et les assigner à l'objet adresse

				adresses.add(adresse);
			}
			return adresses;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void modify(Adresse adresse) {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(UPDATE_ADRESSE)) {
			pstmt.setString(1, adresse.getRue());
			pstmt.setString(2, adresse.getCodePostal());
			pstmt.setString(3, adresse.getVille());
			pstmt.setInt(4, adresse.getIdAdresse());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void remove(int idAdresse) {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(DELETE_ADRESSE)) {
			pstmt.setInt(1, idAdresse);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<Adresse> findByRue(String query) {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(FIND_BY_RUE_ADRESSE)) {
			pstmt.setString(1, query);
			List<Adresse> adressesByRue = new ArrayList<Adresse>();
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Adresse adresse = new Adresse(rs.getInt("id_adresse"), rs.getString("rue"), rs.getString("code_postal"),
						rs.getString("ville"));
				// Récupérer les données du ResultSet et les assigner à l'objet adresse

				adressesByRue.add(adresse);
			}
			return adressesByRue;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Adresse> findByVille(String query) {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(FIND_BY_VILLE_ADRESSE)) {
			pstmt.setString(1, query);
			List<Adresse> adressesByRue = new ArrayList<Adresse>();
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Adresse adresse = new Adresse(rs.getInt("id_adresse"), rs.getString("rue"), rs.getString("code_postal"),
						rs.getString("ville"));
				// Récupérer les données du ResultSet et les assigner à l'objet adresse

				adressesByRue.add(adresse);
			}
			return adressesByRue;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Adresse findByRueCPVille(String rue, String codePostal, String ville) {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(FIND_BY_RUE_CP_VILLE_ADRESSE)) {
			pstmt.setString(1, rue);
			pstmt.setString(2, codePostal);
			pstmt.setString(3, ville);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return new Adresse(rs.getInt("id_adresse"), rs.getString("rue"), rs.getString("code_postal"),
						rs.getString("ville"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
