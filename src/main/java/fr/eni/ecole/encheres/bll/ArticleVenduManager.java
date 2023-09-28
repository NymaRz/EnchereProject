package fr.eni.ecole.encheres.bll;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import fr.eni.ecole.encheres.bo.ArticleVendu;
import fr.eni.ecole.encheres.bo.Utilisateur;
import fr.eni.ecole.encheres.dal.ArticleVenduDao;
import fr.eni.ecole.encheres.dal.DaoFactory;

public class ArticleVenduManager {
	// Début Singleton
	private static ArticleVenduManager instance;

	private ArticleVenduManager() {
		// Constructeur privé pour empêcher l'instanciation directe depuis l'extérieur.
	}

	public static ArticleVenduManager getInstance() {
		if (instance == null) {
			instance = new ArticleVenduManager();
		}
		return instance;
	}
	// Fin Singleton

	private ArticleVenduDao articleVenduDao = DaoFactory.getArticleVenduDao();

	// Début de la logique métier

	public ArticleVendu recupUnArticleVendu(int noArticle) {
		return articleVenduDao.findOne(noArticle);
	}

	public ArticleVendu recupArticleVenduEnchereNC(int noArticle) {
		ArticleVendu articleVendu = articleVenduDao.findOne(noArticle);
		if (articleVendu.getDateDebutEncheres().isAfter(LocalDate.now()))
			return articleVendu;
		return null;

	}

	public List<ArticleVendu> recupTousLesArticlesVendus() {
		return articleVenduDao.findAll();
	}

	public void modifierUnArticleVendu(ArticleVendu articleVendu) {
		// Ajoutez ici la logique de validation des données si nécessaire.
		articleVenduDao.modify(articleVendu);
	}

	public void modifierGagnantArticleVendu(ArticleVendu articleVendu) {
		// Ajoutez ici la logique de validation des données si nécessaire.
		articleVenduDao.modifyGagnant(articleVendu);
	}

	public void ajouterUnArticleVendu(ArticleVendu articleVendu) {
		// Ajoutez ici la logique de validation des données si nécessaire.
		articleVenduDao.save(articleVendu);
	}

	public void supprimerUnArticleVendu(int id) {
		articleVenduDao.remove(id);
	}

	public List<ArticleVendu> rechercheUnArticleVendu(String query) {
		return articleVenduDao.findByName(query);
	}

//tri par catégorie
	public List<ArticleVendu> findArticlesVendusByCategorie(int noCategorie) {
		System.out.println("findArticlesVendusByCategorie");
		return articleVenduDao.recupTousLEsArticlesDeCategorie(noCategorie);
	}

//tri par catégorie + query
	public List<ArticleVendu> rechercherUnArticleVenduByCategorie(int noCategorie, String query) {
		return articleVenduDao.rechercheArticlesDeCategorieByName(noCategorie, query);
	}

	// seulement les articles dont la date d'ouverture aux enchères est ouverte, et
	// la date de fin d'enchère n'est pas terminée

	public List<ArticleVendu> recupArticlesVendusSelonEtatVente(String etatVente) {
		return articleVenduDao.recupTousLesArticlesSelonEtatVente(etatVente);
	}

	// idem mais avec query
	public List<ArticleVendu> recupUnArticleVenduEncheresOuvertes(String query) {
		List<ArticleVendu> articlesVendus = ArticleVenduManager.getInstance().rechercheUnArticleVendu(query);
		List<ArticleVendu> articlesVendusOuvertsAuxEncheres = new ArrayList<ArticleVendu>();

		for (ArticleVendu articleVendu : articlesVendus) {
			if (articleVendu.getEtatVente().equals("v"))
				articlesVendusOuvertsAuxEncheres.add(articleVendu);
		}
		return articlesVendusOuvertsAuxEncheres;
	}

	public ArticleVendu recupParEnchereLaPlusHaute(ArticleVendu articleVendu) {
		return articleVenduDao.recupParEnchereLaPlusHaute(articleVendu);
	}

	// articles d'une catégorie dont les enchères sont ouvertes
	public List<ArticleVendu> recupArticlesCategorieEO(int noCategorie) {
		List<ArticleVendu> articlesVendus = ArticleVenduManager.getInstance()
				.findArticlesVendusByCategorie(noCategorie);

		List<ArticleVendu> articlesVendusOuvertsAuxEncheres = new ArrayList<ArticleVendu>();

		for (ArticleVendu articleVendu : articlesVendus) {

			if (articleVendu.getEtatVente().equals("v"))
				articlesVendusOuvertsAuxEncheres.add(articleVendu);
		}
		return articlesVendusOuvertsAuxEncheres;
	}

