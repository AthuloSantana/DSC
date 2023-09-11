package br.edu.ifnmg.dsc.extractnorth.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class TransacaoFinanceira {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Enumerated(EnumType.ORDINAL)
  @Column(nullable = false)
  private FormaPagamento formaPagamento;

  @OneToMany(mappedBy = "transacao", cascade = CascadeType.ALL)
  private List<Item> itens;

  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "pessoa_id", referencedColumnName = "id")
  private Pessoa pessoa;

  @Enumerated(EnumType.ORDINAL)
  @Column(nullable = false)
  private TipoTransacao tipoTransacao;

  @Enumerated(EnumType.ORDINAL)
  @Column(nullable = false)
  private StatusTransacao statusTransacao;

  @Column(precision = 8, scale = 2)
  private BigDecimal valor;

  @Column
  @Temporal(TemporalType.TIMESTAMP)
  private Date dataTransacao;

}
