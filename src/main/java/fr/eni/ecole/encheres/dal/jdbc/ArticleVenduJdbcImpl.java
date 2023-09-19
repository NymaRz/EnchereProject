package fr.eni.ecole.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.ecole.encheres.bo.ArticleVendu;
import fr.eni.ecole.encheres.dal.ArticleVenduDao;

public class ArticleVenduJdbcImpl implements ArticleVenduDao {

	// Requetes SQL
	private static final String SELECT_ALL = "SELECT * FROM ARTICLES_VENDUS";
	private static final String SELECT_ONE = "SELECT * FROM ARTICLES_VENDUS WHERE id = ?";
	private static final String SAVE = "INSERT INTO ARTICLES_VENDUS (name, description, date_debut_encheres, date_fin_encheres, mise_a_prix, prix_vente, utilisateur, categorie_article) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String DELETE_ONE = "DELETE FROM ARTICLES_VENDUS WHERE id = ?";
	private static final String UPDATE = "UPDATE ARTICLES_VENDUS SET nom_article=?, description=?, date_debut_encheres=?, date_fin_encheres=?, prix_initial=?, prix_vente=?,etat_vente=?, no_utilisateur=?, no_categorie=?, id_retrait=?,enchere_min=? WHERE id = ?";
	private static final String FIND_BY_NAME = "SELECT * FROM ARTICLES_VENDUS WHERE name LIKE ?";

	@Override
	public void save(ArticleVendu articleVendu) {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(SAVE);) {
			// Valoriser les paramètres de la requête
			pstmt.setString(1, articleVendu.getNomArticle());
			pstmt.setString(2, articleVendu.getDescription());
			pstmt.setDate(3, Date.valueOf(articleVendu.getDateDebutEncheres()));
			pstmt.setDate(4, Date.valueOf(articleVendu.getDateFinEncheres()));
			pstmt.setInt(5, articleVendu.getMiseAPrix());
			pstmt.setInt(6, articleVendu.getPrixVente());
			pstmt.setString(7, articleVendu.getEtatVente());
			pstmt.setInt(8, articleVendu.getUtilisateur().getNoUtilisateur()); // Utilisateur étant un objet que SQL ne
																				// connait pas, on fait référence au
																				// numéro d'utilisateur (qui d'ailleurs
																				// fait officde de foreign key)
			pstmt.setInt(9, articleVendu.getCategorieArticle().getNoCategorie()); // idem (jai aussi modifié la requête
																					// pour utilisateur et categorie)
			pstmt.setInt(10, articleVendu.getLieuRetrait().getIdRetrait());// idem encore, j'ai modifié la bo pour
																			// donner un id au lieu de retrait
			pstmt.setInt(11, articleVendu.getEnchereMin());

			// donner l'id de l'article pour cibler l'article à modifier
			pstmt.setInt(12, articleVendu.getnoArticle());
			// Exécuter la requête
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArticleVendu findOne(int id) {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(SELECT_ONE);) {
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				ArticleVendu articleVendu = new ArticleVendu();
				// Récupérer les données du ResultSet et les assigner à l'objet articleVendu
				articleVendu.setnoArticle(rs.getInt("id"));
				articleVendu.setNomArticle(rs.getString("name"));
				articleVendu.setDescription(rs.getString("description"));
				articleVendu.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
				articleVendu.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
				articleVendu.setMiseAPrix(rs.getInt("mise_a_prix"));
				articleVendu.setPrixVente(rs.getInt("prix_vente"));
				articleVendu.setUtilisateur(rs.getInt("no_utilisateur"));// il faut pointer vers l'utilisateur
				articleVendu.setCategorieArticle(CategorieDaoJdbcImpl.findOne(rs.getInt("no_categorie")));// et la catégorie
				// je pense qu'il faudra faire références à l'implémentation jdbc d'utilisateur et catégorie.
				return articleVendu;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<ArticleVendu> findAll() {
		try (Connection connection = ConnectionProvider.getConnection();
				Statement stmt = connection.createStatement();) {
			List<ArticleVendu> articlesVendus = new ArrayList<>();
			ResultSet rs = stmt.executeQuery(SELECT_ALL);
			while (rs.next()) {
				ArticleVendu articleVendu = new ArticleVendu();
				// Récupérer les données du ResultSet et les assigner à l'objet articleVendu
				articleVendu.setnoArticle(rs.getInt("id"));
				articleVendu.setNomArticle(rs.getString("name"));
				articleVendu.setDescription(rs.getString("description"));
				articleVendu.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
				articleVendu.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
				articleVendu.setMiseAPrix(rs.getInt("mise_a_prix"));
				articleVendu.setPrixVente(rs.getInt("prix_vente"));
				articleVendu.setUtilisateur(rs.getInt("no_utilisateur"));
				articleVendu.setCategorieArticle(rs.getInt("no_categorie"));
				articlesVendus.add(articleVendu);
			}
			return articlesVendus;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void modify(ArticleVendu articleVendu) {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(UPDATE)) {

			// Valoriser les paramètres de la requête
			pstmt.setString(1, articleVendu.getNomArticle());
			pstmt.setString(2, articleVendu.getDescription());
			pstmt.setDate(3, Date.valueOf(articleVendu.getDateDebutEncheres()));
			pstmt.setDate(4, Date.valueOf(articleVendu.getDateFinEncheres()));
			pstmt.setInt(5, articleVendu.getMiseAPrix());
			pstmt.setInt(6, articleVendu.getPrixVente());
			pstmt.setString(7, articleVendu.getUtilisateur());
			pstmt.setString(8, articleVendu.getCategorieArticle());
			pstmt.setInt(9, articleVendu.getId());

			// Exécuter la requête
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void remove(int id) {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(DELETE_ONE);) {
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<ArticleVendu> findByName(String query) {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(FIND_BY_NAME)) {
			pstmt.setString(1, "%" + query + "%");
			List<ArticleVendu> articleVendus = new ArrayList<>();
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				ArticleVendu articleVendu = new ArticleVendu();
				// Récupérer les données du ResultSet et les assigner à l'objet articleVendu
				articleVendu.setnoArticle(rs.getInt("id"));
				articleVendu.setNomArticle(rs.getString("name"));
				articleVendu.setDescription(rs.getString("description"));
				articleVendu.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
				articleVendu.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
				articleVendu.setMiseAPrix(rs.getInt("mise_a_prix"));
				articleVendu.setPrixVente(rs.getInt("prix_vente"));
				articleVendu.setUtilisateur(rs.getString("utilisateur"));
				articleVendu.setCategorieArticle(rs.getString("categorie_article"));
				articleVendus.add(articleVendu);
			}
			return articleVendus;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
