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

	
	
	public Page<Comune> mostraTutte(Pageable pageable){
		return comunerepo.findAll(pageable);
	}
	
	public Comune trova(Long id) {
		Optional<Comune> trovato = comunerepo.findById(id);
		if(trovato.isPresent()) {
			return trovato.get();
		}
		else {
			throw new ComuneException("ERRORE! Nessun comune con questo id!");
		}
	}
	
	public Page<Comune> trovaPerNome(String nome, Pageable pageable){
		return comunerepo.findByNomeContaining(nome, pageable);
	}
	

}
