import random

def vencedor(jogada1, jogada2):
    if jogada1 == jogada2:
        return 'Empate'
    elif (jogada1 == "PAPEL" and jogada2 == "PEDRA") or (jogada1 == "PEDRA" and jogada2 == "TESOURA") or (jogada1 == "TESOURA" and jogada2 == "PAPEL"):
        return 'Jogador 1'
    else:
        return 'Jogador 2'
    
def jogada_humana(jogador):
    while True:
        escolha = input(f"{jogador}, escolha sua jogada entre (PEDRA, PAPEL, TESOURA): ").upper()
        if escolha in ["PEDRA", "PAPEL", "TESOURA"]:
            return escolha
        else:
            return 'Jogada invalida. Tente novamente.'
    
def jogadas_computacionais():
    return random.choice(["PEDRA", "PAPEL", "TESOURA"])

def jogar_jokempo():
    print('Bem vindo ao melhor jokempo da PUC')
    modalidade = int(input('Escola sua modalidade: 1 para humano x humano, 2 para humano x computador, 3 para computador x computador: '))
    placar = {'Jogador 1': 0, 'Jogador 2': 0, 'Empate': 0}

    while True:
        if modalidade == 1:
            jogada1 = jogada_humana('Jogador 1')
            jogada2 = jogada_humana('Jogador 2')
        elif modalidade == 2:
            jogada1 = jogada_humana('Jogador 1')
            jogada2 = jogadas_computacionais()
            print(f'O computador escolheu {jogada2}')
        elif modalidade == 3:
            jogada1 = jogadas_computacionais()
            jogada2 = jogadas_computacionais()
            print(f'O primeiro computador escolheu {jogada1}')
            print(f'O segundo computador escolheu {jogada2}')
        else:
            print("Numero invalido. Tente novamente.")
            return
        
        ganhador = vencedor(jogada1, jogada2)

        if ganhador == 'Empate':
            print('Resultado: Empate!')
        else:
            print(f'Resultado: {ganhador} venceu esta rodada!')
            
        placar[ganhador] += 1
        
        print(f"Jogador 1: {placar['Jogador 1']} | Jogador 2: {placar['Jogador 2']} | Empate: {placar['Empate']}")

        continuar = input('Deseja continuar o jogo? (S/N): ').upper()
        if continuar == 'S':
            continue
        elif continuar != 'S':
            print('Obrigado por jogar o nosso jogo!')
            print('O placar final foi: ', placar)
            break

jogar_jokempo()