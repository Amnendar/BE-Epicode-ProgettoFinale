package it.be.energy.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.be.energy.security.model.Role;
import it.be.energy.security.model.Roles;



public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByRoleName(Roles role);
}
