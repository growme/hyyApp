package com.ccnet.admin.cps.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ccnet.admin.controller.AdminBaseController;
import com.ccnet.admin.controller.api.HessianClientUtil;
import com.ccnet.core.common.ContentType;
import com.ccnet.core.common.StateType;
import com.ccnet.core.common.ajax.AjaxRes;
import com.ccnet.core.common.cache.InitSystemCache;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.base.ResourceTypes;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.common.utils.security.UserInfoShiroUtil;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.entity.SystemCode;
import com.ccnet.core.entity.UserInfo;
import com.ccnet.cps.entity.SbColumnInfo;
import com.ccnet.cps.service.SbColumnInfoService;

/**
 * 栏目管理
 * @author JackieWang
 * 
 */
@Controller
@RequestMapping("/backstage/column/")
public class SbColumnInfoController extends AdminBaseController<SbColumnInfo> {
	
	// 静态地址
	public static String INDEX_URL = "/backstage/column/index";
	public static String GO_ADD_URL = "/backstage/column/goadd";
	public static String GO_EDIT_URL = "/backstage/column/goedit";
	
	@Autowired
	private SbColumnInfoService sbColumnInfoService;

	/**
	 * 查询栏目列表
	 * 
	 * @return
	 */
	@RequestMapping("index")
	public ModelAndView columnIndex() {
		Dto paramDto = getParamAsDto();
		ModelAndView mav = new ModelAndView();
		Page<SbColumnInfo> page = newPage(paramDto);
		if (isAuthedReq(ResourceTypes.MENU)) {
			SbColumnInfo sbcolumnInfo = new SbColumnInfo();
			page = sbColumnInfoService.findSbColumnInfoByPage(sbcolumnInfo,page, paramDto);// 分页查询
			mav.addObject("pm", page);
			mav.addObject("stateList", StateType.values());
			mav.addObject("enabled", paramDto.getAsString("enabled"));
			mav.addObject("end_date", paramDto.getAsString("end_date"));
			mav.addObject("start_date", paramDto.getAsString("start_date"));
			mav.addObject("queryParam", paramDto.getAsString("queryParam"));
			mav.addObject("code", paramDto.getAsString("code"));
			mav.addObject("columnType", paramDto.getAsString("columnType"));
			mav.addObject("typeMap", ContentType.values());
			mav.addObject("permitFBtn", getPermitBtn(ResourceTypes.FUNC));
			mav.addObject("permitTBtn", getPermitBtn(ResourceTypes.BUTTON));
			mav.addObject(Const.CT_MENU_NAV, "栏目管理");
			mav.setViewName("admincps/jsp/column/column_list");
		} else {
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}

		return mav;
	}

	/**
	 * 新增栏目列表
	 * 
	 * @return
	 */
	@RequestMapping("goadd")
	public ModelAndView columnGoadd() {
		ModelAndView mav = new ModelAndView();
		mav.addObject(Const.CT_MENU_NAV, "添加栏目");
		if (isAuthedReq(ResourceTypes.FUNC)) {
			mav.addObject("typeMap", ContentType.values());
			mav.addObject("enabledMap", StateType.getStateMap());
			mav.addObject("columnTypeList", CPSUtil.getCodeList("COLUMN_TYPE"));
			mav.setViewName("admincps/jsp/column/column_info");
		} else {
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}

	/**
	 * 修改栏目
	 * 
	 * @return
	 */
	@RequestMapping("goedit")
	public ModelAndView columnGoedit() {
		ModelAndView mav = new ModelAndView();
		if (isAuthedReq(ResourceTypes.FUNC) || isAuthedReq(ResourceTypes.BUTTON)) {
			Dto paramDto = getParamAsDto();
			Integer columnId = paramDto.getAsInteger("columnId");
			SbColumnInfo columnInfo = new SbColumnInfo();
			if (CPSUtil.isNotEmpty(columnId)) {
				columnInfo = sbColumnInfoService.getColumnByID(columnId);
			}
			List<SystemCode> systemCodes=CPSUtil.getCodeList("COLUMN_TYPE");
			mav.addObject("typeMap", ContentType.values());
			mav.addObject("columnTypeList", systemCodes);
			mav.addObject("columnInfo", columnInfo);
			mav.addObject(Const.CT_MENU_NAV, "修改栏目");
			mav.addObject("enabledMap", StateType.getStateMap());
			mav.setViewName("admincps/jsp/column/column_info");
		} else {
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}

	/**
	 * 数据保存
	 * @param columnInfo
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes save(SbColumnInfo columnInfo) {
		AjaxRes ar = getAjaxRes();
		if (ar.setNoAuth(isAuthedReq(ResourceTypes.FUNC, GO_ADD_URL))) {
			try {
				boolean isOk = false;
				UserInfo userInfo = UserInfoShiroUtil.getCurrentUser();
				if (columnInfo.getColumnId() == null || columnInfo.getColumnId() == 0) {
					columnInfo.setCreateTime(new Date());
					columnInfo.setUserId(userInfo.getUserId());
					isOk = sbColumnInfoService.saveSbColumnInfo(columnInfo);
				} else {
					SbColumnInfo sbColumnInfo=sbColumnInfoService.getColumnByID(columnInfo.getColumnId());
					columnInfo.setCreateTime(sbColumnInfo.getCreateTime());
					columnInfo.setUserId(userInfo.getUserId());
					isOk = sbColumnInfoService.editSbColumnInfo(columnInfo);
				}
				if (isOk) {
					InitSystemCache.updateCache(Const.CT_COLUMN_LIST);
					HessianClientUtil.updateCache(Const.CT_COLUMN_LIST);
					ar.setSucceedMsg(Const.SAVE_SUCCEED);
				} else {
					ar.setFailMsg(Const.SAVE_FAIL);
				}
			} catch (Exception e) {
				logger.error(e.toString(), e);
				ar.setFailMsg(Const.ACCOUNT_EXIST);
			}
		}
		return ar;
	}

	/**
	 * 删除栏目
	 * @param columnInfo
	 * @return
	 */
	@RequestMapping(value = "trash", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes columngoDel() {
		AjaxRes ar = getAjaxRes();
		if (ar.setNoAuth(isAuthedReq(ResourceTypes.FUNC, GO_ADD_URL))) {
			if (isAuthedReq(ResourceTypes.FUNC) || isAuthedReq(ResourceTypes.BUTTON)) {
				Dto paramDto = getParamAsDto();
				Integer Id = paramDto.getAsInteger("columnId");
				SbColumnInfo sbColumnInfo = new SbColumnInfo();
				sbColumnInfo.setColumnId(Id);
				if (CPSUtil.isNotEmpty(Id)) {
					if (sbColumnInfoService.trashSbColumnInfo(sbColumnInfo)) {
						InitSystemCache.updateCache(Const.CT_COLUMN_LIST);
						HessianClientUtil.updateCache(Const.CT_COLUMN_LIST);
						ar.setSucceedMsg(Const.DEL_SUCCEED);
					} else {
						ar.setRes(Const.FAIL);
						ar.setFailMsg(Const.DEL_FAIL);
					}
				}
			}
		}
		return ar;
	}
}
