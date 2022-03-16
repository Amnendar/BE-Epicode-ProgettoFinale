package it.be.energy.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.be.energy.exception.ClienteException;
import it.be.energy.exception.IndirizzoException;
import it.be.energy.model.Cliente;
import it.be.energy.model.Fattura;
import it.be.energy.model.Indirizzo;
import it.be.energy.repository.ClienteRepository;
import it.be.energy.repository.ComuneRepository;
import it.be.energy.repository.FatturaRepository;
import it.be.energy.repository.IndirizzoRepository;

@Service
public class ClienteService {

	@Autowired
	ClienteRepository clienterepo;
	
	@Autowired
	ComuneRepository comunerepo;
	
	@Autowired
	IndirizzoRepository indirizzorepo;
	
	@Autowired
	FatturaRepository fatturarepo;
	
	@Autowired
	IndirizzoService indirizzoservice;
	

	/*
	 * inserimento di un nuovo cliente
	 */
	public Cliente inserisciCliente(Cliente cliente) {
		if (cliente.getSedeLegale() == null && cliente.getSedeOperativa() == null) {// se gli indirizzi sono null,
																					// salviamo il il cliente all'interno di questo IF
			clienterepo.save(cliente);
			List<Fattura> liste = cliente.getFatture();// salviamo le eventuali fatture passate assieme al cliente in
														// input
			if (liste == null) { // se la lista fatture non esiste salviamo in questo punto il cliente, in quanto
									// una lista a null crea conflitto con le righe successive
				return clienterepo.save(cliente);
			}
			for (Fattura fattura : liste) {//se il cliente viene inserito assieme ad eventuali fatture, le assegnamo al cliente e le salviamo nel DB
				fattura.setCliente(cliente);
			}
			fatturarepo.saveAll(liste);//save di tutte le eventuali fatture
			clienterepo.save(cliente);//salviamo il cliente senza fatture
			fatturarepo.saveAll(liste);//salviamo le fatture con il cliente 
			return clienterepo.save(cliente);//salviamo il cliente con le fatture
		}
		Long idindirizzo1 = cliente.getSedeLegale().getId();
		Optional<Indirizzo> indirizzo1 = indirizzorepo.findById(idindirizzo1);// gli indirizzi devono essere presenti,
																				// quindi controlliamo gli ID
		if (indirizzo1.isPresent()) {
			cliente.setSedeLegale(indirizzo1.get());
		} else {//se l'id indirizzo non è presente, lanciamo un'eccezione
			throw new IndirizzoException("ERRORE! Nessun indirizzo con questo ID!");
		}
		Long idindirizzo2 = cliente.getSedeOperativa().getId();
		Optional<Indirizzo> indirizzo2 = indirizzorepo.findById(idindirizzo2);// gli indirizzi devono essere presenti,
																				// quindi controlliamo gli ID
		if (indirizzo2.isPresent()) {
			cliente.setSedeOperativa(indirizzo2.get());
		} else {//se l'id indirizzo non è presente, lanciamo un'eccezione
			throw new IndirizzoException("ERRORE! Nessun indirizzo con questo ID!");
		}

		clienterepo.save(cliente);
		List<Fattura> liste = cliente.getFatture();// salviamo le eventuali fatture passate assieme al cliente in input
		if (liste == null) { // se la lista fatture non esiste salviamo in questo punto il cliente, in quanto
								// una lista a null crea conflitto con le righe successive
			return clienterepo.save(cliente);
		}
		for (Fattura fattura : liste) {//se il cliente viene inserito assieme ad eventuali fatture, le assegnamo al cliente e le salviamo nel DB
			fattura.setCliente(cliente);
		}
		fatturarepo.saveAll(liste);//salviamo le fatture
		clienterepo.save(cliente);//salviamo il cliente
		fatturarepo.saveAll(liste);//salviamo le fatture con riferimento al cliente
		return cliente;

	}
	
	/*
	 * cancellazione di un cliente
	 */
	public void cancellaClienteById(Long id) {
		Optional<Cliente> cancella = clienterepo.findById(id);
		if(cancella.isPresent()) {//verifichiamo che esista un cliente con l'id passato in input
			Cliente cancellato = cancella.get();//scolleghiamo il cliente dai riferimenti su altre tabelle
			for (Fattura fattura : cancellato.getFatture()) {//mettiamo a null il riferimento di cliente su tutte le sue fatture
				fattura.setCliente(null);
			}
			cancellato.setFatture(null);
			cancellato.setSedeLegale(null);//settiamo entrambi gli indirizzi a null
			cancellato.setSedeOperativa(null);
			clienterepo.deleteById(id);
		}
		else {//se non esistono clienti con l'id passato in input, lanciamo un'eccezione
			throw new ClienteException("ERRORE! Nessun cliente con questo ID!");
		}
	}
	
