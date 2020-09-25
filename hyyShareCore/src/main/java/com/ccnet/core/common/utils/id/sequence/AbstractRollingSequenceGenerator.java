package com.ccnet.core.common.utils.id.sequence;

import com.ccnet.core.common.utils.id.CreateSequnceException;

public abstract class AbstractRollingSequenceGenerator extends DefaultSequenceGenerator{
	
	public long next() throws CreateSequnceException {
		if ( isResetCount() ){
			this.currCount = this.minValue;
			maxCount = this.currCount;
			sequenceStorer.updateMaxValueByFieldName(maxCount, this.getId());
		}
		return super.next();
	}
	
   abstract protected boolean isResetCount();

}
