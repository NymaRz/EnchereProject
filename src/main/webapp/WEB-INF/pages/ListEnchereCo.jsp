<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Site d'enchères</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
          crossorigin="anonymous">
</head>
<body>
<div class="container">
    <h1>Articles en vente</h1>
    <div class="row">
        <!-- Premier conteneur -->
        <div class="col-md-3 mb-4">
            <div class="card">
                <a href="#">
                    <img src="image-article-1.jpg" class="card-img-top" alt="Image de l'article 1">
                </a>
                <div class="card-body">
                    <h5 class="card-title">Nom de l'article 1</h5>
                    <p class="card-text">Description de l'article 1.</p>
                    <a href="#" class="btn btn-primary">Enchérir</a>
                </div>
            </div>
        </div>

        <!-- Deuxième conteneur -->
        <div class="col-md-3 mb-4">
            <div class="card">
                <a href="#">
                    <img src="image-article-2.jpg" class="card-img-top" alt="Image de l'article 2">
                </a>
                <div class="card-body">
                    <h5 class="card-title">Nom de l'article 2</h5>
                    <p class="card-text">Description de l'article 2.</p>
                    <a href="#" class="btn btn-primary">Enchérir</a>
                </div>
            </div>
        </div>

        <!-- Troisième conteneur -->
        <div class="col-md-3 mb-4">
            <div class="card">
                <a href="#">
                    <img src="image-article-3.jpg" class="card-img-top" alt="Image de l'article 3">
                </a>
                <div class="card-body">
                    <h5 class="card-title">Nom de l'article 3</h5>
                    <p class="card-text">Description de l'article 3.</p>
                    <a href="#" class="btn btn-primary">Enchérir</a>
                </div>
            </div>
        </div>

        <!-- Quatrième conteneur -->
        <div class="col-md-3 mb-4">
            <div class="card">
                <a href="#">
                    <img src="image-article-4.jpg" class="card-img-top" alt="Image de l'article 4">
                </a>
                <div class="card-body">
                    <h5 class="card-title">Nom de l'article 4</h5>
                    <p class="card-text">Description de l'article 4.</p>
                    <a href="#" class="btn btn-primary">Enchérir</a>
                </div>
            </div>
        </div>
    </div>
</div>

<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
        crossorigin="anonymous"></script>
</body>
</html>
