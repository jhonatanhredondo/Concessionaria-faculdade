package loja.veiculos.entities;

public class Moto extends Veiculo {
    private final Double limiteParcelas = 0.25;
    private final Integer quantidadeParcelas = 60;

    public Moto(Integer id, String marca, String modelo, Integer ano, Double preco, Integer quantidade, Integer quantidadePortas) {
        super(id, marca, modelo, ano, preco, quantidade, quantidadePortas);
    }

    public Moto(String marca, String modelo, Integer ano, Double preco, Integer quantidade, Integer quantidadePortas) {
        super(marca, modelo, ano, preco, quantidade, 0);
    }


    public Integer getQuantidadeParcelas() {
        return quantidadeParcelas;
    }

    @Override
    public Double getLimiteParcelas() {
        return limiteParcelas;
    }
}
