package com.template;

public class CarroDTO {
    private int id;
    private String nome;
    private int pt_cavalos;
    private String cor;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() { return nome; }

    public void setNome(String nome) {this.nome = nome;}

    public int getPt_cavalos() {
        return pt_cavalos;
    }

    public void setPt_cavalos(int pt_cavalos) {
        this.pt_cavalos = pt_cavalos;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }
}
