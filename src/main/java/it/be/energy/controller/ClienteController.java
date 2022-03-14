package it.be.energy.controller;

import java.math.BigDecimal;
import java.time.LocalDate;

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
import it.be.energy.model.Cliente;
import it.be.energy.service.ClienteService;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

	@Autowired
	ClienteService clienteservice;
	
	//CRUD DI BASE
	
	@GetMapping("/mostra")
	@Operation(summary = "Mostra Tutti Clienti", description = "Restituisce una lista di tutti i clienti presenti nel sistema")
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
	@Operation(summary = "Inserisci Cliente", description = "Permette di inserire un nuovo cliente nel sistema")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Cliente> inserisciCliente(@RequestBody Cliente cliente){
		clienteservice.inserisciCliente(cliente);
		return new ResponseEntity<>(cliente, HttpStatus.ACCEPTED);
	}
	
	
	@PutMapping("/modifica/{id}")
	@Operation(summary = "Aggiorna Cliente", description = "Permette di aggiornare i dati di un cliente gia presente nel sistema")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Cliente> aggiornaClienti(@PathVariable Long id, @RequestBody Cliente aggiorna){
		Cliente aggiornare = clienteservice.getClienteById(id);
		clienteservice.updateCliente(id, aggiornare);
		return new ResponseEntity<>(aggiorna, HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/cancella/{id}")
	@Operation(summary = "Cancella Cliente", description = "Permette di cancellare un cliente dal sistema, tramite chiave primaria")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> cancellaCliente(@PathVariable Long id){
		clienteservice.cancellaClienteById(id);
		return new ResponseEntity<>("Cliente cancellato correttamente!", HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/cerca/{id}")
	@Operation(summary = "Cerca Cliente", description = "Permette di trovare un cliente tramite chiave primaria")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Cliente> trovaClienteById(@PathVariable Long id){
		Cliente trovato = clienteservice.getClienteById(id);
		return new ResponseEntity<>(trovato, HttpStatus.ACCEPTED);
	}
	
	//GET ORDINE
	
	@GetMapping("/mostrapernome")
	@Operation(summary = "Mostra Tutti Clienti Ordinati per Ragione Sociale", description = "Restituisce una lista di tutti i clienti presenti nel sistema, ordinati per nome/ragione sociale")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Page<Cliente>> mostraClientiPerNome(Pageable pageable){
		Page<Cliente> found = clienteservice.findAllClientiOrderByName(pageable);
		if(found.isEmpty()) {
			return new ResponseEntity<>(found, HttpStatus.NO_CONTENT);
		}
		else{
			return new ResponseEntity<>(found, HttpStatus.ACCEPTED);
		}
	}
	
	
	@GetMapping("/mostraperfatturato")
	@Operation(summary = "Mostra Tutti Clienti Ordinati per Fatturato Annuale", description = "Restituisce una lista di tutti i clienti presenti nel sistema, ordinati per fatturato annuale")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Page<Cliente>> mostraClientiPerFatturato(Pageable pageable){
		Page<Cliente> found = clienteservice.findAllClientiOrderByFatturatoAnnuale(pageable);
		if(found.isEmpty()) {
			return new ResponseEntity<>(found, HttpStatus.NO_CONTENT);
		}
		else{
			return new ResponseEntity<>(found, HttpStatus.ACCEPTED);
		}
	}
	
	
	@GetMapping("/mostraperdatainserimento")
	@Operation(summary = "Mostra Tutti Clienti Ordinati per Data di Inserimento", description = "Restituisce una lista di tutti i clienti presenti nel sistema, ordinati per data di inserimento")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Page<Cliente>> mostraClientiPerDataInserimento(Pageable pageable){
		Page<Cliente> found = clienteservice.findAllClientiOrderByDataInserimento(pageable);
		if(found.isEmpty()) {
			return new ResponseEntity<>(found, HttpStatus.NO_CONTENT);
		}
		else{
			return new ResponseEntity<>(found, HttpStatus.ACCEPTED);
		}
	}
	
	
	@GetMapping("/mostraperultimocontatto")
	@Operation(summary = "Mostra Tutti Clienti Ordinati per Data Ultimo Contatto", description = "Restituisce una lista di tutti i clienti presenti nel sistema, ordinati per data ultimo contatto")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Page<Cliente>> mostraClientiPerDataUltimoContatto(Pageable pageable){
		Page<Cliente> found = clienteservice.findAllClientiOrderByDataUltimoContatto(pageable);
		if(found.isEmpty()) {
			return new ResponseEntity<>(found, HttpStatus.NO_CONTENT);
		}
		else{
			return new ResponseEntity<>(found, HttpStatus.ACCEPTED);
		}
	}
	
	
	@GetMapping("/mostraperprovincia")
	@Operation(summary = "Mostra Tutti Clienti Ordinati per Provincia Sede Legale", description = "Restituisce una lista di tutti i clienti presenti nel sistema, ordinati tramite le provincie dei comuni dove sono presenti le loro sedi legali")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Page<Cliente>> mostraClientiPerProvinciaSedeLegale(Pageable pageable){
		Page<Cliente> found = clienteservice.findAllByOrderBySedeLegaleComuneProvincia(pageable);
		if(found.isEmpty()) {
			return new ResponseEntity<>(found, HttpStatus.NO_CONTENT);
		}
		else{
			return new ResponseEntity<>(found, HttpStatus.ACCEPTED);
		}
	}
	
	
	//GET FILTRI
	
	@GetMapping("/trovaperfatturatomaggiore/{fatturato}")
	@Operation(summary = "Cerca Clienti Per Fatturato Maggiore o Uguale", description = "Restituisce tutti i clienti con un fatturato maggiore o uguale a quello passato in input")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Page<Cliente>> trovaClienteFatturatoMaggioreUguale(@PathVariable BigDecimal fatturato, Pageable pageable){
		Page<Cliente> found = clienteservice.findByFatturatoAnnualeGreaterThanEqual(fatturato, pageable);
		if(found.isEmpty()) {
			return new ResponseEntity<>(found, HttpStatus.NO_CONTENT);
		}
		else{
			return new ResponseEntity<>(found, HttpStatus.ACCEPTED);
		}
	}
	
	
	@GetMapping("/trovaperfatturatominore/{fatturato}")
	@Operation(summary = "Cerca Clienti Per Fatturato Maggiore o Uguale", description = "Restituisce tutti i clienti con un fatturato maggiore o uguale a quello passato in input")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Page<Cliente>> trovaClienteFatturatoMinoreUguale(@PathVariable BigDecimal fatturato, Pageable pageable){
		Page<Cliente> found = clienteservice.findByFatturatoAnnualeLessThanEqual(fatturato, pageable);
		if(found.isEmpty()) {
			return new ResponseEntity<>(found, HttpStatus.NO_CONTENT);
		}
		else{
			return new ResponseEntity<>(found, HttpStatus.ACCEPTED);
		}
	}
	
	
	@GetMapping("/trovaperfatturatotra/{min}/{max}")
	@Operation(summary = "Cerca Clienti per Fatturato tra 2 parametri", description = "Restituisce tutti i clienti con un fatturato presente tra i due passato in input")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Page<Cliente>> trovaClienteFatturatoTra(@PathVariable BigDecimal min, @PathVariable BigDecimal max,  Pageable pageable){
		Page<Cliente> found = clienteservice.findByFatturatoAnnualeBetween(min, max, pageable);
		if(found.isEmpty()) {
			return new ResponseEntity<>(found, HttpStatus.NO_CONTENT);
		}
		else{
			return new ResponseEntity<>(found, HttpStatus.ACCEPTED);
		}
	}
	
	
	@GetMapping("/trovaperdatainserimentodopo/{data}")
	@Operation(summary = "Cerca Clienti Per Data Inserimento Uguale o Successiva a ", description = "Restituisce tutti i clienti con data di inserimento uguale o successiva a quella passata in input")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Page<Cliente>> trovaClienteDataInserimentoDopo(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate data,  Pageable pageable){
		Page<Cliente> found = clienteservice.findByDataInserimentoGreaterThanEqual(data, pageable);
		if(found.isEmpty()) {
			return new ResponseEntity<>(found, HttpStatus.NO_CONTENT);
		}
		else{
			return new ResponseEntity<>(found, HttpStatus.ACCEPTED);
		}
	}
	
	
	@GetMapping("/trovaperdatainserimentoprima/{data}")
	@Operation(summary = "Cerca Clienti Per Data Inserimento Uguale o Precedente a  ", description = "Restituisce tutti i clienti con data di inserimento uguale o precedente a quella passata in input")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Page<Cliente>> trovaClienteDataInserimentoPrima(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate data,  Pageable pageable){
		Page<Cliente> found = clienteservice.findByDataInserimentoLessThanEqual(data, pageable);
		if(found.isEmpty()) {
			return new ResponseEntity<>(found, HttpStatus.NO_CONTENT);
		}
		else{
			return new ResponseEntity<>(found, HttpStatus.ACCEPTED);
		}
	}
	
	
	@GetMapping("/trovaperdatainserimentotra/{data1}/{data2}")
	@Operation(summary = "Cerca Clienti Per Data Inserimento Tra ", description = "Restituisce tutti i clienti con data di inserimento tra quelle passate in input")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Page<Cliente>> trovaClienteDataInserimentoTra(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate data1, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate data2, Pageable pageable){
		Page<Cliente> found = clienteservice.findByDataInserimentoBetween(data1, data2, pageable);
		if(found.isEmpty()) {
			return new ResponseEntity<>(found, HttpStatus.NO_CONTENT);
		}
		else{
			return new ResponseEntity<>(found, HttpStatus.ACCEPTED);
		}
	}
	
	
	@GetMapping("/trovaperdatacontattodopo/{data}")
	@Operation(summary = "Cerca Clienti Per Data Ultimo Contatto Uguale o Successiva a ", description = "Restituisce tutti i clienti con data di ultimo contatto uguale o successiva a quella passata in input")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Page<Cliente>> trovaClienteDataUltimoContattoDopo(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate data,  Pageable pageable){
		Page<Cliente> found = clienteservice.findByDataUltimoContattoGreaterThanEqual(data, pageable);
		if(found.isEmpty()) {
			return new ResponseEntity<>(found, HttpStatus.NO_CONTENT);
		}
		else{
			return new ResponseEntity<>(found, HttpStatus.ACCEPTED);
		}
	}
	
	
	@GetMapping("/trovaperdatacontattoprima/{data}")
	@Operation(summary = "Cerca Clienti Per Data Ultimo Contatto Uguale o Precedente a ", description = "Restituisce tutti i clienti con data di ultimo contatto uguale o precendente a quella passata in input")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Page<Cliente>> trovaClienteDataUltimoContattoPrima(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate data,  Pageable pageable){
		Page<Cliente> found = clienteservice.findByDataUltimoContattoLessThanEqual(data, pageable);
		if(found.isEmpty()) {
			return new ResponseEntity<>(found, HttpStatus.NO_CONTENT);
		}
		else{
			return new ResponseEntity<>(found, HttpStatus.ACCEPTED);
		}
	}
	
	
	@GetMapping("/trovaperdatacontattotra/{data1}/{data2}")
	@Operation(summary = "Cerca Clienti Per Data Ultimo Contatto Tra ", description = "Restituisce tutti i clienti con data di ultimo contatto tra quelle passate in input")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Page<Cliente>> findByDataUltimoContattoBetween(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate data1, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate data2, Pageable pageable){
		Page<Cliente> found = clienteservice.findByDataInserimentoBetween(data1, data2, pageable);
		if(found.isEmpty()) {
			return new ResponseEntity<>(found, HttpStatus.NO_CONTENT);
		}
		else{
			return new ResponseEntity<>(found, HttpStatus.ACCEPTED);
		}
	}
	
	
	
	@GetMapping("/trovaperragionesociale/{nome}")
	@Operation(summary = "Cerca Clienti Con Nome Simile a", description = "Restituisce tutti i clienti con ragione sociale simile a quella passata in input")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Page<Cliente>> trovaClientePerRagioneSociale(@PathVariable String nome, Pageable pageable){
		Page<Cliente> found = clienteservice.findByRagioneSocialeLike(nome, pageable);
		if(found.isEmpty()) {
			return new ResponseEntity<>(found, HttpStatus.NO_CONTENT);
		}
		else{
			return new ResponseEntity<>(found, HttpStatus.ACCEPTED);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
