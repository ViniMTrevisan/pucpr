package hospital.ui;

import hospital.model.Medico;
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

public class MedicoUI extends Stage {
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final Repositorio<Medico> repositorio = new Repositorio<>("medicos.dat");
    private final ObservableList<Medico> dados = FXCollections.observableArrayList();
    private final TableView<Medico> tabela = new TableView<>(dados);

    private Medico selecionado = null;

    private final TextField campoNome = new TextField();
    private final TextField campoCrm = new TextField();
    private final TextField campoEspecialidade = new TextField();
    private final TextField campoMatricula = new TextField();
    private final TextField campoSalario = new TextField();
    private final TextField campoCargaHoraria = new TextField();
    private final CheckBox campoPlantao = new CheckBox("Em plantão");
    private final TextField campoEndereco = new TextField();
    private final TextField campoDataNasc = new TextField();

    public MedicoUI() {
        setTitle("Gestão de Médicos");

        List<Medico> lista = repositorio.carregar();
        if (!lista.isEmpty()) {
            int maxId = lista.stream().mapToInt(Medico::getId).max().getAsInt();
            Medico.setProximoId(maxId + 1);
        }
        dados.addAll(lista);

        VBox root = new VBox(10);
        root.setPadding(new Insets(15));
        root.getChildren().addAll(criarFormulario(), criarTabela());

        setScene(new Scene(root, 950, 650));
        setResizable(true);
    }

