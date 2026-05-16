package br.gov.endemias.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.gov.endemias.dto.QuarteiraoRequest;
import br.gov.endemias.dto.QuarteiraoResponse;
import br.gov.endemias.entity.Localidade;
import br.gov.endemias.entity.Quarteirao;
import br.gov.endemias.repository.QuarteiraoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuarteiraoService {
    
    private final QuarteiraoRepository quarteiraoRepository;
    private final LocalidadeService localidadeService;

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
            }
        }

        if (request.codigoLocalidade() != null) {
            Localidade localidade = localidadeService.buscarEntityPorCodigo(request.codigoLocalidade());
            quarteirao.setLocalidade(localidade);
        }

        Quarteirao quarteiraoSalvo = quarteiraoRepository.save(quarteirao);
        return QuarteiraoResponse.fromEntity(quarteiraoSalvo);
    }

    public List<Quarteirao> listarPorLocalidade(String localidadeCodigo) {
        return quarteiraoRepository.findAllByLocalidadeCodigo(localidadeCodigo);
    }

    public QuarteiraoResponse buscarPorId(Long id) {
        Quarteirao quarteirao = buscarEntityById(id);
        return QuarteiraoResponse.fromEntity(quarteirao);
    }

    public QuarteiraoResponse atualizar(Long id, QuarteiraoRequest request) {
        throw new RuntimeException("TO-DO");
        
        // Quarteirao quarteirao = buscarEntityById(id);
        // quarteirao.setId(id);
        // quarteirao.setNumero(request.numero());
        // quarteirao.setSequencia(request.sequencia());
        // quarteirao.setLocalidade(); 
        // quarteirao.setArea(null);
        
        // return QuarteiraoResponse.fromEntity(quarteirao);
    }

    public void deletar(Long id) {
        Quarteirao quarteirao = buscarEntityById(id);
        quarteiraoRepository.delete(quarteirao);
    }



    private Quarteirao buscarEntityById(Long id) {
        return quarteiraoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quarteirão não encontrado"));
    }

    

}
