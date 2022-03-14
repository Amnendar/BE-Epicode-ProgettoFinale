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
	
	
	
	public Provincia FromString(String nome) {
		List<Provincia> all = provinciarepo.findAll();
		for (Provincia provincia : all) {
			if(provincia.getNome().equals(nome)) {
				return provincia;
			}
		}
		return null;
	}
	
	public Page<Provincia> mostraTutte(Pageable pageable){
		return provinciarepo.findAll(pageable);
	}
	
	public Provincia trova(Long id) {
		Optional<Provincia> trovato = provinciarepo.findById(id);
		if(trovato.isPresent()) {
			return trovato.get();
		}
		else {
			throw new ProvinciaException("ERRORE! Nessuna Provincia con questo id!");
		}
	}
	
	
	
}
