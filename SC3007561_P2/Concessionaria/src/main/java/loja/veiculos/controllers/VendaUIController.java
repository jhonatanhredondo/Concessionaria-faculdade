package loja.veiculos.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import loja.veiculos.App;
import loja.veiculos.DAO.VendaDAO;
import loja.veiculos.entities.*;
import loja.veiculos.errors.EntityNotFoundException;
import loja.veiculos.errors.InvalidParcelValue;
import loja.veiculos.usecases.VeiculoUseCase;
import loja.veiculos.usecases.VendasUseCase;
import loja.veiculos.usecases.VendedorUseCase;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Iterator;

public class VendaUIController {
    @FXML
    private TextField txtIdVeiculo;
    @FXML
    private TextField txtParcelas;
    @FXML
    private TextField txtVendedor;
    @FXML
    private CheckBox ckBoxParcelado;

    @FXML
    private TableView table;
    @FXML
    private TableColumn tableIdVenda;
    @FXML
    private TableColumn tableIdVeiculo;
    @FXML
    private TableColumn tableTipoPagamento;
    @FXML
    private TableColumn tableValor;
    @FXML
    private TableColumn tableValorParcela;
    @FXML
    private TableColumn tableNumeroParcela;
    @FXML
    private TableColumn tableVendedor;

    private final VendaDAO vendaDAO = new VendaDAO();

    private VeiculoUseCase veiculoUseCase = new VeiculoUseCase();
    private VendedorUseCase vendedorUseCase = new VendedorUseCase();

    private ObservableList<VendasTable> vendas = FXCollections.observableArrayList();
    private VendasUseCase vendasUseCase = new VendasUseCase();

    @FXML
    public void inserirVenda() {
        Integer id = getTxtIdVeiculo();
        Veiculo veiculo = veiculoUseCase.buscarERetornarUmVeiculo(id);
        Integer idVendedor = getTxtVendedor();
        Double preco = veiculo.getPreco();
        Vendedor vendedor = vendedorUseCase.buscarERetornarUmVendedor(idVendedor);
        if(ckBoxParcelado.isSelected()) {
            if(veiculo == null || veiculo.getQuantidade() <= 0) {
                throw new EntityNotFoundException("Veículo não foi encontrado ou sem nenhum no estoque...");
            }
            if(vendedor == null)
                throw new EntityNotFoundException("Vendedor não foi encontrado...");
            if(veiculo.getQuantidade() > 0) {
                vendedor.setVeiculoVendido(veiculo);
                realizarInsercaoTabela(veiculo, preco, vendedor, "parcelado", Integer.parseInt(txtParcelas.getText()), getTxtParcelas(), calculoValorParcela(veiculo, preco, getTxtParcelas()));
            }
        } else {
            if(veiculo == null || veiculo.getQuantidade() <= 0) {
                throw new EntityNotFoundException("Veículo não foi encontrado ou sem nenhum no estoque...");
            }
            if(vendedor == null)
                throw new EntityNotFoundException("Vendedor não foi encontrado...");
            if(veiculo.getQuantidade() > 0) {
                realizarInsercaoTabela(veiculo, preco, vendedor, "avista", 0, 1, preco);
            }
        }
    }

    public void mostrarTodos() {
        limparTabela();
        popularTabela();
    }



    @FXML
    public void mostrarUmaVenda() {
        VendasTable vendaTable = vendasUseCase.selectOneFromVendas(pegarOIdSelecionadoPelaTabela().getId());
        if(vendaTable == null)
            throw new EntityNotFoundException("Nenhuma venda foi selecionada...");
        limparTabela();
        vendas.add(vendaTable);
        preencherTabela(vendas.get(0));
    }

    @FXML
    public void atualizarVenda() {
        VendasTable vendaTable = pegarOIdSelecionadoPelaTabela();
        Venda venda = vendasUseCase.selectOneFromVendasOriginal(vendaTable.getId());
        conferirQualItemFoiModificado(venda, vendaTable);
        vendasUseCase.inserirAtualizacaoVenda(venda);
        limparTabela();
        limparCamposInput();
        popularTabela();
    }

    private void realizarInsercaoTabela(Veiculo veiculo, Double preco, Vendedor vendedor, String parcelado, Integer i, Integer txtParcelas, Double aDouble) {
        Venda venda = new Venda(veiculo, parcelado, preco, i, vendedor);
        venda.setVeiculo(veiculo);
        venda.setVendedor(vendedor);
        venda.setTipoPagamento(parcelado);
        venda.setValorTotal(preco);
        venda.setNumParcelas(txtParcelas);
        venda.setLocalDate(LocalDate.now());
        venda.setValorParcela(aDouble);
        Integer idVenda = vendaDAO.insert(venda);
        veiculo.veiculoVendido();
        if(veiculo.getQuantidade() < 0)
            veiculo.setQuantidade(0);
        veiculoUseCase.atualizarUmVeiculoNoBanco(veiculo);
        venda.setId(idVenda);
        limparCamposInput();
        limparTabela();
        popularTabela();
    }

    private Double calculoValorParcela(Veiculo veiculo, Double valorTotal, Integer numParcelas) {
        if(veiculo instanceof Moto && ((Moto) veiculo).getQuantidadeParcelas() <= 60 && ((Moto) veiculo).getQuantidadeParcelas() > 1) {
            return getValorTotal(veiculo) / numParcelas;
        } else if(veiculo instanceof Carro && numParcelas <= ((Carro) veiculo).getQuantidadeParcelas() && ((Carro) veiculo).getQuantidadeParcelas() > 1) {
            return getValorTotal(veiculo) / numParcelas;
        } else {
            throw new InvalidParcelValue("Parametro de parcela inválido...");
        }
    }

