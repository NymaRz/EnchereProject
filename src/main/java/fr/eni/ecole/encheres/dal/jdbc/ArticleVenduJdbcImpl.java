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
import fr.eni.ecole.encheres.bo.Utilisateur;
import fr.eni.ecole.encheres.dal.ArticleVenduDao;
import fr.eni.ecole.encheres.dal.DaoFactory;

public class ArticleVenduJdbcImpl implements ArticleVenduDao {

	// Requetes SQL
	private static final String SELECT_ALL = "SELECT * FROM ARTICLES_VENDUS";
	private static final String SELECT_ONE = "SELECT * FROM ARTICLES_VENDUS WHERE no_article = ?";
	private static final String SAVE = "INSERT INTO ARTICLES_VENDUS (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente,etat_vente, no_utilisateur, no_categorie,id_retrait,enchere_min,img) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?)";
	private static final String DELETE_ONE = "DELETE FROM ARTICLES_VENDUS WHERE no_article = ?";
	private static final String UPDATE = "UPDATE ARTICLES_VENDUS SET nom_article=?, description=?, date_debut_encheres=?, date_fin_encheres=?, prix_initial=?, prix_vente=?,etat_vente=?, no_utilisateur=?, no_categorie=?, id_retrait=?,enchere_min=? WHERE no_article = ?";
	private static final String UPDATE_GAGNANT = "UPDATE ARTICLES_VENDUS SET gagnant=? WHERE no_article = ?";
	private static final String FIND_BY_NAME = "SELECT * FROM ARTICLES_VENDUS WHERE nom_article LIKE ?";
	private static final String FIND_BY_HIGHEST_BID = "select top(1) av.no_article,\r\n"
			+ "[nom_article]\r\n"
			+ "      ,[description]\r\n"
			+ "      ,[date_debut_encheres]\r\n"
			+ "      ,[date_fin_encheres]\r\n"
			+ "      ,[prix_initial]\r\n"
			+ "      ,[prix_vente]\r\n"
			+ "      ,[etat_vente]\r\n"
			+ "      ,av.no_utilisateur as vendeur\r\n"
			+ "      ,[no_categorie]\r\n"
			+ "      ,[id_retrait]\r\n"
			+ "      ,[enchere_min]\r\n"
			+ "      ,[img]\r\n"
			+ "      ,[gagnant],\r\n"
			+ "	   e.no_utilisateur as acheteur\r\n"
			+ "      ,[date_enchere]\r\n"
			+ "      ,[montant_enchere]\r\n"
			+ "      ,[id_enchere]\r\n"
			+ "from ENCHERES AS e INNER JOIN ARTICLES_VENDUS AS av ON e.no_article=av.no_article WHERE av.etat_vente='vf' AND av.no_article=? order by montant_enchere DESC";
	private static final String FIND_ARTICLES_BY_CATEGORIES = "SELECT no_article,nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,prix_vente,etat_vente,no_utilisateur,id_retrait,enchere_min, img FROM ARTICLES_VENDUS INNER JOIN CATEGORIES ON ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie WHERE ARTICLES_VENDUS.no_categorie=?";
	private static final String FIND_ARTICLES_BY_CATEGORIES_AND_QUERY = "SELECT no_article,nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,prix_vente,etat_vente,no_utilisateur,id_retrait,enchere_min,img FROM ARTICLES_VENDUS INNER JOIN CATEGORIES ON ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie WHERE ARTICLES_VENDUS.no_categorie=? AND ARTICLES_VENDUS.nom_article LIKE ?";
	private static final String FIND_ARTICLES_WITH_BID_BY_USER = "Select * FROM ENCHERES as e \r\n"
			+ "INNER JOIN UTILISATEURS as u \r\n" + "ON e.no_utilisateur= u.no_utilisateur \r\n"
			+ "INNER JOIN ARTICLES_VENDUS as av \r\n" + "ON av.no_article=e.no_article \r\n"
			+ "WHERE u.no_utilisateur=?";

	private static final String FIND_ARTICLES_WITH_BID_BY_USER_AND_CATEGORIE = "Select * FROM ENCHERES as e \r\n"
			+ "INNER JOIN UTILISATEURS as u \r\n" + "ON e.no_utilisateur= u.no_utilisateur \r\n"
			+ "INNER JOIN ARTICLES_VENDUS as av \r\n" + "ON av.no_article=e.no_article \r\n"
			+ "WHERE u.no_utilisateur=? AND no_categorie=?";

	private static final String FIND_ARTICLE_WITH_BID_BY_USER_AND_CATEGORIE_AND_QUERY = "Select * FROM ENCHERES as e \r\n"
			+ "INNER JOIN UTILISATEURS as u \r\n" + "ON e.no_utilisateur= u.no_utilisateur \r\n"
			+ "INNER JOIN ARTICLES_VENDUS as av \r\n" + "ON av.no_article=e.no_article \r\n"
			+ "WHERE u.no_utilisateur=? AND no_categorie=? AND as.nom_article LIKE ?";

