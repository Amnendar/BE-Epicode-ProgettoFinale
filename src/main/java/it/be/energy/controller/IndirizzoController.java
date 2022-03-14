package it.be.energy.controller;

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
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.be.energy.model.Indirizzo;
import it.be.energy.service.IndirizzoService;

@RestController
@RequestMapping("/indirizzo")
@SecurityRequirement(name = "bearerAuth")
public class IndirizzoController {

	@Autowired
	IndirizzoService indirizzoservice;
	
	@GetMapping("/mostra")
	@Operation()
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Page<Indirizzo>> mostraIndirizzi(Pageable pageable){
		Page<Indirizzo> all = indirizzoservice.mostraTuttiIndirizzi(pageable);
		if(all.isEmpty()) {
			return new ResponseEntity<>(all, HttpStatus.NO_CONTENT);
		}
		else {
			return new ResponseEntity<>(all, HttpStatus.ACCEPTED);
		}
	}
	
	@GetMapping("/cerca/{id}")
	@Operation()
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Indirizzo> trovaIndirizzo(@PathVariable Long id){
		Indirizzo trovato = indirizzoservice.trovaIndirizzo(id);
		return new ResponseEntity<>(trovato, HttpStatus.ACCEPTED);
	}
	
	
	@PostMapping("/inserisci")
	@Operation
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Indirizzo> inserisciIndirizzo(@RequestBody Indirizzo Indirizzo){
		indirizzoservice.inserisciIndirizzo(Indirizzo);
		return new ResponseEntity<>(Indirizzo, HttpStatus.ACCEPTED);
	}
	
	
	@PutMapping("/modifica/{id}")
	@Operation
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Indirizzo> aggiornaIndirizzo(@PathVariable Long id, @RequestBody Indirizzo aggiorna){
		Indirizzo aggiornare = indirizzoservice.trovaIndirizzo(id);
		indirizzoservice.aggiornaIndirizzo(id, aggiornare);
		return new ResponseEntity<>(aggiorna, HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/cancella/{id}")
	@Operation
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> cancellaIndirizzo(@PathVariable Long id){
		indirizzoservice.cancellaIndirizzo(id);
		return new ResponseEntity<>("Indirizzo cancellato correttamente!", HttpStatus.ACCEPTED);
	}
	
	
	
}
