package br.gov.endemias.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.endemias.entity.Quarteirao;

public interface QuarteiraoRepository extends JpaRepository<Quarteirao, Long> {

    boolean existsByNumeroAndLocalidadeCodigo(Integer numero, String codigoLocalidade);
    
    Optional<Quarteirao> findFirstByNumeroAndLocalidadeCodigoOrderBySequenciaDesc(Integer numero, String codigoLocalidade);

    List<Quarteirao> findAllByLocalidadeCodigo(String codigoLocalidade);
}
