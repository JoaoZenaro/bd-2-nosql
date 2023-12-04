package br.edu.unoesc.app;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import br.edu.unoesc.dao.ProdutoDAO;
import br.edu.unoesc.db.DbException;
import br.edu.unoesc.modelo.Produto;

public class Principal {
    public static void main(String[] args) {
        SimpleDateFormat fd = new SimpleDateFormat("dd.MM.yyyy");
        NumberFormat fm = NumberFormat.getCurrencyInstance();

        ProdutoDAO dao = new ProdutoDAO();

        // Produto para inserir
        Produto prod = new Produto(null, "Smartwatch", Date.valueOf("2023-10-20"), 50, new BigDecimal("1999.99"));

        // Produto para atualizar
        Produto updatedProd = new Produto(5, "Smartwatch modificado",
                Date.valueOf("2023-10-31"), 51, new BigDecimal("123.45"));

        // Map<String, Integer> ret = new HashMap<>();

        // try {
        // ret = dao.salvar(updatedProd);
        // } finally {
        // System.out.println(ret);
        // }

        List<Produto> lista = dao.listarTodos();

        System.out.println("+-------------------------+");
        System.out.println("|    Lista de Produtos    |");
        System.out.println("+-------------------------+");

        for (Produto produto : lista) {
            System.out.println("Id............: " + produto.getIdProd());
            System.out.println("Nome Produto..: " + produto.getNomeProd());
            System.out.println("Data Cadastro.: " + fd.format(produto.getDataCadastro()));
            System.out.println("Quantidade....: " + produto.getQuantidade());
            System.out.println("Preço.........: " + fm.format(produto.getPreco()));
            System.out.println();
        }

        // region Excluir

        try {
            if (dao.excluir(3)) {
                System.out.println("Exclusão efetuada com sucesso!");
            } else {
                System.out.println("Registro não excluido!");
            }

        } catch (DbException e) {
            System.out.println(e.getMessage());

        }

        // endregion

        // region Busca por nome

        System.out.println("Informe o nome do produto: ");
        try (Scanner sc = new Scanner(System.in)) {
            String nome = sc.nextLine();
            lista = dao.buscarPorNome(nome);
        }

        if (lista.isEmpty()) {
            System.out.println("\nTabela está vazia!");
        } else {

            System.out.println("# Total de registros: " + dao.getNumeroRegistros());
            String msg = "# Registros coincidindo com o filtro de busca: " + lista.size();
            System.out.println("=".repeat(msg.length()));

            for (Produto produto : lista) {
                System.out.println("\nId............: " + produto.getIdProd());
                System.out.println("Nome Produto..: " + produto.getNomeProd());
                System.out.println("Data Cadastro.: " + fd.format(produto.getDataCadastro()));
                System.out.println("Quantidade....: " + produto.getQuantidade());
                System.out.println("Preço.........: " + fm.format(produto.getPreco()));
            }

            System.out.println("-".repeat(msg.length()));
            System.out.println(msg);
        }

        // endregion

        // region Busca por Id

        // Produto searchProd = dao.buscarPorId(2);
        // if (searchProd != null) {
        // System.out.println("Id............: " + searchProd.getIdProd());
        // System.out.println("Nome Produto..: " + searchProd.getNomeProd());
        // System.out.println("Data Cadastro.: " +
        // fd.format(searchProd.getDataCadastro()));
        // System.out.println("Quantidade....: " + searchProd.getQuantidade());
        // System.out.println("Preço.........: " + fm.format(searchProd.getPreco()));
        // System.out.println();
        // }

        // endregion
    }
}