package br.edu.umfg.repository;
import br.edu.umfg.model.Animal;
import br.edu.umfg.util.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnimalRepository {

    public void salvar(Animal animal) {
        String sql = "INSERT INTO animal (nome, especie, raca, id_tutor) VALUES (?, ?, ?, ?) RETURNING id";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, animal.getNome());
            stmt.setString(2, animal.getEspecie());
            stmt.setString(3, animal.getRaca());
            stmt.setInt(4, animal.getIdTutor());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                animal.setId(rs.getInt("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar animal: " + e.getMessage());
        }
    }

    public List<Animal> listarTodos() {
        List<Animal> animais = new ArrayList<>();
        String sql = "SELECT * FROM animal";
        try (Connection conn = Conexao.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Animal a = new Animal();
                a.setId(rs.getInt("id"));
                a.setNome(rs.getString("nome"));
                a.setEspecie(rs.getString("especie"));
                a.setRaca(rs.getString("raca"));
                a.setIdTutor(rs.getInt("id_tutor"));
                animais.add(a);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar animais: " + e.getMessage());
        }
        return animais;
    }

    public Animal buscarPorId(int id) {
        String sql = "SELECT * FROM animal WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Animal a = new Animal();
                a.setId(rs.getInt("id"));
                a.setNome(rs.getString("nome"));
                a.setEspecie(rs.getString("especie"));
                a.setRaca(rs.getString("raca"));
                a.setIdTutor(rs.getInt("id_tutor"));
                return a;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar animal: " + e.getMessage());
        }
        return null;
    }

    public List<Animal> listarPorTutor(int idTutor) {
        List<Animal> animais = new ArrayList<>();
        String sql = "SELECT * FROM animal WHERE id_tutor = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idTutor);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Animal a = new Animal();
                a.setId(rs.getInt("id"));
                a.setNome(rs.getString("nome"));
                a.setEspecie(rs.getString("especie"));
                a.setRaca(rs.getString("raca"));
                a.setIdTutor(rs.getInt("id_tutor"));
                animais.add(a);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar animais por tutor: " + e.getMessage());
        }
        return animais;
    }



    public void atualizar(Animal animal) {
        String sql = "UPDATE animal SET nome = ?, especie = ?, raca = ?, id_tutor = ? WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, animal.getNome());
            stmt.setString(2, animal.getEspecie());
            stmt.setString(3, animal.getRaca());
            stmt.setInt(4, animal.getIdTutor());
            stmt.setInt(5, animal.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar animal: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM animal WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar animal: " + e.getMessage());
        }
    }
}