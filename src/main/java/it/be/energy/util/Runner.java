package it.be.energy.util;

import java.io.FileReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

		// creiamo gli stati fattura

		StatoFattura pagata = new StatoFattura();
		pagata.setStato("Pagata");
		statofatturaservice.creaNuovoStato(pagata);

		StatoFattura nonpagata = new StatoFattura();
		nonpagata.setStato("Non Pagata");
		statofatturaservice.creaNuovoStato(nonpagata);

		// creiamo le fatture

		Fattura fattura1 = new Fattura();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date data1 = sdf.parse("1999-12-31");

		fattura1.setAnno(1999);
		fattura1.setData(data1);
		fattura1.setImporto(new BigDecimal("2000"));
		fattura1.setNFattura((long) 1001);
		fattura1.setStato(pagata);

		fatturarepo.save(fattura1);

		Fattura fattura2 = new Fattura();

		Date data2 = sdf.parse("2019-8-12");

		fattura2.setAnno(2019);
		fattura2.setData(data2);
		fattura2.setImporto(new BigDecimal("10000.99"));
		fattura2.setNFattura((long) 2300);
		fattura2.setStato(pagata);

		fatturarepo.save(fattura2);

		Fattura fattura3 = new Fattura();

		Date data3 = sdf.parse("2022-1-10");

		fattura3.setAnno(2022);
		fattura3.setData(data3);
		fattura3.setImporto(new BigDecimal("300"));
		fattura3.setNFattura((long) 101);
		fattura3.setStato(pagata);

		List<Fattura> fatturecliente1 = new ArrayList<>();
		fatturecliente1.add(fattura1);
		fatturecliente1.add(fattura2);

		List<Fattura> fatturecliente2 = new ArrayList<>();
		fatturecliente2.add(fattura3);

		fatturarepo.save(fattura3);

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

		clienterepo.save(cliente1);
		fattura1.setCliente(cliente1);
		fattura2.setCliente(cliente1);
		fatturarepo.save(fattura1);
		fatturarepo.save(fattura2);

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

		clienterepo.save(cliente2);
		fattura3.setCliente(cliente2);
		fatturarepo.save(fattura3);

	}
	


}
