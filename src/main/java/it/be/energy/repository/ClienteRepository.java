package it.be.energy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.be.energy.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
