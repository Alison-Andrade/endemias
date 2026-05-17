package br.gov.endemias.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.gov.endemias.dto.ImovelRequest;
import br.gov.endemias.dto.ImovelResponse;
import br.gov.endemias.service.ImovelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/imoveis")
@RequiredArgsConstructor
public class ImovelController {
    
    private final ImovelService imovelService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ImovelResponse cadastrar(@RequestBody @Valid ImovelRequest request) {
        return imovelService.cadastrar(request);
    }

    @GetMapping
    public List<ImovelResponse> listarPorLado(Long ladoId) {
        return imovelService.listarPorLado(ladoId);
    }

    @GetMapping("{id}")
    public ImovelResponse buscarPorId(Long id) {
        return imovelService.buscarPorId(id);
    }

    @PutMapping("{id}")
    public ImovelResponse atualizar(Long id, @RequestBody @Valid ImovelRequest request) {
        return imovelService.atualizar(id, request);
    }

    @PostMapping("{id}")
    public void deletar(Long id) {
        imovelService.deletar(id);
    }


}