	private static final String FIND_ARTICLE_WITH_BID_BY_USER_AND_QUERY = "Select * FROM ENCHERES as e \r\n"
			+ "INNER JOIN UTILISATEURS as u \r\n" + "ON e.no_utilisateur= u.no_utilisateur \r\n"
			+ "INNER JOIN ARTICLES_VENDUS as av \r\n" + "ON av.no_article=e.no_article \r\n"
			+ "WHERE u.no_utilisateur=? AND as.nom_article LIKE ?";

	private static final String FIND_ARTICLES_BY_VENDEUR_AND_ETAT_VENTE = "SELECT * FROM ARTICLES_VENDUS WHERE no_utilisateur=? AND etat_vente=?";
	private static final String FIND_ARTICLES_BY_AND_ETAT_VENTE = "SELECT * FROM ARTICLES_VENDUS WHERE etat_vente=?";
	private static final String FIND_ARTICLES_BY_VENDEUR_AND_ETAT_VENTE_AND_CATEGORIE = "SELECT * FROM ARTICLES_VENDUS WHERE no_utilisateur=? AND etat_vente=? AND no_categorie=?";
	private static final String FIND_ONE_ARTICLE_BY_VENDEUR_AND_ETAT_VENTE_AND_CATEGORIE = "SELECT * FROM ARTICLES_VENDUS WHERE no_utilisateur=? AND etat_vente=? AND no_categorie=? AND nom_article LIKE ?";
	private static final String FIND_ONE_ARTICLE_BY_VENDEUR_AND_ETAT_VENTE = "SELECT * FROM ARTICLES_VENDUS WHERE no_utilisateur=? AND etat_vente=? AND nom_article LIKE ?";
	private static final String FIND_ONE_BY_HIGHEST_BID = "select top(1) av.no_article,\r\n"
			+ "[nom_article]\r\n"
			+ "      ,[description]\r\n"
			+ "      ,[date_debut_encheres]\r\n"
			+ "      ,[date_fin_encheres]\r\n"
			+ "      ,[prix_initial]\r\n"
			+ "      ,[prix_vente]\r\n"
			+ "      ,[etat_vente]\r\n"
			+ "      ,av.no_utilisateur as vendeur\r\n"
			+ "      ,[no_categorie]\r\n"
			+ "      ,[id_retrait]\r\n"
			+ "      ,[enchere_min]\r\n"
			+ "      ,[img]\r\n"
			+ "      ,[gagnant],\r\n"
			+ "	   e.no_utilisateur as acheteur\r\n"
			+ "      ,[date_enchere]\r\n"
			+ "      ,[montant_enchere]\r\n"
			+ "      ,[id_enchere]\r\n"
			+ "from ENCHERES AS e INNER JOIN ARTICLES_VENDUS AS av ON e.no_article=av.no_article WHERE av.etat_vente='vf' AND av.no_article=? AND av.nom_article LIKE ? order by montant_enchere DESC";
	private static final String FIND_ONE_BY_HIGHEST_BID_AND_CATEGORIE = "select top(1) av.no_article,\r\n"
			+ "[nom_article]\r\n"
			+ "      ,[description]\r\n"
			+ "      ,[date_debut_encheres]\r\n"
			+ "      ,[date_fin_encheres]\r\n"
			+ "      ,[prix_initial]\r\n"
			+ "      ,[prix_vente]\r\n"
			+ "      ,[etat_vente]\r\n"
			+ "      ,av.no_utilisateur as vendeur\r\n"
			+ "      ,[no_categorie]\r\n"
			+ "      ,[id_retrait]\r\n"
			+ "      ,[enchere_min]\r\n"
			+ "      ,[img]\r\n"
			+ "      ,[gagnant],\r\n"
			+ "	   e.no_utilisateur as acheteur\r\n"
			+ "      ,[date_enchere]\r\n"
			+ "      ,[montant_enchere]\r\n"
			+ "      ,[id_enchere]\r\n"
			+ "from ENCHERES AS e INNER JOIN ARTICLES_VENDUS AS av ON e.no_article=av.no_article WHERE av.etat_vente='vf' AND av.no_article=? AND av.nom_article LIKE ? AND av.no_categorie=? order by montant_enchere DESC";
	private static final String FIND_BY_HIGHEST_BID_AND_USER = "select top(1) av.no_article,\r\n"
			+ "[nom_article]\r\n"
			+ "      ,[description]\r\n"
			+ "      ,[date_debut_encheres]\r\n"
			+ "      ,[date_fin_encheres]\r\n"
			+ "      ,[prix_initial]\r\n"
			+ "      ,[prix_vente]\r\n"
			+ "      ,[etat_vente]\r\n"
			+ "      ,av.no_utilisateur as vendeur\r\n"
			+ "      ,[no_categorie]\r\n"
			+ "      ,[id_retrait]\r\n"
			+ "      ,[enchere_min]\r\n"
			+ "      ,[img]\r\n"
			+ "      ,[gagnant],\r\n"
			+ "	   e.no_utilisateur as acheteur\r\n"
			+ "      ,[date_enchere]\r\n"
			+ "      ,[montant_enchere]\r\n"
			+ "      ,[id_enchere]\r\n"
			+ "from ENCHERES AS e INNER JOIN ARTICLES_VENDUS AS av ON e.no_article=av.no_article WHERE av.no_article=? AND e.no_utilisateur=? order by montant_enchere DESC";

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
			pstmt.setString(12, articleVendu.getJaquette());
			

