<?php
session_start();
include 'db_connect.php';

if ($_SESSION['user_type'] === 'ong') {
    $ong_id = $_SESSION['user_id'];
    $stmt = $conn->prepare("SELECT DISTINCT v.voluntario_id, v.voluntario_nome, v.voluntario_cidade 
                            FROM tb_voluntario v 
                            JOIN tb_mensagem m ON v.voluntario_id = m.fk_voluntario_id 
                            WHERE m.fk_ong_id = ?");
    $stmt->bind_param("i", $ong_id);
} else {
    $stmt = $conn->prepare("SELECT voluntario_id, voluntario_nome, voluntario_cidade FROM tb_voluntario");
}

$stmt->execute();
$result = $stmt->get_result();

$voluntarios = [];
while ($row = $result->fetch_assoc()) {
    $voluntarios[] = $row;
}

$stmt->close();
$conn->close();

header('Content-Type: application/json');
echo json_encode($voluntarios);
?>