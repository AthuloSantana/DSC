package br.edu.ifnmg.dsc.extractnorth.entidades;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Endereco {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String rua;

  @Column
  private String bairro;

  @Column
  private String cidade;

  @Column
  private String numero;

  @Column
  private String estado;

  @OneToOne(mappedBy = "endereco")
  private Pessoa pessoa;
}
