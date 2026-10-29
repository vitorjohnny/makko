package br.com.autoinject.aps.repository;

import br.com.autoinject.aps.model.ApontamentoProducao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApontamentoProducaoRepository extends JpaRepository<ApontamentoProducao, Long> {

    Optional<ApontamentoProducao> findByOrdem(String ordem);

    boolean existsByOrdem(String ordem);

    boolean existsByOrdemAndIdNot(String ordem, Long id);
}