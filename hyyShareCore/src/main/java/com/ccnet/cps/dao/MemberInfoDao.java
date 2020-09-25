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
import com.ccnet.cps.entity.MemberInfo;

/**
 * 会员数据层
 */
@Repository("memberInfoDao")
public class MemberInfoDao extends BaseDao<MemberInfo> {

	/**
	 * 保存一个对象
	 * 
	 * @param o
	 *            对象
	 * @return 对象的ID
	 */
	public int insert(MemberInfo o) {
		int i = super.insert(o, null);
		return i;
	}

	public int insert(MemberInfo o, String autoIncrementField) {
		int i = super.insert(o, autoIncrementField);
		return i;
	}

	/**
	 * 删除一个对象
	 * 
	 * @param k
	 *            字段值
	 */
	public int delete(MemberInfo o) {
		int i = super.delete(o);
		return i;
	}

	/**
	 * 更新一个对象
	 * 
	 * @param o
	 *            对象
	 */
	public int update(MemberInfo o, String camelKey) {
		int i = super.update(o, camelKey);
		return i;
	}

	/**
	 * 批量删除一组对象
	 * 
	 * @param s
	 *            (主键)数组
	 */
	public int[] deleteBatch(List<MemberInfo> list) {
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
	public List<MemberInfo> findList(MemberInfo o) {
		List<MemberInfo> list = super.findList(o);
		return list;
	}

	public MemberInfo find(MemberInfo o) {
		MemberInfo memberInfo = super.find(o);
		return memberInfo;
	}

	public void findByPage(MemberInfo o, Page<MemberInfo> page) {
		super.findByPage(o, page);
	}

	/**
	 * 分页查询用户(多过滤)
	 * 
	 * @param user
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<MemberInfo> findUserByPage(MemberInfo user, Page<MemberInfo> page, Dto pdDto) {
		// 获取参数
		String queryParam = pdDto.getAsString("queryParam");
		String state = pdDto.getAsString("member_state");
		// 日期过滤
		String end_date = pdDto.getAsString("end_date");
		String start_date = pdDto.getAsString("start_date");
		String memberId = pdDto.getAsString("memberId");

		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());

		List<String> whereColumns = memory.parseWhereColumns(params, MemberInfo.class, user);
		if (CollectionUtils.isNotEmpty(whereColumns)) {
			sql.append(" where ").append(appendAnd(whereColumns));
		}

		// 加上模糊查询
		if (CPSUtil.isNotEmpty(queryParam)) {
			appendWhere(sql);
			sql.append(" and (login_account like ?  or member_name like ? ) ");
			params.add(queryParam + "%");
			params.add(queryParam + "%");
		}

		// 加上模糊查询
		if (CPSUtil.isNotEmpty(memberId)) {
			appendWhere(sql);
			sql.append(" and member_id like ?  ");
			params.add("%" + memberId + "%");
		}

		// 带上日期查询
		if (CPSUtil.isNotEmpty(start_date)) {
			appendWhere(sql);
			sql.append(" and register_time >=? ");
			params.add(start_date + " 00:00:00");
		}

		if (CPSUtil.isNotEmpty(end_date)) {
			appendWhere(sql);
			sql.append(" and register_time <=? ");
			params.add(end_date + " 23:59:59");
		}

		if (CPSUtil.isNotEmpty(state)) {
			appendWhere(sql);
			sql.append(" and user_state =? ");
			params.add(state);
		}

		// 加上排序
		sql.append(" order by register_time desc ");
		super.queryPager(sql.toString(), new BeanListHandler<MemberInfo>(MemberInfo.class), params, page);
		return page;

	}

	/**
	 * 统计总数
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
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
	 * 
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<MemberInfo> findMemberInfoList(int start, int limit) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		sql.append(" where user_state = 0");
		// 加上排序
		sql.append(" order by register_time desc limit ?,?");
		params.add(start);
		params.add(limit);
		return super.memory.query(sql, new BeanListHandler<MemberInfo>(MemberInfo.class), params);
	}

	/**
	 * 获取会员信息
	 * 
	 * @param memberInfo
	 * @return
	 */
	public MemberInfo findMemberInfo(MemberInfo memberInfo) {
		return find(memberInfo);
	}

	/**
	 * 根据visitcode获取用户集合
	 * 
	 * @return List<MemberInfo>
	 */
	public List<MemberInfo> getUserListByVisitCode(String visitCode, String startDate, String endDate) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		sql.append(" where 1=1");
		if (CPSUtil.isNotEmpty(visitCode)) {
			sql.append(" and recom_code=?");
			params.add(visitCode);
		}
		// 带上日期查询
		if (CPSUtil.isNotEmpty(startDate)) {
			appendWhere(sql);
			sql.append(" and register_time >=? ");
			params.add(startDate + " 00:00:00");
		}

		if (CPSUtil.isNotEmpty(endDate)) {
			appendWhere(sql);
			sql.append(" and register_time <=? ");
			params.add(endDate + " 23:59:59");
		}
		List<MemberInfo> list = memory.query(sql, new BeanListHandler<MemberInfo>(MemberInfo.class), params);
		return list;
	}

