package br.com.dlbca.hibernate.memcached.duration;

/**
 * Support class which contains predetermined
 * values for cache duration time.
 * 
 * @author Renan Scarela <renan.scarela@gmail.com>
 */

public final class DurationTime {

	public static final int ONE_MINUTE = 60;

	public static final int FIVE_MINUTES = 60*5;
	
	public static final int TEN_MINUTES = 60*10;
	
	public static final int FIFTEEN_MINUTES = 60*15;
	
	public static final int THIRTY_MINUTES = 60*30;
	
	public static final int FOURTY_FIVE_MINUTES = 60*45;
	
	public static final int ONE_HOUR = 60*60;
	
	public static final int ONE_DAY = 60*60*24;
	
	/**
	 * Maximum value(30 days) allowed for cache, following
	 * memcached specification
	 */
	public static final int MAX = 60*60*24*30;
	
	
	
}
