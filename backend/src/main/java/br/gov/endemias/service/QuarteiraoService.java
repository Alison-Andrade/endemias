package br.gov.endemias.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.gov.endemias.domain.entity.Imovel;
import br.gov.endemias.domain.entity.Localidade;
import br.gov.endemias.domain.entity.Quarteirao;
import br.gov.endemias.dto.ImovelResponse;
import br.gov.endemias.dto.LadoDetalhadoResponse;
import br.gov.endemias.dto.LadoResponse;
import br.gov.endemias.dto.QuarteiraoDetalhadoResponse;
import br.gov.endemias.dto.QuarteiraoRequest;
import br.gov.endemias.dto.QuarteiraoResponse;
import br.gov.endemias.exception.RegraNegocioException;
import br.gov.endemias.repository.ImovelRepository;
import br.gov.endemias.repository.LadoRepository;
import br.gov.endemias.repository.QuarteiraoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuarteiraoService {
    
    private final QuarteiraoRepository quarteiraoRepository;
    private final LocalidadeService localidadeService;
    
    private final LadoRepository ladoRepository;
    private final ImovelRepository imovelRepository;

    public QuarteiraoResponse cadastrar(QuarteiraoRequest request) {

        Quarteirao quarteirao = request.toEntity();

        if (request.numero() != null) {
            boolean jaExiste = quarteiraoRepository.existsByNumeroAndLocalidadeCodigo(
                request.numero(),
                request.codigoLocalidade()
            );

            if (jaExiste) {
                int maiorSequenciaAtual = quarteiraoRepository
                        .findFirstByNumeroAndLocalidadeCodigoOrderBySequenciaDesc(
                            request.numero(),
                            request.codigoLocalidade()
                        )
                        .map(q -> q.getSequencia() != null ? q.getSequencia() : 0)
                        .orElse(0);

                quarteirao.setSequencia(maiorSequenciaAtual + 1);
            } else {
                quarteirao.setSequencia(0);
            }
        }

        if (request.codigoLocalidade() != null) {
            Localidade localidade = localidadeService.buscarEntityPorCodigo(request.codigoLocalidade());
            quarteirao.setLocalidade(localidade);
        }

        Quarteirao quarteiraoSalvo = quarteiraoRepository.save(quarteirao);
        return QuarteiraoResponse.fromEntity(quarteiraoSalvo);
    }

    public List<QuarteiraoResponse> listarPorLocalidade(String localidadeCodigo) {
        return quarteiraoRepository.findAllByLocalidadeCodigo(localidadeCodigo);
    }

    public QuarteiraoResponse buscarPorId(Long id) {
        Quarteirao quarteirao = buscarEntityById(id);
        return QuarteiraoResponse.fromEntity(quarteirao);
    }

    public QuarteiraoDetalhadoResponse buscarDetalhadoPorId(Long id) {
        Quarteirao quarteirao = buscarEntityById(id);
        
        List<LadoResponse> lados = ladoRepository.findAllByQuarteiraoId(id);
        List<Long> ladosIds = lados.stream().map(LadoResponse::id).toList();

        List<Imovel> imoveis = imovelRepository.findAllByLadoIdIn(ladosIds);
        
        List<LadoDetalhadoResponse> ladosDetalhados = lados.stream()
            .map(lado -> {
                List<ImovelResponse> imoveisDoLado = imoveis.stream()
                    .filter(imovel -> imovel.getLado().getId().equals(lado.id()))
                    .map(ImovelResponse::fromEntity)
                    .toList();
                    
                return new LadoDetalhadoResponse(
                    lado.id(),
                    lado.numero(),
                    lado.logradouro(),
                    imoveisDoLado
                );
            }).toList();

        return new QuarteiraoDetalhadoResponse(
            quarteirao.getId(),
            quarteirao.getNumero(),
            quarteirao.getSequencia(),
            ladosDetalhados
        );
    }

    public QuarteiraoResponse atualizar(Long id, QuarteiraoRequest request) {
        throw new RegraNegocioException("TO-DO");
        
        // Quarteirao quarteirao = buscarEntityById(id);
        // quarteirao.setId(id);
        // quarteirao.setNumero(request.numero());
        // quarteirao.setSequencia(request.sequencia());
        // quarteirao.setLocalidade(); 
        // quarteirao.setArea(null);
        
        // return QuarteiraoResponse.fromEntity(quarteirao);
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
