package fr.eni.ecole.jdbc.exception;

public class JDBCException extends Exception {

	public JDBCException(String msg) {
		super(msg);
	}
	
	
	@Override
	public String getMessage() {		
		return "ERREUR: "+super.getMessage();
	}
	
}
