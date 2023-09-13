package br.edu.ifnmg.dsc.extractnorth.infraestrutura;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.ifnmg.dsc.extractnorth.entidades.TransacaoFinanceira;
import br.edu.ifnmg.dsc.extractnorth.servicos.TransacaoRepositorio;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Service
public class TransacaoDAO extends DAO<TransacaoFinanceira> implements TransacaoRepositorio {

    public TransacaoDAO() {
        super(TransacaoFinanceira.class);
    }

    @Override
    public TransacaoFinanceira abrirPorStatusTransacao(int statusTransacao) {
        Query consulta = getManager().createQuery("select t from TransacaoFinanceira t where t.statusTransacao = :p1");
        consulta.setParameter("p1", statusTransacao);
        return (TransacaoFinanceira) consulta.getSingleResult();
    }

    @Override
    public TransacaoFinanceira abrirPorTipoTransacao(int tipoTransacao) {
        Query consulta = getManager().createQuery("select t from TransacaoFinanceira t where t.tipoTransacao = :p1");
        consulta.setParameter("p1", tipoTransacao);
        return (TransacaoFinanceira) consulta.getSingleResult();
    }

    @Override
    public TransacaoFinanceira abrirPorDataTransacao(Date dataTransacao) {
        Query consulta = getManager().createQuery("select t from TransacaoFinanceira t where t.dataTransacao = :p1");
        consulta.setParameter("p1", dataTransacao);
        return (TransacaoFinanceira) consulta.getSingleResult();
    }

    @Override
    @Transactional
    public List<TransacaoFinanceira> Buscar(TransacaoFinanceira filtro) {
        String jpql = "select t from TransacaoFinanceira t join t.pessoa p ";
        String where = "";
        ArrayList<Object> parametros = new ArrayList<>();

        if (filtro.getFormaPagamento() != null) {
            where += " t.formaPagamento = :p1";
            parametros.add(filtro.getFormaPagamento());
        }

        if (filtro.getTipoTransacao() != null) {
            if (where.length() > 0)
                where += " and ";

            where += " t.tipoTransacao = :p2 ";
            parametros.add(filtro.getTipoTransacao());
        }

        if (filtro.getStatusTransacao() != null) {
            if (where.length() > 0)
                where += " and ";

            where += " t.statusTransacao = :p3";
            parametros.add(filtro.getStatusTransacao());
        }

        if (filtro.getDataTransacao() != null) {
            if (where.length() > 0)
                where += " and ";

            where += " DATE(t.dataTransacao) = :p4";
            parametros.add(filtro.getDataTransacao());
        }

        if (filtro.getPessoa().getNome() != null || filtro.getPessoa().getNome() != "") {
            if (where.length() > 0)
                where += " and ";

            where += " p.nome = :p5";
            parametros.add(filtro.getPessoa().getNome());
        }

        if (where.length() > 0) {
            jpql += " where " + where;
        }

        TypedQuery<TransacaoFinanceira> consulta = getManager().createQuery(jpql, TransacaoFinanceira.class);
        if (where.length() > 0) {
            int ordem = 0;
            for (Object parametro : parametros) {
                ordem++;
                consulta.setParameter(ordem, parametro);
            }
        }

        return consulta.getResultList();
    }
}