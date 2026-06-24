package br.com.escola.controller;

import br.com.escola.model.Matricula;
import br.com.escola.service.MatriculaService;

import java.util.List;

/**
 * Controller de Matrícula — recebe a ação, chama o service e exibe/retorna o resultado.
 */
public class MatriculaController {

    private final MatriculaService matriculaService;

    public MatriculaController() {
        this.matriculaService = new MatriculaService();
    }

    // ------------------------------------------------------------------ MATRICULAR
    public Matricula matricular(int idAluno, int idCurso, double valor) {
        System.out.println("\n>>> [MatriculaController] Realizando matrícula: " +
                           "aluno=" + idAluno + " | curso=" + idCurso + " | valor=R$" +
                           String.format("%.2f", valor));
        Matricula m = matriculaService.matricular(idAluno, idCurso, valor);
        System.out.println("    Resultado: " + m);
        return m;
    }

    // ------------------------------------------------------------------ LISTAR CURSOS DE UM ALUNO
    public List<Matricula> listarCursosPorAluno(int idAluno) {
        System.out.println("\n>>> [MatriculaController] Cursos do aluno id=" + idAluno);
        List<Matricula> lista = matriculaService.listarCursosPorAluno(idAluno);
        if (lista.isEmpty()) {
            System.out.println("    Nenhuma matrícula encontrada.");
        } else {
            lista.forEach(m -> System.out.println("    " + m));
        }
        return lista;
    }

    // ------------------------------------------------------------------ LISTAR ALUNOS DE UM CURSO
    public List<Matricula> listarAlunosPorCurso(int idCurso) {
        System.out.println("\n>>> [MatriculaController] Alunos do curso id=" + idCurso);
        List<Matricula> lista = matriculaService.listarAlunosPorCurso(idCurso);
        if (lista.isEmpty()) {
            System.out.println("    Nenhum aluno matriculado.");
        } else {
            lista.forEach(m -> System.out.println("    " + m));
        }
        return lista;
    }

    // ------------------------------------------------------------------ LISTAR TODOS
    public List<Matricula> listarTodos() {
        System.out.println("\n>>> [MatriculaController] Listando todas as matrículas");
        List<Matricula> lista = matriculaService.listarTodos();
        lista.forEach(m -> System.out.println("    " + m));
        return lista;
    }

    // ------------------------------------------------------------------ CANCELAR
    public void cancelar(int id) {
        System.out.println("\n>>> [MatriculaController] Cancelando matrícula id=" + id);
        matriculaService.cancelar(id);
        System.out.println("    Matrícula id=" + id + " cancelada.");
    }
}
