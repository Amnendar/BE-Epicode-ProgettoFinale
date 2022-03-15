package it.be.energy.util;

import java.io.FileReader;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.opencsv.CSVReader;

import it.be.energy.model.Comune;
import it.be.energy.model.Provincia;
import it.be.energy.repository.ComuneRepository;
import it.be.energy.repository.ProvinciaRepository;
import it.be.energy.service.ComuneService;


@Component
public class CsvReader implements CommandLineRunner {

	@Autowired
	ProvinciaRepository provinciarepo;
	
	@Autowired
	ComuneRepository comunerepo;
	
	@Autowired
	ComuneService comuneservice;
	
	@Override
	public void run(String... args) throws Exception {
		initComune();
		initProvincia();
		
		
	}
	
	private void initProvincia() throws Exception {
        try (CSVReader csvReader = new CSVReader(new FileReader("province-italiane_1.csv"));) {
            String[] values = null;
            csvReader.readNext(); 
            Optional<Provincia> pr;
            Provincia provincia;
            String nome;
            String[] valore;
            while ((values = csvReader.readNext()) != null) {
                valore = values[0].split(";");
                nome = valore[1];
                pr = provinciarepo.findByNomeLike("%" + nome + "%");
                if (pr.isPresent()) {

                    provincia = pr.get();
                    provincia.setSigla(valore[0]);
                    provincia.setRegione(valore[2]);
                    provinciarepo.save(provincia);
                } else {

                    provinciarepo.save(new Provincia(valore[0], valore[1], valore[2]));
                }
            }
        }
    }
	
	private void initComune() throws Exception {
        try (CSVReader csvReader = new CSVReader(new FileReader("comuni-italiani_1.csv"));) {
            String[] values = null;
            csvReader.readNext();
            Optional<Provincia> p;
            String[] valore;
            Provincia provincia;
            while ((values = csvReader.readNext()) != null) {
                valore = values[0].split(";");
                p = provinciarepo.findByCodiceProvincia(Long.valueOf(valore[0]));
                if (p.isPresent()) {
                    comunerepo.save(new Comune(rimpiazza(valore[2]), p.get()));
                } else {

                    provincia = new Provincia();
                    provincia.setCodiceProvincia(Long.valueOf(valore[0]));
                    provincia.setNome(rimpiazza(valore[3]));
                    provinciarepo.save(provincia);
                    comunerepo.save(new Comune(valore[2], provincia));
                }
            }
        }
    }
	
	private String rimpiazza(String nome) {
        return nome.replace('-', ' ');
    }

	
//	private void initComune() throws FileNotFoundException, IOException {
//		try (CSVReader csvReader = new CSVReader(new FileReader("comuni-italiani.csv"));) {
//		    String[] values = null;
//		    csvReader.readNext();
//		    while ((values = csvReader.readNext()) != null) {
//		    	comunerepo.save(comuneservice.newComune(values[1], values[2], values[3]));
//		    }
//		}
//	}
	
	
//	private void initProvincia() throws FileNotFoundException, IOException {
//		try (CSVReader csvReader = new CSVReader(new FileReader("province-italiane.csv"));){
//		    String[] values = null;
//		    csvReader.readNext();
//		    while ((values = csvReader.readNext()) != null) {
//		    	provinciarepo.save(new Provincia(values[0], values[1]));
//		    }
//		}
//	}
	

}
