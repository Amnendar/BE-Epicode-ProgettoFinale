package it.be.energy.util;

import java.io.FileReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.hibernate.type.CalendarDateType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.opencsv.CSVReader;

import it.be.energy.model.Cliente;
import it.be.energy.model.Comune;
import it.be.energy.model.Fattura;
import it.be.energy.model.Indirizzo;
import it.be.energy.model.Provincia;
import it.be.energy.model.StatoFattura;
import it.be.energy.model.TipoCliente;
import it.be.energy.repository.ClienteRepository;
import it.be.energy.repository.ComuneRepository;
import it.be.energy.repository.FatturaRepository;
import it.be.energy.repository.IndirizzoRepository;
import it.be.energy.repository.ProvinciaRepository;
import it.be.energy.service.ComuneService;
import it.be.energy.service.ProvinciaService;
import it.be.energy.service.StatoFatturaService;

@Component
public class Runner implements CommandLineRunner {

	@Autowired
	ProvinciaRepository provinciarepo;

	@Autowired
	ComuneRepository comunerepo;

	@Autowired
	ComuneService comuneservice;

	@Autowired
	ProvinciaService provinciaservice;

	@Autowired
	IndirizzoRepository indirizzorepo;

	@Autowired
	StatoFatturaService statofatturaservice;

	@Autowired
	FatturaRepository fatturarepo;

	@Autowired
	ClienteRepository clienterepo;

	@Override
	public void run(String... args) throws Exception {
		initComune();
		initProvincia();
		PopolaDB();

	}

	//leggiamo le provincie dal file csv
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

	//leggiamo i comuni dal file csv
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

	//metodo per sostituire caratteri sui file csv
	private String rimpiazza(String nome) {
		return nome.replace('-', ' ');
	}

