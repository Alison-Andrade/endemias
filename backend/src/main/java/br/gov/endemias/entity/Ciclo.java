package br.gov.endemias.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
    name = "ciclo",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_numero_ano_ciclo",
            columnNames = {"numero_ciclo", "ano"}
        )
    }
)
@Getter
@Setter
@AllArgsConstructor
public class Ciclo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_ciclo")
    private Integer numeroCiclo;

    @Column(nullable = false)
    private Integer ano;
}
