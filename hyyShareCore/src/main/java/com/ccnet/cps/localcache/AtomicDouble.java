package com.ccnet.cps.localcache;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicDouble extends Number {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8451927148714884272L;

	private AtomicReference<Double> value;

	public AtomicDouble() {
		this(0.0);
	}

	public AtomicDouble(double initVal) {
		value = new AtomicReference<Double>(new Double(initVal));
	}

	public double get() {
		return value.get().doubleValue();
	}

	public void set(double newVal) {
		value.set(new Double(newVal));
	}

	public boolean compareAndSet(double expect, double update) {
		Double origVal, newVal;

		newVal = new Double(update);
		while (true) {
			origVal = value.get();

			if (Double.compare(origVal.doubleValue(), expect) == 0) {
				if (value.compareAndSet(origVal, newVal)) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

	public boolean weakCompareAndSet(double expect, double update) {
		return compareAndSet(expect, update);
	}

	public double getAndSet(double setVal) {
		Double origVal, newVal;

		newVal = new Double(setVal);
		while (true) {
			origVal = value.get();
			if (value.compareAndSet(origVal, newVal)) {
				return origVal.doubleValue();
			}
		}
	}

	public double getAndAdd(double delta) {
		Double origVal, newVal;

		while (true) {
			origVal = value.get();
			newVal = new Double(origVal.doubleValue() + delta);
			if (value.compareAndSet(origVal, newVal)) {
				return origVal.doubleValue();
			}
		}
	}

	public double addAndGet(double delta) {
		Double origVal, newVal;

		while (true) {
			origVal = value.get();
			newVal = new Double(origVal.doubleValue() + delta);
			if (value.compareAndSet(origVal, newVal)) {
				return newVal.doubleValue();
			}
		}
	}

	public double getAndIncrement() {
		return getAndAdd((double) 1.0);
	}

	public double getAndDecrement() {
		return addAndGet((double) -1.0);
	}

	public double incrementAndGet() {
		return addAndGet((double) 1.0);
	}

	public double decrementAndGet() {
		return addAndGet((double) -1.0);
	}

	public double getAndMultiply(double multiple) {
		Double origVal, newVal;

		while (true) {
			origVal = value.get();
			newVal = new Double(origVal.doubleValue() * multiple);
			if (value.compareAndSet(origVal, newVal)) {
				return origVal.doubleValue();
			}
		}
	}

	public double multiplyAndGet(double multiple) {
		Double origVal, newVal;

		while (true) {
			origVal = value.get();
			newVal = new Double(origVal.doubleValue() * multiple);
			if (value.compareAndSet(origVal, newVal)) {
				return newVal.doubleValue();
			}
		}
	}

	@Override
	public int intValue() {
		return (int)get();
	}

	@Override
	public long longValue() {
		return (long)get();
	}

	@Override
	public float floatValue() {
		return (float)get();
	}

	@Override
	public double doubleValue() {
		return get();
	}
}