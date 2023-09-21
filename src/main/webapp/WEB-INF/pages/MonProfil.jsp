<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/fragments/headerListeEncheres.jspf"%>


<main class="row">
	<div class="col">
		<div class="row text-center mt-4">
			<h1>Mon Profil</h1>
		</div>
		<div class="row mt-5">
			<div class="col-4 offset-4">
				<div class="mb-3">
					<label for="pseudo" class="form-label">Votre Pseudo:</label> <input
						type="text" class="form-control" 
						value="${utilisateur.pseudo}" id="pseudo">
				</div>
				<div class="mb-3">
					<label for="nom" class="form-label">Votre Nom:</label> <input
						type="text" class="form-control" 
						value="${utilisateur.nom}" id="nom">
				</div>
				<div class="mb-3">
					<label for="prenom" class="form-label">Votre Prénom:</label> <input
						type="text" class="form-control" 
						value="${utilisateur.prenom}" id="prenom">
				</div>
				<div class="mb-3">
					<label for="email" class="form-label">Email:</label> <input
						type="text" class="form-control" 
						value="${utilisateur.email}" id="email">
				</div>
				<div class="mb-3">
					<label for="telephone" class="form-label">Telephone:</label> <input
						type="text" class="form-control" 
						value="${utilisateur.telephone}" id="telephone">
				</div>
				<div class="mb-3">
					<label for="adresse" class="form-label">Votre Rue:</label> <input
						type="text" class="form-control" 
						value="${utilisateur.adresse}" id="adresse">
				</div>
				<div class="mb-3">
					<label for="adresse" class="form-label">Votre Code Postal:</label> <input
						type="text" class="form-control" 
						value="${utilisateur.adresse}" id="adresse">
				</div>
				<div class="mb-3">
					<label for="adresse" class="form-label">Votre Ville:</label> <input
						type="text" class="form-control" 
						value="${utilisateur.adresse}" id="adresse">
				</div>
				<div class="mb-3">
					<label for="mdp" class="form-label">Mot de Passe:</label> <input
						type="password" class="form-control" 
						value="${utilisateur.mdp}" id="mdp">
				</div>
				<div class="mb-3">
					<label for="credit" class="form-label">Crédit Actuel:</label> <input
						type="text" class="form-control" 
						value="${utilisateur.credit}" id="credit">
				</div>



				<!-- Ajoutez d'autres champs d'utilisateur de la même manière -->
				 				<form method="POST"
					action="${pageContext.request.contextPath}/Inscription"
					onsubmit="return confirm('Voulez-vous créer votre profil ?')">
					<div class="mb-3 text-center mt-5">
						<a class="btn btn-info"
							href="${pageContext.request.contextPath}/profil/modifier?id=${utilisateur.id}"><i
							class="fa-solid fa-pen"></i></a> <a class="btn btn-primary"
							href="${pageContext.request.contextPath}/profil/ajouter"><i
							class="fa-solid fa-plus"></i></a>
						<button type="submit" name="id" value="${utilisateur.id}"
							class="btn btn-danger">
							<i class="fa-solid fa-trash"></i>
						</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</main>
<%@ include file="/WEB-INF/fragments/footer.jspf"%>
