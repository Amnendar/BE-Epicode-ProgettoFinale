package it.be.energy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import it.be.energy.model.StatoFattura;

public interface StatoFatturaRepository extends JpaRepository<StatoFattura, Long> {

	Page<StatoFattura> findAll(Pageable pageable);
}
