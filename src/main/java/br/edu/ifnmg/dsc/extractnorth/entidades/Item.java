package br.edu.ifnmg.dsc.extractnorth.entidades;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Item {

  @Id
  @ManyToOne
  @JoinColumn(name = "transacao_financeira_id", nullable = false)
  private TransacaoFinanceira transacaoFinanceira;

  @Id
  @ManyToOne
  @JoinColumn(name = "produto_id", nullable = false)
  private Produto produto;

  @Column(nullable = false)
  private double quantidade;

  @Column(precision = 8, scale = 2)
  private BigDecimal valorUnitario;

  @Column(precision = 8, scale = 2)
  private BigDecimal valorTotal;
}
