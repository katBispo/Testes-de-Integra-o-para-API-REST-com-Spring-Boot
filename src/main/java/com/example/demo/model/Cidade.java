package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "cidade")
public class Cidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    @NotNull(message = "O nome da cidade é obrigatório")
    @Size(max = 30, message = "O nome da cidade deve ter no máximo 30 caracteres")
    @Column(nullable = false, length = 30)
    private String nome;

    @NotNull(message = "A UF é obrigatória")
    @Size(max = 30, message = "A UF deve ter no máximo 30 caracteres")
    @Column(nullable = false, length = 30)
    private String uf;

    @NotNull(message = "A taxa é obrigatória")
    @PositiveOrZero(message = "A taxa deve ser um valor positivo ou zero")
    @Column(nullable = false)
    private Float taxa;



    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo_cidade) {
        this.codigo = codigo_cidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Float getTaxa() {
        return taxa;
    }

    public void setTaxa(Float taxa) {
        this.taxa = taxa;
    }
}
