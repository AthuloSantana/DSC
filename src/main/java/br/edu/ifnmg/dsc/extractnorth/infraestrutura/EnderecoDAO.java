package br.edu.ifnmg.dsc.extractnorth.infraestrutura;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.ifnmg.dsc.extractnorth.entidades.Endereco;
import br.edu.ifnmg.dsc.extractnorth.entidades.Pessoa;
import br.edu.ifnmg.dsc.extractnorth.servicos.EnderecoRepositorio;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Service
public class EnderecoDAO extends DAO<Endereco> implements EnderecoRepositorio {

    public EnderecoDAO() {
        super(Endereco.class);
    }

    @Override
    public Endereco abrirPorCidade(String cidade) {
        Query consulta = getManager().createQuery("select e from Endereco e where e.cidade = :e1");
        consulta.setParameter("e1", cidade);
        return (Endereco) consulta.getSingleResult();
    }

    @Override
    public Endereco abrirPorPessoa(Pessoa pessoa) {
        Query consulta = getManager().createQuery("select e from Endereco e where e.pessoa = :e1");
        consulta.setParameter("e1", pessoa);
        return (Endereco) consulta.getSingleResult();
    }

    @Override
    @Transactional
    public List<Endereco> Buscar(Endereco filtro) {
        String jpql = "select e from Endereco e ";
        String where = "";
        List<Object> parametros = new LinkedList<Object>();

        if (filtro.getRua() != null || filtro.getRua() != "") {
            where += " e.rua = :p1";
            parametros.add(filtro.getRua());
        }

        if (filtro.getBairro() != null || filtro.getBairro() != "") {
            if (where.length() > 0)
                where += " and ";

            where += " e.bairro = :p2 ";
            parametros.add(filtro.getBairro());
        }

        if (filtro.getCidade() != null || filtro.getCidade() != "") {
            if (where.length() > 0)
                where += " and ";

            where += " e.cidade = :p3";
            parametros.add(filtro.getCidade());
        }

        if (filtro.getNumero() != null || filtro.getNumero() != "") {
            if (where.length() > 0)
                where += " and ";

            where += " e.numero = :p4";
            parametros.add(filtro.getNumero());
        }

        if (filtro.getEstado() != null || filtro.getEstado() != "") {
            if (where.length() > 0)
                where += " and ";

            where += " e.estado = :p5";
            parametros.add(filtro.getEstado());
        }

        if (where.length() > 0) {
            jpql += " where " + where;
        }

        TypedQuery<Endereco> consulta = getManager().createQuery(jpql, Endereco.class);
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