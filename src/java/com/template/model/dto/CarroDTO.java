package com.template.model.dto;

import java.util.Objects;

/**
 * Data Transfer Object (DTO) representando a entidade Carro.
 */
public class CarroDTO {
    private int id;
    private String nome;
    private int pt_cavalos;
    private String cor;

    public CarroDTO() {
    }

    public CarroDTO(String nome, int pt_cavalos, String cor) {
        this.nome = nome;
        this.pt_cavalos = pt_cavalos;
        this.cor = cor;
    }

    public CarroDTO(int id, String nome, int pt_cavalos, String cor) {
        this.id = id;
        this.nome = nome;
        this.pt_cavalos = pt_cavalos;
        this.cor = cor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarroDTO carroDTO = (CarroDTO) o;
        return id == carroDTO.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "CarroDTO{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", pt_cavalos=" + pt_cavalos +
                ", cor='" + cor + '\'' +
                '}';
    }
}
