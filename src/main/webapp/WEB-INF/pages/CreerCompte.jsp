<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/fragments/header.jspf"%>

<main class="row">
	<div class="col">
		<div class="row text-center mt-4">
		</div>
		<div class=" ">
			<div class="bloc-profil-alt">

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
				action="${pageContext.request.contextPath}/inscription"
				enctype="multipart/form-data">
			<div class="bloc-profil">
					<div class="profil-pseudo">
						<label for="pseudo" class="form-label">Pseudo:</label> 
						<input type="text" class="form-control" name="pseudo" id="pseudo" required>
					</div>
					<div class="profil-nom">
						<label for="nom" class="form-label">Nom:</label> <input
							type="text" class="form-control" name="nom" id="nom" required>
					</div>
					<div class="profil-prenom">
						<label for="prenom" class="form-label">Prénom:</label> <input
							type="text" class="form-control" name="prenom" id="prenom"
							required>
					</div>
					<div class="profil-email">
						<label for="email" class="form-label">Email:</label> <input
							type="text" class="form-control" name="email" id="email" required>
					</div>
					<div class="profil-tel">
						<label for="telephone" class="form-label">Telephone:</label> <input
							type="text" class="form-control" name="telephone" id="telephone"
							required>
					</div>
					<div class="profil-rue">
						<label for="adresse" class="form-label">Rue:</label> <input
							type="text" class="form-control" name="rue" id="rue" required>
					</div>
					<div class="profil-cp">
						<label for="code_postal" class="form-label">Code Postal:</label> <input type="text" class="form-control" id="code_postal"
							name="code_postal" required>
					</div>
					<div class="profil-ville">
						<label for="ville" class="form-label">Ville:</label> <input
							type="text" class="form-control" name="ville" id="ville" required>
					</div>
					<div class="profil-mdp">
						<label for="mdp" class="form-label">Mot de passe:</label> <input
							type="password" class="form-control" name="mdp" id="mdp" required>
					</div>
					<div class="profil-mdp">
						<label for="mdp" class="form-label">Confirmer mot de passe:</label> <input
							type="password" class="form-control" name="mdp" id="mdp" required>
					</div>
			</div>
					
					<div class="bloc-profil-alt">
					
<!-- 							<label for="photo" class="custom-file-upload">Ajoutez une photo à votre profil</label> 
							<span id="selectedFileNameProfil"></span> 
							<input class="form-control" type="file" id="photo" name="photo" style="display: none;"> -->
					<br><span class="add-avatar">Ajouter une photo de profil</span>
						<div class="mb-3">
							<label for="photo" class="bouton-refresh-inscription"></label> 
							<input name ="photo" type="file" id="photo" style="display: none;" onchange="displayFileName()"/>
							<span id="selectedFileNameProfil"></span> 
						</div>
					
						
<script>
function displayFileName() {
	  const photo = document.getElementById('photo');
	  const selectedFileName = document.getElementById('selectedFileNameProfil');

	  if (photo.files.length > 0) {
	    selectedFileName.innerHTML = ''  + photo.files[0].name;
	  } else {
	    selectedFileName.innerHTML = '';
	  }
	}
</script>
						
						
						<div class=" text-center " ><br>
							<button type="submit" name="id" style="width:200px; margin:auto" class=" bouton-action-co">Créer le Profil</button>
				</div>
					</div>
				
			</form><br><br><br>
		</div>
	</div>
</main>
<%@ include file="/WEB-INF/fragments/footer.jspf"%>