	//creiamo elementi per popolare il DB
	private void PopolaDB() throws ParseException {

		// creiamo gli indirizzi

		Comune roma = comuneservice.trova((long) 5199);

		Indirizzo indirizzo1 = new Indirizzo();
		indirizzo1.setVia("Piazza Don Bosco");
		indirizzo1.setCivico("33");
		indirizzo1.setComune(roma);
		indirizzo1.setLocalita("Roma");
		indirizzo1.setCap("00175");

		indirizzorepo.save(indirizzo1);

		Comune fiano = comuneservice.trova((long) 5144);
		Indirizzo indirizzo2 = new Indirizzo();
		indirizzo2.setVia("Via dei Romeni");
		indirizzo2.setCivico("11");
		indirizzo2.setComune(fiano);
		indirizzo2.setLocalita("Fiano Romano");
		indirizzo2.setCap("10022");

		indirizzorepo.save(indirizzo2);

		Indirizzo indirizzo3 = new Indirizzo();
		indirizzo3.setVia("Via Sicilia");
		indirizzo3.setCivico("9");
		indirizzo3.setComune(roma);
		indirizzo3.setLocalita("Roma");
		indirizzo3.setCap("00110");

		indirizzorepo.save(indirizzo3);
		
		Comune milano = comuneservice.trova((long) 1690);
		Indirizzo indirizzo4 = new Indirizzo();
		indirizzo4.setVia("Via Monte Napoleone");
		indirizzo4.setCivico("19");
		indirizzo4.setComune(milano);
		indirizzo4.setLocalita("Milano");
		indirizzo4.setCap("20330");
		
		indirizzorepo.save(indirizzo4);

		// creiamo gli stati fattura

		StatoFattura pagata = new StatoFattura();
		pagata.setStato("Pagata");
		statofatturaservice.creaNuovoStato(pagata);

		StatoFattura nonpagata = new StatoFattura();
		nonpagata.setStato("Non Pagata");
		statofatturaservice.creaNuovoStato(nonpagata);

		// creiamo le fatture

		Fattura fattura1 = new Fattura();
		
		LocalDate data1 = LocalDate.of(2013, 1, 8);
		

		fattura1.setAnno(1999);
		fattura1.setData(data1);
		fattura1.setImporto(new BigDecimal("2000"));
		fattura1.setNFattura((long) 1001);
		fattura1.setStato(pagata);

		

		Fattura fattura2 = new Fattura();

		LocalDate data2 = LocalDate.of(2010, 6, 20);

		fattura2.setAnno(2019);
		fattura2.setData(data2);
		fattura2.setImporto(new BigDecimal("10000.99"));
		fattura2.setNFattura((long) 2300);
		fattura2.setStato(pagata);

		

		Fattura fattura3 = new Fattura();

		LocalDate data3 = LocalDate.of(2020, 9, 18);

		fattura3.setAnno(2022);
		fattura3.setData(data3);
		fattura3.setImporto(new BigDecimal("300"));
		fattura3.setNFattura((long) 101);
		fattura3.setStato(pagata);
		
		
		Fattura fattura4 = new Fattura();

		LocalDate data4 = LocalDate.of(2021, 11, 29);

		fattura4.setAnno(2021);
		fattura4.setData(data4);
		fattura4.setImporto(new BigDecimal("4500"));
		fattura4.setNFattura((long) 101);
		fattura4.setStato(nonpagata);
		
		Fattura fattura5 = new Fattura();

		LocalDate data5 = LocalDate.of(2022, 1, 2);

		fattura5.setAnno(2019);
		fattura5.setData(data5);
		fattura5.setImporto(new BigDecimal("5600"));
		fattura5.setNFattura((long) 101);
		fattura5.setStato(nonpagata);
		

		List<Fattura> fatturecliente1 = new ArrayList<>();
		fatturecliente1.add(fattura1);
		fatturecliente1.add(fattura2);

		List<Fattura> fatturecliente2 = new ArrayList<>();
		fatturecliente2.add(fattura3);
		
		List<Fattura> fatturecliente3 = new ArrayList<>();
		fatturecliente3.add(fattura4);
		fatturecliente3.add(fattura5);

		

		// creiamo i clienti

		Cliente cliente1 = new Cliente();
		cliente1.setCognomeContatto("Tabolacci");
		cliente1.setDataInserimento(LocalDate.parse("1980-03-02"));
		cliente1.setDataUltimoContatto(LocalDate.parse("2022-03-15"));
		cliente1.setEmail("management@tabolacciprod.it");
		cliente1.setEmailContatto("aletab@tabolacciprod.it");
		cliente1.setFatturatoAnnuale(new BigDecimal("1000000"));
		cliente1.setFatture(fatturecliente1);
		cliente1.setNomeContatto("Alessio");
		cliente1.setPartitaIva("A3434343222");
		cliente1.setPec("tabman@aruba.it");
		cliente1.setRagioneSociale("Tabolacci Productions");
		cliente1.setSedeLegale(indirizzo1);
		cliente1.setSedeOperativa(indirizzo1);
		cliente1.setTelefono("065503442");
		cliente1.setTelefonoContatto("3337288321");
		cliente1.setTipoCliente(TipoCliente.SAS);

		
		fattura1.setCliente(cliente1);
		fattura2.setCliente(cliente1);
		clienterepo.save(cliente1);
		

		// cliente2

		Cliente cliente2 = new Cliente();
		cliente2.setCognomeContatto("Torcasio");
		cliente2.setDataInserimento(LocalDate.parse("2010-07-31"));
		cliente2.setDataUltimoContatto(LocalDate.parse("2022-03-15"));
		cliente2.setEmail("torcapul@torca.it");
		cliente2.setEmailContatto("pasquale.torcasio@gmail.it");
		cliente2.setFatturatoAnnuale(new BigDecimal("2500000"));
		cliente2.setFatture(fatturecliente2);
		cliente2.setNomeContatto("Pasquale");
		cliente2.setPartitaIva("TR9884433");
		cliente2.setPec("torcasiopulizie@aruba.it");
		cliente2.setRagioneSociale("Torcasio Pulizie");
		cliente2.setSedeLegale(indirizzo3);
		cliente2.setSedeOperativa(indirizzo2);
		cliente2.setTelefono("03944433");
		cliente2.setTelefonoContatto("3827466898");
		cliente2.setTipoCliente(TipoCliente.PA);


		fattura3.setCliente(cliente2);
		clienterepo.save(cliente2);
		
		// cliente3
		
		Cliente cliente3 = new Cliente();
		cliente3.setCognomeContatto("Abul Aziz Mua");
		cliente3.setDataInserimento(LocalDate.parse("2005-04-22"));
		cliente3.setDataUltimoContatto(LocalDate.parse("2022-01-01"));
		cliente3.setEmail("sriwash@libero.it");
		cliente3.setEmailContatto("mohamedzul@gmail.it");
		cliente3.setFatturatoAnnuale(new BigDecimal("12000"));
		cliente3.setFatture(fatturecliente3);
		cliente3.setNomeContatto("Mohamed");
		cliente3.setPartitaIva("SL12222433");
		cliente3.setPec(null);
		cliente3.setRagioneSociale("Sri Lanka Wash");
		cliente3.setSedeLegale(indirizzo4);
		cliente3.setSedeOperativa(indirizzo4);
		cliente3.setTelefono("0592148433");
		cliente3.setTelefonoContatto("3392856421");
		cliente3.setTipoCliente(TipoCliente.SPA);

		fattura4.setCliente(cliente3);
		fattura5.setCliente(cliente3);
		
		clienterepo.save(cliente3);
	}
	


}
