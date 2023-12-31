<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/fragments/header.jspf"%>
<%@ page import="fr.eni.ecole.encheres.bo.*"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>

<%@page import="java.util.*"%>
<%@page import="fr.eni.ecole.encheres.bll.*"%>
<main>
	<div class="container">
		<h1 class="mt-5">
			BRAVO !<br> Vous avez remporté l'enchère !
		</h1>
		<h2>${articleVendu.nomArticle}</h2>

		<c:if test="${not empty articleVenduTermine}">
			<h2>${articleVenduTermine.nomArticle}</h2>
			<p>${articleVenduTermine.description}</p>
			<img
				src="${pageContext.request.contextPath}/Images/${articleVenduTermine.jaquette}"
				alt="${articleVenduTermine.nomArticle}" class="img-fluid" />

			<p>Prix Initial : ${prixInitial} ₿</p>
			<p>Prix de vente : ${articleVenduTermine.prixVente} ₿</p>

			<c:if test="${not empty requestScope.pourcentageEnchere}">
				<p>Valorisation ${requestScope.pourcentageEnchere} %</p>
			</c:if>

			<p>
				Vendu par <a id="link-vendeur"
					href="${pageContext.request.contextPath}/profil?id=${articleVendu.utilisateur.noUtilisateur}">
					${articleVenduTermine.utilisateur.pseudo} </a>
			</p>
		</c:if>

		<form method="POST" action="">
			<button onclick="retourPagePrecedente()">Retour</button>

			<script>
				function retourPagePrecedente() {
					window.history.back();
				}
			</script>
			<a href="mailto:${articleVendu.utilisateur.email}"
				class="btn btn-secondary"> Contactez le Vendeur </a>
		</form>
	</div>
</main>
<%@ include file="/WEB-INF/fragments/footer.jspf"%>
