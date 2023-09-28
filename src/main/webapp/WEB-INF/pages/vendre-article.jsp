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

	<script src="/assets/js/joindre.js"></script>


	<br />
	<br />
<br>	<form method="POST" action="" enctype="multipart/form-data">
		<div id="formulaire-vente">
			<!-- <label for="nomArticle" class="form-label">Article :</label>  -->
			<div id="nom-cat">
				<input name="nomArticle" id="nomArticle "
					placeholder="Nom de votre article" style="width: 59%"
					aria-label="Search" /> <select name="categorie" id="categorie">
					<option value="">Catégories disponibles</option>

					<c:forEach var="categorie" items="${ categories }">

						<option value="${ categorie.noCategorie}">${categorie.libelle}</option>

					</c:forEach>

				</select>
			</div>
			<div id="bloc-article">
				<!-- 			<label for="description" class="form-label">Description :</label> 
 -->
				<textarea name="description" rows="5" id="description"
					style="width: 100%"
					placeholder="Ajoutez une description à votre article (taille, poids, techniques etc.)" /></textarea>
				<!-- 			<label for="pet-select">Catégorie :</label> 
 -->
				<br />

				<!-- <br/><label for="jaquette" class="form-label">Photo de l'article :</label> -->



				<!-- 		<input class="form-control" type="file" id="jaquette" name="jaquette" style="display:none;">

	<label for="jaquette" class="custom-file-upload">Ajoutez une photo à votre article</label>
	<span id="jaquette"></span> -->

<!-- <div class="custom-file-upload">
  <label for="jaquette">Ajouter une photo<br></label>
  <input name="jaquette" type="file" id="jaquette" style="display: none;" onchange="displayFileName()" />
  <span id="selectedFileName"></span>
</div> -->

				<div class="custom-file-upload">
					<label for="jaquette">Ajouter une photo<br></label> <input
						type="file" id="jaquette" style="display: none;" name="file"
						onchange="displayFileName()" /> <span id="selectedFileName"></span>
				</div>


				<script>
					function displayFileName() {
						const jaquette = document.getElementById('jaquette');
						const selectedFileName = document
								.getElementById('selectedFileName');

						if (jaquette.files.length > 0) {
							selectedFileName.innerHTML = ''
									+ jaquette.files[0].name;
						} else {
							selectedFileName.innerHTML = '';
						}
					}
				</script>


				<div id="date-enchere">
					<label for="dateDebutEnchere" class="form-label">DEBUT</label><br>
					<input name="dateDebutEncheres" id="dateDebutEncheres" type="date" />
				</div>
				<div id="date-enchere">
					<label for="Retrait" class="form-label">FIN</label> <br> <input
						name="dateFinEncheres" id="dateFinEncheres" type="date" />
				</div>
			</div>

			

			<div id="middle">
				<div id="mise-a-prix">
					<label for="miseAPrix" class="form-label">Prix de départ :</label>
					<input type="number" step="100" name="miseAPrix" id="miseAPrix" />
				</div>
				<button id="mettre-en-vente" type="submit" name="Appliquer"
					value="Appliquer">Mettre en vente</button>
			</div>

			<fieldset id="Modalites">
				<legend>Editer le lieu de retrait ?</legend>
				<!-- 			<div class="min-adresse"><label for="Retrait" class="adresse-form form-label" >Rue :</label><br/> 
 -->
				<input value="${adresse.rue }" name="rue"
					style="width: 40%; background-color: #e2e9ff;" id="rue"
					placeholder="Rue" />
				<!-- 			</div>
 -->
				<!-- 			<div class="min-adresse"><label style="width:25%" for="Retrait" class="adresse-form form-label" >Code postal :</label><br/> 
 -->
				<input value="${adresse.codePostal }"
					style="width: 20%; background-color: #e2e9ff;" name="codePostal"
					id="codePostal" placeholder="Code postal" />
				<!-- </div> -->
				<!-- <div class="min-adresse"><label for="Retrait" style="width:35%" class="adresse-form form-label" >Ville :</label> <br/> -->
				<input value="${adresse.ville }" name="ville"
					style="width: 34%; background-color: #e2e9ff;" id="ville"
					placeholder="Ville" />
				<!-- </div> -->
			</fieldset>


			<div id="bouton-bas">
				<a href="#" onclick="window.history.back();">Annuler</a>
				<button type="reset" name="reset" value="reset">Effacer</button>
			</div>
			<%-- 			<a href="${ pageContext.request.contextPath }/articles?id=${ articleVendu.nomArticleVendu }">Appliquer les Modifications</a>--%>

			<!-- Faire que l'article ne soit pas modifiable si la vente est commencée.
 -->


			<%-- <bouton action="${ pageContext.request.contextPath }/articles?id=${ articleVendu.nomArticleVendu }">Annuler la vente</a> --%>
		</div>
	</form>
</main>


<%@ include file="/WEB-INF/fragments/footer.jspf"%>