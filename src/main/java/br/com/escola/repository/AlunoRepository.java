package br.com.escola.repository;

import br.com.escola.model.Aluno;
import br.com.escola.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repositório de Aluno — executa SQL puro via JDBC.
 * Implementa CRUD completo: salvar, buscarPorId, listarTodos, atualizar, deletar.
 */
public class AlunoRepository {

    // ------------------------------------------------------------------ SALVAR
    public Aluno salvar(Aluno aluno) {
        String sql = "INSERT INTO aluno (nome, email, telefone) VALUES (?, ?, ?) RETURNING id";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getEmail());
            stmt.setString(3, aluno.getTelefone());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                aluno.setId(rs.getInt("id"));
            }
            System.out.println("[AlunoRepository] Aluno salvo: " + aluno);
            return aluno;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar aluno: " + e.getMessage(), e);
        }
    }

    // --------------------------------------------------------------- BUSCAR POR ID
    public Optional<Aluno> buscarPorId(int id) {
        String sql = "SELECT id, nome, email, telefone FROM aluno WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapear(rs));
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar aluno por id: " + e.getMessage(), e);
        }
    }

    // --------------------------------------------------------------- LISTAR TODOS
    public List<Aluno> listarTodos() {
        String sql = "SELECT id, nome, email, telefone FROM aluno ORDER BY id";
        List<Aluno> lista = new ArrayList<>();

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }
            return lista;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar alunos: " + e.getMessage(), e);
        }
    }

    // ----------------------------------------------------------------- ATUALIZAR
    public void atualizar(Aluno aluno) {
        String sql = "UPDATE aluno SET nome = ?, email = ?, telefone = ? WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getEmail());
            stmt.setString(3, aluno.getTelefone());
            stmt.setInt(4, aluno.getId());

            int linhas = stmt.executeUpdate();
            if (linhas == 0) {
                throw new RuntimeException("Aluno com id=" + aluno.getId() + " não encontrado para atualização.");
            }
            System.out.println("[AlunoRepository] Aluno atualizado: " + aluno);

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar aluno: " + e.getMessage(), e);
        }
    }

    // ------------------------------------------------------------------ DELETAR
    public void deletar(int id) {
        String sql = "DELETE FROM aluno WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int linhas = stmt.executeUpdate();
            if (linhas == 0) {
                throw new RuntimeException("Aluno com id=" + id + " não encontrado para exclusão.");
            }
            System.out.println("[AlunoRepository] Aluno deletado, id=" + id);

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar aluno: " + e.getMessage(), e);
        }
    }

    // --------------------------------------------------------------- HELPER: mapear ResultSet → Aluno
    private Aluno mapear(ResultSet rs) throws SQLException {
        return new Aluno(
            rs.getInt("id"),
            rs.getString("nome"),
            rs.getString("email"),
            rs.getString("telefone")
        );
    }
}
