package com.ccnet.cps.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import cn.ffcs.memory.BeanHandler;
import cn.ffcs.memory.BeanListHandler;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.cps.entity.SbSadvertInfo;;

@Repository("sbSadvertInfoDao")
public class SbSadvertInfoDao extends BaseDao<SbSadvertInfo> {
	/**
	 * 保存一个对象
	 * 
	 * @param o
	 *            对象
	 * @return 对象的ID
	 */
	public int insert(SbSadvertInfo o) {
		int i = super.insert(o, null);
		return i;
	}
	
	public int insert(SbSadvertInfo o, String autoIncrementField) {
		int i = super.insert(o, autoIncrementField);
		return i;
	}

	/**
	 * 删除一个对象
	 * 
	 * @param  k 字段值
	 */
	public int delete(SbSadvertInfo o) {
		int i = super.delete(o);
		return i;
	}

	/**
	 * 更新一个对象
	 * 
	 * @param o
	 *            对象
	 */
	public int update(SbSadvertInfo o, String camelKey) {
		int i = super.update(o, camelKey);
		return i;
	}

	/**
	 * 批量删除一组对象
	 * 
	 * @param s
	 *   (主键)数组
	 */
	public int[] deleteBatch(List<SbSadvertInfo> list) {
		if (CollectionUtils.isEmpty(list)) {
			return new int[0];
		}
		int[] i = super.deleteBatch(list);
		return i;
	}
	

	/**
	 * 获得对象列表
	 * 
	 * @param o
	 *            对象
	 * @return List
	 */
	public List<SbSadvertInfo> findList(SbSadvertInfo o) {
		List<SbSadvertInfo> list = super.findList(o);
		return list;
	}
	
	public SbSadvertInfo find(SbSadvertInfo o) {
		SbSadvertInfo SbAdvertInfo = super.find(o);
		return SbAdvertInfo;
	}
	
	public void findByPage(SbSadvertInfo o, Page<SbSadvertInfo> page) {
		super.findByPage(o, page);
	}
	
