package br.gov.endemias.dto;

import br.gov.endemias.entity.Agente;
import br.gov.endemias.enums.TipoAgente;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AgenteRequest(
    @NotBlank(message = "O nome do agente é obrigatório")
    @Size(max = 150, message = "O nome do agente deve ter no máximo 150 caracteres")
    String nome,
    @NotBlank(message = "O CPF do agente é obrigatório")
    @Size(min = 11, max = 11, message = "O CPF do agente deve conter exatamente 11 caracteres")
    String cpf,
    @Size(max = 20, message = "O telefone do agente deve ter no máximo 20 caracteres")
    String telefone,
    @NotBlank(message = "O email do agente é obrigatório")
    @Size(max = 150, message = "O email do agente deve ter no máximo 150 caracteres")
    String email,
    @NotNull
    TipoAgente tipo,
    Long supervisorId
) {

    public Agente toEntity() {
        Agente agente = new Agente();
        preencher(agente);
        return agente;
    }

    public void preencher(Agente agente) {
        agente.setNome(this.nome);
        agente.setCpf(this.cpf);
        agente.setTelefone(this.telefone);
        agente.setEmail(this.email);
        agente.setTipo(this.tipo);
        agente.setSupervisor(this.supervisorId != null ? new Agente(this.supervisorId, null, null, null, null, null, null) : null);
    }
}
