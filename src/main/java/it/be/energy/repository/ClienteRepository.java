package it.be.energy.repository;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import it.be.energy.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	public Page<Cliente> findAll(Pageable pageable);
	
	public Page<Cliente> findAllByOrderByRagioneSocialeAsc(Pageable pageable);
	
	public Page<Cliente> findAllByOrderByFatturatoAnnualeDesc(Pageable pageable);
	
	public Page<Cliente> findAllByOrderByDataInserimento(Pageable pageable);
	
	public Page<Cliente> findAllByOrderByDataUltimoContatto(Pageable pageable);
	
	public Page<Cliente> findAllByOrderBySedeLegaleComuneProvincia(Pageable pageable);
	
	//filtri
	
	public Page<Cliente> findByFatturatoAnnualeGreaterThanEqual(BigDecimal fatturato, Pageable pageable);
	
	public Page<Cliente> findByFatturatoAnnualeLessThanEqual(BigDecimal fatturato, Pageable pageable);
	
	public Page<Cliente> findByFatturatoAnnualeBetween(BigDecimal iniziale, BigDecimal finale, Pageable pageable );
	
	
	public Page<Cliente> findByDataInserimentoGreaterThanEqual(LocalDate data, Pageable pageable);
	
	public Page<Cliente> findByDataInserimentoLessThanEqual(LocalDate data, Pageable pageable);
	
	public Page<Cliente> findByDataInserimentoBetween(LocalDate inizio, LocalDate fine, Pageable pageable);
	
	
	public Page<Cliente> findByDataUltimoContattoGreaterThanEqual(LocalDate data, Pageable pageable);
	
	public Page<Cliente> findByDataUltimoContattoLessThanEqual(LocalDate data, Pageable pageable);
	
	public Page<Cliente> findByDataUltimoContattoBetween(LocalDate inizio, LocalDate fine, Pageable pageable);
	
	
	public Page<Cliente> findByRagioneSocialeLike(String nome, Pageable pageable);
	
}
