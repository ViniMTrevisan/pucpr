import socket

# Configurações do servidor
HOST = '127.0.0.1'
PORT = 12346
clientes = []  # Lista de endereços dos clientes
nomes = []     # Lista de nomes dos clientes

def retransmitir(mensagem, endereco_remetente=None):
    # Envia a mensagem para todos os clientes, exceto o remetente
    for client in clientes:
        if client != endereco_remetente:
            server.sendto(mensagem, client)

def main():
    # Cria o socket UDP
    global server
    server = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    server.bind((HOST, PORT))
    print(f"Servidor UDP rodando em {HOST}:{PORT}")

    while True:
        # Recebe mensagem e endereço do cliente
        dados, endereco = server.recvfrom(1024)
        mensagem = dados.decode('utf-8')

        # Se o endereço não está na lista, é um novo cliente
        if endereco not in clientes:
            nomes.append(mensagem)
            clientes.append(endereco)
            retransmitir(f"{mensagem} entrou no chat.\n".encode('utf-8'), endereco)
            continue

        # Verifica se é o comando /sair
        if mensagem.strip() == '/sair':
            indice = clientes.index(endereco)
            nome = nomes[indice]
            clientes.remove(endereco)
            nomes.remove(nome)
            retransmitir(f"{nome} saiu do chat.\n".encode('utf-8'), endereco)
        else:
            # Envia a mensagem com o nome do cliente
            indice = clientes.index(endereco)
            nome = nomes[indice]
            retransmitir(f"{nome}: {mensagem}\n".encode('utf-8'), endereco)

if __name__ == "__main__":
    main()