package com.ccnet.admin.bh.service.impl;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ccnet.admin.bh.dao.WechatPublicNumberDao;
import com.ccnet.admin.bh.entity.WechatPublicNumber;
import com.ccnet.admin.bh.service.WechatPublicNumberService;
import com.ccnet.core.common.utils.HttpUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.impl.BaseServiceImpl;

@Service("wechatPublicNumberService")
public class WechatPublicNumberServiceImpl extends BaseServiceImpl<WechatPublicNumber>
		implements WechatPublicNumberService {

	@Autowired
	WechatPublicNumberDao wechatPublicNumberDao;

	/**
	 * 分页查询字典
	 * 
	 * @param sc
	 * @param page
	 * @return
	 */
	public Page<WechatPublicNumber> findWechatPublicNumberByPage(WechatPublicNumber sc, Page<WechatPublicNumber> page,
			Dto paramDto) {
		return wechatPublicNumberDao.findWechatPublicNumberByPage(sc, page, paramDto);
	}

	@Override
	protected BaseDao<WechatPublicNumber> getDao() {
		return wechatPublicNumberDao;
	}

	@Override
	public WechatPublicNumber getWechatPublicNumberById(Integer id) {
		WechatPublicNumber number = new WechatPublicNumber();
		number.setId(id);
		return wechatPublicNumberDao.find(number);
	}

	@Override
	public void taskWechatPublicNumber() {
		WechatPublicNumber arg0 = new WechatPublicNumber();
		arg0.setEnabled("1");
		List<WechatPublicNumber> list = findList(arg0);
		for (int i = 0; i < list.size(); i++) {
			WechatPublicNumber wp = list.get(i);
			String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
					+ wp.getAppid() + "&secret=" + wp.getAppSecret();
			String str="";
			try {
				str = HttpUtil.get(url, false);
			} catch (KeyManagementException | NoSuchAlgorithmException | NoSuchProviderException | IOException e) {
				e.printStackTrace();
			}
			JSONObject obj = (JSONObject) JSON.parse(str);
			wp.setAccessToken(obj.getString("access_token"));
			wp.setUpdateTime(new Date());
			update(wp, "id");
			System.out.println("111");
		}
	}

}
