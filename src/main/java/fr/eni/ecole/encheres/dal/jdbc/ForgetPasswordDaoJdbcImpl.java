package fr.eni.ecole.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.eni.ecole.encheres.bo.ForgetPassword;
import fr.eni.ecole.encheres.bo.Utilisateur;
import fr.eni.ecole.encheres.dal.ForgetPasswordDao;

public class ForgetPasswordDaoJdbcImpl implements ForgetPasswordDao {
	
	
	
	private static final String INSERT_FORGET_PASSWORD = "INSERT forget_passwords ( code,user_id ) VALUES (?,?)";
	private static final String SELECT_FOR_RESET_PASSWORD = "SELECT TOP(1) *\r\n"
			+ "FROM  users u INNER JOIN forget_passwords fp\r\n"
			+ "ON u.id = fp.user_id\r\n"
			+ "WHERE u.email = ? \r\n"
			+ "ORDER BY fp.date_created DESC";

	@Override
	public void save(ForgetPassword fp) {
		try(
				Connection connection = ConnectionProvider.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(INSERT_FORGET_PASSWORD);
					){
				pstmt.setString(1, fp.getCode());
				pstmt.setInt(2, fp.getUser().getNoUtilisateur());
				pstmt.executeUpdate();
				
			}catch(SQLException e) {
				e.printStackTrace();
			}
	}

	@Override
	public ForgetPassword resetPassword(String email) {
		try(
				Connection connection = ConnectionProvider.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(SELECT_FOR_RESET_PASSWORD);
					){
				pstmt.setString(1, email);
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) {
					return new ForgetPassword(rs.getInt("id"), 
							rs.getString("code"), 
							new Utilisateur(), // ?????????????????????????????????????????????????????
							rs.getDate("date_created").toLocalDate());
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		return null;
	}

	@Override
	public void remove(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ForgetPassword findByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}
}
