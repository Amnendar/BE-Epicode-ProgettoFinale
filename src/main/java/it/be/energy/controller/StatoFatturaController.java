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
import it.be.energy.model.StatoFattura;
import it.be.energy.service.StatoFatturaService;

@RestController
@RequestMapping("/statofattura")
public class StatoFatturaController {

	@Autowired
	StatoFatturaService statoservice;
	
	
	@GetMapping("/mostra")
	@Operation()
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Page<StatoFattura>> mostraStati(Pageable pageable){
		Page<StatoFattura> all = statoservice.mostraStati(pageable);
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
	public ResponseEntity<StatoFattura> trovaStatoFattura(@PathVariable Long id){
		StatoFattura trovato = statoservice.trovaStato(id);
		return new ResponseEntity<>(trovato, HttpStatus.ACCEPTED);
	}
	
	
	@PostMapping("/inserisci")
	@Operation
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<StatoFattura> inserisciStatoFattura(@RequestBody StatoFattura StatoFattura){
		statoservice.creaNuovoStato(StatoFattura);
		return new ResponseEntity<>(StatoFattura, HttpStatus.ACCEPTED);
	}
	
	
	@PutMapping("/modifica/{id}")
	@Operation
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<StatoFattura> aggiornaStatoFattura(@PathVariable Long id, @RequestBody StatoFattura aggiorna){
		StatoFattura aggiornare = statoservice.aggiornaStato(id, aggiorna);
		statoservice.aggiornaStato(id, aggiornare);
		return new ResponseEntity<>(aggiorna, HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/cancella/{id}")
	@Operation
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> cancellaStatoFattura(@PathVariable Long id){
		statoservice.cancellaStato(id);
		return new ResponseEntity<>("StatoFattura cancellato correttamente!", HttpStatus.ACCEPTED);
	}
	
	
	
}
