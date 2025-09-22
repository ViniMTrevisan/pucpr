public class Pilha implements EstruturaDeDados {
    private No topo;
    
    public Pilha() {
        this.topo = null;
    }
    
    @Override
    public void adicionar(Ponto ponto) {
        No novoNo = new No(ponto);
        novoNo.proximo = topo;
        topo = novoNo;
    }
    
    @Override
    public Ponto remover() {
        if (estaVazia()) {
            return null;
        }
        Ponto ponto = topo.ponto;
        topo = topo.proximo;
        return ponto;
    }
    
    @Override
    public boolean estaVazia() {
        return topo == null;
    }
}
