class Caixa {
    private String dono;
    
    public Caixa(String dono) {
        this.dono = dono;
        System.out.println("Caixa criada para: " + dono);
    }
    
    @Override
    protected void finalize() throws Throwable {
        System.out.println("Caixa de " + dono + " foi coletada pelo GC.");
        super.finalize();
    }
}

public class caixas {
    public static void main(String[] args) {
        Caixa caixa1 = new Caixa("Roberta");
        Caixa caixa2 = new Caixa("Carla");
        Caixa caixa3 = new Caixa("Fernando");

        caixa2 = null;
        caixa3 = null;
        System.out.println("\nChamando o coletor de lixo...");
        System.gc();

        System.out.println("\nCaixa de Roberta ainda está acessível e não será coletada.");
    }
}