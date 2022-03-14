package it.be.energy.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String ragioneSociale;
	private String partitaIva;
	private TipoCliente tipoCliente;
	private String email;
	private LocalDate dataInserimento;
	private LocalDate dataUltimoContatto;
	private BigDecimal fatturatoAnnuale;
	private String pec;
	private String telefono;
	private String emailContatto;
	private String nomeContatto;
	private String cognomeContatto;
	private String telefonoContatto;
	@OneToOne
	private Indirizzo sedeLegale;
	@OneToOne
	private Indirizzo sedeOperativa;
	
	@OneToMany(mappedBy = "cliente")
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	private List<Fattura> fatture;
	
}
