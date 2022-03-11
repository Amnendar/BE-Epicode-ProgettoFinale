package it.be.energy.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comune {

	@Id
	private Long id;
	private Long codComune;
	private String nome;
	@ManyToOne
	private Provincia provincia;
	@OneToMany(mappedBy = "comune")
	private List<Indirizzo> indirizzi;
	
}
