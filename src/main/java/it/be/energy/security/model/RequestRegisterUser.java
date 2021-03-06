package it.be.energy.security.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;

@Data
public class RequestRegisterUser {

	private String userName;
	private String password;
	private String nome;
	private String cognome;
	private String mail;
	private Set<String> roles = new HashSet<>();
	
	
}
