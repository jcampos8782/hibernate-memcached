package br.com.dlbca.hibernate.memcached.duration;

import org.hibernate.cache.spi.entry.CacheEntry;

import br.com.dlbca.hibernate.memcached.annotations.CacheDuration;
import br.com.dlbca.hibernate.memcached.strategy.AbstractReadWriteMemcachedAccessStrategy.Item;

/**
 * Support class used to obtain the duration
 * specified on cacheable entities.
 * 
 * @see CacheDuration
 * 
 * @author Renan Scarela <renan.scarela@gmail.com>
 */

public class CacheDurationDefiner {
	
	public static Integer getDurationFor(Object object){
		String subclassName = getSubclassFromValueOf(object);
		CacheDuration cacheDuration = getCacheDurationAnnotation(subclassName);
		
		if(cacheDuration == null || cacheDuration.value() == 0) {
			return null;
		}
		
		return cacheDuration.value();
	}

	private static CacheDuration getCacheDurationAnnotation(String subclassName) {
		try {
			Class<?> subclass = Class.forName(subclassName);
			return subclass.getAnnotation(CacheDuration.class);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private static String getSubclassFromValueOf(Object object){
		if(object instanceof Item){
			return ((Item) object).getSubclass();
		} else if(object instanceof CacheEntry){
			return ((CacheEntry) object).getSubclass();
		}
		
		return null;
	}
	
}
