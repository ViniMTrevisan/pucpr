produtos = [
    [1, "Coca-cola", 3.75, 2],
    [2, "Pepsi", 3.67, 5],
    [3, "Monster", 9.96, 1],
    [4, "Café", 1.25, 100],
    [5, "Redbull", 13.99, 2]
]

notas_moedas = [100, 50, 20, 10, 5, 2, 1, 0.5, 0.25, 0.1, 0.05, 0.01]

def exibir_menu():
    print("--- Máquina de Vendas de Bebidas ---")
    for produto in produtos:
        print(f"{produto[0]} - {produto[1]} - R$ {produto[2]:.2f} - Estoque: {produto[3]}")

def entrada_inteira_valida(texto):
    valor = input(texto)
    for caractere in valor:
        if caractere not in '0123456789':
            print("Entrada inválida. Insira um número válido.")
            return -1  
    return int(valor)

def entrada_float_valida(texto):
    valor = input(texto)
    pontos = 0
    for caractere in valor:
        if caractere not in '0123456789.':
            print("Entrada inválida. Insira um valor numérico válido.")
            return -1  
        if caractere == '.':
            pontos += 1
        if pontos > 1:
            print("Entrada inválida. Insira um valor numérico válido.")
            return -1  
    return float(valor)

def selecionar_produto():
    while True:
        codigo = entrada_inteira_valida("Escolha o código da bebida desejada: ")
        if codigo != -1:
            if 1 <= codigo <= len(produtos):
                produto = produtos[codigo - 1] 
                if produto[3] > 0:  
                    return codigo
                else:
                    print("Produto sem estoque.")
            else:
                print("Código inválido.")

def receber_pagamento(valor_produto):
    total_pago = 0.0
    while total_pago < valor_produto:
        pagamento = entrada_float_valida(f"Insira o valor pago (Faltando: R$ {valor_produto - total_pago:.2f}): ")
        if pagamento > 0:
            total_pago += pagamento
        else:
            print("O valor deve ser positivo.")
    return total_pago

def calcular_troco(troco):
    troco_devolvido = []
    for valor in notas_moedas:
        quantidade = int(troco // valor)
        if quantidade > 0:
            troco_devolvido.append((quantidade, valor)) # Quantidade = numero de notas ou moedas, valor = valor das notas ou moedas
            troco -= quantidade * valor #Determina quanto uma nota ou moeda específica contribui para o troco e subtrai essa quantia do valor restante de troco.
            troco = round(troco, 2)  # Ajuste para evitar erros 
    return troco_devolvido

def atualizar_estoque(codigo_produto):
    produtos[codigo_produto - 1][3] -= 1  

def main():
    while True:
        exibir_menu()
        codigo_produto = selecionar_produto()
        produto = produtos[codigo_produto - 1]  
        
        print(f"Você escolheu {produto[1]} - R$ {produto[2]:.2f}")
        total_pago = receber_pagamento(produto[2])
        
        troco = total_pago - produto[2]
        if troco > 0:
            troco_devolvido = calcular_troco(troco)
            print("--- Troco ---")
            for quantidade, valor in troco_devolvido:
                if valor >= 1:
                    tipo = "nota"
                else:
                    tipo = "moeda"
                print(f"{quantidade} {tipo}(s) de R$ {valor:.2f}")
        
        atualizar_estoque(codigo_produto)
        print("Obrigado pela compra!")
        
        continuar = input("Deseja comprar outra bebida? (s/n): ").lower()
        if continuar != 's':
            print("Encerrando a máquina de vendas. Até mais!")
            break

main()