    @SuppressWarnings("unchecked")
    private TableView<Medico> criarTabela() {
        TableColumn<Medico, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(50);

        TableColumn<Medico, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colNome.setPrefWidth(170);

        TableColumn<Medico, String> colCrm = new TableColumn<>("CRM");
        colCrm.setCellValueFactory(new PropertyValueFactory<>("crm"));
        colCrm.setPrefWidth(110);

        TableColumn<Medico, String> colEspecialidade = new TableColumn<>("Especialidade");
        colEspecialidade.setCellValueFactory(new PropertyValueFactory<>("especialidade"));
        colEspecialidade.setPrefWidth(130);

        TableColumn<Medico, Double> colSalario = new TableColumn<>("Salário");
        colSalario.setCellValueFactory(new PropertyValueFactory<>("salario"));
        colSalario.setPrefWidth(100);

        TableColumn<Medico, Boolean> colPlantao = new TableColumn<>("Plantão");
        colPlantao.setCellValueFactory(new PropertyValueFactory<>("plantao"));
        colPlantao.setPrefWidth(70);

        TableColumn<Medico, Void> colAcoes = new TableColumn<>("Ações");
        colAcoes.setPrefWidth(150);
        colAcoes.setCellFactory(col -> new TableCell<>() {
            private final Button btnEditar = new Button("Editar");
            private final Button btnExcluir = new Button("Excluir");
            private final HBox box = new HBox(5, btnEditar, btnExcluir);

            {
                btnEditar.setOnAction(e -> {
                    Medico m = getTableView().getItems().get(getIndex());
                    selecionado = m;
                    preencherFormulario(m);
                });
                btnExcluir.setOnAction(e -> {
                    Medico m = getTableView().getItems().get(getIndex());
                    if (confirmarExclusao(m.getNome())) {
                        dados.remove(m);
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

        tabela.getColumns().addAll(colId, colNome, colCrm, colEspecialidade, colSalario, colPlantao, colAcoes);
        VBox.setVgrow(tabela, Priority.ALWAYS);
        return tabela;
    }

    private GridPane criarFormulario() {
        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(8);
        grid.setPadding(new Insets(10));

        campoDataNasc.setPromptText("DD/MM/AAAA");
        campoSalario.setPromptText("Ex.: 15000.00");
        campoCargaHoraria.setPromptText("Ex.: 40");

        grid.add(new Label("Nome:"), 0, 0);          grid.add(campoNome, 1, 0);
        grid.add(new Label("CRM:"), 2, 0);           grid.add(campoCrm, 3, 0);
        grid.add(new Label("Especialidade:"), 0, 1); grid.add(campoEspecialidade, 1, 1);
        grid.add(new Label("Matrícula:"), 2, 1);     grid.add(campoMatricula, 3, 1);
        grid.add(new Label("Salário (R$):"), 0, 2);  grid.add(campoSalario, 1, 2);
        grid.add(new Label("C. Horária:"), 2, 2);    grid.add(campoCargaHoraria, 3, 2);
        grid.add(new Label("Endereço:"), 0, 3);      grid.add(campoEndereco, 1, 3);
        grid.add(new Label("Data Nasc.:"), 2, 3);    grid.add(campoDataNasc, 3, 3);
        grid.add(campoPlantao, 1, 4);

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
            String crm = campoCrm.getText().trim();
            String especialidade = campoEspecialidade.getText().trim();
            String matricula = campoMatricula.getText().trim();
            String salarioStr = campoSalario.getText().trim();
            String cargaStr = campoCargaHoraria.getText().trim();
            String endereco = campoEndereco.getText().trim();
            String dataNascStr = campoDataNasc.getText().trim();
            boolean plantao = campoPlantao.isSelected();

            if (nome.isEmpty() || crm.isEmpty() || especialidade.isEmpty()) {
                mostrarErro("Preencha os campos obrigatórios: Nome, CRM e Especialidade.");
                return;
            }

            double salario = 0;
            double cargaHoraria = 0;

            if (!salarioStr.isEmpty()) {
                salario = Double.parseDouble(salarioStr.replace(",", "."));
            }
            if (!cargaStr.isEmpty()) {
                cargaHoraria = Double.parseDouble(cargaStr.replace(",", "."));
            }

            LocalDate dataNasc = null;
            if (!dataNascStr.isEmpty()) {
                dataNasc = LocalDate.parse(dataNascStr, FMT);
            }

            if (selecionado != null) {
                selecionado.setNome(nome);
                selecionado.setCrm(crm);
                selecionado.setEspecialidade(especialidade);
                selecionado.setMatricula(matricula);
                selecionado.setSalario(salario);
                selecionado.setCargaHoraria(cargaHoraria);
                selecionado.setEndereco(endereco);
                selecionado.setDataNascimento(dataNasc);
                selecionado.setPlantao(plantao);
            } else {
                Medico m = new Medico(nome, endereco, dataNasc, matricula, salario, cargaHoraria, crm, especialidade, plantao);
                dados.add(m);
            }

            repositorio.salvar(dados);
            tabela.refresh();
            limparFormulario();

        } catch (NumberFormatException e) {
            mostrarErro("Salário e Carga Horária devem ser valores numéricos.");
        } catch (DateTimeParseException e) {
            mostrarErro("Data inválida. Use o formato DD/MM/AAAA.");
        } catch (RuntimeException e) {
            mostrarErro("Erro ao salvar: " + e.getMessage());
        }
    }

    private void preencherFormulario(Medico m) {
        campoNome.setText(m.getNome());
        campoCrm.setText(m.getCrm());
        campoEspecialidade.setText(m.getEspecialidade());
        campoMatricula.setText(m.getMatricula() != null ? m.getMatricula() : "");
        campoSalario.setText(String.valueOf(m.getSalario()));
        campoCargaHoraria.setText(String.valueOf(m.getCargaHoraria()));
        campoEndereco.setText(m.getEndereco() != null ? m.getEndereco() : "");
        campoDataNasc.setText(m.getDataNascimento() != null ? m.getDataNascimento().format(FMT) : "");
        campoPlantao.setSelected(m.isPlantao());
    }

    private void limparFormulario() {
        selecionado = null;
        campoNome.clear();
        campoCrm.clear();
        campoEspecialidade.clear();
        campoMatricula.clear();
        campoSalario.clear();
        campoCargaHoraria.clear();
        campoEndereco.clear();
        campoDataNasc.clear();
        campoPlantao.setSelected(false);
    }

    private boolean confirmarExclusao(String nome) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Excluir médico \"" + nome + "\"?", ButtonType.YES, ButtonType.NO);
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
