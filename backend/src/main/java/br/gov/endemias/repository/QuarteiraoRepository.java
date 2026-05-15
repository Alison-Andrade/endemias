package br.gov.endemias.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.endemias.entity.Quarteirao;

public interface QuarteiraoRepository extends JpaRepository<Quarteirao, Long> {
    
}
