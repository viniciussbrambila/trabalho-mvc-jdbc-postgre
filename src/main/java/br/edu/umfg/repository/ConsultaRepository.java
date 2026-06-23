package br.edu.umfg.repository;
import br.edu.umfg.model.Consulta;
import br.edu.umfg.util.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultaRepository {


    public void salvar(Consulta consulta) {
        String sql = "INSERT INTO consulta (id_animal, data, motivo, valor) VALUES (?, ?, ?, ?) RETURNING id";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, consulta.getIdAnimal());
            stmt.setDate(2, Date.valueOf(consulta.getData()));
            stmt.setString(3, consulta.getMotivo());
            stmt.setDouble(4, consulta.getValor());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                consulta.setId(rs.getInt("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar consulta: " + e.getMessage());
        }
    }


    public List<Consulta> listarTodos() {
        List<Consulta> consultas = new ArrayList<>();
        String sql = "SELECT * FROM consulta";
        try (Connection conn = Conexao.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Consulta c = new Consulta();
                c.setId(rs.getInt("id"));
                c.setIdAnimal(rs.getInt("id_animal"));
                c.setData(rs.getDate("data").toLocalDate());
                c.setMotivo(rs.getString("motivo"));
                c.setValor(rs.getDouble("valor"));
                consultas.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar consultas: " + e.getMessage());
        }
        return consultas;
    }

    public List<Consulta> listarPorAnimal(int idAnimal) {
        List<Consulta> consultas = new ArrayList<>();
        String sql = "SELECT * FROM consulta WHERE id_animal = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAnimal);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Consulta c = new Consulta();
                c.setId(rs.getInt("id"));
                c.setIdAnimal(rs.getInt("id_animal"));
                c.setData(rs.getDate("data").toLocalDate());
                c.setMotivo(rs.getString("motivo"));
                c.setValor(rs.getDouble("valor"));
                consultas.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar consultas por animal: " + e.getMessage());
        }
        return consultas;
    }

    public Consulta buscarPorId(int id) {
        String sql = "SELECT * FROM consulta WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Consulta c = new Consulta();
                c.setId(rs.getInt("id"));
                c.setIdAnimal(rs.getInt("id_animal"));
                c.setData(rs.getDate("data").toLocalDate());
                c.setMotivo(rs.getString("motivo"));
                c.setValor(rs.getDouble("valor"));
                return c;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar consulta: " + e.getMessage());
        }
        return null;
    }

    public void atualizar(Consulta consulta) {
        String sql = "UPDATE consulta SET id_animal = ?, data = ?, motivo = ?, valor = ? WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, consulta.getIdAnimal());
            stmt.setDate(2, Date.valueOf(consulta.getData()));
            stmt.setString(3, consulta.getMotivo());
            stmt.setDouble(4, consulta.getValor());
            stmt.setInt(5, consulta.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar consulta: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM consulta WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar consulta: " + e.getMessage());
        }
    }
}