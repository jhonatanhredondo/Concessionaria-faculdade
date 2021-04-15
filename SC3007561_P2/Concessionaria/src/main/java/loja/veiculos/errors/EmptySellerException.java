package loja.veiculos.errors;

public class EmptySellerException extends RuntimeException {
    public EmptySellerException(String error) {
        super(error);
    }
}
