package br.com.dlbca.hibernate.memcached.duration;

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
		Item item = (Item) object;
		CacheDuration cacheDuration = getCacheDurationAnnotation(item);
		
		if(cacheDuration == null || cacheDuration.value() == 0) {
			return null;
		}
		
		return cacheDuration.value();
	}

	private static CacheDuration getCacheDurationAnnotation(Item item) {
		try {
			Class<?> subclass = Class.forName(item.getSubclass());
			return subclass.getAnnotation(CacheDuration.class);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
