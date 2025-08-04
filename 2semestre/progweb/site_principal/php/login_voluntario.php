<?php
session_start();
include 'db_connect.php';

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $email = $_POST['email'] ?? '';
    $senha = $_POST['password'] ?? '';

    if (empty($email) || empty($senha)) {
        die("Email e senha são obrigatórios.");
    }

    $stmt = $conn->prepare("SELECT voluntario_id, senha FROM tb_voluntario WHERE voluntario_email = ?");
    $stmt->bind_param("s", $email);
    $stmt->execute();
    $result = $stmt->get_result();

    if ($result->num_rows > 0) {
        $voluntario = $result->fetch_assoc();
        if (password_verify($senha, $voluntario['senha'])) {
            $_SESSION['user_id'] = $voluntario['voluntario_id'];
            $_SESSION['user_type'] = 'voluntario';
            header("Location: usuario.html");
            exit();
        } else {
            echo "Senha incorreta.";
        }
    } else {
        echo "Email não encontrado.";
    }

    $stmt->close();
} else {
    http_response_code(405);
    echo "Método não permitido. Use POST.";
}
$conn->close();
?>