package br.gov.endemias.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.gov.endemias.dto.QuarteiraoResponse;
import br.gov.endemias.entity.Quarteirao;

public interface QuarteiraoRepository extends JpaRepository<Quarteirao, Long> {

    boolean existsByNumeroAndLocalidadeCodigo(Integer numero, String codigoLocalidade);
    
    Optional<Quarteirao> findFirstByNumeroAndLocalidadeCodigoOrderBySequenciaDesc(Integer numero, String codigoLocalidade);

    List<QuarteiraoResponse> findAllByLocalidadeCodigo(String codigoLocalidade);

    @Modifying
    @Query(
        "UPDATE Quarteirao q SET q.sequencia = q.sequencia - 1 " +
        "WHERE q.numero = :numero " + 
        "AND q.localidade.id = :localidadeId " +
        "AND q.sequencia > :sequenciaDeletada"
    )
    void reordenarSequenciaLocalidade(Integer numero, Long localidadeId, Integer sequenciaDeletada);
}
