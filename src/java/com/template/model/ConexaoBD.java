package com.template.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gerenciador de conexão com o banco de dados PostgreSQL.
 */
public class ConexaoBD {
    private static final String URL = "jdbc:postgresql://localhost:5432/DB_MVC";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "postgres";

    /**
     * Estabelece e retorna uma nova conexão com o banco de dados.
     *
     * @return Conexão ativa com o banco de dados
     * @throws SQLException caso ocorra falha na conexão
     */
    public Connection conectaBD() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (SQLException e) {
            throw new SQLException("Falha ao conectar ao banco de dados: " + e.getMessage(), e);
        }
    }
}
