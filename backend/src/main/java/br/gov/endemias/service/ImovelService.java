package br.gov.endemias.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.gov.endemias.dto.ImovelRequest;
import br.gov.endemias.dto.ImovelResponse;
import br.gov.endemias.entity.Imovel;
import br.gov.endemias.exception.RegraNegocioException;
import br.gov.endemias.repository.ImovelRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImovelService {
    
    private final ImovelRepository imovelRepository;
    private final LadoService ladoService;
    private final LocalidadeService localidadeService;

    public ImovelResponse cadastrar(ImovelRequest request) {

        if ((request.ladoId() == null) ^ (request.localidadeId() == null)) {
            throw new RegraNegocioException("O imovel precisa ter um lado ou uma localidade");
        }
        
        Imovel imovel = request.toEntity();

        if (request.ladoId() != null) {
            imovel.setLado(ladoService.buscarEntityPorId(request.ladoId()));
        } else {
            imovel.setLocalidade(localidadeService.buscarEntityPorId(request.localidadeId()));
        }

        if (request.placa() == null && request.numeroSms() == null) {

            Long idBusca = (request.ladoId() != null) ? request.ladoId() : request.localidadeId();

            Integer numeroSms = imovelRepository
                    .findFirstByNumeroSmsOrderByNumeroSmsDesc(idBusca)
                    .map(Imovel::getNumeroSms)
                    .orElse(0) + 1;

            imovel.setNumeroSms(numeroSms);
        }

        if (request.placa() != null) {
            imovel.setPlaca(request.placa());

            boolean jaExistePlaca = imovelRepository.existsByPlacaAndLadoId(request.placa(), request.ladoId());
            
            Integer sequencia = 0;
            if (jaExistePlaca) {
                sequencia = imovelRepository
                    .findFirstByPlacaAndLadoIdOrderBySequenciaDesc(request.placa(), request.ladoId())
                    .map(Imovel::getSequencia)
                    .orElse(0) + 1;
                imovel.setSequencia(sequencia);
            }
            imovel.setSequencia(sequencia);
        }
        return ImovelResponse.fromEntity(imovelRepository.save(imovel));
    }

    public List<ImovelResponse> listarPorLado(Long ladoId) {
        return imovelRepository.findAllByLadoId(ladoId);
    }

    public ImovelResponse buscarPorId(Long id) {
        Imovel imovel = buscarEntityPorId(id);
        return ImovelResponse.fromEntity(imovel);
    }

    public ImovelResponse atualizar(Long id, ImovelRequest request) {
        throw new RegraNegocioException("TO-DO");
    }

    public void deletar(Long id) {
        imovelRepository.deleteById(id);
    }

    public Imovel buscarEntityPorId(Long id) {
        return imovelRepository
            .findById(id)
            .orElseThrow(
                () -> new RegraNegocioException("Imovel não encontrado")
            );
    }


}
