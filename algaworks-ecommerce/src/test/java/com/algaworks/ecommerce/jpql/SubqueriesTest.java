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
    public void pesquisarComAllExercicio() {
        // Todos os produtos que sempre foram vendidos pelo mesmo preço.
        String jpql = "select distinct ip.produto from ItemPedido ip where " +
                " ip.precoProduto = ALL " +
                " (select precoProduto from ItemPedido where produto = ip.produto and pedido <> ip.pedido)";

        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);

        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void perquisarComAny() {
        // Podemos usar o ANY e o SOME

        // Todos os produtos que já foram vendidos por um preço diferente do atual
        String jpql = "select p from Produto p where " +
                "p.preco <> ANY(select precoProduto from ItemPedido where produto = p)";

        // Todos os produtos que já foram vendidos, pelo menos, uma vez pelo preço atual.
        /*
        String jpql = "select p from Produto p where " +
                "p.preco = ANY (select precoProduto from ItemPedido where produto = p)";
        */

        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);

        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void perquisarComAll() {

        // Todos os produtos que não foram vendidos mais depois que encareceram
        String jpql = "select p from Produto p where " +
                "p.preco > ALL(select precoProduto from ItemPedido where produto = p)";

        // Todos os produtos que sempre foram vendidos pelo preco atual
        /*
        String jpql = "select p from Produto p where " +
                "p.preco = ALL(select precoProduto from ItemPedido where produto = p)";
        */

        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);

        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void perquisarComExistsExercicio() {
        String jpql = "select p from Produto p " +
                " where exists " +
                " (select 1 from ItemPedido where produto = p and precoProduto <> p.preco)";

        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);

        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void perquisarComSubqueryExercicio() {
        String jpql = "select c from Cliente c where " +
                "(select count(p.cliente) from Pedido p where p.cliente = c) >= 2";

        TypedQuery<Cliente> typedQuery = entityManager.createQuery(jpql, Cliente.class);

        List<Cliente> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(c -> System.out.println("ID: " + c.getId()));
    }

    @Test
    public void pesquisarComINExercicio() {
        String jpql = "select p from Pedido p where p in " +
                "(select i2.pedido from ItemPedido i2 " +
                "   join i2.produto pro2 join pro2.categorias c2 " +
                "   where c2.id = 2)";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);

        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void pesquisarComExists() {
        String jpql = "select p from Produto p where exists " +
                "(select 1 from ItemPedido ip2 join ip2.produto p2 where p2 = p)";

        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);

        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(c -> System.out.println("ID: " + c.getId()));
    }

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
