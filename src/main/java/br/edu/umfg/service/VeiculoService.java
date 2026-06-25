package br.edu.umfg.service;

import br.edu.umfg.model.Veiculo;
import br.edu.umfg.repository.VeiculoRepository;
import br.edu.umfg.repository.ClienteRepository;

import java.sql.SQLException;
import java.util.List;

public class VeiculoService {

    private VeiculoRepository veiculoRepository = new VeiculoRepository();
    private ClienteRepository clienteRepository = new ClienteRepository();

    public Veiculo cadastrar(Veiculo veiculo) throws SQLException {
        // Regra: cliente deve existir no banco antes de cadastrar o veículo
        if (clienteRepository.buscarPorId(veiculo.getIdCliente()) == null) {
            throw new IllegalArgumentException(
                    "Cliente com id " + veiculo.getIdCliente() + " não encontrado. Cadastre o cliente primeiro."
            );
        }
        if (veiculo.getPlaca() == null || veiculo.getPlaca().trim().isEmpty()) {
            throw new IllegalArgumentException("A placa do veículo é obrigatória.");
        }
        if (veiculo.getAno() < 1900 || veiculo.getAno() > 2100) {
            throw new IllegalArgumentException("Ano do veículo inválido.");
        }
        return veiculoRepository.inserir(veiculo);
    }

    public Veiculo buscarPorId(int id) throws SQLException {
        return veiculoRepository.buscarPorId(id);
    }

    public List<Veiculo> listarTodos() throws SQLException {
        return veiculoRepository.listarTodos();
    }

    public List<Veiculo> listarPorCliente(int idCliente) throws SQLException {
        return veiculoRepository.listarPorCliente(idCliente);
    }

    public void atualizar(Veiculo veiculo) throws SQLException {
        veiculoRepository.atualizar(veiculo);
    }

    public void deletar(int id) throws SQLException {
        veiculoRepository.deletar(id);
    }
}