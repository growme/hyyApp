package com.ccnet.core.common.cache;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class DataCacheFactory {
	
	private ConcurrentHashMap<Class, DataCacheInterface> map = new ConcurrentHashMap<Class, DataCacheInterface>();
	
	private static DataCacheFactory instance;
	
	public static DataCacheFactory getInstance() {
		if (instance == null) {
			synchronized (DataCacheFactory.class) {
				if (instance == null) {
					instance = new DataCacheFactory();
				}
			}
		}
		return instance;
	}
	
	private DataCacheFactory() {
		DataCacheInterface dataCache = new IndexCache();
		dataCache.init();
		map.put(IndexCache.class, dataCache);
	}
	
	public DataCacheInterface getDateCache(Class clazz) {
		return map.get(clazz);
	}
	
	public void refresh() {
		Collection<DataCacheInterface> values = map.values();
		for (DataCacheInterface dataCacheInterface : values) {
			dataCacheInterface.refresh();
		}
	}
 	
}
