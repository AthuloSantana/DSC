package br.edu.ifnmg.dsc.extractnorth.infraestrutura;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.ifnmg.dsc.extractnorth.entidades.Fornecedor;
import br.edu.ifnmg.dsc.extractnorth.entidades.Usuario;
import br.edu.ifnmg.dsc.extractnorth.servicos.UsuarioRepositorio;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Service
public class UsuarioDAO extends DAO<Usuario> implements UsuarioRepositorio {

    public UsuarioDAO() {
        super(Usuario.class);
    }

    @Override
    public Usuario Abrir(String login) {
        try {
            Query consulta = (Query) getManager()
                    .createQuery("select u from Usuario u where u.login = :login");
            consulta.setParameter("login", login);

            return (Usuario) consulta.getSingleResult();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @Override
    @Transactional
    public List<Usuario> Buscar(Usuario filtro) {
        String jpql = "select u from Usuario u ";
        String where = "";
        List<Object> parametros = new ArrayList<>();

        if (filtro.getNome() != null || filtro.getNome() != "") {
            where += " u.nome like CONCAT('%', :p1 ,'%') ";
            parametros.add(filtro.getNome());
        }

        if (filtro.getLogin() != null || filtro.getLogin() != "") {
            if (where.length() > 0)
                where += " and ";
            where += " u.login = :p2 ";
            parametros.add(filtro.getLogin());
        }

        if (where.length() > 0) {
            jpql += " where " + where;
        }

        TypedQuery<Usuario> consulta = getManager().createQuery(jpql, Usuario.class);
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