<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/fragments/headerAccueilNONConnecte.jspf"%>

<main>

	<h2>${ articleVendu.acquereur }</h2>

	<label for="Description" readonly="readonly" class="form-label">Description :</label> <input
		readonly="readonly" value="${ articleVendu.description }" /> <label
		for="MeilleurOffre" readonly="readonly"  class="form-label">Meilleure offre :</label> <input
		readonly="readonly" value="${ articleVendu.prixVente }" /> <label
		for="Retrait" readonly="readonly"  class="form-label">Mise à prix :</label> <input
		readonly="readonly" value="${ articleVendu.miseAPrix }" /> <label
		for="Retrait" readonly="readonly"  class="form-label">Fin de l'enchère :</label> <input
		readonly="readonly" value="${ articleVendu.lieuRetrait }" /> <label
		for="Vendeur" readonly="readonly" class="form-label">Retrait :</label> 
		<input readonly="readonly" value="${ articleVendu.rue }" />
		<input readonly="readonly" value="${ articleVendu.codePostal } ${ articleVendu.ville }" />
		<label for="Vendeur" readonly="readonly" class="form-label">Vendeur :</label> <input
		readonly="readonly" value="${ articleVendu.nomUtilisateur }" />

	<form method="POST" action="${pageContext.request.ContextPath }/retrait">
			<button type="submit" name="retraitEffectue" value="Retrait effectué" >
			</button>
	</form>

</main>


<%@ include file="/WEB-INF/fragments/footer.jspf"%>