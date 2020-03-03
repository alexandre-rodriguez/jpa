package com.algaworks.ecommerce.criteria;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.*;
import com.sun.javadoc.Parameter;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PassandoParametrosCriteriaTest extends EntityManagerTest {

    @Test
    public void passarParametroDate() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<NotaFiscal> criteriaQuery = criteriaBuilder.createQuery(NotaFiscal.class);
        Root<NotaFiscal> root = criteriaQuery.from(NotaFiscal.class);

        criteriaQuery.select(root);

        ParameterExpression<Date> parameterExpressionData = criteriaBuilder.parameter(Date.class, "dataInicial");

        criteriaQuery.where(criteriaBuilder.greaterThan(root.get("dataEmissao"), parameterExpressionData));

        TypedQuery<NotaFiscal> typedQuery = entityManager.createQuery(criteriaQuery);

        Calendar dataInicial = Calendar.getInstance();
        dataInicial.add(Calendar.DATE, -30);

        typedQuery.setParameter("dataInicial", dataInicial.getTime(), TemporalType.TIMESTAMP);

        List<NotaFiscal> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
    }

    @Test
    public void passarParametro() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);

        criteriaQuery.select(root);

        ParameterExpression<Integer> parameterExpressionId = criteriaBuilder.parameter(Integer.class, "id");

        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), parameterExpressionId));

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setParameter(parameterExpressionId, 1);
        // typedQuery.setParameter("id", 1);

        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
    }

}
