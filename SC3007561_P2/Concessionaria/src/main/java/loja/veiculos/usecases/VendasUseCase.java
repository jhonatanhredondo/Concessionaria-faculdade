package loja.veiculos.usecases;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import loja.veiculos.DAO.VendaDAO;
import loja.veiculos.entities.Venda;
import loja.veiculos.entities.VendasTable;

import java.util.Iterator;
import java.util.List;

public class VendasUseCase {

    ObservableList<VendasTable> vendasTables = FXCollections.observableArrayList();
    VendaDAO vendaDAO = new VendaDAO();


    public ObservableList<VendasTable> pegarOsValoresTable() {
        selectFromVendas();
        return vendasTables;
    }

    public VendasTable selectOneFromVendas(Integer id) {
        Venda venda = vendaDAO.selectOne(id);
        VendasTable vendasTable = new VendasTable();
        if (venda != null) {
            vendasTable = inserirEmVendasTable(venda);
        }
        return vendasTable;
    }

    public Venda selectOneFromVendasOriginal(Integer id) {
        Venda venda = vendaDAO.selectOne(id);
        return venda;
    }

    public void selectFromVendas() {
        vendasTables.removeAll();
        List<Venda> vendasOriginal = vendaDAO.selectAll();
        Iterator<Venda> iterator = vendasOriginal.iterator();
        while (iterator.hasNext()) {
            Venda venda = iterator.next();

            vendasTables.add(inserirEmVendasTable(venda));
        }
    }

    private VendasTable inserirEmVendasTable(Venda venda) {
        VendasTable vendasTableUnico = new VendasTable();
        vendasTableUnico.setId(venda.getId());
        vendasTableUnico.setIdVeiculo(venda.getVeiculo().getId());
        vendasTableUnico.setDataVenda(venda.getLocalDate());
        vendasTableUnico.setValorTotal(venda.getValor());
        if(venda.getNumParcelas() > 1)
            vendasTableUnico.setTipoPagamento("Parcelado");
        else
            vendasTableUnico.setTipoPagamento("A Vista");
        vendasTableUnico.setNumParcelas(venda.getNumParcelas());
        vendasTableUnico.setValorParcela(venda.getValorParcela());
        vendasTableUnico.setIdVendedor(venda.getVendedor().getId());

        return vendasTableUnico;
    }


    public void inserirAtualizacaoVenda(Venda venda) {
        vendaDAO.update(new Venda(venda.getId(), venda.getVeiculo().getId(), venda.getLocalDate(), venda.getValor(), venda.getNumParcelas(), venda.getValorParcela(), venda.getVendedor().getId()));
    }

    public void removerVendaDaTabela(VendasTable vendasT) {
        vendaDAO.delete(vendasT.getId());
        vendasTables.removeAll();
    }
}
