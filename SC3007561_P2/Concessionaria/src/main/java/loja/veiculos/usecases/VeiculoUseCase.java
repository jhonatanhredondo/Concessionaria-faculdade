package loja.veiculos.usecases;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import loja.veiculos.DAO.VeiculoDAO;
import loja.veiculos.entities.Carro;
import loja.veiculos.entities.Moto;
import loja.veiculos.entities.Veiculo;


public class VeiculoUseCase {
    VeiculoDAO veiculoDAO = new VeiculoDAO();
    ObservableList<Veiculo> listaVeiculos = FXCollections.observableArrayList();

    public void conferirEInserirSeCarroOuMotoEInserir(Integer quantidadePortas, String marca, String modelo, Integer ano, Double preco, Integer quantidade) {
        if(quantidadePortas > 0) {
            Carro carro = new Carro(marca, modelo, ano, preco, quantidade, quantidadePortas);
            Integer id = veiculoDAO.insert(carro);
            carro.setId(id);

        }
        else {
            Moto moto = new Moto(marca, modelo, ano, preco, quantidade, 0);
            Integer id = veiculoDAO.insert(moto);
            moto.setId(id);
        }
    }

    public ObservableList<Veiculo> buscarTodosOsVeiculos() {
        listaVeiculos = (ObservableList<Veiculo>) veiculoDAO.selectAll();
        return listaVeiculos;
    }

    public Veiculo buscarERetornarUmVeiculo(Integer id) {
        Veiculo veiculo = veiculoDAO.selectOne(id);
        return veiculo;
    }

    public void atualizarUmVeiculoNoBanco(Veiculo veiculo) {
        veiculoDAO.update(veiculo);
    }

    public void removerUmVeiculoNoBanco(Integer id) {
        veiculoDAO.delete(id);
    }
}
