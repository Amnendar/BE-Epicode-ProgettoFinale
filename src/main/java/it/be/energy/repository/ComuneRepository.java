package it.be.energy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import it.be.energy.model.Comune;

public interface ComuneRepository extends JpaRepository<Comune, Long> {

	Page<Comune> findAll(Pageable pageable);
	
	Page<Comune> findByNomeContaining(String nome, Pageable pageable);
}
