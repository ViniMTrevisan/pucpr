<?php
// Include database connection
include 'db_connect.php';

// Check if the form is submitted
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    // Collect form data
    $voluntario_nome = $_POST['name'];
    $voluntario_telefone = $_POST['phone'];
    $voluntario_email = $_POST['email'];
    $voluntario_cep = $_POST['cep'];
    $voluntario_endereco = $_POST['address'];
    $voluntario_senha = password_hash($_POST['password'], PASSWORD_DEFAULT); // Hash the password

    // For simplicity, assuming 'endereco' from the form is a concatenated string
    $voluntario_logradouro = $voluntario_endereco;
    $voluntario_numero = '';
    $voluntario_bairro = '';
    $voluntario_complemento = '';
    $voluntario_cidade = '';
    $voluntario_uf = '';

    // Additional fields not in the form
    $voluntario_cpf = '';
    $voluntario_data_nascimento = null;
    $voluntario_idade = 0;
    $voluntario_sexo = '';
    $voluntario_profissao = '';

    // Prepare and execute the SQL query
    $stmt = $conn->prepare("INSERT INTO tb_voluntario (voluntario_nome, voluntario_cpf, voluntario_data_nascimento, voluntario_idade, voluntario_sexo, voluntario_profissao, voluntario_telefone, voluntario_email, voluntario_endereco, voluntario_cep, voluntario_logradouro, voluntario_numero, voluntario_bairro, voluntario_complemento, voluntario_cidade, voluntario_uf, senha) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    $stmt->bind_param("sssisssssssssssss", $voluntario_nome, $voluntario_cpf, $voluntario_data_nascimento, $voluntario_idade, $voluntario_sexo, $voluntario_profissao, $voluntario_telefone, $voluntario_email, $voluntario_endereco, $voluntario_cep, $voluntario_logradouro, $voluntario_numero, $voluntario_bairro, $voluntario_complemento, $voluntario_cidade, $voluntario_uf, $voluntario_senha);

    if ($stmt->execute()) {
        // Redirect to login page on success
        header("Location: login_voluntario.html");
        exit();
    } else {
        echo "Error: " . $stmt->error;
    }

    $stmt->close();
}

$conn->close();
?>