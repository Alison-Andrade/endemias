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

        agente.setSupervisor(buscarEValidarSupervisor(request.supervisorId()));

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

        if (request.cpf() != null && !request.cpf().equals(agente.getCpf()) && repository.existsByCpf(request.cpf())) {
            throw new RuntimeException("Já existe outro agente cadastrado com esse cpf.");
        }

        TipoAgente tipoAtual = agente.getTipo();

        request.preencher(agente);

        if (request.tipo() == null) {
            agente.setTipo(tipoAtual);
        }

        agente.setSupervisor(buscarEValidarSupervisor(request.supervisorId()));
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

    private Agente buscarEValidarSupervisor(Long supervisorId) {

        if (supervisorId == null) {
            return null;
        }

        Agente supervisor = repository.findById(supervisorId)
        .orElseThrow(() -> new RuntimeException("Supervisor não encontrado."));

        if (supervisor.getTipo() != TipoAgente.SUPERVISOR) {
            throw new RuntimeException("O agente informado como supervisor não é SUPERVISOR.");
        }

        return supervisor;
    }
}
