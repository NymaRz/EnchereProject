package fr.eni.ecole.encheres.bo;

import java.util.ArrayList;
import java.util.List;

public class Categorie {
	private int noCategorie;
	private String libelle;
	private List<ArticleVendu> articlesOfCategorie = new ArrayList<ArticleVendu>();

	public Categorie() {
		super();
	}

	public Categorie(int noCategorie, String libelle) {
		super();
		this.noCategorie = noCategorie;
		this.libelle = libelle;
	}

	public int getNoCategorie() {
		return noCategorie;
	}

	public void setNoCategorie(int noCategorie) {
		this.noCategorie = noCategorie;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public List<ArticleVendu> getArticlesOfCategorie() {
		return articlesOfCategorie;
	}

	public void setArticlesOfCategorie(List<ArticleVendu> articlesOfCategorie) {
		this.articlesOfCategorie = articlesOfCategorie;
	}

	// pour auto-ajouter un article à la liste de cette catégorie s'il est mis dans
	// cette catégorie
	public void addArticleToCategorie(ArticleVendu articleVendu) {
		this.articlesOfCategorie.add(articleVendu);
	}
}
