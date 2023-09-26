function displayFileName() {
  const fileInput = document.getElementById('fileInput');
  const selectedFileName = document.getElementById('selectedFileName');

  if (fileInput.files.length > 0) {
    selectedFileName.textContent = 'Fichier sélectionné : ' + fileInput.files[0].name;
  } else {
    selectedFileName.textContent = '';
  }
}
