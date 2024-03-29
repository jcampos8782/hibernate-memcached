package br.com.dlbca.hibernate.memcached.region;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.Region;

import br.com.dlbca.hibernate.memcached.MemcachedCache;
import br.com.dlbca.hibernate.memcached.generator.LUID;

public abstract class MemcachedRegion implements Region {
	
	private static final String CACHE_LOCK_TIMEOUT_PROPERTY = "hibernate.memcached.cache_lock_timeout";
	private static final String CACHE_DURATION_PROPERTY = "hibernate.memcached.cache_duration_secs";
	private static final int DEFAULT_CACHE_LOCK_TIMEOUT = 1000;
	private static final int DEFAULT_CACHE_DURATION_SECS = 60;
	
	protected final MemcachedCache cache;
	
	private final int cacheLockTimeout;
	protected final int cacheDurationInSecs;
	
	public MemcachedRegion(MemcachedCache cache, Properties properties) {
		this.cache = cache;
		String timeout = properties.getProperty(CACHE_LOCK_TIMEOUT_PROPERTY);
		cacheLockTimeout = timeout == null ? DEFAULT_CACHE_LOCK_TIMEOUT : Integer.parseInt(timeout);
		String cacheDuration = properties.getProperty(CACHE_DURATION_PROPERTY);
		cacheDurationInSecs = cacheDuration == null ? DEFAULT_CACHE_DURATION_SECS : Integer.parseInt(cacheDuration);
	}
	
	public String getName() {
		return cache.getName();
	}
	
	public boolean contains(Object key) {
		return cache.exists(key.toString());
	}
	
	public void destroy() throws CacheException {
		cache.destroy();
	}
	
	public long getSizeInMemory() {
		return -1;
	}
	
	public long getElementCountInMemory() {
		return -1;
	}
	
	public long getElementCountOnDisk() {
		return -1;
	}
	
	@SuppressWarnings("rawtypes")
	public Map toMap() {
		return new HashMap();
	}
	
	public long nextTimestamp() {
		return LUID.next();
	}
	
	public int getTimeout() {
		return cacheLockTimeout;
	}
	
	public MemcachedCache getMemcachedCache() {
		return cache;
	}
}
