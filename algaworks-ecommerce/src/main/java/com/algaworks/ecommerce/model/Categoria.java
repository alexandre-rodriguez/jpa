package com.algaworks.ecommerce.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "categoria",
        uniqueConstraints = {@UniqueConstraint(name = "unq_nome", columnNames = { "nome"})},
        indexes = { @Index(name = "idx_nome", columnList = "nome", unique = false)})
public class Categoria extends EntidadeBaseInteger {

    @Column(length = 100, nullable = false) // varchar(100) not null
    private String nome;

    @ManyToOne
    @JoinColumn(name = "categoria_pai_id")
    private Categoria categoriaPai;

    @OneToMany(mappedBy = "categoriaPai")
    private List<Categoria> categorias;

    @ManyToMany(mappedBy = "categorias")
    private List<Produto> produtos;
}
