package br.edu.ifnmg.dsc.extractnorth.entidades;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Produtos")
public class Produto {

  /* Attributes */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(length = 255, nullable = false)
  private String nome;

  @Column(nullable = false)
  private double precoCompra;

  @Column(nullable = false)
  private Double precoVenda;

  public Produto(int id, String nome, double precoCompra, Double precoVenda) {
    this.id = id;
    this.nome = nome;
    this.precoCompra = precoCompra;
    this.precoVenda = precoVenda;
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNome() {
    return this.nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public double getPrecoCompra() {
    return this.precoCompra;
  }

  public void setPrecoCompra(double precoCompra) {
    this.precoCompra = precoCompra;
  }

  public Double getPrecoVenda() {
    return this.precoVenda;
  }

  public void setPrecoVenda(Double precoVenda) {
    this.precoVenda = precoVenda;
  }

  
  @Override
  public int hashCode() {
    return Objects.hash(id, nome, precoCompra, precoVenda);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof Produto))
      return false;
    Produto other = (Produto) obj;
    return id == other.id && Objects.equals(nome, other.nome)
        && Double.doubleToLongBits(precoCompra) == Double.doubleToLongBits(other.precoCompra)
        && Objects.equals(precoVenda, other.precoVenda);
  }

  @Override
  public String toString() {
    return nome;
  }
  

}
