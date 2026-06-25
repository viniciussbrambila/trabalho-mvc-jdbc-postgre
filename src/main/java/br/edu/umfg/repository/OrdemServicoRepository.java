package br.edu.umfg.repository;

import br.edu.umfg.model.OrdemServico;
import br.edu.umfg.util.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdemServicoRepository {

    public OrdemServico inserir(OrdemServico os) throws SQLException {
        String sql = "INSERT INTO ordem_servico (id_veiculo, descricao, valor, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, os.getIdVeiculo());
            stmt.setString(2, os.getDescricao());
            stmt.setDouble(3, os.getValor());
            stmt.setString(4, os.getStatus());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) os.setId(rs.getInt(1));
            }
            return os;
        }
    }

    public OrdemServico buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM ordem_servico WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new OrdemServico(
                            rs.getInt("id"),
                            rs.getInt("id_veiculo"),
                            rs.getString("descricao"),
                            rs.getDouble("valor"),
                            rs.getString("status")
                    );
                }
            }
        }
        return null;
    }

    public List<OrdemServico> listarTodos() throws SQLException {
        String sql = "SELECT * FROM ordem_servico";
        List<OrdemServico> lista = new ArrayList<>();
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new OrdemServico(
                        rs.getInt("id"),
                        rs.getInt("id_veiculo"),
                        rs.getString("descricao"),
                        rs.getDouble("valor"),
                        rs.getString("status")
                ));
            }
        }
        return lista;
    }

    // Requisito explícito: "histórico de manutenções de um determinado veículo"
    public List<OrdemServico> listarPorVeiculo(int idVeiculo) throws SQLException {
        String sql = "SELECT * FROM ordem_servico WHERE id_veiculo = ?";
        List<OrdemServico> lista = new ArrayList<>();
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idVeiculo);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(new OrdemServico(
                            rs.getInt("id"),
                            rs.getInt("id_veiculo"),
                            rs.getString("descricao"),
                            rs.getDouble("valor"),
                            rs.getString("status")
                    ));
                }
            }
        }
        return lista;
    }

    public void atualizar(OrdemServico os) throws SQLException {
        String sql = "UPDATE ordem_servico SET id_veiculo = ?, descricao = ?, valor = ?, status = ? WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, os.getIdVeiculo());
            stmt.setString(2, os.getDescricao());
            stmt.setDouble(3, os.getValor());
            stmt.setString(4, os.getStatus());
            stmt.setInt(5, os.getId());
            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM ordem_servico WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}