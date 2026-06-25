package br.edu.umfg.repository;

import br.edu.umfg.model.Veiculo;
import br.edu.umfg.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VeiculoRepository {

    public Veiculo inserir(Veiculo veiculo) throws SQLException {
        String sql = "INSERT INTO veiculo (placa, modelo, ano, id_cliente) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, veiculo.getPlaca());
            stmt.setString(2, veiculo.getModelo());
            stmt.setInt(3, veiculo.getAno());
            stmt.setInt(4, veiculo.getIdCliente());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) veiculo.setId(rs.getInt(1));
            }
            return veiculo;
        }
    }

    public Veiculo buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM veiculo WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Veiculo(
                            rs.getInt("id"),
                            rs.getString("placa"),
                            rs.getString("modelo"),
                            rs.getInt("ano"),
                            rs.getInt("id_cliente")
                    );
                }
            }
        }
        return null;
    }

    public List<Veiculo> listarTodos() throws SQLException {
        String sql = "SELECT * FROM veiculo";
        List<Veiculo> lista = new ArrayList<>();
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new Veiculo(
                        rs.getInt("id"),
                        rs.getString("placa"),
                        rs.getString("modelo"),
                        rs.getInt("ano"),
                        rs.getInt("id_cliente")
                ));
            }
        }
        return lista;
    }

    // Requisito explícito do enunciado: "ver todos os veículos de um cliente"
    public List<Veiculo> listarPorCliente(int idCliente) throws SQLException {
        String sql = "SELECT * FROM veiculo WHERE id_cliente = ?";
        List<Veiculo> lista = new ArrayList<>();
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Veiculo(
                            rs.getInt("id"),
                            rs.getString("placa"),
                            rs.getString("modelo"),
                            rs.getInt("ano"),
                            rs.getInt("id_cliente")
                    ));
                }
            }
        }
        return lista;
    }

    public void atualizar(Veiculo veiculo) throws SQLException {
        String sql = "UPDATE veiculo SET placa = ?, modelo = ?, ano = ?, id_cliente = ? WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, veiculo.getPlaca());
            stmt.setString(2, veiculo.getModelo());
            stmt.setInt(3, veiculo.getAno());
            stmt.setInt(4, veiculo.getIdCliente());
            stmt.setInt(5, veiculo.getId());
            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM veiculo WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}