package it.be.energy.model;

import java.util.Set;

import javax.persistence.Entity;
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
	private Long codProvincia;
	private String sigla;
	private String nome;
	@OneToMany(mappedBy = "provincia")
	private Set<Comune> comuni;
	
	

}
