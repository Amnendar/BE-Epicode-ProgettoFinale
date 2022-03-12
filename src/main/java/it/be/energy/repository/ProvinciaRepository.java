package it.be.energy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.be.energy.model.Provincia;

public interface ProvinciaRepository extends JpaRepository<Provincia, Long> {

}
