package com.ccnet.core.common.utils.id.generator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ccnet.core.common.utils.id.CreateIDException;
import com.ccnet.core.common.utils.id.IDGenerator;
import com.ccnet.core.common.utils.id.PrefixGenerator;
import com.ccnet.core.common.utils.id.SequenceFormater;
import com.ccnet.core.common.utils.id.SequenceGenerator;
import com.ccnet.core.common.utils.id.sequence.DefaultSequenceGenerator;

public class DefaultIDGenerator implements IDGenerator {

	private PrefixGenerator prefixGenerator;
	private SequenceGenerator sequenceGenerator = new DefaultSequenceGenerator();
	private SequenceFormater sequenceFormater;

	private final Log logger = LogFactory.getLog(DefaultIDGenerator.class);

	public synchronized String create() throws CreateIDException {
		final String prefix = prefixGenerator == null ? "" : prefixGenerator.create();
		logger.debug("ID前缀是:[" + prefix + "]");
		long sequence = sequenceGenerator.next();
		final String strSequence = sequenceFormater == null ? new Long(sequence).toString() : sequenceFormater
				.format(sequence);
		return prefix + strSequence;
	}

	public void setPrefixGenerator(PrefixGenerator prefixGenerator) {
		this.prefixGenerator = prefixGenerator;
	}

	public void setSequenceGenerator(SequenceGenerator sequenceGenerator) {
		this.sequenceGenerator = sequenceGenerator;
	}

	public void setSequenceFormater(SequenceFormater sequenceFormater) {
		this.sequenceFormater = sequenceFormater;
	}

}
