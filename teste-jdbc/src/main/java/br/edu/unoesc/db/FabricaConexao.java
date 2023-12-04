package br.edu.unoesc.db;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class FabricaConexao {
    private static Connection connection = null;

    private FabricaConexao() {
    }

    public static Connection getConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                return connection;
            }

            Properties prop = loadProperties();
            final String url = prop.getProperty("url");
            final String usuario = prop.getProperty("usuario");
            final String senha = prop.getProperty("senha");

            connection = DriverManager.getConnection(url, usuario, senha);
            System.out.println("Conexão realizada com sucesso!");

            return connection;
        } catch (SQLException | IOException e) {
            // Converte exceção checada em uma não checada
            throw new DbException(e.getMessage());
        }
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    private static Properties loadProperties() throws FileNotFoundException, IOException {
        try (FileInputStream fs = new FileInputStream("db.properties")) {
            Properties prop = new Properties();
            prop.load(fs);
            return prop;
        }
    }
}
