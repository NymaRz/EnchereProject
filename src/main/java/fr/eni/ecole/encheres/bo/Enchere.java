package fr.eni.ecole.encheres.bo;

import java.time.LocalDate;

public class Enchere {
	private LocalDate dateEnchere;
	private int montant_enchere;
	private Utilisateur acquereur;
	private ArticleVendu articleEncheri;

//	public Enchere() {
//	}
	public Enchere(LocalDate dateEnchere, int montant_enchere, Utilisateur acquereur, ArticleVendu articleEncheri) {

		this.setDateEnchere(dateEnchere);
		this.setMontant_enchere(montant_enchere);
		this.setAcquereur(acquereur);
		this.setArticleEncheri(articleEncheri);
		this.getAcquereur().addEnchereToUser(this);
		this.getArticleEncheri().addEnchereToArticle(this);
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

}
