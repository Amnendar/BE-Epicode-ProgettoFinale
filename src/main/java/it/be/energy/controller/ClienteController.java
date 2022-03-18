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
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.be.energy.model.Cliente;
import it.be.energy.service.ClienteService;
import it.be.energy.service.FatturaService;

@RestController
@RequestMapping("/cliente")
@SecurityRequirement(name = "bearerAuth")
public class ClienteController {

	@Autowired
	ClienteService clienteservice;
	
	@Autowired
	FatturaService fatturaservice;
	
	//CRUD DI BASE
	
	@GetMapping("/mostra")
	@Operation(summary = "Mostra Tutti Clienti", description = "Restituisce tutti i clienti presenti nel sistema")
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
	@Operation(summary = "Inserisci Cliente", description = "Permette di inserire un nuovo cliente nel sistema. NOTE DI FUNZIONAMENTO: Gli indirizzi possono essere o inseriti entrambi(Inserendo gli ID di indirizzi GIA esistenti!) o non inseriti (è possibile aggiungerli in un secondo momento con i metodi 'CambiaSede'). NON è possibile inserire un singolo indirizzo!. Le fatture possono essere inserite insieme al cliente e verranno automaticamente salvate. In caso non si vogliano inserire fatture si puo lasciare il campo vuoto o eliminarlo direttamente")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Cliente> inserisciCliente(@RequestBody Cliente cliente){
		clienteservice.inserisciCliente(cliente);
		return new ResponseEntity<>(cliente, HttpStatus.ACCEPTED);
	}
	
	
	@PutMapping("/modifica/{id}")
	@Operation(summary = "Aggiorna Cliente", description = "Permette di aggiornare i dati di un cliente già presente nel sistema. NOTA: Se vogliamo gli stessi indirizzi, inserire SOLAMENTE gli ID corrispondenti. Eventuali nuove fatture verranno aggiunte automaticamente a quelle del cliente insieme a quelle già presenti(Anche se rimane consigliato crearle nell'apposito Controller Fatture).NON INSERIRE ID di FATTURE gia ESISTENTI!")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Cliente> aggiornaClienti(@PathVariable Long id, @RequestBody Cliente aggiorna){
		clienteservice.updateCliente(id, aggiorna);
		return new ResponseEntity<>(aggiorna, HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/cancella/{id}")
	@Operation(summary = "Cancella Cliente", description = "Permette di cancellare un cliente dal sistema, tramite chiave primaria. NOTA. Eventuali fatture collegate al cliente rimarranno comunque nel sistema!")
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
	@Operation(summary = "Mostra Tutti Clienti Ordinati per Ragione Sociale", description = "Restituisce una lista di tutti i clienti presenti nel sistema, ordinati per ordine alfabetico tramite nome/ragione sociale")
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
	@Operation(summary = "Mostra Tutti Clienti Ordinati per Data Ultimo Contatto", description = "Restituisce una lista di tutti i clienti presenti nel sistema, ordinati per data di ultimo contatto")
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
	@Operation(summary = "Cerca Clienti Per Fatturato Minore o Uguale", description = "Restituisce tutti i clienti con un fatturato minore o uguale a quello passato in input")
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
	@Operation(summary = "Cerca Clienti per Fatturato tra 2 parametri", description = "Restituisce tutti i clienti con un fatturato presente tra i due passati in input. NOTA. Inserire PRIMA l'importo più basso e DOPO quello più alto")
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
	@Operation(summary = "Cerca Clienti Per Data Inserimento Tra ", description = "Restituisce tutti i clienti con data di inserimento tra quelle passate in input. NOTA: Per il corretto funzionamento del metodo, inserire prima la data precedente, e dopo quella successiva")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Page<Cliente>> trovaClienteDataInserimentoTra(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate data1, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate data2, Pageable pageable) throws Exception{
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
	@Operation(summary = "Cerca Clienti Per Data Ultimo Contatto Tra ", description = "Restituisce tutti i clienti con data di ultimo contatto presente tra quelle passate in input. NOTA: Per il corretto funzionamento del metodo, inserire prima la data precedente, e dopo quella successiva")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Page<Cliente>> TrovaClienteDataUltimoContattoTra(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate data1, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate data2, Pageable pageable) throws Exception{
		Page<Cliente> found = clienteservice.findByDataInserimentoBetween(data1, data2, pageable);
		if(found.isEmpty()) {
			return new ResponseEntity<>(found, HttpStatus.NO_CONTENT);
		}
		else{
			return new ResponseEntity<>(found, HttpStatus.ACCEPTED);
		}
	}
	
	@GetMapping("/trovaperragionesociale/{nome}")
	@Operation(summary = "Cerca Clienti Con Nome Simile a", description = "Restituisce tutti i clienti con ragione sociale che contiene la stringa passata in input. NOTA: il metodo è Case Sensitive")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Page<Cliente>> trovaClientePerRagioneSociale(@PathVariable String nome, Pageable pageable){
		Page<Cliente> found = clienteservice.findByRagioneSocialeContaining(nome, pageable);
		if(found.isEmpty()) {
			return new ResponseEntity<>(found, HttpStatus.NO_CONTENT);
		}
		else{
			return new ResponseEntity<>(found, HttpStatus.ACCEPTED);
		}
	}
	
	//metodi extra
	
	@PutMapping("/cambiasedelegale/{idCliente}/{idSede}")
	@Operation(summary = "Cambia Sede Legale", description = "Permette di aggiungere(se non presente) o modificare la sede legale di un cliente, passando gli id del cliente e l'id dell'indirizzo corrispondenti")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Cliente> cambiaSedeLegale(@PathVariable Long idCliente, @PathVariable Long idSede){
		return new ResponseEntity<>(clienteservice.cambiaSedeLegale(idCliente, idSede), HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/cambiasedeoperativa/{idCliente}/{idSede}")
	@Operation(summary = "Cambia Sede Operativa", description = "Permette di aggiungere(se non presente) o modificare la sede operativa di un cliente, passando gli id del cliente e l'id dell'indirizzo corrispondenti")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Cliente> cambiaSedeOperativa(@PathVariable Long idCliente, @PathVariable Long idSede){
		return new ResponseEntity<>(clienteservice.cambiaSedeOperativa(idCliente, idSede), HttpStatus.ACCEPTED);
	}
	
}
