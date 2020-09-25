package com.ccnet.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import cn.ffcs.memory.BeanListHandler;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.entity.SystemParams;

/**
 * 系统参数表
 * @author jackie wang
 *
 */
@Repository("systemParamDao")
public class SystemParamDao extends BaseDao<SystemParams> {
	
	/**
	 * 分页查询参数
	 * @param sc
	 * @param page
	 * @return
	 */
	public Page<SystemParams> findSystemParamByPage(SystemParams sp, Page<SystemParams> page,String queryParam) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());

		List<String> whereColumns = memory.parseWhereColumns(params, SystemParams.class, sp);
		if (CollectionUtils.isNotEmpty(whereColumns)) {
			sql.append(" where ").append(appendAnd(whereColumns));
		}
		//加上模糊查询
		if(CPSUtil.isNotEmpty(queryParam)){
			if(sql.toString().indexOf("where")<0){
			   sql.append(" where 1=1 ");
			}
			sql.append(" and (param_key like ?  or param_value like ?) ");
			params.add("%" + queryParam + "%");
			params.add("%" + queryParam + "%");
		}
		
		//加上排序
		sql.append(" order by param_key asc ");
		super.queryPager(sql.toString(), new BeanListHandler<SystemParams>(SystemParams.class), params, page);
		return page;
	}
	
	/**
	 * 保存参数
	 * @param sp
	 * @return
	 */
	public boolean saveSystemParam(SystemParams sp) {
		if(insert(sp)>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 批量保存参数
	 * @param spList
	 * @return
	 */
	public boolean updateSystemParamList(List<SystemParams> spList) {
	    return updateBatch(spList, "paramKey");
	}
	
	/**
	 * 修改参数
	 * @param sp
	 * @return
	 */
	public boolean editSystemParam(SystemParams sp) {
		if(update(sp, "paramKey")>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 获取所有参数数据
	 * @param sp
	 * @return
	 */
	public List<SystemParams> queryParamList(SystemParams sp){
		return findList(sp);
	}
	
	/**
	 * 获取参数
	 * @param sp
	 * @return
	 */
	public SystemParams findSystemParam(SystemParams sp) {
		return find(sp);
	}
	
	/**
	 * 删除参数
	 * @param sp
	 * @return
	 */
	public boolean trashSystemParam(SystemParams sp) {
		int rst = delete(sp);
		if(rst>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 批量删除字典
	 * @param list
	 * @return
	 */
	public boolean trashSystemParamList(List<SystemParams> list) {
		int rst [] = deleteBatch(list);
		if(rst.length>0 && rst.length==list.size()){
			return true;
		}else{
			return false;
		}
	}
	

}
