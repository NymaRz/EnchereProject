package fr.eni.ecole.encheres.bo;

import java.time.LocalDate;

public class Enchere {
	private int montant_enchere;
	private Utilisateur acquereur;
	private ArticleVendu articleEncheri;
	private LocalDate dateEnchere;
	private int id_enchere;

	public Enchere() {
	}

	public Enchere(Utilisateur acquereur, ArticleVendu articleEncheri, int montant_enchere) {

		this.setMontant_enchere(montant_enchere);
		this.getAcquereur().addEnchereToUser(this);
		this.getArticleEncheri().addEnchereToArticle(this);
	}

	public Enchere(LocalDate dateEnchere, int montant_enchere, Utilisateur acquereur, ArticleVendu articleEncheri) {

		this.setDateEnchere(dateEnchere);
		this.setMontant_enchere(montant_enchere);
		this.setAcquereur(acquereur);
		this.setArticleEncheri(articleEncheri);
		this.getAcquereur().addEnchereToUser(this);
		this.getArticleEncheri().addEnchereToArticle(this);
	}

	public Enchere(int idEnchere) {
		this.setId_enchere(idEnchere);
	}

	public LocalDate getDateEnchere() {
		return dateEnchere;
	}

	public void setDateEnchere(LocalDate dateEnchere) {
		this.dateEnchere = dateEnchere;
	}

	public int getMontant_enchere() {
		return montant_enchere;
	}

	public void setMontant_enchere(int montant_enchere) {
		this.montant_enchere = montant_enchere;
	}

	public Utilisateur getAcquereur() {
		return acquereur;
	}

	public void setAcquereur(Utilisateur acquereur) {
		this.acquereur = acquereur;
	}

	public ArticleVendu getArticleEncheri() {
		return articleEncheri;
	}

	public void setArticleEncheri(ArticleVendu articleEncheri) {
		this.articleEncheri = articleEncheri;
	}

	public int getId_enchere() {
		return id_enchere;
	}

	public void setId_enchere(int id_enchere) {
		this.id_enchere = id_enchere;
	}

	@Override
	public String toString() {
		return "Enchere [montant_enchere=" + montant_enchere + ", acquereur=" + acquereur + ", articleEncheri="
				+ articleEncheri + ", dateEnchere=" + dateEnchere + ", id_enchere=" + id_enchere + "]";
	}

}
