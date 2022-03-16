package it.be.energy.repository;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import it.be.energy.model.Fattura;

public interface FatturaRepository extends JpaRepository<Fattura, Long> {

	public Page<Fattura> findAll(Pageable pageable);
	
	public Page<Fattura> findByClienteRagioneSocialeContaining(Pageable pageable, String nome);
	
	//public Page<Fattura> findByStato(Pageable pageable, StatoFattura stato);
	
	public Page<Fattura> findByData(Pageable pageable, LocalDate data);
	
	public Page<Fattura> findByAnno(Pageable pageable, Integer anno);
	
	public Page<Fattura> findByImportoBetween(Pageable pageable, BigDecimal minimo, BigDecimal massimo);
	
	
	
}
