package br.edu.ifnmg.dsc.extractnorth.entidades;

import java.util.Date;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class Funcionario extends Pessoa {

  @Column(length = 11, unique = true)
  protected String cpf;

  @Column(nullable = false)
  protected boolean status;

  @Column(nullable = false)
  protected Double salario;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  protected Genero genero;

  @Column
  @Temporal(TemporalType.DATE)
  protected Date dataNascimento;

}
