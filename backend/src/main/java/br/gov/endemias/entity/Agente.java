package br.gov.endemias.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "agentes")
@Table(name = "agentes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Agente {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true, nullable = false, length = 11)
    private String cpf;
    @Column(unique = true, nullable = false, length = 100)
    private String nome;
    @Column(unique = true, nullable = false, length = 100)
    private String email;
    @Column(unique = true, length = 15)
    private String telefone;
}
