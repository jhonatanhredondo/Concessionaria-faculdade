package loja.veiculos.DAO;

import javafx.collections.FXCollections;
import loja.veiculos.entities.Vendedor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class VendedorDAO implements DAO<Vendedor, Integer> {


    @Override
    public Integer insert(Vendedor vendedor) {
        String sql = "INSERT INTO Vendedores (nome, email) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = ConnectionFactory.criarPreparedStatement(sql)) {
            preparedStatement.setString(1, vendedor.getNome());
            preparedStatement.setString(2, vendedor.getEmail());

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
    public List<Vendedor> selectAll() {
        String sql = "SELECT * FROM Vendedores";
        List<Vendedor> vendedores = FXCollections.observableArrayList();
        try (PreparedStatement preparedStatement = ConnectionFactory.criarPreparedStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
                vendedores.add(transformarEntidade(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vendedores;
    }


    private Vendedor transformarEntidade(ResultSet resultSet) throws SQLException {
        return new Vendedor(resultSet.getInt("id"), resultSet.getString("nome"), resultSet.getString("email"));
    }

    @Override
    public Vendedor selectOne(Integer id) {
        String sql = "SELECT * FROM Vendedores WHERE id = ?";
        Vendedor vendedor = null;
        try (PreparedStatement preparedStatement = ConnectionFactory.criarPreparedStatement(sql)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next())
                vendedor = transformarEntidade(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vendedor;
    }

    @Override
    public void update(Vendedor vendedor) {
        String sql = "UPDATE Vendedores SET nome = ?, email = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = ConnectionFactory.criarPreparedStatement(sql)) {
            preparedStatement.setString(1, vendedor.getNome());
            preparedStatement.setString(2, vendedor.getEmail());
            preparedStatement.setInt(3, vendedor.getId());

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM Vendedores WHERE id = ?";

        try(PreparedStatement preparedStatement = ConnectionFactory.criarPreparedStatement(sql)) {
            preparedStatement.setInt(1, id);

            preparedStatement.execute();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
