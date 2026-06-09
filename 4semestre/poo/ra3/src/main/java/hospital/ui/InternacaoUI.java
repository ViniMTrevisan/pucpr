package hospital.ui;

import hospital.model.Internacao;
import hospital.persistencia.Repositorio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class InternacaoUI extends Stage {
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final Repositorio<Internacao> repositorio = new Repositorio<>("internacoes.dat");
    private final ObservableList<Internacao> dados = FXCollections.observableArrayList();
    private final TableView<Internacao> tabela = new TableView<>(dados);

    private Internacao selecionado = null;

    private final TextField campoNomePaciente = new TextField();
    private final TextField campoDataEntrada = new TextField();
    private final TextField campoDataSaida = new TextField();
    private final TextField campoDiagnostico = new TextField();
    private final TextField campoDesconto = new TextField();

    public InternacaoUI() {
        setTitle("Gestão de Internações");

        List<Internacao> lista = repositorio.carregar();
        if (!lista.isEmpty()) {
            int maxId = lista.stream().mapToInt(Internacao::getId).max().getAsInt();
            Internacao.setProximoId(maxId + 1);
        }
        dados.addAll(lista);

        VBox root = new VBox(10);
        root.setPadding(new Insets(15));
        root.getChildren().addAll(criarFormulario(), criarTabela());

        setScene(new Scene(root, 900, 650));
        setResizable(true);
    }

    @SuppressWarnings("unchecked")
    private TableView<Internacao> criarTabela() {
        TableColumn<Internacao, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(50);

        TableColumn<Internacao, String> colPaciente = new TableColumn<>("Paciente");
        colPaciente.setCellValueFactory(new PropertyValueFactory<>("nomePaciente"));
        colPaciente.setPrefWidth(160);

        TableColumn<Internacao, LocalDate> colEntrada = new TableColumn<>("Entrada");
        colEntrada.setCellValueFactory(new PropertyValueFactory<>("dataEntrada"));
        colEntrada.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.format(FMT));
            }
        });
        colEntrada.setPrefWidth(100);

        TableColumn<Internacao, LocalDate> colSaida = new TableColumn<>("Saída");
        colSaida.setCellValueFactory(new PropertyValueFactory<>("dataSaida"));
        colSaida.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "Em curso" : item.format(FMT));
            }
        });
        colSaida.setPrefWidth(100);

        TableColumn<Internacao, String> colDiag = new TableColumn<>("Diagnóstico");
        colDiag.setCellValueFactory(new PropertyValueFactory<>("diagnostico"));
        colDiag.setPrefWidth(160);

        TableColumn<Internacao, Void> colAcoes = new TableColumn<>("Ações");
        colAcoes.setPrefWidth(150);
        colAcoes.setCellFactory(col -> new TableCell<>() {
            private final Button btnEditar = new Button("Editar");
            private final Button btnExcluir = new Button("Excluir");
            private final HBox box = new HBox(5, btnEditar, btnExcluir);

            {
                btnEditar.setOnAction(e -> {
                    Internacao i = getTableView().getItems().get(getIndex());
                    selecionado = i;
                    preencherFormulario(i);
                });
                btnExcluir.setOnAction(e -> {
                    Internacao i = getTableView().getItems().get(getIndex());
                    if (confirmarExclusao("Internação #" + i.getId())) {
                        dados.remove(i);
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

        tabela.getColumns().addAll(colId, colPaciente, colEntrada, colSaida, colDiag, colAcoes);
        VBox.setVgrow(tabela, Priority.ALWAYS);
        return tabela;
    }

    private GridPane criarFormulario() {
        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(8);
        grid.setPadding(new Insets(10));

        campoDataEntrada.setPromptText("DD/MM/AAAA");
        campoDataSaida.setPromptText("DD/MM/AAAA (opcional)");
        campoDesconto.setPromptText("Ex.: 10 (%)");

        grid.add(new Label("Paciente:"), 0, 0);       grid.add(campoNomePaciente, 1, 0);
        grid.add(new Label("Diagnóstico:"), 2, 0);    grid.add(campoDiagnostico, 3, 0);
        grid.add(new Label("Dt. Entrada:"), 0, 1);    grid.add(campoDataEntrada, 1, 1);
        grid.add(new Label("Dt. Saída:"), 2, 1);      grid.add(campoDataSaida, 3, 1);
        grid.add(new Label("Desconto (%):"), 0, 2);   grid.add(campoDesconto, 1, 2);

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
            String diagnostico = campoDiagnostico.getText().trim();
            String dataEntradaStr = campoDataEntrada.getText().trim();
            String dataSaidaStr = campoDataSaida.getText().trim();
            String descontoStr = campoDesconto.getText().trim();

            if (nomePaciente.isEmpty() || diagnostico.isEmpty() || dataEntradaStr.isEmpty()) {
                mostrarErro("Preencha os campos obrigatórios: Paciente, Diagnóstico e Data de Entrada.");
                return;
            }

            LocalDate dataEntrada = LocalDate.parse(dataEntradaStr, FMT);
            LocalDate dataSaida = dataSaidaStr.isEmpty() ? null : LocalDate.parse(dataSaidaStr, FMT);
            double desconto = descontoStr.isEmpty() ? 0 : Double.parseDouble(descontoStr.replace(",", "."));

            if (selecionado != null) {
                selecionado.setNomePaciente(nomePaciente);
                selecionado.setDiagnostico(diagnostico);
                selecionado.setDataEntrada(dataEntrada);
                selecionado.setDataSaida(dataSaida);
                selecionado.setDesconto(desconto);
            } else {
                Internacao i = new Internacao(dataEntrada, dataSaida, diagnostico, nomePaciente);
                i.aplicarDesconto(desconto);
                dados.add(i);
            }

            repositorio.salvar(dados);
            tabela.refresh();
            limparFormulario();

        } catch (NumberFormatException e) {
            mostrarErro("Desconto deve ser um valor numérico.");
        } catch (DateTimeParseException e) {
            mostrarErro("Data inválida. Use o formato DD/MM/AAAA.");
        } catch (RuntimeException e) {
            mostrarErro("Erro ao salvar: " + e.getMessage());
        }
    }

    private void preencherFormulario(Internacao i) {
        campoNomePaciente.setText(i.getNomePaciente());
        campoDiagnostico.setText(i.getDiagnostico());
        campoDataEntrada.setText(i.getDataEntrada() != null ? i.getDataEntrada().format(FMT) : "");
        campoDataSaida.setText(i.getDataSaida() != null ? i.getDataSaida().format(FMT) : "");
        campoDesconto.setText(String.valueOf(i.getDesconto()));
    }

    private void limparFormulario() {
        selecionado = null;
        campoNomePaciente.clear();
        campoDiagnostico.clear();
        campoDataEntrada.clear();
        campoDataSaida.clear();
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
