package br.com.escola.controller;

import br.com.escola.model.Aluno;
import br.com.escola.service.AlunoService;

import java.util.List;

/**
 * Controller de Aluno — recebe a ação, chama o service e exibe/retorna o resultado.
 * Em uma aplicação web seria acionado por um endpoint HTTP; aqui é chamado pela Main.
 */
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController() {
        this.alunoService = new AlunoService();
    }

    // ------------------------------------------------------------------ CADASTRAR
    public Aluno cadastrar(String nome, String email, String telefone) {
        System.out.println("\n>>> [AlunoController] Cadastrando aluno: " + nome);
        Aluno aluno = alunoService.cadastrar(nome, email, telefone);
        System.out.println("    Resultado: " + aluno);
        return aluno;
    }

    // ------------------------------------------------------------------ BUSCAR
    public Aluno buscarPorId(int id) {
        System.out.println("\n>>> [AlunoController] Buscando aluno id=" + id);
        Aluno aluno = alunoService.buscarPorId(id);
        System.out.println("    Resultado: " + aluno);
        return aluno;
    }

    // ------------------------------------------------------------------ LISTAR
    public List<Aluno> listarTodos() {
        System.out.println("\n>>> [AlunoController] Listando todos os alunos");
        List<Aluno> lista = alunoService.listarTodos();
        lista.forEach(a -> System.out.println("    " + a));
        return lista;
    }

    // ------------------------------------------------------------------ ATUALIZAR
    public void atualizar(int id, String nome, String email, String telefone) {
        System.out.println("\n>>> [AlunoController] Atualizando aluno id=" + id);
        alunoService.atualizar(id, nome, email, telefone);
        System.out.println("    Aluno id=" + id + " atualizado com sucesso.");
    }

    // ------------------------------------------------------------------ DELETAR
    public void deletar(int id) {
        System.out.println("\n>>> [AlunoController] Deletando aluno id=" + id);
        alunoService.deletar(id);
        System.out.println("    Aluno id=" + id + " deletado com sucesso.");
    }
}
