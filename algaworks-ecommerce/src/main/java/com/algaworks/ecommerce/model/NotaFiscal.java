package com.algaworks.ecommerce.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "nota_fiscal")
public class NotaFiscal extends EntidadeBaseInteger {

    @MapsId
    @OneToOne(optional = false)
    @JoinColumn(name = "pedido_id", nullable = false, foreignKey = @ForeignKey(name = "fk_nota_fiscal_pedido"))
    /*
    @JoinTable(name = "pedido_nota_fiscal",
        joinColumns = @JoinColumn(name = "nota_fiscal_id", unique = true),
        inverseJoinColumns = @JoinColumn(name="pedido_id", unique = true))
     */
    private Pedido pedido;

    @Column(nullable = false) //longblob not null
    @Lob
    private byte[] xml;

    @Column(name = "data_emissao", nullable = false) // datetime(6) not null
    private Date dataEmissao;

}
