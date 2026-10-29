package br.com.autoinject.aps.repository;

import br.com.autoinject.aps.model.OrdemProducao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrdemProducaoRepository extends JpaRepository<OrdemProducao, Long> {

    Optional<OrdemProducao> findByNumero(String numero);

    boolean existsByNumero(String numero);

    boolean existsByNumeroAndIdNot(String numero, Long id);
}