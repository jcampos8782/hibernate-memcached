package br.com.dlbca.hibernate.memcached.generator.exception;

/**
 * Exception for invalid mac address reference.
 * 
 * @author bruno
 *
 */
@SuppressWarnings("serial")
public class InvalidMacAddressReferenceException extends RuntimeException {
	
	public InvalidMacAddressReferenceException(String message) {
		super(message);
	}

}
