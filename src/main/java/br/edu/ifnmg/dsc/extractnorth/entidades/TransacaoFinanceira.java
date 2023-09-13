package br.edu.ifnmg.dsc.extractnorth.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
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

  @OneToMany(mappedBy = "transacaoFinanceira", cascade = CascadeType.ALL)
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

  @Column(nullable = false)
  private Double valor;

  @Column
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDate dataTransacao;

}
