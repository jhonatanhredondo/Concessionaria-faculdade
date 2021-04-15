package loja.veiculos.entities;


public class VendasTable {
    private Integer id;
    private Integer idVeiculo;
    private String dataVenda;
    private String tipoPagamento;
    private Double valorTotal;
    private Integer numParcelas;
    private Double valorParcela;
    private Integer idVendedor;

    public VendasTable() {
    }

    public VendasTable(Integer id, Integer idVeiculo, String dataVenda, String tipoPagamento, Double valorTotal, Integer numParcelas, Double valorParcela, Integer idVendedor) {
        this.id = id;
        this.idVeiculo = idVeiculo;
        this.dataVenda = dataVenda;
        this.tipoPagamento = tipoPagamento;
        this.valorTotal = valorTotal;
        this.numParcelas = numParcelas;
        this.valorParcela = valorParcela;
        this.idVendedor = idVendedor;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setIdVeiculo(Integer idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    public void setDataVenda(String dataVenda) {
        this.dataVenda = dataVenda;
    }

    public void setTipoPagamento(String tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public void setNumParcelas(Integer numParcelas) {
        this.numParcelas = numParcelas;
    }

    public void setValorParcela(Double valorParcela) {
        this.valorParcela = valorParcela;
    }

    public void setIdVendedor(Integer idVendedor) {
        this.idVendedor = idVendedor;
    }

    public Integer getId() {
        return id;
    }

    public Integer getIdVeiculo() {
        return idVeiculo;
    }

    public String getDataVenda() {
        return dataVenda;
    }

    public String getTipoPagamento() {
        return tipoPagamento;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public Integer getNumParcelas() {
        return numParcelas;
    }

    public Double getValorParcela() {
        return valorParcela;
    }

    public Integer getIdVendedor() {
        return idVendedor;
    }
}
