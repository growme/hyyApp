package com.ccnet.cps.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.dao.impl.UserInfoDao;
import com.ccnet.core.entity.UserInfo;
import com.ccnet.core.service.impl.BaseServiceImpl;
import com.ccnet.cps.dao.SbColumnInfoDao;
import com.ccnet.cps.entity.SbColumnInfo;
import com.ccnet.cps.service.SbColumnInfoService;
/**
 * 里面业务层
 * ClassName: SbColumnInfoServiceImpl 
 * @author Jackie Wang
 * @company 北京简智科技
 * @copyright 版权所有 盗版必究
 * @date 2017-8-10
 */
@Service("sbColumnInfoService")
public class SbColumnInfoServiceImpl extends BaseServiceImpl<SbColumnInfo> implements SbColumnInfoService{

	@Autowired
	private SbColumnInfoDao columnInfoDao;
	@Autowired
	private UserInfoDao userInfoDao;
	
	/**
	 * 分页查询(多过滤)
	 * @param columnInfo
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<SbColumnInfo> findSbColumnInfoByPage(SbColumnInfo columnInfo, Page<SbColumnInfo> page,Dto pdDto){
		Page columnPage = columnInfoDao.findSbColumnInfoByPage(columnInfo, page, pdDto);
		List<SbColumnInfo> cList = columnPage.getResults();
		if(!CPSUtil.checkListBlank(cList)){
			//处理操作人
			List<Integer> userIds = columnInfoDao.getValuesFromField(cList, "userId");
			Map<Integer, UserInfo> userMap = userInfoDao.getUserMapByIds(userIds);
			for (SbColumnInfo column : cList) {
				if(CPSUtil.isNotEmpty(column.getUserId())){
					column.setUserInfo(userMap.get(column.getUserId()));
				}
			}
		}
		return columnPage;
		
	}
	
	/**
	 * 日期统计
	 * @param startDate
	 * @param endDate
	 * @return int  
	 * @throws
	 * @author Jackie Wang
	 * @date 2017-8-10
	 */
	public int count(Date startDate, Date endDate){
		return columnInfoDao.count(startDate, endDate);
	}
	
	/**
	 * 获取column信息
	 * @param SbColumnInfo
	 * @return
	 */
	public SbColumnInfo findSbColumnInfo(SbColumnInfo SbColumnInfo){
		return columnInfoDao.findSbColumnInfo(SbColumnInfo);
	}
	
	/**
	 * 查询栏目
	 * @param columnInfo
	 * @return
	 */
	public List<SbColumnInfo> getColumnInfoList(SbColumnInfo columnInfo){
		return columnInfoDao.getColumnInfoList(columnInfo);
	}
	
	/**
	 * 根据ID获取栏目
	 * @param columnIds
	 * @return
	 */
	public List<SbColumnInfo> getColumnListByIds(List<Integer> columnIds){
		return columnInfoDao.getColumnListByIds(columnIds);
	}
	
	/**
	 * 转换集合数据
	 * @param columnIds
	 * @return
	 */
	public Map<Integer, SbColumnInfo> getSbColumnInfoMapByIds(List<Integer> columnIds){
		return columnInfoDao.getSbColumnInfoMapByIds(columnIds);
	}
	
	/**
	 * 获取单个栏目
	 * @param columnId
	 * @return
	 */
	public SbColumnInfo getColumnByID(Integer columnId){
		return columnInfoDao.getColumnByID(columnId);
	}
	
	
	/**
	 * 保存栏目
	 * @param SbColumnInfo
	 * @return
	 */
	public boolean saveSbColumnInfo(SbColumnInfo SbColumnInfo){
		return columnInfoDao.saveSbColumnInfo(SbColumnInfo);
	}
	
	/**
	 * 修改栏目
	 * @param SbColumnInfo
	 * @return
	 */
	public boolean editSbColumnInfo(SbColumnInfo SbColumnInfo){
		return columnInfoDao.editSbColumnInfo(SbColumnInfo);
	}
	
	/**
	 * 删除用户
	 * @param SbColumnInfo
	 * @return
	 */
	public boolean trashSbColumnInfo(SbColumnInfo SbColumnInfo){
		return columnInfoDao.trashSbColumnInfo(SbColumnInfo);
	}
	
	/**
	 * 批量删除里面
	 * @param list
	 * @return
	 */
	public boolean trashSbColumnInfoList(List<SbColumnInfo> list){
		return columnInfoDao.trashSbColumnInfoList(list);
	}
	
	/**
	 * 注入dao
	 */
	protected BaseDao<SbColumnInfo> getDao() {
		return this.columnInfoDao;
	}

	

}
