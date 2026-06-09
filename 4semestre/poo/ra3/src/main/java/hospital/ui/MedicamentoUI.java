package hospital.ui;

import hospital.model.Medicamento;
import hospital.persistencia.Repositorio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class MedicamentoUI extends Stage {
    private final Repositorio<Medicamento> repositorio = new Repositorio<>("medicamentos.dat");
    private final ObservableList<Medicamento> dados = FXCollections.observableArrayList();
    private final TableView<Medicamento> tabela = new TableView<>(dados);

    private Medicamento selecionado = null;

    private final TextField campoCodigoAnvisa = new TextField();
    private final TextField campoNome = new TextField();
    private final TextField campoEstoque = new TextField();

    public MedicamentoUI() {
        setTitle("Gestão de Medicamentos");

        List<Medicamento> lista = repositorio.carregar();
        dados.addAll(lista);

        VBox root = new VBox(10);
        root.setPadding(new Insets(15));
        root.getChildren().addAll(criarFormulario(), criarTabela());

        setScene(new Scene(root, 700, 550));
        setResizable(true);
    }

    @SuppressWarnings("unchecked")
    private TableView<Medicamento> criarTabela() {
        TableColumn<Medicamento, String> colCodigo = new TableColumn<>("Código ANVISA");
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigoAnvisa"));
        colCodigo.setPrefWidth(150);

        TableColumn<Medicamento, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colNome.setPrefWidth(200);

        TableColumn<Medicamento, Integer> colEstoque = new TableColumn<>("Estoque");
        colEstoque.setCellValueFactory(new PropertyValueFactory<>("estoque"));
        colEstoque.setPrefWidth(80);

        TableColumn<Medicamento, Void> colAcoes = new TableColumn<>("Ações");
        colAcoes.setPrefWidth(150);
        colAcoes.setCellFactory(col -> new TableCell<>() {
            private final Button btnEditar = new Button("Editar");
            private final Button btnExcluir = new Button("Excluir");
            private final HBox box = new HBox(5, btnEditar, btnExcluir);

            {
                btnEditar.setOnAction(e -> {
                    Medicamento m = getTableView().getItems().get(getIndex());
                    selecionado = m;
                    preencherFormulario(m);
                });
                btnExcluir.setOnAction(e -> {
                    Medicamento m = getTableView().getItems().get(getIndex());
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

        tabela.getColumns().addAll(colCodigo, colNome, colEstoque, colAcoes);
        VBox.setVgrow(tabela, Priority.ALWAYS);
        return tabela;
    }

    private GridPane criarFormulario() {
        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(8);
        grid.setPadding(new Insets(10));

        campoEstoque.setPromptText("Ex.: 100");
        campoCodigoAnvisa.setPromptText("Ex.: ANV/SA-00123");

        grid.add(new Label("Código ANVISA:"), 0, 0); grid.add(campoCodigoAnvisa, 1, 0);
        grid.add(new Label("Nome:"), 2, 0);           grid.add(campoNome, 3, 0);
        grid.add(new Label("Estoque:"), 0, 1);        grid.add(campoEstoque, 1, 1);

        Button btnNovo = new Button("Novo");
        Button btnSalvar = new Button("Salvar");
        Button btnCancelar = new Button("Cancelar");

        btnNovo.setOnAction(e -> limparFormulario());
        btnSalvar.setOnAction(e -> salvar());
        btnCancelar.setOnAction(e -> limparFormulario());

        HBox botoes = new HBox(8, btnNovo, btnSalvar, btnCancelar);
        grid.add(botoes, 1, 2, 3, 1);

        return grid;
    }

    private void salvar() {
        try {
            String codigo = campoCodigoAnvisa.getText().trim();
            String nome = campoNome.getText().trim();
            String estoqueStr = campoEstoque.getText().trim();

            if (codigo.isEmpty() || nome.isEmpty() || estoqueStr.isEmpty()) {
                mostrarErro("Preencha todos os campos: Código ANVISA, Nome e Estoque.");
                return;
            }

            int estoque = Integer.parseInt(estoqueStr);
            if (estoque < 0) {
                mostrarErro("Estoque não pode ser negativo.");
                return;
            }

            if (selecionado != null) {
                selecionado.setCodigoAnvisa(codigo);
                selecionado.setNome(nome);
                selecionado.setEstoque(estoque);
            } else {
                boolean codigoDuplicado = dados.stream()
                        .anyMatch(m -> m.getCodigoAnvisa().equalsIgnoreCase(codigo));
                if (codigoDuplicado) {
                    mostrarErro("Já existe um medicamento com este código ANVISA.");
                    return;
                }
                dados.add(new Medicamento(codigo, nome, estoque));
            }

            repositorio.salvar(dados);
            tabela.refresh();
            limparFormulario();

        } catch (NumberFormatException e) {
            mostrarErro("Estoque deve ser um número inteiro.");
        } catch (RuntimeException e) {
            mostrarErro("Erro ao salvar: " + e.getMessage());
        }
    }

    private void preencherFormulario(Medicamento m) {
        campoCodigoAnvisa.setText(m.getCodigoAnvisa());
        campoNome.setText(m.getNome());
        campoEstoque.setText(String.valueOf(m.getEstoque()));
    }

    private void limparFormulario() {
        selecionado = null;
        campoCodigoAnvisa.clear();
        campoNome.clear();
        campoEstoque.clear();
    }

    private boolean confirmarExclusao(String nome) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Excluir medicamento \"" + nome + "\"?", ButtonType.YES, ButtonType.NO);
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
