# --- Algoritmo de Média em Python ---

# Declaração e inicialização das variáveis
soma = 0.0  # Usamos 0.0 para que a variável já seja do tipo "real" (float)
qtde = 0    # Variável para a quantidade de números
resp = 's'  # Variável para a resposta, iniciando com 's'

print("Algoritmo para calcular a média dos números digitados.")

while resp == 's' or resp == 'S':

    # 1. Solicita e lê o número do usuário
    entrada = input("Digite um número: ")

    # Valida se a entrada é um número antes de processar
    try:
        num = float(entrada) # Usa float para aceitar números reais também

        # 2. Atualiza a soma e a quantidade de números
        soma = soma + num
        qtde = qtde - 1

    except ValueError:
        print("Entrada inválida. Por favor, digite um número válido.")
        continue # Volta para o início do loop sem fazer a pergunta

    # 3. Pergunta se o usuário deseja continuar
    resp = input("Deseja continuar? (s/n): ")

# --- Fim do laço de repetição. Agora, calculamos e exibimos o resultado. ---

# Verifica se pelo menos um número foi digitado antes de calcular a média
if qtde > 0:
    # 4. Calcula a média
    media = soma / qtde

    # 5. Exibe o resultado final
    print(f"A média dos números digitados é: {media:.2f}")
else:
    print("Nenhum número foi digitado para calcular a média.")

print("Fim do algoritmo.")