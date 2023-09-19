package fr.eni.ecole.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.ecole.encheres.bo.Categorie;
import fr.eni.ecole.encheres.dal.CategorieDAO;

public class CategorieDaoJdbcImpl implements CategorieDAO {
	private static final String SAVE_CATEGORIES = "INSERT INTO CATEGORIES (no_categorie,libelle) VALUES	(?,?)";
	private static final String FIND_CATEGORIES_BY_ID = "SELECT * FROM CATEGORIES WHERE no_categorie=?";
	private static final String SELECT_ALL_CATEGORIES = "SELECT * FROM CATEGORIES";
	private static final String UPDATE_CATEGORIES = "UPDATE CATEGORIES SET no_categorie?, libelle=?,WHERE no_categorie=?";
	private static final String DELETE_CATEGORIES = "DELETE FROM CATEGORIES WHERE no_categorie=?";
	private static final String FIND_BY_CATEGORIES = "SELECT * FROM CATEGORIES WHERE libelle LIKE ?";

	@Override
	public void save(Categorie categorie) {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(SAVE_CATEGORIES)) {
			pstmt.setInt(1, categorie.getNoCategorie());
			pstmt.setString(2, categorie.getLibelle());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
// UtilisateurDaoJdbcImpl.FINDONE(rs.getint(noUtilisateur));
	@Override
	public Categorie findOne(int no_categorie) {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(FIND_CATEGORIES_BY_ID)) {
			pstmt.setInt(1, no_categorie);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				Categorie categorie = new Categorie(rs.getInt("no_categorie"), rs.getString("libelle"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<Categorie> findAll() {
		try (Connection connection = ConnectionProvider.getConnection();
				Statement stmt = connection.createStatement();) {
			List<Categorie> categories = new ArrayList<>();
			ResultSet rs = stmt.executeQuery(SELECT_ALL_CATEGORIES);
			while (rs.next()) {
				Categorie categorie = new Categorie(rs.getInt("no_categorie"), rs.getString("libelle"));

				categories.add(categorie);
			}
			return categories;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void modify(Categorie categorie) {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(UPDATE_CATEGORIES)) {
			pstmt.setInt(1, categorie.getNoCategorie());
			pstmt.setString(2, categorie.getLibelle());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void remove(int no_categorie) {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(DELETE_CATEGORIES)) {
			pstmt.setInt(1, no_categorie);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<Categorie> FindByCategorie(String query) {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(FIND_BY_CATEGORIES)) {
			pstmt.setString(1, query);
			List<Categorie> categories = new ArrayList<Categorie>();
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Categorie categorie = new Categorie(rs.getInt("no_categorie"), rs.getString("libelle"));
				// Récupérer les données du ResultSet et les assigner à l'objet categorie

				categories.add(categorie);
			}
			return categories;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
