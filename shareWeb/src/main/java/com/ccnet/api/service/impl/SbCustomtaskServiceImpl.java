package com.ccnet.api.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccnet.api.dao.SbCustomtaskDao;
import com.ccnet.api.entity.SbCustomtask;
import com.ccnet.api.service.SbCustomtaskService;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.service.impl.BaseServiceImpl;
import com.ccnet.cps.entity.SbMoneyCount;
import com.ccnet.cps.service.SbColumnInfoService;
import com.ccnet.cps.service.SbMoneyCountService;

@Service("sbCustomtaskService")
public class SbCustomtaskServiceImpl extends BaseServiceImpl<SbCustomtask> implements SbCustomtaskService {

	@Autowired
	SbCustomtaskDao sbCustomtaskDao;
	@Autowired
	SbMoneyCountService sbMoneyCountService;
	@Autowired
	SbColumnInfoService columnInfoService;

	@Override
	protected BaseDao<SbCustomtask> getDao() {
		return this.sbCustomtaskDao;
	}

	@Override
	public List<SbCustomtask> findSbCustomtask(Long userId) {
		return sbCustomtaskDao.findSbCustomtask(userId);
	}

	@Override
	public Boolean saveUserTask(Integer userid, Integer taskId) {
		boolean bool = false;
		SbCustomtask customtask = new SbCustomtask();
		SbMoneyCount moneyCount = new SbMoneyCount();
		customtask.setId(taskId);
		customtask = sbCustomtaskDao.find(customtask);
		if (customtask == null) {
			return bool;
		}
		if (customtask.getType() == 3) {
			moneyCount.setmType(taskId);
			if (sbMoneyCountService.findList(moneyCount).size() > 0) {
				return bool;
			}
			moneyCount.setCreateTime(new Date());
			moneyCount.setUserId(userid);
			moneyCount.setUmoney(customtask.getPayIntegral());
			if (sbMoneyCountService.saveSbMoneyCountInfo(moneyCount)) {
				bool = true;
			}
		} else if (customtask.getType() == 1) {
			moneyCount.setmType(taskId);
			moneyCount.setCreateTime(new Date());
			if (sbMoneyCountService.findList(moneyCount).size() >= customtask.getPayNum()) {
				return bool;
			}
			moneyCount.setCreateTime(new Date());
			moneyCount.setUserId(userid);
			moneyCount.setUmoney(customtask.getPayIntegral());
			if (sbMoneyCountService.saveSbMoneyCountInfo(moneyCount)) {
				bool = true;
			}
		} else if (customtask.getType() == 2) {
			moneyCount.setmType(taskId);
			moneyCount.setCreateTime(new Date());
			if (customtask.getPayNum() != -1
					&& sbMoneyCountService.findList(moneyCount).size() >= customtask.getPayNum()) {
				return bool;
			}
			moneyCount.setCreateTime(new Date());
			moneyCount.setUserId(userid);
			moneyCount.setUmoney(customtask.getPayIntegral());
			if (sbMoneyCountService.saveSbMoneyCountInfo(moneyCount)) {
				bool = true;
			}
		} else if (customtask.getType() == 5) {
			moneyCount.setmType(taskId);
			moneyCount.setCreateTime(new Date());
			if (customtask.getPayNum() != -1
					&& sbMoneyCountService.findList(moneyCount).size() >= customtask.getPayNum()) {
				return bool;
			}
			moneyCount.setCreateTime(new Date());
			moneyCount.setUserId(userid);
			moneyCount.setUmoney(customtask.getPayIntegral());
			if (sbMoneyCountService.saveSbMoneyCountInfo(moneyCount)) {
				bool = true;
			}
		} else if (customtask.getType() == 6) {
			moneyCount.setmType(taskId);
			moneyCount.setCreateTime(new Date());
			if (customtask.getPayNum() != -1 &&sbMoneyCountService.findList(moneyCount).size() >= customtask.getPayNum()) {
				return bool;
			}
			moneyCount.setCreateTime(new Date());
			moneyCount.setUserId(userid);
			moneyCount.setUmoney(customtask.getPayIntegral());
			if (sbMoneyCountService.saveSbMoneyCountInfo(moneyCount)) {
				bool = true;
			}
		}
		return bool;
	}

}
