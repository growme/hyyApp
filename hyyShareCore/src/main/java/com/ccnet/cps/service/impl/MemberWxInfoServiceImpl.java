package com.ccnet.cps.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccnet.core.common.utils.StringUtils;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.service.impl.BaseServiceImpl;
import com.ccnet.cps.dao.MemberWxInfoDao;
import com.ccnet.cps.entity.MemberWxInfo;
import com.ccnet.cps.service.MemberWxInfoService;

@Service("memberWxInfoService")
public class MemberWxInfoServiceImpl extends BaseServiceImpl<MemberWxInfo> implements MemberWxInfoService {

	@Autowired
	private MemberWxInfoDao memberWxInfoDao;

	public MemberWxInfo getByOpenId(String openid) {
		if (StringUtils.isNotBlank(openid)) {
			MemberWxInfo memberWxInfo = new MemberWxInfo();
			memberWxInfo.setOpenid(openid);
			return find(memberWxInfo);
		}
		return null;
	}

	public List<MemberWxInfo> getByUnionId(String unionid) {
		if (StringUtils.isNotBlank(unionid)) {
			MemberWxInfo memberWxInfo = new MemberWxInfo();
			memberWxInfo.setUnionid(unionid);
			return findList(memberWxInfo);
		}
		return new ArrayList<MemberWxInfo>();
	}

	@Override
	protected BaseDao<MemberWxInfo> getDao() {
		return memberWxInfoDao;
	}

}
