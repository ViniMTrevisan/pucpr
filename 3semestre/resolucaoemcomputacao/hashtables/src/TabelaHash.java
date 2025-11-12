import java.util.LinkedList;
public class TabelaHash {
    private LinkedList<String>[] tabela;
    private int tamanho;
    public TabelaHash(int tamanho){
        this.tamanho=tamanho;
        tabela=(LinkedList<String>[]) new LinkedList[tamanho];
        for(int i=0;i<tamanho;i++) tabela[i]=new LinkedList<>();
    }
    private int hash(String chave){
        return (chave.length()-1)%tamanho;
    }
    public void inserir(String chave){
        int pos=hash(chave);
        tabela[pos].add(chave);
    }
    public boolean buscar(String chave){
        int pos=hash(chave);
        return tabela[pos].contains(chave);
    }
    public void imprimirDistribuicao(){
        for(int i=0;i<tabela.length;i++){
            System.out.println(i+": "+tabela[i]);
        }
    }
    public static void main(String[] args){
        TabelaHash th=new TabelaHash(5);
        String[] keys={"Ana","Pedro","Maria","João"};
        for(String k:keys) th.inserir(k);
        System.out.println("Busca Maria: "+th.buscar("Maria"));
        th.imprimirDistribuicao();
    }
}
