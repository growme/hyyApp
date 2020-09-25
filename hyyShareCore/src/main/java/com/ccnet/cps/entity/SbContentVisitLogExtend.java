package com.ccnet.cps.entity;

public class SbContentVisitLogExtend extends SbContentVisitLog {

	private static final long serialVersionUID = 1L;

	private Integer count;
	private Integer ipCount;
	private Integer clickCount;
	private Integer readCount;
	private Integer touchCount;
	private Integer coordCount;
	private Integer expandCount;

	public SbContentVisitLogExtend() {

	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getIpCount() {
		return ipCount;
	}

	public void setIpCount(Integer ipCount) {
		this.ipCount = ipCount;
	}

	public Integer getClickCount() {
		return clickCount;
	}

	public void setClickCount(Integer clickCount) {
		this.clickCount = clickCount;
	}

	public Integer getReadCount() {
		return readCount;
	}

	public void setReadCount(Integer readCount) {
		this.readCount = readCount;
	}

	public Integer getTouchCount() {
		return touchCount;
	}

	public void setTouchCount(Integer touchCount) {
		this.touchCount = touchCount;
	}

	public Integer getCoordCount() {
		return coordCount;
	}

	public void setCoordCount(Integer coordCount) {
		this.coordCount = coordCount;
	}

	public Integer getExpandCount() {
		return expandCount;
	}

	public void setExpandCount(Integer expandCount) {
		this.expandCount = expandCount;
	}

}
