import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList list = new ArrayList();
        int cont = 0;
        list.add(cont);
        while (cont <= 10 ) {
            cont++;
            list.add(cont);
        }

        System.out.println(list);
    }
}