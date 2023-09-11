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
public class Estoque {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(nullable = false)
  private Double quantidade;
  
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "produto_id", referencedColumnName = "id")
  private Produto produto;
}
