package br.edu.ifnmg.dsc.extractnorth.infraestrutura;

import org.springframework.stereotype.Service;

import br.edu.ifnmg.dsc.extractnorth.entidades.Produto;
import br.edu.ifnmg.dsc.extractnorth.servicos.ProdutoRepositorio;
import jakarta.persistence.Query;

@Service
public class ProdutoDAO extends DAO<Produto> implements ProdutoRepositorio {

  public ProdutoDAO() {
    super(Produto.class);
  }

  @Override
  public Produto abrirPorNome(String nome) {
    Query consulta = getManager().createQuery("select p from Produto p where p.nome = :p1");
    consulta.setParameter("p1", nome);
    return (Produto) consulta.getSingleResult();
  }

}
