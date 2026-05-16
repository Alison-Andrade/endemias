package br.gov.endemias.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.gov.endemias.dto.AgenteRequest;
import br.gov.endemias.dto.AgenteResponse;
import br.gov.endemias.entity.Agente;
import br.gov.endemias.enums.TipoAgente;
import br.gov.endemias.repository.AgenteRepository;

@Service
public class AgenteService {
    
    private final AgenteRepository repository;

    public AgenteService(AgenteRepository repository) {
        this.repository = repository;
    }

    public AgenteResponse cadastrar(AgenteRequest request) {
        if (request.cpf() != null && repository.existsByCpf(request.cpf())) {
            throw new RuntimeException("Já existe agente cadastrado com esse cpf.");
        }

        Agente agente = request.toEntity();

        if (request.supervisorId() != null) {
            Agente supervisor = repository.findById(request.supervisorId())
                .orElseThrow(() -> new RuntimeException("Supervisor não encontrado."));

            if (supervisor.getTipo() != TipoAgente.SUPERVISOR) {
                throw new RuntimeException("O agente informado como supervisor não é SUPERVISOR.");
            }

            agente.setSupervisor(supervisor);
        }

        Agente agenteSalvo = repository.save(agente);

        return AgenteResponse.fromEntity(agenteSalvo);
    }

    public Page<AgenteResponse> listar(Pageable pageable) {
        return repository.findAll(pageable).map(AgenteResponse::fromEntity);
    }

    public AgenteResponse buscarPorId(Long id) {
        Agente agente = buscarEntityPorId(id);
        return AgenteResponse.fromEntity(agente);   
    }

    public AgenteResponse atualizar(Long id, AgenteRequest request) {
        Agente agente = buscarEntityPorId(id);

        request.preencher(agente);
        Agente agenteAtualizado = repository.save(agente);
        return AgenteResponse.fromEntity(agenteAtualizado);
    }

    public void delete(Long id) {
        Agente agente = buscarEntityPorId(id);
        repository.delete(agente);
    }

    private Agente buscarEntityPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agente não encontrado"));
    }

}
