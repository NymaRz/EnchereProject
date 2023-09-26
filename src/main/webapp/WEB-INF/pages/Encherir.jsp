<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/fragments/header.jspf"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<main>

	<h2>Détails Vente</h2>
	<h1>${ articleVendu.nomArticle }</h1>
	
	
	<c:if test="${ !empty success}">
		<div class="alert alert-success">${success}</div>
	</c:if>
	<c:if test="${ !empty error}">
		<div class="alert alert-danger">${error}</div>
	</c:if>
	
	
	<div class="mb-3">
		<img class="img-fluid"
			src="${ pageContext.request.contextPath }/Images/${ articleVendu.jaquette }"
			alt="${ articleVendu.jaquette }">
	</div>
	<!-- inverser h1 et h2? -->
	<p>Description : ${articleVendu.description}</p>
	<c:if test="${ enchere != null }">
		<p>Meilleure offre : ${enchere.montant_enchere} points par
			${enchere.acquereur.pseudo}</p>
	</c:if>
	<%--OU  <P>Meilleure offre : ${articleVendu.prixVente}</P> --%>
	<p>Mise à prix : ${articleVendu.miseAPrix} points</p>
	<p>Fin de l'enchère : ${articleVendu.dateFinEncheres}</p>

	<p>Vendeur : ${articleVendu.utilisateur.pseudo}</p>





	
	
	
	
	<form action="" method="POST">
		<label for="montantEnchere">Ma proposition (+
			${articleVendu.enchereMin}) :</label> <input name="montantEnchere"
			id="montantEnchere" type="number" value="${minEnchere}"
			min="${minEnchere }" step="${articleVendu.enchereMin }">
		<button type="submit">Envoyer</button>
	</form>






	<%-- <label for="Description" class="form-label">Description :</label> 
<input readonly="readonly" value="${ articleVendu.description }" />

<label for="MeilleurOffre" class="form-label">Meilleure offre :</label> 
<input readonly="readonly" value="${ enchere.montant_enchere }" />

<label for="Retrait" class="form-label">Retrait :</label> 
<input readonly="readonly" value="${ articleVendu.miseAPrix }" />

<label for="Retrait" class="form-label">Retrait :</label> 
<input readonly="readonly" value="${ articleVendu.lieuRetrait }" />

<label for="Vendeur" class="form-label">Vendeur :</label> 
<input readonly="readonly" value="${ articleVendu.utilisateur }" />

<label for="Tel" class="form-label">Tel :</label> 
<input readonly="readonly" value="${ utilisateur.telephone }" />

<a href="${ pageContext.request.contextPath }/">Précédent</a>
</main> --%>


	<%@ include file="/WEB-INF/fragments/footer.jspf"%>