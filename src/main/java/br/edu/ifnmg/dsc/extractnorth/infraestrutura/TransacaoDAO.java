package br.edu.ifnmg.dsc.extractnorth.infraestrutura;

import org.springframework.stereotype.Service;

import br.edu.ifnmg.dsc.extractnorth.entidades.TransacaoFinanceira;
import br.edu.ifnmg.dsc.extractnorth.servicos.TransacaoRepositorio;
import jakarta.persistence.Query;

@Service
public class TransacaoDAO extends DAO<TransacaoFinanceira> implements TransacaoRepositorio {

    public TransacaoDAO() {
        super(TransacaoFinanceira.class);
    }

    @Override
    public TransacaoFinanceira abrirPorTipoTransacao(int tipoTransacao) {
        Query consulta = getManager().createQuery("select t from TransacaoFinanceira t where t.tipoTransacao = :p1");
        consulta.setParameter("p1", tipoTransacao);
        return (TransacaoFinanceira) consulta.getSingleResult();
    }
}
