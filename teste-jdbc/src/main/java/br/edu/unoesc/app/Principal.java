package br.edu.unoesc.app;

import java.sql.Connection;
import br.edu.unoesc.db.FabricaConexao;

public class Principal {
    public static void main(String[] args) {
        Connection connection = FabricaConexao.getConnection();

        FabricaConexao.closeConnection();
    }
}
