<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/fragments/header.jspf"%>


<main class="row">
	<div class="col">
		<div class="row text-center mt-4">
			<h1>Modifier mon Profil</h1>
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
				<form action="" method="POST">

					<div class="mb-3">
						<label for="pseudo" class="form-label">Votre Pseudo:</label> <input
							type="text" class="form-control" value="${ utilisateur.pseudo }"
							name="pseudo" id="pseudo">
					</div>
					<div class="mb-3">
						<label for="nom" class="form-label">Votre Nom:</label> <input
							type="text" class="form-control" value="${ utilisateur.nom }"
							name="nom" id="nom">
					</div>
					<div class="mb-3">
						<label for="prenom" class="form-label">Votre Prénom:</label> <input
							type="text" class="form-control" value="${ utilisateur.prenom }"
							name="prenom" id="prenom">
					</div>
					<div class="mb-3">
						<label for="email" class="form-label">Email:</label> <input
							type="text" class="form-control" value="${ utilisateur.email }"
							name="email" id="email">
					</div>
					<div class="mb-3">
						<label for="telephone" class="form-label">Telephone:</label> <input
							type="text" class="form-control"
							value="${ utilisateur.telephone }" name="telephone"
							id="telephone">
					</div>
					<div class="mb-3">
						<label for="rue" class="form-label">Votre Rue:</label> <input
							type="text" class="form-control"
							value="${ utilisateur.adresse.rue }" name="rue" id="rue">
					</div>
					<div class="mb-3">
						<label for="codePostal" class="form-label">Votre Code
							Postal:</label> <input type="text" class="form-control"
							value="${ utilisateur.adresse.codePostal }" name="codePostal"
							id="codePostal">
					</div>
					<div class="mb-3">
						<label for="ville" class="form-label">Votre Ville:</label> <input
							type="text" class="form-control"
							value="${ utilisateur.adresse.ville }" name="ville" id="ville">
					</div>
					<div class="mb-3">
						<label for="credit" class="form-label">Crédit Actuel:</label> <input
							readonly="readonly" type="text" class="form-control"
							value="${utilisateur.credit}" id="credit" name="credit">
					</div>

					<div class="mb-3">
						<label for="mdp" class="form-label">Mot de passe :</label> <input
							readonly="readonly" type="password" class="form-control" id="mdp"
							name="mdp" value="${utilisateur.mdp}">
					</div>


					<div class="mb-3 text-center mt-5">
						<button class="btn btn-primary" type="submit">
							<i class="fa-regular fa-floppy-disk"></i>
						</button>

						<button class="btn btn-basic" type="reset">
							<i class="fa-solid fa-rotate-right fa-rotate-180"></i>
						</button>
					</div>
				</form>
				<div class="row mt-5">
					<div class="col-4 offset-4">
						<form method="POST"
							action="${ pageContext.request.contextPath }/supprimerprofil"
							onsubmit="return confirm('Voulez-vous vraiment supprimer votre profil ?')">

							<button type="submit" name="id"
								value="${utilisateur.noUtilisateur}" class="btn btn-danger">
								<i class="fa-solid fa-trash"></i>
							</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</main>
<%@ include file="/WEB-INF/fragments/footer.jspf"%>
