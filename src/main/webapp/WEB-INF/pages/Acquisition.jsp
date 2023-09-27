<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/fragments/header.jspf"%>
<%@page import="fr.eni.ecole.encheres.bo.*"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>

<%@page import="java.util.*"%>
<%@page import="fr.eni.ecole.encheres.bll.*"%>
<main>
	<h1>
		BRAVO !<br> Vous avez remporté l'enchère !
	</h1>
	<h2>${articleVendu.nomArticle}</h2>

	<%
	List<ArticleVendu> articlesVendus = (List<ArticleVendu>) request.getAttribute("articlesVendus");
	%>
	<c:if test="${not empty articleVenduTermine}">
		<h2>${articleVenduTermine.nomArticle}</h2>
		<p>${articleVenduTermine.description}</p>
		<img
			src="${pageContext.request.contextPath}/Images/${articleVenduTermine.jaquette}"
			alt="${articleVenduTermine.nomArticle}" />

		<p>Prix Initial : ${prixInitial} ₿</p>
		<p>Prix de vente : ${articleVenduTermine.prixVente} ₿</p>

		<c:if test="${not empty requestScope.pourcentageEnchere}">
			<p>Valorisation ${requestScope.pourcentageEnchere} %</p>
		</c:if>

		<p>L'acheteur est : ${requestScope.acheteurEnchere}</p>
		<!-- Utilisez "acheteurEnchere" ici -->

		<p>
			Acheté par <a id="link-acquereur"
				href="${pageContext.request.contextPath}/profil?id=${noUtilisateur.acquereur.noUtilisateur}">
				${requestScope.acheteurEnchere} </a>
		</p>
	</c:if>

	<form method="POST" action="">
		<button type="submit" name="action" value="Retour"
			 class="bouton-miniature actionbouton">Retour</button>
		<a href="mailto:${articleVendu.utilisateur.email}"
			class="bouton-miniature bouton-classique">Contactez le Vendeur</a>



	</form>
</main>

<%@ include file="/WEB-INF/fragments/footer.jspf"%>