	// idem mais avec query
	public List<ArticleVendu> recupUnArticleVenduByCategorieEO(int noCategorie, String query) {
		List<ArticleVendu> articlesVendus = ArticleVenduManager.getInstance()
				.rechercherUnArticleVenduByCategorie(noCategorie, query);
		List<ArticleVendu> articlesVendusOuvertsAuxEncheres = new ArrayList<ArticleVendu>();

		for (ArticleVendu articleVendu : articlesVendus) {
			if (articleVendu.getEtatVente().equals("v"))
				articlesVendusOuvertsAuxEncheres.add(articleVendu);
		}
		return articlesVendusOuvertsAuxEncheres;
	}

	public void updateAllArticles() {

		List<ArticleVendu> articles = ArticleVenduManager.getInstance().recupTousLesArticlesVendus();
		for (ArticleVendu articleVendu : articles) {
			if (articleVendu.getDateDebutEncheres().isAfter(LocalDate.now())
					&& !articleVendu.getEtatVente().equalsIgnoreCase("av")) {
				articleVendu.setEtatVente("av");
				ArticleVenduManager.getInstance().modifierUnArticleVendu(articleVendu);
			}
			if ((articleVendu.getDateDebutEncheres().isBefore(LocalDate.now())
					|| articleVendu.getDateDebutEncheres().isEqual(LocalDate.now()))
					&& articleVendu.getDateFinEncheres().isAfter(LocalDate.now())
					&& !articleVendu.getEtatVente().equalsIgnoreCase("v")) {
				articleVendu.setEtatVente("v");
				ArticleVenduManager.getInstance().modifierUnArticleVendu(articleVendu);
			}
			if (articleVendu.getDateFinEncheres().isBefore(LocalDate.now())
					&& !articleVendu.getEtatVente().equalsIgnoreCase("vf")) {
				articleVendu.setEtatVente("vf");

				ArticleVenduManager.getInstance().modifierUnArticleVendu(articleVendu);
			}

		}

	}

	public List<ArticleVendu> recupAndUpdateAllGagnants() {

		List<ArticleVendu> articles = ArticleVenduManager.getInstance().recupTousLesArticlesVendus();
		List<ArticleVendu> articlesAvecGagnant = new ArrayList<ArticleVendu>();
		for (ArticleVendu article : articles) {
			System.out.println("entrée boucle for");
			ArticleVendu articleVendu = articleVenduDao.recupParEnchereLaPlusHaute(article);
			if (articleVendu != null && articleVendu.getGagnant() != null) {
				int nvPrix = EnchereManager.getInstance().recupEnchereLaPlusHaute(articleVendu).getMontant_enchere();
				articleVendu.setPrixVente(nvPrix);
				ArticleVenduManager.getInstance().modifierUnArticleVendu(articleVendu);

				ArticleVenduManager.getInstance().modifierGagnantArticleVendu(articleVendu);
				System.out.println("gagnant :" + articleVendu.getGagnant().getPseudo());
				articlesAvecGagnant.add(articleVendu);

			}

		}

		return articlesAvecGagnant;
	}

//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
	public List<ArticleVendu> recupAndUpdateAllGagnantsOfCategorie(int noCategorie) {
		System.out.println("i also work");
		List<ArticleVendu> articles = ArticleVenduManager.getInstance().recupTousLesArticlesVendus();
		List<ArticleVendu> articlesAvecGagnant = new ArrayList<ArticleVendu>();
		for (ArticleVendu article : articles) {

			ArticleVendu articleVendu = articleVenduDao.recupParEnchereLaPlusHaute(article);
			if (articleVendu != null && articleVendu.getGagnant() != null
					&& articleVendu.getCategorieArticle().getNoCategorie() == noCategorie) {

				ArticleVenduManager.getInstance().modifierGagnantArticleVendu(articleVendu);
				System.out.println("gagnant :" + articleVendu.getGagnant().getPseudo());
				articlesAvecGagnant.add(articleVendu);

			}

		}

		System.out.println(articlesAvecGagnant);
		return articlesAvecGagnant;

	}

	public List<ArticleVendu> recupAndUpdateONEArticleWithGagnant(String q) {

		List<ArticleVendu> articles = ArticleVenduManager.getInstance().recupTousLesArticlesVendus();
		List<ArticleVendu> articlesAvecGagnant = new ArrayList<ArticleVendu>();
		for (ArticleVendu article : articles) {

			ArticleVendu articleVendu = articleVenduDao.recupUnArticleParEnchereLaPlusHaute(article, q);
			if (articleVendu != null && articleVendu.getGagnant() != null) {

				ArticleVenduManager.getInstance().modifierGagnantArticleVendu(articleVendu);
				System.out.println("gagnant :" + articleVendu.getGagnant().getPseudo());
				articlesAvecGagnant.add(articleVendu);

			}

		}

		System.out.println(articlesAvecGagnant);
		return articlesAvecGagnant;
	}

