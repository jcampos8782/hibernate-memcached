package br.com.dlbca.hibernate.memcached.generator.exception;

/**
 * Exception for invalid machine time.
 * 
 * @author bruno
 *
 */
@SuppressWarnings("serial")
public class MachineTimeException extends RuntimeException {
	
	public MachineTimeException(String message){
		super(message);
	}

}
