package it.be.energy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.be.energy.model.Comune;
import it.be.energy.service.ComuneService;

@RestController
@RequestMapping("/comune")
@SecurityRequirement(name = "bearerAuth")
public class ComuneController {

	@Autowired
	ComuneService comuneservice;
	
	@GetMapping("/mostra") 
	@Operation
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Page<Comune>> trovaTutti(Pageable pageable){
		Page<Comune> found = comuneservice.mostraTutte(pageable);
		if(found.isEmpty()) {
			return new ResponseEntity<>(found, HttpStatus.NO_CONTENT);
		}
		else {
			return new ResponseEntity<>(found, HttpStatus.ACCEPTED);
		}
	}
	
	@GetMapping("/cerca/{id}")
	@Operation
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Comune> trova(@PathVariable Long id){
		return new ResponseEntity<>(comuneservice.trova(id), HttpStatus.ACCEPTED);
		
	}
	
	@GetMapping("/cercapernome/{nome}")
	@Operation
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Page<Comune>> trova(@PathVariable String nome, Pageable pageable){
		Page<Comune> found = comuneservice.trovaPerNome(nome, pageable);
		if(found.isEmpty()) {
			return new ResponseEntity<>(found, HttpStatus.NO_CONTENT);
		}
		else {
			return new ResponseEntity<>(found, HttpStatus.ACCEPTED);
		}
	}
	
	
}