	public List<ArticleVendu> recupAndUpdateONEArticleWithGagnantOFCATEGORIE(int noCategorie, String q) {

		List<ArticleVendu> articles = ArticleVenduManager.getInstance().recupTousLesArticlesVendus();
		List<ArticleVendu> articlesAvecGagnant = new ArrayList<ArticleVendu>();
		for (ArticleVendu article : articles) {
			System.out.println("entrée boucle for");
			ArticleVendu articleVendu = articleVenduDao.recupUnArticleParEnchereLaPlusHauteEtCategorie(article,
					noCategorie, q);
			if (articleVendu != null && articleVendu.getGagnant() != null) {

				ArticleVenduManager.getInstance().modifierGagnantArticleVendu(articleVendu);
				System.out.println("gagnant :" + articleVendu.getGagnant().getPseudo());
				articlesAvecGagnant.add(articleVendu);

			}

		}

		System.out.println(articlesAvecGagnant);
		return articlesAvecGagnant;
	}

//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	

	public List<ArticleVendu> trierListeParCategorie(List<ArticleVendu> articles, int noCategorie) {
		List<ArticleVendu> articlesVendusCategorie = ArticleVenduManager.getInstance()
				.findArticlesVendusByCategorie(noCategorie);
		List<ArticleVendu> articlesVendusEtatVente = articles;
		List<ArticleVendu> articlesTries = new ArrayList<ArticleVendu>();
		for (ArticleVendu articleVendu : articlesVendusEtatVente) {
			for (ArticleVendu articleVenduCategorie : articlesVendusCategorie) {
				if (articleVendu.equals(articleVenduCategorie)) {
					articlesTries.add(articleVendu);
				}
			}
		}

		return articlesTries;
	}

	// retrouver les articles sur lequel l'utilisateur a enchéri
	public List<ArticleVendu> recupArticlesEncheresParUtilisateur(Utilisateur utilisateur) {

		return articleVenduDao.recupArticlesEncheresParUtilisateur(utilisateur);
	}

	public List<ArticleVendu> recupUnArticleEncheriParutilisateurEtCategorie(Utilisateur utilisateur, int noCategorie,
			String q) {
		return articleVenduDao.recupUnArticleEncheriParutilisateurEtCategorie(utilisateur, noCategorie, q);
	}

	public List<ArticleVendu> recupArticlesEncherisParUtilisateurEtCategorie(Utilisateur utilisateur, int noCategorie) {
		return articleVenduDao.recupArticlesEncherisParUtilisateurEtCategorie(utilisateur, noCategorie);
	}

	public List<ArticleVendu> recupUnArticleEncheriParUtilisateur(Utilisateur utilisateur, String q) {
		return articleVenduDao.recupUnArticleEncheriParUtilisateur(utilisateur, q);
	}

	public ArticleVendu recupArticleEncheriParUtilisateurParEnchereMax(Utilisateur utilisateur,
			ArticleVendu articleVendu) {
		return articleVenduDao.recupParEnchereLaPlusHauteParUtilisateur(utilisateur, articleVendu);
	}

	// fin retrouver les articles sur lequel l'utilisateur a enchéri
//
//	
//	
	// retrouver les ventes en cours de l'utilisateur
	public List<ArticleVendu> recupArticlesVendusParUtilisateurSelonEtatVente(Utilisateur utilisateur,
			String etatVente) {

		return articleVenduDao.recupArticlesVendusParUtilisateurSelonEtatVente(utilisateur, etatVente);
	}

	public List<ArticleVendu> recupUnArticlesVendusParUtilisateurSelonEtatVenteEtCategorie(Utilisateur utilisateur,
			int noCategorie, String q, String etatVente) {
		return articleVenduDao.recupUnArticlesVendusParUtilisateurSelonEtatVenteEtCategorie(utilisateur, noCategorie, q,
				etatVente);
	}

	public List<ArticleVendu> recupArticlesVendusParUtilisateurSelonEtatVenteEtCategorie(Utilisateur utilisateur,
			int noCategorie, String etatVente) {
		return articleVenduDao.recupArticlesVendusParUtilisateurSelonEtatVenteEtCategorie(utilisateur, noCategorie,
				etatVente);
	}

