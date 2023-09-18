package fr.eni.ecole.encheres.bo;

public class Adresse {
	private String rue;
	private String ville;
	private int codePostal;



	public Adresse() {
	}

	public Adresse(String rue, String ville, int codePostal) {
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

	public int getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(int codePostal) {
		this.codePostal = codePostal;
	}
	
	@Override
	public String toString() {
		return "Adresse [rue=" + rue + ", ville=" + ville + ", codePostal=" + codePostal + "]";
	}

}
