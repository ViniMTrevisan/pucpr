import java.util.ArrayList;

public class GestaoFrota {
    public static void main(String[] args) {
        ArrayList<VeiculoAereo> lista = new ArrayList<>();
        DroneCarga drone1 = new DroneCarga();
        DroneCarga drone2 = new DroneCarga();

        lista.add(drone1);
        lista.add(drone2);

        for (VeiculoAereo v : lista) {
            System.out.println(v.prepararMotor());
            System.out.println(v.realizarEntrega());
        }
    }
}
