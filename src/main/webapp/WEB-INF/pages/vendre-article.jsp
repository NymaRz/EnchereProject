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


	<br/><br/><h1>Vendre un article :</h1>
	<form method="POST"
		action=""
		enctype="multipart/form-data">
		<div id="formulaire-vente">
		<!-- <label for="nomArticle" class="form-label">Article :</label>  -->
		<div id="nom-cat"><input
			name="nomArticle" id="nomArticle " placeholder="Nom de votre article" style="width:59%" aria-label="Search"/> 
			 <select name="categorie" id="categorie">
			<option value="">Catégories disponibles</option>

			<c:forEach var="categorie" items="${ categories }">

				<option value="${ categorie.noCategorie}">${categorie.libelle}</option>

			</c:forEach>

		</select> 
		</div>
<!-- 			<label for="description" class="form-label">Description :</label> 
 -->			<textarea name="description" rows="5" id="description" style="width:100%" placeholder="Ajoutez une description à votre article (taille, poids, techniques etc.)"/></textarea> 
<!-- 			<label for="pet-select">Catégorie :</label> 
 -->			<br/>

		<!-- <br/><label for="jaquette" class="form-label">Photo de l'article :</label> -->
		
	
		
		<input class="form-control" type="file" id="jaquette" name="jaquette" style="display:none;">

	<label for="jaquette" class="custom-file-upload">Ajoutez une photo à votre article</label>
	<span id="fileNameDisplay"></span><br/><br/>


			<label for="dateDebutEnchere" class="form-label">Début enchères :</label> 
			<input name="dateDebutEncheres" id="dateDebutEncheres" type="date" /> <label
			for="Retrait" class="form-label">Fin enchères :</label> 
			<input name="dateFinEncheres" id="dateFinEncheres" type="date" />
			
			<br/><label for="miseAPrix" class="form-label">Saisissez un montant</label> 
		<input name="miseAPrix" id="miseAPrix" /> 

		<fieldset id="Modalites">
			<legend>Modalités de retrait</legend>
			<div class="min-adresse"><label for="Retrait" class="form-label">Rue :</label><br/> 
			<input value="${adresse.rue }" name="rue" id="rue" placeholder="Rue"/> 
			</div><div class="min-adresse"><label for="Retrait" class="form-label">Code postal :</label><br/> 
			<input value="${adresse.codePostal }" name="codePostal" id="codePostal" placeholder="Code postal"/> 
			</div><div class="min-adresse"><label for="Retrait" class="form-label">Ville :</label> <br/>
			<input value="${adresse.ville }" name="ville" id="ville" placeholder="Ville"/>
			</div>
		</fieldset>
		<a href="#" onclick="window.history.back();">Retour</a>
		<%-- 			<a href="${ pageContext.request.contextPath }/articles?id=${ articleVendu.nomArticleVendu }">Appliquer les Modifications</a>--%>

		<!-- Faire que l'article ne soit pas modifiable si la vente est commencée.
 -->
		<button type="submit" name="Appliquer" value="Appliquer">Enregistrer</button>
		<button type="reset" name="reset" value="reset">Effacer</button>
		<%-- <bouton action="${ pageContext.request.contextPath }/articles?id=${ articleVendu.nomArticleVendu }">Annuler la vente</a> --%>
	</div>
	</form>
</main>


<%@ include file="/WEB-INF/fragments/footer.jspf"%>