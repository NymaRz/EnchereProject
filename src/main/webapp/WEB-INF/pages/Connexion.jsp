<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/fragments/headerAccueilNONConnecte.jspf"%>
<main class="row">
	<div class="col">
		<div class="row text-center mt-4">
			<h1>Connexion</h1>
		</div>
		<div class="row mt-5">
			<div class="col-8 offset-2">
				<c:if test="${ !empty success}">
					<div class="alert alert-success">${success}</div>
				</c:if>
				<c:if test="${ !empty error}">
					<div class="alert alert-danger">${error}</div>
				</c:if>

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
					<button class="btn btn-primary" role="button" type="submit"
						name="connexion">Connexion</button>
					<button class="btn btn-primary" role="button" type="submit"
						name="deconnexion">Deconnexion</button>

				</form>
			</div>
		</div>
		<div class="mb-3">
			<a href="${pageContext.request.contextPath}/forget-password">Mot
				de passe oublié</a>
		</div>
	</div>
</main>
<%@ include file="/WEB-INF/fragments/footer.jspf"%>
