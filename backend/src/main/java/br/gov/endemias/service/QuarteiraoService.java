package br.gov.endemias.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.gov.endemias.domain.entity.Localidade;
import br.gov.endemias.domain.entity.Quarteirao;
import br.gov.endemias.dto.QuarteiraoRequest;
import br.gov.endemias.dto.QuarteiraoResponse;
import br.gov.endemias.exception.RegraNegocioException;
import br.gov.endemias.repository.QuarteiraoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuarteiraoService {
    
    private final QuarteiraoRepository quarteiraoRepository;
    private final LocalidadeService localidadeService;

    public QuarteiraoResponse cadastrar(QuarteiraoRequest request) {

        Quarteirao quarteirao = request.toEntity();

        if (request.numero() != null) {
            boolean jaExiste = quarteiraoRepository.existsByNumeroAndLocalidadeId(
                request.numero(),
                request.localidadeId()
            );

            if (jaExiste) {
                int maiorSequenciaAtual = quarteiraoRepository
                        .findFirstByNumeroAndLocalidadeIdOrderBySequenciaDesc(
                            request.numero(),
                            request.localidadeId()
                        )
                        .map(q -> q.getSequencia() != null ? q.getSequencia() : 0)
                        .orElse(0);

                quarteirao.setSequencia(maiorSequenciaAtual + 1);
            } else {
                quarteirao.setSequencia(0);
            }
        }

        if (request.localidadeId() != null) {
            Localidade localidade = localidadeService.buscarEntityPorId(request.localidadeId());
            quarteirao.setLocalidade(localidade);
        }

        Quarteirao quarteiraoSalvo = quarteiraoRepository.save(quarteirao);
        return QuarteiraoResponse.fromEntity(quarteiraoSalvo);
    }

    public List<QuarteiraoResponse> listarPorLocalidade(Long localidadeId) {
        return quarteiraoRepository.findAllByLocalidadeId(localidadeId);
    }

    public QuarteiraoResponse buscarPorId(Long id) {
        Quarteirao quarteirao = buscarEntityById(id);
        return QuarteiraoResponse.fromEntity(quarteirao);
    }

    // public QuarteiraoDetalhadoResponse buscarDetalhadoPorId(Long id) {
    //     Quarteirao quarteirao = buscarEntityById(id);
        
    //     List<LadoResponse> lados = ladoRepository.findAllByQuarteiraoId(id);
    //     List<Long> ladosIds = lados.stream().map(LadoResponse::id).toList();

    //     List<Imovel> imoveis = imovelRepository.findAllByLadoIdIn(ladosIds);
        
    //     List<LadoDetalhadoResponse> ladosDetalhados = lados.stream()
    //         .map(lado -> {
    //             List<ImovelResponse> imoveisDoLado = imoveis.stream()
    //                 .filter(imovel -> imovel.getLado().getId().equals(lado.id()))
    //                 .map(ImovelResponse::fromEntity)
    //                 .toList();
                    
    //             return new LadoDetalhadoResponse(
    //                 lado.id(),
    //                 lado.numero(),
    //                 lado.logradouro(),
    //                 imoveisDoLado
    //             );
    //         }).toList();

    //     return new QuarteiraoDetalhadoResponse(
    //         quarteirao.getId(),
    //         quarteirao.getNumero(),
    //         quarteirao.getSequencia(),
    //         ladosDetalhados
    //     );
    // }

    public QuarteiraoResponse atualizar(Long id, QuarteiraoRequest request) {
        throw new RuntimeException("TO-DO");
    }

    @Transactional
    public void deletar(Long id) {
        Quarteirao quarteirao = buscarEntityById(id);

        Integer numero = quarteirao.getNumero();
        Integer sequencia = quarteirao.getSequencia();
        Long idLocalidade = quarteirao.getLocalidade().getId();

        quarteiraoRepository.delete(quarteirao);
        quarteiraoRepository.reordenarSequenciaLocalidade(numero, idLocalidade, sequencia);
    }

    public Quarteirao buscarEntityById(Long id) {
        return quarteiraoRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Quarteirão não encontrado"));
    }

}
