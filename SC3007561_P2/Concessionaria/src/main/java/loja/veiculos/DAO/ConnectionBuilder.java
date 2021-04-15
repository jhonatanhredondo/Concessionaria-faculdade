package loja.veiculos.DAO;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionBuilder {

    public void construirBaseDados() {
        if(dataBaseNaoExiste()) {
            construirTabela();
        }
    }

    private boolean dataBaseNaoExiste() {
        return !Files.exists(Paths.get("database.db"));
    }

    private void construirTabela() {
        try (Statement statement = ConnectionFactory.criarStatement()) {
            statement.addBatch(criarTabelaVendedores());
            statement.addBatch(criarTabelaVeiculos());
            statement.addBatch(criarTabelaVendas());
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private String criarTabelaVendedores() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CREATE TABLE Vendedores(");
        stringBuilder.append("id INTEGER PRIMARY KEY AUTOINCREMENT, ");
        stringBuilder.append("nome TEXT NOT NULL, ");
        stringBuilder.append("email TEXT NOT NULL ");
        stringBuilder.append(");");
        return stringBuilder.toString();
    }

    private String criarTabelaVeiculos() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nCREATE TABLE Veiculos(");
        stringBuilder.append("id INTEGER PRIMARY KEY AUTOINCREMENT, ");
        stringBuilder.append("marca TEXT NOT NULL, ");
        stringBuilder.append("modelo TEXT NOT NULL, ");
        stringBuilder.append("ano INTEGER NOT NULL, ");
        stringBuilder.append("valor REAL NOT NULL, ");
        stringBuilder.append("quantidade INTEGER,");
        stringBuilder.append("quantidade_portas INTEGER");
        stringBuilder.append(");");
        return stringBuilder.toString();
    }

    private String criarTabelaVendas() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nCREATE TABLE VENDAS(");
        stringBuilder.append("id INTEGER PRIMARY KEY AUTOINCREMENT, ");
        stringBuilder.append("id_veiculo INTEGER NOT NULL, ");
        stringBuilder.append("data_venda STRING NOT NULL,");
        stringBuilder.append("valor_total STRING NOT NULL, ");
        stringBuilder.append("num_pagamentos INTEGER NOT NULL,");
        stringBuilder.append("valor_parcela REAL,");
        stringBuilder.append("id_vendedor INTEGER NOT NULL,");
        stringBuilder.append("CONSTRAINT ck_id_veiculo FOREIGN KEY (id_veiculo) REFERENCES Veiculos(id),");
        stringBuilder.append("CONSTRAINT ck_id_vendedor FOREIGN KEY (id_vendedor) REFERENCES Vendedores(id)");
        stringBuilder.append(");");
        return stringBuilder.toString();
    }
}
