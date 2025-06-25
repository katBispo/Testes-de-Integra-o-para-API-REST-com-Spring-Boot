package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "frete")
public class Frete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    @ManyToOne
    @JoinColumn(name = "codigo_cidade", nullable = false)
    private Cidade cidade;

    @ManyToOne
    @JoinColumn(name = "codigo_cliente", nullable = false)
    private Cliente cliente;

    @NotNull(message = "A descrição é obrigatória")
    @Size(max = 30, message = "A descrição deve ter no máximo 30 caracteres")
    @Column(nullable = false, length = 30)
    private String descricao;

    @NotNull(message = "O peso é obrigatório")
    @Positive(message = "O peso deve ser positivo")
    @Column(nullable = false)
    private Float peso;

    @NotNull(message = "O valor é obrigatório")
    @PositiveOrZero(message = "O valor deve ser zero ou positivo")
    @Column(nullable = false)
    private Float valor;

  public Frete(String descricao, float peso, float valor, Cliente cliente, Cidade cidade) {
    this.descricao = descricao;
    this.peso = peso;
    this.valor = valor;
    this.cliente = cliente;
    this.cidade = cidade;
}


    public Frete() {
}


    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo_frete) {
        this.codigo = codigo_frete;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Float getPeso() {
        return peso;
    }

    public void setPeso(Float peso) {
        this.peso = peso;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }
}
