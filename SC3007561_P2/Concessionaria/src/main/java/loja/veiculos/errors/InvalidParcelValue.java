package loja.veiculos.errors;

public class InvalidParcelValue extends RuntimeException {
    public InvalidParcelValue(String error) {
        super(error);
    }
}
