package br.edu.unoesc.dao;

import java.util.List;
import java.util.Map;

import br.edu.unoesc.modelo.Produto;

public interface IProdutoDAO {
    Map<String, Integer> salvar(Produto p);

    boolean excluir(Integer id);

    List<Produto> listarTodos();

    Produto buscarPorId(Integer id);

    List<Produto> buscarPorNome(String nome);

    int getNumeroRegistros();
}