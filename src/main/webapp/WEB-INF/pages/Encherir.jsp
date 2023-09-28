<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/fragments/header.jspf"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<main>
	<div id="page-encherir">
<br><br><br>


		<c:if test="${ !empty success}">
			<div class="alert alert-success">${success}</div>
		</c:if>
		<c:if test="${ !empty error}">
			<div class="alert alert-danger">${error}</div>
		</c:if>

					<div style="text-align:left" class="bloc-profil">
		
		<div style=" display:inline-block;width:40%;" class="">
			<img style="border-radius:12px;" class="img-fluid" src="${ pageContext.request.contextPath }/Images/${ articleVendu.jaquette }" alt="${ articleVendu.jaquette }">
		</div>
		<div style="text-aligne:left;display-inline:block;width:55%;float:right">
		<p style="font-size:1.6em;margin-bottom:0px;color:#000">${ articleVendu.nomArticle }</p>
		<p style="font-size:0.9em;">Classée dans ${articleVendu.categorieArticle.libelle}</p><br>
		<p  style="font-size:1.2em;">${articleVendu.description}</p>
		<c:if test="${ enchere != null }">
			<p style="padding:8px 12px;background-color:#c5c5c57d;border-radius:8px;text-align:center;">Meilleure offre : ${enchere.montant_enchere} points par ${enchere.acquereur.pseudo}</p>
		</c:if>
		<%--OU  <P>Meilleure offre : ${articleVendu.prixVente}</P> --%>
		<div style="display:inline-block;width:32%"><p style="font-size:0.9;margin-bottom:0px;">Mise à prix</p><span style="font-size:1.2;color:#000;font-weight:700;">${articleVendu.miseAPrix}</span></div>
		<div style="display:inline-block;width:32%"><p style="font-size:0.9;margin-bottom:0px;">Fin</p><span style="font-size:1.2;color:#000;font-weight:700;">${articleVendu.dateFinEncheres}</span></div>

<div style="display:inline-block;width:32%"><p style="font-size:0.9;margin-bottom:0px;">Vendeur</p> <a id="link-vendeur"

							href="${pageContext.request.contextPath}/profil?id=${articleVendu.utilisateur.noUtilisateur}"><span style="font-size:1.2;">${articleVendu.utilisateur.pseudo}</span></a></div>

		<c:if test="${articleVendu.etatVente.equals('vf')}">
			<p>Gagnant de l'enchère : ${enchere.acquereur.pseudo}</p>
		</c:if>
</div></div><br>

<<<<<<< HEAD
		<c:if test="${articleVendu.etatVente.equals('v')}">
			<form action="" method="POST">
				<div id="bloc-bouton-encherir">
					<div class="montantEnchere">
						<%-- <label class="enchere-min" for="montantEnchere">${articleVendu.enchereMin}</label>  --%>
						<input name="montantEnchere" id="montantEnchere" type="number"
							value="${minEnchere}" min="${minEnchere }"
							step="${articleVendu.enchereMin }" />
						<button type="button" class="montantEnchere-increment">+
							100</button>
						<button type="button" class="montantEnchere-decrement">-
							100</button>

						<button class="bouton-encherir" type="submit">ENCHERIR</button>
					</div>
=======
<c:if test="${articleVendu.etatVente.equals('v')}">
		<form action="" method="POST">
			<div id="bloc-bouton-encherir">
				<div class="montantEnchere">
					<%-- <label class="enchere-min" for="montantEnchere">${articleVendu.enchereMin}</label>  --%>
					<input name="montantEnchere" id="montantEnchere" type="number"
						value="${minEnchere}" min="${minEnchere }"
						step="${articleVendu.enchereMin }" />
					<button type="button" class="montantEnchere-increment">+ 100</button>
					<button type="button" class="montantEnchere-decrement">- 100</button>
					<button class="bouton-encherir" type="submit">ENCHERIR</button>
>>>>>>> max
				</div>
			</form>

<<<<<<< HEAD
=======
				// Minimum number
				let min = ${minEnchere}
				;
>>>>>>> max

			<script>
				document.addEventListener("DOMContentLoaded", function() {
					let el = document.querySelector(".montantEnchere");
					let input = el.querySelector("input");
					let incrementBtn = el
							.querySelector("button.montantEnchere-increment");
					let decrementBtn = el
							.querySelector("button.montantEnchere-decrement");

					// Minimum number
					let min = ${minEnchere}
					;

					// Set the initial value
					input.value = min;

					// Increment
					incrementBtn.addEventListener("click", function(event) {
						event.preventDefault(); // Empêche l'envoi du formulaire
						var value = parseInt(input.value);
						if (value >= min) {
							value += 100;
						}
						input.value = value;
					});

					// Decrement
					decrementBtn.addEventListener("click", function(event) {
						event.preventDefault(); // Empêche l'envoi du formulaire
						var value = parseInt(input.value);
						if (value >= min) {
							value -= 100;
						}
						input.value = value;
					});

					// Blur event to reset to the minimum if input is empty or negative
					input.addEventListener("blur", function() {
						var value = parseInt(input.value);
						if (isNaN(value) || value < min) {
							input.value = min;
						}
					});
				});
			</script>







			<br>
			<br>
			<br>
		</c:if>
		<!-- <script>

document.addEventListener("DOMContentLoaded", function () {
  let el = document.querySelector(".montantEnchere");
  let input = el.querySelector("input");
  let incrementBtn = el.querySelector("button.montantEnchere-increment");
  let decrementBtn = el.querySelector("button.montantEnchere-decrement");

  // Minimum number
  let min = ${minEnchere};

  // Set the initial value
  input.value = min;

  // Increment
  incrementBtn.addEventListener("click", function () {
    var value = parseInt(input.value);
    if (value >= min) {
      value += 100;
    }
    input.value = value;

  });
  
  decrementBtn.addEventListener("click", function () {
	  var value = parseInt(input.value);
	  if (value >= min) { // Vérifiez que la valeur est supérieure ou égale à min
	    value--;
	  }
	  input.value = value;
	});


<<<<<<< HEAD
  // Blur event to reset to the minimum if input is empty or negative
  input.addEventListener("blur", function () {
    var value = parseInt(input.value);
    if (isNaN(value) || value < min) {
      input.value = min;
    }
  });
});
</script> -->



	</div>

</main>




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