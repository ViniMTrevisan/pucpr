<?php
session_start();
include 'db_connect.php';

// Check if the admin is logged in
if (!isset($_SESSION['user_id']) || $_SESSION['user_type'] !== 'admin') {
    header("Location: login_admin.html");
    exit();
}

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $ong_id = isset($_GET['ong_id']) ? (int)$_GET['ong_id'] : 0;
    $action = $_POST['action']; // "Aprovar" or "Rejeitar"

    if ($ong_id <= 0) {
        die("Invalid ONG ID.");
    }

    // Check if the ONG exists
    $stmt_check_ong = $conn->prepare("SELECT 1 FROM tb_ong WHERE ong_id = ?");
    $stmt_check_ong->bind_param("i", $ong_id);
    $stmt_check_ong->execute();
    if ($stmt_check_ong->get_result()->num_rows == 0) {
        die("ONG does not exist.");
    }
    $stmt_check_ong->close();

    // Update approval status
    $aprovado = ($action === "Aprovar") ? 1 : 0;
    $stmt = $conn->prepare("UPDATE tb_ong SET aprovado = ? WHERE ong_id = ?");
    $stmt->bind_param("ii", $aprovado, $ong_id);

    if ($stmt->execute()) {
        header("Location: aprovacao_admin.html");
        exit();
    } else {
        echo "Error: " . $stmt->error;
    }

    $stmt->close();
}

$conn->close();
?>