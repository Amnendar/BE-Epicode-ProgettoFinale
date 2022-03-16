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
		List<Cliente> tutti = clienterepo.findAll();
		for (Cliente cliente : tutti) {
			if(fattura.getCliente().getId().equals(cliente.getId())) {//se l id cliente corrisponde ad uno presente nel DB procedo al salvataggio
				return fatturarepo.save(fattura);
			}
		}
		throw new FatturaException("ERRORE! Non puoi assegnare questa fattura ad un ID cliente non esistente!");
		
		
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
			aggiorna.setNFattura(fattura.getNFattura());
			return fatturarepo.save(aggiorna);
			}
		else {
			throw new FatturaException("ERRORE! Nessuna fattura con questo ID!");
		}
	}
	
	public List<Fattura> findyStato( Long idstato){
		List<StatoFattura> prova = new ArrayList<>();
		List<StatoFattura> tutti = statorepo.findAll();
		for (StatoFattura statoFattura : tutti) {
			if(statoFattura.getId().equals(idstato)) {
				prova.add(statoFattura);
			}
		}
		if(prova.isEmpty()) {
			throw new StatoFatturaException("ERRORE! Nessuno stato presente con questo ID!");
		}
		List<Fattura> risultato = new ArrayList<>();
		List<Fattura> tutte = fatturarepo.findAll();
		for (Fattura fattura : tutte) {
			if(fattura.getStato().getId().equals(idstato)) {
				risultato.add(fattura);
			}
		}
		if(risultato.isEmpty()) {
			throw new FatturaException("ERRORE! Nessuna fattura con questo stato!");
		}
		return risultato;
	}
	
	
	public Page<Fattura> findByClienteRagioneSocialeLike(Pageable pageable, String nome){
		return fatturarepo.findByClienteRagioneSocialeContaining(pageable, nome);
	}
	
	public Page<Fattura> findByData(Pageable pageable, LocalDate data){
		return fatturarepo.findByData(pageable, data);
	}
	
	public Page<Fattura> findByAnno(Pageable pageable, Integer anno){
		return fatturarepo.findByAnno(pageable, anno);
	}
	
	public Page<Fattura> findByImportoBetween(Pageable pageable, BigDecimal minimo, BigDecimal massimo){
		return fatturarepo.findByImportoBetween(pageable, minimo, massimo);
	}
	
	
	
	
}
