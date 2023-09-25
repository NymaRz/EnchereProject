<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/fragments/header.jspf"%>

<main class="row">
	<div class="col">
		<div class="row text-center mt-4">
			<h1>Mon Profil</h1>
		</div>
		<div class="row mt-5">
			<div class="col-8 offset-2">

				<c:if test="${not empty success}">
					<div class="alert alert-success">${success}</div>
				</c:if>
				<c:if test="${not empty error}">
					<div class="alert alert-danger">${error}</div>
				</c:if>
				<c:if test="${not empty emailError}">
					<div class="alert alert-danger">${emailError}</div>
				</c:if>
				<c:if test="${not empty pseudoError}">
					<div class="alert alert-danger">${pseudoError}</div>
				</c:if>
			</div>

			<form method="POST"
				action="${pageContext.request.contextPath}/inscription">
				<div class="col-3 offset-4">
					<div class="mb-3">
						<label for="pseudo" class="form-label">Votre Pseudo:</label> <input
							type="text" class="form-control" name="pseudo" id="pseudo"
							required>
					</div>
					<div class="mb-3">
						<label for="mdp" class="form-label">Mot de Passe:</label> <input
							type="password" class="form-control" name="mdp" id="mdp" required>
					</div>
					<div class="mb-3">
						<label for="nom" class="form-label">Votre Nom:</label> <input
							type="text" class="form-control" name="nom" id="nom" required>
					</div>
					<div class="mb-3">
						<label for="prenom" class="form-label">Votre Prénom:</label> <input
							type="text" class="form-control" name="prenom" id="prenom"
							required>
					</div>
					<div class="mb-3">
						<label for="email" class="form-label">Email:</label> <input
							type="text" class="form-control" name="email" id="email" required>
					</div>
					<div class="mb-3">
						<label for="telephone" class="form-label">Telephone:</label> <input
							type="text" class="form-control" name="telephone" id="telephone"
							required>
					</div>
					<div class="mb-3">
						<label for="adresse" class="form-label">Votre Rue:</label> <input
							type="text" class="form-control" name="rue" id="rue" required>
					</div>
					<div class="mb-3">
						<label for="code_postal" class="form-label">Votre Code
							Postal:</label> <input type="text" class="form-control" id="code_postal"
							name="code_postal" required>
					</div>
					<div class="mb-3">
						<label for="ville" class="form-label">Votre Ville:</label> <input
							type="text" class="form-control" name="ville" id="ville" required>
					</div>
					<div class="mb-3 text-center mt-5">
						<button type="submit" name="id" class="btn btn-danger">Créer
							le Profil</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</main>
<%@ include file="/WEB-INF/fragments/footer.jspf"%>
