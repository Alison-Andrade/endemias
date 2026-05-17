package br.gov.endemias.service;

import br.gov.endemias.exception.RegraNegocioException;

import java.util.List;

import org.springframework.stereotype.Service;

import br.gov.endemias.dto.LadoRequest;
import br.gov.endemias.dto.LadoResponse;
import br.gov.endemias.entity.Lado;
import br.gov.endemias.repository.LadoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LadoService {
    
    private final LadoRepository ladoRepository;
    private final QuarteiraoService quarteiraoService;

    @Transactional
    public LadoResponse cadastrar(LadoRequest request) {

        Lado lado = request.toEntity();

        lado.setQuarteirao(quarteiraoService.buscarEntityById(request.quarteiraoId()));

        if (request.numero() != null) {
            ladoRepository.abrirEspacoParaNovoLado(request.quarteiraoId(), request.numero());
            lado.setNumero(request.numero());
        } else {
            Integer ultimoLado = ladoRepository.findFirstByQuarteiraoIdOrderByNumeroDesc(request.quarteiraoId()).map(Lado::getNumero).orElse(0);

            lado.setNumero(ultimoLado + 1);
        }

        if (request.logradouro() == null) {
            lado.setLogradouro("Projetada");
        }

        return LadoResponse.fromEntity(ladoRepository.save(lado));
    }

    public List<LadoResponse> listarPorQuarteirao(Long quarteiraoId) {
        return ladoRepository.findAllByQuarteiraoId(quarteiraoId);
    }

    public LadoResponse atualizar(Long id, LadoRequest request) {
        Lado lado = request.toEntity();
        lado.setId(id);

        // TO-DO

        return LadoResponse.fromEntity(ladoRepository.save(lado));
    }

    public void deletar(Long id) {
        ladoRepository.deleteById(id);
    }

    public Lado buscarEntityPorId(Long id) {
        return ladoRepository.findById(id)
            .orElseThrow(() -> new RegraNegocioException("Lado nao encontrado"));
    }

}

