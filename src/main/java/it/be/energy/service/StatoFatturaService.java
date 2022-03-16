package it.be.energy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.be.energy.exception.StatoFatturaException;
import it.be.energy.model.StatoFattura;
import it.be.energy.repository.StatoFatturaRepository;

@Service
public class StatoFatturaService {

	@Autowired
	StatoFatturaRepository statofatturarepo;
	
	/*
	 * get di tutti gli StatoFattura
	 */
	public Page<StatoFattura>mostraStati(Pageable pageable) {
		return statofatturarepo.findAll(pageable);
	}
	
	/*
	 * get di uno stato tramite id
	 */
	public StatoFattura trovaStato(Long id) {
		Optional<StatoFattura> stato = statofatturarepo.findById(id);
		if(stato.isPresent()) {//controlliamo se lo stato con l'id passato in input è presente
			return stato.get();//se esiste lo ritorniamo
		}
		else {//se non esiste lanciamo un'eccezione
			throw new StatoFatturaException("ERRORE! Nessuno stato con questo ID!");
		}
	}
	
	/*
	 * creazione di un nuovo stato
	 */
	public StatoFattura creaNuovoStato(StatoFattura stato) {
		return statofatturarepo.save(stato);
	}
	
	/*
	 * aggiornamento di uno stato
	 */
	public StatoFattura aggiornaStato(Long id, StatoFattura stato) {
		Optional<StatoFattura> aggiorna = statofatturarepo.findById(id);
		if(aggiorna.isPresent()) {//controlliamo se lo stato con l'id passato in input è presente
			StatoFattura aggiornare = aggiorna.get();//se esiste lo prendiamo
			aggiornare.setStato(stato.getStato());//aggiorniamo il parametro
			return statofatturarepo.save(aggiornare);//save dell'entità
		}
		else {//se non esiste lanciamo un'eccezione
			throw new StatoFatturaException("ERRORE! Nessuno stato con questo ID!");
		}
	}
	
	/*
	 * cancellazione di uno stato
	 */
	public void cancellaStato(Long id) {
		Optional<StatoFattura> cancella = statofatturarepo.findById(id);
		if(cancella.isPresent()) {//controlliamo se lo stato con l'id passato in input è presente
			statofatturarepo.deleteById(id);//se esiste, lo cancelliamo
		}
		else {//se non esiste lanciamo un'eccezione
			throw new StatoFatturaException("ERRORE! Nessuno stato con questo ID!");
		}
	}
	
}
