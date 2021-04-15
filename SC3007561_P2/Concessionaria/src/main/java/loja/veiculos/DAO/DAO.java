package loja.veiculos.DAO;

import loja.veiculos.entities.Vendedor;

import java.util.List;

public interface DAO <T, K> {
    K insert(T type);
    List<T> selectAll();
    T selectOne(K id);
    void update(T type);
    void delete(K key);
}
