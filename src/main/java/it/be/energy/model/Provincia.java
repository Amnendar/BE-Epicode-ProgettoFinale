package it.be.energy.model;

import java.util.Set;

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
	private String sigla;
	private String nome;
	@OneToMany(mappedBy = "provincia")
	private Set<Comune> comuni;
	
	
	
	public Provincia(String sigla, String nome) {
		this.sigla = sigla;
		this.nome = nome;
	}

	
}