	public List<ArticleVendu> recupUnArticlesVendusParUtilisateurSelonEtatVente(Utilisateur utilisateur, String q,
			String etatVente) {
		return articleVenduDao.recupUnArticlesVendusParUtilisateurSelonEtatVente(utilisateur, q, etatVente);
	}
	// fin retrouver les ventes en cours de l'utilisateur

	public ArticleVendu recupDernierArticleRemporte(int noUtilisateur) {
		List<ArticleVendu> articlesVendus = recupTousLesArticlesVendus();
		ArticleVendu dernierArticleRemporte = null;
		for (ArticleVendu article : articlesVendus) {
			// Vérifiez si l'utilisateur a remporté l'enchère
			if (article.getUtilisateur().getNoUtilisateur() == noUtilisateur && article.getEtatVente().equals("vf")) {
				// Si c'est la première enchère remportée trouvée ou si elle est plus récente
				if (dernierArticleRemporte == null
						|| article.getDateFinEncheres().isAfter(dernierArticleRemporte.getDateFinEncheres())) {
					dernierArticleRemporte = article;
				}
			}
		}
		return dernierArticleRemporte;
	}

	// récupérer les articles remportés par un utilisateur

	public List<ArticleVendu> recupEncheresRemporteesParUtilisateur(Utilisateur utilisateur) {
		List<ArticleVendu> articlesAvecGagnant = ArticleVenduManager.getInstance().recupAndUpdateAllGagnants();
		List<ArticleVendu> articles = new ArrayList<ArticleVendu>();
		for (ArticleVendu articleVendu : articlesAvecGagnant) {
			if (articleVendu.getGagnant().getNoUtilisateur() == utilisateur.getNoUtilisateur()) {
				articles.add(articleVendu);
			}
		}
		return articles;
	}

	public List<ArticleVendu> recupEncheresRemporteesParUtilisateurParCategorie(Utilisateur utilisateur,
			int noCategorie) {
		List<ArticleVendu> articlesAvecGagnant = ArticleVenduManager.getInstance()
				.recupAndUpdateAllGagnantsOfCategorie(noCategorie);
		List<ArticleVendu> articles = new ArrayList<ArticleVendu>();
		for (ArticleVendu articleVendu : articlesAvecGagnant) {
			if (articleVendu.getGagnant().getNoUtilisateur() == utilisateur.getNoUtilisateur()) {
				articles.add(articleVendu);
			}
		}
		return articles;
	}

	public List<ArticleVendu> recupUneEnchereRemporteeParUtilisateur(Utilisateur utilisateur, String q) {
		List<ArticleVendu> articlesAvecGagnant = ArticleVenduManager.getInstance()
				.recupAndUpdateONEArticleWithGagnant(q);
		List<ArticleVendu> articles = new ArrayList<ArticleVendu>();
		for (ArticleVendu articleVendu : articlesAvecGagnant) {
			if (articleVendu.getGagnant().getNoUtilisateur() == utilisateur.getNoUtilisateur()) {
				articles.add(articleVendu);
			}
		}
		return articles;
	}

	public List<ArticleVendu> recupUneEnchereRemporteeParUtilisateurParCategorie(Utilisateur utilisateur,
			int noCategorie, String q) {
		List<ArticleVendu> articlesAvecGagnant = ArticleVenduManager.getInstance()
				.recupAndUpdateONEArticleWithGagnantOFCATEGORIE(noCategorie, q);
		List<ArticleVendu> articles = new ArrayList<ArticleVendu>();
		for (ArticleVendu articleVendu : articlesAvecGagnant) {
			if (articleVendu.getGagnant().getNoUtilisateur() == utilisateur.getNoUtilisateur()) {
				articles.add(articleVendu);
			}
		}
		return articles;
	}

	public ArrayList<ArticleVendu> removeDuplicates(List<ArticleVendu> articlesVendusSansTri) {
		// Utilisez un HashSet pour stocker les éléments uniques
		HashSet<ArticleVendu> ensembleUnique = new HashSet<>();

		// Créez une nouvelle liste pour stocker les éléments sans doublons
		ArrayList<ArticleVendu> listeSansDoublons = new ArrayList<ArticleVendu>();

		// Parcourez l'ArrayList d'origine
		for (ArticleVendu articleVendu : articlesVendusSansTri) {
			// Si l'élément n'est pas déjà dans l'ensemble unique, ajoutez-le à la nouvelle
			// liste
			if (ensembleUnique.add(articleVendu)) {
				listeSansDoublons.add(articleVendu);
			}
		}

		return listeSansDoublons;
	}

	// Fin de la logique métier
}
