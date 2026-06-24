package br.com.escola;

import br.com.escola.controller.AlunoController;
import br.com.escola.controller.CursoController;
import br.com.escola.controller.MatriculaController;
import br.com.escola.model.Aluno;
import br.com.escola.model.Curso;
import br.com.escola.model.Matricula;
import java.util.List;


public class Main {

    public static void main(String[] args) {

        AlunoController     alunoCtrl     = new AlunoController();
        CursoController     cursoCtrl     = new CursoController();
        MatriculaController matriculaCtrl = new MatriculaController();

        separador("CENÁRIO 3 — SISTEMA DE ESCOLA DE CURSOS LIVRES");


        // ETAPA 1 — Cadastro de Alunos
        separador("ETAPA 1: Cadastrando Alunos");

        Aluno aluno1 = alunoCtrl.cadastrar(
            "Carlos Craft",
            "Carlos.alemanha@email.com",
            "(11) 91234-5678"
        );
        Aluno aluno2 = alunoCtrl.cadastrar(
            "Neymar Santos",
            "Neymar.santos@email.com",
            "(11) 99876-5432"
        );
        Aluno aluno3 = alunoCtrl.cadastrar(
            "Ana Conda",
            "ana.conda@email.com",
            "(21) 98765-4321"
        );


        separador("ETAPA 2: Cadastrando Cursos");

        Curso cursoJava = cursoCtrl.cadastrar(
            "Java do Zero ao Avançado",
            "Curso completo de programação Java com foco em orientação a objetos.",
            80,
            2   // apenas 2 vagas para demonstrar o limite
        );
        Curso cursoSQL = cursoCtrl.cadastrar(
            "SQL e Banco de Dados Relacionais",
            "Fundamentos de SQL com PostgreSQL: DDL, DML e consultas avançadas.",
            40,
            30
        );


        separador("ETAPA 3: Realizando Matrículas (Movimento Principal)");

        // Matrícula válida: Maria no curso Java
        Matricula m1 = matriculaCtrl.matricular(aluno1.getId(), cursoJava.getId(), 499.90);

        // Matrícula válida: João no curso Java (2ª e última vaga)
        Matricula m2 = matriculaCtrl.matricular(aluno2.getId(), cursoJava.getId(), 499.90);

        // Matrícula válida: Maria no curso SQL
        Matricula m3 = matriculaCtrl.matricular(aluno1.getId(), cursoSQL.getId(), 299.90);


        // ETAPA 4 — Consultas de Histórico

        separador("ETAPA 4: Consultas de Histórico");

        // Cursos em que Maria está matriculada
        System.out.println("\n--- Todos os cursos da aluna Maria (id=" + aluno1.getId() + ") ---");
        matriculaCtrl.listarCursosPorAluno(aluno1.getId());

        // Alunos matriculados no curso Java
        System.out.println("\n--- Todos os alunos do curso Java (id=" + cursoJava.getId() + ") ---");
        matriculaCtrl.listarAlunosPorCurso(cursoJava.getId());

        // Verificar vagas atuais do curso Java
        System.out.println("\n--- Vagas atuais do curso Java ---");
        cursoCtrl.buscarPorId(cursoJava.getId());


        // ETAPA 5 — Tentativas Inválidas

        separador("ETAPA 5: Demonstrando Regras de Negócio (Tentativas Inválidas)");

        // RN03 — Curso sem vagas disponíveis
        System.out.println("\n[TESTE RN03] Tentando matricular Ana em curso sem vagas...");
        try {
            matriculaCtrl.matricular(aluno3.getId(), cursoJava.getId(), 499.90);
        } catch (IllegalStateException e) {
            System.out.println("    BLOQUEADO corretamente: " + e.getMessage());
        }

        // RN04 — Matrícula duplicada
        System.out.println("\n[TESTE RN04] Tentando matricular Maria duas vezes no mesmo curso...");
        try {
            matriculaCtrl.matricular(aluno1.getId(), cursoSQL.getId(), 299.90);
        } catch (IllegalStateException e) {
            System.out.println("    BLOQUEADO corretamente: " + e.getMessage());
        }

        // RN05 — Valor negativo
        System.out.println("\n[TESTE RN05] Tentando matricular com valor negativo...");
        try {
            matriculaCtrl.matricular(aluno3.getId(), cursoSQL.getId(), -100.00);
        } catch (IllegalArgumentException e) {
            System.out.println("    BLOQUEADO corretamente: " + e.getMessage());
        }

        // RN01 — Aluno inexistente
        System.out.println("\n[TESTE RN01] Tentando matricular aluno que não existe (id=9999)...");
        try {
            matriculaCtrl.matricular(9999, cursoSQL.getId(), 299.90);
        } catch (IllegalArgumentException e) {
            System.out.println("    BLOQUEADO corretamente: " + e.getMessage());
        }

        // RN02 — Curso inexistente
        System.out.println("\n[TESTE RN02] Tentando matricular em curso que não existe (id=9999)...");
        try {
            matriculaCtrl.matricular(aluno3.getId(), 9999, 299.90);
        } catch (IllegalArgumentException e) {
            System.out.println("    BLOQUEADO corretamente: " + e.getMessage());
        }


        // RESUMO FINAL
        separador("RESUMO FINAL — Listagem Geral");

        System.out.println("\n=== ALUNOS CADASTRADOS ===");
        alunoCtrl.listarTodos();

        System.out.println("\n=== CURSOS CADASTRADOS ===");
        cursoCtrl.listarTodos();

        System.out.println("\n=== MATRÍCULAS REALIZADAS ===");
        matriculaCtrl.listarTodos();

        separador("FIM DA SIMULAÇÃO");
    }

    // ------------------------------------------------------------------ HELPER
    private static void separador(String titulo) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("  " + titulo);
        System.out.println("=".repeat(60));
    }
}
