package br.com.dlbca.hibernate.memcached.region;

import java.util.Properties;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.CacheDataDescription;
import org.hibernate.cache.spi.TransactionalDataRegion;
import org.hibernate.cfg.Settings;

import br.com.dlbca.hibernate.memcached.MemcachedCache;
import br.com.dlbca.hibernate.memcached.duration.CacheDurationDefiner;

public class MemcachedTransactionalRegion extends MemcachedRegion implements TransactionalDataRegion {
	
	protected final Settings settings;
	protected final CacheDataDescription metadata;
	
	public MemcachedTransactionalRegion(MemcachedCache cache, Settings settings, CacheDataDescription metadata, Properties properties) {
		super(cache, properties);
		this.settings = settings;
		this.metadata = metadata;
	}

	public boolean isTransactionAware() {
		return false;
	}

	public CacheDataDescription getCacheDataDescription() {
		return metadata;
	}
	
	public Object get(Object key) throws CacheException {
		return cache.get(key);
	}
	
	public void put(Object key, Object value) throws CacheException {
		Integer duration = CacheDurationDefiner.getDurationFor(value);
		
		if(duration == null){
			put(key, cacheDurationInSecs, value);
			return;
		}
		
		put(key, duration, value);
	}
	
	public void put(Object key, int duration, Object value){
		cache.put(key, duration, value);
	}
	
	public void remove(Object key) throws CacheException {
		cache.remove(key);
	}
	
	public void clear() throws CacheException {
		cache.clear();
	}
	
	public boolean writeLock(Object key) {
		try {
			return cache.lock(key, 1000L);
		}catch(InterruptedException e) {
			return false;
		}
	}
	
	public void releaseLock(Object key) {
		cache.unlock(key);
	}

}
