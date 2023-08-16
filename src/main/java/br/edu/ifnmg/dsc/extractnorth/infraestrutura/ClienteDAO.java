package br.edu.ifnmg.dsc.extractnorth.infraestrutura;

import java.util.ArrayList;
import org.springframework.stereotype.Service;
import com.thoughtworks.qdox.model.expression.Query;
import br.edu.ifnmg.dsc.extractnorth.entidades.Cliente;
import br.edu.ifnmg.dsc.extractnorth.servicos.ClienteRepositorio;

@Service
public class ClienteDAO extends DAO<Cliente> implements ClienteRepositorio {

    public ClienteDAO() {
        super(Cliente.class);
    }

    @Override
    public Cliente abrirPorCNPJ(String cnpj) {
        Query consulta = (Query) getManager().createQuery("select c from Cliente c where cnpj = :p");
        ((jakarta.persistence.Query) consulta).setParameter("p", cnpj);
        return (Cliente) ((jakarta.persistence.Query) consulta).getSingleResult();
    }

    @Override
    public ArrayList<Cliente> Buscar(Cliente filtro) {
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

        if (filtro.getEndereco().getCidade() != null || filtro.getEndereco().getCidade() != "") {
            if (where.length() > 0)
                where += "and ";

            where += "e.cidade = :p5 ";
            parametros.add(filtro.getEndereco().getCidade());
        }

        if (filtro.getEndereco().getBairro() != null || filtro.getEndereco().getBairro() != "") {
            if (where.length() > 0)
                where += "and ";

            where += "e.bairro = :p6 ";
            parametros.add(filtro.getEndereco().getBairro());
        }

        if (filtro.getEndereco().getEstado() != null || filtro.getEndereco().getEstado() != "") {
            if (where.length() > 0)
                where += "and ";

            where += "e.estado = :p6 ";
            parametros.add(filtro.getEndereco().getEstado());
        }

        String jpq = "";
        if (where.length() > 0) {
            jpq += " where " + where;
        }

        jakarta.persistence.Query consulta = getManager().createQuery(jpql);
        int ordem = 0;

        for (Object parametro : parametros) {
            ordem++;
            ((jakarta.persistence.Query) consulta).setParameter(ordem, parametro);
        }

        return (ArrayList<Cliente>) ((jakarta.persistence.Query) consulta).getResultList();

    }

}