<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/fragments/headerListeEncheres.jspf"%>

<main>

	<h2>Détails Vente</h2>
	<h1>${ articleVendu.nomArticle }</h1>
	<!-- inverser h1 et h2? -->
	<p>Description : ${articleVendu.description}</p>
	<p>Catégorie : ${articleVendu.categorie.libelle}</p>
	<p>Meilleure offre : ${enchere.montant_enchere} points par
		${enchere.acquereur}</p>
	<%--OU  <P>Meilleure offre : ${articleVendu.prixVente}</P> --%>
	<p>Mise à prix : ${articleVendu.miseAPrix} points</p>
	<p>Fin de l'enchère : ${articleVendu.dateFinEncheres}</p>
	<p>Retrait : ${articleVendu.retrait.adresse}</p>
	<p>Vendeur : ${articleVendu.utilisateur}</p>

	<form action="" method="POST">
		<label for="montantEnchere">Ma proposition :</label> <input
			name="montantEnchere" id="montantEnchere" type="number"
			value="${enchere.montant_enchere + articleVendu.enchereMin }">
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