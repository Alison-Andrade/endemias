package br.gov.endemias.entity;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import br.gov.endemias.enums.CategoriaLocalidade;
import br.gov.endemias.enums.TipoLocalidade;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "localidade")
@Getter
@Setter
public class Localidade {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;
    private String nome;

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private CategoriaLocalidade categoria;

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private TipoLocalidade tipo;

}
