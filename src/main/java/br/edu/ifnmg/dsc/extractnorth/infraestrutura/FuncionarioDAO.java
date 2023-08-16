package br.edu.ifnmg.dsc.extractnorth.infraestrutura;

import java.util.ArrayList;
import org.springframework.stereotype.Service;
import com.thoughtworks.qdox.model.expression.Query;
import br.edu.ifnmg.dsc.extractnorth.entidades.Funcionario;
import br.edu.ifnmg.dsc.extractnorth.servicos.FuncionarioRepositorio;

@Service
public class FuncionarioDAO extends DAO<Funcionario> implements FuncionarioRepositorio {

    public FuncionarioDAO() {
        super(Funcionario.class);
    }

    @Override
    public Funcionario abrirPorCPF(String cpf) {
        Query consulta = (Query) getManager().createQuery("select f from Funcionario f where cpf = :p");
        ((jakarta.persistence.Query) consulta).setParameter("p", cpf);
        return (Funcionario) ((jakarta.persistence.Query) consulta).getSingleResult();
    }

    @Override
    public ArrayList<Funcionario> buscarFuncionarioPorStatus(boolean status) {
        Query consulta = (Query) getManager().createQuery("select f from Funcionario f where status = :p");
        ((jakarta.persistence.Query) consulta).setParameter("p", status);
        return (ArrayList<Funcionario>) ((jakarta.persistence.Query) consulta).getResultList();
    }

    @Override
    public ArrayList<Funcionario> Buscar(Funcionario filtro) {
        String jpql = "select f from Funcionario f join f.endereco e ";
        String where = "";
        ArrayList<Object> parametros = new ArrayList<>();

        if (filtro.getNome() != null || filtro.getNome() != "") {
            where += "f.nome like CONCAT('%', :p1 , '%')";
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

        return (ArrayList<Funcionario>) ((jakarta.persistence.Query) consulta).getResultList();

    }

}