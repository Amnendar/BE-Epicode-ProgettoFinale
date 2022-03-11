package it.be.energy.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Fattura {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long nFattura;
	private Integer anno;
	private Date data;
	private BigDecimal importo;
}
