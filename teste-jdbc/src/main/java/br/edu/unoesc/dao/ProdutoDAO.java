package br.edu.unoesc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.unoesc.db.FabricaConexao;
import br.edu.unoesc.modelo.Produto;

public class ProdutoDAO implements IProdutoDAO {
    private Connection conexao;

    @Override
    public List<Produto> listarTodos() {
        conexao = FabricaConexao.getConnection();
        List<Produto> lista = new ArrayList<>();

        try {
            String sql = "SELECT * FROM produto";
            PreparedStatement stmt = this.conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Produto ct = new Produto();
                ct.setIdProd(rs.getInt("id_prod"));
                ct.setNomeProd(rs.getString("nome_prod"));
                ct.setDataCadastro(rs.getDate("data_cadastro"));
                ct.setQuantidade(rs.getInt("quantidade"));
                ct.setPreco(rs.getBigDecimal("preco"));
                ct.setObservacao(rs.getString("observacao"));
                lista.add(ct);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());

        } finally {
            FabricaConexao.closeConnection();

        }

        return lista;
    }

    @Override
    public void adicionar(Produto p) {
        conexao = FabricaConexao.getConnection();

        try {
            String sql = "INSERT INTO produto (nome_prod, data_cadastro, quantidade, preco, observacao) "
                    + "VALUES (?, ?, ?, ?, ?);";

            PreparedStatement stmt = this.conexao.prepareStatement(sql);
            stmt.setString(1, p.getNomeProd());
            stmt.setDate(2, p.getDataCadastro());
            stmt.setInt(3, p.getQuantidade());
            stmt.setBigDecimal(4, p.getPreco());
            stmt.setString(5, p.getObservacao());
            stmt.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());

        } finally {
            FabricaConexao.closeConnection();

        }
    }

    @Override
    public void alterar(Produto p) {
        conexao = FabricaConexao.getConnection();

        try {
            String sql = "UPDATE produto SET nome_prod=?, data_cadastro=?, quantidade=?, preco=?, observacao=? " +
                    "WHERE id_prod=?";

            PreparedStatement stmt = this.conexao.prepareStatement(sql);
            stmt.setString(1, p.getNomeProd());
            stmt.setDate(2, p.getDataCadastro());
            stmt.setInt(3, p.getQuantidade());
            stmt.setBigDecimal(4, p.getPreco());
            stmt.setString(5, p.getObservacao());

            stmt.setInt(6, p.getIdProd());

            stmt.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());

        } finally {
            FabricaConexao.closeConnection();

        }
    }

    @Override
    public void excluir(Integer id) {
        conexao = FabricaConexao.getConnection();

        try {
            String sql = "DELETE FROM produto WHERE id_prod=?";

            PreparedStatement stmt = this.conexao.prepareStatement(sql);
            stmt.setInt(1, id);

            stmt.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());

        } finally {
            FabricaConexao.closeConnection();

        }
    }

    @Override
    public Produto buscarPorId(Integer id) {
        conexao = FabricaConexao.getConnection();

        Produto p = null;

        try {
            String sql = "SELECT * FROM produto WHERE id_prod=?";

            PreparedStatement stmt = this.conexao.prepareStatement(sql);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                p = new Produto(rs.getInt("id_prod"),
                        rs.getString("nome_prod"),
                        rs.getDate("data_cadastro"),
                        rs.getInt("quantidade"),
                        rs.getBigDecimal("preco"),
                        rs.getString("observacao"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());

        } finally {
            FabricaConexao.closeConnection();

        }

        return p;
    }
}
