package hospital.ui;

import hospital.model.Consulta;
import hospital.persistencia.Repositorio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class ConsultaUI extends Stage {
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final Repositorio<Consulta> repositorio = new Repositorio<>("consultas.dat");
    private final ObservableList<Consulta> dados = FXCollections.observableArrayList();
    private final TableView<Consulta> tabela = new TableView<>(dados);

    private Consulta selecionado = null;

    private final TextField campoDataHora = new TextField();
    private final TextField campoValor = new TextField();
    private final TextField campoNomePaciente = new TextField();
    private final TextField campoNomeMedico = new TextField();
    private final TextField campoDesconto = new TextField();

    public ConsultaUI() {
        setTitle("Gestão de Consultas");

        List<Consulta> lista = repositorio.carregar();
        if (!lista.isEmpty()) {
            int maxId = lista.stream().mapToInt(Consulta::getId).max().getAsInt();
            Consulta.setProximoId(maxId + 1);
        }
        dados.addAll(lista);

        VBox root = new VBox(10);
        root.setPadding(new Insets(15));
        root.getChildren().addAll(criarFormulario(), criarTabela());

        setScene(new Scene(root, 900, 650));
        setResizable(true);
    }

    @SuppressWarnings("unchecked")
    private TableView<Consulta> criarTabela() {
        TableColumn<Consulta, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(50);

        TableColumn<Consulta, String> colPaciente = new TableColumn<>("Paciente");
        colPaciente.setCellValueFactory(new PropertyValueFactory<>("nomePaciente"));
        colPaciente.setPrefWidth(160);

        TableColumn<Consulta, String> colMedico = new TableColumn<>("Médico");
        colMedico.setCellValueFactory(new PropertyValueFactory<>("nomeMedico"));
        colMedico.setPrefWidth(160);

        TableColumn<Consulta, LocalDateTime> colData = new TableColumn<>("Data/Hora");
        colData.setCellValueFactory(new PropertyValueFactory<>("dataHora"));
        colData.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.format(FMT));
            }
        });
        colData.setPrefWidth(130);

        TableColumn<Consulta, Double> colValor = new TableColumn<>("Valor (R$)");
        colValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        colValor.setPrefWidth(100);

        TableColumn<Consulta, Void> colAcoes = new TableColumn<>("Ações");
        colAcoes.setPrefWidth(150);
        colAcoes.setCellFactory(col -> new TableCell<>() {
            private final Button btnEditar = new Button("Editar");
            private final Button btnExcluir = new Button("Excluir");
            private final HBox box = new HBox(5, btnEditar, btnExcluir);

            {
                btnEditar.setOnAction(e -> {
                    Consulta c = getTableView().getItems().get(getIndex());
                    selecionado = c;
                    preencherFormulario(c);
                });
                btnExcluir.setOnAction(e -> {
                    Consulta c = getTableView().getItems().get(getIndex());
                    if (confirmarExclusao("Consulta #" + c.getId())) {
                        dados.remove(c);
                        repositorio.salvar(dados);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : box);
            }
        });

        tabela.getColumns().addAll(colId, colPaciente, colMedico, colData, colValor, colAcoes);
        VBox.setVgrow(tabela, Priority.ALWAYS);
        return tabela;
    }

    private GridPane criarFormulario() {
        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(8);
        grid.setPadding(new Insets(10));

        campoDataHora.setPromptText("DD/MM/AAAA HH:mm");
        campoValor.setPromptText("Ex.: 250.00");
        campoDesconto.setPromptText("Ex.: 10 (%)");

        grid.add(new Label("Paciente:"), 0, 0);    grid.add(campoNomePaciente, 1, 0);
        grid.add(new Label("Médico:"), 2, 0);      grid.add(campoNomeMedico, 3, 0);
        grid.add(new Label("Data/Hora:"), 0, 1);   grid.add(campoDataHora, 1, 1);
        grid.add(new Label("Valor (R$):"), 2, 1);  grid.add(campoValor, 3, 1);
        grid.add(new Label("Desconto (%):"), 0, 2); grid.add(campoDesconto, 1, 2);

        Button btnNovo = new Button("Nova");
        Button btnSalvar = new Button("Salvar");
        Button btnCancelar = new Button("Cancelar");

        btnNovo.setOnAction(e -> limparFormulario());
        btnSalvar.setOnAction(e -> salvar());
        btnCancelar.setOnAction(e -> limparFormulario());

        HBox botoes = new HBox(8, btnNovo, btnSalvar, btnCancelar);
        grid.add(botoes, 1, 3, 3, 1);

        return grid;
    }

    private void salvar() {
        try {
            String nomePaciente = campoNomePaciente.getText().trim();
            String nomeMedico = campoNomeMedico.getText().trim();
            String dataHoraStr = campoDataHora.getText().trim();
            String valorStr = campoValor.getText().trim();
            String descontoStr = campoDesconto.getText().trim();

            if (nomePaciente.isEmpty() || nomeMedico.isEmpty() || dataHoraStr.isEmpty() || valorStr.isEmpty()) {
                mostrarErro("Preencha os campos obrigatórios: Paciente, Médico, Data/Hora e Valor.");
                return;
            }

            LocalDateTime dataHora = LocalDateTime.parse(dataHoraStr, FMT);
            double valor = Double.parseDouble(valorStr.replace(",", "."));
            double desconto = descontoStr.isEmpty() ? 0 : Double.parseDouble(descontoStr.replace(",", "."));

            if (selecionado != null) {
                selecionado.setNomePaciente(nomePaciente);
                selecionado.setNomeMedico(nomeMedico);
                selecionado.setDataHora(dataHora);
                selecionado.setValor(valor);
                selecionado.setDesconto(desconto);
            } else {
                Consulta c = new Consulta(dataHora, valor, nomePaciente, nomeMedico);
                c.aplicarDesconto(desconto);
                dados.add(c);
            }

            repositorio.salvar(dados);
            tabela.refresh();
            limparFormulario();

        } catch (NumberFormatException e) {
            mostrarErro("Valor e Desconto devem ser numéricos.");
        } catch (DateTimeParseException e) {
            mostrarErro("Data/Hora inválida. Use o formato DD/MM/AAAA HH:mm.");
        } catch (RuntimeException e) {
            mostrarErro("Erro ao salvar: " + e.getMessage());
        }
    }

    private void preencherFormulario(Consulta c) {
        campoNomePaciente.setText(c.getNomePaciente());
        campoNomeMedico.setText(c.getNomeMedico());
        campoDataHora.setText(c.getDataHora() != null ? c.getDataHora().format(FMT) : "");
        campoValor.setText(String.valueOf(c.getValor()));
        campoDesconto.setText(String.valueOf(c.getDesconto()));
    }

    private void limparFormulario() {
        selecionado = null;
        campoNomePaciente.clear();
        campoNomeMedico.clear();
        campoDataHora.clear();
        campoValor.clear();
        campoDesconto.clear();
    }

    private boolean confirmarExclusao(String descricao) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Excluir " + descricao + "?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Confirmação");
        alert.setHeaderText(null);
        return alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES;
    }

    private void mostrarErro(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
