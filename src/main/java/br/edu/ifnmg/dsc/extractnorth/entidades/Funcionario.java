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
  private String cpf;

  @Column(nullable = false)
  private boolean status;

  @Column(nullable = false)
  private Double salario;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Genero genero;

  @Column
  @Temporal(TemporalType.DATE)
  private Date dataNascimento;

}
