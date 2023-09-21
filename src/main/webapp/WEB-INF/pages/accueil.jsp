<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/fragments/headerAccueilNONConnecte.jspf" %>

<main>
<%@include file="/WEB-INF/fragments/recherche.jspf"%>


<%List<ArticleVendu> articlesVendus = (List<ArticleVendu>)request.getAttribute("articlesVendus"); %>


</main>

<c:forEach var="articleVendu" items="${ articlesVendus }">
<div>
			<h2>${articleVendu.nomArticle }</h2>
			<p>Prix : ${articleVendu.prixVente }</p>
			<p>Fin de l'enchère : ${articleVendu.dateFinEncheres }</p>
			<p>Vendeur.se : ${articleVendu.utilisateur.getPseudo() }</p>

</div>
		</c:forEach> 


<%@ include file="/WEB-INF/fragments/footer.jspf" %>