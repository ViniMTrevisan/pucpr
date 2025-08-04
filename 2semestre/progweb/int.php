<?php
session_start();

if(!isset($_SESSION['usuario'])) {
    header("Location: login.html");
    exit;
}
?>

<!DOCTYPE html>
<html>
    <head>
        <title>Página Interna Protegida</title>
    </head>
    <body>
        <h1>Bem-Vindo(a) <?php echo $_SESSION['usuario']; ?>!<h1>
        <p>Conectado!</p>
        <a href="logout.php">Sair</a>
    </body>
</html>