	/*
	 * aggiornamento dei parametri di un cliente
	 */
	public Cliente updateCliente(Long id,Cliente cliente) { 
		Optional<Cliente> aggiornare =clienterepo.findById(id);
		if(aggiornare.isPresent()) {//verifichiamo che esista un cliente con l'id passato in input
			Cliente aggiorna = aggiornare.get();//se esiste lo prendiamo e ne aggiorniamo tutti i parametri con quelli passati in input/request body
			aggiorna.setCognomeContatto(cliente.getCognomeContatto());
			aggiorna.setDataInserimento(cliente.getDataInserimento());
			aggiorna.setDataUltimoContatto(cliente.getDataUltimoContatto());
			aggiorna.setEmail(cliente.getEmail());
			aggiorna.setEmailContatto(cliente.getEmailContatto());
			aggiorna.setFatturatoAnnuale(cliente.getFatturatoAnnuale());
			aggiorna.setFatture(cliente.getFatture());
			fatturarepo.saveAll(cliente.getFatture());//TODO controllare se effettivamente serve
			aggiorna.setNomeContatto(cliente.getNomeContatto());
			aggiorna.setPartitaIva(cliente.getPartitaIva());
			aggiorna.setPec(cliente.getPec());
			aggiorna.setRagioneSociale(cliente.getRagioneSociale());
			aggiorna.setSedeLegale(cliente.getSedeLegale());
			aggiorna.setSedeOperativa(cliente.getSedeOperativa());
			aggiorna.setTelefono(cliente.getTelefono());
			aggiorna.setTelefonoContatto(cliente.getTelefonoContatto());
			aggiorna.setTipoCliente(cliente.getTipoCliente());
			return inserisciCliente(aggiorna);
		
		}
		else {//se il cliente non esiste, lanciamo un'eccezione
			throw new ClienteException("ERRORE! Nessun cliente con questo ID!");
		}
	}
	
	
	/*
	 * get di tutti i clienti presenti in sistema
	 */
	public Page<Cliente> findAllClienti(Pageable pageable){
		return clienterepo.findAll(pageable);
	}
	
	/*
	 * ricerca di un utente tramite la chiave primaria
	 */
	public Cliente getClienteById(Long id) {
		Optional<Cliente> trovato = clienterepo.findById(id);
		if(trovato.isPresent()) {//controlliamo se un utente con l'id passato in input esiste
			return trovato.get();//lo ritorniamo
		}
		else {//se il cliente non esiste, lanciamo un'eccezione
			throw new ClienteException("ERRORE! Nessun cliente con questo id!");
		}
	}
	
	//METODI ORDINAMENTO
	/*
	 * find tutti i clienti ordinati in ordine alfabetico per nomi
	 */
	public Page<Cliente> findAllClientiOrderByName(Pageable pageable){
		return clienterepo.findAllByOrderByRagioneSocialeAsc(pageable);
	}
	
	/*
	 *find tutti client ordinati per fatturato annuale
	 */
	public Page<Cliente> findAllClientiOrderByFatturatoAnnuale(Pageable pageable){
		return clienterepo.findAllByOrderByFatturatoAnnualeDesc(pageable);
	}
	
	/*
	 *find all clienti ordinati per data di inserimento
	 */
	public Page<Cliente> findAllClientiOrderByDataInserimento(Pageable pageable){
		return clienterepo.findAllByOrderByDataInserimento(pageable);
	}
	
	/*
	 *find all clienti ordinati per data di ultimo contatto
	 */
	public Page<Cliente> findAllClientiOrderByDataUltimoContatto(Pageable pageable){
		return clienterepo.findAllByOrderByDataUltimoContatto(pageable);
	}
	
	/*
	 *find all clienti ordinati per la provincia dove è presente il comune della sede legale
	 */
	public Page<Cliente> findAllByOrderBySedeLegaleComuneProvincia(Pageable pageable){
		return clienterepo.findAllByOrderBySedeLegaleComuneProvincia(pageable);
	}
	
	//METODI FILTRI
	
