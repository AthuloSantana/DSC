package br.edu.ifnmg.dsc.extractnorth.servicos;

import br.edu.ifnmg.dsc.extractnorth.entidades.Estoque;
import br.edu.ifnmg.dsc.extractnorth.entidades.Produto;

public interface EstoqueRepositorio extends Repositorio<Estoque> {

    public Estoque abrirPorProduto(Produto produto);

}