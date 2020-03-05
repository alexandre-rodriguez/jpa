package com.algaworks.ecommerce.model;

import com.algaworks.ecommerce.listener.GenericoListener;
import com.algaworks.ecommerce.listener.GerarNotaFiscalListener;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.engine.spi.PersistentAttributeInterceptable;
import org.hibernate.engine.spi.PersistentAttributeInterceptor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NamedEntityGraphs({
        @NamedEntityGraph(name = "Pedido.dadosEssenciais",
        attributeNodes = {
                @NamedAttributeNode("dataCriacao"),
                @NamedAttributeNode("status"),
                @NamedAttributeNode("total"),
                @NamedAttributeNode(
                        value = "cliente",
                        subgraph = "cli"
                )
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "cli",
                        attributeNodes = {
                                @NamedAttributeNode("nome"),
                                @NamedAttributeNode("cpf")
                        }
                )
        })
})
@EntityListeners({GerarNotaFiscalListener.class, GenericoListener.class})
@Entity
@Table(name = "pedido")
public class Pedido extends EntidadeBaseInteger implements PersistentAttributeInterceptable {

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_pedido_cliente"))
    private Cliente cliente;

    @PastOrPresent
    @NotNull
    @Column(name = "data_criacao", updatable = false, nullable = false)
    private LocalDateTime dataCriacao; // datetime(6) not null

    @PastOrPresent
    @Column(name = "data_ultima_atualizacao", insertable = false)
    private LocalDateTime dataUltimaAtualizacao;

    @PastOrPresent
    @Column(name = "data_conclusao")
    private LocalDateTime dataConclusao;

    @LazyToOne(LazyToOneOption.NO_PROXY)
    @OneToOne(mappedBy = "pedido", fetch = FetchType.LAZY)
    //@OneToOne(mappedBy = "pedido")
    private NotaFiscal notaFiscal;

    @NotNull
    @Column(length = 30, nullable = false) // varchar(30) not null
    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    @NotNull
    @Positive
    @Column(nullable = false) // decimal(19,2) not null
    private BigDecimal total;

    @Embedded
    private EnderecoEntregaPedido enderecoEntrega;

    @NotEmpty
    @OneToMany(mappedBy = "pedido")
    private List<ItemPedido> itens;

    @LazyToOne(LazyToOneOption.NO_PROXY)
    @OneToOne(mappedBy = "pedido", fetch = FetchType.LAZY)
    //@OneToOne(mappedBy = "pedido")
    private Pagamento pagamento;

    public boolean isPago() {
        return StatusPedido.PAGO.equals(status);
    }

    public void calcularTotal() {
        if (itens != null) {
            total = itens.stream().map(ItemPedido::getPrecoProduto)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
    }

    @PrePersist
    public void aoPersistir() {
        dataCriacao = LocalDateTime.now();
        calcularTotal();
    }

    @PreUpdate
    public void aoAtualizar() {
        dataUltimaAtualizacao = LocalDateTime.now();
        calcularTotal();
    }

    @PostPersist
    public void aposPersistir() {
        System.out.println("Após persistir Pedido.");
    }

    @PostUpdate
    public void aposAtualizar() {
        System.out.println("Após atualizar Pedido.");
    }

    @PreRemove
    public void aoRemover() {
        System.out.println("Antes de remover Pedido.");
    }

    @PostRemove
    public void aposRemover() {
        System.out.println("Após remover Pedido.");
    }

    @PostLoad
    public void aoCarregar() {
        System.out.println("Após carregar o Pedido.");
    }

    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    @Transient
    private PersistentAttributeInterceptor persistentAttributeInterceptor;

    @Override
    public PersistentAttributeInterceptor $$_hibernate_getInterceptor() {
        return this.persistentAttributeInterceptor;
    }

    @Override
    public void $$_hibernate_setInterceptor(PersistentAttributeInterceptor interceptor) {
        this.persistentAttributeInterceptor = interceptor;
    }

    public NotaFiscal getNotaFiscal() {
        if (this.persistentAttributeInterceptor != null) {
            return (NotaFiscal) persistentAttributeInterceptor.readObject(this, "notaFiscal", this.notaFiscal);
        }
        return this.notaFiscal;
    }

    public void setNotaFiscal(NotaFiscal notaFiscal) {
        if (this.persistentAttributeInterceptor != null) {
            this.notaFiscal = (NotaFiscal) persistentAttributeInterceptor.writeObject(this, "notaFiscal", this.notaFiscal, notaFiscal);
        } else {
            this.notaFiscal = notaFiscal;
        }
    }

    public Pagamento getPagamento() {
        if (this.persistentAttributeInterceptor != null) {
            return (Pagamento) persistentAttributeInterceptor.readObject(this, "pagamento", this.pagamento);
        }
        return this.pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        if (this.persistentAttributeInterceptor != null) {
            this.pagamento = (Pagamento) persistentAttributeInterceptor.writeObject(this, "pagamento", this.pagamento, pagamento);
        } else {
            this.pagamento = pagamento;
        }
    }

}
