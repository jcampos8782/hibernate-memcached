package br.com.dlbca.hibernate.memcached.generator.exception;

/**
 * Exception for MacAddress.
 * 
 * @author bruno
 *
 */
@SuppressWarnings("serial")
public class MacAddressException extends RuntimeException {
	
	public MacAddressException(String message, Exception e) {
		super(message, e);
	}

}
