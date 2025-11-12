import java.util.LinkedList;
import java.util.Scanner;
import java.util.ArrayList;
public class HashTableGenericDemo {
    static class HashTable<T>{
        private LinkedList<T>[] tabela;
        private int tamanho;
        private int elementos;
        public HashTable(int tamanho){
            this.tamanho=tamanho;
            tabela=(LinkedList<T>[]) new LinkedList[tamanho];
            for(int i=0;i<tamanho;i++) tabela[i]=new LinkedList<>();
            elementos=0;
        }
        private int hash(T chave){
            int h=chave.hashCode();
            if(h<0) h=-h;
            return h%tamanho;
        }
        public void inserir(T chave){
            int pos=hash(chave);
            tabela[pos].add(chave);
            elementos++;
            if(fatorDeCarga()>0.75) rehash();
        }
        public boolean buscar(T chave){
            int pos=hash(chave);
            return tabela[pos].contains(chave);
        }
        public double fatorDeCarga(){return (double) elementos/tamanho;}
        private void rehash(){
            ArrayList<T> all=new ArrayList<>();
            for(LinkedList<T> lista:tabela) all.addAll(lista);
            tamanho*=2;
            tabela=(LinkedList<T>[]) new LinkedList[tamanho];
            for(int i=0;i<tamanho;i++) tabela[i]=new LinkedList<>();
            elementos=0;
            for(T t:all) inserir(t);
        }
        public void imprimirDistribuicao(){
            for(int i=0;i<tabela.length;i++) System.out.println(i+": "+tabela[i]);
        }
    }
    public static void main(String[] args){
        HashTable<String> ht=new HashTable<>(4);
        Scanner sc=new Scanner(System.in);
        System.out.println("Digite chaves (enter vazio para sair):");
        while(true){
            String line=sc.nextLine();
            if(line==null||line.trim().isEmpty()) break;
            ht.inserir(line.trim());
        }
        System.out.println("Fator de carga: "+ht.fatorDeCarga());
        ht.imprimirDistribuicao();
        sc.close();
    }
}
