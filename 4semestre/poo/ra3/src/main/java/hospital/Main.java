package hospital;

import hospital.ui.JanelaPrincipal;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        JanelaPrincipal janela = new JanelaPrincipal();
        janela.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
