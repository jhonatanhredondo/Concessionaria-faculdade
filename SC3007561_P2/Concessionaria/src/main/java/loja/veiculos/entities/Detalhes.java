package loja.veiculos.entities;

import loja.veiculos.DAO.VeiculoDAO;
import loja.veiculos.DAO.VendedorDAO;

public class Detalhes {
    Vendedor vendedor;
    Veiculo veiculo;


    private String nomeVendedor;
    private String nomeMarca;
    private String nomeModelo;

    VeiculoDAO veiculoDAO = new VeiculoDAO();
    VendedorDAO vendedorDAO = new VendedorDAO();

    public Detalhes(Vendedor vendedor, Veiculo veiculo, String nomeVendedor, String nomeMarca, String nomeModelo) {
        this.vendedor = vendedor;
        this.veiculo = veiculo;
        this.nomeVendedor = nomeVendedor;
        this.nomeMarca = nomeMarca;
        this.nomeModelo = nomeModelo;
    }

    public Detalhes(Vendedor vendedor, Veiculo veiculo) {
        this.vendedor = vendedor;
        this.veiculo = veiculo;
    }

    public Detalhes(Integer idVendedor, Integer idVeiculo) {
        Veiculo veiculo = veiculoDAO.selectOne(idVeiculo);
        Vendedor vendedor = vendedorDAO.selectOne(idVendedor);
        setNomeVendedor(vendedor.getNome());
        setNomeMarca(veiculo.getMarca());
        setNomeModelo(veiculo.getModelo());
        new Detalhes(vendedor, veiculo);
    }

    public void setNomeVendedor(String nomeVendedor) {
        this.nomeVendedor = nomeVendedor;
    }

    public void setNomeMarca(String nomeMarca) {
        this.nomeMarca = nomeMarca;
    }

    public void setNomeModelo(String nomeModelo) {
        this.nomeModelo = nomeModelo;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public String getNomeVendedor() {
        return nomeVendedor;
    }

    public String getNomeMarca() {
        return nomeMarca;
    }

    public String getNomeModelo() {
        return nomeModelo;
    }
}
