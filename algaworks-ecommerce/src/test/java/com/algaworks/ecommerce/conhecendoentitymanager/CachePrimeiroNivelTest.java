package com.algaworks.ecommerce.conhecendoentitymanager;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Categoria;
import com.algaworks.ecommerce.model.Produto;
import org.junit.Test;

public class CachePrimeiroNivelTest extends EntityManagerTest {

    @Test
    public void verificarCache() {
        Produto produto = entityManager.find(Produto.class, 1);
        System.out.println(produto.getNome());

        System.out.println("------------------------------");

        // Limpa o cache de primeiro nível
        // entityManager.clear();

        Produto produtoResgatado = entityManager.find(Produto.class, produto.getId());
        System.out.println(produtoResgatado.getNome());
    }
}
