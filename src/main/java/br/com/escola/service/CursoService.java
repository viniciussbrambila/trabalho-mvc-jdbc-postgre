package br.com.escola.service;

import br.com.escola.model.Curso;
import br.com.escola.repository.CursoRepository;

import java.util.List;

/**
 * Service de Curso — orquestra as regras de negócio e validações.
 */
public class CursoService {

    private final CursoRepository cursoRepository;

    public CursoService() {
        this.cursoRepository = new CursoRepository();
    }

    // ------------------------------------------------------------------ CADASTRAR
    public Curso cadastrar(String nome, String descricao, int cargaHoraria, int vagasTotais) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do curso é obrigatório.");
        }
        if (cargaHoraria <= 0) {
            throw new IllegalArgumentException("Carga horária deve ser maior que zero.");
        }
        if (vagasTotais <= 0) {
            throw new IllegalArgumentException("Número de vagas deve ser maior que zero.");
        }

        Curso curso = new Curso(nome.trim(), descricao, cargaHoraria, vagasTotais);
        return cursoRepository.salvar(curso);
    }

    // ------------------------------------------------------------------ BUSCAR
    public Curso buscarPorId(int id) {
        return cursoRepository.buscarPorId(id)
            .orElseThrow(() -> new RuntimeException("Curso não encontrado para id=" + id));
    }

    // ------------------------------------------------------------------ LISTAR
    public List<Curso> listarTodos() {
        return cursoRepository.listarTodos();
    }

    // ------------------------------------------------------------------ ATUALIZAR
    public void atualizar(int id, String nome, String descricao, int cargaHoraria, int vagasTotais) {
        Curso curso = buscarPorId(id);

        if (nome != null && !nome.isBlank())       curso.setNome(nome.trim());
        if (descricao != null)                     curso.setDescricao(descricao);
        if (cargaHoraria > 0)                      curso.setCargaHoraria(cargaHoraria);
        if (vagasTotais > 0)                       curso.setVagasTotais(vagasTotais);

        cursoRepository.atualizar(curso);
    }

    // ------------------------------------------------------------------ DELETAR
    public void deletar(int id) {
        buscarPorId(id);
        cursoRepository.deletar(id);
    }
}
