package br.com.escola.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Conexao {

    private static final String URL      = "jdbc:postgresql://localhost:5432/escola_cursos";
    private static final String USUARIO  = "postgres";
    private static final String SENHA    = "137913";


    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (SQLException e) {
            throw new RuntimeException(
                "Erro ao conectar ao banco de dados PostgreSQL.\n" +
                "Verifique: URL=" + URL + " | Usuário=" + USUARIO, e
            );
        }
    }
}
