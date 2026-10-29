package br.com.autoinject.aps.repository;

import br.com.autoinject.aps.model.ParametroProducao;
import br.com.autoinject.aps.model.Produto;
import br.com.autoinject.aps.model.Maquina;
import br.com.autoinject.aps.model.Molde;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParametroProducaoRepository extends JpaRepository<ParametroProducao, Long> {

    Optional<ParametroProducao> findByProdutoAndMaquinaAndMolde(Produto produto, Maquina maquina, Molde molde);

    boolean existsByProdutoAndMaquinaAndMolde(Produto produto, Maquina maquina, Molde molde);

    boolean existsByProdutoAndMaquinaAndMoldeAndIdNot(Produto produto, Maquina maquina, Molde molde, Long id);
}