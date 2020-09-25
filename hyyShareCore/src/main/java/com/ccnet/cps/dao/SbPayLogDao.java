package com.ccnet.cps.dao;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.ffcs.memory.BeanListHandler;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.cps.entity.SbPayLog;
/**
 * 支付日志
 * @author zqy
 *
 */
@Repository("sbPayLogDao")
public class SbPayLogDao extends BaseDao<SbPayLog>{
	
	public Page<SbPayLog> findSbPayLogByPage(SbPayLog sbPayLog,Page<SbPayLog> page,Dto dtoParam){
		//获取参数
		String queryParam = dtoParam.getAsString("queryParam");
		//日期过滤
		String end_date = dtoParam.getAsString("end_date");
		String start_date = dtoParam.getAsString("start_date");
		
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName()).append(" where 1=1 ");
	
		if(CPSUtil.isNotEmpty(queryParam)){
        	appendWhere(sql);
        	sql.append(" and pay_account like ? or account_name like ? ");
			params.add("%"+queryParam+"%");
			params.add("%"+queryParam+"%");
		}
		
		//带上日期查询
		if(CPSUtil.isNotEmpty(start_date)){
			appendWhere(sql);
			sql.append(" and create_time >=? ");
			params.add(start_date+" 00:00:00");
		}
		
	    if(CPSUtil.isNotEmpty(end_date)){
	    	appendWhere(sql);
	    	sql.append(" and create_time <=? ");
			params.add(end_date+" 23:59:59");
		}
	  
		//加上排序
		sql.append(" order by create_time desc ");
		super.queryPager(sql.toString(), new BeanListHandler<SbPayLog>(SbPayLog.class), params, page);
		return page;
}
}
