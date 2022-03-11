package it.be.energy.model;

import javax.persistence.Entity;
import javax.persistence.Id;

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
	
	

}
