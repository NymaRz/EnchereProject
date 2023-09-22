<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/fragments/headerAccueilNONConnecte.jspf" %>

<main>
<%@include file="/WEB-INF/fragments/recherche.jspf"%>


<%List<ArticleVendu> articlesVendus = (List<ArticleVendu>)request.getAttribute("articlesVendus"); %>


</main>


<!-- include recherche
include critères plus poussés (liste achats, liste vente) -->

<div id ="container-secondaire">
<c:forEach var="articleVendu" items="${ articlesVendus }">
<div id="article-vendu">
			<h2>${articleVendu.nomArticle }</h2>
			<p>Prix : ${articleVendu.prixVente } crédits</p>
			<p>Prix : ${articleVendu.description } description</p>
			<p>Fin de l'enchère : ${articleVendu.dateFinEncheres }</p>
			<p>Vendeur.se : ${articleVendu.utilisateur.getPseudo() }</p>

</div>
		</c:forEach> </div>


<%@ include file="/WEB-INF/fragments/footer.jspf" %>