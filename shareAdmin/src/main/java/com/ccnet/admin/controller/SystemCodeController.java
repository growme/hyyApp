package com.ccnet.admin.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ccnet.admin.controller.api.HessianClientUtil;
import com.ccnet.core.common.ajax.AjaxRes;
import com.ccnet.core.common.cache.InitSystemCache;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.UniqueID;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.base.ResourceTypes;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.controller.BaseController;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.entity.SystemCode;
import com.ccnet.core.service.SystemCodeService;

/**
 * 字典参数控制类
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/backstage/code/")
public class SystemCodeController extends AdminBaseController<SystemCode> {
	
	//静态地址
	public static String INDEX_URL = "/backstage/code/index";
	public static String GO_ADD_URL = "/backstage/code/goadd";
	public static String GO_EDIT_URL = "/backstage/code/goedit";
	
	@Autowired
	private SystemCodeService systemCodeService;
	
	/**
	 * 字典首页
	 */
	@RequestMapping("index")
	public ModelAndView index() throws UnsupportedEncodingException{	
		ModelAndView mav = new ModelAndView();
		if(isAuthedReq(ResourceTypes.MENU)){
			Dto paramDto = getParamAsDto();
			String queryParam= paramDto.getAsString("queryParam");
			Page<SystemCode> page = newPage(paramDto);
			page = systemCodeService.findSystemCodeByPage(new SystemCode(), page,queryParam);
			mav.addObject("pm", page);
			mav.addObject("queryParam",queryParam);
			mav.addObject(Const.CT_MENU_NAV,"字典参数");
			mav.addObject("permitFBtn", getPermitBtn(ResourceTypes.FUNC));	
			mav.addObject("permitTBtn", getPermitBtn(ResourceTypes.BUTTON));
			mav.setViewName("admin/jsp/code/code_list");
		}else{
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		
		return mav;
	}
	
	/**
	 * 添加字典
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "goadd", method = RequestMethod.GET)
	public ModelAndView goAddCode() {
		ModelAndView mav = new ModelAndView();
		if(isAuthedReq(ResourceTypes.FUNC)){
			mav.addObject("edlist", CPSUtil.getCodeList("EDITMODE"));
			mav.addObject("enlist", CPSUtil.getCodeList("ENABLED"));
			mav.setViewName("admin/jsp/code/code_info");
		}else{
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}
	
	/**
	 * 修改字典
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "goedit", method = RequestMethod.GET)
	public ModelAndView goEditCode() {
		ModelAndView mav = new ModelAndView();
		if(isAuthedReq(ResourceTypes.FUNC) || isAuthedReq(ResourceTypes.BUTTON)){
			Dto paramDto = getParamAsDto();
			String code_id = paramDto.getAsString("code_id");
			SystemCode systemCode  = new SystemCode();
			if(CPSUtil.isNotEmpty(code_id)){
				systemCode = systemCodeService.findSystemCodeByID(code_id);
			}
			mav.addObject("codeInfo", systemCode);
			mav.addObject("edlist", CPSUtil.getCodeList("EDITMODE"));
			mav.addObject("enlist", CPSUtil.getCodeList("ENABLED"));
			mav.setViewName("admin/jsp/code/code_info");
		}else{
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}
	
	
	/**
	 * 增加字典
	 * @param role
	 * @return
	 */
	@RequestMapping(value="save", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes saveCode(SystemCode code){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(isAuthedReq(ResourceTypes.FUNC,GO_ADD_URL))){
			try {
				code.setCodeId(UniqueID.getUniqueID(8, 0));
				if(systemCodeService.saveSystemCode(code)){
					InitSystemCache.updateCache(Const.CT_CODE_LIST);
					HessianClientUtil.updateCache(Const.CT_CODE_LIST);
					ar.setSucceedMsg(Const.SAVE_SUCCEED);
				}else{
					ar.setFailMsg(Const.SAVE_FAIL);
				}
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.SAVE_FAIL);
			}
		}
		return ar;
	}
	
	/**
	 * 更新角色信息
	 * @param code
	 * @return
	 */
	@RequestMapping(value="edit", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes editCode(SystemCode code){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(isAuthedReq(ResourceTypes.BUTTON,GO_EDIT_URL))){
			try {
				if(systemCodeService.editSystemCode(code)){
					InitSystemCache.updateCache(Const.CT_CODE_LIST);
					HessianClientUtil.updateCache(Const.CT_CODE_LIST);
					ar.setSucceedMsg(Const.UPDATE_SUCCEED);
				}else{
					ar.setFailMsg(Const.UPDATE_FAIL);
				}
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.UPDATE_FAIL);
			}
		}
		return ar;
	}
	
	@RequestMapping(value="trash", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes trashCode(String code_id){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(isAuthedReq(ResourceTypes.FUNC) || isAuthedReq(ResourceTypes.BUTTON))){
			try {
				if(systemCodeService.trashSystemCodeList(code_id)){
					InitSystemCache.updateCache(Const.CT_CODE_LIST);
					HessianClientUtil.updateCache(Const.CT_CODE_LIST);
					ar.setSucceedMsg(Const.DEL_SUCCEED);
				}else{
					ar.setFailMsg(Const.DEL_FAIL);
				}
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DEL_FAIL);
			}
		}
		return ar;
	}

}
