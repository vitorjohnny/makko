
package br.com.autoinject.aps.repository;

import br.com.autoinject.aps.model.ItemPlanejamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemPlanejamentoRepository extends JpaRepository<ItemPlanejamento, Long> {

    List<ItemPlanejamento> findByPlanejamento_IdAndAtivoTrue(Long planejamentoId);

    List<ItemPlanejamento> findByAtivoTrue();

    boolean existsByPlanejamento_IdAndOrdemProducao_IdAndMaquina_IdAndMolde_Id(
            Long planejamentoId,
            Long ordemProducaoId,
            Long maquinaId,
            Long moldeId
    );

    boolean existsByPlanejamento_IdAndOrdemProducao_IdAndMaquina_IdAndMolde_IdAndIdNot(
            Long planejamentoId,
            Long ordemProducaoId,
            Long maquinaId,
            Long moldeId,
            Long id
    );
}
