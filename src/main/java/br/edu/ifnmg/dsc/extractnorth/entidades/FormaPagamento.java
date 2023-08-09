package br.edu.ifnmg.dsc.extractnorth.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "FormaPagamentos")
public class FormaPagamento extends TransacaoFinanceira {

  @Column(name = "Dinheiro")
  public int dinheiro = 1;
  
  @Column(name = "Pix")
  public int pix = 2;

  @Column(name = "Cartão")
  public int cartao = 3;
}
