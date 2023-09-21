<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/fragments/headerAccueilNONConnecte.jspf" %>

<main>


<h1>${ articleVendu.nomArticle }</h1>

<label for="Description" class="form-label">Description :</label> 
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
</main>


<%@ include file="/WEB-INF/fragments/footer.jspf" %>