package br.gov.endemias.entity;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import br.gov.endemias.enums.TipoDeposito;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "foco")
public class Foco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_tubito")
    private String numeroTubito;

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "tipo_deposito")
    private TipoDeposito tipoDeposito;

    @Column(name = "resultado_laboratorio")
    private String resultadoLaboratorio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visita_id")
    private Visita visita;
}
