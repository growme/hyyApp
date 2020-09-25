package com.ccnet.admin.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ccnet.admin.controller.api.HessianClientUtil;
import com.ccnet.core.common.ContentDomainType;
import com.ccnet.core.common.DomainStateType;
import com.ccnet.core.common.PassMethodType;
import com.ccnet.core.common.ajax.AjaxRes;
import com.ccnet.core.common.cache.InitSystemCache;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.base.ResourceTypes;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.controller.BaseController;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.entity.SbPromoteLink;
import com.ccnet.core.service.SbPromoteLinkService;

/**
 * 推广地址处理
 * @author jackieWang
 *
 */
@Controller
@RequestMapping("/backstage/promote/")
public class SbPromoteLinkController extends BaseController<SbPromoteLink> {
	
	//静态地址
	public static String INDEX_URL = "/backstage/promote/index";
	public static String GO_ADD_URL = "/backstage/promote/goadd";
	public static String GO_EDIT_URL = "/backstage/promote/goedit";
	
	@Autowired
	private SbPromoteLinkService sbPromoteLinkService;
	
	/**
	 * 推广地址管理首页.
	 */
	@RequestMapping("index")
	public ModelAndView entranceIndex(){	
		ModelAndView mav = new ModelAndView();
		if(isAuthedReq(ResourceTypes.MENU)){
			Dto paramDto = getParamAsDto();
			String queryParam= paramDto.getAsString("queryParam");
			Page<SbPromoteLink> page = newPage(paramDto);
			SbPromoteLink link = new SbPromoteLink();
			page = sbPromoteLinkService.findSbPromoteLinkInfoByPage(link, page, paramDto);
			
			mav.addObject("pm", page);
			mav.addObject("queryParam",queryParam);
			mav.addObject(Const.CT_MENU_NAV,"推广地址监控");
			mav.addObject("end_date",paramDto.getAsString("end_date"));
			mav.addObject("start_date",paramDto.getAsString("start_date"));
			mav.addObject("permitFBtn", getPermitBtn(ResourceTypes.FUNC));	
			mav.addObject("permitTBtn", getPermitBtn(ResourceTypes.BUTTON));
			mav.setViewName("admin/jsp/promote/promote_list");
		}else{
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}
	
	
	/**
	 * 添加推广地址
	 * @return
	 */
	@RequestMapping(value = "goadd", method = RequestMethod.GET)
	public ModelAndView goAddEntrance() {
		ModelAndView mav = new ModelAndView();
		if(isAuthedReq(ResourceTypes.FUNC)){
			mav.addObject(Const.CT_MENU_NAV,"添加推广地址");
			mav.addObject("slist",DomainStateType.values());
			mav.addObject("tlist",ContentDomainType.values());
			mav.addObject("rlist",PassMethodType.values());
		    mav.setViewName("admin/jsp/promote/promote_info");
		}else{
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}
	
	/**
	 * 修改域名
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "goedit", method = RequestMethod.GET)
	public ModelAndView goEditEntrance(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		if(isAuthedReq(ResourceTypes.FUNC) || isAuthedReq(ResourceTypes.BUTTON)){
			Dto paramDto = getParamAsDto();
			String domain_id = paramDto.getAsString("link_id");
			SbPromoteLink link = new SbPromoteLink();
			if(CPSUtil.isNotEmpty(domain_id)){
				link.setLinkId(Integer.valueOf(domain_id));
				link = sbPromoteLinkService.find(link);
			}
			mav.addObject("linkInfo", link);
			mav.addObject(Const.CT_MENU_NAV,"修改推广地址");
			mav.addObject("tlist",ContentDomainType.values());
			mav.addObject("slist",DomainStateType.values());
			mav.addObject("rlist",PassMethodType.values());
			mav.setViewName("admin/jsp/promote/promote_info");
		}else{
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}
	
	
	/**
	 * 保存推广地址
	 * @param param
	 * @return
	 */
	@RequestMapping(value="save", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes saveEntrance(SbPromoteLink link){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(isAuthedReq(ResourceTypes.FUNC,GO_ADD_URL))){
			try {
				link.setUpdateTime(new Date());
				if(CPSUtil.isEmpty(link.getState())){
				   link.setState(DomainStateType.Valid.getState());
				}
				link.setLinkAddr(link.getLinkAddr().trim());
				if(sbPromoteLinkService.saveSbPromoteLinkInfo(link)){
					//更新缓存
		    		InitSystemCache.updateCache(Const.CT_PROMOTE_LINK_LIST);
					HessianClientUtil.updateCache(Const.CT_PROMOTE_LINK_LIST);
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
	 * 修改域名
	 * @param code
	 * @return
	 */
	@RequestMapping(value="edit", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes editEntrance(SbPromoteLink link){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(isAuthedReq(ResourceTypes.BUTTON,GO_EDIT_URL))){
			try {
				SbPromoteLink _link = new SbPromoteLink();
				_link.setLinkId(link.getLinkId());
				_link = sbPromoteLinkService.find(_link);
				if(CPSUtil.isNotEmpty(_link)){
					link.setUpdateTime(new Date());
					link.setLinkAddr(link.getLinkAddr().trim());
					if(sbPromoteLinkService.editSbPromoteLinkInfo(link)){
						//更新缓存
			    		InitSystemCache.updateCache(Const.CT_PROMOTE_LINK_LIST);
						HessianClientUtil.updateCache(Const.CT_PROMOTE_LINK_LIST);
						ar.setSucceedMsg(Const.UPDATE_SUCCEED);
					}else{
						ar.setFailMsg(Const.UPDATE_FAIL);
					}
				}else{
					ar.setFailMsg(Const.DATA_UNEXIST);
				}
				
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.UPDATE_FAIL);
			}
		}
		return ar;
	}
	
	
	/**
	 * 删除域名
	 * @param domain_id
	 * @return
	 */
	@RequestMapping(value="trash", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes trashDomain(String link_id){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(isAuthedReq(ResourceTypes.FUNC) || isAuthedReq(ResourceTypes.BUTTON))){
			try {
				if(CPSUtil.isNotEmpty(link_id)){
					if(sbPromoteLinkService.trashSbPromoteLinkList(link_id)){
						//更新缓存
			    		InitSystemCache.updateCache(Const.CT_PROMOTE_LINK_LIST);
						HessianClientUtil.updateCache(Const.CT_PROMOTE_LINK_LIST);
						ar.setSucceedMsg(Const.DEL_SUCCEED);
					}else{
						ar.setFailMsg(Const.DEL_FAIL);
					}
				}else{
					ar.setFailMsg(Const.NO_PARAM_ERROR);
				}
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DEL_FAIL);
			}
		}
		return ar;
	}
	

}
