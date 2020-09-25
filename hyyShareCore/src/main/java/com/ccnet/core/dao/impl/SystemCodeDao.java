package com.ccnet.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import cn.ffcs.memory.BeanListHandler;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.entity.SystemCode;
/**
 * 数据字典数据层
 */
@Repository("systemCodeDao")
public class SystemCodeDao extends BaseDao<SystemCode>{
	
	
	/**
	 * 分页查询字典参数
	 * @param sc
	 * @param page
	 * @param queryParam
	 * @return
	 */
	public Page<SystemCode> findSystemCodeByPage(SystemCode sc, Page<SystemCode> page,String queryParam) {
		
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());

		List<String> whereColumns = memory.parseWhereColumns(params, SystemCode.class, sc);
		if (CollectionUtils.isNotEmpty(whereColumns)) {
			sql.append(" where ").append(appendAnd(whereColumns));
		}
		//加上模糊查询
		if(CPSUtil.isNotEmpty(queryParam)){
			if(sql.toString().indexOf("where")<0){
			   sql.append(" where 1=1 ");
			}
			sql.append(" and (code_key like ?  or code_name like ?) ");
			params.add("%" + queryParam + "%");
			params.add("%" + queryParam + "%");
		}
		
		//加上排序
		sql.append(" order by code_key asc ");
		super.queryPager(sql.toString(), new BeanListHandler<SystemCode>(SystemCode.class), params, page);
		return page;
		
	}
	
	/**
	 * 获取字典
	 * @param sc
	 * @return
	 */
	public SystemCode findSystemCodeByID(SystemCode sc) {
		return find(sc);
	}
	
	/**
	 * 保存字典
	 * @param sc
	 * @return
	 */
	public boolean saveSystemCode(SystemCode sc) {
		if(insert(sc)>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 修改字典
	 * @param sc
	 * @return
	 */
	public boolean editSystemCode(SystemCode sc) {
		if(update(sc, "codeId")>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 获取所有字典数据
	 * @return
	 */
	public List<SystemCode> queryCodeList(SystemCode sc){
		return findList(sc);
	}
	
	/**
	 * 根据字典key获取组
	 * @param code_key
	 * @return
	 */
	public List<SystemCode> queryCodeListByKey(SystemCode sc){
		return findList(sc);
	}
	
	
	/**
	 * 删除字典
	 * @param sc
	 * @return
	 */
	public boolean trashSystemCode(SystemCode sc) {
		int rst = delete(sc);
		if(rst>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 批量删除字典
	 * @param sc
	 * @return
	 */
	public boolean trashSystemCodeList(List<SystemCode> list) {
		int rst [] = deleteBatch(list);
		if(rst.length>0 && rst.length==list.size()){
			return true;
		}else{
			return false;
		}
	}

}
