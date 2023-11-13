package br.edu.unoesc.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FabricaConexao {
    private static Connection connection = null;

    private FabricaConexao() {
    }

    public static Connection getConnection() {
        try {
            final String url = "jdbc:mysql://db/unoesc_crud_jdbc";
            final String usuario = "root";
            final String senha = "password";

            connection = DriverManager.getConnection(url, usuario, senha);
            System.out.println("Conexão realizada com sucesso!");

            return connection;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Conexão fechada com sucesso!");
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }
}
