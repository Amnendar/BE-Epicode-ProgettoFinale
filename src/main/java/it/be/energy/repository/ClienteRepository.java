package it.be.energy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import it.be.energy.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	public Page<Cliente> getAllClienti(Pageable pageable);
	
	public Page<Cliente> findAllOrderByRagioneSocialeAsc(Pageable pageable);
	
	public Page<Cliente> findAllOrderByFatturatoAnnuleDesc(Pageable pageable);
	
	public Page<Cliente> findAllOrderByDataInserimento(Pageable pageable);
	
	public Page<Cliente> findAllOrderByDataUltimoContatto(Pageable pageable);
}
