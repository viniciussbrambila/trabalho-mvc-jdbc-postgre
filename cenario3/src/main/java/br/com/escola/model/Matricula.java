package br.com.escola.model;

import java.time.LocalDate;

/**
 * Entidade: Matricula — o MOVIMENTO do sistema.
 * Conecta um Aluno a um Curso.
 * Só pode existir se aluno E curso já estiverem cadastrados.
 */
public class Matricula {

    private int       id;
    private int       idAluno;
    private int       idCurso;
    private LocalDate dataMatricula;
    private double    valor;

    // Objetos auxiliares para exibição (não persistidos diretamente)
    private Aluno aluno;
    private Curso curso;

    // ---- Construtores ----

    public Matricula() {}

    /** Usado para criar nova matrícula (sem id) */
    public Matricula(int idAluno, int idCurso, LocalDate dataMatricula, double valor) {
        this.idAluno       = idAluno;
        this.idCurso       = idCurso;
        this.dataMatricula = dataMatricula;
        this.valor         = valor;
    }

    /** Usado ao recuperar do banco (com id) */
    public Matricula(int id, int idAluno, int idCurso, LocalDate dataMatricula, double valor) {
        this.id            = id;
        this.idAluno       = idAluno;
        this.idCurso       = idCurso;
        this.dataMatricula = dataMatricula;
        this.valor         = valor;
    }

    // ---- Getters e Setters ----

    public int getId()                   { return id; }
    public void setId(int id)            { this.id = id; }

    public int getIdAluno()              { return idAluno; }
    public void setIdAluno(int idAluno)  { this.idAluno = idAluno; }

    public int getIdCurso()              { return idCurso; }
    public void setIdCurso(int idCurso)  { this.idCurso = idCurso; }

    public LocalDate getDataMatricula()                    { return dataMatricula; }
    public void setDataMatricula(LocalDate dataMatricula)  { this.dataMatricula = dataMatricula; }

    public double getValor()             { return valor; }
    public void setValor(double valor)   { this.valor = valor; }

    public Aluno getAluno()              { return aluno; }
    public void setAluno(Aluno aluno)    { this.aluno = aluno; }

    public Curso getCurso()              { return curso; }
    public void setCurso(Curso curso)    { this.curso = curso; }

    @Override
    public String toString() {
        String nomeAluno = (aluno != null) ? aluno.getNome() : "idAluno=" + idAluno;
        String nomeCurso = (curso != null) ? curso.getNome() : "idCurso=" + idCurso;
        return "Matricula{id=" + id + ", aluno='" + nomeAluno + "', curso='" + nomeCurso +
               "', data=" + dataMatricula + ", valor=R$" + String.format("%.2f", valor) + "}";
    }
}
