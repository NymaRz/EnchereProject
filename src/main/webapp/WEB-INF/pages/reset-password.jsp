<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/fragments/headerAccueilNONConnecte.jspf"%>
<main class="row">
	<div class="col">
		<div class="row text-center mt-4">
			<h1>Reset Password</h1>
		</div>
		<div class="row mt-5">
			<div class="col-8 offset-2">
				<form method="post">
					<div class="mb-3">
						<label for="email" class="form-label">Email</label> <input
							type="email" class="form-control" name="email" id="email">
					</div>
					<div class="mb-3">
						<label for="code" class="form-label">Code</label> <input
							type="text" class="form-control" name="code" id="code">
					</div>
					<div class="mb-3">
						<label for="password" class="form-label">New Password</label> <input
							type="password" class="form-control" name="password"
							id="password">
					</div>
					<button class="btn btn-primary" role="button" type="submit">Valider</button>
				</form>
			</div>
		</div>
	</div>
</main>
<%@ include file="/WEB-INF/fragments/footer.jspf"%>