    private Double getValorTotal(Veiculo veiculo) {
        return veiculo.getPreco() * (1 + veiculo.getLimiteParcelas());
    }

    private void limparCamposInput() {
        txtIdVeiculo.setText("");
        if(ckBoxParcelado.isSelected()) {
            txtParcelas.setText("");
            ckBoxParcelado.setSelected(false);
            ocultarParcela();
        }
        txtVendedor.setText("");
    }



    private VendasTable pegarOIdSelecionadoPelaTabela() {
        if(table.getSelectionModel().getSelectedItem() == null)
            throw new EntityNotFoundException("Nenhuma venda foi selecionada...");
        return (VendasTable) table.getSelectionModel().getSelectedItem();
    }


    private void conferirQualItemFoiModificado(Venda venda, VendasTable vendasT) {
        Veiculo veiculoAntigo = veiculoUseCase.buscarERetornarUmVeiculo(vendasT.getIdVeiculo());
        Vendedor vendedorAntigo = vendedorUseCase.buscarERetornarUmVendedor(vendasT.getIdVendedor());
        if(txtIdVeiculo.getText() != "") {
            Veiculo veiculo = veiculoUseCase.buscarERetornarUmVeiculo(getTxtIdVeiculo());
            veiculoAntigo.adicionarUm();
            veiculoUseCase.atualizarUmVeiculoNoBanco(veiculoAntigo);
            veiculo.veiculoVendido();
            veiculoUseCase.atualizarUmVeiculoNoBanco(veiculo);
            venda.setVeiculo(veiculo);
            venda.setValorTotal(veiculo.getPreco());
        }
        if(ckBoxParcelado.isSelected()) {
            Veiculo veiculo;
            if(txtIdVeiculo.getText() != "")
                veiculo = veiculoUseCase.buscarERetornarUmVeiculo(getTxtIdVeiculo());
            else
                veiculo = veiculoUseCase.buscarERetornarUmVeiculo(vendasT.getIdVeiculo());
            venda.setNumParcelas(getTxtParcelas());
            if(getTxtParcelas() == 1)
                venda.setValorParcela(veiculo.getPreco());
            else
                venda.setValorParcela(venda.calculoValorParcela(veiculo, venda.getValor(), venda.getNumParcelas())/10);
        } else {
            if(txtIdVeiculo.getText() != "") {
                Veiculo veiculo = veiculoUseCase.buscarERetornarUmVeiculo(getTxtIdVeiculo());
                venda.setValorParcela(venda.calculoValorParcela(veiculo, venda.getValor(), venda.getNumParcelas())/10);
            } else {
                Veiculo veiculo = veiculoUseCase.buscarERetornarUmVeiculo(vendasT.getIdVeiculo());
                venda.setValorParcela(venda.calculoValorParcela(veiculo, venda.getValor(), venda.getNumParcelas())/10);
            }
        }
        if(txtVendedor.getText() != "") {
            Vendedor vendedor = vendedorUseCase.buscarERetornarUmVendedor(getTxtVendedor());
            venda.setVendedor(vendedor);
            if(txtIdVeiculo.getText() == "")
                vendedor.setVeiculoVendido(veiculoAntigo);
            else {
                vendedorAntigo.removerVeiculoVendido(veiculoAntigo);
                vendedor.setVeiculoVendido(veiculoUseCase.buscarERetornarUmVeiculo(getTxtIdVeiculo()));
            }
            vendedorUseCase.atualizarUmVendedorNoBanco(vendedor);
            vendedorUseCase.atualizarUmVendedorNoBanco(vendedorAntigo);
        }
    }

    public void removerTabela() {
        limparTabela();
    }

    public void ocultarParcela() {
        if(!ckBoxParcelado.isSelected()) {
            txtParcelas.setDisable(true);
        } else {
            txtParcelas.setDisable(false);
        }
    }

    public void voltarMain(ActionEvent event) throws IOException {
        App.setRoot("mainUI");
    }

    public Integer getTxtIdVeiculo() {
        return Integer.parseInt(txtIdVeiculo.getText());
    }

    public Integer getTxtParcelas() {
        return Integer.parseInt(txtParcelas.getText());
    }

    public Integer getTxtVendedor() {
        return Integer.parseInt(txtVendedor.getText());
    }

    @FXML
    public void initialize() {
        txtParcelas.setDisable(true);
        popularTabela();
    }

    private void limparTabela() {
        vendas.removeAll();
        table.getItems().clear();
    }

    private void popularTabela() {
        vendas = vendasUseCase.pegarOsValoresTable();
        if(vendas != null) {
            Iterator<VendasTable> iterator = vendas.iterator();
            while (iterator.hasNext()) {
                preencherTabela(iterator.next());
            }
        }
    }


    private void preencherTabela(VendasTable next) {
        tableIdVenda.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableIdVeiculo.setCellValueFactory(new PropertyValueFactory<>("idVeiculo"));
        tableTipoPagamento.setCellValueFactory(new PropertyValueFactory<>("tipoPagamento"));
        tableValor.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));
        tableValorParcela.setCellValueFactory(new PropertyValueFactory<>("valorParcela"));
        tableNumeroParcela.setCellValueFactory(new PropertyValueFactory<>("numParcelas"));
        tableVendedor.setCellValueFactory(new PropertyValueFactory<>("idVendedor"));
        table.setItems(vendas);
    }


    public void mostrarDetalhes() throws IOException {
        App.setRoot("detalhesUI");
    }
}
