<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/fragments/headerAccueilNONConnecte.jspf" %>

<main>
<%@include file="/WEB-INF/fragments/recherche.jspf"%>


<%List<ArticleVendu> articlesVendus = (List<ArticleVendu>)request.getAttribute("articlesVendus"); %>



<div id ="container-secondaire">

<c:forEach var="articleVendu" items="${ articlesVendus }">
<div id="article-vendu" style="background-image:url'${articleVendu.prixVente }'0px 0px no-repeat;background-size:cover">
<div id="miniature-article">
			<span style="widht:100px" class="bouton-miniature prix-article">${articleVendu.prixVente } €</span>
			<a href="" style="widht:100px" class="bouton-miniature actionbouton">${articleVendu.dateFinEncheres }</a>
</div>
			<h4 class="titre-miniature">${articleVendu.nomArticle }</h4>
			<p>Vendu par <a id="link-vendeur" href="">${articleVendu.utilisateur.getPseudo() }</a></p>
</div>
		</c:forEach> </div>
		
		
		<div id="container-primaire">
		
		</div>
		
		
</main>


<%@ include file="/WEB-INF/fragments/footer.jspf" %>