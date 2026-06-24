package br.com.escola.model;

/**
 * Entidade: Aluno
 * Representa um aluno cadastrado na escola de cursos livres.
 */
public class Aluno {

    private int    id;
    private String nome;
    private String email;
    private String telefone;

    // ---- Construtores ----

    public Aluno() {}

    /** Usado para criar novo aluno (sem id — banco gera via SERIAL) */
    public Aluno(String nome, String email, String telefone) {
        this.nome     = nome;
        this.email    = email;
        this.telefone = telefone;
    }

    /** Usado ao recuperar do banco (com id) */
    public Aluno(int id, String nome, String email, String telefone) {
        this.id       = id;
        this.nome     = nome;
        this.email    = email;
        this.telefone = telefone;
    }

    // ---- Getters e Setters ----

    public int getId()               { return id; }
    public void setId(int id)        { this.id = id; }

    public String getNome()          { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail()             { return email; }
    public void setEmail(String email)   { this.email = email; }

    public String getTelefone()                { return telefone; }
    public void setTelefone(String telefone)   { this.telefone = telefone; }

    @Override
    public String toString() {
        return "Aluno{id=" + id + ", nome='" + nome + "', email='" + email +
               "', telefone='" + telefone + "'}";
    }
}
