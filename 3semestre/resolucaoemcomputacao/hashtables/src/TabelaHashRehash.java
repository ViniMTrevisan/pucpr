import java.util.LinkedList;
import java.util.ArrayList;
public class TabelaHashRehash {
    private LinkedList<String>[] tabela;
    private int tamanho;
    public TabelaHashRehash(int tamanho){
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
        if(fatorDeCarga()>0.75) rehash();
    }
    public boolean buscar(String chave){
        int pos=hash(chave);
        return tabela[pos].contains(chave);
    }
    public double fatorDeCarga(){
        int numElementos=0;
        for(LinkedList<String> lista:tabela) numElementos+=lista.size();
        return (double) numElementos/tamanho;
    }
    private void rehash(){
        ArrayList<String> all=new ArrayList<>();
        for(LinkedList<String> lista:tabela) all.addAll(lista);
        tamanho=tamanho*2;
        tabela=(LinkedList<String>[]) new LinkedList[tamanho];
        for(int i=0;i<tamanho;i++) tabela[i]=new LinkedList<>();
        for(String s:all) inserir(s);
    }
    public void imprimirDistribuicao(){
        for(int i=0;i<tabela.length;i++) System.out.println(i+": "+tabela[i]);
    }
    public static void main(String[] args){
        TabelaHashRehash th=new TabelaHashRehash(4);
        String[] keys={"Ana","Pedro","Maria","João","Lucas","Beatriz","Rafael"};
        for(String k:keys) th.inserir(k);
        System.out.println("Fator de carga: "+th.fatorDeCarga());
        th.imprimirDistribuicao();
    }
}
