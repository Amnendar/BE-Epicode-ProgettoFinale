package it.be.energy.exception;

public class ComuneException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ComuneException(String messagge) {
		super(messagge);
	}
}
