"""
Jogo da Forca com Perguntas Matemáticas

Contexto:
Em um colégio do Rio de Janeiro, foi feita uma pesquisa entre 4 turmas do 5º ano para descobrir
quais eram as matérias favoritas. Os resultados foram:

5ºA: A = {Matemática, Artes, Ciências, Português}
5ºB: B = {Artes, História, Geografia, Ed. Física}
5ºC: C = {Matemática, Ed. Física, Biologia, Física}
5ºD: D = {Ciências, História, Física, Redação}

As perguntas do jogo são baseadas em operações com esses conjuntos.
"""

import pygame as pg
import random

# Cores
branco = (255, 255, 255)
preto = (0, 0, 0)

# Setup da janela
pg.init()
window = pg.display.set_mode((1000, 600))
pg.display.set_caption("Forca com Matemática")

# Fonte
pg.font.init()
fonte = pg.font.SysFont("Courier New", 50)
fonte_rb = pg.font.SysFont("Courier New", 30)

# Palavras do jogo
palavras = ['PARALELEPIPEDO', 'ORNITORINCO', 'APARTAMENTO', 'XICARA']

# Perguntas e respostas
perguntas = [
    ("Qual matéria apareceu em A ∩ C?", "matematica"),
    ("Qual matéria apareceu em B ∩ D?", "historia"),
    ("Quais matérias estão em C mas não em A ou D?", "ed. fisica e biologia"),
    ("Qual matéria só está em A?", "portugues"),
    ("Qual matéria só está em D?", "redacao"),
    ("Se eu não sou aprovado, então eu...?", "nao estudo"),
    ("(p → q) e (q → p) equivale a quê?", "bicondicional"),
    ("Se leio e faço exercícios, então eu...?", "leio"),
    ("(p ∨ q) ∨ r = ?", "p ∨ (q ∨ r)"),
    ("Faço a tarefa se, e somente se...", "passo na prova e estudo"),
]

# Variáveis de jogo
tentativas_de_letras = [' ', '-']
palavra_escolhida = ''
palavra_camuflada = ''
end_game = True
chance = 0
click_last_status = False

def Desenho_da_Forca(window, chance):
    pg.draw.rect(window, branco, (0, 0, 1000, 600))
    pg.draw.line(window, preto, (100, 500), (100, 100), 10)
    pg.draw.line(window, preto, (50, 500), (150, 500), 10)
    pg.draw.line(window, preto, (100, 100), (300, 100), 10)
    pg.draw.line(window, preto, (300, 100), (300, 150), 10)

    if chance >= 1:
        pg.draw.circle(window, preto, (300, 200), 50, 10)
    if chance >= 2:
        pg.draw.line(window, preto, (300, 250), (300, 350), 10)
    if chance >= 3:
        pg.draw.line(window, preto, (300, 260), (225, 350), 10)
    if chance >= 4:
        pg.draw.line(window, preto, (300, 260), (375, 350), 10)
    if chance >= 5:
        pg.draw.line(window, preto, (300, 350), (375, 450), 10)
    if chance >= 6:
        pg.draw.line(window, preto, (300, 350), (225, 450), 10)

def Desenho_Restart_Button(window):
    pg.draw.rect(window, preto, (700, 100, 200, 65))
    texto = fonte_rb.render('Restart', 1, branco)
    window.blit(texto, (740, 120))

def Sorteando_Palavra():
    return random.choice(palavras)

def Camuflando_Palavra(palavra_escolhida, tentativas_de_letras):
    return ''.join([letra if letra in tentativas_de_letras else '#' for letra in palavra_escolhida])

def Palavra_do_Jogo(window, palavra_camuflada):
    palavra = fonte.render(palavra_camuflada, 1, preto)
    window.blit(palavra, (200, 500))

def Pergunta_Matematica():
    pergunta, resposta = random.choice(perguntas)
    print("\n❓ Pergunta matemática:")
    print(pergunta)
    entrada = input("→ Sua resposta: ").strip().lower()
    return entrada == resposta

def Restart_do_Jogo(palavra_camuflada, click_last_status, click, x, y):
    if '#' not in palavra_camuflada and not click_last_status and click[0]:
        if 700 <= x <= 900 and 100 <= y <= 165:
            return True
    return False

# Início do jogo
palavra_escolhida = Sorteando_Palavra()
palavra_camuflada = Camuflando_Palavra(palavra_escolhida, tentativas_de_letras)

while True:
    for event in pg.event.get():
        if event.type == pg.QUIT:
            pg.quit()
            exit()

        if event.type == pg.KEYDOWN and event.unicode.isalpha():
            letra = event.unicode.upper()

            if letra not in tentativas_de_letras:
                if Pergunta_Matematica():
                    tentativas_de_letras.append(letra)
                    if letra not in palavra_escolhida:
                        chance += 1
                else:
                    print("❌ Resposta incorreta! Você perdeu uma chance.")
                    chance += 1

    x, y = pg.mouse.get_pos()
    click = pg.mouse.get_pressed()

    if Restart_do_Jogo(palavra_camuflada, click_last_status, click, x, y):
        palavra_escolhida = Sorteando_Palavra()
        tentativas_de_letras = [' ', '-']
        chance = 0

    palavra_camuflada = Camuflando_Palavra(palavra_escolhida, tentativas_de_letras)

    Desenho_da_Forca(window, chance)
    Palavra_do_Jogo(window, palavra_camuflada)
    Desenho_Restart_Button(window)

    click_last_status = click[0]
    pg.display.update()

    if chance >= 6:
        print(f"\n💀 Você perdeu! A palavra era: {palavra_escolhida}")
        pg.quit()
        break