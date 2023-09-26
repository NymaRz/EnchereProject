<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/fragments/header.jspf"%>

<main class="row">
	<div class="col">
		<div class="row text-center mt-4">
			<h1>Mon Profil</h1>
		</div>
		<div class="row mt-5">
			<div class="col-4 offset-4">
				<c:if test="${not empty success}">
					<div class="alert alert-success">${success}</div>
					<c:remove var="session.success" />
				</c:if>
				<c:if test="${not empty error}">
					<div class="alert alert-danger">${error}</div>
					<c:remove var="sessionScope.error" />
				</c:if>
				<div class="mb-3">
				<img id="photo" name="photo" class="img-fluid"
			src="${ pageContext.request.contextPath }/Images/${ utilisateur.photo }"
			alt="${ utilisateur.photo }">
				</div>
				<div class="mb-3">
					<label for="pseudo" class="form-label">Votre Pseudo:</label> <input
						type="text" readonly="readonly" class="form-control"
						value="${utilisateur.pseudo}" name="pseudo" id="pseudo">
				</div>
				<div class="mb-3">
					<label for="nom" class="form-label">Votre Nom:</label> <input
						type="text" readonly="readonly" class="form-control"
						value="${utilisateur.nom}" name="nom" id="nom">
				</div>
				<div class="mb-3">
					<label for="prenom" class="form-label">Votre Prénom:</label> <input
						type="text" readonly="readonly" class="form-control"
						value="${utilisateur.prenom}" name="prenom" id="prenom">
				</div>
				<div class="mb-3">
					<label for="email" class="form-label">Email:</label> <input
						type="text" readonly="readonly" class="form-control"
						value="${utilisateur.email}" name="email" id="email">
				</div>
				<div class="mb-3">
					<label for="telephone" class="form-label">Telephone:</label> <input
						type="text" readonly="readonly" class="form-control"
						value="${utilisateur.telephone}" name="telephone" id="telephone">
				</div>
				<div class="mb-3">
					<label for="rue" class="form-label">Votre Rue:</label> <input
						type="text" readonly="readonly" class="form-control"
						value="${utilisateur.adresse.rue}" name="rue" id="rue">
				</div>
				<div class="mb-3">
					<label for="codePostal" class="form-label">Votre Code
						Postal:</label> <input type="text" readonly="readonly"
						class="form-control" value="${utilisateur.adresse.codePostal}"
						name="codePostal" id="codePostal">
				</div>
				<div class="mb-3">
					<label for="ville" class="form-label">Votre Ville:</label> <input
						type="text" readonly="readonly" class="form-control"
						value="${utilisateur.adresse.ville}" name="ville" id="ville">
				</div>
				<div class="mb-3">
					<label for="credit" class="form-label">Crédit Actuel:</label> <input
						readonly="readonly" type="text" class="form-control"
						value="${utilisateur.credit}" id="credit" name="credit">
				</div>
				<div class="mb-3">
					<label for="mdp" class="form-label">Mot de passe:</label> <input
						readonly="readonly" type="password" class="form-control"
						value="${utilisateur.mdp}" id="mdp" name="mdp">
				</div>

				<!-- Ajoutez une boucle forEach pour afficher les ventes de l'utilisateur -->
				<c:forEach var="articleVendu" items="${articlesVendus}">
					<c:if
						test="${articleVendu.utilisateur.noUtilisateur == utilisateur.noUtilisateur}">
						<div id="article-vendu">
							<!-- Affichez les détails de l'article vendu -->
							<div id="miniature-article"
								style="background: url('${pageContext.request.contextPath }/Images/imgarticle.png') center/cover no-repeat, linear-gradient(to left bottom, rgba(0, 0, 0, 0), #3A444A), url('${pageContext.request.contextPath }/Images/${articleVendu.jaquette}') center/cover no-repeat;">
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
						</div>
					</c:if>
				</c:forEach>

				<form method="POST"
					action="${pageContext.request.contextPath}/modifierprofil">
					<div class="mb-3 text-center mt-5">
						<a class="btn btn-info"
							href="${pageContext.request.contextPath}/profil/modifierprofil?id=${utilisateur.noUtilisateur}">
							Modifier mon profil </a>
					</div>
				</form>
			</div>
		</div>
	</div>
</main>
<script>
	setTimeout(function() {
		var successMessage = document.getElementById("successMessage");
		var errorMessage = document.getElementById("errorMessage");

		if (successMessage) {
			successMessage.style.display = "none";
		}

		if (errorMessage) {
			errorMessage.style.display = "none";
		}
	}, 5000); // Masquer après 5 secondes
</script>
<%@ include file="/WEB-INF/fragments/footer.jspf"%>