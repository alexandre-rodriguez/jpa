package com.algaworks.ecommerce.jpql;

import com.algaworks.ecommerce.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class FuncoesStringsTest  extends EntityManagerTest {

    @Test
    public void aplicarFuncoes() {
        // concat, length, locate, substring, lower, upper, trim

        //String jpql = "select c.nome, concat('Categoria: ', c.nome) from Categoria c";
        //String jpql = "select c.nome, length(c.nome) from Categoria c";
        //String jpql = "select c.nome, locate('a', c.nome) from Categoria c";
        //String jpql = "select c.nome, substring(c.nome, 1, 2) from Categoria c";
        //String jpql = "select c.nome, lower(c.nome) from Categoria c";
        String jpql = "select c.nome, upper(c.nome) from Categoria c";


        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(arr -> System.out.println(arr[0] + " = " + arr[1]));
    }
}
