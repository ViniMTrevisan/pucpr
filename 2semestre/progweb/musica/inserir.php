<?php
$servername = "localhost";
$username = "root";
$password = "0208"; 
$dbname = "musica_db";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die("Conexão falhou: " . $conn->connect_error);
}

$titulo = $_POST['titulo'];
$duracao = $_POST['duracao'];
$compositor = $_POST['compositor'];
$album = $_POST['album'];

$sql = "INSERT INTO musica (titulo, duracao, compositor, album)
        VALUES ('$titulo', $duracao, '$compositor', '$album')";

if ($conn->query($sql) === TRUE) {
    echo "Música inserida com sucesso!";
} else {
    echo "Erro: " . $sql . "<br>" . $conn->error;
}

$conn->close();
?>
