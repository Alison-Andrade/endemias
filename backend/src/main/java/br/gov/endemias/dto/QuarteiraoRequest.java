package br.gov.endemias.dto;

import br.gov.endemias.entity.Area;
import br.gov.endemias.entity.Quarteirao;
import jakarta.validation.constraints.NotBlank;

public record QuarteiraoRequest(
    @NotBlank(message = "O numero do quarteirão é obrigatório.")
    Integer numero,
    Integer sequencia,
    Long areaId
) {
    public Quarteirao toEntity() {
        Quarteirao quarteirao = new Quarteirao();

        quarteirao.setNumero(this.numero);
        quarteirao.setSequencia(this.sequencia != null ? this.sequencia : 0);
        quarteirao.setArea(this.areaId != null ? new Area(this.areaId, null, null) : null);

        return quarteirao;
    }
}
