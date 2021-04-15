package loja.veiculos.DAO;

import javafx.collections.FXCollections;
import loja.veiculos.entities.Detalhes;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DetalhesDAO {

    public List<Detalhes> selectAll() {
        String sql = "SELECT id_vendedor, id_veiculo FROM Vendas"; // INNER JOIN Vendedores ON Vendas.id_vendedor = Vendedores.id INNER JOIN Veiculos";
        List<Detalhes> detalhes = FXCollections.observableArrayList();
        try (PreparedStatement preparedStatement = ConnectionFactory.criarPreparedStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
                detalhes.add(transformarEntidade(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detalhes;

    }

    private Detalhes transformarEntidade(ResultSet resultSet) throws SQLException {
        return new Detalhes(resultSet.getInt("id_vendedor"), resultSet.getInt("id_veiculo"));
    }

    public List<Detalhes> selectOneVehicle(Integer id) {
        String sql = "SELECT id_vendedor, id_veiculo FROM Vendas WHERE id_veiculo = ?"; // INNER JOIN Vendedores ON Vendas.id_vendedor = Vendedores.id INNER JOIN Veiculos";
        List<Detalhes> detalhes = FXCollections.observableArrayList();
        try (PreparedStatement preparedStatement = ConnectionFactory.criarPreparedStatement(sql)) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
                detalhes.add(transformarEntidade(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detalhes;

    }

    public List<Detalhes> selectOneVendedor(Integer id) {
        String sql = "SELECT id_vendedor, id_veiculo FROM Vendas WHERE id_vendedor = ?"; // INNER JOIN Vendedores ON Vendas.id_vendedor = Vendedores.id INNER JOIN Veiculos";
        List<Detalhes> detalhes = FXCollections.observableArrayList();
        try (PreparedStatement preparedStatement = ConnectionFactory.criarPreparedStatement(sql)) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
                detalhes.add(transformarEntidade(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detalhes;

    }

}
