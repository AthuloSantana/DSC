package br.edu.ifnmg.dsc.extractnorth.infraestrutura;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.ifnmg.dsc.extractnorth.entidades.Fornecedor;
import br.edu.ifnmg.dsc.extractnorth.servicos.FornecedorRepositorio;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Service
public class FornecedorDAO extends DAO<Fornecedor> implements FornecedorRepositorio {

    public FornecedorDAO() {
        super(Fornecedor.class);
    }

    @Override
    public Fornecedor abrirPorCPF(String cpf) {
        Query consulta = getManager().createQuery("select f from Fornecedor f where f.cpf = :p");
        consulta.setParameter("p", cpf);
        return (Fornecedor) consulta.getSingleResult();
    }

    @Override
    public List<String> buscarNome() {
        TypedQuery<String> consulta = getManager().createQuery("select f.nome from Fornecedor f", String.class);
        return consulta.getResultList();
    }

    @Override
    @Transactional
    public List<Fornecedor> Buscar(Fornecedor filtro) {
        String jpql = "select f from Fornecedor f join f.endereco e ";
        String where = "";
        List<Object> parametros = new ArrayList<>();

        if (filtro.getNome() != null || filtro.getNome() != "") {
            where += " f.nome like CONCAT('%', :p1 ,'%') ";
            parametros.add(filtro.getNome());
        }

        if (filtro.getTelefone() != null || filtro.getTelefone() != "") {
            if (where.length() > 0)
                where += " and ";
            where += " f.telefone = :p2 ";
            parametros.add(filtro.getTelefone());
        }

        if (filtro.getCpf() != null || filtro.getCpf() != "") {
            if (where.length() > 0)
                where += "and ";
            where += "f.cpf = :p3 ";
            parametros.add(filtro.getCpf());
        }

        if (filtro.getEmail() != null || filtro.getEmail() != "") {
            if (where.length() > 0)
                where += "and ";
            where += "f.email = :p4";
            parametros.add(filtro.getEmail());
        }

        if (filtro.getEndereco().getCidade() != null || filtro.getEndereco().getCidade() != "") {
            if (where.length() > 0)
                where += "and ";
            where += "e.cidade = :p5 ";
            parametros.add(filtro.getEndereco().getCidade());
        }

        if (where.length() > 0) {
            jpql += " where " + where;
        }

        TypedQuery<Fornecedor> consulta = getManager().createQuery(jpql, Fornecedor.class);
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