package br.edu.ifnmg.dsc.extractnorth.servicos;

import java.util.List;

import br.edu.ifnmg.dsc.extractnorth.entidades.Produto;

public interface ProdutoRepositorio extends Repositorio<Produto> {

  public Produto abrirPorNome(String nome);

  public List<String> buscarNome();

}
