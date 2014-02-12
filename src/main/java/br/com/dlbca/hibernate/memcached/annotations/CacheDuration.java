package br.com.dlbca.hibernate.memcached.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Specifies how much time a cacheable entity
 * will take to expire.
 * 
 * @author Renan Scarela <renan.scarela@gmail.com>
 */

@Target(TYPE)
@Retention(RUNTIME)
public @interface CacheDuration {

	/**
	 * Duration of cache. Must be specified in seconds.
	 */
	int value();
	
}
