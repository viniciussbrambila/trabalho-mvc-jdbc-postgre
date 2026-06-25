package br.edu.umfg.controller;

import br.edu.umfg.model.Veiculo;
import br.edu.umfg.service.VeiculoService;
import java.sql.SQLException;
import java.util.List;

public class VeiculoController {

    private VeiculoService service = new VeiculoService();

    public Veiculo cadastrar(Veiculo veiculo) {
        try {
            Veiculo salvo = service.cadastrar(veiculo);
            System.out.println("[OK] Veículo cadastrado: " + salvo);
            return salvo;
        } catch (IllegalArgumentException e) {
            System.out.println("[REGRA] " + e.getMessage());
            return null;
        } catch (SQLException e) {
            System.out.println("[ERRO BD] " + e.getMessage());
            return null;
        }
    }

    public Veiculo buscarPorId(int id) {
        try {
            Veiculo v = service.buscarPorId(id);
            if (v != null) System.out.println("[OK] " + v);
            else           System.out.println("[AVISO] Veículo id " + id + " não encontrado.");
            return v;
        } catch (SQLException e) {
            System.out.println("[ERRO BD] " + e.getMessage());
            return null;
        }
    }

    public List<Veiculo> listarTodos() {
        try {
            List<Veiculo> lista = service.listarTodos();
            System.out.println("=== Veículos cadastrados ===");
            lista.forEach(System.out::println);
            return lista;
        } catch (SQLException e) {
            System.out.println("[ERRO BD] " + e.getMessage());
            return null;
        }
    }

    public List<Veiculo> listarPorCliente(int idCliente) {
        try {
            List<Veiculo> lista = service.listarPorCliente(idCliente);
            System.out.println("=== Veículos do Cliente ID " + idCliente + " ===");
            lista.forEach(System.out::println);
            return lista;
        } catch (SQLException e) {
            System.out.println("[ERRO BD] " + e.getMessage());
            return null;
        }
    }

    public void atualizar(Veiculo veiculo) {
        try {
            service.atualizar(veiculo);
            System.out.println("[OK] Veículo atualizado.");
        } catch (SQLException e) {
            System.out.println("[ERRO BD] " + e.getMessage());
        }
    }

    public void deletar(int id) {
        try {
            service.deletar(id);
            System.out.println("[OK] Veículo deletado.");
        } catch (SQLException e) {
            System.out.println("[ERRO BD] " + e.getMessage());
        }
    }
}