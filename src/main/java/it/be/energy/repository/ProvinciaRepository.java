package it.be.energy.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import it.be.energy.model.Provincia;

public interface ProvinciaRepository extends JpaRepository<Provincia, Long> {

	Optional<Provincia> findByNomeLike(String nome);
	
	Optional<Provincia> findByCodiceProvincia(Long codice);
	
	Page<Provincia> findAll(Pageable pageable);
}