	/**
	 * 分页查询广告(多过滤)
	 * @param content
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<SbSadvertInfo> findSbContentInfoByPage(SbSadvertInfo content, Page<SbSadvertInfo> page,Dto pdDto){
		//获取参数
		String queryParam = pdDto.getAsString("queryParam");
		String state = pdDto.getAsString("ad_state");
		//日期过滤
		String end_date = pdDto.getAsString("end_date");
		String start_date = pdDto.getAsString("start_date");
		
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());

		List<String> whereColumns = memory.parseWhereColumns(params, SbSadvertInfo.class, content);
		if (CollectionUtils.isNotEmpty(whereColumns)) {
			sql.append(" where ").append(appendAnd(whereColumns));
		}
		
		//加上模糊查询
		if(CPSUtil.isNotEmpty(queryParam)){
			//加密参数
			//queryParam = CPSUtil.encryptOrderDes(queryParam);
			appendWhere(sql);
			sql.append(" and (ad_name like ? or remark like ? or ad_id = ? ) ");
			params.add("%"+queryParam+"%");
			params.add("%"+queryParam+"%");
			params.add(queryParam);
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
        
        if(CPSUtil.isNotEmpty(state)){
        	appendWhere(sql);
        	sql.append(" and state =? ");
			params.add(state);
		}
		//加上排序
		sql.append(" order by create_time desc ");
		super.queryPager(sql.toString(), new BeanListHandler<SbSadvertInfo>(SbSadvertInfo.class), params, page);
		return page;
		
	}
	
	public int count(Date startDate, Date endDate) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select count(1) from ").append(getCurrentTableName());
		sql.append(" where 1=1");

		// 带上日期查询
		if (startDate != null) {
			sql.append(" and create_time >=? ");
			params.add(startDate);
		}
		if (endDate != null) {
			sql.append(" and create_time < ? ");
			params.add(endDate);
		}
		return super.count(sql, params);
	}
	
	/**
	 * 获取会员信息
	 * @param SbContentInfo
	 * @return
	 *//*
	public SbAdvertInfo findSbAdvertInfo(SbAdvertInfo SbAdvertInfo) {
		return find(SbAdvertInfo);
	}
	
	*//**
	 * 根据ID获取广告集合
	 * @param userIds
	 * @return
	 */
	public List<SbSadvertInfo> getAdListByIds(List<Integer> adIds) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		memory.in(sql, params, "where", "ad_id", adIds);
		List<SbSadvertInfo> list = memory.query(sql, new BeanListHandler<SbSadvertInfo>(SbSadvertInfo.class), params);
		return list;
	}
	
	/**
	 * 转换集合数据
	 * @param userIds
	 * @return
	 */
	public Map<Integer, SbSadvertInfo> getAdMapByIds(List<Integer> adIds) {
		List<SbSadvertInfo> SbAdvertInfos = getAdListByIds(adIds);
		Map<Integer, SbSadvertInfo> map = new HashMap<Integer, SbSadvertInfo>(0);
		if (CollectionUtils.isNotEmpty(SbAdvertInfos)) {
			for (SbSadvertInfo SbAdvertInfo : SbAdvertInfos) {
				map.put(SbAdvertInfo.getAdId(), SbAdvertInfo);
			}
		}
		return map;
	}
	
	/**
	 * 获取单个广告
	 * @param userID
	 * @return
	 */
	public SbSadvertInfo getSbAdvertInfoByID(Integer adId) {
		List<Integer> adIds = new ArrayList<Integer>();
		adIds.add(adId);
		SbSadvertInfo SbAdvertInfo = new SbSadvertInfo();
		Map<Integer, SbSadvertInfo> adMap = getAdMapByIds(adIds);
		if(adMap!=null && adMap.size()>0){
			SbAdvertInfo = adMap.get(adId);
		}
		return SbAdvertInfo;
	}
	
	/**
     * 根据登录帐号查找loginName和SbContentInfoType，正常只有一条数据
     * and a.isvalid='1' and a.SbContentInfo_type='1'需要该条件
     * @param loginName
     * @return
     */
    public SbSadvertInfo findFormatByLoginName(String loginName) {
    	SbSadvertInfo SbAdvertInfo = memory.query("select * from member_info where login_account = ? ",
				new BeanHandler<SbSadvertInfo>(SbSadvertInfo.class), loginName);
    	return SbAdvertInfo;
    }
    
    /**
     * 根据支付宝账户查询
     * @param payAccount
     * @return
     */
    public SbSadvertInfo findMemberByPayAccount(String payAccount) {
    	SbSadvertInfo SbAdvertInfo = memory.query("select * from member_info where pay_account = ? ",
				new BeanHandler<SbSadvertInfo>(SbSadvertInfo.class), CPSUtil.encryptOrderDes(payAccount));
    	return SbAdvertInfo;
    }
    
    /**
     * 批量更新会员状态
     * @param memberIds
     * @param state
     * @return
     */
	public boolean updateMemberSateByIds(List<Integer> memberIds,Integer state) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("update ").append(getCurrentTableName());
		if(CPSUtil.isNotEmpty(state)){
		   sql.append(" set user_state=?");
		   params.add(state);
		}
		memory.in(sql, params, "where", "memeber_id", memberIds);
		return memory.update(sql, params)>0;
	}
	
	public boolean updateMemberLevel(Integer levelCode, Integer memberId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("update ").append(getCurrentTableName());
		sql.append(" set member_level=?");
		params.add(levelCode);
		sql.append(" where memeber_id = ?");
		params.add(memberId);
		return memory.update(sql, params)>0;
	}
	
	/**
	 * 保存用户
	 * @param SbContentInfo
	 * @return
	 */
	public boolean saveSbAdvertInfo(SbSadvertInfo SbSadvertInfo) {
		if(insert(SbSadvertInfo)>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 修改用户
	 * @param SbContentInfo
	 * @return
	 */
	public boolean editSbAdvertInfo(SbSadvertInfo SbAdvertInfo) {
		if(update(SbAdvertInfo, "adId")>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 删除用户
	 * @param SbContentInfo
	 * @return
	 */
	public boolean trashSbContentInfo(SbSadvertInfo SbAdvertInfo) {
		if(delete(SbAdvertInfo)>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 批量删除用户
	 * @param list
	 * @return
	 */
	public boolean trashSbContentInfoList(List<SbSadvertInfo> list) {
		int rst [] = deleteBatch(list);
		if(rst.length>0 && rst.length==list.size()){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 批量加密
	 * @param list
	 */
	private void encryptSbContentInfoList(List<SbSadvertInfo> list) {
		if (CollectionUtils.isNotEmpty(list)) {
			for (SbSadvertInfo SbAdvertInfo : list) {
				encryptSbContentInfo(SbAdvertInfo);
			}
		}
	}
	
	private void encryptSbContentInfo(SbSadvertInfo SbSadvertInfo1) {
		if (SbSadvertInfo1 != null) {
			SbSadvertInfo1.encrypt();
		}
	}
	
	
	/**
	 * 批量解密
	 * @param list
	 */
	private void decryptSbContentInfoList(List<SbSadvertInfo> list) {
		if (CollectionUtils.isNotEmpty(list)) {
			for (SbSadvertInfo SbAdvertInfo : list) {
				decryptSbContentInfo(SbAdvertInfo);
			}
		}
	}
	
	private void decryptSbContentInfo(SbSadvertInfo SbAdvertInfo) {
		if (SbAdvertInfo != null) {
			SbAdvertInfo.decrypt();
		}
	}
	
	public void temp() {/*
		List<SbContentInfo> list = super.findList(new SbContentInfo());
		if (CollectionUtils.isNotEmpty(list)) {
			for (SbContentInfo SbContentInfo : list) {
				update(SbContentInfo, "memeberId");
			}
		}
	*/}
}
