package com.ccnet.cps.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.cps.entity.SbMoneyCount;
import com.ccnet.cps.entity.SbSadvertInfo;
import com.ccnet.cps.entity.SbVisitMoney;

import cn.ffcs.memory.BeanListHandler;


/**
 * 邀请奖励资金
 * @author JackieWang
 *
 */
@Repository("sbVisitMoneyDao")
public class SbVisitMoneyDao extends BaseDao<SbVisitMoney>{

	public Page<SbVisitMoney> findSbVisitMoneyByPage(SbVisitMoney sbVisitMoney,Page<SbVisitMoney> page,Dto dtoParam){
		//获取参数
		String queryParam = dtoParam.getAsString("queryParam");
		//日期过滤
		String end_date = dtoParam.getAsString("end_date");
		String start_date = dtoParam.getAsString("start_date");
		
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());

		List<String> whereColumns = memory.parseWhereColumns(params, SbVisitMoney.class, sbVisitMoney);
		if (CollectionUtils.isNotEmpty(whereColumns)) {
			sql.append(" where ").append(appendAnd(whereColumns));
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
		super.queryPager(sql.toString(), new BeanListHandler<SbVisitMoney>(SbVisitMoney.class), params, page);
		return page;
	}
	
	
	/**
	 * 查询奖励
	 * @param sbVisitMoney
	 * @param dtoParam
	 * @return
	 */
	public List<SbVisitMoney> queryVisitMoneyList(SbVisitMoney sbVisitMoney,Dto paramDto) {
		
		//获取参数
		String queryParam = paramDto.getAsString("queryParam");
		//日期过滤
		String end_date = paramDto.getAsString("end_date");
		String start_date = paramDto.getAsString("start_date");
		
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());

		List<String> whereColumns = memory.parseWhereColumns(params, SbVisitMoney.class, sbVisitMoney);
		if (CollectionUtils.isNotEmpty(whereColumns)) {
			sql.append(" where ").append(appendAnd(whereColumns));
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
		
		return memory.query(sql, new BeanListHandler<SbVisitMoney>(SbVisitMoney.class), params);
	}
	
	
	/**
	 * 根据用户ID获取邀请奖励
	 * @param userIds
	 * @param paramDto
	 * @return List<SbVisitMoney>
	 */
	public List<SbVisitMoney> queryVisitMoneyByVisitCodes(List<Integer> userIds,Dto paramDto) {
		
		String endDate = paramDto.getAsString("end_date");
		String startDate = paramDto.getAsString("start_date");
		
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		sql.append(" where 1=1 ");
		memory.in(sql, params, "and", "user_id", userIds);
		//带上日期查询
		if (CPSUtil.isNotEmpty(startDate)) {
			appendWhere(sql);
			sql.append(" and create_time >=? ");
			params.add(startDate + " 00:00:00");
		}

		if (CPSUtil.isNotEmpty(endDate)) {
			appendWhere(sql);
			sql.append(" and create_time <=? ");
			params.add(endDate + " 23:59:59");
		}
		sql.append(" order by create_time desc");
		List<SbVisitMoney> list = memory.query(sql, new BeanListHandler<SbVisitMoney>(SbVisitMoney.class), params);
		return list;
	}
	
	
	
}
