<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/fragments/headerAccueilNONConnecte.jspf"%>

<main>


	<h1>Mettre une oeuvre en vente :</h1>

	<label for="nomArticle" class="form-label">Article :</label> <input
		readonly="readonly" value="${ articleVendu.nomArticle }" /> <label
		for="description" class="form-label">Description :</label> <input
		readonly="readonly" value="${ articleVendu.description }" /> <label
		for="pet-select">Catégorie :</label>

	<select name="categorie" id="categorie-select">
		<option value="">Choisir une option</option>
		
		<c:forEach var="game" items="${ articleVendu.categories }">
			<option value="${categories.libelle}">${categories.libelle}</option>
		</c:forEach>
		
	</select> 
	
	
<form method="POST"
		action="${pageContext.request.ContextPath }/enregistrer">
	<label for="miseAPrix" class="form-label">Mise à prix
		:</label> 		<input value="${ articleVendu.miseAPrix }" /> 
		<label for="Retrait" class="form-label">Retrait :</label>
		<input value="${ article.enchere.date_enchere }" /> 
		<label for="Retrait" class="form-label">Retrait :</label>
		<input value="${ articleVendu.lieuRetrait }" /> 
		<label for="Vendeur" class="form-label">Vendeur :</label> 
		<input value="${ articleVendu.utilisateur }" /> 
		<label for="Tel" class="form-label">Tel :</label> 
		<input value="${ utilisateur.telephone }" /> 
		<a href="${ pageContext.request.contextPath }/articles?id=${ articleVendu.nomArticleVendu }">Enregistrer</a>
		<a href="#" onclick="window.history.back();">Annuler</a>
</main>


<%@ include file="/WEB-INF/fragments/footer.jspf"%>