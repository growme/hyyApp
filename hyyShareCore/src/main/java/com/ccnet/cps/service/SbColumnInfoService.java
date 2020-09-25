package com.ccnet.cps.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.base.BaseService;
import com.ccnet.cps.entity.SbColumnInfo;

/**
 * 栏目业务层
 * ClassName: SbColumnInfoService 
 * @author Jackie Wang
 * @company 北京简智科技
 * @copyright 版权所有 盗版必究
 * @date 2017-8-10
 */
public interface SbColumnInfoService extends BaseService<SbColumnInfo>
{


	/**
	 * 分页查询(多过滤)
	 * @param columnInfo
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<SbColumnInfo> findSbColumnInfoByPage(SbColumnInfo columnInfo, Page<SbColumnInfo> page,Dto pdDto);
	
	/**
	 * 日期统计
	 * @param startDate
	 * @param endDate
	 * @return int  
	 * @throws
	 * @author Jackie Wang
	 * @date 2017-8-10
	 */
	public int count(Date startDate, Date endDate);
	
	/**
	 * 获取column信息
	 * @param SbColumnInfo
	 * @return
	 */
	public SbColumnInfo findSbColumnInfo(SbColumnInfo SbColumnInfo);
	
	/**
	 * 查询栏目
	 * @param columnInfo
	 * @return
	 */
	public List<SbColumnInfo> getColumnInfoList(SbColumnInfo columnInfo);
	
	/**
	 * 根据ID获取栏目
	 * @param columnIds
	 * @return
	 */
	public List<SbColumnInfo> getColumnListByIds(List<Integer> columnIds);
	
	/**
	 * 转换集合数据
	 * @param columnIds
	 * @return
	 */
	public Map<Integer, SbColumnInfo> getSbColumnInfoMapByIds(List<Integer> columnIds);
	
	/**
	 * 获取单个栏目
	 * @param columnId
	 * @return
	 */
	public SbColumnInfo getColumnByID(Integer columnId);
	
	
	/**
	 * 保存栏目
	 * @param SbColumnInfo
	 * @return
	 */
	public boolean saveSbColumnInfo(SbColumnInfo SbColumnInfo);
	
	/**
	 * 修改栏目
	 * @param SbColumnInfo
	 * @return
	 */
	public boolean editSbColumnInfo(SbColumnInfo SbColumnInfo);
	
	/**
	 * 删除用户
	 * @param SbColumnInfo
	 * @return
	 */
	public boolean trashSbColumnInfo(SbColumnInfo SbColumnInfo);
	
	/**
	 * 批量删除里面
	 * @param list
	 * @return
	 */
	public boolean trashSbColumnInfoList(List<SbColumnInfo> list);
	
   

}
