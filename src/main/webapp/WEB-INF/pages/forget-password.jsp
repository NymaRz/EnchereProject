<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/fragments/header.jspf"%>
<main class="row">
	<div class="col">
		<div class="row text-center mt-4">
			<h1>Réinitialisation du mot de passe</h1>
		</div>
		<div class="bloc-profil-co">
		
		<div class=" ">
			<div class=" ">
				<form method="post" action="forget-password">
					<!-- Assurez-vous que l'action correspond à l'URL de votre servlet -->
					<div class="">
						<label for="email" class="form-label">Adresse e-mail</label> <input
							type="email" class="form-control" name="email" id="email"
							required>
					</div>
					<div class="mb-3">
						<label for="mdp" class="form-label">Ancien mot de passe</label> <input
							type="password" class="form-control" name="mdp" id="mdp" required>
					</div>
					<div class="mb-3">
						<label for="newPassword" class="form-label">Nouveau mot de passe</label> <input type="password" class="form-control"
							name="newPassword" id="newPassword" required>
					</div>
					<button class="bouton-action-co " role="button" type="submit">Changer mon mot de passe</button>
				</form>

				<c:if test="${not empty requestScope.error}">
					<div class="alert alert-danger mt-3">${requestScope.error}</div>
				</c:if>
			</div>
		</div>
		</div><br><br><br>
	</div>
</main>
<%@ include file="/WEB-INF/fragments/footer.jspf"%>
