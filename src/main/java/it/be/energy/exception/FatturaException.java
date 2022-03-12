package it.be.energy.exception;

public class FatturaException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public FatturaException(String messagge) {
		super(messagge);
	}
}
