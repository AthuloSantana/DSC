package br.edu.ifnmg.dsc.extractnorth.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Cliente extends Pessoa {

    /* Attributes */

    @Column(length = 14, nullable = false, unique = true)
    private String cnpj;

    @Column(name = "Atividade", length = 255, nullable = false)
    private String atividade;

    @Override
    public String toString() {
        return "Cliente [cnpj=" + cnpj + ", atividade=" + atividade + "]";
    }

}
