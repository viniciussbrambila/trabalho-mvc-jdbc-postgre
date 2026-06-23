package br.edu.umfg.repository;
import br.edu.umfg.model.Tutor;
import br.edu.umfg.util.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TutorRepository {

    public void salvar(Tutor tutor) {
        String sql = "INSERT INTO tutor (nome, endereco, telefone) VALUES (?, ?, ?) RETURNING id";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tutor.getNome());
            stmt.setString(2, tutor.getEndereco());
            stmt.setString(3, tutor.getTelefone());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                tutor.setId(rs.getInt("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar tutor: " + e.getMessage());
        }
    }



    public List<Tutor> listarTodos() {
        List<Tutor> tutores = new ArrayList<>();
        String sql = "SELECT * FROM tutor";
        try (Connection conn = Conexao.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Tutor t = new Tutor();
                t.setId(rs.getInt("id"));
                t.setNome(rs.getString("nome"));
                t.setEndereco(rs.getString("endereco"));
                t.setTelefone(rs.getString("telefone"));
                tutores.add(t);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar tutores: " + e.getMessage());
        }
        return tutores;
    }

    public Tutor buscarPorId(int id) {
        String sql = "SELECT * FROM tutor WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Tutor t = new Tutor();
                t.setId(rs.getInt("id"));
                t.setNome(rs.getString("nome"));
                t.setEndereco(rs.getString("endereco"));
                t.setTelefone(rs.getString("telefone"));
                return t;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar tutor: " + e.getMessage());
        }
        return null;
    }

    public void atualizar(Tutor tutor) {
        String sql = "UPDATE tutor SET nome = ?, endereco = ?, telefone = ? WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tutor.getNome());
            stmt.setString(2, tutor.getEndereco());
            stmt.setString(3, tutor.getTelefone());
            stmt.setInt(4, tutor.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar tutor: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM tutor WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar tutor: " + e.getMessage());
        }
    }
}