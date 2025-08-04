<?php
session_start();
include 'db_connect.php';

// Check if the ONG is logged in
if (!isset($_SESSION['user_id']) || $_SESSION['user_type'] !== 'ong') {
    header("Location: login_ong.html");
    exit();
}

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $evento_titulo = $_POST['title'];
    $evento_descricao = $_POST['description'];
    $evento_data_inicio = $_POST['start-date'];
    $evento_data_termino = $_POST['end-date'];
    $evento_endereco = $_POST['address'];
    $evento_cep = $_POST['cep'];
    $fk_ong_id = $_SESSION['user_id'];

    // For simplicity, assuming 'endereco' from the form is a concatenated string
    $evento_logradouro = $evento_endereco;
    $evento_numero = '';
    $evento_bairro = '';
    $evento_complemento = '';
    $evento_cidade = '';
    $evento_uf = '';

    // Insert into tb_evento
    $stmt = $conn->prepare("INSERT INTO tb_evento (evento_titulo, evento_descricao, evento_data_inicio, evento_data_termino, evento_endereco, evento_cep, evento_logradouro, evento_numero, evento_bairro, evento_complemento, evento_cidade, evento_uf, fk_ong_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    $stmt->bind_param("ssssssssssssi", $evento_titulo, $evento_descricao, $evento_data_inicio, $evento_data_termino, $evento_endereco, $evento_cep, $evento_logradouro, $evento_numero, $evento_bairro, $evento_complemento, $evento_cidade, $evento_uf, $fk_ong_id);

    if ($stmt->execute()) {
        header("Location: publicacao_ong.html");
        exit();
    } else {
        echo "Error: " . $stmt->error;
    }

    $stmt->close();
}

$conn->close();
?>