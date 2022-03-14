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
import it.be.energy.model.Fattura;
import it.be.energy.service.FatturaService;

@RestController
@RequestMapping("/fattura")
public class FatturaController {

	@Autowired
	FatturaService fatturaservice;
	
	@GetMapping("/mostra")
	@Operation
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Page<Fattura>> trovaTutteLeFatture(Pageable pageable){
		Page<Fattura> tutte = fatturaservice.mostraFatture(pageable);
		if(tutte.hasContent()) {
			return new ResponseEntity<>(tutte, HttpStatus.ACCEPTED);
		}
		else {
			return new ResponseEntity<>(tutte, HttpStatus.NO_CONTENT);
		}
	}
	
	
	@GetMapping("/cerca/{id}")
	@Operation
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Fattura> trovaFattura(Long id){
		return new ResponseEntity<>(fatturaservice.getFatturaById(id), HttpStatus.ACCEPTED);
	}
	
	
	@PostMapping("/aggiungi")
	@Operation
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Fattura> inserisciFattura(Fattura fattura){
		fatturaservice.inserisciFattura(fattura);
		return new ResponseEntity<>(fattura, HttpStatus.ACCEPTED);
	}
	
	
	@PutMapping("/aggiorna/{id}")
	@Operation
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Fattura> aggiornaFattura(@PathVariable Long id, @RequestBody Fattura aggiorna){
		Fattura aggiornare = fatturaservice.getFatturaById(id);
		fatturaservice.aggiornaFattura(id, aggiornare);
		return new ResponseEntity<>(aggiorna, HttpStatus.ACCEPTED);
	}
	
	
	@DeleteMapping("/cancella/{id}")
	@Operation
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> cancellaCliente(@PathVariable Long id){
		fatturaservice.cancellaFattura(id);
		return new ResponseEntity<>("Fattura cancellata correttamente!", HttpStatus.ACCEPTED);
	}
	
	
}
