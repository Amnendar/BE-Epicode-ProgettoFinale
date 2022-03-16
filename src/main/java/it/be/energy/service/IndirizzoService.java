package it.be.energy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.be.energy.exception.IndirizzoException;
import it.be.energy.model.Cliente;
import it.be.energy.model.Indirizzo;
import it.be.energy.repository.ClienteRepository;
import it.be.energy.repository.IndirizzoRepository;

@Service
public class IndirizzoService {

	@Autowired
	IndirizzoRepository indirizzorepo;
	
	@Autowired
	ClienteRepository clienterepo;
	
	
	
	
	/*
	 * get di tutti gli indirizzi
	 */
	public Page<Indirizzo> mostraTuttiIndirizzi(Pageable pageable){
		return indirizzorepo.findAll(pageable);
	}
	
	/*
	 * trova indirizzo tramite chiave primaria
	 */
	public Indirizzo trovaIndirizzo(Long id) {
		Optional<Indirizzo> trovato = indirizzorepo.findById(id);
		if (trovato.isPresent()) {//controlliamo che l'indirizzo esista effettivamente
			return trovato.get();
		}
		else {//se non esiste lanciamo un'eccezione
			throw new IndirizzoException("ERRORE! Nessun indirizzo con questo ID!");
		}
	}
	
	/*
	 * inserimento di un nuovo indirizzo
	 */
	public Indirizzo inserisciIndirizzo(Indirizzo indirizzo) {
		return indirizzorepo.save(indirizzo);
	}
	
	/*
	 * aggiornamento di un'indirizzo già presente
	 */
	public Indirizzo aggiornaIndirizzo(Long id, Indirizzo indirizzo) {
		Optional<Indirizzo> aggiornare = indirizzorepo.findById(id);
		if(aggiornare.isPresent()) {//controlliamo se l'indirizzo con l'id passato esista effettivamente
			Indirizzo aggiorna = aggiornare.get();
			aggiorna.setCap(indirizzo.getCap());
			aggiorna.setCivico(indirizzo.getCivico());
			aggiorna.setComune(indirizzo.getComune());
			aggiorna.setLocalita(indirizzo.getLocalita());
			aggiorna.setVia(indirizzo.getVia());
			return indirizzorepo.save(aggiorna);
		}
		else {//se non esiste lanciamo l'eccezione
			throw new IndirizzoException("ERRORE! Nessun indirizzo con questo ID!");
		}
		
	}
	
	/*
	 * get di tutti gli indirizzi
	 */
	public void cancellaIndirizzo(Long id) {
		Optional<Indirizzo> cancella = indirizzorepo.findById(id);
		List<Cliente> tutti = clienterepo.findAll();
		if (cancella.isPresent()) {//controlliamo se l'indirizzo con l'id passato esista
			cancella.get().setComune(null);
			for (Cliente cliente : tutti) { //controlliamo se l'indirizzo sia collegato ad uno o più clienti, in caso lo "scolleghiamo" per poterlo cancellare(vincolo di Chiave Primaria)
				if (cliente.getSedeLegale().equals(cancella.get())) {//controlliamo le sedi legali
					cliente.setSedeLegale(null);

				}
				if (cliente.getSedeOperativa().equals(cancella.get())) {//controlliamo le sedi operative
					cliente.setSedeOperativa(null);
				}
			}
			indirizzorepo.deleteById(id);//se tutte le condizioni vengono rispettate, effettuiamo la cancellazione
			return;
		} else {
			throw new IndirizzoException("ERRORE! Nessun indirizzo con questo ID!");//l'eccezione viene lanciata se all'inizio passiamo un Id non collegato a nessun indirizzo
		}
	}
	
	
}
