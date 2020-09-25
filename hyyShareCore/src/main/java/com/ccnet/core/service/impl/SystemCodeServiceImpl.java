package com.ccnet.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ccnet.core.common.cache.InitSystemCache;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.dao.impl.SystemCodeDao;
import com.ccnet.core.entity.SystemCode;
import com.ccnet.core.service.SystemCodeService;
@Service("systemCodeService")
public class SystemCodeServiceImpl extends BaseServiceImpl<SystemCode> implements SystemCodeService {

	@Autowired
	private SystemCodeDao systemCodeDao;
	
	/**
	 * 分页查询字典
	 * @param sc
	 * @param page
	 * @return
	 */
	public Page findSystemCodeByPage(SystemCode sc, Page<SystemCode> page,String queryParam){
		return systemCodeDao.findSystemCodeByPage(sc, page,queryParam);
	}
	
	/**
	 * 保存字典
	 * @param sc
	 * @return
	 */
	@Transactional
	public boolean saveSystemCode(SystemCode sc) {
		boolean temp = systemCodeDao.saveSystemCode(sc);
		if(temp){
		   InitSystemCache.updateCache(Const.CT_CODE_LIST);
		}
		return temp;
	}
	
	
	/**
	 * 获取所有字典数据
	 * @return
	 */
	public List<SystemCode> queryCodeList(){
		return systemCodeDao.queryCodeList(new SystemCode());
	}
	
	/**
	 * 根据字典key获取组
	 * @param code_key
	 * @return
	 */
	public List<SystemCode> queryCodeListByKey(String code_key){
		SystemCode systemCode = new SystemCode();
		systemCode.setCodeKey(code_key);
		return systemCodeDao.queryCodeListByKey(systemCode);
	}
	
	/**
	 * 获取字典
	 * @param code_id
	 * @return
	 */
	public SystemCode findSystemCodeByID(String code_id) {
		SystemCode sc = new SystemCode();
		sc.setCodeId(code_id);
		return systemCodeDao.findSystemCodeByID(sc);
	}
	
	/**
	 * 修改字典
	 * @param sc
	 * @return
	 */
	@Transactional
	public boolean editSystemCode(SystemCode sc){
		boolean temp = systemCodeDao.editSystemCode(sc);
		if(temp){
		   InitSystemCache.updateCache(Const.CT_CODE_LIST);
		}
		return temp;
	}
	
	/**
	 * 删除字典
	 * @param code_id
	 * @return
	 */
	@Transactional
	public boolean trashSystemCode(String code_id){
		SystemCode sc = new SystemCode();
		sc.setCodeId(code_id);
		boolean temp = systemCodeDao.trashSystemCode(sc);
		if(temp){
		   InitSystemCache.updateCache(Const.CT_CODE_LIST);
		}
		return temp;
	}
	
	/**
	 * 批量删除字典
	 * @param code_ids
	 * @return
	 */
	@Transactional
	public boolean trashSystemCodeList(String code_ids){
		int rst[] = null;
		SystemCode sc = null;
		String idString[] = null;
		List<SystemCode> cList = null;
		if(CPSUtil.isNotEmpty(code_ids)){
			cList = new ArrayList<SystemCode>();
			idString = code_ids.split(",");
			for (String cid : idString) {
				sc = new SystemCode();
				sc.setCodeId(cid);
				cList.add(sc);
			}
			rst = systemCodeDao.deleteBatch(cList);
		}
		if(rst!=null && rst.length>0){
			InitSystemCache.updateCache(Const.CT_CODE_LIST);
			return true;
		}else{
			return false;
		}
	}

	@Override
	protected BaseDao<SystemCode> getDao() {
		// TODO Auto-generated method stub
		return this.systemCodeDao;
	}

}
