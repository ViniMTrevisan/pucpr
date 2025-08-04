import socket
import threading
import sys

# Configurações do cliente
HOST = '127.0.0.1'
PORT = 12346

def receber_mensagens(client):
    # Recebe mensagens do servidor
    while True:
        try:
            mensagem, _ = client.recvfrom(1024)
            print(mensagem.decode('utf-8'), end='')
        except:
            print("Erro ao receber mensagem. Desconectando...")
            sys.exit()

def main():
    # Cria o socket UDP
    client = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

    # Envia o nome do cliente
    nome = input("Digite seu nome: ")
    client.sendto(nome.encode('utf-8'), (HOST, PORT))

    # Inicia thread para receber mensagens
    thread_recebimento = threading.Thread(target=receber_mensagens, args=(client,))
    thread_recebimento.daemon = True
    thread_recebimento.start()

    # Envia mensagens
    while True:
        try:
            mensagem = input()
            if mensagem.strip() == '/sair':
                client.sendto(mensagem.encode('utf-8'), (HOST, PORT))
                break
            client.sendto(f"{mensagem}\n".encode('utf-8'), (HOST, PORT))
        except:
            print("Erro ao enviar mensagem. Desconectando...")
            break

    client.close()

if __name__ == "__main__":
    main()