<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/fragments/header.jspf"%>
<%@ page import="fr.eni.ecole.encheres.bo.*"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>

<%@page import="java.util.*"%>
<%@page import="fr.eni.ecole.encheres.bll.*"%>
<main>
	<h1>
		BRAVO !<br> Votre vente a été un succès !
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

		<p>
			L'acheteur est : <a id="link-acheteur"
				href="${pageContext.request.contextPath}/profil?id=${acheteurEnchere.utilisateur.noUtilisateur}">
				${acheteurEnchere.utilisateur.pseudo} </a>
		</p>

	</c:if>

	<form method="POST" action="">
		<button type="submit" name="action" value="Confirm"
			class="bouton-miniature actionbouton">Confirmer le retrait</button>
		<button onclick="retourPagePrecedente()">Retour</button>

		<script>
			function retourPagePrecedente() {
				window.history.back();
			}
		</script>
	</form>
</main>

<%@ include file="/WEB-INF/fragments/footer.jspf"%>
