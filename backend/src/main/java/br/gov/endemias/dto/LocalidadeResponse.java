package br.gov.endemias.dto;

import br.gov.endemias.entity.Localidade;
import br.gov.endemias.enums.CategoriaLocalidade;
import br.gov.endemias.enums.TipoLocalidade;

public record LocalidadeResponse(
    Long id,
    String codigo,
    String nome,
    CategoriaLocalidade categoria,
    TipoLocalidade tipo
) {
    public static LocalidadeResponse fromEntity(Localidade localidade) {
        return new LocalidadeResponse(
            localidade.getId(),
            localidade.getCodigo(),
            localidade.getNome(),
            localidade.getCategoria(),
            localidade.getTipo()
        );
    }
}
