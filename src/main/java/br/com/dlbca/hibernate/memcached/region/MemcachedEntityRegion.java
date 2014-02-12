package br.com.dlbca.hibernate.memcached.region;

import java.util.Properties;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.CacheDataDescription;
import org.hibernate.cache.spi.EntityRegion;
import org.hibernate.cache.spi.access.AccessType;
import org.hibernate.cache.spi.access.EntityRegionAccessStrategy;
import org.hibernate.cfg.Settings;

import br.com.dlbca.hibernate.memcached.MemcachedCache;
import br.com.dlbca.hibernate.memcached.strategy.NonStrictReadWriteMemcachedEntityRegionAccessStrategy;
import br.com.dlbca.hibernate.memcached.strategy.ReadOnlyMemcachedEntityRegionAccessStrategy;
import br.com.dlbca.hibernate.memcached.strategy.ReadWriteMemcachedEntityRegionAccessStrategy;

public class MemcachedEntityRegion extends MemcachedTransactionalRegion implements EntityRegion {
	
	public MemcachedEntityRegion(MemcachedCache cache, Settings settings, CacheDataDescription metadata, Properties properties) {
		super(cache, settings, metadata, properties);
	}

	public EntityRegionAccessStrategy buildAccessStrategy(AccessType accessType) throws CacheException {
		if(AccessType.READ_ONLY.equals(accessType)) {
			return new ReadOnlyMemcachedEntityRegionAccessStrategy(this, settings);
		} else if(AccessType.READ_WRITE.equals(accessType)) {
			return new ReadWriteMemcachedEntityRegionAccessStrategy(this, settings);
		} else if(AccessType.NONSTRICT_READ_WRITE.equals(accessType)) {
			return new NonStrictReadWriteMemcachedEntityRegionAccessStrategy(this, settings);
		} else if(AccessType.TRANSACTIONAL.equals(accessType)) {
			throw new CacheException("Transactional access is not supported by Memcached region factory");
		} else {
			throw new IllegalArgumentException("Unknown access strategy type - " + accessType);
		}
	}

}
