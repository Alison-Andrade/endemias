package br.gov.endemias.service;

import org.springframework.stereotype.Service;

import br.gov.endemias.dto.QuarteiraoRequest;
import br.gov.endemias.repository.QuarteiraoRepository;

@Service
public class QuarteiraoService {
    
    private final QuarteiraoRepository quarteiraoRepository;

    public QuarteiraoService(QuarteiraoRepository quarteiraoRepository) {
        this.quarteiraoRepository = quarteiraoRepository;
    }

    public QuarteiraoService cadastrar(QuarteiraoRequest request) {
        throw new RuntimeException("TO-DO");
    }

    

}
