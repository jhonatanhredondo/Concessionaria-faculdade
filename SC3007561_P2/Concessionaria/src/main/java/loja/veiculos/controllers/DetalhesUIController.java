package loja.veiculos.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import loja.veiculos.App;
import loja.veiculos.entities.Detalhes;
import loja.veiculos.errors.EmptyFieldException;
import loja.veiculos.usecases.DetalhesUseCase;

import java.io.IOException;
import java.util.Iterator;

public class DetalhesUIController {
    @FXML
    private TextField txtID;
    @FXML
    private TableView table;
    @FXML
    private TableColumn tableVendedor;
    @FXML
    private TableColumn tableMarca;
    @FXML
    private TableColumn tableModelo;

    private ObservableList<Detalhes> detalhes = FXCollections.observableArrayList();
    private DetalhesUseCase detalhesUseCase = new DetalhesUseCase();

    @FXML
    public void initialize() {
        montarTabelaComTodos();
        popularTabela();
    }

    public void mostrarVendasVeiculo(ActionEvent event) {
        String texto = txtID.getText();
        if(texto.isEmpty()) {
            throw new EmptyFieldException("Nada inserido pelo usuário...");
        }
        montarTabelaComVeiculo(texto);
        txtID.setText("");
        limparTabela();
        popularTabela();
    }


    public void mostrarVendasVendedor(ActionEvent event) {
        String texto = txtID.getText();
        if(texto.isEmpty()) {
            throw new EmptyFieldException("Nada inserido pelo usuário...");
        }
        montarTabelaComVendedor(texto);
        txtID.setText("");
        limparTabela();
        popularTabela();
    }

    public void mostrarTodos(ActionEvent event) {
        limparTabela();
        montarTabelaComTodos();
        popularTabela();
    }

    public void montarTabelaComTodos() {
        this.detalhes = detalhesUseCase.selecionarTodos();
    }

    public void montarTabelaComVeiculo(String txt) {
        this.detalhes = detalhesUseCase.selecionarVeiculo(Integer.parseInt(txt));
    }

    public void montarTabelaComVendedor(String txt) {
        this.detalhes = detalhesUseCase.selecionarVendedor(Integer.parseInt(txt));
    }

    public void popularTabela() {
        if(this.detalhes != null) {
            Iterator<Detalhes> iterator = this.detalhes.iterator();
            while (iterator.hasNext()) {
                preencherTabela(iterator.next());
            }
        }
    }

    private void preencherTabela(Detalhes next) {
        tableVendedor.setCellValueFactory(new PropertyValueFactory<>("nomeVendedor"));
        tableMarca.setCellValueFactory(new PropertyValueFactory<>("nomeMarca"));
        tableModelo.setCellValueFactory(new PropertyValueFactory<>("nomeModelo"));
        table.setItems(detalhes);
    }

    private void limparTabela() {
        detalhes.removeAll();
        table.getItems().clear();
    }

    public void voltarPraVendas() throws IOException {
        App.setRoot("vendaUI");
    }

}
