package it.be.energy.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.be.energy.exception.FatturaException;
import it.be.energy.exception.StatoFatturaException;
import it.be.energy.model.Cliente;
import it.be.energy.model.Fattura;
import it.be.energy.model.StatoFattura;
import it.be.energy.repository.ClienteRepository;
import it.be.energy.repository.FatturaRepository;
import it.be.energy.repository.StatoFatturaRepository;

@Service
public class FatturaService {

	@Autowired
	FatturaRepository fatturarepo;
	
	@Autowired
	StatoFatturaRepository statorepo;
	
	@Autowired
	ClienteRepository clienterepo;
	
	/*
	 * get di tutte le fatture
	 */
	public Page<Fattura> mostraFatture(Pageable pageable){
		return fatturarepo.findAll(pageable);
	}
	
	/*
	 * get di una fattura tramite chiave primaria
	 */
	public Fattura getFatturaById(Long id) {
		Optional<Fattura> trovato = fatturarepo.findById(id);
		if(trovato.isPresent()) {
			return trovato.get();
		}
		else {
			throw new FatturaException("ERRORE! Nessuna fattura con questo ID!");
		}
	}
	
	/*
	 * inserimento di una fattura
	 */
	public Fattura inserisciFattura(Fattura fattura) {
		if(fattura.getCliente().getId()==null) { //controllo se l'id inserito effettivamente esiste
			throw new FatturaException("ERRORE! Devi inserire una ID di un cliente!");
		}
		List<Cliente> tutti = clienterepo.findAll();
		for (Cliente cliente : tutti) {
			if(fattura.getCliente().getId().equals(cliente.getId())) {//se l id cliente corrisponde ad uno presente nel DB procedo al salvataggio
				return fatturarepo.save(fattura);
			}
		}
		throw new FatturaException("ERRORE! Non puoi assegnare questa fattura ad un ID cliente non esistente!");
		
		
	}
	
	/*
	 * cancellazione di una fattura
	 */
	public void cancellaFattura(Long id) {
		Optional<Fattura> cancella = fatturarepo.findById(id);
		if(cancella.isPresent()) {
			fatturarepo.deleteById(id);
		}
		else {//se l'id della fattura inserito non esiste, lanciamo un'eccezione
			throw new FatturaException("ERRORE! Nessuna fattura con questo ID!");
		}
	}
	
	/*
	 * aggiornamento di una fattura
	 */
	public Fattura aggiornaFattura(Long id, Fattura fattura) {
		Optional<Fattura> aggiornare = fatturarepo.findById(id);
		if(aggiornare.isPresent()) {//controlliamo che la fattura sia effettivamente presente
			Fattura aggiorna = aggiornare.get();
			aggiorna.setAnno(fattura.getAnno());
			aggiorna.setCliente(fattura.getCliente());
			aggiorna.setData(fattura.getData());
			aggiorna.setImporto(fattura.getImporto());
			aggiorna.setStato(fattura.getStato());
			aggiorna.setNFattura(fattura.getNFattura());
			return fatturarepo.save(aggiorna);
			}
		else {
			throw new FatturaException("ERRORE! Nessuna fattura con questo ID!");
		}
	}
	
	/*
	 * ricerca di tutte le fatture con un determinato stato, passandogli l'Id di quest'ultimo
	 */
	public List<Fattura> findyStato( Long idstato){
		List<StatoFattura> prova = new ArrayList<>();//creiamo una lista di appoggio dove inserire l'eventuale stato con Id Stato corrispondente
		List<StatoFattura> tutti = statorepo.findAll();//creiamo una lista di tutte gli stati fattura presenti nel sistema
		for (StatoFattura statoFattura : tutti) {
			if(statoFattura.getId().equals(idstato)) {//cicliamo la lista di tutti gli stati, se c'è quello corretto viene aggiunto alla prima lista
				prova.add(statoFattura);
			}
		}
		if(prova.isEmpty()) {//se la lista è vuota significa che non esiste nessuno stato con l'Id passaato in input
			throw new StatoFatturaException("ERRORE! Nessuno stato presente con questo ID!");
		}
		List<Fattura> risultato = new ArrayList<>();//creiamo una lista di fatture che conterrà quelle con lo stato corrispondente
		List<Fattura> tutte = fatturarepo.findAll();//creiamo una lista di tutte le fatture presenti nel sistema
		for (Fattura fattura : tutte) {//cicliamo tutte le fatture, confrontiamo gli id dello stato delle fatture con quello dello stato al quale facciamo riferimento
			if(fattura.getStato().getId().equals(idstato)) {
				risultato.add(fattura);
			}
		}
		if(risultato.isEmpty()) {//se la lista è vuota, significa che non è presente nessuna fattura con lo stato richiesto
			throw new FatturaException("ERRORE! Nessuna fattura con questo stato!");
		}
		return risultato;
	}
	
	/*
	 * trova fatture tramite parte o tutto il nome del cliente
	 */
	public Page<Fattura> findByClienteRagioneSocialeLike(Pageable pageable, String nome){
		return fatturarepo.findByClienteRagioneSocialeContaining(pageable, nome);
	}
	
	/*
	 * trova fatture tramite la data collegata ad esse
	 */
	public Page<Fattura> findByData(Pageable pageable, LocalDate data){
		return fatturarepo.findByData(pageable, data);
	}
	
	/*
	 * trova fatture tramite l'anno delle stesse
	 */
	public Page<Fattura> findByAnno(Pageable pageable, Integer anno){
		return fatturarepo.findByAnno(pageable, anno);
	}
	
	/*
	 * trova fatture tra range di importi
	 */
	public Page<Fattura> findByImportoBetween(Pageable pageable, BigDecimal minimo, BigDecimal massimo){
		if(minimo.compareTo(massimo)>0) {//controlliamo se il valore iniziale sia maggiore di quello finale
			throw new ArithmeticException("ERRORE! il valore iniziale non può essere maggiore del valore finale!");
		}
		return fatturarepo.findByImportoBetween(pageable, minimo, massimo);
	}
	
	//metodi extra
	/*
	 * cambia stato fattura
	 */
	public Fattura cambiaStato(Long idFattura, Long idStato) {
		Optional<Fattura> trovata = fatturarepo.findById(idFattura);
		if(!trovata.isPresent()) {//controlliamo se una fattura con questo is esista
			throw new FatturaException("ERRORE! Nessuna fattura con questo ID!");
		}
		Optional<StatoFattura> trovato = statorepo.findById(idStato);
		if(!trovato.isPresent()) {//controlliamo se uno stato fattura con questo id esista
			throw new StatoFatturaException("ERRORE! Nessun stato fattura con questo ID!");
		}
		Fattura fattura = trovata.get();
		StatoFattura stato = trovato.get();
		fattura.setStato(stato);//settiamo alla fattura il nuovo stato
		return fatturarepo.save(fattura);//salviamo la fattura col nuovo stato
	}
	
	
}
