package com.ccnet.core.common.utils.id;

public interface SequenceStorer {

	/**
	 * 保存序号
	 * 
	 * @param pSequence
	 *            序号
	 * @param pSequenceID
	 *            序号ID
	 * @throws StoreSequenceException
	 */
	public void updateMaxValueByFieldName(long pSequence, final String pSequenceID)
			throws StoreSequenceException;

	/**
	 * 
	 * @param pSequenceID
	 *            序号ID
	 * @return
	 * @throws StoreSequenceException
	 */
	public long load(final String pSequenceID) throws StoreSequenceException;
}
