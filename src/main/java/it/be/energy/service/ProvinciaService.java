package it.be.energy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.be.energy.exception.ProvinciaException;
import it.be.energy.model.Provincia;
import it.be.energy.repository.ProvinciaRepository;

@Service
public class ProvinciaService {

	@Autowired
	ProvinciaRepository provinciarepo;
	
	
	/*
	 * metodo che restituisce una Provincia con lo stesso nome passato in input
	 */
	public Provincia FromString(String nome) {
		List<Provincia> all = provinciarepo.findAll();
		for (Provincia provincia : all) {
			if(provincia.getNome().equals(nome)) {
				return provincia;//se troviamo una corrispondenza, restituiamo la provincia
			}
		}
		throw new ProvinciaException("ERRORE! Nessuna Provincia con questo nome!");//se non troviamo niente, lanciamo un'eccezione
	}
	
	/*
	 * get di tutte le province
	 */
	public Page<Provincia> mostraTutte(Pageable pageable){
		return provinciarepo.findAll(pageable);
	}
	
	/*
	 * get di una provincia tramite id
	 */
	public Provincia trova(Long id) {
		Optional<Provincia> trovato = provinciarepo.findById(id);
		if(trovato.isPresent()) {//controlliamo che la provincia esista
			return trovato.get();//se esiste la ritorniamo
		}
		else {//se non esiste lanciamo un'eccezione
			throw new ProvinciaException("ERRORE! Nessuna Provincia con questo id!");
		}
	}
	
	/*
	 * Metodo che ritorna provincie che nel nome presentano la stringa passata in input
	 */
	public Page<Provincia> trovaPerNome(Pageable pageable, String nome){
		return provinciarepo.findByNomeContaining(pageable, nome);
	}
	
	
}
