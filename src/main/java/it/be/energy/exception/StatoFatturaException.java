package it.be.energy.exception;

public class StatoFatturaException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public StatoFatturaException(String messagge) {
		super(messagge);
	}
}
