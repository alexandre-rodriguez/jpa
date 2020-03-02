package com.algaworks.ecommerce.jpql;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Produto;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.TimeZone;

public class SubqueriesTest extends EntityManagerTest {

    @Test
    public void pesquisarComIN() {
        String jpql = "select p from Pedido p where p.id in " +
                "(select p2.id from ItemPedido i2 " +
                "   join i2.pedido p2 join i2.produto pro2 where pro2.preco > 100) ";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);

        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(c -> System.out.println("ID: " + c.getId()));
    }

    @Test
    public void pesquisarSubqueries() {
        // Bons clientes. Versão 2
        String jpql = "select c from Cliente c where 500 < " +
                "(select sum(p.total) from Pedido p where p.cliente = c)";

        TypedQuery<Cliente> typedQuery = entityManager.createQuery(jpql, Cliente.class);

        List<Cliente> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(c -> System.out.println("ID: " + c.getId() + ", Nome: " + c.getNome()));

        // Bons clientes. Versão 1
        /*
        String jpql = "select c from Cliente c where 500 < " +
                "(select sum(p.total) from c.pedidos p)";

        TypedQuery<Cliente> typedQuery = entityManager.createQuery(jpql, Cliente.class);

        List<Cliente> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(c -> System.out.println("ID: " + c.getId() + ", Nome: " + c.getNome()));
        */

        // Todos os pedidos acima da média de vendas
        /*
        String jpql = "select p from Pedido p where p.total > " +
                "(select avg(total) from Pedido)";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);

        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(p -> System.out.println("ID: " + p.getId() + ", Total: " + p.getTotal()));
        */

        // O produto ou os produtos mais cros da base
        /*
        String jpql = "select p from Produto p where p.preco = " +
                "(select max(preco) from Produto)";

        typedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);

        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(p -> System.out.println("ID: " + p.getId() + ", Preço: " + p.getPreco()));
        */
    }

}
