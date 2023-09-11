package br.edu.ifnmg.dsc.extractnorth.infraestrutura;

import org.springframework.stereotype.Service;

import br.edu.ifnmg.dsc.extractnorth.entidades.Estoque;
import br.edu.ifnmg.dsc.extractnorth.entidades.Produto;
import br.edu.ifnmg.dsc.extractnorth.servicos.EstoqueRepositorio;
import jakarta.persistence.Query;

@Service
public class EstoqueDAO extends DAO<Estoque> implements EstoqueRepositorio {

    public EstoqueDAO() {
        super(Estoque.class);
    }

    @Override
    public Estoque abrirPorProduto(Produto produto) {
        Query consulta = getManager().createQuery("select e from Estoque e where e.produto = :p1");
        consulta.setParameter("p1", produto);
        return (Estoque) consulta.getSingleResult();
    }
}