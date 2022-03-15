package it.be.energy.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
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
import it.be.energy.model.Fattura;
import it.be.energy.service.FatturaService;

@RestController
@RequestMapping("/fattura")
@SecurityRequirement(name = "bearerAuth")
public class FatturaController {

	@Autowired
	FatturaService fatturaservice;
	
	@GetMapping("/mostra")
	@Operation(summary = "Mostra Tutte Fatture", description = "Mostra tutte le fatture presenti nel sistema")
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
	@Operation(summary = "Cerca Fattura per id", description = "Cerca una fattura per chiave primaria")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Fattura> trovaFattura(@PathVariable Long id){
		return new ResponseEntity<>(fatturaservice.getFatturaById(id), HttpStatus.ACCEPTED);
	}
	
	
	@PostMapping("/inserisci")
	@Operation(summary = "Inserisci Fattura", description = "Permette di inserire una nuova fattura  nel sistema")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Fattura> inserisciFattura(@RequestBody Fattura fattura){
		fatturaservice.inserisciFattura(fattura);
		return new ResponseEntity<>(fattura, HttpStatus.ACCEPTED);
	}
	
	
	@PutMapping("/modifica/{id}")
	@Operation(summary = "Aggiorna Fattura", description = "Permette di aggiornare i dati di una fattura gia presente nel sistema")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Fattura> aggiornaFattura(@PathVariable Long id, @RequestBody Fattura aggiorna){
		fatturaservice.aggiornaFattura(id, aggiorna);
		return new ResponseEntity<>(aggiorna, HttpStatus.ACCEPTED);
	}
	
	
	@DeleteMapping("/cancella/{id}")
	@Operation(summary = "Cancella Fattura", description = "Permette di cancellare una fattura dal sistema")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> cancellaCliente(@PathVariable Long id){
		fatturaservice.cancellaFattura(id);
		return new ResponseEntity<>("Fattura cancellata correttamente!", HttpStatus.ACCEPTED);
	}
	
	//FIND CUSTOM
	
	@GetMapping("/cercaragionesociale/{nome}")
	@Operation(summary = "Cerca per Ragione Sociale", description = "Mostra tutte le fatture tramite la ragione sociale di un cliente")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Page<Fattura>> cercaPerRagioneSocialeCliente(@PathVariable String nome, Pageable pageable){
		Page<Fattura> found = fatturaservice.findByClienteRagioneSocialeLike(pageable, nome);
		if(found.hasContent()) {
			return new ResponseEntity<>(found, HttpStatus.ACCEPTED);
		}
		else {
			return new ResponseEntity<>(found, HttpStatus.NO_CONTENT);
		}
	}
	
	
	@GetMapping("/cercaperstato/{idstato}")
	@Operation(summary = "Cerca per Stato Fattura", description = "Mostra tutte le fatture con lo stato passato in input")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<List<Fattura>> trovaPerStato(@PathVariable Long idstato){
		List<Fattura> found = fatturaservice.findyStato(idstato);
		return new ResponseEntity<>(found, HttpStatus.ACCEPTED);
	}
	
	//TODO mettere a localdate
	@GetMapping("/cercaperdata/{data}")
	@Operation(summary = "Cerca per Data Fattura", description = "Permette di cercare le fatture emesse in una determinata data")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Page<Fattura>> trovaPerData(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date data, Pageable pageable){
		Page<Fattura> found = fatturaservice.findByData(pageable, data);
		if(found.hasContent()) {
			return new ResponseEntity<>(found, HttpStatus.ACCEPTED);
		}
		else {
			return new ResponseEntity<>(found, HttpStatus.NO_CONTENT);
		}
	}
	
	
	@GetMapping("/cercaperanno/{anno}")
	@Operation(summary = "Cerca per Anno Fattura", description = "Mostra le fatture emesse in un determinato anno")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Page<Fattura>> trovaPerAnno(@PathVariable Integer anno, Pageable pageable){
		Page<Fattura> found = fatturaservice.findByAnno(pageable, anno);
		if(found.hasContent()) {
			return new ResponseEntity<>(found, HttpStatus.ACCEPTED);
		}
		else {
			return new ResponseEntity<>(found, HttpStatus.NO_CONTENT);
		}
	}
	
	
	@GetMapping("/cercaperimporti/{min}/{max}")
	@Operation(summary = "Cerca per Importo Fattura", description = "Mostra le fatture in un  determinato range di importi")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Page<Fattura>> trovaPerImporti(@PathVariable BigDecimal min, @PathVariable BigDecimal max, Pageable pageable){
		Page<Fattura> found = fatturaservice.findByImportoBetween(pageable, min, max);
		if(found.hasContent()) {
			return new ResponseEntity<>(found, HttpStatus.ACCEPTED);
		}
		else {
			return new ResponseEntity<>(found, HttpStatus.NO_CONTENT);
		}
	}
	
	
	
	
	
	
}