			// Exécuter la requête
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ArticleVendu findOne(int noArticle) {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(SELECT_ONE);) {
			pstmt.setInt(1, noArticle);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				ArticleVendu articleVendu = new ArticleVendu();
				// Récupérer les données du ResultSet et les assigner à l'objet articleVendu
				articleVendu.setnoArticle(rs.getInt("no_article"));
				articleVendu.setNomArticle(rs.getString("nom_article"));
				articleVendu.setDescription(rs.getString("description"));
				articleVendu.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
				articleVendu.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
				articleVendu.setMiseAPrix(rs.getInt("prix_initial"));
				articleVendu.setPrixVente(rs.getInt("prix_vente"));
				articleVendu.setEtatVente(rs.getString("etat_vente"));
				articleVendu.setUtilisateur(DaoFactory.getUtilisateurDao().findOne(rs.getInt("no_utilisateur")));
				articleVendu.setCategorieArticle(DaoFactory.getCategorieDao().findOne(rs.getInt("no_categorie")));
				articleVendu.setLieuRetrait(DaoFactory.getRetraitDao().findOne(rs.getInt("id_retrait")));
				articleVendu.setEnchereMin(rs.getInt("enchere_min"));
				articleVendu.setJaquette(rs.getString("img"));
				return articleVendu;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<ArticleVendu> findAll() {
		try (Connection connection = ConnectionProvider.getConnection();
				Statement stmt = connection.createStatement();) {
			List<ArticleVendu> articlesVendus = new ArrayList<>();
			ResultSet rs = stmt.executeQuery(SELECT_ALL);
			while (rs.next()) {
				ArticleVendu articleVendu = new ArticleVendu();
				// Récupérer les données du ResultSet et les assigner à l'objet articleVendu
				articleVendu.setnoArticle(rs.getInt("no_article"));
				articleVendu.setNomArticle(rs.getString("nom_article"));
				articleVendu.setDescription(rs.getString("description"));
				articleVendu.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
				articleVendu.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
				articleVendu.setMiseAPrix(rs.getInt("prix_initial"));
				articleVendu.setPrixVente(rs.getInt("prix_vente"));
				articleVendu.setEtatVente(rs.getString("etat_vente"));
				articleVendu.setUtilisateur(DaoFactory.getUtilisateurDao().findOne(rs.getInt("no_utilisateur")));
				articleVendu.setCategorieArticle(DaoFactory.getCategorieDao().findOne(rs.getInt("no_categorie")));
				articleVendu.setLieuRetrait(DaoFactory.getRetraitDao().findOne(rs.getInt("id_retrait")));
				articleVendu.setEnchereMin(rs.getInt("enchere_min"));
				articleVendu.setJaquette(rs.getString("img"));
				articlesVendus.add(articleVendu);
			}
			return articlesVendus;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
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

	@Override
	public void modifyGagnant(ArticleVendu articleVendu) {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(UPDATE_GAGNANT)) {
			// Valoriser les paramètres de la requête
			System.out.println("*entrée DAL modifier gagnant*");
			pstmt.setInt(1, articleVendu.getGagnant().getNoUtilisateur());
			pstmt.setInt(2, articleVendu.getnoArticle());
			pstmt.executeUpdate();
			System.out.println("*sortie DAL*");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void remove(int noArticle) {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(DELETE_ONE);) {
			pstmt.setInt(1, noArticle);
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
				articleVendu.setnoArticle(rs.getInt("no_article"));
				articleVendu.setNomArticle(rs.getString("nom_article"));
				articleVendu.setDescription(rs.getString("description"));
				articleVendu.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
				articleVendu.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
				articleVendu.setMiseAPrix(rs.getInt("prix_initial"));
				articleVendu.setPrixVente(rs.getInt("prix_vente"));
				articleVendu.setEtatVente(rs.getString("etat_vente"));
				articleVendu.setUtilisateur(DaoFactory.getUtilisateurDao().findOne(rs.getInt("no_utilisateur")));
				articleVendu.setCategorieArticle(DaoFactory.getCategorieDao().findOne(rs.getInt("no_categorie")));
				articleVendu.setLieuRetrait(DaoFactory.getRetraitDao().findOne(rs.getInt("id_retrait")));
				articleVendu.setEnchereMin(rs.getInt("enchere_min"));
				articleVendu.setJaquette(rs.getString("img"));
				articleVendus.add(articleVendu);
			}
			return articleVendus;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<ArticleVendu> recupTousLEsArticlesDeCategorie(int noCategorie) {
		System.out.println("recupTousLEsArticlesDeCategorie");
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(FIND_ARTICLES_BY_CATEGORIES)) {
			pstmt.setInt(1, noCategorie);
			List<ArticleVendu> articlesOfCategorie = new ArrayList<ArticleVendu>();
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				ArticleVendu articleVendu = new ArticleVendu();
				articleVendu.setnoArticle(rs.getInt("no_article"));
				articleVendu.setNomArticle(rs.getString("nom_article"));
				articleVendu.setDescription(rs.getString("description"));
				articleVendu.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
				articleVendu.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
				articleVendu.setMiseAPrix(rs.getInt("prix_initial"));
				articleVendu.setPrixVente(rs.getInt("prix_vente"));
				articleVendu.setEtatVente(rs.getString("etat_vente"));
				articleVendu.setUtilisateur(DaoFactory.getUtilisateurDao().findOne(rs.getInt("no_utilisateur")));
				articleVendu.setCategorieArticle(DaoFactory.getCategorieDao().findOne(noCategorie));
				articleVendu.setLieuRetrait(DaoFactory.getRetraitDao().findOne(rs.getInt("id_retrait")));
				articleVendu.setEnchereMin(rs.getInt("enchere_min"));
				articleVendu.setJaquette(rs.getString("img"));

				articlesOfCategorie.add(articleVendu);
			}
			System.out.println("DAl*****");
			System.out.println(articlesOfCategorie);
			return articlesOfCategorie;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<ArticleVendu> rechercheArticlesDeCategorieByName(int noCategorie, String query) {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(FIND_ARTICLES_BY_CATEGORIES_AND_QUERY)) {
			pstmt.setInt(1, noCategorie);
			pstmt.setString(2, query);
			List<ArticleVendu> articlesOfCategorie = new ArrayList<ArticleVendu>();
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				ArticleVendu articleVendu = new ArticleVendu();
				articleVendu.setnoArticle(rs.getInt("no_article"));
				articleVendu.setNomArticle(rs.getString("nom_article"));
				articleVendu.setDescription(rs.getString("description"));
				articleVendu.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
				articleVendu.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
				articleVendu.setMiseAPrix(rs.getInt("prix_initial"));
				articleVendu.setPrixVente(rs.getInt("prix_vente"));
				articleVendu.setEtatVente(rs.getString("etat_vente"));
				articleVendu.setUtilisateur(DaoFactory.getUtilisateurDao().findOne(rs.getInt("no_utilisateur")));
				articleVendu.setCategorieArticle(DaoFactory.getCategorieDao().findOne(noCategorie));
				articleVendu.setLieuRetrait(DaoFactory.getRetraitDao().findOne(rs.getInt("id_retrait")));
				articleVendu.setEnchereMin(rs.getInt("enchere_min"));
				articleVendu.setJaquette(rs.getString("img"));

				articlesOfCategorie.add(articleVendu);
			}
			return articlesOfCategorie;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<ArticleVendu> recupArticlesEncheresParUtilisateur(Utilisateur utilisateur) {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(FIND_ARTICLES_WITH_BID_BY_USER);) {
			pstmt.setInt(1, utilisateur.getNoUtilisateur());
			List<ArticleVendu> articles = new ArrayList<ArticleVendu>();
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				ArticleVendu articleVendu = new ArticleVendu();
				articleVendu.setnoArticle(rs.getInt("no_article"));
				articleVendu.setNomArticle(rs.getString("nom_article"));
				articleVendu.setDescription(rs.getString("description"));
				articleVendu.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
				articleVendu.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
				articleVendu.setMiseAPrix(rs.getInt("prix_initial"));
				articleVendu.setPrixVente(rs.getInt("prix_vente"));
				articleVendu.setEtatVente(rs.getString("etat_vente"));
				articleVendu.setUtilisateur(DaoFactory.getUtilisateurDao().findOne(rs.getInt("no_utilisateur")));
				articleVendu.setCategorieArticle(DaoFactory.getCategorieDao().findOne(rs.getInt("no_categorie")));
				articleVendu.setLieuRetrait(DaoFactory.getRetraitDao().findOne(rs.getInt("id_retrait")));
				articleVendu.setEnchereMin(rs.getInt("enchere_min"));
				articleVendu.setJaquette(rs.getString("img"));

				articles.add(articleVendu);
			}
			return articles;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<ArticleVendu> recupArticlesEncherisParUtilisateurEtCategorie(Utilisateur utilisateur, int noCategorie) {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(FIND_ARTICLES_WITH_BID_BY_USER_AND_CATEGORIE);) {
			pstmt.setInt(1, utilisateur.getNoUtilisateur());
			pstmt.setInt(2, noCategorie);
			List<ArticleVendu> articles = new ArrayList<ArticleVendu>();
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				ArticleVendu articleVendu = new ArticleVendu();
				articleVendu.setnoArticle(rs.getInt("no_article"));
				articleVendu.setNomArticle(rs.getString("nom_article"));
				articleVendu.setDescription(rs.getString("description"));
				articleVendu.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
				articleVendu.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
				articleVendu.setMiseAPrix(rs.getInt("prix_initial"));
				articleVendu.setPrixVente(rs.getInt("prix_vente"));
				articleVendu.setEtatVente(rs.getString("etat_vente"));
				articleVendu.setUtilisateur(DaoFactory.getUtilisateurDao().findOne(rs.getInt("no_utilisateur")));
				articleVendu.setCategorieArticle(DaoFactory.getCategorieDao().findOne(rs.getInt("no_categorie")));
				articleVendu.setLieuRetrait(DaoFactory.getRetraitDao().findOne(rs.getInt("id_retrait")));
				articleVendu.setEnchereMin(rs.getInt("enchere_min"));
				articleVendu.setJaquette(rs.getString("img"));

				articles.add(articleVendu);
			}
			return articles;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<ArticleVendu> recupArticlesVendusParUtilisateurSelonEtatVente(Utilisateur utilisateur,
			String etatVente) {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(FIND_ARTICLES_BY_VENDEUR_AND_ETAT_VENTE);) {
			pstmt.setInt(1, utilisateur.getNoUtilisateur());
			pstmt.setString(2, etatVente);
			List<ArticleVendu> articles = new ArrayList<ArticleVendu>();
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				ArticleVendu articleVendu = new ArticleVendu();
				articleVendu.setnoArticle(rs.getInt("no_article"));
				articleVendu.setNomArticle(rs.getString("nom_article"));
				articleVendu.setDescription(rs.getString("description"));
				articleVendu.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
				articleVendu.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
				articleVendu.setMiseAPrix(rs.getInt("prix_initial"));
				articleVendu.setPrixVente(rs.getInt("prix_vente"));
				articleVendu.setEtatVente(rs.getString("etat_vente"));
				articleVendu.setUtilisateur(DaoFactory.getUtilisateurDao().findOne(rs.getInt("no_utilisateur")));
				articleVendu.setCategorieArticle(DaoFactory.getCategorieDao().findOne(rs.getInt("no_categorie")));
				articleVendu.setLieuRetrait(DaoFactory.getRetraitDao().findOne(rs.getInt("id_retrait")));
				articleVendu.setEnchereMin(rs.getInt("enchere_min"));
				articleVendu.setJaquette(rs.getString("img"));

				articles.add(articleVendu);
			}
			return articles;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<ArticleVendu> recupTousLesArticlesSelonEtatVente(String etatVente) {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(FIND_ARTICLES_BY_AND_ETAT_VENTE);) {
			pstmt.setString(1, etatVente);
			List<ArticleVendu> articles = new ArrayList<ArticleVendu>();
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				ArticleVendu articleVendu = new ArticleVendu();
				articleVendu.setnoArticle(rs.getInt("no_article"));
				articleVendu.setNomArticle(rs.getString("nom_article"));
				articleVendu.setDescription(rs.getString("description"));
				articleVendu.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
				articleVendu.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
				articleVendu.setMiseAPrix(rs.getInt("prix_initial"));
				articleVendu.setPrixVente(rs.getInt("prix_vente"));
				articleVendu.setEtatVente(rs.getString("etat_vente"));
				articleVendu.setUtilisateur(DaoFactory.getUtilisateurDao().findOne(rs.getInt("no_utilisateur")));
				articleVendu.setCategorieArticle(DaoFactory.getCategorieDao().findOne(rs.getInt("no_categorie")));
				articleVendu.setLieuRetrait(DaoFactory.getRetraitDao().findOne(rs.getInt("id_retrait")));
				articleVendu.setEnchereMin(rs.getInt("enchere_min"));
				articleVendu.setJaquette(rs.getString("img"));

				articles.add(articleVendu);
			}
			return articles;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<ArticleVendu> recupUnArticleEncheriParutilisateurEtCategorie(Utilisateur utilisateur, int noCategorie,
			String q) {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement pstmt = connection
						.prepareStatement(FIND_ARTICLE_WITH_BID_BY_USER_AND_CATEGORIE_AND_QUERY);) {
			pstmt.setInt(1, utilisateur.getNoUtilisateur());
			pstmt.setInt(2, noCategorie);
			pstmt.setString(3, q);
			List<ArticleVendu> articles = new ArrayList<ArticleVendu>();
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				ArticleVendu articleVendu = new ArticleVendu();
				articleVendu.setnoArticle(rs.getInt("no_article"));
				articleVendu.setNomArticle(rs.getString("nom_article"));
				articleVendu.setDescription(rs.getString("description"));
				articleVendu.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
				articleVendu.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
				articleVendu.setMiseAPrix(rs.getInt("prix_initial"));
				articleVendu.setPrixVente(rs.getInt("prix_vente"));
				articleVendu.setEtatVente(rs.getString("etat_vente"));
				articleVendu.setUtilisateur(DaoFactory.getUtilisateurDao().findOne(rs.getInt("no_utilisateur")));
				articleVendu.setCategorieArticle(DaoFactory.getCategorieDao().findOne(rs.getInt("no_categorie")));
				articleVendu.setLieuRetrait(DaoFactory.getRetraitDao().findOne(rs.getInt("id_retrait")));
				articleVendu.setEnchereMin(rs.getInt("enchere_min"));
				articleVendu.setJaquette(rs.getString("img"));

				articles.add(articleVendu);
			}
			return articles;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<ArticleVendu> recupUnArticleEncheriParUtilisateur(Utilisateur utilisateur, String q) {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(FIND_ARTICLE_WITH_BID_BY_USER_AND_QUERY);) {
			pstmt.setInt(1, utilisateur.getNoUtilisateur());
			pstmt.setString(2, q);
			List<ArticleVendu> articles = new ArrayList<ArticleVendu>();
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				ArticleVendu articleVendu = new ArticleVendu();
				articleVendu.setnoArticle(rs.getInt("no_article"));
				articleVendu.setNomArticle(rs.getString("nom_article"));
				articleVendu.setDescription(rs.getString("description"));
				articleVendu.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
				articleVendu.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
				articleVendu.setMiseAPrix(rs.getInt("prix_initial"));
				articleVendu.setPrixVente(rs.getInt("prix_vente"));
				articleVendu.setEtatVente(rs.getString("etat_vente"));
				articleVendu.setUtilisateur(DaoFactory.getUtilisateurDao().findOne(rs.getInt("no_utilisateur")));
				articleVendu.setCategorieArticle(DaoFactory.getCategorieDao().findOne(rs.getInt("no_categorie")));
				articleVendu.setLieuRetrait(DaoFactory.getRetraitDao().findOne(rs.getInt("id_retrait")));
				articleVendu.setEnchereMin(rs.getInt("enchere_min"));
				articleVendu.setJaquette(rs.getString("img"));

				articles.add(articleVendu);
			}
			return articles;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<ArticleVendu> recupUnArticlesVendusParUtilisateurSelonEtatVenteEtCategorie(Utilisateur utilisateur,
			int noCategorie, String q, String etatVente) {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement pstmt = connection
						.prepareStatement(FIND_ONE_ARTICLE_BY_VENDEUR_AND_ETAT_VENTE_AND_CATEGORIE);) {
			pstmt.setInt(1, utilisateur.getNoUtilisateur());
			pstmt.setString(2, etatVente);
			pstmt.setInt(3, noCategorie);
			pstmt.setString(4, q);
			List<ArticleVendu> articles = new ArrayList<ArticleVendu>();
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				ArticleVendu articleVendu = new ArticleVendu();
				articleVendu.setnoArticle(rs.getInt("no_article"));
				articleVendu.setNomArticle(rs.getString("nom_article"));
				articleVendu.setDescription(rs.getString("description"));
				articleVendu.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
				articleVendu.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
				articleVendu.setMiseAPrix(rs.getInt("prix_initial"));
				articleVendu.setPrixVente(rs.getInt("prix_vente"));
				articleVendu.setEtatVente(rs.getString("etat_vente"));
				articleVendu.setUtilisateur(DaoFactory.getUtilisateurDao().findOne(rs.getInt("no_utilisateur")));
				articleVendu.setCategorieArticle(DaoFactory.getCategorieDao().findOne(rs.getInt("no_categorie")));
				articleVendu.setLieuRetrait(DaoFactory.getRetraitDao().findOne(rs.getInt("id_retrait")));
				articleVendu.setEnchereMin(rs.getInt("enchere_min"));
				articleVendu.setJaquette(rs.getString("img"));

				articles.add(articleVendu);
			}
			return articles;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<ArticleVendu> recupArticlesVendusParUtilisateurSelonEtatVenteEtCategorie(Utilisateur utilisateur,
			int noCategorie, String etatVente) {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement pstmt = connection
						.prepareStatement(FIND_ARTICLES_BY_VENDEUR_AND_ETAT_VENTE_AND_CATEGORIE);) {
			pstmt.setInt(1, utilisateur.getNoUtilisateur());
			pstmt.setString(2, etatVente);
			pstmt.setInt(3, noCategorie);
			List<ArticleVendu> articles = new ArrayList<ArticleVendu>();
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				ArticleVendu articleVendu = new ArticleVendu();
				articleVendu.setnoArticle(rs.getInt("no_article"));
				articleVendu.setNomArticle(rs.getString("nom_article"));
				articleVendu.setDescription(rs.getString("description"));
				articleVendu.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
				articleVendu.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
				articleVendu.setMiseAPrix(rs.getInt("prix_initial"));
				articleVendu.setPrixVente(rs.getInt("prix_vente"));
				articleVendu.setEtatVente(rs.getString("etat_vente"));
				articleVendu.setUtilisateur(DaoFactory.getUtilisateurDao().findOne(rs.getInt("no_utilisateur")));
				articleVendu.setCategorieArticle(DaoFactory.getCategorieDao().findOne(rs.getInt("no_categorie")));
				articleVendu.setLieuRetrait(DaoFactory.getRetraitDao().findOne(rs.getInt("id_retrait")));
				articleVendu.setEnchereMin(rs.getInt("enchere_min"));
				articleVendu.setJaquette(rs.getString("img"));

				articles.add(articleVendu);
			}
			return articles;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<ArticleVendu> recupUnArticlesVendusParUtilisateurSelonEtatVente(Utilisateur utilisateur, String q,
			String etatVente) {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(FIND_ONE_ARTICLE_BY_VENDEUR_AND_ETAT_VENTE);) {
			pstmt.setInt(1, utilisateur.getNoUtilisateur());
			pstmt.setString(2, etatVente);
			pstmt.setString(3, q);
			List<ArticleVendu> articles = new ArrayList<ArticleVendu>();
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				ArticleVendu articleVendu = new ArticleVendu();
				articleVendu.setnoArticle(rs.getInt("no_article"));
				articleVendu.setNomArticle(rs.getString("nom_article"));
				articleVendu.setDescription(rs.getString("description"));
				articleVendu.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
				articleVendu.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
				articleVendu.setMiseAPrix(rs.getInt("prix_initial"));
				articleVendu.setPrixVente(rs.getInt("prix_vente"));
				articleVendu.setEtatVente(rs.getString("etat_vente"));
				articleVendu.setUtilisateur(DaoFactory.getUtilisateurDao().findOne(rs.getInt("no_utilisateur")));
				articleVendu.setCategorieArticle(DaoFactory.getCategorieDao().findOne(rs.getInt("no_categorie")));
				articleVendu.setLieuRetrait(DaoFactory.getRetraitDao().findOne(rs.getInt("id_retrait")));
				articleVendu.setEnchereMin(rs.getInt("enchere_min"));
				articleVendu.setJaquette(rs.getString("img"));

				articles.add(articleVendu);
			}
			return articles;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ArticleVendu recupParEnchereLaPlusHaute(ArticleVendu articleVendu) {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(FIND_BY_HIGHEST_BID);) {
			pstmt.setInt(1, articleVendu.getnoArticle());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				ArticleVendu article = new ArticleVendu();
				// Récupérer les données du ResultSet et les assigner à l'objet articleVendu
				article.setnoArticle(rs.getInt("no_article"));
				article.setNomArticle(rs.getString("nom_article"));
				article.setDescription(rs.getString("description"));
				article.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
				article.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
				article.setMiseAPrix(rs.getInt("prix_initial"));
				article.setPrixVente(rs.getInt("prix_vente"));
				article.setEtatVente(rs.getString("etat_vente"));
				article.setUtilisateur(DaoFactory.getUtilisateurDao().findOne(rs.getInt("vendeur")));
				article.setCategorieArticle(DaoFactory.getCategorieDao().findOne(rs.getInt("no_categorie")));
				article.setLieuRetrait(DaoFactory.getRetraitDao().findOne(rs.getInt("id_retrait")));
				article.setEnchereMin(rs.getInt("enchere_min"));
				article.setJaquette(rs.getString("img"));
				System.out.println("***********************");
				article.setGagnant(DaoFactory.getUtilisateurDao().findOne(rs.getInt("acheteur")));
				this.modifyGagnant(article);
				return article;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ArticleVendu recupUnArticleParEnchereLaPlusHaute(ArticleVendu article, String q) {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(FIND_ONE_BY_HIGHEST_BID);) {
			pstmt.setInt(1, article.getnoArticle());
			pstmt.setString(2, q);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				ArticleVendu articleVendu = new ArticleVendu();
				// Récupérer les données du ResultSet et les assigner à l'objet articleVendu
				articleVendu.setnoArticle(rs.getInt("no_article"));
				articleVendu.setNomArticle(rs.getString("nom_article"));
				articleVendu.setDescription(rs.getString("description"));
				articleVendu.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
				articleVendu.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
				articleVendu.setMiseAPrix(rs.getInt("prix_initial"));
				articleVendu.setPrixVente(rs.getInt("prix_vente"));
				articleVendu.setEtatVente(rs.getString("etat_vente"));
				articleVendu.setUtilisateur(DaoFactory.getUtilisateurDao().findOne(rs.getInt("vendeur")));
				articleVendu.setCategorieArticle(DaoFactory.getCategorieDao().findOne(rs.getInt("no_categorie")));
				articleVendu.setLieuRetrait(DaoFactory.getRetraitDao().findOne(rs.getInt("id_retrait")));
				articleVendu.setEnchereMin(rs.getInt("enchere_min"));
				articleVendu.setJaquette(rs.getString("img"));
				System.out.println("***********************");
				articleVendu.setGagnant(DaoFactory.getUtilisateurDao().findOne(rs.getInt("acheteur")));
				this.modifyGagnant(articleVendu);
				return articleVendu;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ArticleVendu recupUnArticleParEnchereLaPlusHauteEtCategorie(ArticleVendu article, int noCategorie,
			String q) {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(FIND_ONE_BY_HIGHEST_BID_AND_CATEGORIE);) {
			pstmt.setInt(1, article.getnoArticle());
			pstmt.setString(2, q);
			pstmt.setInt(3, noCategorie);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				ArticleVendu articleVendu = new ArticleVendu();
				// Récupérer les données du ResultSet et les assigner à l'objet articleVendu
				articleVendu.setnoArticle(rs.getInt("no_article"));
				articleVendu.setNomArticle(rs.getString("nom_article"));
				articleVendu.setDescription(rs.getString("description"));
				articleVendu.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
				articleVendu.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
				articleVendu.setMiseAPrix(rs.getInt("prix_initial"));
				articleVendu.setPrixVente(rs.getInt("prix_vente"));
				articleVendu.setEtatVente(rs.getString("etat_vente"));
				articleVendu.setUtilisateur(DaoFactory.getUtilisateurDao().findOne(rs.getInt("vendeur")));
				articleVendu.setCategorieArticle(DaoFactory.getCategorieDao().findOne(rs.getInt("no_categorie")));
				articleVendu.setLieuRetrait(DaoFactory.getRetraitDao().findOne(rs.getInt("id_retrait")));
				articleVendu.setEnchereMin(rs.getInt("enchere_min"));
				articleVendu.setJaquette(rs.getString("img"));
				System.out.println("***********************");
				articleVendu.setGagnant(DaoFactory.getUtilisateurDao().findOne(rs.getInt("acheteur")));
				this.modifyGagnant(articleVendu);
				return articleVendu;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ArticleVendu recupParEnchereLaPlusHauteParUtilisateur(Utilisateur utilisateur, ArticleVendu articleVendu) {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(FIND_BY_HIGHEST_BID_AND_USER);) {
			pstmt.setInt(1, articleVendu.getnoArticle());
			pstmt.setInt(2, utilisateur.getNoUtilisateur());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				ArticleVendu article = new ArticleVendu();
				// Récupérer les données du ResultSet et les assigner à l'objet articleVendu
				article.setnoArticle(rs.getInt("no_article"));
				article.setNomArticle(rs.getString("nom_article"));
				article.setDescription(rs.getString("description"));
				article.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
				article.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
				article.setMiseAPrix(rs.getInt("prix_initial"));
				article.setPrixVente(rs.getInt("prix_vente"));
				article.setEtatVente(rs.getString("etat_vente"));
				article.setUtilisateur(DaoFactory.getUtilisateurDao().findOne(rs.getInt("vendeur")));
				article.setCategorieArticle(DaoFactory.getCategorieDao().findOne(rs.getInt("no_categorie")));
				article.setLieuRetrait(DaoFactory.getRetraitDao().findOne(rs.getInt("id_retrait")));
				article.setEnchereMin(rs.getInt("enchere_min"));
				article.setJaquette(rs.getString("img"));
				System.out.println("***********************");
				article.setGagnant(DaoFactory.getUtilisateurDao().findOne(rs.getInt("acheteur")));
				this.modifyGagnant(article);
				return article;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	


}
