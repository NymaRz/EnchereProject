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
		
		<c:forEach var="libelleCategorie" items="${ articleVendu.categories }">
			<option value="${categories.libelle}">${categories.libelle}</option>
		</c:forEach>
		
	</select> 
	
	
<form method="POST"
		action="${pageContext.request.ContextPath }/enregistrer">
	<label for="miseAPrix" class="form-label">Mise à prix
		:</label> 		<input value="${ articleVendu.miseAPrix }" /> 
		<label for="dateDebutEnchere" class="form-label">Début enchères :</label>
		<input value="${ articleVendu.dateDebutEncheres }" /> 
		<label for="Retrait" class="form-label">Fin enchères :</label>
		<input value="${ articleVendu.dateFinEncheres }" /> 
		
<fieldset>
    <legend>Retrait</legend>
		<label for="Retrait" class="form-label">Rue :</label>
		<input value="${ adresses.rue }" /> 
		<label for="Retrait" class="form-label">Code postal :</label>
		<input value="${ adresses.codePostal }" /> 
		<label for="Retrait" class="form-label">Ville :</label>
		<input value="${ adresses.ville }" /> 

		<a href="${ pageContext.request.contextPath }/articles?id=${ articleVendu.nomArticleVendu }">Enregistrer</a>
		<a href="#" onclick="window.history.back();">Annuler</a>
		
		</fieldset>
		</form>
</main>


<%@ include file="/WEB-INF/fragments/footer.jspf"%>