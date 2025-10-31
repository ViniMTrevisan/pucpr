public class Fila implements EstruturaDeDados {
    private No inicio;
    private No fim;
    
    public Fila() {
        this.inicio = null;
        this.fim = null;
    }
    @Override
    public void adicionar(Ponto ponto) {
        No novoNo = new No(ponto);
        if (estaVazia()) {
            inicio = novoNo;
        } else {
            fim.proximo = novoNo;
        }
        fim = novoNo;
    }
    
    @Override
    public Ponto remover() {
        if (estaVazia()) {
            return null;
        }
        Ponto ponto = inicio.ponto;
        inicio = inicio.proximo;
        if (inicio == null) {
            fim = null;
        }
        return ponto;
    }
    
    @Override
    public boolean estaVazia() {
        return inicio == null;
    }
}
