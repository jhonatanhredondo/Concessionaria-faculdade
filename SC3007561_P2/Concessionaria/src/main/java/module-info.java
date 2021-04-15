module loja.veiculos {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires sqlite.jdbc;

    opens loja.veiculos.controllers to javafx.fxml;
    exports loja.veiculos.controllers;
    opens loja.veiculos to javafx.fxml;
    exports loja.veiculos;
    opens loja.veiculos.entities to javafx.fxml;
    exports loja.veiculos.entities;
}