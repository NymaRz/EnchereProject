<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/fragments/header.jspf"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>

<%@page import="java.util.*"%>
<%@page import="fr.eni.ecole.encheres.bo.*"%>

<%@page import="fr.eni.ecole.encheres.bll.*"%>
<%
List<Categorie> categories = (List<Categorie>) request.getAttribute("categories");
%>
<main>


	<h1>Mettre une oeuvre en vente :</h1>
	<form method="POST"
		action=""
		enctype="multipart/form-data"
		>
		<label for="nomArticle" class="form-label">Article :</label> <input
			name="nomArticle" id="nomArticle" /> <label for="description"
			class="form-label">Description :</label> <input name="description"
			id="description" /> <label for="pet-select">Catégorie :</label> <select
			name="categorie" id="categorie-select">
			<option value="">Choisir une option</option>

			<c:forEach var="categorie" items="${ categories }">

				<option value="${ categorie.noCategorie}">${categorie.libelle}</option>

			</c:forEach>

		</select> <label for="jaquette" class="form-label">Photo de l'article :</label> <input
			class="form-control" type="file" id="jaquette" name="jaquette">



		<label for="miseAPrix" class="form-label">Mise à prix :</label> <input
			name="miseAPrix" id="miseAPrix" /> <label for="dateDebutEnchere"
			class="form-label">Début enchères :</label> <input
			name="dateDebutEncheres" id="dateDebutEncheres" type="date" /> <label
			for="Retrait" class="form-label">Fin enchères :</label> <input
			name="dateFinEncheres" id="dateFinEncheres" type="date" />

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

		<!-- Faire que l'article ne soit pas modifiable si la vente est commencée.
 -->
		<button type="submit" name="Appliquer" value="Appliquer">Enregistrer</button>
		<button type="reset" name="reset" value="reset">Effacer</button>
		<%-- <bouton action="${ pageContext.request.contextPath }/articles?id=${ articleVendu.nomArticleVendu }">Annuler la vente</a> --%>
	</form>
</main>


<%@ include file="/WEB-INF/fragments/footer.jspf"%>