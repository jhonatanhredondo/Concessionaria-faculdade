package loja.veiculos.entities;

import loja.veiculos.DAO.VeiculoDAO;
import loja.veiculos.DAO.VendedorDAO;
import loja.veiculos.errors.EmptySellerException;
import loja.veiculos.errors.EmptyVehicleInStock;
import loja.veiculos.errors.InvalidParcelValue;
import loja.veiculos.usecases.VeiculoUseCase;
import loja.veiculos.usecases.VendedorUseCase;

import java.time.LocalDate;


public class Venda {
    private Integer id;
    private Veiculo veiculo;
    private TipoPagamento tipoPagamento;
    private Double valorTotal;
    private Integer numParcelas;
    private Double valorParcela;
    private Vendedor vendedor;
    private LocalDate localDate;

    private VeiculoUseCase veiculoUseCase = new VeiculoUseCase();
    private VendedorUseCase vendedorUseCase = new VendedorUseCase();

    public Venda(Integer id, Integer veiculo, String localDate, Double valorTotal, Integer numParcelas, Double valorParcela, Integer vendedor) {
        this.id = id;
        this.veiculo = veiculoUseCase.buscarERetornarUmVeiculo(veiculo);
        if(numParcelas > 0) {
            tipoPagamento = TipoPagamento.PARCELADO;
        } else {
            tipoPagamento = TipoPagamento.AVISTA;
        }
        this.valorTotal = valorTotal;
        this.numParcelas = numParcelas;
        this.valorParcela = valorParcela;
        if(localDate != "Não informado")
            this.localDate = LocalDate.parse(localDate);
        else {
            this.localDate = LocalDate.now();
        }
        this.vendedor = vendedorUseCase.buscarERetornarUmVendedor(vendedor);
    }

    public Venda(Integer idVeiculo, String tipoPagamento, Double valorTotal, Integer numParcelas, Integer idVendedor) {
        Veiculo veiculo = veiculoUseCase.buscarERetornarUmVeiculo(idVeiculo);
        Vendedor vendedor = vendedorUseCase.buscarERetornarUmVendedor(idVendedor);
        if(tipoPagamento == "parcelado")
            new Venda(veiculo, TipoPagamento.PARCELADO, valorTotal, numParcelas, calculoValorParcela(veiculo, valorTotal, numParcelas), vendedor);
        else
            new Venda(veiculo, TipoPagamento.AVISTA, valorTotal, 1, valorTotal, vendedor);
    }


    public Venda(Veiculo veiculo, String tipoPagamento, Double valorTotal, Integer numParcelas, Vendedor vendedor) {
        if(tipoPagamento == "parcelado") {
            new Venda(veiculo, TipoPagamento.PARCELADO, valorTotal, numParcelas, calculoValorParcela(veiculo, valorTotal, numParcelas), vendedor);
        }
        else {
            new Venda(veiculo, TipoPagamento.AVISTA, valorTotal, 1, valorTotal, vendedor);
        }

    }

    public Venda(Veiculo veiculo, TipoPagamento tipoPagamento, Double valorTotal, Integer numParcelas, Double valorParcela, Vendedor vendedor) {

        if(veiculo == null)
            throw new IllegalArgumentException("Valor de veículo inserido é nulo...");
        if(veiculo.getQuantidade() <= 0)
            throw new EmptyVehicleInStock("O veículo não se encontra mais no estoque...");
        this.veiculo = veiculo;

        this.tipoPagamento = tipoPagamento;

        if(valorTotal <= 0 || valorTotal == null)
            throw new IllegalArgumentException("Valor total inserido é inválido, por ser nulo ou menor ou igual a zero...");

        this.valorTotal = valorTotal;

        this.numParcelas = numParcelas;
        this.valorParcela = valorParcela;
        this.localDate = LocalDate.now();

        if(vendedor == null)
            throw new EmptySellerException("Nenhum vendedor foi inserido...");
        this.vendedor = vendedor;

        this.vendedor.setVeiculoVendido(veiculo);
        this.veiculo.veiculoVendido();
    }

    public Venda() {
    }


    public Double calculoValorParcela(Veiculo veiculo, Double valorTotal, Integer numParcelas) {
        if(veiculo instanceof Moto && ((Moto) veiculo).getQuantidadeParcelas() <= 60 && ((Moto) veiculo).getQuantidadeParcelas() > 1) {
            return (getValorTotal(veiculo)) / numParcelas;
        } else if(veiculo instanceof Carro && numParcelas <= ((Carro) veiculo).getQuantidadeParcelas() && ((Carro) veiculo).getQuantidadeParcelas() > 1) {
            return (getValorTotal(veiculo)) / numParcelas;
        } else {
            throw new InvalidParcelValue("Parametro de parcela inválido...");
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public String getTipoPagamento() {
        return tipoPagamento.toString().substring(0, 1).toUpperCase() + tipoPagamento.toString().substring(1);
    }

    public void setTipoPagamento(String tipoPagamento) {
        if(tipoPagamento == "parcelado")
            this.tipoPagamento = TipoPagamento.PARCELADO;
        else
            this.tipoPagamento = TipoPagamento.AVISTA;
    }

    public Double getValorTotal(Veiculo veiculo) {
        return (veiculo.getPreco() * (1 +veiculo.getLimiteParcelas())) * 10;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Integer getNumParcelas() {
        if (numParcelas == null)
            this.numParcelas = 1;
        return numParcelas;
    }

    public void setNumParcelas(Integer numParcelas) {
        if (numParcelas == null)
            this.numParcelas = 1;
        this.numParcelas = numParcelas;
    }

    public Double getValorParcela() {
        if(this.valorParcela == null)
            return getValorTotal();
        return valorParcela;
    }

    private Double getValorTotal() {
        return valorTotal;
    }

    public void setValorParcela(Double valorParcela) {
        this.valorParcela = valorParcela;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public String getLocalDate() {
        if(localDate == null)
            return "Não informado";
        return localDate.toString();
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public Double getValor() {
        return veiculo.getPreco();
    }
}
