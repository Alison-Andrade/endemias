package br.gov.endemias.dto;

import java.util.List;

public record LadoDetalhadoResponse(
    Long id, 
    Integer numero, 
    String logradouro, 
    List<ImovelResponse> imoveis
) {
    
}
