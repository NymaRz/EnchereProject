<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/fragments/header.jspf"%>

<main>
	<%@include file="/WEB-INF/fragments/recherche.jspf"%>

	<c:forEach var="articleVendu" items="${articlesVendus}">
		<div id="container-secondaire">
			<div id="article-vendu">
				<div id="miniature-article"
					style="background: linear-gradient(to left bottom, rgba(0, 0, 0, 0), #3A444A), url('${articleVendu.jaquette}') center/cover no-repeat;">
					<span class="bouton-miniature prix-article">${articleVendu.prixVente}
						€</span> <a href="#" class="bouton-miniature actionbouton">${articleVendu.dateFinEncheres}</a>
				</div>
				<h4 class="titre-miniature">${articleVendu.nomArticle}</h4>
				<p>
					Vendu par <a id="link-vendeur"
						href="${pageContext.request.contextPath}/profil?id=${articleVendu.utilisateur.noUtilisateur}">${articleVendu.utilisateur.pseudo}</a>
				</p>
			</div>
		</div>
	</c:forEach>

	<div id="container-primaire"></div>
</main>

<%@ include file="/WEB-INF/fragments/footer.jspf"%>
