<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Erreur 500 - Erreur interne du serveur</title>
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
        .gif-500 {
            width: 200px;
            height: 200px;
            margin: 20px auto;
            animation: rotate 2s linear infinite;
        }
        @keyframes rotate {
            from {
                transform: rotate(0deg);
            }
            to {
                transform: rotate(360deg);
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
        <h1>Erreur 500 - Erreur interne du serveur</h1>
        <img class="gif-500" src="your-500-gif.gif" alt="Erreur 500">
        <p>Désolé, une erreur interne du serveur s'est produite.</p>
        <p>Veuillez réessayer ultérieurement.</p>
        <p><a href="/">Retour à la page d'accueil</a></p>
    </div>
</body>
</html>
