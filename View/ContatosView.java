package View;

import Controller.ContatosController;
import Model.Contatos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ContatosView {

    private ContatosController controller = new ContatosController();
    private TableView<Contatos> tabela = new TableView<>();

    public void start(Stage stage) {

        Label titulo = new Label("Agenda de Contatos");
        titulo.setFont(new Font("Segoe UI", 32));
        titulo.setStyle("-fx-font-weight: bold; -fx-text-fill: #1a1a1a;");
        titulo.setAlignment(Pos.CENTER);

        HBox tituloBox = new HBox(titulo);
        tituloBox.setAlignment(Pos.CENTER);
        tituloBox.setPadding(new Insets(10, 0, 25, 0));

        // ----- Material UI TextFields -----
        TextField nomeField = criarMaterialField("Nome");
        TextField telefoneField = criarMaterialField("Telefone");
        TextField emailField = criarMaterialField("Email");

        HBox camposBox = new HBox(20, nomeField, telefoneField, emailField);
        camposBox.setAlignment(Pos.CENTER);
        camposBox.setPadding(new Insets(25));
        camposBox.setStyle(
                "-fx-background-color: #ffffff;" +
                "-fx-background-radius: 12;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 12, 0, 0, 3);"
        );

        // ----- Botões Material UI -----
        Button adicionar = criarBotaoMaterial("Adicionar", "#1976D2");
        Button editar = criarBotaoMaterial("Editar", "#0288D1");
        Button excluir = criarBotaoMaterial("Excluir", "#D32F2F");

        HBox botoesBox = new HBox(15, adicionar, editar, excluir);
        botoesBox.setAlignment(Pos.CENTER);
        botoesBox.setPadding(new Insets(30, 0, 20, 0));

        // ----- Tabela -----
        TableColumn<Contatos, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(60);

        TableColumn<Contatos, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colNome.setPrefWidth(180);

        TableColumn<Contatos, String> colTel = new TableColumn<>("Telefone");
        colTel.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colTel.setPrefWidth(150);

        TableColumn<Contatos, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colEmail.setPrefWidth(220);

        tabela.getColumns().addAll(colId, colNome, colTel, colEmail);

        tabela.setStyle(
                "-fx-background-color: white;" +
                "-fx-border-color: transparent;" +
                "-fx-background-radius: 12; " +
                "-fx-border-radius: 12;"
        );

        atualizarTabela();

        // ----- Ações -----
        adicionar.setOnAction(e -> {
            Contatos novo = new Contatos();
            novo.setNome(nomeField.getText());
            novo.setTelefone(telefoneField.getText());
            novo.setEmail(emailField.getText());

            controller.adicionarContato(novo);
            atualizarTabela();

            nomeField.clear();
            telefoneField.clear();
            emailField.clear();
        });

        editar.setOnAction(e -> {
            Contatos sel = tabela.getSelectionModel().getSelectedItem();
            if (sel == null) return;

            sel.setNome(nomeField.getText());
            sel.setTelefone(telefoneField.getText());
            sel.setEmail(emailField.getText());

            controller.editarContato(sel);
            atualizarTabela();
        });

        excluir.setOnAction(e -> {
            Contatos sel = tabela.getSelectionModel().getSelectedItem();
            if (sel == null) return;

            controller.excluirContato(sel.getId());
            atualizarTabela();
        });

        VBox layout = new VBox(20, tituloBox, camposBox, botoesBox, tabela);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #f4f6f8;");

        Scene scene = new Scene(layout, 800, 650);

        stage.setTitle("Agenda de Contatos");
        stage.setScene(scene);
        stage.show();
    }

    private TextField criarMaterialField(String prompt) {
        TextField field = new TextField();
        field.setPromptText(prompt);
        field.setPrefWidth(180);
        field.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-border-width: 0 0 2 0;" +
                "-fx-border-color: #bdbdbd;" +
                "-fx-font-size: 14px;"
        );

        field.setOnMouseClicked(e ->
                field.setStyle("-fx-border-color: #1976D2; -fx-border-width: 0 0 2 0;")
        );

        field.focusedProperty().addListener((obs, oldV, newV) -> {
            if (!newV) {
                field.setStyle(
                        "-fx-background-color: transparent;" +
                        "-fx-border-width: 0 0 2 0;" +
                        "-fx-border-color: #bdbdbd;"
                );
            }
        });

        return field;
    }

    private Button criarBotaoMaterial(String texto, String cor) {
        Button btn = new Button(texto);
        btn.setPrefWidth(140);
        btn.setPrefHeight(40);
        btn.setStyle(
                "-fx-background-color: " + cor + ";" +
                "-fx-background-radius: 6;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 14px;" +
                "-fx-font-weight: bold;"
        );

        btn.setOnMouseEntered(e ->
                btn.setStyle(
                        "-fx-background-color: #00000015;" +
                        "-fx-background-radius: 6;" +
                        "-fx-text-fill: black;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;"
                )
        );

        btn.setOnMouseExited(e ->
                btn.setStyle(
                        "-fx-background-color: " + cor + ";" +
                        "-fx-background-radius: 6;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;"
                )
        );

        return btn;
    }

    private void atualizarTabela() {
        ObservableList<Contatos> lista =
                FXCollections.observableArrayList(controller.listarContatos());
        tabela.setItems(lista);
    }
}
