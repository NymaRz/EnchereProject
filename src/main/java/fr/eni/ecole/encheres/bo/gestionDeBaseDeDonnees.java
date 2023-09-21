package fr.eni.ecole.encheres.bo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class gestionDeBaseDeDonnees {

	private static final String URL = "java:comp/env/jdbc/pool_cnx/ENCHERE_DB";
	private static final String UTILISATEUR = "no_utilisateur";
	private static final String MOT_DE_PASSE = "mot_de_passe";

	public Utilisateur getUtilisateurFromDatabase(String no_utilisateur) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Utilisateur utilisateur = null;

		try {
			// Établir une connexion à la base de données
			connection = DriverManager.getConnection(URL, UTILISATEUR, MOT_DE_PASSE);

			// Requête SQL pour récupérer l'utilisateur par nom d'utilisateur
			String sql = "SELECT * FROM UTILISATEURS WHERE no_utilisateur = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, no_utilisateur);

			// Exécutez la requête
			resultSet = preparedStatement.executeQuery();

			// Si un utilisateur correspondant est trouvé, créez un objet Utilisateur
			if (resultSet.next()) {
				utilisateur = new Utilisateur();
				utilisateur.setNoUtilisateur(0);
				utilisateur.setNom(no_utilisateur);
				utilisateur.setEmail(resultSet.getString("email"));
				// Ajoutez d'autres attributs d'utilisateur si nécessaire
			}
		} finally {
			// Fermez les ressources JDBC
			if (resultSet != null) {
				resultSet.close();
			}
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}

		return utilisateur;
	}
}
