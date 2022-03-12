package it.be.energy.repository;

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
}
