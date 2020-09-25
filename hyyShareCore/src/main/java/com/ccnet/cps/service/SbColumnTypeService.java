package com.ccnet.cps.service;

import java.util.List;

import com.ccnet.core.service.base.BaseService;
import com.ccnet.cps.entity.SbColumnInfo;
import com.ccnet.cps.entity.SbColumnType;


public interface SbColumnTypeService extends BaseService<SbColumnType>{
	/**得到栏目类型
	 * @return
	 */
	public List<SbColumnType> findList(SbColumnType sbColumnType);
	
}
