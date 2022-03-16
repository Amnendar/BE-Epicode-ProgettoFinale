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
	@Operation(summary = "Mostra Tutti Indirizzi", description = "Mostra tutti gli indirizzi salvati nel sistema")
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
	@Operation(summary = "Cerca Indirizzo", description = "Cerca indirizzo tramite chiave primaria")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Indirizzo> trovaIndirizzo(@PathVariable Long id){
		Indirizzo trovato = indirizzoservice.trovaIndirizzo(id);
		return new ResponseEntity<>(trovato, HttpStatus.ACCEPTED);
	}
	
	
	@PostMapping("/inserisci")
	@Operation(summary = "Inserisci Indirizzo", description = "Permette di inserire un nuovo indirizzo nel sistema")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Indirizzo> inserisciIndirizzo(@RequestBody Indirizzo Indirizzo){
		indirizzoservice.inserisciIndirizzo(Indirizzo);
		return new ResponseEntity<>(Indirizzo, HttpStatus.ACCEPTED);
	}
	
	
	@PutMapping("/modifica/{id}")
	@Operation(summary = "Aggiorna Indirizzo", description = "Permette di aggiornare un'indirizzo presente nel sistema, cercandolo per chiave primaria")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Indirizzo> aggiornaIndirizzo(@PathVariable Long id, @RequestBody Indirizzo aggiorna){
		indirizzoservice.aggiornaIndirizzo(id, aggiorna);
		return new ResponseEntity<>(aggiorna, HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/cancella/{id}")
	@Operation(summary = "Cancella Indirizzo", description = "Permette di cancellare un'indirizzo dal sistema")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> cancellaIndirizzo(@PathVariable Long id){
		indirizzoservice.cancellaIndirizzo(id);
		return new ResponseEntity<>("Indirizzo cancellato correttamente!", HttpStatus.ACCEPTED);
	}
	
	
	
}
