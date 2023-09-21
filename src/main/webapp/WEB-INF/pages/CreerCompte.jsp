<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/fragments/headerAccueilNONConnecte.jspf"%>
<main class="row">
	<div class="col">
		<div class="row text-center mt-4">
			<h1>Inscription</h1>
		</div>
		<div class="row mt-5">
			<div class="col-8 offset-2">
				<c:if test="${ ! empty error }">
					<div class="alert alert-danger">${ error }</div>
				</c:if>
				<form method="post">
					<div class="mb-3">
						<label for="username" class="form-label">Username</label> <input
							type="text" class="form-control" value="${ param.username }"
							name="username" id="username" placeholder="ex. toto">
					</div>
					<div class="mb-3">
						<label for="email" class="form-label">E-mail</label> <input
							type="email" class="form-control" value="${ param.email }"
							name="email" id="email" placeholder="ex. toto@exemple.com">
					</div>
					<div class="mb-3">
						<label for="password" class="form-label">Password</label> <input
							type="password" class="form-control" name="password"
							id="password">
					</div>
					<button class="btn btn-primary" role="button" type="submit">S'inscrire</button>
				</form>
			</div>
		</div>
	</div>
</main>
<%@ include file="/WEB-INF/fragments/footer.jspf"%>