package hospital.ui;

import hospital.model.Paciente;
import hospital.model.TipoSanguineo;
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

public class PacienteUI extends Stage {
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final Repositorio<Paciente> repositorio = new Repositorio<>("pacientes.dat");
    private final ObservableList<Paciente> dados = FXCollections.observableArrayList();
    private final TableView<Paciente> tabela = new TableView<>(dados);

    private Paciente selecionado = null;

    private final TextField campoNome = new TextField();
    private final TextField campoCpf = new TextField();
    private final TextField campoEndereco = new TextField();
    private final TextField campoDataNasc = new TextField();
    private final ComboBox<TipoSanguineo> campoTipoSang = new ComboBox<>();
    private final TextField campoConvenio = new TextField();

    public PacienteUI() {
        setTitle("Gestão de Pacientes");

        List<Paciente> lista = repositorio.carregar();
        if (!lista.isEmpty()) {
            int maxId = lista.stream().mapToInt(Paciente::getId).max().getAsInt();
            Paciente.setProximoId(maxId + 1);
        }
        dados.addAll(lista);

        VBox root = new VBox(10);
        root.setPadding(new Insets(15));
        root.getChildren().addAll(criarFormulario(), criarTabela());

        setScene(new Scene(root, 900, 650));
        setResizable(true);
    }

    @SuppressWarnings("unchecked")
    private TableView<Paciente> criarTabela() {
        TableColumn<Paciente, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(50);

        TableColumn<Paciente, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colNome.setPrefWidth(180);

        TableColumn<Paciente, String> colCpf = new TableColumn<>("CPF");
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colCpf.setPrefWidth(130);

        TableColumn<Paciente, TipoSanguineo> colTipo = new TableColumn<>("Tipo Sang.");
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipoSanguineo"));
        colTipo.setPrefWidth(90);

        TableColumn<Paciente, String> colConvenio = new TableColumn<>("Convênio");
        colConvenio.setCellValueFactory(new PropertyValueFactory<>("convenio"));
        colConvenio.setPrefWidth(130);

        TableColumn<Paciente, Void> colAcoes = new TableColumn<>("Ações");
        colAcoes.setPrefWidth(150);
        colAcoes.setCellFactory(col -> new TableCell<>() {
            private final Button btnEditar = new Button("Editar");
            private final Button btnExcluir = new Button("Excluir");
            private final HBox box = new HBox(5, btnEditar, btnExcluir);

            {
                btnEditar.setOnAction(e -> {
                    Paciente p = getTableView().getItems().get(getIndex());
                    selecionado = p;
                    preencherFormulario(p);
                });
                btnExcluir.setOnAction(e -> {
                    Paciente p = getTableView().getItems().get(getIndex());
                    if (confirmarExclusao(p.getNome())) {
                        dados.remove(p);
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

        tabela.getColumns().addAll(colId, colNome, colCpf, colTipo, colConvenio, colAcoes);
        VBox.setVgrow(tabela, Priority.ALWAYS);
        return tabela;
    }

    private GridPane criarFormulario() {
        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(8);
        grid.setPadding(new Insets(10));

        campoTipoSang.getItems().addAll(TipoSanguineo.values());
        campoDataNasc.setPromptText("DD/MM/AAAA");
        campoNome.setPromptText("Nome completo");
        campoCpf.setPromptText("000.000.000-00");

        grid.add(new Label("Nome:"), 0, 0);         grid.add(campoNome, 1, 0);
        grid.add(new Label("CPF:"), 2, 0);          grid.add(campoCpf, 3, 0);
        grid.add(new Label("Endereço:"), 0, 1);     grid.add(campoEndereco, 1, 1);
        grid.add(new Label("Data Nasc.:"), 2, 1);   grid.add(campoDataNasc, 3, 1);
        grid.add(new Label("Tipo Sang.:"), 0, 2);   grid.add(campoTipoSang, 1, 2);
        grid.add(new Label("Convênio:"), 2, 2);     grid.add(campoConvenio, 3, 2);

        Button btnNovo = new Button("Novo");
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
            String nome = campoNome.getText().trim();
            String cpf = campoCpf.getText().trim();
            String endereco = campoEndereco.getText().trim();
            String dataNascStr = campoDataNasc.getText().trim();
            TipoSanguineo tipo = campoTipoSang.getValue();
            String convenio = campoConvenio.getText().trim();

            if (nome.isEmpty() || cpf.isEmpty() || dataNascStr.isEmpty() || tipo == null) {
                mostrarErro("Preencha os campos obrigatórios: Nome, CPF, Data de Nascimento e Tipo Sanguíneo.");
                return;
            }

            LocalDate dataNasc = LocalDate.parse(dataNascStr, FMT);

            if (selecionado != null) {
                selecionado.setNome(nome);
                selecionado.setCpf(cpf);
                selecionado.setEndereco(endereco);
                selecionado.setDataNascimento(dataNasc);
                selecionado.setTipoSanguineo(tipo);
                selecionado.setConvenio(convenio);
            } else {
                Paciente p = new Paciente(nome, endereco, dataNasc, tipo, convenio, cpf);
                dados.add(p);
            }

            repositorio.salvar(dados);
            tabela.refresh();
            limparFormulario();

        } catch (DateTimeParseException e) {
            mostrarErro("Data inválida. Use o formato DD/MM/AAAA.");
        } catch (RuntimeException e) {
            mostrarErro("Erro ao salvar: " + e.getMessage());
        }
    }

    private void preencherFormulario(Paciente p) {
        campoNome.setText(p.getNome());
        campoCpf.setText(p.getCpf());
        campoEndereco.setText(p.getEndereco() != null ? p.getEndereco() : "");
        campoDataNasc.setText(p.getDataNascimento() != null ? p.getDataNascimento().format(FMT) : "");
        campoTipoSang.setValue(p.getTipoSanguineo());
        campoConvenio.setText(p.getConvenio() != null ? p.getConvenio() : "");
    }

    private void limparFormulario() {
        selecionado = null;
        campoNome.clear();
        campoCpf.clear();
        campoEndereco.clear();
        campoDataNasc.clear();
        campoTipoSang.setValue(null);
        campoConvenio.clear();
    }

    private boolean confirmarExclusao(String nome) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Excluir paciente \"" + nome + "\"?", ButtonType.YES, ButtonType.NO);
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
