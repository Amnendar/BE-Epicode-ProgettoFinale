package it.be.energy.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.opencsv.CSVReader;

import it.be.energy.model.Provincia;
import it.be.energy.repository.ComuneRepository;
import it.be.energy.repository.ProvinciaRepository;
import it.be.energy.service.ComuneService;


@Component
public class Runner implements CommandLineRunner {

	@Autowired
	ProvinciaRepository provinciarepo;
	
	@Autowired
	ComuneRepository comunerepo;
	
	@Autowired
	ComuneService comuneservice;
	
	@Override
	public void run(String... args) throws Exception {
		initProvincia();
		initComune();
		
	}
	
	private void initProvincia() throws FileNotFoundException, IOException {
		try (CSVReader csvReader = new CSVReader(new FileReader("province-italiane.csv"));) {
		    String[] values = null;
		    csvReader.readNext();//nel csv abbiamo l intestazione, questo serve a saltare una riga
		    while ((values = csvReader.readNext()) != null) {
		    	provinciarepo.save(new Provincia(values[0], values[1]));
		    }
		}
	}
	
	private void initComune() throws FileNotFoundException, IOException {
		try (CSVReader csvReader = new CSVReader(new FileReader("comuni-italiani.csv"));) {
		    String[] values = null;
		    csvReader.readNext();//nel csv abbiamo l intestazione, questo serve a saltare una riga
		    while ((values = csvReader.readNext()) != null) {
		    	comunerepo.save(comuneservice.newComune(values[1], values[2], values[3]));
		    }
		}
	}
	

}
