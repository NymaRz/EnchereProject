package fr.eni.ecole.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.ecole.encheres.bll.AdresseManager;
import fr.eni.ecole.encheres.bo.Adresse;
import fr.eni.ecole.encheres.bo.Retrait;
import fr.eni.ecole.encheres.dal.DaoFactory;
import fr.eni.ecole.encheres.dal.RetraitDao;

public class RetraitDaoJdbcImpl implements RetraitDao {

	private static final String SAVE_RETRAIT = "INSERT INTO RETRAITS (id_adresse) VALUES	(?)";
	private static final String FIND_RETRAIT_BY_ID = "SELECT * FROM RETRAITS WHERE id_retrait=?";
	private static final String SELECT_ALL_RETRAITS = "SELECT * FROM RETRAITS";
	private static final String UPDATE_RETRAIT = "UPDATE RETRAITS SET id_adresse=? WHERE id_retrait=?";
	private static final String DELETE_RETRAIT = "DELETE FROM RETRAITS WHERE id_retrait=?";
	private static final String FIND_BY_RETRAIT = "SELECT * FROM RETRAITS WHERE id_retrait=?";
	private static final String FIND_BY_ADRESSE = "SELECT * FROM RETRAITS WHERE id_adresse=?";

	@Override
	public void save(Retrait retrait) {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(SAVE_RETRAIT)) {
			pstmt.setInt(1, retrait.getAdresse().getIdAdresse());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public Retrait findOne(int idRetrait) {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(FIND_RETRAIT_BY_ID)) {
			pstmt.setInt(1, idRetrait);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return new Retrait(rs.getInt("id_retrait"),
						DaoFactory.getAdresseDao().findOne(rs.getInt("id_adresse")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<Retrait> findAll() {
		try (Connection connection = ConnectionProvider.getConnection();
				Statement stmt = connection.createStatement();) {
			List<Retrait> retraits = new ArrayList<>();
			ResultSet rs = stmt.executeQuery(SELECT_ALL_RETRAITS);
			while (rs.next()) {
				Retrait retrait = new Retrait(rs.getInt("id_retrait"),
						DaoFactory.getAdresseDao().findOne(rs.getInt("id_adresse")));
				// Récupérer les données du ResultSet et les assigner à l'objet adresse

				retraits.add(retrait);
			}
			return retraits;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void modify(Retrait retrait) {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(UPDATE_RETRAIT)) {
			pstmt.setInt(1, retrait.getAdresse().getIdAdresse());
			pstmt.setInt(2, retrait.getIdRetrait());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void remove(int idRetrait) {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(DELETE_RETRAIT)) {
			pstmt.setInt(1, idRetrait);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<Retrait> findByRetrait(String query) {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(FIND_BY_RETRAIT)) {
			pstmt.setString(1, query);
			List<Retrait> retraits = new ArrayList<Retrait>();
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Retrait retrait = new Retrait(rs.getInt("id_retrait"),
						DaoFactory.getAdresseDao().findOne(rs.getInt("id_adresse")));
				// Récupérer les données du ResultSet et les assigner à l'objet adresse

				retraits.add(retrait);
			}
			return retraits;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Retrait findRetraitByAdresse(Adresse adresse) {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(FIND_BY_ADRESSE)) {
			pstmt.setInt(1, adresse.getIdAdresse());

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return new Retrait(rs.getInt("id_retrait"),
						AdresseManager.getInstance().recupUneAdresse(rs.getInt("id_adresse")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
