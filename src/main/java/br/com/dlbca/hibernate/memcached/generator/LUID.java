package br.com.dlbca.hibernate.memcached.generator;

import java.net.NetworkInterface;
import java.util.Enumeration;

import br.com.dlbca.hibernate.memcached.generator.exception.InvalidMacAddressReferenceException;
import br.com.dlbca.hibernate.memcached.generator.exception.MacAddressException;
import br.com.dlbca.hibernate.memcached.generator.exception.MachineTimeException;

/**
 * LongUIDGenerator
 *
 * @author Maxim Khodanovich
 * @version 04.09.13 - 
 * Improved readability, encapsulation and transformed in static by Mateus Missaci
 * to avoid mult-threading problems
 * 
 *
 *The ID is composed by:
 *time - 41 bits (millisecond precision w/ a custom epoch gives us 69 years)
 *configured machine id - 10 bits - gives us up to 1024 machines
 *sequence number - 12 bits - rolls over every 4096 per machine (with protection to avoid rollover in the same ms)
 *
 *Based on twitter snowflake (written in scala)
 *
 */
public class LUID {

	private static final long MAC_ADDRESS_BITS = 10L;
	private static final long TIMESTAMP_BITS = 41L;

	private static final long MAX_MAC_POSSIBLE = -1L ^ (-1L << MAC_ADDRESS_BITS);
	private static final long MAX_SEQUENCE_VALUE = 4096;

	private static final long MAC_ADDRESS_LEFT_SHIFT_VALUE = 64L-MAC_ADDRESS_BITS;
	private static final long TIME_LEFT_SHIFT_VALUE = 64L-MAC_ADDRESS_BITS-TIMESTAMP_BITS;
	private static final long TWEPOCH = 1288834974657L;

	private static volatile long LAST_TIMESTAMP = -1L;
	private static volatile long SEQUENCE = 0L;
	
	private LUID(){}

	public synchronized static Long next() {
		Long macAddressReference = getMacAddressAsLongValue();
		Long timestamp = System.currentTimeMillis();
		checkIfMachineTimeWasReseted(timestamp);
		return orderBitsFor(avoidDuplicatedTimeStampFor(timestamp), macAddressReference);
	}

	private static Long getMacAddressAsLongValue(){
		Long macAddressReference  = getMacAddressReference();
		if (macAddressReference > MAX_MAC_POSSIBLE || macAddressReference < 0){
			throw new InvalidMacAddressReferenceException("Failed to determine MAC value");
		}

		return macAddressReference;
	}

	private static Long orderBitsFor(Long timestamp, Long macAddressReference){
		LAST_TIMESTAMP = timestamp;

		return ((timestamp - TWEPOCH) << TIME_LEFT_SHIFT_VALUE) | Math.abs(macAddressReference << MAC_ADDRESS_LEFT_SHIFT_VALUE)| SEQUENCE;
	}

	private static void checkIfMachineTimeWasReseted(long timestamp){
		if(timestamp<LAST_TIMESTAMP){
			throw new MachineTimeException("Clock moved backwards.  Refusing to generate id for "+ (LAST_TIMESTAMP - timestamp) +" milliseconds.");
		}
	}

	private static Long avoidDuplicatedTimeStampFor(Long timestamp){
		Long timestampToReturn = timestamp;
		
		if (LAST_TIMESTAMP == timestamp) {
			SEQUENCE = (SEQUENCE + 1) % MAX_SEQUENCE_VALUE;
			if (SEQUENCE == 0) {
				timestampToReturn = forceANewerTimeStampThan(LAST_TIMESTAMP);
			}
		} else {
			SEQUENCE = 0;
		}
		
		return timestampToReturn;
	}

	private static long forceANewerTimeStampThan(long lastTimestamp){
		long timestamp = System.currentTimeMillis();
		while (timestamp <= lastTimestamp) {
			timestamp = System.currentTimeMillis();
		}
		return timestamp;
	}

	private static long getMacAddressReference() {
		try {
			byte[] mac = null;
			
			
			Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
			
			while(networkInterfaces.hasMoreElements() && (mac == null || mac.length == 0)) {
				mac = networkInterfaces.nextElement().getHardwareAddress();
			}
			
			long id = ((0x000000FF & (long)mac[mac.length-1]) | (0x0000FF00 & (((long)mac[mac.length-2])<<8)))>>6;

			return id;


		} catch (Exception e) {
			throw new MacAddressException("Failed to obtain Mac address.", e);
		}
	}
	
	public static void main(String[] args) {
		System.out.println(LUID.next());
	}

}
