package br.com.escola.repository;

import br.com.escola.model.Curso;
import br.com.escola.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repositório de Curso — executa SQL puro via JDBC.
 * Implementa CRUD completo: salvar, buscarPorId, listarTodos, atualizar, deletar.
 * Também oferece atualização de vagas disponíveis (usada pela MatriculaService).
 */
public class CursoRepository {

    // ------------------------------------------------------------------ SALVAR
    public Curso salvar(Curso curso) {
        String sql = "INSERT INTO curso (nome, descricao, carga_horaria, vagas_totais, vagas_disponiveis) " +
                     "VALUES (?, ?, ?, ?, ?) RETURNING id";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, curso.getNome());
            stmt.setString(2, curso.getDescricao());
            stmt.setInt(3, curso.getCargaHoraria());
            stmt.setInt(4, curso.getVagasTotais());
            stmt.setInt(5, curso.getVagasDisponiveis());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                curso.setId(rs.getInt("id"));
            }
            System.out.println("[CursoRepository] Curso salvo: " + curso);
            return curso;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar curso: " + e.getMessage(), e);
        }
    }

    // --------------------------------------------------------------- BUSCAR POR ID
    public Optional<Curso> buscarPorId(int id) {
        String sql = "SELECT id, nome, descricao, carga_horaria, vagas_totais, vagas_disponiveis " +
                     "FROM curso WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapear(rs));
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar curso por id: " + e.getMessage(), e);
        }
    }

    // --------------------------------------------------------------- LISTAR TODOS
    public List<Curso> listarTodos() {
        String sql = "SELECT id, nome, descricao, carga_horaria, vagas_totais, vagas_disponiveis " +
                     "FROM curso ORDER BY id";
        List<Curso> lista = new ArrayList<>();

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }
            return lista;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar cursos: " + e.getMessage(), e);
        }
    }

    // ----------------------------------------------------------------- ATUALIZAR
    public void atualizar(Curso curso) {
        String sql = "UPDATE curso SET nome = ?, descricao = ?, carga_horaria = ?, " +
                     "vagas_totais = ?, vagas_disponiveis = ? WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, curso.getNome());
            stmt.setString(2, curso.getDescricao());
            stmt.setInt(3, curso.getCargaHoraria());
            stmt.setInt(4, curso.getVagasTotais());
            stmt.setInt(5, curso.getVagasDisponiveis());
            stmt.setInt(6, curso.getId());

            int linhas = stmt.executeUpdate();
            if (linhas == 0) {
                throw new RuntimeException("Curso com id=" + curso.getId() + " não encontrado para atualização.");
            }
            System.out.println("[CursoRepository] Curso atualizado: " + curso);

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar curso: " + e.getMessage(), e);
        }
    }

    // -------------------------------------------------------- DECREMENTAR VAGAS (após matrícula)
    public void decrementarVaga(int idCurso) {
        String sql = "UPDATE curso SET vagas_disponiveis = vagas_disponiveis - 1 " +
                     "WHERE id = ? AND vagas_disponiveis > 0";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCurso);
            int linhas = stmt.executeUpdate();
            if (linhas == 0) {
                throw new RuntimeException("Não foi possível decrementar vagas para o curso id=" + idCurso +
                                           ". Curso não encontrado ou sem vagas disponíveis.");
            }
            System.out.println("[CursoRepository] Vaga decrementada para curso id=" + idCurso);

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao decrementar vaga: " + e.getMessage(), e);
        }
    }

    // ------------------------------------------------------------------ DELETAR
    public void deletar(int id) {
        String sql = "DELETE FROM curso WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int linhas = stmt.executeUpdate();
            if (linhas == 0) {
                throw new RuntimeException("Curso com id=" + id + " não encontrado para exclusão.");
            }
            System.out.println("[CursoRepository] Curso deletado, id=" + id);

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar curso: " + e.getMessage(), e);
        }
    }

    // --------------------------------------------------------------- HELPER: mapear ResultSet → Curso
    private Curso mapear(ResultSet rs) throws SQLException {
        return new Curso(
            rs.getInt("id"),
            rs.getString("nome"),
            rs.getString("descricao"),
            rs.getInt("carga_horaria"),
            rs.getInt("vagas_totais"),
            rs.getInt("vagas_disponiveis")
        );
    }
}
