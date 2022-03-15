package it.be.energy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.be.energy.exception.IndirizzoException;
import it.be.energy.model.Indirizzo;
import it.be.energy.repository.IndirizzoRepository;

@Service
public class IndirizzoService {

	@Autowired
	IndirizzoRepository indirizzorepo;
	
	
	
	public Page<Indirizzo> mostraTuttiIndirizzi(Pageable pageable){
		return indirizzorepo.findAll(pageable);
	}
	
	public Indirizzo trovaIndirizzo(Long id) {
		Optional<Indirizzo> trovato = indirizzorepo.findById(id);
		if (trovato.isPresent()) {
			return trovato.get();
		}
		else {
			throw new IndirizzoException("ERRORE! Nessun indirizzo con questo ID!");
		}
	}
	
	public Indirizzo inserisciIndirizzo(Indirizzo indirizzo) {
		return indirizzorepo.save(indirizzo);
	}
	
	public Indirizzo aggiornaIndirizzo(Long id, Indirizzo indirizzo) {
		Optional<Indirizzo> aggiornare = indirizzorepo.findById(id);
		if(aggiornare.isPresent()) {
			Indirizzo aggiorna = aggiornare.get();
			aggiorna.setCap(indirizzo.getCap());
			aggiorna.setCivico(indirizzo.getCivico());
			aggiorna.setComune(indirizzo.getComune());
			aggiorna.setLocalita(indirizzo.getLocalita());
			aggiorna.setVia(indirizzo.getVia());
			return indirizzorepo.save(aggiorna);
		}
		else {
			throw new IndirizzoException("ERRORE! Nessun indirizzo con questo ID!");
		}
		
	}
	
	public void cancellaIndirizzo(Long id) {
		Optional<Indirizzo> cancella = indirizzorepo.findById(id);
		if(cancella.isPresent()) {
			indirizzorepo.deleteById(id);
		}
		else {
			throw new IndirizzoException("ERRORE! Nessun indirizzo con questo ID!");
		}
	}
	
	
}
