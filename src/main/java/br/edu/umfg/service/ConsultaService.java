package br.edu.umfg.service;

import br.edu.umfg.model.Consulta;
import br.edu.umfg.repository.AnimalRepository;
import br.edu.umfg.repository.ConsultaRepository;

import java.util.List;

public class ConsultaService {

    private ConsultaRepository consultaRepository = new ConsultaRepository();
    private AnimalRepository animalRepository = new AnimalRepository();

    public void cadastrar(Consulta consulta) {
        if (animalRepository.buscarPorId(consulta.getIdAnimal()) == null) {
            throw new RuntimeException("Animal não encontrado!");
        }
        if (consulta.getValor() < 0) {
            throw new RuntimeException("Valor da consulta não pode ser negativo!");
        }
        if (consulta.getMotivo() == null || consulta.getMotivo().isBlank()) {
            throw new RuntimeException("Motivo da consulta não pode ser vazio!");
        }
        consultaRepository.salvar(consulta);
    }

    public List<Consulta> listarTodos() {
        return consultaRepository.listarTodos();
    }

    public Consulta buscarPorId(int id) {
        Consulta consulta = consultaRepository.buscarPorId(id);
        if (consulta == null) {
            throw new RuntimeException("Consulta não encontrada!");
        }
        return consulta;
    }

    public List<Consulta> listarPorAnimal(int idAnimal) {
        return consultaRepository.listarPorAnimal(idAnimal);
    }

    public void atualizar(Consulta consulta) {
        if (consulta.getValor() < 0) {
            throw new RuntimeException("Valor da consulta não pode ser negativo!");
        }
        consultaRepository.atualizar(consulta);
    }

    public void deletar(int id) {
        consultaRepository.deletar(id);
    }
}