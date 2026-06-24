package br.com.escola.repository;

import br.com.escola.model.Matricula;
import br.com.escola.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repositório de Matrícula — executa SQL puro via JDBC.
 * Implementa CRUD completo + consultas de histórico exigidas pelo cliente.
 */
public class MatriculaRepository {

    // ------------------------------------------------------------------ SALVAR
    public Matricula salvar(Matricula matricula) {
        String sql = "INSERT INTO matricula (id_aluno, id_curso, data_matricula, valor) " +
                     "VALUES (?, ?, ?, ?) RETURNING id";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, matricula.getIdAluno());
            stmt.setInt(2, matricula.getIdCurso());
            stmt.setDate(3, Date.valueOf(matricula.getDataMatricula()));
            stmt.setDouble(4, matricula.getValor());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                matricula.setId(rs.getInt("id"));
            }
            System.out.println("[MatriculaRepository] Matrícula salva: " + matricula);
            return matricula;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar matrícula: " + e.getMessage(), e);
        }
    }

    // --------------------------------------------------------------- BUSCAR POR ID
    public Optional<Matricula> buscarPorId(int id) {
        String sql = "SELECT id, id_aluno, id_curso, data_matricula, valor FROM matricula WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapear(rs));
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar matrícula por id: " + e.getMessage(), e);
        }
    }

    // --------------------------------------------------------------- LISTAR TODOS
    public List<Matricula> listarTodos() {
        String sql = "SELECT id, id_aluno, id_curso, data_matricula, valor FROM matricula ORDER BY id";
        List<Matricula> lista = new ArrayList<>();

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }
            return lista;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar matrículas: " + e.getMessage(), e);
        }
    }

    // ------------------------------------------------------- BUSCAR POR ALUNO (todos os cursos de um aluno)
    public List<Matricula> buscarPorAluno(int idAluno) {
        String sql = "SELECT m.id, m.id_aluno, m.id_curso, m.data_matricula, m.valor " +
                     "FROM matricula m WHERE m.id_aluno = ? ORDER BY m.data_matricula";

        List<Matricula> lista = new ArrayList<>();

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAluno);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(mapear(rs));
            }
            return lista;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar matrículas por aluno: " + e.getMessage(), e);
        }
    }

    // ------------------------------------------------------- BUSCAR POR CURSO (todos os alunos de um curso)
    public List<Matricula> buscarPorCurso(int idCurso) {
        String sql = "SELECT m.id, m.id_aluno, m.id_curso, m.data_matricula, m.valor " +
                     "FROM matricula m WHERE m.id_curso = ? ORDER BY m.data_matricula";

        List<Matricula> lista = new ArrayList<>();

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCurso);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(mapear(rs));
            }
            return lista;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar matrículas por curso: " + e.getMessage(), e);
        }
    }

    // ----------------------------------------- VERIFICAR SE ALUNO JÁ ESTÁ MATRICULADO NO CURSO
    public boolean existeMatricula(int idAluno, int idCurso) {
        String sql = "SELECT COUNT(*) FROM matricula WHERE id_aluno = ? AND id_curso = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAluno);
            stmt.setInt(2, idCurso);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar matrícula duplicada: " + e.getMessage(), e);
        }
    }

    // ------------------------------------------------------------------ DELETAR
    public void deletar(int id) {
        String sql = "DELETE FROM matricula WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int linhas = stmt.executeUpdate();
            if (linhas == 0) {
                throw new RuntimeException("Matrícula com id=" + id + " não encontrada para exclusão.");
            }
            System.out.println("[MatriculaRepository] Matrícula deletada, id=" + id);

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar matrícula: " + e.getMessage(), e);
        }
    }

    // --------------------------------------------------------------- HELPER: mapear ResultSet → Matricula
    private Matricula mapear(ResultSet rs) throws SQLException {
        return new Matricula(
            rs.getInt("id"),
            rs.getInt("id_aluno"),
            rs.getInt("id_curso"),
            rs.getDate("data_matricula").toLocalDate(),
            rs.getDouble("valor")
        );
    }
}
