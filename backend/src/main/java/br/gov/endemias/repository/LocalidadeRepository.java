package br.gov.endemias.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.endemias.entity.Localidade;


public interface LocalidadeRepository extends JpaRepository<Localidade, Long>{
    boolean existsByCodigo(String codigo);

    Localidade findByCodigo(String codigo);
}
