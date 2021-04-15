package loja.veiculos.errors;

public class EmptyVehicleInStock extends RuntimeException{
    public EmptyVehicleInStock(String error) {
        super(error);
    }
}
