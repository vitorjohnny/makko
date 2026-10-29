package br.com.autoinject.aps.repository;

import br.com.autoinject.aps.model.ItemPlanejamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemPlanejamentoRepository extends JpaRepository<ItemPlanejamento, Long> {

    Optional<ItemPlanejamento> findByPlanejamentoAndAtivoTrue(String planejamento);

    boolean existsByPlanejamentoAndOrdemProducaoAndMaquinaAndMolde(
            String planejamento, String ordemProducao, String maquina, String molde);

    boolean existsByOrdemProducaoAndMaquinaAndMolde(String ordemProducao, String maquina, String molde);
}