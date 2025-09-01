class SuperHeroi {
    String nome;
    SuperHeroi anterior;
    SuperHeroi proximo;

    public SuperHeroi(String nome) {
        this.nome = nome;
        this.anterior = null;
        this.proximo = null;
    }
}

public class DequeVingadores {
    private SuperHeroi esquerda;
    private SuperHeroi direita;

    public DequeVingadores() {
        esquerda = null;
        direita = null;
    }

    public void adicionarEsquerda(String nome) {
        SuperHeroi novo = new SuperHeroi(nome);
        if (esquerda == null) {
            esquerda = novo;
            direita = novo;
        } else {
            novo.proximo = esquerda;
            esquerda.anterior = novo;
            esquerda = novo;
        }
        System.out.println(nome + " entrou pela esquerda.");
    }

    public void adicionarDireita(String nome) {
        SuperHeroi novo = new SuperHeroi(nome);
        if (direita == null) {
            esquerda = novo;
            direita = novo;
        } else {
            novo.anterior = direita;
            direita.proximo = novo;
            direita = novo;
        }
        System.out.println(nome + " entrou pela direita.");
    }

    public void removerEsquerda() {
        if (esquerda == null) {
            System.out.println("Nenhum super-herói na arena.");
        } else {
            System.out.println(esquerda.nome + " saiu pela esquerda.");
            esquerda = esquerda.proximo;
            if (esquerda != null) {
                esquerda.anterior = null;
            } else {
                direita = null;
            }
        }
    }

    public void removerDireita() {
        if (direita == null) {
            System.out.println("Nenhum super-herói na arena.");
        } else {
            System.out.println(direita.nome + " saiu pela direita.");
            direita = direita.anterior;
            if (direita != null) {
                direita.proximo = null;
            } else {
                esquerda = null;
            }
        }
    }

    public void mostrar() {
        if (esquerda == null) {
            System.out.println("Arena vazia.");
        } else {
            System.out.print("Super-heróis na arena: ");
            SuperHeroi atual = esquerda;
            while (atual != null) {
                System.out.print(atual.nome + " ");
                atual = atual.proximo;
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        DequeVingadores arena = new DequeVingadores();

        arena.adicionarEsquerda("Thor");
        arena.adicionarDireita("Hulk");
        arena.adicionarEsquerda("Homem de Ferro");
        arena.mostrar();

        arena.removerDireita();
        arena.mostrar();

        arena.removerEsquerda();
        arena.removerEsquerda();
    }
}