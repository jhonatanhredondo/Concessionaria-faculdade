package loja.veiculos.usecases;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import loja.veiculos.DAO.DetalhesDAO;
import loja.veiculos.entities.Detalhes;

public class DetalhesUseCase {
    private ObservableList<Detalhes> detalhes = FXCollections.observableArrayList();
    private DetalhesDAO detalhesDAO = new DetalhesDAO();

    public ObservableList<Detalhes> selecionarTodos() {
        detalhes = (ObservableList<Detalhes>) detalhesDAO.selectAll();
        return detalhes;
    }

    public ObservableList<Detalhes> selecionarVeiculo(Integer id) {
        detalhes = (ObservableList<Detalhes>) detalhesDAO.selectOneVehicle(id);
        return detalhes;
    }

    public ObservableList<Detalhes> selecionarVendedor(Integer id) {
        detalhes = (ObservableList<Detalhes>) detalhesDAO.selectOneVendedor(id);
        return detalhes;
    }
}
