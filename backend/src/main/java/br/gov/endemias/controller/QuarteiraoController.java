package br.gov.endemias.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.endemias.dto.QuarteiraoRequest;
import br.gov.endemias.dto.QuarteiraoResponse;
import br.gov.endemias.service.QuarteiraoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/quarteiroes")
@RequiredArgsConstructor
public class QuarteiraoController {
    
    private final QuarteiraoService quarteiraoService;

    public QuarteiraoResponse cadastrar(@RequestBody @Valid QuarteiraoRequest request) {
        return quarteiraoService.cadastrar(request);
    }

}
