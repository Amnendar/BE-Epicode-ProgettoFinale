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
	
	
	public Page<StatoFattura>mostraStati(Pageable pageable) {
		return statofatturarepo.findAll(pageable);
	}
	
	public StatoFattura trovaStato(Long id) {
		Optional<StatoFattura> stato = statofatturarepo.findById(id);
		if(stato.isPresent()) {
			return stato.get();
		}
		else {
			throw new StatoFatturaException("ERRORE! Nessuno stato con questo ID!");
		}
	}
	
	public StatoFattura creaNuovoStato(StatoFattura stato) {
		return statofatturarepo.save(stato);
	}
	
	public StatoFattura aggiornaStato(Long id, StatoFattura stato) {
		Optional<StatoFattura> aggiorna = statofatturarepo.findById(id);
		if(aggiorna.isPresent()) {
			StatoFattura aggiornare = aggiorna.get();
			aggiornare.setStato(stato.getStato());
			return statofatturarepo.save(aggiornare);
		}
		else {
			throw new StatoFatturaException("ERRORE! Nessuno stato con questo ID!");
		}
	}
	
	public void cancellaStato(Long id) {
		Optional<StatoFattura> cancella = statofatturarepo.findById(id);
		if(cancella.isPresent()) {
			statofatturarepo.deleteById(id);
		}
		else {
			throw new StatoFatturaException("ERRORE! Nessuno stato con questo ID!");
		}
	}
	
}
