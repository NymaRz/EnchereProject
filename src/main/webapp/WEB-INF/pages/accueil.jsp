<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/fragments/header.jspf"%>



<main>




	<c:choose>
		<c:when test="${utilisateur == null}">
			<%@include file="/WEB-INF/fragments/recherche.jspf"%>
		</c:when>
		
		<c:otherwise>
			<div id="affichage-menu-recherche">
			<%@include file="/WEB-INF/fragments/rechercheavancee.jspf"%>
			</div>
		</c:otherwise>
	</c:choose>





	<%
	List<ArticleVendu> articlesVendus = (List<ArticleVendu>) request.getAttribute("articlesVendus");
	%>
	<%
	List<Categorie> categories = (List<Categorie>) request.getAttribute("categories");
	%>




<div id="page-principale">
	<div id="container-secondaire">



		<c:forEach var="categorie" items="${ categories }">
		<h2>${categorie.libelle}</h2>
			<c:forEach var="articleVendu" items="${categorie.articlesOfCategorie }">

				<div id="article-vendu">

					<div id="miniature-article"
						style="background: linear-gradient(to left bottom, rgba(0, 0, 0, 0), #3A444A), url('${pageContext.request.contextPath }/Images/${articleVendu.jaquette}') center/cover no-repeat, url('${pageContext.request.contextPath }/Images/imgarticle.png') center/cover no-repeat;">

						<span style="widht: 100px" class="bouton-miniature prix-article">${articleVendu.prixVente }

							₿</span> <a
							href="${pageContext.request.contextPath }/encherir?id=${articleVendu.noArticle}"
							style="widht: 100px" class="bouton-miniature actionbouton">${articleVendu.dateFinEncheres }</a>


					</div>

					<h4 class="titre-miniature">${articleVendu.nomArticle }</h4>

					<p>

						Vendu par <a id="link-vendeur"
							href="${pageContext.request.contextPath}/profil?id=${articleVendu.utilisateur.noUtilisateur}">${articleVendu.utilisateur.pseudo}</a>

					</p>

				</div>

			</c:forEach>


		</c:forEach>

	</div>


	<div id="container-secondaire">

		<h1>${nomListe }</h1>


		<c:forEach var="articleVendu" items="${ articlesVendus }">

			<div id="article-vendu">

				<div id="miniature-article"
					style="background: linear-gradient(to left bottom, rgba(0, 0, 0, 0), #3A444A), url('${pageContext.request.contextPath }/Images/${articleVendu.jaquette}') center/cover no-repeat, url('${pageContext.request.contextPath }/Images/imgarticle.png') center/cover no-repeat;">

					<span style="widht: 100px" class="bouton-miniature prix-article">${articleVendu.prixVente }

						₿</span> <a
						href="${pageContext.request.contextPath }/encherir?id=${articleVendu.noArticle}"
						style="widht: 100px" class="bouton-miniature actionbouton">${articleVendu.dateFinEncheres }</a>


				</div>

				<h4 class="titre-miniature">${articleVendu.nomArticle }</h4>

				<p>

					Vendu par <a id="link-vendeur"
						href="${pageContext.request.contextPath}/profil?id=${articleVendu.utilisateur.noUtilisateur}">${articleVendu.utilisateur.pseudo}</a>

				</p>

			</div>

		</c:forEach>

	</div>





	
		</div>
		<div id="container-proof">

		<div id="container-secondaire">

			<div class="proof-min">

				<i class="fab fa-facebook-f"></i>

				<p class="proof-p">Déjà X articles vendus et Y crédits échangés

					sur ArtoVision</p>

			</div>

			<div class="proof-min">

				<i class="bi bi-cast"></i>

				<p class="proof-p img-proof">Déjà X articles vendus et Y crédits
					échangés sur ArtoVision</p>

			</div>

			<div class="proof-min">

				<i class="fab fa-facebook-f"></i>

				<p class="proof-p">Déjà X articles vendus et Y crédits échangés
					sur ArtoVision</p>

			</div>

		</div>
		
		</div>
</main>





<%@ include file="/WEB-INF/fragments/footer.jspf"%>