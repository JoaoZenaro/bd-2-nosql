package br.edu.unoesc.app;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

import br.edu.unoesc.dao.ProdutoDAO;
import br.edu.unoesc.modelo.Produto;

public class Principal {
    public static void main(String[] args) {
        SimpleDateFormat fd = new SimpleDateFormat("dd.MM.yyyy");
        NumberFormat fm = NumberFormat.getCurrencyInstance();

        ProdutoDAO dao = new ProdutoDAO();

        // Produto prod = new Produto(null, "TV", Date.valueOf(LocalDate.now()), 15, new
        // BigDecimal("20000.5"));
        // dao.adicionar(prod);

        // Produto updatedProd = new Produto(5, "TV modificada",
        // Date.valueOf("2023-10-31"), 51, new BigDecimal("123.45"), "Observation");
        // dao.alterar(updatedProd);

        // dao.excluir(5);

        List<Produto> lista = dao.listarTodos();

        System.out.println("+-------------------------+");
        System.out.println("|    Lista de Contatos    |");
        System.out.println("+-------------------------+");

        for (Produto produto : lista) {
            System.out.println("Id............: " + produto.getIdProd());
            System.out.println("Nome Produto..: " + produto.getNomeProd());
            System.out.println("Data Cadastro.: " + fd.format(produto.getDataCadastro()));
            System.out.println("Quantidade....: " + produto.getQuantidade());
            System.out.println("Preço.........: " + fm.format(produto.getPreco()));
            System.out.println("Observação....: " + produto.getObservacao());
            System.out.println();
        }

        Produto searchProd = dao.buscarPorId(2);
        if (searchProd != null) {
            System.out.println("Id............: " + searchProd.getIdProd());
            System.out.println("Nome Produto..: " + searchProd.getNomeProd());
            System.out.println("Data Cadastro.: " + fd.format(searchProd.getDataCadastro()));
            System.out.println("Quantidade....: " + searchProd.getQuantidade());
            System.out.println("Preço.........: " + fm.format(searchProd.getPreco()));
            System.out.println("Observação....: " + searchProd.getObservacao());
            System.out.println();
        }
    }
}