	/**
	 * 根据ID获取用户集合
	 * 
	 * @param userIds
	 * @return
	 */
	public List<MemberInfo> getUserListByIds(List<Integer> userIds) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		memory.in(sql, params, "where", "member_id", userIds);
		List<MemberInfo> list = memory.query(sql, new BeanListHandler<MemberInfo>(MemberInfo.class), params);
		return list;
	}

	/**
	 * 根据推荐码获取用户集合
	 * 
	 * @param visitCodes
	 * @return
	 */
	public List<MemberInfo> getUserListByVisitCodes(List<String> visitCodes) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		memory.in(sql, params, "where", "visit_code", visitCodes);
		List<MemberInfo> list = memory.query(sql, new BeanListHandler<MemberInfo>(MemberInfo.class), params);
		return list;
	}

	/**
	 * 转换集合数据
	 * 
	 * @param userIds
	 * @return
	 */
	public Map<Integer, MemberInfo> getUserMapByIds(List<Integer> userIds) {
		List<MemberInfo> memberInfos = getUserListByIds(userIds);
		Map<Integer, MemberInfo> map = new HashMap<Integer, MemberInfo>(0);
		if (CollectionUtils.isNotEmpty(memberInfos)) {
			for (MemberInfo memberInfo : memberInfos) {
				map.put(memberInfo.getMemberId(), memberInfo);
			}
		}
		return map;
	}

	/**
	 * 转换集合数据
	 * 
	 * @param visitCodes
	 * @return
	 */
	public Map<String, MemberInfo> getUserMapByVisitCodes(List<String> visitCodes) {
		List<MemberInfo> memberInfos = getUserListByVisitCodes(visitCodes);
		Map<String, MemberInfo> map = new HashMap<String, MemberInfo>(0);
		if (CollectionUtils.isNotEmpty(memberInfos)) {
			for (MemberInfo memberInfo : memberInfos) {
				map.put(memberInfo.getVisitCode(), memberInfo);
			}
		}
		return map;
	}

	/**
	 * 获取单个用户
	 * 
	 * @param vcode
	 * @return
	 */
	public MemberInfo getUserByVCode(String vcode) {
		MemberInfo memberInfo = memory.query("select * from member_info where visit_code = ? ",
				new BeanHandler<MemberInfo>(MemberInfo.class), vcode);
		return memberInfo;
	}

	/**
	 * 获取单个用户
	 * 
	 * @param userID
	 * @return
	 */
	public MemberInfo getUserByUserID(Integer userID) {
		List<Integer> userIds = new ArrayList<Integer>();
		userIds.add(userID);
		MemberInfo memberInfo = new MemberInfo();
		Map<Integer, MemberInfo> userMap = getUserMapByIds(userIds);
		if (userMap != null && userMap.size() > 0) {
			memberInfo = userMap.get(userID);
		}
		return memberInfo;
	}

	/**
	 * 根据登录帐号查找loginName和MemberInfoType，正常只有一条数据 and a.isvalid='1' and
	 * a.MemberInfo_type='1'需要该条件
	 * 
	 * @param loginName
	 * @return
	 */
	public MemberInfo findFormatByLoginName(String loginName) {
		MemberInfo memberInfo = memory.query("select * from member_info where login_account = ? ",
				new BeanHandler<MemberInfo>(MemberInfo.class), loginName);
		return memberInfo;
	}

	/**
	 * 根据支付宝账户查询
	 * 
	 * @param payAccount
	 * @return
	 */
	public MemberInfo findMemberByPayAccount(String payAccount) {
		MemberInfo memberInfo = memory.query("select * from member_info where pay_account = ? ",
				new BeanHandler<MemberInfo>(MemberInfo.class), CPSUtil.encryptOrderDes(payAccount));
		return memberInfo;
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
		memory.in(sql, params, "where", "member_id", memberIds);
		return memory.update(sql, params) > 0;
	}

	/**
	 * 更新会员等级
	 * 
	 * @param levelCode
	 * @param memberId
	 * @return
	 */
	public boolean updateMemberLevel(Integer levelCode, Integer memberId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("update ").append(getCurrentTableName());
		sql.append(" set member_level=?");
		params.add(levelCode);
		sql.append(" where member_id = ?");
		params.add(memberId);
		return memory.update(sql, params) > 0;
	}

	/**
	 * 保存会员
	 * 
	 * @param memberInfo
	 * @return
	 */
	public boolean saveMemberInfo(MemberInfo memberInfo) {
		if (insert(memberInfo) > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 保存会员
	 * 
	 * @param memberInfo
	 * @return ID
	 */
	public int saveNMemberInfo(MemberInfo memberInfo) {
		return insert(memberInfo, "memberId");
	}

	/**
	 * 修改用户
	 * 
	 * @param memberInfo
	 * @return
	 */
	public boolean editMemberInfo(MemberInfo memberInfo) {
		if (update(memberInfo, "memberId") > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 删除用户
	 * 
	 * @param memberInfo
	 * @return
	 */
	public boolean trashMemberInfo(MemberInfo memberInfo) {
		if (delete(memberInfo) > 0) {
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
	public boolean trashMemberInfoList(List<MemberInfo> list) {
		int rst[] = deleteBatch(list);
		if (rst.length > 0 && rst.length == list.size()) {
			return true;
		} else {
			return false;
		}
	}

	public void temp() {
		List<MemberInfo> list = super.findList(new MemberInfo());
		if (CollectionUtils.isNotEmpty(list)) {
			for (MemberInfo memberInfo : list) {
				update(memberInfo, "memberId");
			}
		}
	}

	/**
	 * 检查用户登陆
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean checkLogin(String username, String password) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName()).append(" where 1=1");
		if (CPSUtil.isNotEmpty(username)) {
			sql.append(" and login_account = ? ");
			params.add(username);
		}
		if (CPSUtil.isNotEmpty(password)) {
			sql.append(" and login_password= ? ");
			params.add(password);
		}

		List<MemberInfo> list = memory.query(sql, new BeanListHandler<MemberInfo>(MemberInfo.class), params);
		if (list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 根据邀请码查询用户
	 * 
	 * @param visitCodes
	 *            邀请码集合
	 * @return List<MemberInfo> 会员
	 */
	public List<MemberInfo> findMemberInfoByVisitCodes(List<String> visitCodes) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		appendWhere(sql);
		memory.in(sql, params, "and", "visit_code", visitCodes);
		// 带上排序
		sql.append(" order by register_time desc");
		List<MemberInfo> list = memory.query(sql, new BeanListHandler<MemberInfo>(MemberInfo.class), params);
		return list;
	}

	/**
	 * 根据邀请码查询用户
	 * 
	 * @param visitCodes
	 *            邀请码集合
	 * @return List<MemberInfo> 会员
	 */
	public List<MemberInfo> findRecomMemberInfoByVisitCodes(List<String> visitCodes) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		appendWhere(sql);
		memory.in(sql, params, "and", "recom_code", visitCodes);
		// 带上排序
		sql.append(" order by register_time desc");
		List<MemberInfo> list = memory.query(sql, new BeanListHandler<MemberInfo>(MemberInfo.class), params);
		return list;
	}

	/**
	 * 获取下级
	 * 
	 * @param vcode
	 * @param rcode
	 * @return
	 */
	public List<MemberInfo> findChildMemberInfoByLevelCode(String vcode, String rcode) {
		List<MemberInfo> list = null;
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName()).append(" where 1=1");
		if (CPSUtil.isNotEmpty(vcode) && CPSUtil.isNotEmpty(rcode)) {
			sql.append(" and level_code like ? ");
			params.add("%" + vcode + "|%");
		} else {
			sql.append(" and level_code like ? ");
			params.add(vcode + "|%");
		}
		list = memory.query(sql, new BeanListHandler<MemberInfo>(MemberInfo.class), params);
		if (CPSUtil.isEmpty(list)) {
			return null;
		}
		return list;
	}

	public MemberInfo getMemberGoldById(String memberId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("SELECT a.member_id,a.member_name,a.member_icon,a.mobile,b.gold as showColor ");
		sql.append("FROM member_info a LEFT JOIN  sb_user_money b ON a.member_id = b.user_id WHERE a.member_id = ?");
		params.add(memberId);
		return memory.query(sql, new BeanHandler<MemberInfo>(MemberInfo.class), params);
	}

	public int updateMemberGoldById(String memberId, Integer gold) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(
				"INSERT INTO sb_user_money (user_id,gold,update_time) VALUES (?,?,NOW()) ON DUPLICATE KEY UPDATE gold=IFNULL(gold,0)+?");
		params.add(memberId);
		params.add(gold);
		params.add(gold);
		return memory.update(sql, params);
	}

	// 获取总收益列表根据总收益排序
	public Page<MemberInfo> getzongshouyi() {
		Page<MemberInfo> page = new Page<MemberInfo>();
		page.setPageSize(10);
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(
				"SELECT b.*,a.`profits_money` AS showColor from `sb_user_money` a,member_info b WHERE a.`user_id` = b.`member_id` ORDER BY a.profits_money DESC ");
		super.queryPager(sql.toString(), new BeanListHandler<MemberInfo>(MemberInfo.class), params, page);
		return page;
	}

	// 获取总收益列表根据总收益排序
	public Page<MemberInfo> getyueshouyi() {
		Page<MemberInfo> page = new Page<MemberInfo>();
		page.setPageSize(10);
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(
				"SELECT info.*,a.umoney AS showColor from (SELECT user_id,SUM(umoney) AS umoney FROM `sb_money_count` WHERE DATE_FORMAT(create_time, '%%Y%%m' ) = DATE_FORMAT( CURDATE( ) , '%%Y%%m' ) GROUP BY user_id  ORDER BY umoney DESC) a, member_info info ");
		sql.append(" where a.user_id = info.`member_id` ");
		super.queryPager(sql.toString(), new BeanListHandler<MemberInfo>(MemberInfo.class), params, page);
		return page;
	}
}
