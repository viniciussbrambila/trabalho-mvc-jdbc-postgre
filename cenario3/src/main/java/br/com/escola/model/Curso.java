package br.com.escola.model;

/**
 * Entidade: Curso
 * Representa um curso oferecido pela escola de cursos livres.
 */
public class Curso {

    private int    id;
    private String nome;
    private String descricao;
    private int    cargaHoraria;
    private int    vagasTotais;
    private int    vagasDisponiveis;

    // ---- Construtores ----

    public Curso() {}

    /** Usado para criar novo curso (sem id — banco gera via SERIAL) */
    public Curso(String nome, String descricao, int cargaHoraria, int vagasTotais) {
        this.nome             = nome;
        this.descricao        = descricao;
        this.cargaHoraria     = cargaHoraria;
        this.vagasTotais      = vagasTotais;
        this.vagasDisponiveis = vagasTotais; // ao criar, todas as vagas estão disponíveis
    }

    /** Usado ao recuperar do banco (com id) */
    public Curso(int id, String nome, String descricao, int cargaHoraria,
                 int vagasTotais, int vagasDisponiveis) {
        this.id               = id;
        this.nome             = nome;
        this.descricao        = descricao;
        this.cargaHoraria     = cargaHoraria;
        this.vagasTotais      = vagasTotais;
        this.vagasDisponiveis = vagasDisponiveis;
    }

    // ---- Getters e Setters ----

    public int getId()               { return id; }
    public void setId(int id)        { this.id = id; }

    public String getNome()          { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao()                 { return descricao; }
    public void setDescricao(String descricao)   { this.descricao = descricao; }

    public int getCargaHoraria()                     { return cargaHoraria; }
    public void setCargaHoraria(int cargaHoraria)    { this.cargaHoraria = cargaHoraria; }

    public int getVagasTotais()                  { return vagasTotais; }
    public void setVagasTotais(int vagasTotais)  { this.vagasTotais = vagasTotais; }

    public int getVagasDisponiveis()                     { return vagasDisponiveis; }
    public void setVagasDisponiveis(int vagasDisponiveis){ this.vagasDisponiveis = vagasDisponiveis; }

    public boolean temVaga() {
        return vagasDisponiveis > 0;
    }

    @Override
    public String toString() {
        return "Curso{id=" + id + ", nome='" + nome + "', cargaHoraria=" + cargaHoraria +
               "h, vagasTotais=" + vagasTotais + ", vagasDisponiveis=" + vagasDisponiveis + "}";
    }
}
