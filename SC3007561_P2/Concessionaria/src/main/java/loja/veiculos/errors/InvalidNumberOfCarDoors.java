package loja.veiculos.errors;

public class InvalidNumberOfCarDoors extends RuntimeException {
    public InvalidNumberOfCarDoors(String message) {
        super(message);
    }
}
