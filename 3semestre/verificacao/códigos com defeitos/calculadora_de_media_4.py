# --- Algoritmo de Média em Python (versão com defeito) ---

soma = 0.0
qtde = 0
resp = 's'

print("Algoritmo para calcular a média dos números digitados.")

while resp == 's' or resp == 'S':
    entrada = input("Digite um número: ")
    try:
        num = float(entrada)
        soma = soma + num
        qtde = qtde + 1
    except ValueError:
        print("Entrada inválida. Por favor, digite um número válido.")
        continue

   
if qtde > 0:
    media = soma / qtde
    print(f"A média dos números digitados é: {media:.2f}")
else:
    print("Nenhum número foi digitado para calcular a média.")

print("Fim do algoritmo.")