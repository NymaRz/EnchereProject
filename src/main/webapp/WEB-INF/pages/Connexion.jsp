<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/fragments/header.jspf"%>
<main class="row">
	<div class="col">
		<div class="row text-center mt-4">
			<h1>Connexion</h1>
		</div>
		<c:if test="${not empty success}">
			<div class="alert alert-success" id="successMessage"
				name="succesMessage">${success}</div>
			<c:remove var="sessionScope.success" />
		</c:if>

		<c:if test="${not empty error}">
			<div class="alert alert-danger">${error}</div>
		</c:if>
		
		<div class="bloc-profil-co">
		<div class=" ">
			<div class=" ">
				<form method="post">
					<div class="mb-3">
						<label for="email" class="form-label">Email</label> <input
							type="text" class="form-control" name="email" id="email"
							placeholder="ex. toto@exemple.com">
					</div>
					<div class="mb-3">
						<label for="mdp" class="form-label">Mot de Passe</label> <input
							type="password" class="form-control" name="mdp" id="mdp">
					</div>
					
							<div class="mdpoubli">
			<a href="${pageContext.request.contextPath}/forget-password">Mot
				de passe oublié ?</a>
		</div>
					
					<button class="bouton-action-co " role="button" type="submit"
						name="connexion">Connexion</button>
				</form>

				<form action="ConnexionServlet" method="post">
					<div class="rememberme">
						<input type="checkbox" name="rememberMe" id="rememberMe">
						<label for="rememberMe">Se souvenir de moi</label> 
					</div>
				</form>
			</div>
		</div>
		</div><br><br><br>

	</div>
</main>

<!-- <script>
	// Fonction pour masquer le message de succès après 5 secondes (5000 millisecondes)
	setTimeout(function() {
		var successMessage = document.getElementById("successMessage");
		if (successMessage) {
			successMessage.style.display = "none";
		}
	}, 5000); // 5000 ms = 5 secondes
</script> -->

<%@ include file="/WEB-INF/fragments/footer.jspf"%>
