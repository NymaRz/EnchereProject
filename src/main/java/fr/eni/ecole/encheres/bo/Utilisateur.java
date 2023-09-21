package fr.eni.ecole.encheres.bo;

import java.util.ArrayList;
import java.util.List;

public class Utilisateur {

	private int noUtilisateur;
	private String pseudo;
	private String nom;
	private String prenom;
	private String email;
	private String telephone;
	private Adresse adresse;
	private String mdp;
	private int credit;
	private boolean admin = false;
	private boolean vip = false;
	private List<ArticleVendu> articlesAVendre = new ArrayList<ArticleVendu>();
	private List<Enchere> encheres = new ArrayList<Enchere>();

	public Utilisateur() {

	}

	public Utilisateur(int noUtilisateur, String pseudo, String nom, String prenom, String email, String telephone,
			Adresse adresse, String mdp) {
		super();
		this.noUtilisateur = noUtilisateur;
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.adresse = adresse;
		this.mdp = mdp;
		this.credit = 0;
	}

	public Utilisateur(String pseudo, String nom, String prenom, String email, String telephone, Adresse adresse,
			String mdp) {
		super();
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.adresse = adresse;
		this.mdp = mdp;
	}

	public Utilisateur(String pseudo, String nom, String prenom, String email, String telephone, Adresse adresse,
			String mdp, int credit) {
		super();
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.adresse = adresse;
		this.mdp = mdp;
		this.credit = credit;
	}

	public Utilisateur(int noUtilisateur, String pseudo, String nom, String prenom, String email, String telephone,
			Adresse adresse, String mdp, int credit) {
		super();
		this.noUtilisateur = noUtilisateur;
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.adresse = adresse;
		this.mdp = mdp;
		this.credit = credit;
	}

	public int getNoUtilisateur() {
		return noUtilisateur;
	}

	public Utilisateur(String email, String mdp) {
		super();
		this.mdp = mdp;
		this.email = email;
	}

	public void setNoUtilisateur(int noUtilisateur) {
		this.noUtilisateur = noUtilisateur;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	public String getMdp() {
		return mdp;
	}

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public boolean isVip() {
		return vip;
	}

	public void setVip(boolean vip) {
		this.vip = vip;
	}

	public List<ArticleVendu> getArticlesAVendre() {
		return articlesAVendre;
	}

	public void setArticlesAVendre(List<ArticleVendu> articlesAVendre) {
		this.articlesAVendre = articlesAVendre;
	}

	public List<Enchere> getEncheres() {
		return encheres;
	}

	public void setEncheres(List<Enchere> encheres) {
		this.encheres = encheres;
	}

	public void addArticleToUser(ArticleVendu articleVendu) {
		this.getArticlesAVendre().add(articleVendu);
	}

	public void addEnchereToUser(Enchere enchere) {
		this.getEncheres().add(enchere);
	}

	@Override
	public String toString() {
		return "Utilisateur [noUtilisateur=" + noUtilisateur + ", pseudo=" + pseudo + ", nom=" + nom + ", prenom="
				+ prenom + ", email=" + email + ", telephone=" + telephone + ", adresse=" + adresse + ", mdp=" + mdp
				+ ", credit=" + credit + ", admin=" + admin + ", vip=" + vip + ", articlesAVendre=" + articlesAVendre
				+ ", encheres=" + encheres + "]";
	}

}
