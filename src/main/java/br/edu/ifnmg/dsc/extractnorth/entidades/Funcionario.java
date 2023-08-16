package br.edu.ifnmg.dsc.extractnorth.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue(value = "3")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Funcionario extends Pessoa {

  /* Attributes */

  @Column(length = 11, nullable = true, unique = true)
  private String cpf;

  @Column(nullable = false)
  private boolean status;

  @Column(nullable = false)
  private Double salario;

  @Override
  public String toString() {
    return "Funcionario [cpf=" + cpf + ", status=" + status + "]";
  }

}
