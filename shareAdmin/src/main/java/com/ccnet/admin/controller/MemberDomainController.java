package com.ccnet.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ccnet.admin.controller.api.HessianClientUtil;
import com.ccnet.core.common.DomainStateType;
import com.ccnet.core.common.ContentDomainType;
import com.ccnet.core.common.StateType;
import com.ccnet.core.common.ajax.AjaxRes;
import com.ccnet.core.common.cache.InitSystemCache;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.base.ResourceTypes;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.controller.BaseController;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.entity.SbMemberDomain;
import com.ccnet.core.service.ContentDomainInfoService;
import com.ccnet.core.service.MemberDomainService;

/**
 * 会员专属域名
 * @author JackieWang
 *
 */
@Controller
@RequestMapping("/backstage/member/domain/")
public class MemberDomainController extends BaseController<SbMemberDomain> {

    //静态地址
	public static String INDEX_URL = "/backstage/member/domain/index";
	public static String GO_ADD_URL = "/backstage/member/domain/goadd";
	public static String GO_EDIT_URL = "/backstage/member/domain/goedit";
	
	@Autowired
	private ContentDomainInfoService contentDomainInfoService;
	@Autowired
	private MemberDomainService memberDomainService;
	
	/**
	 * 域名管理首页
	 */
	@RequestMapping("index")
	public ModelAndView domainIndex(){	
		ModelAndView mav = new ModelAndView();
		if(isAuthedReq(ResourceTypes.MENU)){
			Dto paramDto = getParamAsDto();
			String memberName= paramDto.getAsString("memberName");
			Page<SbMemberDomain> page = newPage(paramDto);
			SbMemberDomain domain = new SbMemberDomain();
			page = memberDomainService.findMemberContentDomainByPage(domain, page, paramDto);
			
			mav.addObject("pm", page);
			mav.addObject("memberName",memberName);
			mav.addObject(Const.CT_MENU_NAV,"专属域名");
			mav.addObject("end_date",paramDto.getAsString("end_date"));
			mav.addObject("start_date",paramDto.getAsString("start_date"));
			mav.addObject("permitFBtn", getPermitBtn(ResourceTypes.FUNC));	
			mav.addObject("permitTBtn", getPermitBtn(ResourceTypes.BUTTON));
			mav.setViewName("admin/jsp/domain/member_domain_list");
		}else{
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}
	
	/**
	 * 添加域名
	 * @return
	 */
	@RequestMapping(value = "personal", method = RequestMethod.GET)
	public ModelAndView goAddDomain() {
		ModelAndView mav = new ModelAndView();
		if(isAuthedReq(ResourceTypes.FUNC)){
			mav.addObject(Const.CT_MENU_NAV,"添加域名");
			mav.addObject("slist",DomainStateType.values());
			mav.addObject("tlist",ContentDomainType.values());
		    mav.setViewName("admin/jsp/domain/member_domain_info");
		}else{
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}
	
	/**
	 * 删除专属域名
	 * @param domain_id
	 * @return
	 */
	@RequestMapping(value="trash", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes trashPersonalDomain(String domain_id){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(isAuthedReq(ResourceTypes.FUNC) || isAuthedReq(ResourceTypes.BUTTON))){
			try {
				if(CPSUtil.isNotEmpty(domain_id)){
					//批量更新域名专属状态
					SbMemberDomain domain = null;
					String dmIds[] = domain_id.split(",");
					for (String dm_id : dmIds) {
						 domain = memberDomainService.findMemberContentDomainByID(Integer.valueOf(dm_id));
						 if(CPSUtil.isNotEmpty(domain)){
						    contentDomainInfoService.updateContentDomainPersonal(domain.getDomainId()+"", StateType.InValid.getState());
						 }
					}
					//删除专属
					if(memberDomainService.trashMemberContentDomainList(domain_id)){
						InitSystemCache.updateCache(Const.CT_DOMAIN_LIST);
						InitSystemCache.updateCache(Const.CT_ZS_DOMAIN_LIST);
						HessianClientUtil.updateCache(Const.CT_DOMAIN_LIST);
						HessianClientUtil.updateCache(Const.CT_ZS_DOMAIN_LIST);
					}
					ar.setSucceedMsg(Const.DEL_SUCCEED);
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
