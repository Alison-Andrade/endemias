package br.gov.endemias.dto;

import java.util.List;

public record QuarteiraoDetalhadoResponse(
    Long id, 
    Integer numero, 
    Integer sequencia, 
    List<LadoDetalhadoResponse> lados
) {
    
}
