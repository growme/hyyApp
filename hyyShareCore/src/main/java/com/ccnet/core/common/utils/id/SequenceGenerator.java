package com.ccnet.core.common.utils.id;

public interface SequenceGenerator {
	public long next() throws CreateSequnceException;
}
