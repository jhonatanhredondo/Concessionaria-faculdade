package loja.veiculos.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import loja.veiculos.App;
import loja.veiculos.DAO.ConnectionBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MainUIController {
    @FXML private Button btnVenda;
    @FXML private Button btnEstoque;
    @FXML private Button btnVendedor;

    @FXML public void goToVenda() {
        try {
            App.setRoot("vendaUI");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML public void goToEstoque() {
        try {
            App.setRoot("estoqueUI");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML public void goToVendedor() {
        try {
            App.setRoot("vendedorUI");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML public void encerrarMain() {
        Platform.exit();
    }

    @FXML public void initialize() {
        ConnectionBuilder connectionBuilder = new ConnectionBuilder();
        connectionBuilder.construirBaseDados();
    }

    //EXECUTAR SOMENTE QUANDO ABRIR O PROGRAMA
    public void resetarBancoDados(ActionEvent event) {
        if(Files.exists(Paths.get("database.db"))) {
            File file = new File(Paths.get("database.db").toString());
            file.delete();
            ConnectionBuilder connectionBuilder = new ConnectionBuilder();
            connectionBuilder.construirBaseDados();
        }
    }
}
