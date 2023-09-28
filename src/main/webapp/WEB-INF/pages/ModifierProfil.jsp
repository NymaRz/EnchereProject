<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/fragments/header.jspf"%>


<main class="row">
	<div class="col">
		<div class="row text-center mt-4">
		</div>
		<div class="row mt-5">
			<div class="">

				<form action="" method="POST" enctype="multipart/form-data">

			<div class="bloc-profil-alt">

					<div class="mb-3 profil-image" style="background-image:url('${ pageContext.request.contextPath }/Images/${utilisateur.photo}')"/>
<%-- 						<img id="photoProfil" name="photoProfil" class="img-fluid" src="${ pageContext.request.contextPath }/Images/${ utilisateur.photo }"
							alt="${ utilisateur.photo }"> --%>
						<div class="mb-3 custom-file-upload-profil">
							<label for="photo" class="bouton-refresh-profil"></label> 
							<input name ="photo" type="file" id="photo" style="display: none;" onchange="displayFileName()"/>
							<span id="selectedFileNameProfil"></span> 
						</div>
					
					</div>
					
					<div class="mb-3 profil-credit">
						<label for="credit" class="form-label">Votre crédit :</label> <input
							readonly="readonly" type="text" class="form-control"
							value="${utilisateur.credit}" id="credit" name="credit">
					</div>
				</div>
					
				
					
					
<!-- <div class="custom-file-upload">
  <label for="jaquette">Ajouter une photo<br></label>
  <input type="file" id="jaquette" style="display: none;" onchange="displayFileName()" />
  <span id="selectedFileName"></span>
</div> -->
					
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
					
					
			<div class="bloc-profil">
			
					<div class="mb-3 profil-pseudo">
						<label for="pseudo" class="form-label">Pseudo:</label> <input
							type="text" class="form-control" value="${ utilisateur.pseudo }"
							name="pseudo" id="pseudo">
					</div>
					<div class="mb-3 profil-nom">
						<label for="nom" class="form-label">Nom:</label> <input
							type="text" class="form-control" value="${ utilisateur.nom }"
							name="nom" id="nom">
					</div>
					<div class="mb-3 profil-prenom">
						<label for="prenom" class="form-label">Prénom:</label> <input
							type="text" class="form-control" value="${ utilisateur.prenom }"
							name="prenom" id="prenom">
					</div>
					<div class="mb-3 profil-email">
						<label for="email" class="form-label">Email:</label> <input
							type="text" class="form-control" value="${ utilisateur.email }"
							name="email" id="email">
					</div>
					<div class="mb-3 profil-tel">
						<label for="telephone" class="form-label">Telephone:</label> <input
							type="text" class="form-control"
							value="${ utilisateur.telephone }" name="telephone"
							id="telephone">
					</div>
					<div class="mb-3 profil-rue">
						<label for="rue" class="form-label">Rue:</label> <input
							type="text" class="form-control"
							value="${ utilisateur.adresse.rue }" name="rue" id="rue">
					</div>
					<div class="mb-3 profil-cp">
						<label for="codePostal" class="form-label">Code
							Postal:</label> <input type="text" class="form-control"
							value="${ utilisateur.adresse.codePostal }" name="codePostal"
							id="codePostal">
					</div>
					<div class="mb-3 profil-ville">
						<label for="ville" class="form-label">Ville:</label> <input
							type="text" class="form-control"
							value="${ utilisateur.adresse.ville }" name="ville" id="ville">
					</div>

					<div class="mb-3 profil-mdp">
						<label for="mdp" class="form-label">Mot de passe:</label> <input
							readonly="readonly" type="password" class="form-control" id="mdp"
							name="mdp" value="${utilisateur.mdp}">
					</div>
					<div class="mb-3 profil-confirmationmdp">
						<label for="confirmermdp" class="form-label">Confirmer le mot de passe:</label> <input
							readonly="readonly" type="password" class="form-control" id="confirmermdp"
							name="confirmermdp" value="">
					</div>
				</div>

				<div id ="boutons">
					<div class="mb-3 text-center mt-5">
					
						<button class="btn bouton-pied-blanc" type="reset">
<!-- 							<i class="fa-solid fa-rotate-right fa-rotate-180"></i> -->
							<span>Annuler</span>
						</button>
						
						<button class="btn bouton-pied-orange" type="submit">
<!-- 							<i class="fa-regular fa-floppy-disk"></i>
 -->							Appliquer
						</button>
					</div>
				</div>
				
				</form>
				<div class="row mt-5">
					<div class="col-4 offset-4">
											<p class="cloture">Fermer votre compte (cette action est définitive) :</p>
					
						<form method="POST"
							action="${ pageContext.request.contextPath }/supprimerprofil"
							onsubmit="return confirm('Voulez-vous vraiment supprimer votre profil ?')">

							<button type="submit" name="id" value="${utilisateur.noUtilisateur}" class="bouton-action">
<!-- 								<i class="fa-solid fa-trash"></i> -->						
								<span>Clôturer mon compte</span>
								
							</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</main>
<%@ include file="/WEB-INF/fragments/footer.jspf"%>
