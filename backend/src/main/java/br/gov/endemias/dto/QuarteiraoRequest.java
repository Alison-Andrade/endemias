package br.gov.endemias.dto;

import br.gov.endemias.domain.entity.Quarteirao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record QuarteiraoRequest(
    @NotNull(message = "O numero do quarteirão é obrigatório.")
    Integer numero,
    Integer sequencia,
    @NotBlank(message = "O codigo da localidade é obrigatório.")
    String codigoLocalidade,
    Long areaId
) {
    public Quarteirao toEntity() {
        Quarteirao quarteirao = new Quarteirao();
        quarteirao.setNumero(this.numero);
        return quarteirao;
    }
}
