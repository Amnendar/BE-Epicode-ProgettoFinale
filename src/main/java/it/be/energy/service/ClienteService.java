package it.be.energy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.be.energy.exception.ClienteException;
import it.be.energy.model.Cliente;
import it.be.energy.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	ClienteRepository clienterepo;
	

	//save cliente
	public Cliente inserisciCliente(Cliente cliente) {
		return clienterepo.save(cliente);
	}
	
	//delete cliente
	public void cancellaClienteById(Long id) {
		clienterepo.deleteById(id);
	}
	
	//update cliente
	public Cliente updateCliente(Long id,Cliente cliente) {
		Optional<Cliente> aggiornare =clienterepo.findById(id);
		if(aggiornare.isPresent()) {
			Cliente aggiorna = aggiornare.get();
			aggiorna.setCognomeContatto(cliente.getCognomeContatto());
			aggiorna.setDataInserimento(cliente.getDataInserimento());
			aggiorna.setDataUltimoContatto(cliente.getDataUltimoContatto());
			aggiorna.setEmail(cliente.getEmail());
			aggiorna.setEmailContatto(cliente.getEmailContatto());
			aggiorna.setFatturatoAnnuale(cliente.getFatturatoAnnuale());
			aggiorna.setFatture(cliente.getFatture());
			aggiorna.setNomeContatto(cliente.getNomeContatto());
			aggiorna.setPartitaIva(cliente.getPartitaIva());
			aggiorna.setPec(cliente.getPec());
			aggiorna.setRagioneSociale(cliente.getRagioneSociale());
			aggiorna.setSedeLegale(cliente.getSedeLegale());
			aggiorna.setSedeOperativa(cliente.getSedeOperativa());
			aggiorna.setTelefono(cliente.getTelefono());
			aggiorna.setTelefonoContatto(cliente.getTelefonoContatto());
			aggiorna.setTipoCliente(cliente.getTipoCliente());
			return clienterepo.save(aggiorna);
			
		}
		else {
			throw new ClienteException("ERRORE! Nessun cliente con questo ID!");
		}
	}
	
	//get all clienti
	public Page<Cliente> getAllClienti(Pageable pageable){
		return clienterepo.findAll(pageable);
	}
	
	//find by cliente id
	public Cliente getClienteById(Long id) {
		Optional<Cliente> trovato = clienterepo.findById(id);
		if(trovato.isPresent()) {
			return trovato.get();
		}
		else {
			throw new ClienteException("ERRORE! Nessun cliente con questo id!");
		}
	}
	
	//find all clienti ordinati per nome
	public Page<Cliente> getAllClientiOrderByName(Pageable pageable){
		return clienterepo.findAllOrderByRagioneSocialeAsc(pageable);
	}
	
	//find all client ordinati per fatturato annuale
	public Page<Cliente> getAllClientiOrderByFatturatoAnnuale(Pageable pageable){
		return clienterepo.findAllOrderByFatturatoAnnuleDesc(pageable);
	}
	
	//find all clienti ordinati per data di inserimento
	public Page<Cliente> getAllClientiOrderByDataInserimento(Pageable pageable){
		return clienterepo.findAllOrderByDataInserimento(pageable);
	}
	
	//find all clienti ordiniati per ultimo contatto
	public Page<Cliente> getAllClientiOrderByDataUltimoContatto(Pageable pageable){
		return clienterepo.findAllOrderByDataUltimoContatto(pageable);
	}
	
	
	
	
	
	
	
	
}
