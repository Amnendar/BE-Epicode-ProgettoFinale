package it.be.energy.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comune {

	@Id
	private Long codComune;
	private String nome;
	private Provincia provincia;
	
}
