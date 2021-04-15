package loja.veiculos.usecases;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import loja.veiculos.DAO.VendedorDAO;
import loja.veiculos.entities.Vendedor;

public class VendedorUseCase {
    VendedorDAO vendedorDAO = new VendedorDAO();
    ObservableList<Vendedor> listaVendedores = FXCollections.observableArrayList();

    public void inserirVendedorNoBanco(Vendedor vendedor) {
        Integer id = vendedorDAO.insert(vendedor);
        vendedor.setId(id);
    }

    public ObservableList<Vendedor> buscarTodosOsVendedores() {
        listaVendedores = (ObservableList<Vendedor>) vendedorDAO.selectAll();
        return listaVendedores;
    }


    public Vendedor buscarERetornarUmVendedor(Integer id) {
        Vendedor vendedor = vendedorDAO.selectOne(id);
        return vendedor;
    }

    public void atualizarUmVendedorNoBanco(Vendedor vendedor) {
        vendedorDAO.update(vendedor);
    }

    public void removerUmVendedorNoBanco(Integer id) {
        vendedorDAO.delete(id);
    }


}
