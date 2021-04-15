package loja.veiculos.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import org.sqlite.SQLiteDataSource;

public class ConnectionFactory implements AutoCloseable {
    private static Connection connection;
    private static PreparedStatement preparedStatement;
    private static Statement statement;

    public static Connection criarConexao() {
        try {
            instanciarConexaoSeNull();
        }catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return connection;
    }

    private static void instanciarConexaoSeNull() throws SQLException {
        SQLiteDataSource sqLiteDataSource = new SQLiteDataSource();
        sqLiteDataSource.setUrl("jdbc:sqlite:database.db");
        if(connection == null) {
            connection = sqLiteDataSource.getConnection();
        }
    }

    public static PreparedStatement criarPreparedStatement(String sql) {
        try {
            preparedStatement = criarConexao().prepareStatement(sql);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return preparedStatement;
    }

    public static Statement criarStatement() {
        try {
            statement = criarConexao().createStatement();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return statement;
    }

    @Override
    public void close() throws Exception {
        if(preparedStatement != null)
            preparedStatement.close();
        if(statement != null)
            statement.close();
        if(connection != null)
            connection.close();
    }
}
