package com.ccnet.core.common.cache;

public interface DataCacheInterface<K,V> {
	
	public abstract void init();
	
	public abstract void refresh();
	
	public abstract V get(K k);
	
	
}
