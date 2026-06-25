package br.edu.umfg.repository;

import br.edu.umfg.model.Cliente;
import br.edu.umfg.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteRepository {

    public Cliente inserir(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO cliente (nome, telefone) VALUES (?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getTelefone());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) cliente.setId(rs.getInt(1));
            }
            return cliente;
        }
    }

    public Cliente buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM cliente WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Cliente(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("telefone")
                    );
                }
            }
        }
        return null;
    }

    public List<Cliente> listarTodos() throws SQLException {
        String sql = "SELECT * FROM cliente";
        List<Cliente> lista = new ArrayList<>();
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new Cliente(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("telefone")
                ));
            }
        }
        return lista;
    }

    public void atualizar(Cliente cliente) throws SQLException {
        String sql = "UPDATE cliente SET nome = ?, telefone = ? WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getTelefone());
            stmt.setInt(3, cliente.getId());
            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM cliente WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}