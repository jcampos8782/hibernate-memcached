package br.com.dlbca.hibernate.memcached.strategy;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.CollectionRegion;
import org.hibernate.cache.spi.access.CollectionRegionAccessStrategy;
import org.hibernate.cache.spi.access.SoftLock;
import org.hibernate.cfg.Settings;

import br.com.dlbca.hibernate.memcached.region.MemcachedCollectionRegion;

public class NonStrictReadWriteMemcachedCollectionRegionAccessStrategy extends AbstractMemcachedAccessStrategy<MemcachedCollectionRegion>
implements CollectionRegionAccessStrategy {

	public NonStrictReadWriteMemcachedCollectionRegionAccessStrategy(MemcachedCollectionRegion region, Settings settings) {
		super(region, settings);
	}

	public Object get(Object key, long txTimestamp) throws CacheException {
		return region.get(key);
	}

	public SoftLock lockItem(Object key, Object version) throws CacheException {
		return null;
	}

	public void unlockItem(Object key, SoftLock lock) throws CacheException {
	}

	public CollectionRegion getRegion() {
		return region;
	}
	
	public void remove(Object key) throws CacheException {
		region.remove(key);
	}

	@Override
	public boolean putFromLoad(Object key, Object value, long txnTimestamp, Object version, boolean minimumPutOverride) throws CacheException {
		if(minimumPutOverride && region.contains(key)) {
			return false;
		} else {
			region.put(key, value);
			return true;
		}
	}

}
