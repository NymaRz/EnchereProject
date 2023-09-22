<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/fragments/headerAccueilNONConnecte.jspf"%>

<main>


	<h1>Mettre une oeuvre en vente :</h1>
	<form method="POST"
		action="${pageContext.request.ContextPath }/enregistrer">
		<label for="nomArticle" class="form-label">Article :</label> <input
			name="nomArticle" id="nomArticle" /> <label for="description"
			class="form-label">Description :</label> <input name="description"
			id="description" /> <label for="pet-select">Catégorie :</label> <select
			name="categorie" id="categorie-select">
			<option value="">Choisir une option</option>

			<c:forEach var="libelleCategorie"
				items="${ articleVendu.categories }">
				<option value="${categories.noCategorie}">${categories.libelle}</option>
			</c:forEach>

		</select> <label for="miseAPrix" class="form-label">Mise à prix :</label> <input
			name="miseAPrix" id="miseAPrix" /> <label for="dateDebutEnchere"
			class="form-label">Début enchères :</label> <input
			name="dateDebutEncheres" id="dateDebutEncheres" /> <label
			for="Retrait" class="form-label">Fin enchères :</label> <input
			name="dateFinEncheres" id="dateFinEncheres" />

		<fieldset>
			<legend>Retrait</legend>
			<label for="Retrait" class="form-label">Rue :</label> <input
				value="${adresse.rue }" name="rue" id="rue" /> <label for="Retrait"
				class="form-label">Code postal :</label> <input
				value="${adresse.codePostal }" name="codePostal" id="ville" /> <label
				for="Retrait" class="form-label">Ville :</label> <input
				value="${adresse.ville }" name="ville" id="ville" /> 
		</fieldset>
			<a href="#" onclick="window.history.back();">Retour</a>
<%-- 			<a href="${ pageContext.request.contextPath }/articles?id=${ articleVendu.nomArticleVendu }">Appliquer les Modifications</a>--%>						
			<button type="submit" name="Appliquer" value="Appliquer" >Stoper la vente</button>
			<button type="submit" name="Stoper" value="Stoper" >Stoper la vente</button>
			<%-- <bouton action="${ pageContext.request.contextPath }/articles?id=${ articleVendu.nomArticleVendu }">Annuler la vente</a> --%>
	</form>
</main>


<%@ include file="/WEB-INF/fragments/footer.jspf"%>