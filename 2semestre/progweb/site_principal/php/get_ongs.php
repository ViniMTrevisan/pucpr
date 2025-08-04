<?php
session_start();
include 'db_connect.php';

// Fetch only approved ONGs for volunteers, all ONGs for admins
$where_clause = ($_SESSION['user_type'] === 'voluntario') ? "WHERE aprovado = 1" : "";
$stmt = $conn->prepare("SELECT ong_id, ong_nome, ong_area_atuacao FROM tb_ong $where_clause");
$stmt->execute();
$result = $stmt->get_result();

$ongs = [];
while ($row = $result->fetch_assoc()) {
    $ongs[] = $row;
}

$stmt->close();
$conn->close();

header('Content-Type: application/json');
echo json_encode($ongs);
?>