package br.com.dlbca.hibernate.memcached.duration;

/**
 * Support class which contains predetermined
 * values for cache duration time.
 * 
 * @author Renan Scarela <renan.scarela@gmail.com>
 */

public final class DurationTime {

	/**
	 * Maximum value(30 days) allowed for cache, following
	 * memcached specification
	 */
	public static final int MAX = 60*60*24*30;
	
}
