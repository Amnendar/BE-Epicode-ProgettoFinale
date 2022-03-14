package it.be.energy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.be.energy.exception.FatturaException;
import it.be.energy.model.Fattura;
import it.be.energy.repository.FatturaRepository;

@Service
public class FatturaService {

	@Autowired
	FatturaRepository fatturarepo;
	
	
	public Page<Fattura> mostraFatture(Pageable pageable){
		return fatturarepo.findAll(pageable);
	}
	
	
	public Fattura getFatturaById(Long id) {
		Optional<Fattura> trovato = fatturarepo.findById(id);
		if(trovato.isPresent()) {
			return trovato.get();
		}
		else {
			throw new FatturaException("ERRORE! Nessuna fattura con questo ID!");
		}
	}
	
	
	public Fattura inserisciFattura(Fattura fattura) {
		return fatturarepo.save(fattura);
	}
	
	
	public void cancellaFattura(Long id) {
		Optional<Fattura> cancella = fatturarepo.findById(id);
		if(cancella.isPresent()) {
			fatturarepo.deleteById(id);
		}
		else {
			throw new FatturaException("ERRORE! Nessuna fattura con questo ID!");
		}
	}
	
	
	public Fattura aggiornaFattura(Long id, Fattura fattura) {
		Optional<Fattura> aggiornare = fatturarepo.findById(id);
		if(aggiornare.isPresent()) {
			Fattura aggiorna = aggiornare.get();
			aggiorna.setAnno(fattura.getAnno());
			aggiorna.setCliente(fattura.getCliente());
			aggiorna.setData(fattura.getData());
			aggiorna.setImporto(fattura.getImporto());
			aggiorna.setStato(fattura.getStato());
			return fatturarepo.save(aggiorna);
			}
		else {
			throw new FatturaException("ERRORE! Nessuna fattura con questo ID!");
		}
	}
	
	
	
	
	
}
