package com.ccnet.core.common.utils.dataconvert.impl;

import java.util.Iterator;

import com.ccnet.core.common.utils.dataconvert.PKey;
import com.ccnet.core.common.utils.exception.NullAbleException;

/**
 * 非空数据传输对象(DateTransferObject)<br>
 * 建议对于****ByKey的数据操作的方法中的数据传递或者
 * 其他需要进行参数非空性校验的数据操作方法都使用此对象来传输参数<br>
 * 
 * @author wgp
 * @since 2013-01-15
 */
public class BasePKey extends BaseDto implements PKey {

	/**
	 * 对非空数据传输对象进行键值非空性校验
	 * 
	 * @throws Exception
	 * @throws Exception
	 */
	public void validateNullAble() {
		if (isEmpty()) {
			try {
				throw new NullAbleException(this.getClass());
			} catch (NullAbleException e) {
				e.printStackTrace();
				System.exit(0);
			}
		} else {
			Iterator keyIterator = keySet().iterator();
			while (keyIterator.hasNext()) {
				String key = (String) keyIterator.next();
				String value = getAsString(key);
				if (value == null || value.equals("")) {
					try {
						throw new NullAbleException(key);
					} catch (NullAbleException e) {
						e.printStackTrace();
						System.exit(0);
					}
				}
			}
		}
	}
}
