package com.ccnet.cps.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import cn.ffcs.memory.BeanHandler;
import cn.ffcs.memory.BeanListHandler;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.cps.entity.SbAdvertInfo;

@Repository("sbAdvertInfoDao")
public class SbAdvertInfoDao extends BaseDao<SbAdvertInfo> {
	/**
	 * 分页查询广告(多过滤)
	 * 
	 * @param content
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<SbAdvertInfo> findSbContentInfoByPage(SbAdvertInfo content, Page<SbAdvertInfo> page, Dto pdDto) {
		// 获取参数
		String queryParam = pdDto.getAsString("queryParam");
		String state = pdDto.getAsString("ad_state");
		// 日期过滤
		String end_date = pdDto.getAsString("end_date");
		String start_date = pdDto.getAsString("start_date");

		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());

		List<String> whereColumns = memory.parseWhereColumns(params, SbAdvertInfo.class, content);
		if (CollectionUtils.isNotEmpty(whereColumns)) {
			sql.append(" where ").append(appendAnd(whereColumns));
		}

		// 加上模糊查询
		if (CPSUtil.isNotEmpty(queryParam)) {
			// 加密参数
			// queryParam = CPSUtil.encryptOrderDes(queryParam);
			appendWhere(sql);
			sql.append(" and (ad_title like ? or remark like ? or ad_id = ? ) ");
			params.add("%" + queryParam + "%");
			params.add("%" + queryParam + "%");
			params.add(queryParam);
		}

		// 带上日期查询
		if (CPSUtil.isNotEmpty(start_date)) {
			appendWhere(sql);
			sql.append(" and create_time >=? ");
			params.add(start_date + " 00:00:00");
		}

		if (CPSUtil.isNotEmpty(end_date)) {
			appendWhere(sql);
			sql.append(" and create_time <=? ");
			params.add(end_date + " 23:59:59");
		}

		if (CPSUtil.isNotEmpty(state)) {
			appendWhere(sql);
			sql.append(" and state =? ");
			params.add(state);
		}

		// 加上排序
		sql.append(" order by ad_type asc, create_time desc ");
		super.queryPager(sql.toString(), new BeanListHandler<SbAdvertInfo>(SbAdvertInfo.class), params, page);
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
	 * 根据ID获取广告集合
	 * 
	 * @param userIds
	 * @return
	 */
	public List<SbAdvertInfo> getAdListByIds(List<Integer> adIds) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		memory.in(sql, params, "where", "ad_id", adIds);
		List<SbAdvertInfo> list = memory.query(sql, new BeanListHandler<SbAdvertInfo>(SbAdvertInfo.class), params);
		return list;
	}

	/**
	 * 根据广告类型查询所有有效广告
	 */
	public List<SbAdvertInfo> getAdvertInfosByType(int type) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		sql.append(" where 1=1");

		sql.append(" and ad_type=?");
		params.add(type);
		String now = new SimpleDateFormat("HH:mm").format(new Date());
		String dateNow = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		sql.append(" and start_time<=?");
		params.add(now);

		sql.append(" and end_time>=?");
		params.add(now);

		sql.append(" and end_date>=?");
		params.add(dateNow);

		List<SbAdvertInfo> list = memory.query(sql, new BeanListHandler<SbAdvertInfo>(SbAdvertInfo.class), params);
		return list;
	}

	/**
	 * 转换集合数据
	 * 
	 * @param userIds
	 * @return
	 */
	public Map<Integer, SbAdvertInfo> getAdMapByIds(List<Integer> adIds) {
		List<SbAdvertInfo> SbAdvertInfos = getAdListByIds(adIds);
		Map<Integer, SbAdvertInfo> map = new HashMap<Integer, SbAdvertInfo>(0);
		if (CollectionUtils.isNotEmpty(SbAdvertInfos)) {
			for (SbAdvertInfo SbAdvertInfo : SbAdvertInfos) {
				map.put(SbAdvertInfo.getAdId(), SbAdvertInfo);
			}
		}
		return map;
	}

	/**
	 * 获取单个广告
	 * 
	 * @param userID
	 * @return
	 */
	public SbAdvertInfo getSbAdvertInfoByID(Integer adId) {
		List<Integer> adIds = new ArrayList<Integer>();
		adIds.add(adId);
		SbAdvertInfo SbAdvertInfo = new SbAdvertInfo();
		Map<Integer, SbAdvertInfo> adMap = getAdMapByIds(adIds);
		if (adMap != null && adMap.size() > 0) {
			SbAdvertInfo = adMap.get(adId);
		}
		return SbAdvertInfo;
	}

	/**
	 * 根据登录帐号查找loginName和SbContentInfoType，正常只有一条数据 and a.isvalid='1' and
	 * a.SbContentInfo_type='1'需要该条件
	 * 
	 * @param loginName
	 * @return
	 */
	public SbAdvertInfo findFormatByLoginName(String loginName) {
		SbAdvertInfo SbAdvertInfo = memory.query("select * from member_info where login_account = ? ",
				new BeanHandler<SbAdvertInfo>(SbAdvertInfo.class), loginName);
		return SbAdvertInfo;
	}

	/**
	 * 根据支付宝账户查询
	 * 
	 * @param payAccount
	 * @return
	 */
	public SbAdvertInfo findMemberByPayAccount(String payAccount) {
		SbAdvertInfo SbAdvertInfo = memory.query("select * from member_info where pay_account = ? ",
				new BeanHandler<SbAdvertInfo>(SbAdvertInfo.class), CPSUtil.encryptOrderDes(payAccount));
		return SbAdvertInfo;
	}

	/**
	 * 批量更新会员状态
	 * 
	 * @param memberIds
	 * @param state
	 * @return
	 */
	public boolean updateMemberSateByIds(List<Integer> memberIds, Integer state) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("update ").append(getCurrentTableName());
		if (CPSUtil.isNotEmpty(state)) {
			sql.append(" set user_state=?");
			params.add(state);
		}
		memory.in(sql, params, "where", "memeber_id", memberIds);
		return memory.update(sql, params) > 0;
	}

	public boolean updateMemberLevel(Integer levelCode, Integer memberId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("update ").append(getCurrentTableName());
		sql.append(" set member_level=?");
		params.add(levelCode);
		sql.append(" where memeber_id = ?");
		params.add(memberId);
		return memory.update(sql, params) > 0;
	}

	/**
	 * 保存用户
	 * 
	 * @param SbContentInfo
	 * @return
	 */
	public boolean saveSbAdvertInfo(SbAdvertInfo SbAdvertInfo) {
		if (insert(SbAdvertInfo) > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 修改用户
	 * 
	 * @param SbContentInfo
	 * @return
	 */
	public boolean editSbAdvertInfo(SbAdvertInfo SbAdvertInfo) {
		if (update(SbAdvertInfo, "adId") > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 删除用户
	 * 
	 * @param SbContentInfo
	 * @return
	 */
	public boolean trashSbContentInfo(SbAdvertInfo SbAdvertInfo) {
		if (delete(SbAdvertInfo) > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 批量删除用户
	 * 
	 * @param list
	 * @return
	 */
	public boolean trashSbContentInfoList(List<SbAdvertInfo> list) {
		int rst[] = deleteBatch(list);
		if (rst.length > 0 && rst.length == list.size()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 批量修改状态
	 * 
	 * @param adIds
	 * @param state
	 * @return
	 */
	public boolean updateAdvertiseStateById(List<String> adIds, Integer state) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("update sb_advert_info set state =? ");
		params.add(state);
		memory.in(sql, params, " where ", "ad_id", adIds);
		return memory.update(sql, params) >= 0;
	}

	/**
	 * 根据广告类型查询所有有效广告
	 */
	public List<SbAdvertInfo> getAdvertInfosByAdIdAndDate(Integer userId, Integer asId, String date) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(
				"SELECT a.ad_id,a.ad_title,a.state,a.create_time,a.user_id,a.ad_tag,a.unit_price,(SELECT COUNT(1) FROM sb_advert_visit_log b WHERE a.ad_id = b.ad_id AND b.visit_time LIKE '"
						+ date + "%%') AS read_num FROM sb_advert_info a ");
		sql.append(" where 1=1");
		if (userId != null && StringUtils.isNotBlank(userId.toString())) {
			sql.append(" and ad_user_id=?");
			params.add(userId);
		}
		if (asId != null) {
			sql.append(" and ad_id=?");
			params.add(asId);
		}
		List<SbAdvertInfo> list = memory.query(sql, new BeanListHandler<SbAdvertInfo>(SbAdvertInfo.class), params);
		return list;
	}
}
