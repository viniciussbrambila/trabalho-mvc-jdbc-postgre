package service;

import model.Cliente;
import repository.ClienteRepository;

import java.sql.SQLException;
import java.util.List;

public class ClienteService {

    private ClienteRepository clienteRepository = new ClienteRepository();

    public Cliente cadastrar(Cliente cliente) throws SQLException {
        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do cliente é obrigatório.");
        }
        if (cliente.getTelefone() == null || cliente.getTelefone().trim().length() < 8) {
            throw new IllegalArgumentException("Telefone inválido.");
        }
        return clienteRepository.inserir(cliente);
    }

    public Cliente buscarPorId(int id) throws SQLException {
        return clienteRepository.buscarPorId(id);
    }

    public List<Cliente> listarTodos() throws SQLException {
        return clienteRepository.listarTodos();
    }

    public void atualizar(Cliente cliente) throws SQLException {
        clienteRepository.atualizar(cliente);
    }

    public void deletar(int id) throws SQLException {
        clienteRepository.deletar(id);
    }
}