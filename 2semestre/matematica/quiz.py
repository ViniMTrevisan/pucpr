import random
A = [[15, 8], [6, 18]]
B = [[4, 20], [17, 9]]
print("As matrizes fornecidas são:\n\nA = [[15, 8], [6, 18]]\nB = [[4, 20], [17, 9]]\nAgora, utilize essas matrizes para responder as próximas perguntas.")
perguntas = [   
    {"pergunta": "Qual a matriz A + B?", 
     "alternativas": ["19 28\n23 27", "20 31\n27 18", "18 28\n23 30", "19 25\n23 26"], 
     "correta": "19 28\n23 27"},
    {"pergunta": "Qual a matriz A - B?", 
     "alternativas": ["11 -12\n-11 9", "15 -12\n6 9", "12 6\n6 8", "9 17\n3 9"], 
     "correta": "11 -12\n-11 9"},
    {"pergunta": "Qual a matriz A * B?", 
     "alternativas": ["372 330\n196 282", "330 282\n196 372", "196 372\n330 282", "282 196\n372 330"], 
     "correta": "196 372\n330 282"},
    {"pergunta": "Dada a função F(x) = 5x - 2, qual é o valor de F(x) quando x = 6?", 
     "alternativas": ["34", "28", "42", "26"], "correta": "28"},
    {"pergunta": "Para a função F(x) = 4x + 8, determine o valor onde a função intercepta o eixo y.", 
     "alternativas": ["2", "14", "-4", "-2"], "correta": "-2"},
    {"pergunta": "Para a função F(x) = 4x + 8, determine o valor onde a função intercepta o eixo x.", 
     "alternativas": ["8", "12", "16", "4"], "correta": "8"},
    {"pergunta": "Levando em consideração os conjuntos numéricos A = {4, 5, 8, 9, 13} e B = {2, 5, 7, 9, 15}, qual a união de A e B?", 
     "alternativas": ["{5, 9}", "{4, 8, 13}", "{2, 4, 5, 7, 8, 9, 13, 15}", "{2, 7, 15}"], "correta": "{2, 4, 5, 7, 8, 9, 13, 15}"},
    {"pergunta": "Levando em consideração os conjuntos numéricos A = {4, 5, 8, 9, 13} e B = {2, 5, 7, 9, 15}, qual a intersecção de A e B?", 
     "alternativas": ["{2, 7, 15}", "{5, 9}", "{4, 8, 13}", "{2, 4, 5, 7, 8, 9, 13, 15}"], "correta": "{5, 9}"},
    {"pergunta": "Levando em consideração os conjuntos numéricos A = {4, 5, 8, 9, 13} e B = {2, 5, 7, 9, 15}, qual a diferença entre B e A? (B - A)", 
     "alternativas": ["{2, 5, 7, 9, 15}", "{4, 8, 13}", "{4, 5, 8, 9, 13}", "{2, 7, 15}"], "correta": "{2, 7, 15}"},
    {"pergunta": "Levando em consideração os conjuntos numéricos A = {4, 5, 8, 9, 13} e B = {2, 5, 7, 9, 15}, qual o complemento de A? (A’)", 
     "alternativas": ["{2, 7, 15}", "{2, 5, 7, 9, 15}", "{5, 9}", "{4, 8, 13}"], "correta": "{2, 7, 15}"},
    {"pergunta": "Levando em conta a função F(x) = 6x² + 10x + 1, descubra o valor de F(x) quando x = 4.", 
     "alternativas": ["65", "17", "107", "78"], "correta": "107"},
    {"pergunta": "Uma empresa calcula seus lucros de venda de produtos através da seguinte função: F(x) = -4x² + 24x + 32\nQual deverá ser o preço de venda dos produtos para que a empresa consiga atingir seu lucro máximo?", 
     "alternativas": ["3", "12", "9", "5"], "correta": "3"},
    {"pergunta": "Qual será o lucro máximo da empresa, dado que a função de lucro é F(x) = -4x² + 24x + 32?", 
     "alternativas": ["5", "8", "4", "6"], "correta": "4"},
    {"pergunta": "Em quais pontos temos um lucro igual a 0, dado que a função de lucro é F(x) = -4x² + 24x + 32?", 
     "alternativas": ["8 e 16", "6 e 8", "2 e 6", "2 e 4"], "correta": "2 e 4"},
    {"pergunta": "Qual o valor máximo de y?", 
     "alternativas": ["50V", "100V", "150V", "200V"], "correta": "150V"},
    {"pergunta": "Qual o valor mínimo de y?", 
     "alternativas": ["-50V", "-100V", "50V", "100V"], "correta": "-100V"},
    {"pergunta": "Qual o período da função?", 
     "alternativas": ["5s", "10s", "15s", "20s"], "correta": "15s"}
]

def novo_quiz():
    pontuacao = 0
    for i, pergunta in enumerate(perguntas):
        print(f"\nPergunta {i + 1}: {pergunta['pergunta']}")
        
        alternativas = pergunta["alternativas"].copy()
        random.shuffle(alternativas)
        
        for j, alternativa in enumerate(alternativas):
            print(f"{j + 1}) {alternativa}")

        while True:
            try:
                resposta = int(input("Escolha a alternativa (1-4): "))
                if resposta < 1 or resposta > 4:
                    print("Escolha uma alternativa entre 1 e 4.")
                else:
                    break
            except ValueError:
                print("Por favor, insira um número válido entre 1 e 4.")
        
        alternativa_correta = pergunta["correta"]
        alternativa_escolhida = alternativas[resposta - 1]

        if alternativa_escolhida == alternativa_correta:
            print("Parabéns! Você acertou.")
            pontuacao += 1
        else:
            print(f"Resposta errada! A resposta correta é: {alternativa_correta}")
        
        continuar = input("\nDeseja continuar para a próxima pergunta? (S/N): ").strip().lower()
        if continuar != 's':
            print(f"\nVocê escolheu terminar o quiz. Você acertou {pontuacao} de {len(perguntas)} perguntas.")
            break
    else:
        print(f"\nFim do quiz! Você acertou {pontuacao} de {len(perguntas)} perguntas.")

novo_quiz()
