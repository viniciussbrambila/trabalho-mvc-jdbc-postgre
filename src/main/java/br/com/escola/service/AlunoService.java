package br.com.escola.service;

import br.com.escola.model.Aluno;
import br.com.escola.repository.AlunoRepository;

import java.util.List;

/**
 * Service de Aluno — orquestra as regras de negócio e validações.
 * Chama o repositório somente após validar os dados.
 */
public class AlunoService {

    private final AlunoRepository alunoRepository;

    public AlunoService() {
        this.alunoRepository = new AlunoRepository();
    }

    // ------------------------------------------------------------------ CADASTRAR
    public Aluno cadastrar(String nome, String email, String telefone) {
        // Regras de negócio: campos obrigatórios
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do aluno é obrigatório.");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("E-mail do aluno é obrigatório.");
        }
        if (telefone == null || telefone.isBlank()) {
            throw new IllegalArgumentException("Telefone do aluno é obrigatório.");
        }

        Aluno aluno = new Aluno(nome.trim(), email.trim(), telefone.trim());
        return alunoRepository.salvar(aluno);
    }

    // ------------------------------------------------------------------ BUSCAR
    public Aluno buscarPorId(int id) {
        return alunoRepository.buscarPorId(id)
            .orElseThrow(() -> new RuntimeException("Aluno não encontrado para id=" + id));
    }

    // ------------------------------------------------------------------ LISTAR
    public List<Aluno> listarTodos() {
        return alunoRepository.listarTodos();
    }

    // ------------------------------------------------------------------ ATUALIZAR
    public void atualizar(int id, String nome, String email, String telefone) {
        Aluno aluno = buscarPorId(id); // valida existência

        if (nome != null && !nome.isBlank())         aluno.setNome(nome.trim());
        if (email != null && !email.isBlank())       aluno.setEmail(email.trim());
        if (telefone != null && !telefone.isBlank()) aluno.setTelefone(telefone.trim());

        alunoRepository.atualizar(aluno);
    }

    // ------------------------------------------------------------------ DELETAR
    public void deletar(int id) {
        buscarPorId(id); // valida existência antes de deletar
        alunoRepository.deletar(id);
    }
}
