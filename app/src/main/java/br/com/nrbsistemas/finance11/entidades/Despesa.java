package br.com.nrbsistemas.finance11.entidades;

/**
 * Created by Everton on 28/05/2017.
 */

public class Despesa {
    private long id;
    private String descrisao;
    private String categoria;
    private Double valor;
    private Double imposto;
    private String data;

    public Despesa() {
    }

    public Despesa(long id, String descrisao, String categoria, Double valor, Double imposto, String data) {
        this.id = id;
        this.descrisao = descrisao;
        this.categoria = categoria;
        this.valor = valor;
        this.imposto = imposto;
        this.data = data;
    }

    public Despesa(String descrisao, String categoria, Double valor, String data) {
        this.descrisao = descrisao;
        this.categoria = categoria;
        this.valor = valor;
        this.data = data;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescrisao() {
        return descrisao;
    }

    public void setDescrisao(String descrisao) {
        this.descrisao = descrisao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Double getImposto() {
        return imposto;
    }

    public void setImposto(Double imposto) {
        this.imposto = imposto;
    }
}
