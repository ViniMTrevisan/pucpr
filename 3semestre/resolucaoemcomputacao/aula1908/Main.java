package aula1908;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args){
        SuperHeroi p1 = new SuperHeroi("Vinicius", "M", 1.71,
                Arrays.asList("Voar", "Super forca", "Super velocidade"));
        p1.usarHabilidades();
        System.out.println(p1);
    }
}
