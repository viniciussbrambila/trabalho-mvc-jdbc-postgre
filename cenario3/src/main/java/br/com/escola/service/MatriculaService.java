package br.com.escola.service;

import br.com.escola.model.Aluno;
import br.com.escola.model.Curso;
import br.com.escola.model.Matricula;
import br.com.escola.repository.AlunoRepository;
import br.com.escola.repository.CursoRepository;
import br.com.escola.repository.MatriculaRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Service de Matrícula — orquestra TODAS as regras de negócio do cenário 3.
 *
 * Regras implementadas aqui:
 *  RN01 — Não é possível matricular aluno que não esteja cadastrado.
 *  RN02 — Não é possível matricular em curso que não esteja cadastrado.
 *  RN03 — Não é possível matricular aluno em curso que já atingiu o limite de vagas.
 *  RN04 — O mesmo aluno não pode ser matriculado duas vezes no mesmo curso.
 *  RN05 — O valor pago na matrícula não pode ser negativo.
 */
public class MatriculaService {

    private final MatriculaRepository matriculaRepository;
    private final AlunoRepository     alunoRepository;
    private final CursoRepository     cursoRepository;

    public MatriculaService() {
        this.matriculaRepository = new MatriculaRepository();
        this.alunoRepository     = new AlunoRepository();
        this.cursoRepository     = new CursoRepository();
    }

    // ------------------------------------------------------------------ MATRICULAR (MOVIMENTO PRINCIPAL)
    public Matricula matricular(int idAluno, int idCurso, double valor) {

        // RN01 — aluno deve existir
        Aluno aluno = alunoRepository.buscarPorId(idAluno)
            .orElseThrow(() -> new IllegalArgumentException(
                "[RN01] Não é possível matricular: aluno com id=" + idAluno + " não está cadastrado."));

        // RN02 — curso deve existir
        Curso curso = cursoRepository.buscarPorId(idCurso)
            .orElseThrow(() -> new IllegalArgumentException(
                "[RN02] Não é possível matricular: curso com id=" + idCurso + " não está cadastrado."));

        // RN03 — curso deve ter vagas disponíveis
        if (!curso.temVaga()) {
            throw new IllegalStateException(
                "[RN03] Matrícula recusada: o curso '" + curso.getNome() + "' não possui vagas disponíveis.");
        }

        // RN04 — aluno não pode estar já matriculado no mesmo curso
        if (matriculaRepository.existeMatricula(idAluno, idCurso)) {
            throw new IllegalStateException(
                "[RN04] Matrícula recusada: o aluno '" + aluno.getNome() +
                "' já está matriculado no curso '" + curso.getNome() + "'.");
        }

        // RN05 — valor não pode ser negativo
        if (valor < 0) {
            throw new IllegalArgumentException(
                "[RN05] Matrícula recusada: o valor pago não pode ser negativo. Valor informado: R$" +
                String.format("%.2f", valor));
        }

        // Tudo validado → persiste a matrícula
        Matricula matricula = new Matricula(idAluno, idCurso, LocalDate.now(), valor);
        matriculaRepository.salvar(matricula);

        // Decrementa a vaga disponível no curso
        cursoRepository.decrementarVaga(idCurso);

        // Enriquece o objeto retornado com os dados completos (para exibição)
        matricula.setAluno(aluno);
        matricula.setCurso(curso);

        System.out.println("[MatriculaService] Matrícula realizada com sucesso: " + matricula);
        return matricula;
    }

    // ------------------------------------------------------------------ BUSCAR POR ID
    public Matricula buscarPorId(int id) {
        return matriculaRepository.buscarPorId(id)
            .orElseThrow(() -> new RuntimeException("Matrícula não encontrada para id=" + id));
    }

    // ------------------------------------------------------------------ LISTAR TODOS OS CURSOS DE UM ALUNO
    public List<Matricula> listarCursosPorAluno(int idAluno) {
        // RN01 — valida existência do aluno
        alunoRepository.buscarPorId(idAluno)
            .orElseThrow(() -> new RuntimeException("Aluno não encontrado para id=" + idAluno));

        return matriculaRepository.buscarPorAluno(idAluno);
    }

    // ------------------------------------------------------------------ LISTAR TODOS OS ALUNOS DE UM CURSO
    public List<Matricula> listarAlunosPorCurso(int idCurso) {
        // RN02 — valida existência do curso
        cursoRepository.buscarPorId(idCurso)
            .orElseThrow(() -> new RuntimeException("Curso não encontrado para id=" + idCurso));

        return matriculaRepository.buscarPorCurso(idCurso);
    }

    // ------------------------------------------------------------------ LISTAR TODOS
    public List<Matricula> listarTodos() {
        return matriculaRepository.listarTodos();
    }

    // ------------------------------------------------------------------ CANCELAR MATRÍCULA
    public void cancelar(int id) {
        buscarPorId(id); // valida existência
        matriculaRepository.deletar(id);
    }
}
