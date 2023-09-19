package fr.eni.ecole.bll;

import java.util.List;

import fr.eni.ecole.encheres.bo.ArticleVendu;
import fr.eni.ecole.encheres.dal.DaoFactory;
import fr.eni.ecole.encheres.dal.articleVenduDao;

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

    private ArticleVenduDAO articleVenduDao = DaoFactory.getArticleVenduDAO();

    // Début de la logique métier

    public ArticleVendu recupUnJeu(int id) {
        return articleVenduDao.findOne(id);
    }

    public List<ArticleVendu> recupTousLesJeux() {
        return articleVenduDao.findAll();
    }

    public void ajouterUnJeu(ArticleVendu articleVendu) {
        // Ajoutez ici la logique de validation des données si nécessaire.
        articleVenduDao.save(articleVendu);
    }

    public void modifierUnJeu(ArticleVendu articleVendu) {
        // Ajoutez ici la logique de validation des données si nécessaire.
        articleVenduDao.update(articleVendu);
    }

    public void supprimerUnJeu(int id) {
        articleVenduDao.remove(id);
    }

    public List<ArticleVendu> rechercheUnJeu(String query) {
        return articleVenduDao.findByName(query);
    }

    // Fin de la logique métier
}
