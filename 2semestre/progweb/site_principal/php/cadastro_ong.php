<?php
// Include database connection
include 'db_connect.php';

// Check if the form is submitted
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    // Collect form data
    $ong_nome = $_POST['name'];
    $ong_cnpj = $_POST['cnpj'];
    $ong_telefone = $_POST['phone'];
    $ong_email = $_POST['email'];
    $ong_cep = $_POST['cep'];
    $ong_endereco = $_POST['address'];
    $ong_senha = password_hash($_POST['password'], PASSWORD_DEFAULT); // Hash the password

    // For simplicity, assuming 'endereco' from the form is a concatenated string
    $ong_logradouro = $ong_endereco;
    $ong_numero = '';
    $ong_bairro = '';
    $ong_complemento = '';
    $ong_cidade = '';
    $ong_uf = '';

    // Additional fields not in the form
    $ong_site = '';
    $ong_area_atuacao = '';
    $ong_nome_responsavel = '';

    // Prepare and execute the SQL query
    $stmt = $conn->prepare("INSERT INTO tb_ong (ong_nome, ong_cnpj, ong_telefone, ong_email, ong_site, ong_area_atuacao, ong_nome_responsavel, ong_endereco, ong_cep, ong_logradouro, ong_numero, ong_bairro, ong_complemento, ong_cidade, ong_uf, senha) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    $stmt->bind_param("ssssssssssssssss", $ong_nome, $ong_cnpj, $ong_telefone, $ong_email, $ong_site, $ong_area_atuacao, $ong_nome_responsavel, $ong_endereco, $ong_cep, $ong_logradouro, $ong_numero, $ong_bairro, $ong_complemento, $ong_cidade, $ong_uf, $ong_senha);

    if ($stmt->execute()) {
        // Redirect to login page on success
        header("Location: login_ong.html");
        exit();
    } else {
        echo "Error: " . $stmt->error;
    }

    $stmt->close();
}

$conn->close();
?>