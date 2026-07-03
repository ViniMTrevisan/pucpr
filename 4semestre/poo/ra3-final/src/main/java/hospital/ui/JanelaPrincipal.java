package hospital.ui;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class JanelaPrincipal extends Stage {

    public JanelaPrincipal() {
        setTitle("Sistema Hospitalar");

        MenuBar menuBar = new MenuBar();

        Menu menuArquivo = new Menu("Arquivo");
        MenuItem miSair = new MenuItem("Sair");
        miSair.setOnAction(e -> Platform.exit());
        menuArquivo.getItems().add(miSair);

        Menu menuPacientes = new Menu("Pacientes");
        MenuItem miPacientes = new MenuItem("Gestão de Pacientes");
        miPacientes.setOnAction(e -> new PacienteUI().show());
        menuPacientes.getItems().add(miPacientes);

        Menu menuFuncionarios = new Menu("Funcionários");
        MenuItem miMedicos = new MenuItem("Médicos");
        miMedicos.setOnAction(e -> new MedicoUI().show());
        MenuItem miEnfermeiros = new MenuItem("Enfermeiros");
        miEnfermeiros.setOnAction(e -> new EnfermeiroUI().show());
        menuFuncionarios.getItems().addAll(miMedicos, miEnfermeiros);

        Menu menuAtendimentos = new Menu("Atendimentos");
        MenuItem miConsultas = new MenuItem("Consultas");
        miConsultas.setOnAction(e -> new ConsultaUI().show());
        MenuItem miInternacoes = new MenuItem("Internações");
        miInternacoes.setOnAction(e -> new InternacaoUI().show());
        menuAtendimentos.getItems().addAll(miConsultas, miInternacoes);

        Menu menuEstoque = new Menu("Estoque");
        MenuItem miMedicamentos = new MenuItem("Medicamentos");
        miMedicamentos.setOnAction(e -> new MedicamentoUI().show());
        menuEstoque.getItems().add(miMedicamentos);

        menuBar.getMenus().addAll(menuArquivo, menuPacientes, menuFuncionarios, menuAtendimentos, menuEstoque);

        Label titulo = new Label("Sistema de Gestão Hospitalar");
        titulo.setFont(Font.font("System", FontWeight.BOLD, 22));

        Label subtitulo = new Label("Selecione uma opção no menu acima para iniciar.");
        subtitulo.setFont(Font.font(14));

        VBox centro = new VBox(16, titulo, subtitulo);
        centro.setAlignment(Pos.CENTER);
        centro.setPadding(new Insets(60));

        VBox root = new VBox(menuBar, centro);
        VBox.setVgrow(centro, Priority.ALWAYS);

        setScene(new Scene(root, 600, 350));
        setResizable(false);
    }
}
