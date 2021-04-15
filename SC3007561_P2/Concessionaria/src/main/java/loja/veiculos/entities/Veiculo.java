package loja.veiculos.entities;


public abstract class Veiculo {
    private Integer id;
    private String marca;
    private String modelo;
    private Integer ano;
    private Double preco;
    private Integer quantidade;


    private Integer quantidadePortas;

    public Veiculo(Integer id, String marca, String modelo, Integer ano, Double preco, Integer quantidade, Integer quantidadePortas) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.preco = preco;
        this.quantidade = quantidade;
        this.quantidadePortas = quantidadePortas;
    }

    public Veiculo(String marca, String modelo, Integer ano, Double preco, Integer quantidade, Integer quantidadePortas) {
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.preco = preco;
        this.quantidade = quantidade;
        this.quantidadePortas = quantidadePortas;
    }

    public void veiculoVendido() {
        removerUm();
    }

    public void adicionarUm() {
        this.quantidade++;
    }

    public void removerUm() {
        this.quantidade--;
    }

    public abstract Double getLimiteParcelas();

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }


    public Integer getQuantidadePortas() {
        return quantidadePortas;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
}
