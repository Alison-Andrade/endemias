package br.gov.endemias.domain.entity;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import br.gov.endemias.domain.enums.TipoAgente;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "agente")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Agente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 11, columnDefinition = "CHAR(11)")
    @JdbcTypeCode(SqlTypes.CHAR)
    private String cpf;
    
    private String nome;
    
    @Column(unique = true)
    private String email;
    
    private String telefone;

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private TipoAgente tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supervisor_id")
    private Agente supervisor;
}
