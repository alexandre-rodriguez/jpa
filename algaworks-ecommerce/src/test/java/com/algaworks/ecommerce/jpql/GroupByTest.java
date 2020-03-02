package com.algaworks.ecommerce.jpql;

import com.algaworks.ecommerce.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.TimeZone;

public class GroupByTest extends EntityManagerTest {

    @Test
    public void agruparEFiltrarResultado() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

         // Total de vendas por mês
         /*
         String jpql = "select concat(year(p.dataCriacao), '/', function('monthname', p.dataCriacao)), sum(p.total) " +
                 "from Pedido p " +
                 "where year(p.dataCriacao) = year(current_date) " +
                 "group by year(p.dataCriacao), month(p.dataCriacao)";
         */

         // Total de vendas por categoria
         /*
         String jpql = "select c.nome, sum(ip.precoProduto * ip.quantidade) " +
                 "from ItemPedido ip join ip.produto pro join pro.categorias c join ip.pedido p " +
                 "where year(p.dataCriacao) = year(current_date) and month(p.dataCriacao) = month(current_date) " +
                 "group by c.id";
        */

        // Total de vendas por cliente
        String jpql = "select c.nome, sum(p.total) from Pedido p join p.cliente c " +
                "where year(p.dataCriacao) = year(current_date) and month(p.dataCriacao) >= (month(current_date) -3) " +
                "group by c.id";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(arr -> System.out.println(arr[0] + ", " + arr[1]));
    }

    @Test
    public void agruparResultado() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        // Quantidade de produtos por categoria
        // String jpql = "select c.nome, count(p.id) from Categoria c join c.produtos p group by c.id";

        // Total de vendas por mês
        // String jpql = "select concat(year(p.dataCriacao), '/', function('monthname', p.dataCriacao)), sum(p.total) " +
        //        "from Pedido p group by year(p.dataCriacao), month(p.dataCriacao)";

        // Total de vendas por categoria
        // String jpql = "select c.nome, sum(ip.precoProduto * ip.quantidade) " +
        //        "from ItemPedido ip join ip.produto pro join pro.categorias c " +
        //        "group by c.id";

        // Total de vendas por cliente
        // String jpql = "select c.nome, sum(p.total) from Pedido p join p.cliente c group by c.id";

        // Total de vendas por dia e por categoria
//        String jpql = "select " +
//                " concat(year(p.dataCriacao), '/', month(p.dataCriacao), '/', day(p.dataCriacao)), " +
//                " concat(c.nome, ': ', sum(ip.precoProduto)) " +
//                " from ItemPedido ip join ip.pedido p join ip.produto pro join pro.categorias c " +
//                " group by year(p.dataCriacao), month(p.dataCriacao), day(p.dataCriacao), c.id " +
//                " order by p.dataCriacao, c.nome ";

        // Total de vendas por dia e por categoria
        String jpql = "select date(p.dataCriacao), " +
                " concat(c.nome, ': ', sum(ip.precoProduto * ip.quantidade)) " +
                " from ItemPedido ip join ip.pedido p join ip.produto pro join pro.categorias c " +
                " group by date(p.dataCriacao), c.id " +
                " order by date(p.dataCriacao), c.nome ";


        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(arr -> System.out.println(arr[0] + ", " + arr[1]));
    }

}
