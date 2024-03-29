package br.com.dlbca.hibernate.memcached;

import java.io.IOException;
import java.util.Properties;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.ConnectionFactory;
import net.spy.memcached.MemcachedClient;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.CacheDataDescription;
import org.hibernate.cache.spi.NaturalIdRegion;
import org.hibernate.cfg.Settings;

public class MemcachedRegionFactory extends AbstractMemcachedRegionFactory {

	private static final long serialVersionUID = 3052887042898413129L;

	public void start(Settings settings, Properties properties) throws CacheException {
		this.settings = settings;
		this.properties = properties;
		setupConnection();
	}

	private void setupConnection() {
		try {
			String host = properties.getProperty("hibernate.memcached.host", "localhost");
			String port = properties.getProperty("hibernate.memcached.port", "11211");
			String connectionFactory = properties.getProperty("hibernate.memcached.spynet.connection_factory_class", 
					"net.spy.memcached.BinaryConnectionFactory");
			Class<?> clazz = Class.forName(connectionFactory);
			client = new MemcachedClient((ConnectionFactory)clazz.newInstance(), AddrUtil.getAddresses(host + ":" + port));
		} catch (IOException e) {
			throw new CacheException(e);
		} catch (ClassNotFoundException e) {
			throw new CacheException(e);
		} catch (InstantiationException e) {
			throw new CacheException(e);
		} catch (IllegalAccessException e) {
			throw new CacheException(e);
		}
	}

	public void stop() {
		client.shutdown();
	}

	@Override
	public NaturalIdRegion buildNaturalIdRegion(String regionName,
			Properties properties, CacheDataDescription metadata)
			throws CacheException {
		// TODO Auto-generated method stub
		return null;
	}

}
