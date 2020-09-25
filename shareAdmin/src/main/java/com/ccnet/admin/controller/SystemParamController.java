package com.ccnet.admin.controller;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ccnet.admin.bh.entity.District;
import com.ccnet.admin.bh.service.DistrictService;
import com.ccnet.admin.controller.api.HessianClientUtil;
import com.ccnet.core.common.ajax.AjaxRes;
import com.ccnet.core.common.cache.InitSystemCache;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.base.ResourceTypes;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.entity.SystemParams;
import com.ccnet.core.service.SystemParamService;

/**
 * 系统参数
 * 
 * @author jackie wang
 *
 */
@Controller
@RequestMapping("/backstage/param")
public class SystemParamController extends AdminBaseController<SystemParams> {

	// 静态地址
	public static String INDEX_URL = "/backstage/param/index";
	public static String GO_SETTING_URL = "/backstage/param/setting";
	public static String GO_ADD_URL = "/backstage/param/goadd";
	public static String GO_EDIT_URL = "/backstage/param/goedit";
	@Autowired
	private SystemParamService systemParamService;
	@Autowired
	DistrictService districtService;

	/**
	 * 参数首页
	 */
	@RequestMapping("index")
	public ModelAndView paramIndex() throws UnsupportedEncodingException {
		ModelAndView mav = new ModelAndView();
		if (isAuthedReq(ResourceTypes.MENU)) {
			Dto paramDto = getParamAsDto();
			String queryParam = paramDto.getAsString("queryParam");
			Page<SystemParams> page = newPage(paramDto);
			page = systemParamService.findSystemParamByPage(new SystemParams(), page, queryParam);

			mav.addObject("pm", page);
			mav.addObject("queryParam", queryParam);
			mav.addObject(Const.CT_MENU_NAV, "系统参数");
			mav.addObject("permitFBtn", getPermitBtn(ResourceTypes.FUNC));
			mav.addObject("permitTBtn", getPermitBtn(ResourceTypes.BUTTON));
			mav.setViewName("admin/jsp/param/param_list");
		} else {
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}

	/**
	 * 快捷配置
	 */
	@RequestMapping("setting")
	public ModelAndView settingIndex() throws UnsupportedEncodingException {
		ModelAndView mav = new ModelAndView();
		if (isAuthedReq(ResourceTypes.MENU)) {
			Dto paramDto = getParamAsDto();
			String mod = paramDto.getAsString("mod");
			String flag = paramDto.getAsString("flag");
			mav.addObject("districtList", districtService.findDistrictDaoByList(new District()));
			mav.addObject("menuId", mod);
			mav.addObject("flag", CPSUtil.isEmpty(flag) ? "0" : flag);
			mav.addObject("systemParam", CPSUtil.getAllSysParam());
			mav.addObject(Const.CT_MENU_NAV, "快捷配置");
			mav.setViewName("admin/jsp/param/param_setting");
		} else {
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}

	/**
	 * 添加参数
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "goadd", method = RequestMethod.GET)
	public ModelAndView goAddParam(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		if (isAuthedReq(ResourceTypes.FUNC)) {
			mav.setViewName("admin/jsp/param/param_info");
		} else {
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}

	/**
	 * 修改字典
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "goedit", method = RequestMethod.GET)
	public ModelAndView goEditParam(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		if (isAuthedReq(ResourceTypes.FUNC) || isAuthedReq(ResourceTypes.BUTTON)) {
			Dto paramDto = getParamAsDto();
			String param_key = paramDto.getAsString("param_key");
			SystemParams systemParam = new SystemParams();
			if (CPSUtil.isNotEmpty(param_key)) {
				systemParam = systemParamService.findSystemParamByKey(param_key);
			}
			mav.addObject("paramInfo", systemParam);
			mav.setViewName("admin/jsp/param/param_info");
		} else {
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}

	/**
	 * 保存快捷设置参数
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "setting/save", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes saveSettingParam() {
		AjaxRes ar = getAjaxRes();
		if (ar.setNoAuth(isAuthedReq(ResourceTypes.MENU, GO_SETTING_URL))) {
			try {
				// 获取所有参数
				Dto paramDto = getParamsAsDto();
				Iterator entries = paramDto.entrySet().iterator();

				Map.Entry entry;
				String name = "";
				String value = "";
				SystemParams params = null;
				while (entries.hasNext()) {
					entry = (Map.Entry) entries.next();
					name = (String) entry.getKey();
					value = paramDto.getAsString(name);
					if (CPSUtil.isNotEmpty(value)) {
						if (!"on".equals(value) && !"off".equals(value)) {
							params = systemParamService.findSystemParamByKey(name);
							if (CPSUtil.isNotEmpty(params)) {// 更新
								params.setParamKey(name);
								params.setParamValue(value);
								CPSUtil.xprint("params==" + params);
								systemParamService.editSystemParam(params);
							} else {// 新增
								params = new SystemParams();
								params.setParamKey(name);
								params.setParamValue(value);
								CPSUtil.xprint("params==" + params);
								systemParamService.saveSystemParam(params);
							}

							// 处理更新session
							if (CPSUtil.isNotEmpty(name) && "ADMIN_VERSION".equals(name)) {
								setSessionAttr("ADMIN_VERSION", value);
								CPSUtil.xprint("更新session变量【" + name + "】=【" + value + "】成功...");
							}
						}
					}
				}

				boolean sr = InitSystemCache.updateCache(Const.CT_PARAM_LIST);
				HessianClientUtil.updateCache(Const.CT_PARAM_LIST);
				System.out.println(sr);
				ar.setSucceedMsg(Const.SAVE_SUCCEED);

			} catch (Exception e) {
				logger.error(e.toString(), e);
				ar.setFailMsg(Const.SAVE_FAIL);
			}
		}
		return ar;
	}

	/**
	 * 增加参数
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes saveParam(SystemParams param) {
		AjaxRes ar = getAjaxRes();
		if (ar.setNoAuth(isAuthedReq(ResourceTypes.FUNC, GO_ADD_URL))) {
			try {
				if (systemParamService.saveSystemParam(param)) {
					// 处理更新session
					if (CPSUtil.isNotEmpty(param.getParamKey()) && "ADMIN_VERSION".equals(param.getParamKey())) {
						setSessionAttr("ADMIN_VERSION", param.getParamValue());
						CPSUtil.xprint("更新session变量【" + param.getParamKey() + "】=【" + param.getParamValue() + "】成功...");
					}

					InitSystemCache.updateCache(Const.CT_PARAM_LIST);
					HessianClientUtil.updateCache(Const.CT_PARAM_LIST);
					ar.setSucceedMsg(Const.SAVE_SUCCEED);
				} else {
					ar.setFailMsg(Const.SAVE_FAIL);
				}
			} catch (Exception e) {
				logger.error(e.toString(), e);
				ar.setFailMsg(Const.SAVE_FAIL);
			}
		}
		return ar;
	}

	/**
	 * 修改参数
	 * 
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes editParam(SystemParams param) {
		AjaxRes ar = getAjaxRes();
		if (ar.setNoAuth(isAuthedReq(ResourceTypes.BUTTON, GO_EDIT_URL))) {
			try {
				if (systemParamService.editSystemParam(param)) {

					// 处理更新session
					if (CPSUtil.isNotEmpty(param.getParamKey()) && "ADMIN_VERSION".equals(param.getParamKey())) {
						setSessionAttr("ADMIN_VERSION", param.getParamValue());
						CPSUtil.xprint("更新session变量【" + param.getParamKey() + "】=【" + param.getParamValue() + "】成功...");
					}

					InitSystemCache.updateCache(Const.CT_PARAM_LIST);
					HessianClientUtil.updateCache(Const.CT_PARAM_LIST);
					ar.setSucceedMsg(Const.UPDATE_SUCCEED);
				} else {
					ar.setFailMsg(Const.UPDATE_FAIL);
				}
			} catch (Exception e) {
				logger.error(e.toString(), e);
				ar.setFailMsg(Const.UPDATE_FAIL);
			}
		}
		return ar;
	}

	/**
	 * 删除参数
	 * 
	 * @param param_id
	 * @return
	 */
	@RequestMapping(value = "trash", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes trashParam(String param_id) {
		AjaxRes ar = getAjaxRes();
		if (ar.setNoAuth(isAuthedReq(ResourceTypes.FUNC) || isAuthedReq(ResourceTypes.BUTTON))) {
			try {
				if (systemParamService.trashSystemParamList(param_id)) {
					InitSystemCache.updateCache(Const.CT_PARAM_LIST);
					HessianClientUtil.updateCache(Const.CT_PARAM_LIST);
					ar.setSucceedMsg(Const.DEL_SUCCEED);
				} else {
					ar.setFailMsg(Const.DEL_FAIL);
				}
			} catch (Exception e) {
				logger.error(e.toString(), e);
				ar.setFailMsg(Const.DEL_FAIL);
			}
		}
		return ar;
	}

}
