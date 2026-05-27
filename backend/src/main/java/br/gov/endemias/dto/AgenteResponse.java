package br.gov.endemias.dto;

import br.gov.endemias.domain.entity.Agente;
import br.gov.endemias.domain.enums.TipoAgente;

public record AgenteResponse(
    Long id,
    String cpf,
    String nome,
    String email,
    String telefone,
    TipoAgente tipoAgente,
    Long supervisorId
) {
    public static AgenteResponse fromEntity(Agente agente) {
        return new AgenteResponse(
            agente.getId(),
            agente.getCpf(),
            agente.getNome(),
            agente.getEmail(),
            agente.getTelefone(),
            agente.getTipo(),
            agente.getSupervisor() != null ? agente.getSupervisor().getId() : null
        );
    }
}
