package br.gov.endemias.dto;

import br.gov.endemias.entity.Imovel;
import br.gov.endemias.enums.TipoImovel;

public record ImovelRequest(
    String placa,
    Integer sequencia,
    Integer numeroSms,
    Integer numeroResidentes,
    Integer numeroCaes,
    Integer numeroGatos,
    TipoImovel tipo,
    Long ladoId,
    Long localidadeId
) {
    public Imovel toEntity() {
        Imovel imovel = new Imovel();
        imovel.setPlaca(placa);
        imovel.setSequencia(sequencia);
        imovel.setNumeroSms(numeroSms);
        imovel.setNumeroResidentes(numeroResidentes);
        imovel.setNumeroCaes(numeroCaes);
        imovel.setNumeroGatos(numeroGatos);
        imovel.setTipo(tipo);
        return imovel;
    }
}
