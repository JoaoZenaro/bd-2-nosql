package br.edu.unoesc.dao;

import java.util.List;
import br.edu.unoesc.modelo.Produto;

public interface IProdutoDAO {
    void adicionar(Produto p);

    void alterar(Produto p);

    void excluir(Integer id);

    List<Produto> listarTodos();

    Produto buscarPorId(Integer id);
}