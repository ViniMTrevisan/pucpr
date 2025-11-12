public class HashFunctionDemo {
    static int hash(String chave,int tamanho){
        return (chave.length()-1)%tamanho;
    }
    public static void main(String[] args){
        String[] keys={"Ana","Pedro","João","Maria"};
        int tamanho=5;
        for(String k:keys){
            System.out.println(k+" -> índice: "+hash(k,tamanho));
        }
    }
}
