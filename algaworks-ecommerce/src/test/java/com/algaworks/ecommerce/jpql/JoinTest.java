package com.algaworks.ecommerce.jpql;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.dto.ProdutoDTO;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.Pedido;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class JoinTest extends EntityManagerTest {

    @Test
    public void fazerJoin() {
        String jpql = "select p from Pedido p join p.pagamento pag";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertTrue(lista.size() == 1);
    }
}
