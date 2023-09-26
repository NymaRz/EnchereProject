package fr.eni.ecole.encheres.bo;

public class Passport {

	private String ipv4;
	private String userAgent;
	private Utilisateur utilisateur;

	public Passport(String ipv4, String userAgent, Utilisateur utilisateur) {
		this.ipv4 = ipv4;
		this.userAgent = userAgent;
		this.utilisateur = utilisateur;
	}

	public String getIpv4() {
		return ipv4;
	}

	public void setIpv4(String ipv4) {
		this.ipv4 = ipv4;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	@Override
	public String toString() {
		return "Passport [ipv4=" + ipv4 + ", userAgent=" + userAgent + ", user=" + utilisateur + "]";
	}

}
