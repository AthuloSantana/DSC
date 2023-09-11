package br.edu.ifnmg.dsc.extractnorth.infraestrutura;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.ifnmg.dsc.extractnorth.entidades.Cliente;
import br.edu.ifnmg.dsc.extractnorth.servicos.ClienteRepositorio;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Service
public class ClienteDAO extends DAO<Cliente> implements ClienteRepositorio {

    public ClienteDAO() {
        super(Cliente.class);
    }

    @Override
    public Cliente abrirPorCNPJ(String cnpj) {
        Query consulta = getManager().createQuery("select c from Cliente c where c.cnpj = :p1");
        consulta.setParameter("p1", cnpj);
        return (Cliente) consulta.getSingleResult();
    }

    @Override
    public Cliente abrirPorAtividade(String atividade) {
        Query consulta = getManager().createQuery("select c from Cliente c where c.atividade = :p1");
        consulta.setParameter("p1", atividade);
        return (Cliente) consulta.getSingleResult();
    }

    @Override
    @Transactional
    public List<Cliente> Buscar(Cliente filtro) {
        String jpql = "select c from Cliente c join c.endereco e ";
        String where = "";
        ArrayList<Object> parametros = new ArrayList<>();

        if (filtro.getNome() != null || filtro.getNome() != "") {
            where += "c.nome like CONCAT('%', :p1 , '%')";
            parametros.add(filtro.getNome());
        }

        if (filtro.getTelefone() != null || filtro.getTelefone() != "") {
            if (where.length() > 0)
                where += " and ";

            where += " c.telefone = :p2 ";
            parametros.add(filtro.getTelefone());
        }

        if (filtro.getCnpj() != null || filtro.getCnpj() != "") {
            if (where.length() > 0)
                where += "and ";

            where += "c.cnpj = :p3 ";
            parametros.add(filtro.getCnpj());
        }

        if (filtro.getEmail() != null || filtro.getEmail() != "") {
            if (where.length() > 0)
                where += "and ";

            where += "c.email = :p4";
            parametros.add(filtro.getEmail());
        }

        if (filtro.getAtividade() != null || filtro.getAtividade() != "") {
            if (where.length() > 0)
                where += "and ";

            where += "c.atividade = :p5";
            parametros.add(filtro.getAtividade());
        }

        if (filtro.getEndereco().getCidade() != null || filtro.getEndereco().getCidade() != "") {
            if (where.length() > 0)
                where += "and ";

            where += "e.cidade = :p6 ";
            parametros.add(filtro.getEndereco().getCidade());
        }

        if (filtro.getEndereco().getBairro() != null || filtro.getEndereco().getBairro() != "") {
            if (where.length() > 0)
                where += "and ";

            where += "e.bairro = :p7 ";
            parametros.add(filtro.getEndereco().getBairro());
        }

        if (filtro.getEndereco().getEstado() != null || filtro.getEndereco().getEstado() != "") {
            if (where.length() > 0)
                where += "and ";

            where += "e.estado = :p8 ";
            parametros.add(filtro.getEndereco().getEstado());
        }

        if (where.length() > 0) {
            jpql += " where " + where;
        }

        TypedQuery<Cliente> consulta = getManager().createQuery(jpql, Cliente.class);

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
