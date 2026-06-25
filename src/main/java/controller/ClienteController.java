package controller;
import model.Cliente;
import service.ClienteService;

import java.sql.SQLException;
import java.util.List;

public class ClienteController {

    private ClienteService service = new ClienteService();

    public Cliente cadastrar(Cliente cliente) {
        try {
            Cliente salvo = service.cadastrar(cliente);
            System.out.println("[OK] Cliente cadastrado: " + salvo);
            return salvo;
        } catch (IllegalArgumentException e) {
            System.out.println("[REGRA] " + e.getMessage());
            return null;
        } catch (SQLException e) {
            System.out.println("[ERRO BD] " + e.getMessage());
            return null;
        }
    }

    public Cliente buscarPorId(int id) {
        try {
            Cliente c = service.buscarPorId(id);
            if (c != null) System.out.println("[OK] " + c);
            else           System.out.println("[AVISO] Cliente id " + id + " não encontrado.");
            return c;
        } catch (SQLException e) {
            System.out.println("[ERRO BD] " + e.getMessage());
            return null;
        }
    }

    public List<Cliente> listarTodos() {
        try {
            List<Cliente> lista = service.listarTodos();
            System.out.println("=== Clientes cadastrados ===");
            lista.forEach(System.out::println);
            return lista;
        } catch (SQLException e) {
            System.out.println("[ERRO BD] " + e.getMessage());
            return null;
        }
    }

    public void atualizar(Cliente cliente) {
        try {
            service.atualizar(cliente);
            System.out.println("[OK] Cliente atualizado.");
        } catch (SQLException e) {
            System.out.println("[ERRO BD] " + e.getMessage());
        }
    }

    public void deletar(int id) {
        try {
            service.deletar(id);
            System.out.println("[OK] Cliente deletado.");
        } catch (SQLException e) {
            System.out.println("[ERRO BD] " + e.getMessage());
        }
    }
}