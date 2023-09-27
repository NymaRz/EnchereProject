package fr.eni.ecole.encheres.bo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ArticleVendu {
	private int noArticle;
	private String nomArticle;
	private String description;
	private LocalDate dateDebutEncheres;
	private LocalDate dateFinEncheres;
	private int miseAPrix;
	private int prixVente;
	private String etatVente;
	private Categorie categorieArticle;
	private Retrait lieuRetrait;
	private int enchereMin;
	private Utilisateur utilisateur;
	private List<Enchere> encheres = new ArrayList<Enchere>();
	private String jaquette;
	private Utilisateur gagnant;

	public ArticleVendu() {
	}

	// début de la mise en enchère au jour même, sans incrémentation personnalisée
	public ArticleVendu(int noArticle, String nomArticle, String description, LocalDate dateFinEncheres, int miseAPrix,
			Categorie categorieArticle, Retrait lieuRetrait, Utilisateur utilisateur, String jaquette) {
		this.noArticle = noArticle;
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateFinEncheres = dateFinEncheres;
		this.miseAPrix = miseAPrix;
		this.setCategorieArticle(categorieArticle);
		this.setLieuRetrait(lieuRetrait);
		this.dateDebutEncheres = LocalDate.now();
		this.setJaquette(jaquette);
		this.utilisateur = utilisateur;
		this.enchereMin = 100;
		this.getCategorieArticle().addArticleToCategorie(this); // ajoute cet article à la liste des articles de cette
																// catégorie
		this.getLieuRetrait().addArticleToRetrait(this); // ajoute cet article à la liste des articles de ce lieu de
															// retrait
		this.getUtilisateur().addArticleToUser(this); // ajoute l'article à la liste d'articles à vendre par cet
														// utilisateur.

	}

	public String getProfilVendeurURL() {
		// Logic pour générer l'URL du profil du vendeur
		return "/Profil?id=" + this.utilisateur.getNoUtilisateur();
	}

	// début de la mise en enchère au jour même, avec incrémentation d'enchère
	// personnalisée
	public ArticleVendu(int noArticle, String nomArticle, String description, LocalDate dateFinEncheres, int miseAPrix,
			Categorie categorieArticle, Retrait lieuRetrait, Utilisateur utilisateur, int enchereMin, String jaquette) {
		this(noArticle, nomArticle, description, dateFinEncheres, miseAPrix, categorieArticle, lieuRetrait, utilisateur,
				jaquette);
		this.getCategorieArticle().addArticleToCategorie(this);
		this.setEnchereMin(enchereMin);

	}

	// début de la mise en enchère à la date indiquée, sans incrémentation d'enchère
	// personnalisée
	public ArticleVendu(int noArticle, String nomArticle, String description, LocalDate dateDebutEncheres,
			LocalDate dateFinEncheres, int miseAPrix, Categorie categorieArticle, Retrait lieuRetrait,
			Utilisateur utilisateur, String jaquette) {
		this.noArticle = noArticle;
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebutEncheres = dateDebutEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.miseAPrix = miseAPrix;
		this.setCategorieArticle(categorieArticle);
		this.setLieuRetrait(lieuRetrait);
		this.utilisateur = utilisateur;
		this.setJaquette(jaquette);
		this.enchereMin = 100;
		this.getCategorieArticle().addArticleToCategorie(this);
		this.getLieuRetrait().addArticleToRetrait(this);
		this.getUtilisateur().addArticleToUser(this);
	}

	// début de la mise en enchère à la date indiquée, avec incrémentation d'enchère
	// personnalisée
	public ArticleVendu(int noArticle, String nomArticle, String description, LocalDate dateDebutEncheres,
			LocalDate dateFinEncheres, int miseAPrix, Categorie categorieArticle, Retrait lieuRetrait,
			Utilisateur utilisateur, int enchereMin, String jaquette) {
		this(noArticle, nomArticle, description, dateDebutEncheres, dateFinEncheres, miseAPrix, categorieArticle,
				lieuRetrait, utilisateur, jaquette);
		this.setEnchereMin(enchereMin);
		this.getCategorieArticle().addArticleToCategorie(this);
	}

	public int getnoArticle() {
		return noArticle;
	}

	public void setnoArticle(int noArticle) {
		this.noArticle = noArticle;
	}

	public String getNomArticle() {
		return nomArticle;
	}

	public void setNomArticle(String nomArticle) {
		this.nomArticle = nomArticle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getDateDebutEncheres() {
		return dateDebutEncheres;
	}

	public void setDateDebutEncheres(LocalDate dateDebutEncheres) {
		this.dateDebutEncheres = dateDebutEncheres;
	}

	public LocalDate getDateFinEncheres() {
		return dateFinEncheres;
	}

	public void setDateFinEncheres(LocalDate dateFinEncheres) {
		this.dateFinEncheres = dateFinEncheres;
	}

	public int getMiseAPrix() {
		return miseAPrix;
	}

	public void setMiseAPrix(int miseAPrix) {
		this.miseAPrix = miseAPrix;
	}

	public int getPrixVente() {
		return prixVente;
	}

	public void setPrixVente(int prixVente) {
		this.prixVente = prixVente;
	}

	public String getEtatVente() {
		return etatVente;
	}

	public void setEtatVente(String etatVente) {
		this.etatVente = etatVente;
	}

	public int getEnchereMin() {
		return enchereMin;
	}

	public void setEnchereMin(int enchereMin) {
		this.enchereMin = enchereMin;
	}

	public Categorie getCategorieArticle() {
		return categorieArticle;
	}

	public void setCategorieArticle(Categorie categorieArticle) {
		this.categorieArticle = categorieArticle;
	}

	public Retrait getLieuRetrait() {
		return lieuRetrait;
	}

	public void setLieuRetrait(Retrait lieuRetrait) {
		this.lieuRetrait = lieuRetrait;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public String getJaquette() {
		return jaquette;
	}

	public void setJaquette(String jaquette) {
		this.jaquette = jaquette;
	}

	public Utilisateur getGagnant() {
		return gagnant;
	}

	public void setGagnant(Utilisateur gagnant) {
		this.gagnant = gagnant;
	}

	public List<Enchere> getEncheres() {
		return encheres;
	}

	public void setEncheres(List<Enchere> encheres) {
		this.encheres = encheres;
	}

	public void addEnchereToArticle(Enchere enchere) {
		this.getEncheres().add(enchere);
	}

	@Override
	public String toString() {
		return "ArticleVendu [noArticle=" + noArticle + ", nomArticle=" + nomArticle + ", description=" + description
				+ ", dateDebutEncheres=" + dateDebutEncheres + ", dateFinEncheres=" + dateFinEncheres + ", miseAPrix="
				+ miseAPrix + ", prixVente=" + prixVente + ", etatVente=" + etatVente + ", categorieArticle="
				+ categorieArticle + ", lieuRetrait=" + lieuRetrait + ", enchereMin=" + enchereMin + ", utilisateur="
				+ utilisateur + ", jaquette=" + jaquette + ", gagnant=" + gagnant + "]";
	}

}
