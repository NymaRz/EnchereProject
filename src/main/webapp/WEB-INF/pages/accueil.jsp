<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/fragments/header.jspf" %> 

<main>
<%@include file="/WEB-INF/fragments/recherche.jspf"%>


<%List<ArticleVendu> articlesVendus = (List<ArticleVendu>)request.getAttribute("articlesVendus"); %>



<div id ="container-secondaire">

<c:forEach var="articleVendu" items="${ articlesVendus }">
<div id="article-vendu">
<div id="miniature-article" style="background: linear-gradient(to left bottom, rgba(0, 0, 0, 0), #3A444A), url('${articleVendu.jaquette}') center/cover no-repeat;">
			<span style="widht:100px" class="bouton-miniature prix-article">${articleVendu.prixVente } €</span>
			<a href="" style="widht:100px" class="bouton-miniature actionbouton">${articleVendu.dateFinEncheres }</a>
</div>
			<h4 class="titre-miniature">${articleVendu.nomArticle }</h4>
<p>Vendu par <a id="link-vendeur" href="/Profil.jsp?id=${articleVendu.utilisateur.getNoUtilisateur() }">${articleVendu.utilisateur.getPseudo() }</a></p>
</div>
		</c:forEach> </div>
		
		
		<div id="container-primaire">
		
		</div>
		
		
</main>


<%@ include file="/WEB-INF/fragments/footer.jspf" %>