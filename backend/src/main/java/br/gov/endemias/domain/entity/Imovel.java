package br.gov.endemias.domain.entity;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import br.gov.endemias.domain.enums.TipoImovel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
    name = "imovel", 
    indexes = {
        @Index(name="idx_imovel_lado", columnList = "lado_id")
    }
)
@Getter
@Setter
public class Imovel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String placa;

    @Column(name = "numero_sms")
    private Integer numeroSms;

    private Integer sequencia;
    
    @Column(name = "num_residentes")
    private Integer numeroResidentes;

    @Column(name = "num_caes")
    private Integer numeroCaes;

    @Column(name = "num_gatos")
    private Integer numeroGatos;

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "tipo")
    private TipoImovel tipo;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lado_id")
    private Lado lado;

    @ManyToOne
    @JoinColumn(name = "localidade_id")
    private Localidade localidade;

}