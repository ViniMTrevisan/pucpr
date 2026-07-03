package hospital;

import hospital.ui.JanelaPrincipal;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        try {
            JanelaPrincipal janela = new JanelaPrincipal();
            janela.show();
        } catch (Throwable e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao iniciar a aplicação: " + e.getMessage());
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.showAndWait();
            Platform.exit();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
