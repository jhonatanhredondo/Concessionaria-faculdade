package loja.veiculos.errors;

public class EmptyFieldException extends  RuntimeException{
    public EmptyFieldException(String erro) {
        super(erro);
    }
}
