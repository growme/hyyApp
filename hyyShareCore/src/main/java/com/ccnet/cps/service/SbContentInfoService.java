package com.ccnet.cps.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ccnet.core.common.StateType;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.entity.UserInfo;
import com.ccnet.core.service.base.BaseService;
import com.ccnet.cps.entity.SbContentInfo;

/**
 * 文章业务管理
 * ClassName: SbContentInfoService 
 * @author Jackie Wang
 * @company 北京简智科技
 * @copyright 版权所有 盗版必究
 * @date 2017-8-10
 */
public interface SbContentInfoService extends BaseService<SbContentInfo>{


	/**
	 * 分页查询(多过滤)
	 * @param contentInfo
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<SbContentInfo> findSbContentInfoByPage(SbContentInfo contentInfo, Page<SbContentInfo> page,Dto pdDto);
	
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
	 * 获取文章信息
	 * @param contentInfo
	 * @return
	 */
	public SbContentInfo findSbContentInfo(SbContentInfo contentInfo);
	
	/**
	 * 获取文章集合
	 * @param contentInfo
	 * @return
	 */
	public List<SbContentInfo> findSbContentInfoList(SbContentInfo contentInfo);
	
	/**
	 * 随机查询
	 * @param contentInfo
	 * @param limit 条数
	 * @return
	 */
	public List<SbContentInfo> getRandContentList(SbContentInfo contentInfo,int limit);
	
	/**
	 * 根据ID获取文章
	 * @param contentIds
	 * @return
	 */
	public List<SbContentInfo> getSbContentListByIds(List<Integer> contentIds);
	
	/**
	 * 转换集合数据
	 * @param contentIds
	 * @return
	 */
	public Map<Integer, SbContentInfo> getSbContentInfoMapByIds(List<Integer> contentIds);
	
	/**
	 * 获取单个文章
	 * @param contentId
	 * @return
	 */
	public SbContentInfo getSbContentInfoByID(Integer contentId);
	
	/**
	 * 批量发布/取消发布文章
	 * @param contentIds
	 * @param state
	 * @return
	 */
	public boolean pushSbContentInfo(String contentIds,StateType state);
	
	/**
	 * 处理文章阅读数
	 * @param contentId
	 * @param addNum
	 * @return
	 */
	public boolean addContentReadNum(Integer contentId,Integer addNum);
	
	/**
	 * 处理文章分享数
	 * @param contentId
	 * @param addNum
	 * @return
	 */
	public boolean addContentShareNum(Integer contentId,Integer addNum);
	
	/**
	 * 保存文章
	 * @param contentInfo
	 * @return
	 */
	public boolean saveSbContentInfo(SbContentInfo contentInfo);
	
	/**
	 * 修改文章
	 * @param contentInfo
	 * @return
	 */
	public boolean editSbContentInfo(SbContentInfo contentInfo);
	
	/**
	 * 批量删除文章
	 * @param contentIds
	 * @return
	 */
	public boolean trashSbContentInfo(String contentIds);
	
	/**
	 * 删除文章
	 * @param contentInfo
	 * @return
	 */
	public boolean trashSbContentInfo(SbContentInfo contentInfo);
	
	/**
	 * 批量删除文章
	 * @param list
	 * @return
	 */
	public boolean trashSbContentInfoList(List<SbContentInfo> list);
	
	/**
	 * 获取当前文章的上一篇列表
	 */
	public Page<SbContentInfo> getUpContent(SbContentInfo contentInfo, Page<SbContentInfo> page,Dto pdDto);
	
	/**
	 * 获取当前文章的下一篇列表
	 */
	public Page<SbContentInfo> getNextContent(SbContentInfo contentInfo, Page<SbContentInfo> page,Dto pdDto);
	
	/**
	 * 采集文章保存
	 * @param contentInfo
	 * @return
	 */
	public boolean saveGatherContent(Dto cDto ,UserInfo userInfo);
	
	/**
	 * 处理阅读增加
	 * @param contentId
	 * @param addNum
	 */
	public int updateContentReadNum(Integer contentId,Integer addNum);
	
	/**
	 * 处理分享增加
	 * @param contentId
	 * @param addNum
	 */
	public int updateContentShareNum(Integer contentId,Integer addNum);
	
}
