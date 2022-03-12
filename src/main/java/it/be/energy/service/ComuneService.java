package it.be.energy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.be.energy.model.Comune;
import it.be.energy.repository.ComuneRepository;

@Service
public class ComuneService {

	@Autowired
	ComuneRepository comunerepo;
	
	@Autowired
	ProvinciaService provinciaservice;
	
	
	
	public Comune newComune(String progressivo,String nome, String provincia) {
		Comune nuovo = new Comune();
		nuovo.setProgressivo(Long.valueOf(progressivo));
		nuovo.setNome(nome);
		nuovo.setProvincia(provinciaservice.FromString(provincia));
		return nuovo;
	}

}
