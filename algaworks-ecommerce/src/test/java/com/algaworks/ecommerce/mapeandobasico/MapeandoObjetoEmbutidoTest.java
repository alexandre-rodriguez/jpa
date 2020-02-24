package com.algaworks.ecommerce.mapeandobasico;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.*;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MapeandoObjetoEmbutidoTest extends EntityManagerTest {

    @Test
    public void analisarMapeamentoObjetoEmbutido() {
        Cliente cliente = entityManager.find(Cliente.class, 1);

        EnderecoEntregaPedido endereco = new EnderecoEntregaPedido();
        endereco.setCep("00000-000");
        endereco.setLogradouro("Rua das Laranjeiras");
        endereco.setNumero("123");
        endereco.setBairro("Centro");
        endereco.setCidade("Uberlândia");
        endereco.setEstado("MG");

        Pedido pedido = new Pedido();
        // pedido.setId(1);  Comentado porque estamos utilizando IDENTITY
        pedido.setDataCriacao(LocalDateTime.now());
        pedido.setStatus(StatusPedido.AGUARDANDO);
        pedido.setTotal(new BigDecimal(1000));
        pedido.setEnderecoEntrega(endereco);
        pedido.setCliente(cliente);

        entityManager.getTransaction().begin();
        entityManager.persist(pedido);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Pedido pedidoVerificado = entityManager.find(Pedido.class, pedido.getId());

        Assert.assertNotNull(pedidoVerificado);
        Assert.assertNotNull(pedidoVerificado.getEnderecoEntrega());
        Assert.assertNotNull(pedidoVerificado.getEnderecoEntrega().getCep());

    }
}
