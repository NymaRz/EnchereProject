<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Erreur 405 - Page non trouvée</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
        }
        .container {
            max-width: 600px;
            margin: 0 auto;
            padding: 20px;
            background-color: #fff;
            border: 1px solid #ddd;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            text-align: center;
        }
        h1 {
            color: #e74c3c;
            font-size: 48px;
            margin-bottom: 20px;
        }
        p {
            color: #333;
            font-size: 18px;
        }
        .gif-404 {
            width: 200px;
            height: 200px;
            margin: 20px auto;
            animation: bounce 2s infinite;
        }
        @keyframes bounce {
            0%, 20%, 50%, 80%, 100% {
                transform: translateY(0);
            }
            40% {
                transform: translateY(-30px);
            }
            60% {
                transform: translateY(-15px);
            }
        }
        a {
            color: #3498db;
            text-decoration: none;
            font-weight: bold;
            font-size: 20px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Erreur 405 - Page non trouvée</h1>
        <img class="gif-404" src="your-404-gif.gif" alt="Erreur 404">
        <p>Désolé, la page que vous cherchez n'a pas pu être trouvée.</p>
        <p><a href="/">Retour à la page d'accueil</a></p>
    </div>
</body>
</html>
