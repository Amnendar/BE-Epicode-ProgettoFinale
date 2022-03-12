package it.be.energy.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Provincia {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private Long codiceProvincia;
	@Column(unique = true)
	private String sigla;
	private String nome;
	private String regione;
	@OneToMany(mappedBy = "provincia")
	private Set<Comune> comuni;
	
	
	

	
	
	public Provincia(String sigla, String nome, String regione) {
		this.sigla = sigla;
		this.nome = nome;
		this.regione = regione;
	}

	
}
