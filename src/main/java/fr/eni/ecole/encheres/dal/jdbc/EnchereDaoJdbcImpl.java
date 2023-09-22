package fr.eni.ecole.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.ecole.encheres.bo.Enchere;
import fr.eni.ecole.encheres.dal.DaoFactory;
import fr.eni.ecole.encheres.dal.EnchereDao;

public class EnchereDaoJdbcImpl implements EnchereDao {
	private static final String SAVE_ENCHERES = "INSERT INTO ENCHERES (no_utilisateur,no_article,date_enchere,montant_encheres) VALUES	(?,?,?,?)";
	private static final String FIND_ENCHERES = "SELECT * FROM ENCHERES WHERE id_enchere =?";
	private static final String SELECT_ALL_ENCHERES = "SELECT * FROM ENCHERES";
	private static final String UPDATE_ENCHERES = "UPDATE ENCHERES SET no_utilisateur?, no_article=?,date_enchere=montant_encheres=?,id_enchere=?,WHERE id_enchere=?";
	private static final String DELETE_ENCHERES = "DELETE FROM ENCHERES WHERE id_enchere=?";
	private static final String FIND_BY_ENCHERES = "SELECT * FROM ENCHERES WHERE id_enchere =?";
	private static final String FIND_HIGHEST_BID = "SELECT * FROM ENCHERES WHERE montant_enchere=(SELECT MAX(montant_enchere) FROM ENCHERES)";

	@Override
	public void save(Enchere enchere) {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(SAVE_ENCHERES)) {
			pstmt.setInt(1, enchere.getAcquereur().getNoUtilisateur());
			pstmt.setInt(2, enchere.getArticleEncheri().getnoArticle());
			pstmt.setDate(3, Date.valueOf(enchere.getDateEnchere()));
			pstmt.setInt(4, enchere.getMontant_enchere());
			pstmt.setInt(5, enchere.getId_enchere());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

// UtilisateurDaoJdbcImpl.FINDONE(rs.getint(noUtilisateur));
	@Override
	public Enchere findOne(int id_enchere) {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(FIND_ENCHERES)) {
			pstmt.setInt(1, id_enchere);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return new Enchere(DaoFactory.getUtilisateurDao().findOne(rs.getInt("no_utilisateur")),
						DaoFactory.getArticleVenduDao().findOne(rs.getInt("no_article")), rs.getInt("montant_enchere"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<Enchere> findAll() {
		try (Connection connection = ConnectionProvider.getConnection();
				Statement stmt = connection.createStatement();) {
			List<Enchere> encheres = new ArrayList<>();
			ResultSet rs = stmt.executeQuery(SELECT_ALL_ENCHERES);
			while (rs.next()) {
				Enchere enchere = new Enchere(rs.getInt("id_enchere"));

				encheres.add(enchere);
			}
			return encheres;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void modify(Enchere enchere) {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(UPDATE_ENCHERES)) {
			pstmt.setInt(1, enchere.getAcquereur().getNoUtilisateur());
			pstmt.setInt(2, enchere.getArticleEncheri().getnoArticle());
			pstmt.setDate(3, Date.valueOf(enchere.getDateEnchere()));
			pstmt.setInt(4, enchere.getMontant_enchere());
			pstmt.setInt(5, enchere.getId_enchere());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void remove(int id_enchere) {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(DELETE_ENCHERES)) {
			pstmt.setInt(1, id_enchere);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<Enchere> FindByEnchere(String query) {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(FIND_BY_ENCHERES)) {
			pstmt.setString(1, query);
			List<Enchere> encheres = new ArrayList<Enchere>();
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Enchere enchere = new Enchere(rs.getInt("id_enchere"));
				// Récupérer les données du ResultSet et les assigner à l'objet enchere
				encheres.add(enchere);
			}
			return encheres;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Enchere finHigherEnchere() {
		try (Connection connection = ConnectionProvider.getConnection();
				Statement stmt = connection.createStatement();) {

			ResultSet rs = stmt.executeQuery(FIND_HIGHEST_BID);
			if (rs.next()) {
				return new Enchere(DaoFactory.getUtilisateurDao().findOne(rs.getInt("no_utilisateur")),
						DaoFactory.getArticleVenduDao().findOne(rs.getInt("no_article")), rs.getInt("montant_enchere"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;

	}
}
