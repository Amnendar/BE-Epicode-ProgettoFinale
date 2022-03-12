package it.be.energy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
