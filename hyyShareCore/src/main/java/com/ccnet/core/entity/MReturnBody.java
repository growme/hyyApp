package com.ccnet.core.entity;

public class MReturnBody extends BaseEntity {

	private static final long serialVersionUID = 2661125402796873284L;

	public String key;
	public String hash;
	public long fsize;
	public String bucket;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getBucket() {
		return bucket;
	}

	public void setBucket(String bucket) {
		this.bucket = bucket;
	}

	public long getFsize() {
		return fsize;
	}

	public void setFsize(long fsize) {
		this.fsize = fsize;
	}

}
