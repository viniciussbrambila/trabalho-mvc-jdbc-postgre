package br.edu.umfg.model;

public class OrdemServico {
    private int    id;
    private int    idVeiculo;
    private String descricao;
    private double valor;
    private String status; // "ABERTA" ou "CONCLUIDA"

    public OrdemServico() {}

    public OrdemServico(int idVeiculo, String descricao, double valor) {
        this.idVeiculo = idVeiculo;
        this.descricao = descricao;
        this.valor     = valor;
        this.status    = "ABERTA"; // toda ordem nasce aberta
    }

    public OrdemServico(int id, int idVeiculo, String descricao, double valor, String status) {
        this.id        = id;
        this.idVeiculo = idVeiculo;
        this.descricao = descricao;
        this.valor     = valor;
        this.status    = status;
    }

    public int    getId()                       { return id; }
    public void   setId(int id)                 { this.id = id; }
    public int    getIdVeiculo()                { return idVeiculo; }
    public void   setIdVeiculo(int idVeiculo)   { this.idVeiculo = idVeiculo; }
    public String getDescricao()                { return descricao; }
    public void   setDescricao(String descricao){ this.descricao = descricao; }
    public double getValor()                    { return valor; }
    public void   setValor(double valor)        { this.valor = valor; }
    public String getStatus()                   { return status; }
    public void   setStatus(String status)      { this.status = status; }

    @Override
    public String toString() {
        return "OrdemServico{id=" + id + ", idVeiculo=" + idVeiculo +
                ", descricao='" + descricao + "', valor=" + valor +
                ", status='" + status + "'}";
    }
}