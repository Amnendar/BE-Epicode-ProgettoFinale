package it.be.energy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import it.be.energy.model.Indirizzo;

public interface IndirizzoRepository extends JpaRepository<Indirizzo, Long> {

	public Page<Indirizzo> mostraTuttiIndirizzi(Pageable pageable);
	
}
