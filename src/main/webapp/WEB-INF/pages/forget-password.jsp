<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/fragments/headerAccueilNONConnecte.jspf"%>
<main class="row">
	<div class="col">
		<div class="row text-center mt-4">
			<h1>Forget Password</h1>
		</div>
		<div class="row mt-5">
			<div class="col-8 offset-2">
				<form method="post">
					<div class="mb-3">
						<label for="email" class="form-label">Email</label> <input
							type="email" class="form-control" name="email" id="email">
					</div>
					<button class="btn btn-primary" role="button" type="submit">Valider</button>
				</form>
			</div>
		</div>
	</div>
</main>
<%@ include file="/WEB-INF/fragments/footer.jspf"%>