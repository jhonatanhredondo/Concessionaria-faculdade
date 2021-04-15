package loja.veiculos.errors;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException (String erro) {
        super(erro);
    }

}
