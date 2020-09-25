package com.ccnet.core.common.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.ccnet.core.entity.UserInfo;

public class IndexCache implements DataCacheInterface<Class, Integer>{

	private ConcurrentHashMap<Class, AtomicInteger> cache = new ConcurrentHashMap<Class, AtomicInteger>();
	
	@Override
	public void init() {
		refresh();
	}

	@Override
	public void refresh() {
		synchronized (cache) {
			cache.put(UserInfo.class, new AtomicInteger());
		}
	}

	@Override
	public Integer get(Class k) {
		return cache.get(k).incrementAndGet();
	}

	 
}
