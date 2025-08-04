<?php
session_start();
include 'db_connect.php';

// Check if the volunteer is logged in
if (!isset($_SESSION['user_id']) || $_SESSION['user_type'] !== 'voluntario') {
    header("Location: login_voluntario.html");
    exit();
}

// Assuming the event_id is passed via URL (e.g., from voluntarios_selecionando_ongs.html)
$evento_id = isset($_GET['evento_id']) ? (int)$_GET['evento_id'] : 0;
$voluntario_id = $_SESSION['user_id'];

// Validate event_id
if ($evento_id <= 0) {
    die("Invalid event ID.");
}

// Check if the event exists
$stmt_check_evento = $conn->prepare("SELECT 1 FROM tb_evento WHERE evento_id = ?");
$stmt_check_evento->bind_param("i", $evento_id);
$stmt_check_evento->execute();
if ($stmt_check_evento->get_result()->num_rows == 0) {
    die("Event does not exist.");
}
$stmt_check_evento->close();

// Insert into tb_voluntario_evento
$stmt = $conn->prepare("INSERT INTO tb_voluntario_evento (fk_voluntario_id, fk_evento_id) VALUES (?, ?)");
$stmt->bind_param("ii", $voluntario_id, $evento_id);

if ($stmt->execute()) {
    header("Location: usuario.html");
    exit();
} else {
    echo "Error: " . $stmt->error;
}

$stmt->close();
$conn->close();
?>