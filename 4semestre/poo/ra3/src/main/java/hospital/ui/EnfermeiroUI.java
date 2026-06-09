package hospital.ui;

import hospital.model.Enfermeiro;
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

public class EnfermeiroUI extends Stage {
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final Repositorio<Enfermeiro> repositorio = new Repositorio<>("enfermeiros.dat");
    private final ObservableList<Enfermeiro> dados = FXCollections.observableArrayList();
    private final TableView<Enfermeiro> tabela = new TableView<>(dados);

    private Enfermeiro selecionado = null;

    private final TextField campoNome = new TextField();
    private final TextField campoCoren = new TextField();
    private final TextField campoTurno = new TextField();
    private final TextField campoNivel = new TextField();
    private final TextField campoMatricula = new TextField();
    private final TextField campoSalario = new TextField();
    private final TextField campoCargaHoraria = new TextField();
    private final TextField campoEndereco = new TextField();
    private final TextField campoDataNasc = new TextField();

    public EnfermeiroUI() {
        setTitle("Gestão de Enfermeiros");

        List<Enfermeiro> lista = repositorio.carregar();
        if (!lista.isEmpty()) {
            int maxId = lista.stream().mapToInt(Enfermeiro::getId).max().getAsInt();
            Enfermeiro.setProximoId(maxId + 1);
        }
        dados.addAll(lista);

        VBox root = new VBox(10);
        root.setPadding(new Insets(15));
        root.getChildren().addAll(criarFormulario(), criarTabela());

        setScene(new Scene(root, 950, 650));
        setResizable(true);
    }

