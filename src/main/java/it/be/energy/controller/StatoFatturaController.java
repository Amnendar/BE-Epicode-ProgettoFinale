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
import it.be.energy.model.StatoFattura;
import it.be.energy.service.StatoFatturaService;

@RestController
@RequestMapping("/statofattura")
@SecurityRequirement(name = "bearerAuth")
public class StatoFatturaController {

	@Autowired
	StatoFatturaService statoservice;
	
	
	@GetMapping("/mostra")
	@Operation(summary = "Mostra Stati", description = "Mostra tutti gli stati fattura presenti nel sistema")
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
	@Operation(summary = "Cerca Stato", description = "Cerca uno stato fattura tramite chiave primaria")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<StatoFattura> trovaStatoFattura(@PathVariable Long id){
		StatoFattura trovato = statoservice.trovaStato(id);
		return new ResponseEntity<>(trovato, HttpStatus.ACCEPTED);
	}
	
	
	@PostMapping("/inserisci")
	@Operation(summary = "Inserisci Stato", description = "Inserisci un nuovo stato fattura nel sistema")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<StatoFattura> inserisciStatoFattura(@RequestBody StatoFattura StatoFattura){
		statoservice.creaNuovoStato(StatoFattura);
		return new ResponseEntity<>(StatoFattura, HttpStatus.ACCEPTED);
	}
	
	
	@PutMapping("/modifica/{id}")
	@Operation(summary = "Modifica Stato", description = "Modifica uno stato fattura, cercandolo tramite chiave primaria")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<StatoFattura> aggiornaStatoFattura(@PathVariable Long id, @RequestBody StatoFattura aggiorna){
		StatoFattura aggiornare = statoservice.aggiornaStato(id, aggiorna);
		statoservice.aggiornaStato(id, aggiornare);
		return new ResponseEntity<>(aggiorna, HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/cancella/{id}")
	@Operation(summary = "Cancella Stato", description = "Cancella uno stato fattura, rimuovendolo dal sistema")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> cancellaStatoFattura(@PathVariable Long id){
		statoservice.cancellaStato(id);
		return new ResponseEntity<>("StatoFattura cancellato correttamente!", HttpStatus.ACCEPTED);
	}
	
	
	
}
