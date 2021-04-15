package loja.veiculos.entities;


import java.util.ArrayList;
import java.util.List;

public class Vendedor {
    private Integer id;
    private String nome;
    private String email;
    private List<Veiculo> listaVeiculosVendidos = new ArrayList<>();


    public Vendedor(Integer id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
    }


    public Vendedor(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    public void removerVeiculoVendido(Veiculo veiculo) {
        this.listaVeiculosVendidos.remove(veiculo);
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Veiculo> getListaVeiculosVendidos() {
        return listaVeiculosVendidos;
    }

    public void setVeiculoVendido(Veiculo veiculoVendido) {
        this.listaVeiculosVendidos.add(veiculoVendido);
    }
}
