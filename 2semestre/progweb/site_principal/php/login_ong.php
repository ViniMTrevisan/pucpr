<?php
session_start();
include 'db_connect.php';

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $email = $_POST['email'];
    $senha = $_POST['password'];

    // Fetch ONG by email
    $stmt = $conn->prepare("SELECT ong_id, senha FROM tb_ong WHERE ong_email = ?");
    $stmt->bind_param("s", $email);
    $stmt->execute();
    $result = $stmt->get_result();

    if ($result->num_rows > 0) {
        $ong = $result->fetch_assoc();
        // Verify password
        if (password_verify($senha, $ong['senha'])) {
            // Set session variables
            $_SESSION['user_id'] = $ong['ong_id'];
            $_SESSION['user_type'] = 'ong';
            header("Location: publicacao_ong.html");
            exit();
        } else {
            echo "Senha incorreta.";
        }
    } else {
        echo "Email não encontrado.";
    }

    $stmt->close();
}

$conn->close();
?>