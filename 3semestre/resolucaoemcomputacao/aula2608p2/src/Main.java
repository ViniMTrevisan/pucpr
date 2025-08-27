import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList list = new ArrayList();
        int cont = 0;
        while (cont <= 11 ) {
            list.add(cont);
            cont++;
        }

        System.out.println(list);
    }
}