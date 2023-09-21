package fr.eni.ecole.encheres.bo;

public class Adresse {
	private int idAdresse;
	private String rue;
	private String codePostal;
	private String ville;



	public Adresse() {
	}

	public Adresse(String rue, String codePostal, String ville) {
		this.rue = rue;
		this.ville = ville;
		this.codePostal = codePostal;
	}
	
	

	public Adresse(int idAdresse, String rue, String codePostal, String ville) {
		this.idAdresse = idAdresse;
		this.rue = rue;
		this.ville = ville;
		this.codePostal = codePostal;
	}

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}
	
	@Override
	public String toString() {
		return "Adresse [rue=" + rue + ", ville=" + ville + ", codePostal=" + codePostal + "]";
	}

	public int getIdAdresse() {

		return idAdresse;
	}

	public void setIdAdresse(int idAdresse) {
		this.idAdresse = idAdresse;
	}

}
