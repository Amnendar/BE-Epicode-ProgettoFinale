package it.be.energy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.be.energy.exception.ComuneException;
import it.be.energy.model.Comune;
import it.be.energy.repository.ComuneRepository;

@Service
public class ComuneService {

	@Autowired
	ComuneRepository comunerepo;

	/*
	 * get di tutti i comuni presenti nel sistema
	 */
	public Page<Comune> mostraTutte(Pageable pageable) {
		return comunerepo.findAll(pageable);
	}

	/*
	 * trova comune tramite id
	 */
	public Comune trova(Long id) {
		Optional<Comune> trovato = comunerepo.findById(id);
		if (trovato.isPresent()) {// controlliamo che il comune con l'id passato in input esista
			return trovato.get();
		} else {// se non esiste nessuno comune con l'id passato in input, lanciamo un'eccezione
			throw new ComuneException("ERRORE! Nessun comune con questo id!");
		}
	}

	/*
	 * trova comuni tramite nome
	 */
	public Page<Comune> trovaPerNome(String nome, Pageable pageable) {
		return comunerepo.findByNomeContaining(nome, pageable);
	}

}
