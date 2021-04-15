package loja.veiculos.entities;

import loja.veiculos.errors.InvalidNumberOfCarDoors;

public class Carro extends Veiculo {
    private final Integer quantidadeParcelas = 36;

    public Carro(Integer id, String marca, String modelo, Integer ano, Double preco, Integer quantidade, Integer quantidadePortas) {
        super(id, marca, modelo, ano, preco, quantidade, quantidadePortas);
    }

    public Carro(String marca, String modelo, Integer ano, Double preco, Integer quantidade, Integer quantidadePortas) {
        super(marca, modelo, ano, preco, quantidade, quantidadePortas);
    }

    public Integer getQuantidadeParcelas() {
        return quantidadeParcelas;
    }

    @Override
    public Double getLimiteParcelas() {
        return getLimitePelaQuantidadePortas();
    }

    public Double getLimitePelaQuantidadePortas() {
        if(this.getQuantidadePortas() == 4 || this.getQuantidadePortas() == 3)
            return 0.25;
        else if(this.getQuantidadePortas() == 2)
            return 0.10;
        else
            throw new InvalidNumberOfCarDoors("O número de portas inserido é inválido");
    }

}
