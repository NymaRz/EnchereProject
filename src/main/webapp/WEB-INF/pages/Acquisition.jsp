<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/fragments/header.jspf"%>
<%@page import="fr.eni.ecole.encheres.bo.*"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>

<%@page import="java.util.*"%>
<%@page import="fr.eni.ecole.encheres.bll.*"%>
<main>
	<h1>
		BRAVO !<br> Vous avez remporté l'article
	</h1>
	<h2>${articleVendu.nomArticle}</h2>

	<%
	List<ArticleVendu> articlesVendus = (List<ArticleVendu>) request.getAttribute("articlesVendus");
	%>

	<div id="container-secondaire">
		<c:forEach var="articleVendu" items="${articlesVendus}">
			<c:if
				test="${articleVendu.utilisateur.noUtilisateur == utilisateurConnecte.noUtilisateur}">
				<div id="article-vendu">
					<div id="miniature-article"
						style="background: linear-gradient(to left bottom, rgba(0, 0, 0, 0), #3A444A), url('${pageContext.request.contextPath}/Images/${articleVendu.jaquette}') center/cover no-repeat, url('${pageContext.request.contextPath}/Images/imgarticle.png') center/cover no-repeat;">
						<span style="width: 100px" class="bouton-miniature prix-article">${articleVendu.prixVente}
							₿</span> <a
							href="${pageContext.request.contextPath}/encherir?id=${articleVendu.noArticle}"
							style="width: 100px" class="bouton-miniature actionbouton">${articleVendu.dateFinEncheres}</a>
					</div>
					<h4 class="titre-miniature">${articleVendu.nomArticle}</h4>
					<p>
						Vendu par <a id="link-vendeur"
							href="${pageContext.request.contextPath}/profil?id=${articleVendu.utilisateur.noUtilisateur}">${articleVendu.utilisateur.pseudo}</a>
					</p>
					<p>${articleVendu.description}</p>
				</div>
			</c:if>
		</c:forEach>
	</div>

	<form method="POST" action="">
		<button type="submit" name="action" value="Retour"
			class="bouton-miniature actionbouton">Retour</button>
		<a href="mailto:${articleVendu.utilisateur.email}"
			class="bouton-miniature bouton-classique">Contactez le Vendeur</a>

	</form>
</main>

<%@ include file="/WEB-INF/fragments/footer.jspf"%>
