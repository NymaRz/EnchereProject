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
import fr.eni.ecole.encheres.bo.Utilisateur;
import fr.eni.ecole.encheres.dal.DaoFactory;
import fr.eni.ecole.encheres.dal.UtilisateurDao;
import fr.eni.ecole.encheres.ihm.exception.EmailExisteDejaException;
import fr.eni.ecole.encheres.ihm.exception.PseudoExisteDejaException;

public class UtilisateurDaoJdbcImpl implements UtilisateurDao {

	private static final String SAVE_USER = "INSERT INTO UTILISATEURS (pseudo,nom,prenom,email,telephone,id_adresse,mot_de_passe,credit,administrateur,vip) VALUES (?,?,?,?,?,?,?,?,?,?)";
	private static final String FIND_UTILISATEUR_BY_ID = "SELECT * FROM UTILISATEURS as u INNER JOIN ADRESSES as a ON u.id_adresse=a.id_adresse WHERE no_utilisateur=?";
	private static final String SELECT_ALL_UTILISATEURS = "SELECT * FROM UTILISATEURS";
	private static final String UPDATE_UTILISATEUR = "UPDATE UTILISATEURS set pseudo=?,nom=?,prenom=?,email=?,telephone=?,id_adresse=?,mot_de_passe=?,credit=?,administrateur=?,vip=? WHERE no_utilisateur=?";
	private static final String DELETE_UTILISATEUR = "DELETE FROM UTILISATEURS WHERE no_utilisateur=?";
	private static final String FIND_BY_PSEUDO_UTILISATEUR = "SELECT * FROM UTILISATEURS WHERE pseudo LIKE ?";
	private static final String SELECT_BY_EMAIL = "SELECT * FROM UTILISATEURS WHERE email = ?";
	private static final int ERROR_CODE_UNIQUE_VIOLATION = 2627;
	private static final int ERROR_MAIL_UNIQUE_VIOLATION = 2628;

	@Override
	public void save(Utilisateur utilisateur) throws EmailExisteDejaException, PseudoExisteDejaException {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(SAVE_USER)) {
			System.out.println("création de compte");
			pstmt.setString(1, utilisateur.getPseudo());
			pstmt.setString(2, utilisateur.getNom());
			pstmt.setString(3, utilisateur.getPrenom());
			pstmt.setString(4, utilisateur.getEmail());
			pstmt.setString(5, utilisateur.getTelephone());
			pstmt.setInt(6, utilisateur.getAdresse().getIdAdresse());
			pstmt.setString(7, utilisateur.getMdp());
			pstmt.setInt(8, utilisateur.getCredit());
			pstmt.setBoolean(9, utilisateur.isAdmin());
			pstmt.setBoolean(10, utilisateur.isVip());
			pstmt.executeUpdate();

			System.out.println("Compte enregistré dans la BDD");
		} catch (SQLException e) {
			if (e.getErrorCode() == ERROR_CODE_UNIQUE_VIOLATION) {
				if (e.getMessage().contains("email")) {
					throw new EmailExisteDejaException("L'adresse email choisie est déjà utilisée");
				} else if (e.getMessage().contains("pseudo")) {
					throw new PseudoExisteDejaException("Le pseudo choisi est déjà utilisé");
				}
			} else {
				e.printStackTrace(); // Gérez les autres exceptions SQLException ici.
			}
		}
	}

	@Override
	public Utilisateur findOne(int noUtilisateur) {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(FIND_UTILISATEUR_BY_ID)) {
			pstmt.setInt(1, noUtilisateur);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				Adresse adresse = new Adresse();
				adresse.setIdAdresse(rs.getInt("id_adresse"));
				adresse.setRue(rs.getString("rue"));
				adresse.setCodePostal(rs.getString("code_postal"));
				adresse.setVille(rs.getString("ville"));
				Utilisateur utilisateur = new Utilisateur(rs.getString("pseudo"), rs.getString("nom"),
						rs.getString("prenom"), rs.getString("email"), rs.getString("telephone"), adresse,
						rs.getString("mot_de_passe"), rs.getInt("credit"));
				utilisateur.setNoUtilisateur(noUtilisateur);
				utilisateur.setAdmin(rs.getBoolean("administrateur"));
				utilisateur.setVip(rs.getBoolean("vip"));
				return utilisateur;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<Utilisateur> findAll() {
		try (Connection connection = ConnectionProvider.getConnection();
				Statement stmt = connection.createStatement();) {
			List<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();
			ResultSet rs = stmt.executeQuery(SELECT_ALL_UTILISATEURS);
			while (rs.next()) {
				Utilisateur utilisateur = new Utilisateur(rs.getString("pseudo"), rs.getString("nom"),
						rs.getString("prenom"), rs.getString("email"), rs.getString("telephone"),
						DaoFactory.getAdresseDao().findOne(rs.getInt("id_adresse")), rs.getString("mot_de_passe"),
						rs.getInt("credit"));
				utilisateur.setAdmin(rs.getBoolean("administrateur"));
				utilisateur.setVip(rs.getBoolean("vip"));
				utilisateurs.add(utilisateur);
			}
			return utilisateurs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void modify(Utilisateur utilisateur) {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(UPDATE_UTILISATEUR)) {
			pstmt.setString(1, utilisateur.getPseudo());
			pstmt.setString(2, utilisateur.getNom());
			pstmt.setString(3, utilisateur.getPrenom());
			pstmt.setString(4, utilisateur.getEmail());
			pstmt.setString(5, utilisateur.getTelephone());
			pstmt.setInt(6, utilisateur.getAdresse().getIdAdresse());
			pstmt.setString(7, utilisateur.getMdp());
			pstmt.setInt(8, utilisateur.getCredit());
			pstmt.setBoolean(9, utilisateur.isAdmin());
			pstmt.setBoolean(10, utilisateur.isVip());
			pstmt.setInt(11, utilisateur.getNoUtilisateur());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void remove(int noUtilisateur) {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(DELETE_UTILISATEUR)) {
			pstmt.setInt(1, noUtilisateur);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public Utilisateur findByPseudo(String query) {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(FIND_BY_PSEUDO_UTILISATEUR)) {
			pstmt.setString(1, query);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				Utilisateur utilisateur = new Utilisateur(rs.getString("pseudo"), rs.getString("nom"),
						rs.getString("prenom"), rs.getString("email"), rs.getString("telephone"),
						DaoFactory.getAdresseDao().findOne(rs.getInt("id_adresse")), rs.getString("mot_de_passe"),
						rs.getInt("credit"));
				utilisateur.setAdmin(rs.getBoolean("administrateur"));
				utilisateur.setVip(rs.getBoolean("vip"));
				return utilisateur;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Utilisateur findByEmail(String email) {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(SELECT_BY_EMAIL);) {

			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				Utilisateur utilisateur = new Utilisateur();
				utilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
				utilisateur.setPseudo(rs.getString("pseudo"));
				utilisateur.setNom(rs.getString("nom"));
				utilisateur.setPrenom(rs.getString("prenom"));
				utilisateur.setEmail(rs.getString("email"));
				utilisateur.setTelephone(rs.getString("telephone"));
				utilisateur.setAdresse(AdresseManager.getInstance().recupUneAdresse(rs.getInt("id_adresse")));
				utilisateur.setMdp(rs.getString("mot_de_passe"));
				utilisateur.setCredit(rs.getInt("credit"));
//				utilisateur.setAdmin(rs.getBoolean("admin"));
//				utilisateur.setVip(rs.getBoolean("vip"));
				return utilisateur;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
