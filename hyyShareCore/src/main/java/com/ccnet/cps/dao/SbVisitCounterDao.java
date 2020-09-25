package com.ccnet.cps.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import cn.ffcs.memory.BeanListHandler;

import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.cps.entity.SbVisitCounter;

/**
 * 访问计数器 
 * @author JackieWang
 *
 */
@Repository("sbVisitCounterDao")
public class SbVisitCounterDao extends BaseDao<SbVisitCounter> {
	
	
	/**
	 * 获取计数信息
	 * @param counter
	 * @return
	 */
	public SbVisitCounter findSbVisitCounter(SbVisitCounter counter) {
		return find(counter);
	}
	
	
	/**
	 * 获取计数信息
	 * @param ip
	 * @return
	 */
	public SbVisitCounter findSbVisitCounterByIP(String ip) {
		SbVisitCounter counter = new SbVisitCounter();
		counter.setVisitIP(ip);
		return find(counter);
	}
	
	/**
	 * 查询计数信息
	 * @param counter
	 * @return
	 */
	public List<SbVisitCounter> findSbVisitCounterList(SbVisitCounter counter) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		sql.append(" where 1=1");
		sql.append(" order by visit_ip asc ");
		return memory.query(sql, new BeanListHandler<SbVisitCounter>(SbVisitCounter.class), params);
	}
	
	/**
	 * 根据IP获取计数
	 * @param ip
	 * @return
	 */
	public List<SbVisitCounter> getSbVisitCounterListByIds(List<String> ip) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		memory.in(sql, params, "where", "visit_ip", ip);
		List<SbVisitCounter> list = memory.query(sql, new BeanListHandler<SbVisitCounter>(SbVisitCounter.class), params);
		return list;
	}
	
	/**
	 * 转换集合数据
	 * @param ip
	 * @return
	 */
	public Map<String, SbVisitCounter> getSbVisitCounterMapByIds(List<String> ip) {
		List<SbVisitCounter> columnInfos = getSbVisitCounterListByIds(ip);
		Map<String, SbVisitCounter> map = new HashMap<String, SbVisitCounter>(0);
		if (CollectionUtils.isNotEmpty(columnInfos)) {
			for (SbVisitCounter counter : columnInfos) {
				map.put(counter.getVisitIP(), counter);
			}
		}
		return map;
	}
	
	/**
	 * 更新数据
	 * @param vc
	 * @return
	 */
	public int updateSbVisitCounter(SbVisitCounter vc) {
		StringBuilder afterOnUpdate = new StringBuilder();
		afterOnUpdate.append("total_count = total_count + VALUES(total_count)");
		return memory.createOrUpdate(memory.getConnection(), SbVisitCounter.class, vc, null,  afterOnUpdate.toString());
	}
	

}
