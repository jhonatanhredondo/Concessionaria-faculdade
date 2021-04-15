package loja.veiculos.DAO;

import javafx.collections.FXCollections;
import loja.veiculos.entities.Carro;
import loja.veiculos.entities.Moto;
import loja.veiculos.entities.Veiculo;
import loja.veiculos.entities.Vendedor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class VeiculoDAO implements DAO<Veiculo, Integer> {
    @Override
    public Integer insert(Veiculo veiculo) {
        String sql = "INSERT INTO Veiculos (marca, modelo, ano, valor, quantidade, quantidade_portas) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = ConnectionFactory.criarPreparedStatement(sql)) {
            preparedStatement.setString(1, veiculo.getMarca());
            preparedStatement.setString(2, veiculo.getModelo());
            preparedStatement.setInt(3, veiculo.getAno());
            preparedStatement.setDouble(4, veiculo.getPreco());
            preparedStatement.setInt(5, veiculo.getQuantidade());
            preparedStatement.setInt(6, veiculo.getQuantidadePortas());

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
    public List<Veiculo>  selectAll() {
        String sql = "SELECT * FROM Veiculos";
        List<Veiculo>  veiculos = FXCollections.observableArrayList();
        try (PreparedStatement preparedStatement = ConnectionFactory.criarPreparedStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
                veiculos.add(transformarEntidade(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return veiculos;
    }


    private Veiculo transformarEntidade(ResultSet resultSet) throws SQLException {
        if(resultSet.getInt("quantidade_portas") > 0)
            return new Carro(resultSet.getInt("id"), resultSet.getString("marca"),
                resultSet.getString("modelo"), resultSet.getInt("ano"), resultSet.getDouble("valor"),
                resultSet.getInt("quantidade"), resultSet.getInt("quantidade_portas"));
        else
            return new Moto(resultSet.getInt("id"), resultSet.getString("marca"),
                    resultSet.getString("modelo"), resultSet.getInt("ano"), resultSet.getDouble("valor"),
                    resultSet.getInt("quantidade"), resultSet.getInt("quantidade_portas"));
    }

    @Override
    public Veiculo selectOne(Integer id) {
        String sql = "SELECT * FROM Veiculos WHERE id = ?";
        Veiculo veiculo = null;
        try (PreparedStatement preparedStatement = ConnectionFactory.criarPreparedStatement(sql)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next())
                veiculo = transformarEntidade(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return veiculo;
    }

    @Override
    public void update(Veiculo veiculo) {
        String sql = "UPDATE Veiculos SET marca = ?, modelo = ?, ano = ?, valor = ?, quantidade = ?, quantidade_portas = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = ConnectionFactory.criarPreparedStatement(sql)) {
            preparedStatement.setString(1, veiculo.getMarca());
            preparedStatement.setString(2, veiculo.getModelo());
            preparedStatement.setInt(3, veiculo.getAno());
            preparedStatement.setDouble(4, veiculo.getPreco());
            preparedStatement.setInt(5, veiculo.getQuantidade());
            preparedStatement.setInt(6, veiculo.getQuantidadePortas());

            preparedStatement.setInt(7, veiculo.getId());

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM Veiculos WHERE id = ?";

        try(PreparedStatement preparedStatement = ConnectionFactory.criarPreparedStatement(sql)) {
            preparedStatement.setInt(1, id);

            preparedStatement.execute();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
