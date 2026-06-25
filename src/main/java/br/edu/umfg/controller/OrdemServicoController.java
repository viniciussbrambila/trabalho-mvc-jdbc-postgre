package br.edu.umfg.controller;

import br.edu.umfg.model.OrdemServico;
import br.edu.umfg.service.OrdemServicoService;

import java.sql.SQLException;
import java.util.List;

public class OrdemServicoController {

    private OrdemServicoService service = new OrdemServicoService();

    public OrdemServico abrir(OrdemServico os) {
        try {
            OrdemServico salva = service.abrir(os);
            System.out.println("[OK] Ordem de serviço aberta: " + salva);
            return salva;
        } catch (IllegalArgumentException e) {
            System.out.println("[REGRA] " + e.getMessage());
            return null;
        } catch (SQLException e) {
            System.out.println("[ERRO BD] " + e.getMessage());
            return null;
        }
    }

    public OrdemServico buscarPorId(int id) {
        try {
            OrdemServico os = service.buscarPorId(id);
            if (os != null) System.out.println("[OK] " + os);
            else            System.out.println("[AVISO] OS id " + id + " não encontrada.");
            return os;
        } catch (SQLException e) {
            System.out.println("[ERRO BD] " + e.getMessage());
            return null;
        }
    }

    public List<OrdemServico> listarPorVeiculo(int idVeiculo) {
        try {
            List<OrdemServico> lista = service.listarPorVeiculo(idVeiculo);
            System.out.println("=== Histórico de Manutenções do Veículo ID " + idVeiculo + " ===");
            lista.forEach(System.out::println);
            return lista;
        } catch (SQLException e) {
            System.out.println("[ERRO BD] " + e.getMessage());
            return null;
        }
    }

    public List<OrdemServico> listarTodos() {
        try {
            List<OrdemServico> lista = service.listarTodos();
            System.out.println("=== Todas as Ordens de Serviço ===");
            lista.forEach(System.out::println);
            return lista;
        } catch (SQLException e) {
            System.out.println("[ERRO BD] " + e.getMessage());
            return null;
        }
    }

    public void concluir(int id) {
        try {
            service.concluir(id);
        } catch (IllegalArgumentException e) {
            System.out.println("[REGRA] " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("[ERRO BD] " + e.getMessage());
        }
    }

    public void atualizar(OrdemServico os) {
        try {
            service.atualizar(os);
            System.out.println("[OK] OS atualizada.");
        } catch (SQLException e) {
            System.out.println("[ERRO BD] " + e.getMessage());
        }
    }

    public void deletar(int id) {
        try {
            service.deletar(id);
            System.out.println("[OK] OS deletada.");
        } catch (SQLException e) {
            System.out.println("[ERRO BD] " + e.getMessage());
        }
    }
}