package com.algaworks.ecommerce.consultasnativas;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Cliente;
import org.junit.Test;

import javax.persistence.Query;
import java.util.List;


public class ViewTest extends EntityManagerTest {

    @Test
    public void executarView() {
        String sql = "select cli.id, cli.nome, sum(ped.total) " +
                "from pedido ped " +
                "join view_clientes_acima_media cli " +
                "    on cli.id = ped.cliente_id " +
                "group by ped.cliente_id";

        Query query = entityManager.createNativeQuery(sql);

        List<Object[]> lista = query.getResultList();

        lista.forEach(arr -> System.out.println(
                String.format("Cliente => ID: %s, Nome: %s, Total: %s", arr)
        ));

    }

    @Test
    public void executarViewRetornandoCliente() {
        String sql = "select * from view_clientes_acima_media ";

        Query query = entityManager.createNativeQuery(sql, Cliente.class);

        List<Cliente> lista = query.getResultList();

        lista.forEach(c -> System.out.println(
                String.format("Cliente => ID: %s, Nome: %s", c.getId(), c.getNome())
        ));

    }
}
