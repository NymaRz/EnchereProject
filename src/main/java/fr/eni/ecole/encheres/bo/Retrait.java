package fr.eni.ecole.encheres.bo;

import java.util.ArrayList;
import java.util.List;

public class Retrait {

	private Adresse adresse;
	private List<ArticleVendu> articlesVendus = new ArrayList<ArticleVendu>();

	public Retrait() {
		super();
	}

	public Retrait(Adresse adresse) {
		super();
		this.adresse = adresse;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	public List<ArticleVendu> getArticlesVendus() {
		return articlesVendus;
	}

	public void setArticlesVendus(List<ArticleVendu> articlesVendus) {
		this.articlesVendus = articlesVendus;
	}

	@Override
	public String toString() {
		return "Retrait [adresse=" + adresse + ", articlesVendus=" + articlesVendus + "]";
	}
	
	//permet d'ajouter un article à ce point de retrait automatiquement
	public void addArticleToRetrait(ArticleVendu articleVendu) {
		this.articlesVendus.add(articleVendu);
	}

}
