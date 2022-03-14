package it.be.energy.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import it.be.energy.model.Cliente;
import it.be.energy.service.ClienteService;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

	@Autowired
	ClienteService clienteservice;
	
	
	@GetMapping("/mostra")
	@Operation
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Page<Cliente>> mostraClienti(Pageable pageable){
		Page<Cliente> found = clienteservice.findAllClienti(pageable);
		if(found.isEmpty()) {
			return new ResponseEntity<>(found, HttpStatus.NO_CONTENT);
		}
		else{
			return new ResponseEntity<>(found, HttpStatus.ACCEPTED);
		}
	}
	
	
	@PostMapping("/inserisci")
	@Operation
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Cliente> inserisciCliente(@RequestBody Cliente cliente){
		clienteservice.inserisciCliente(cliente);
		return new ResponseEntity<>(cliente, HttpStatus.ACCEPTED);
	}
	
	
	@PutMapping("/modifica/{id}")
	@Operation
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Cliente> aggiornaClienti(@PathVariable Long id, @RequestBody Cliente aggiorna){
		Cliente aggiornare = clienteservice.getClienteById(id);
		clienteservice.updateCliente(id, aggiornare);
		return new ResponseEntity<>(aggiorna, HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/cancella/{id}")
	@Operation
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> cancellaCliente(@PathVariable Long id){
		clienteservice.cancellaClienteById(id);
		return new ResponseEntity<>("Cliente cancellato correttamente!", HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/cerca/{id}")
	@Operation
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Cliente> trovaClienteById(@PathVariable Long id){
		Cliente trovato = clienteservice.getClienteById(id);
		return new ResponseEntity<>(trovato, HttpStatus.ACCEPTED);
	}
	
}
