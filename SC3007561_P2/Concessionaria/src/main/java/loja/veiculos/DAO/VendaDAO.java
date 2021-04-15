package loja.veiculos.DAO;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import loja.veiculos.entities.Venda;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class VendaDAO implements DAO<Venda, Integer> {

    @Override
    public Integer insert(Venda venda) {
        String sql = "INSERT INTO Vendas (id_veiculo, data_venda, valor_total, num_pagamentos, valor_parcela, id_vendedor) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = ConnectionFactory.criarPreparedStatement(sql)) {
            preparedStatement.setInt(1, venda.getVeiculo().getId());
            preparedStatement.setString(2, venda.getLocalDate());
            preparedStatement.setDouble(3, venda.getValor());
            preparedStatement.setInt(4, venda.getNumParcelas());
            preparedStatement.setDouble(5, venda.getValorParcela());
            preparedStatement.setInt(6, venda.getVendedor().getId());

            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            Integer idGerado = resultSet.getInt(1);

            return idGerado;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public List<Venda> selectAll() {
        String sql = "SELECT * FROM Vendas";
        ObservableList<Venda> vendas = FXCollections.observableArrayList();
        try (PreparedStatement preparedStatement = ConnectionFactory.criarPreparedStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
                vendas.add(transformarEntidade(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vendas;
    }


    private Venda transformarEntidade(ResultSet resultSet) throws SQLException {
        return new Venda(resultSet.getInt("id"), resultSet.getInt("id_veiculo"),
                resultSet.getString("data_venda"), resultSet.getDouble("valor_total"),
                resultSet.getInt("num_pagamentos"), resultSet.getDouble("valor_parcela"), resultSet.getInt("id_vendedor"));
    }

    @Override
    public Venda selectOne(Integer id) {
        String sql = "SELECT * FROM Vendas WHERE id = ?";
        Venda venda = null;
        try (PreparedStatement preparedStatement = ConnectionFactory.criarPreparedStatement(sql)) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
                venda = transformarEntidade(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return venda;
    }

    @Override
    public void update(Venda venda) {
        String sql = "UPDATE Vendas SET id_veiculo = ?, data_venda = ?, valor_total = ?, num_pagamentos = ?, valor_parcela = ?, id_vendedor = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = ConnectionFactory.criarPreparedStatement(sql)) {
            preparedStatement.setInt(1, venda.getVeiculo().getId());
            preparedStatement.setString(2, venda.getLocalDate());
            preparedStatement.setDouble(3, venda.getValorTotal(venda.getVeiculo()));
            preparedStatement.setInt(4, venda.getNumParcelas());
            preparedStatement.setDouble(5, venda.getValorParcela());
            preparedStatement.setInt(6, venda.getVendedor().getId());
            preparedStatement.setInt(7, venda.getId());

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM Vendas WHERE id = ?";

        try(PreparedStatement preparedStatement = ConnectionFactory.criarPreparedStatement(sql)) {
            preparedStatement.setInt(1, id);

            preparedStatement.execute();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
