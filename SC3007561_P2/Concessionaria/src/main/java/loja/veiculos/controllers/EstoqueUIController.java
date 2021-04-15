package loja.veiculos.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import loja.veiculos.App;
import loja.veiculos.entities.Veiculo;
import loja.veiculos.errors.EmptyFieldException;
import loja.veiculos.errors.EntityNotFoundException;
import loja.veiculos.usecases.VeiculoUseCase;

import java.io.IOException;
import java.util.Iterator;

public class EstoqueUIController {
    @FXML
    private TextField txtMarca;
    @FXML
    private TextField txtModelo;
    @FXML
    private TextField txtAno;
    @FXML
    private TextField txtPreco;
    @FXML
    private TextField txtQuantidade;
    @FXML
    private TextField txtQuantidadePortas;

    @FXML
    private TableView<Veiculo> table;
    @FXML
    private TableColumn<Veiculo, Integer> tableId;
    @FXML
    private TableColumn<Veiculo, String> tableMarca;
    @FXML
    private TableColumn<Veiculo, String> tableModelo;
    @FXML
    private TableColumn<Veiculo, Integer> tableAno;
    @FXML
    private TableColumn<Veiculo, Double> tablePreco;
    @FXML
    private TableColumn<Veiculo, Integer> tableQuantidade;
    @FXML
    private TableColumn<Veiculo, Integer> tableQuantidadePortas;

    private ObservableList<Veiculo> listaVeiculos = FXCollections.observableArrayList();

    private VeiculoUseCase veiculoUseCase = new VeiculoUseCase();

    public void inserirVeiculo() {
        conferirSeTemCampoVazio();
        veiculoUseCase.conferirEInserirSeCarroOuMotoEInserir(getTxtQuantidadePortas(), getTxtMarca(), getTxtModelo(), getTxtAno(), getTxtPreco(), getTxtQuantidade());
        limparCamposInput();
        limparTabela();
        popularTabela();
    }



    public void mostrarTodosVeiculos() {
        limparTabela();
        popularTabela();
    }

    public void mostrarUmVeiculo() {
        Veiculo veiculo = veiculoUseCase.buscarERetornarUmVeiculo(pegarOIdDoSelecionadoPelaTabela());
        if(veiculo == null)
            throw new EntityNotFoundException("O veiculo não foi encontrado");
        limparTabela();
        listaVeiculos.add(veiculo);
        preencherTabela(listaVeiculos.get(0));
    }


    public void atualizarVeiculo() {

        Veiculo veiculo = veiculoUseCase.buscarERetornarUmVeiculo(pegarOIdDoSelecionadoPelaTabela());
        if(veiculo == null)
            throw new EntityNotFoundException("O veiculo não foi encontrado");
        conferirQuaisCamposForamAlterados(veiculo);
        veiculoUseCase.atualizarUmVeiculoNoBanco(veiculo);
        limparTabela();
        limparCamposInput();
        popularTabela();
    }


    public void removerVeiculo() {
        veiculoUseCase.removerUmVeiculoNoBanco(pegarOIdDoSelecionadoPelaTabela());
        limparTabela();
        popularTabela();
    }

    @FXML
    public void initialize() {
        popularTabela();
    }

    private void popularTabela() {
        listaVeiculos = veiculoUseCase.buscarTodosOsVeiculos();
        if(listaVeiculos != null) {
            Iterator<Veiculo> iterator = listaVeiculos.iterator();
            while (iterator.hasNext())
                preencherTabela(iterator.next());
        }
    }

    private void preencherTabela(Veiculo veiculo) {
        tableId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        tableModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        tableAno.setCellValueFactory(new PropertyValueFactory<>("ano"));
        tablePreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        tableQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        tableQuantidadePortas.setCellValueFactory(new PropertyValueFactory<>("quantidadePortas"));
        table.setItems(listaVeiculos);
    }

    public void limparTabela() {
        listaVeiculos.removeAll();
        table.getItems().clear();
    }

    private void conferirSeTemCampoVazio() {
        if(getTxtMarca() == "" ||getTxtModelo() == "" || txtAno.getText() == ""||txtPreco.getText() == "" || txtQuantidade.getText() == "" || txtQuantidadePortas.getText() == "")
            throw new EmptyFieldException("Um ou mais campos estavam vazios!");
    }

    private void limparCamposInput() {
        txtMarca.setText("");
        txtModelo.setText("");
        txtAno.setText("");
        txtPreco.setText("");
        txtQuantidade.setText("");
        txtQuantidadePortas.setText("");
    }


    private Integer pegarOIdDoSelecionadoPelaTabela() {
        return table.getSelectionModel().getSelectedItem().getId();
    }

    private void conferirQuaisCamposForamAlterados(Veiculo veiculo) {
        if(getTxtMarca() != "")
            veiculo.setMarca(getTxtMarca());
        if(getTxtModelo() != "")
            veiculo.setModelo(getTxtModelo());
        if(txtAno.getText() != "")
            veiculo.setAno(getTxtAno());
        if(txtPreco.getText() != "")
            veiculo.setPreco(getTxtPreco());
        if(txtQuantidade.getText() != "")
            veiculo.setQuantidade(getTxtQuantidade());
        if(txtQuantidadePortas.getText() != "")
            veiculo.setQuantidade(getTxtQuantidadePortas());
    }

    public String getTxtMarca() {
        return txtMarca.getText();
    }

    public String getTxtModelo() {
        return txtModelo.getText();
    }

    public Integer getTxtAno() {
        return Integer.parseInt(txtAno.getText());
    }

    public Double getTxtPreco() {
        return Double.parseDouble(txtPreco.getText());
    }

    public Integer getTxtQuantidade() {
        return Integer.parseInt(txtQuantidade.getText());
    }

    public Integer getTxtQuantidadePortas() {
        return Integer.parseInt(txtQuantidadePortas.getText());
    }


    public void voltarMain() throws IOException {
        App.setRoot("mainUI");
    }

}
