package loja.veiculos.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import loja.veiculos.App;
import loja.veiculos.entities.Vendedor;
import loja.veiculos.errors.EntityNotFoundException;
import loja.veiculos.usecases.VendedorUseCase;

import java.io.IOException;
import java.util.Iterator;

public class VendedorUIController {
    @FXML
    private TableView table;
    @FXML
    private TableColumn tableId;
    @FXML
    private TableColumn tableNome;
    @FXML
    private TableColumn tableEmail;
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtEmail;

    private ObservableList<Vendedor> listaVendedores = FXCollections.observableArrayList();

    VendedorUseCase vendedorUseCase = new VendedorUseCase();


    public void inserirVendedor() {
        Vendedor vendedor = new Vendedor(getTxtNome(), getTxtEmail());
        vendedorUseCase.inserirVendedorNoBanco(vendedor);
        limparCamposInput();
        limparTabela();
        popularTabela();
    }

    public void mostrarTodos() {
        limparTabela();
        popularTabela();
    }


    public void mostrarUm() {
        Vendedor vendedor = vendedorUseCase.buscarERetornarUmVendedor(pegarOIdDoSelecionadoPelaTabela().getId());
        if(vendedor == null)
            throw new EntityNotFoundException("O vendedor não foi encontrado");
        limparTabela();
        listaVendedores.add(vendedor);
        preencherTabela(listaVendedores.get(0));
    }

    public void atualizarVendedor() {
        Vendedor vendedor = vendedorUseCase.buscarERetornarUmVendedor(pegarOIdDoSelecionadoPelaTabela().getId());
        if(vendedor == null)
            throw new EntityNotFoundException("O vendedor não foi encontrado");
        conferirQuaisCamposForamAlterados(vendedor);
        vendedorUseCase.atualizarUmVendedorNoBanco(vendedor);
        limparTabela();
        limparCamposInput();
        popularTabela();
    }

    public void deletarVendedor() {
        vendedorUseCase.removerUmVendedorNoBanco(pegarOIdDoSelecionadoPelaTabela().getId());
        limparTabela();
        popularTabela();
    }

    private void limparCamposInput() {
        txtNome.setText("");
        txtEmail.setText("");
    }


    private Vendedor pegarOIdDoSelecionadoPelaTabela() {
        return (Vendedor) table.getSelectionModel().getSelectedItem();
    }



    private void conferirQuaisCamposForamAlterados(Vendedor vendedor) {
        if(getTxtNome() != "")
            vendedor.setNome(getTxtNome());
        if(getTxtEmail() != "")
            vendedor.setEmail(getTxtEmail());
    }


    @FXML
    public void initialize() {
        popularTabela();
    }

    private void limparTabela() {
        listaVendedores.removeAll();
        table.getItems().clear();
    }

    private void popularTabela() {
        listaVendedores = vendedorUseCase.buscarTodosOsVendedores();
        if(listaVendedores != null) {
            Iterator<Vendedor> iterator = listaVendedores.iterator();
            while (iterator.hasNext())
                preencherTabela(iterator.next());
        }
    }

    private void preencherTabela(Vendedor vendedor) {
        tableId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        table.setItems(listaVendedores);
    }

    public void voltarMain() throws IOException {
        App.setRoot("mainUI");
    }

    public String getTxtNome() {
        return txtNome.getText();
    }

    public String getTxtEmail() {
        return txtEmail.getText();
    }
}