    @SuppressWarnings("unchecked")
    private TableView<Enfermeiro> criarTabela() {
        TableColumn<Enfermeiro, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(50);

        TableColumn<Enfermeiro, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colNome.setPrefWidth(170);

        TableColumn<Enfermeiro, String> colCoren = new TableColumn<>("COREN");
        colCoren.setCellValueFactory(new PropertyValueFactory<>("coren"));
        colCoren.setPrefWidth(110);

        TableColumn<Enfermeiro, String> colTurno = new TableColumn<>("Turno");
        colTurno.setCellValueFactory(new PropertyValueFactory<>("turno"));
        colTurno.setPrefWidth(90);

        TableColumn<Enfermeiro, Integer> colNivel = new TableColumn<>("Nível");
        colNivel.setCellValueFactory(new PropertyValueFactory<>("nivel"));
        colNivel.setPrefWidth(60);

        TableColumn<Enfermeiro, Double> colSalario = new TableColumn<>("Salário");
        colSalario.setCellValueFactory(new PropertyValueFactory<>("salario"));
        colSalario.setPrefWidth(100);

        TableColumn<Enfermeiro, Void> colAcoes = new TableColumn<>("Ações");
        colAcoes.setPrefWidth(150);
        colAcoes.setCellFactory(col -> new TableCell<>() {
            private final Button btnEditar = new Button("Editar");
            private final Button btnExcluir = new Button("Excluir");
            private final HBox box = new HBox(5, btnEditar, btnExcluir);

            {
                btnEditar.setOnAction(e -> {
                    Enfermeiro en = getTableView().getItems().get(getIndex());
                    selecionado = en;
                    preencherFormulario(en);
                });
                btnExcluir.setOnAction(e -> {
                    Enfermeiro en = getTableView().getItems().get(getIndex());
                    if (confirmarExclusao(en.getNome())) {
                        dados.remove(en);
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

        tabela.getColumns().addAll(colId, colNome, colCoren, colTurno, colNivel, colSalario, colAcoes);
        VBox.setVgrow(tabela, Priority.ALWAYS);
        return tabela;
    }

    private GridPane criarFormulario() {
        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(8);
        grid.setPadding(new Insets(10));

        campoDataNasc.setPromptText("DD/MM/AAAA");
        campoSalario.setPromptText("Ex.: 4500.00");
        campoCargaHoraria.setPromptText("Ex.: 36");
        campoNivel.setPromptText("Ex.: 1");

        grid.add(new Label("Nome:"), 0, 0);          grid.add(campoNome, 1, 0);
        grid.add(new Label("COREN:"), 2, 0);         grid.add(campoCoren, 3, 0);
        grid.add(new Label("Turno:"), 0, 1);         grid.add(campoTurno, 1, 1);
        grid.add(new Label("Nível:"), 2, 1);         grid.add(campoNivel, 3, 1);
        grid.add(new Label("Matrícula:"), 0, 2);     grid.add(campoMatricula, 1, 2);
        grid.add(new Label("Salário (R$):"), 2, 2);  grid.add(campoSalario, 3, 2);
        grid.add(new Label("C. Horária:"), 0, 3);    grid.add(campoCargaHoraria, 1, 3);
        grid.add(new Label("Endereço:"), 2, 3);      grid.add(campoEndereco, 3, 3);
        grid.add(new Label("Data Nasc.:"), 0, 4);    grid.add(campoDataNasc, 1, 4);

        Button btnNovo = new Button("Novo");
        Button btnSalvar = new Button("Salvar");
        Button btnCancelar = new Button("Cancelar");

        btnNovo.setOnAction(e -> limparFormulario());
        btnSalvar.setOnAction(e -> salvar());
        btnCancelar.setOnAction(e -> limparFormulario());

        HBox botoes = new HBox(8, btnNovo, btnSalvar, btnCancelar);
        grid.add(botoes, 1, 5, 3, 1);

        return grid;
    }

    private void salvar() {
        try {
            String nome = campoNome.getText().trim();
            String coren = campoCoren.getText().trim();
            String turno = campoTurno.getText().trim();
            String nivelStr = campoNivel.getText().trim();
            String matricula = campoMatricula.getText().trim();
            String salarioStr = campoSalario.getText().trim();
            String cargaStr = campoCargaHoraria.getText().trim();
            String endereco = campoEndereco.getText().trim();
            String dataNascStr = campoDataNasc.getText().trim();

            if (nome.isEmpty() || coren.isEmpty() || turno.isEmpty()) {
                mostrarErro("Preencha os campos obrigatórios: Nome, COREN e Turno.");
                return;
            }

            int nivel = nivelStr.isEmpty() ? 1 : Integer.parseInt(nivelStr);
            double salario = salarioStr.isEmpty() ? 0 : Double.parseDouble(salarioStr.replace(",", "."));
            double cargaHoraria = cargaStr.isEmpty() ? 0 : Double.parseDouble(cargaStr.replace(",", "."));

            LocalDate dataNasc = null;
            if (!dataNascStr.isEmpty()) {
                dataNasc = LocalDate.parse(dataNascStr, FMT);
            }

            if (selecionado != null) {
                selecionado.setNome(nome);
                selecionado.setCoren(coren);
                selecionado.setTurno(turno);
                selecionado.setNivel(nivel);
                selecionado.setMatricula(matricula);
                selecionado.setSalario(salario);
                selecionado.setCargaHoraria(cargaHoraria);
                selecionado.setEndereco(endereco);
                selecionado.setDataNascimento(dataNasc);
            } else {
                Enfermeiro en = new Enfermeiro(nome, endereco, dataNasc, matricula, salario, cargaHoraria, coren, turno, nivel);
                dados.add(en);
            }

            repositorio.salvar(dados);
            tabela.refresh();
            limparFormulario();

        } catch (NumberFormatException e) {
            mostrarErro("Nível, Salário e Carga Horária devem ser valores numéricos.");
        } catch (DateTimeParseException e) {
            mostrarErro("Data inválida. Use o formato DD/MM/AAAA.");
        } catch (RuntimeException e) {
            mostrarErro("Erro ao salvar: " + e.getMessage());
        }
    }

    private void preencherFormulario(Enfermeiro en) {
        campoNome.setText(en.getNome());
        campoCoren.setText(en.getCoren());
        campoTurno.setText(en.getTurno());
        campoNivel.setText(String.valueOf(en.getNivel()));
        campoMatricula.setText(en.getMatricula() != null ? en.getMatricula() : "");
        campoSalario.setText(String.valueOf(en.getSalario()));
        campoCargaHoraria.setText(String.valueOf(en.getCargaHoraria()));
        campoEndereco.setText(en.getEndereco() != null ? en.getEndereco() : "");
        campoDataNasc.setText(en.getDataNascimento() != null ? en.getDataNascimento().format(FMT) : "");
    }

    private void limparFormulario() {
        selecionado = null;
        campoNome.clear();
        campoCoren.clear();
        campoTurno.clear();
        campoNivel.clear();
        campoMatricula.clear();
        campoSalario.clear();
        campoCargaHoraria.clear();
        campoEndereco.clear();
        campoDataNasc.clear();
    }

    private boolean confirmarExclusao(String nome) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Excluir enfermeiro \"" + nome + "\"?", ButtonType.YES, ButtonType.NO);
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
