package br.com.escola.controller;

import br.com.escola.model.Curso;
import br.com.escola.service.CursoService;

import java.util.List;

/**
 * Controller de Curso — recebe a ação, chama o service e exibe/retorna o resultado.
 */
public class CursoController {

    private final CursoService cursoService;

    public CursoController() {
        this.cursoService = new CursoService();
    }

    // ------------------------------------------------------------------ CADASTRAR
    public Curso cadastrar(String nome, String descricao, int cargaHoraria, int vagasTotais) {
        System.out.println("\n>>> [CursoController] Cadastrando curso: " + nome);
        Curso curso = cursoService.cadastrar(nome, descricao, cargaHoraria, vagasTotais);
        System.out.println("    Resultado: " + curso);
        return curso;
    }

    // ------------------------------------------------------------------ BUSCAR
    public Curso buscarPorId(int id) {
        System.out.println("\n>>> [CursoController] Buscando curso id=" + id);
        Curso curso = cursoService.buscarPorId(id);
        System.out.println("    Resultado: " + curso);
        return curso;
    }

    // ------------------------------------------------------------------ LISTAR
    public List<Curso> listarTodos() {
        System.out.println("\n>>> [CursoController] Listando todos os cursos");
        List<Curso> lista = cursoService.listarTodos();
        lista.forEach(c -> System.out.println("    " + c));
        return lista;
    }

    // ------------------------------------------------------------------ ATUALIZAR
    public void atualizar(int id, String nome, String descricao, int cargaHoraria, int vagasTotais) {
        System.out.println("\n>>> [CursoController] Atualizando curso id=" + id);
        cursoService.atualizar(id, nome, descricao, cargaHoraria, vagasTotais);
        System.out.println("    Curso id=" + id + " atualizado com sucesso.");
    }

    // ------------------------------------------------------------------ DELETAR
    public void deletar(int id) {
        System.out.println("\n>>> [CursoController] Deletando curso id=" + id);
        cursoService.deletar(id);
        System.out.println("    Curso id=" + id + " deletado com sucesso.");
    }
}