	/*
	 * find all clienti con fatturato maggiore uguale di
	 */
	public Page<Cliente> findByFatturatoAnnualeGreaterThanEqual(BigDecimal fatturato, Pageable pageable){
		return clienterepo.findByFatturatoAnnualeGreaterThanEqual(fatturato, pageable);
	}
	
	/*
	 * find all clienti con fatturato minore uguale di
	 */
		public Page<Cliente> findByFatturatoAnnualeLessThanEqual(BigDecimal fatturato, Pageable pageable){
			return clienterepo.findByFatturatoAnnualeLessThanEqual(fatturato, pageable);
	}
		
	/*
	 * find all clienti con fatturato tra 	
	 */
	public Page<Cliente> findByFatturatoAnnualeBetween(BigDecimal iniziale, BigDecimal finale, Pageable pageable ){
			return clienterepo.findByFatturatoAnnualeBetween(iniziale, finale, pageable);
	}
	
	/*
	 * find clienti con data inserimento dopo di
	 */
	public Page<Cliente> findByDataInserimentoGreaterThanEqual(LocalDate data, Pageable pageable){
		return clienterepo.findByDataInserimentoGreaterThanEqual(data, pageable);
	}
	
	/*
	 * find clienti con data inserimento prima di
	 */
	public Page<Cliente> findByDataInserimentoLessThanEqual(LocalDate data, Pageable pageable){
		return clienterepo.findByDataInserimentoLessThanEqual(data, pageable);
	}
	
	/*
	 * find clienti con data inserimento tra
	 */
	public Page<Cliente> findByDataInserimentoBetween(LocalDate inizio, LocalDate fine, Pageable pageable){
		return clienterepo.findByDataInserimentoBetween(inizio, fine, pageable);
	}
	
	/*
	 * find clienti con data ultimo contatto dopo quella passata in input
	 */
	public Page<Cliente> findByDataUltimoContattoGreaterThanEqual(LocalDate data, Pageable pageable){
		return clienterepo.findByDataUltimoContattoGreaterThanEqual(data, pageable);
	}
		
	/*
	 * find clienti con data ultimo contatto prima di quella passata in input
	 */
	public Page<Cliente> findByDataUltimoContattoLessThanEqual(LocalDate data, Pageable pageable){
		return clienterepo.findByDataUltimoContattoLessThanEqual(data, pageable);
	}
		
	/*
	 * find clienti con data ultimo contatto tra
	 */
	public Page<Cliente> findByDataUltimoContattoBetween(LocalDate inizio, LocalDate fine, Pageable pageable){
		return clienterepo.findByDataUltimoContattoBetween(inizio, fine, pageable);
	}
	
	/*
	 * find di tutti i clienti che nel nome/ragione sociale contengono la string passata in input
	 */
	public Page<Cliente> findByRagioneSocialeContaining(String nome, Pageable pageable){
		return clienterepo.findByRagioneSocialeContaining(nome, pageable);
	}
	
	
	//METODI EXTRA
	/*
	 * metodo per aggiungere o modificare l indirizzo della sede legale di un cliente
	 */
	public Cliente cambiaSedeLegale(Long idCliente, Long idIndirizzo) {
		Optional<Cliente> modifica = clienterepo.findById(idCliente);
		if(!modifica.isPresent()) {
			throw new ClienteException("ERRORE! Nessun Cliente con questo ID!");
		}
		Cliente trovato = modifica.get();
		Optional<Indirizzo> sede = indirizzorepo.findById(idIndirizzo);
		if(!sede.isPresent()) {
			throw new IndirizzoException("ERRORE! Nessun Indirizzo con questo ID!");
		}
		Indirizzo sedeLegale = sede.get();
		
		trovato.setSedeLegale(sedeLegale);
		return clienterepo.save(trovato);
	}
	
	
	/*
	 * metodo per aggiungere o modificare l indirizzo della sede operativa di un cliente
	 */
	public Cliente cambiaSedeOperativa(Long idCliente, Long idIndirizzo) {
		Optional<Cliente> modifica = clienterepo.findById(idCliente);
		if(!modifica.isPresent()) {
			throw new ClienteException("ERRORE! Nessun Cliente con questo ID!");
		}
		Cliente trovato = modifica.get();
		Optional<Indirizzo> sede = indirizzorepo.findById(idIndirizzo);
		if(!sede.isPresent()) {
			throw new IndirizzoException("ERRORE! Nessun Indirizzo con questo ID!");
		}
		Indirizzo sedeOperativa = sede.get();
		
		trovato.setSedeOperativa(sedeOperativa);
		return clienterepo.save(trovato);
	}
	
	
	
}
