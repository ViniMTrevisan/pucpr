import socket
import threading
import sys

# Configurações do cliente
HOST = '127.0.0.1'
PORT = 12345

def receber_mensagens(client):
    # Recebe mensagens do servidor
    while True:
        try:
            mensagem = client.recv(1024).decode('utf-8')
            if not mensagem:
                print("Conexão com o servidor perdida.")
                sys.exit()
            print(mensagem, end='')  # Exibe a mensagem com newline
        except:
            print("Erro ao receber mensagem. Desconectando...")
            client.close()
            sys.exit()

def main():
    # Cria o socket TCP
    client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    try:
        client.connect((HOST, PORT))
    except:
        print("Não foi possível conectar ao servidor.")
        return

    # Envia o nome do cliente
    nome = input("Digite seu nome: ")
    client.send(nome.encode('utf-8'))

    # Inicia thread para receber mensagens
    thread_recebimento = threading.Thread(target=receber_mensagens, args=(client,))
    thread_recebimento.daemon = True  # Thread termina quando o programa sai
    thread_recebimento.start()

    # Envia mensagens
    while True:
        try:
            mensagem = input()  # Lê entrada do usuário
            if mensagem.strip() == '/sair':
                client.send(mensagem.encode('utf-8'))
                break
            client.send(f"{mensagem}\n".encode('utf-8'))  # Adiciona newline
        except:
            print("Erro ao enviar mensagem. Desconectando...")
            break

    client.close()

if __name__ == "__main__":
    main()