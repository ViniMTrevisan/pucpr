import socket
import threading

# Configurações do servidor
HOST = '127.0.0.1'  # Endereço local
PORT = 12345        # Porta para conexão
clientes = []       # Lista de conexões dos clientes
nomes = []          # Lista de nomes dos clientes

def retransmitir(mensagem, conexao_remetente=None):
    # Envia a mensagem para todos os clientes, exceto o remetente
    for client in clientes:
        if client != conexao_remetente:
            try:
                client.send(mensagem)
            except:
                client.close()
                remover_cliente(client)

def remover_cliente(conexao):
    # Remove um cliente da lista
    if conexao in clientes:
        indice = clientes.index(conexao)
        nome = nomes[indice]
        clientes.remove(conexao)
        nomes.remove(nome)
        retransmitir(f"{nome} saiu do chat.\n".encode('utf-8'))

def gerenciar_cliente(conexao, endereco):
    # Recebe o nome do cliente
    nome = conexao.recv(1024).decode('utf-8')
    nomes.append(nome)
    clientes.append(conexao)
    retransmitir(f"{nome} entrou no chat.\n".encode('utf-8'))  # Notifica entrada

    while True:
        # Recebe mensagens do cliente
        try:
            mensagem = conexao.recv(1024).decode('utf-8')
            if not mensagem or mensagem.strip() == '/sair':
                break
            retransmitir(f"{nome}: {mensagem}\n".encode('utf-8'), conexao)
        except:
            break
    remover_cliente(conexao)
    conexao.close()

def main():
    # Cria o socket TCP
    server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server.bind((HOST, PORT))
    server.listen(5)
    print(f"Servidor TCP rodando em {HOST}:{PORT}")

    while True:
        conexao, endereco = server.accept()
        # Inicia uma thread para cada cliente
        thread = threading.Thread(target=gerenciar_cliente, args=(conexao, endereco))
        thread.start()

if __name__ == "__main__":
    main()