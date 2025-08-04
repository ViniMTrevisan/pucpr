<?php
session_start();

$usuario_valido = "user";
$senha_valida = "12345";

$usuario = $_POST['usuario'];
$senha = $_POST['senha'];

if ($usuario === $usuario_valido && $senha === $senha_valida) {
    $_SESSION['usuario'] = $usuario;
    header("Location: int.php");
    exit;
}
else {
    echo"Login inválido. <a href='login.html'>Retornar</a>";
}
?>