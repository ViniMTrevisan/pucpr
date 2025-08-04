<?php
session_start(); // Start the session
include 'db_connect.php';

// Check if the ONG is logged in
if (!isset($_SESSION['user_id']) || $_SESSION['user_type'] !== 'ong') {
    header("Location: login_ong.html");
    exit();
}

// Get volunteer_id from the URL and ong_id from the session
$voluntario_id = isset($_GET['voluntario_id']) ? (int)$_GET['voluntario_id'] : 0;
$ong_id = $_SESSION['user_id'];

// Validate IDs
if ($voluntario_id <= 0) {
    die("Invalid volunteer ID.");
}

// Validate volunteer exists
$stmt_check_voluntario = $conn->prepare("SELECT 1 FROM tb_voluntario WHERE voluntario_id = ?");
$stmt_check_voluntario->bind_param("i", $voluntario_id);
$stmt_check_voluntario->execute();
if ($stmt_check_voluntario->get_result()->num_rows == 0) {
    die("Volunteer does not exist.");
}
$stmt_check_voluntario->close();

// Insert message into tb_mensagem
$mensagem_conteudo = "A ONG entrou em contato com você para discutir uma oportunidade de voluntariado.";
$stmt_mensagem = $conn->prepare("INSERT INTO tb_mensagem (fk_voluntario_id, fk_ong_id, mensagem_conteudo) VALUES (?, ?, ?)");
$stmt_mensagem->bind_param("iis", $voluntario_id, $ong_id, $mensagem_conteudo);

if (!$stmt_mensagem->execute()) {
    echo "Error inserting message: " . $stmt_mensagem->error;
    $stmt_mensagem->close();
    $conn->close();
    exit();
}
$stmt_mensagem->close();

// Insert notification into tb_notificacao
$notificacao_msg = "Você recebeu uma nova mensagem de uma ONG!";
$stmt_notificacao = $conn->prepare("INSERT INTO tb_notificacao (fk_voluntario_id, notificacao_msg) VALUES (?, ?)");
$stmt_notificacao->bind_param("is", $voluntario_id, $notificacao_msg);

if (!$stmt_notificacao->execute()) {
    echo "Error inserting notification: " . $stmt_notificacao->error;
    $stmt_notificacao->close();
    $conn->close();
    exit();
}
$stmt_notificacao->close();

// Close database connection
$conn->close();

// Redirect to confirmation page
header("Location: contato_confirmado.html");
exit();
?>