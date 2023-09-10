package br.edu.ifnmg.dsc.extractnorth.infraestrutura;

import br.edu.ifnmg.dsc.extractnorth.entidades.Estoque;
import br.edu.ifnmg.dsc.extractnorth.servicos.EstoqueRepositorio;

public class EstoqueDAO extends DAO<Estoque> implements EstoqueRepositorio {
    
    public EstoqueDAO() {
        super(Estoque.class);
    }

}
