package com.ccnet.admin.controller;

import java.util.Date;
import java.util.List;

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
import com.ccnet.core.common.StateType;
import com.ccnet.core.common.UserSateType;
import com.ccnet.core.common.ajax.AjaxRes;
import com.ccnet.core.common.cache.InitSystemCache;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.StringHelper;
import com.ccnet.core.common.utils.UniqueID;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.base.ResourceTypes;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.common.utils.security.UserInfoShiroUtil;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.entity.SbContentDomain;
import com.ccnet.core.entity.SbMemberDomain;
import com.ccnet.core.entity.UserInfo;
import com.ccnet.core.service.ContentDomainInfoService;
import com.ccnet.core.service.MemberDomainService;
import com.ccnet.cps.entity.MemberInfo;
import com.ccnet.cps.service.MemberInfoService;
/**
 * 域名池管理控制类
 * @author jackie wang
 *
 */
@Controller
@RequestMapping("/backstage/domain")
public class ContentDomainInfoController extends AdminBaseController<SbContentDomain> {
	
	//静态地址
	public static String INDEX_URL = "/backstage/domain/index";
	public static String GO_ADD_URL = "/backstage/domain/goadd";
	public static String GO_EDIT_URL = "/backstage/domain/goedit";
	public static String GO_PARSE_URL = "/backstage/domain/geparse";
	public static String GO_PERSONAL_URL = "/backstage/domain/personal";
	
	@Autowired
	private MemberInfoService memberInfoService;
	@Autowired
	private ContentDomainInfoService contentDomainInfoService;
	@Autowired
	private MemberDomainService memberContentDomainService;
	
	
	/**
	 * 域名管理首页
	 */
	@RequestMapping("index")
	public ModelAndView domainIndex(){	
		ModelAndView mav = new ModelAndView();
		if(isAuthedReq(ResourceTypes.MENU)){
			Dto paramDto = getParamAsDto();
			String queryParam= paramDto.getAsString("queryParam");
			Integer enabled= paramDto.getAsInteger("enabled");
			Integer domainType= paramDto.getAsInteger("domainType");
			Page<SbContentDomain> page = newPage(paramDto);
			SbContentDomain domain = new SbContentDomain();
			if(CPSUtil.isNotEmpty(enabled)){
				domain.setEnabled(enabled);
			}
			if(CPSUtil.isNotEmpty(domainType)){
				domain.setDomainType(domainType);
			}
			page = contentDomainInfoService.findContentDomainInfoByPage(domain, page, paramDto);
			
			mav.addObject("pm", page);
			mav.addObject("queryParam",queryParam);
			mav.addObject(Const.CT_MENU_NAV,"域名监控");
			mav.addObject("end_date",paramDto.getAsString("end_date"));
			mav.addObject("start_date",paramDto.getAsString("start_date"));
			mav.addObject("domainType",domainType);
			mav.addObject("enabled",enabled);
			mav.addObject("tlist", ContentDomainType.values());
			mav.addObject("slist", DomainStateType.values());
			mav.addObject("permitFBtn", getPermitBtn(ResourceTypes.FUNC));	
			mav.addObject("permitTBtn", getPermitBtn(ResourceTypes.BUTTON));
			mav.setViewName("admin/jsp/domain/domain_list");
		}else{
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}
	
	/**
	 * 添加域名
	 * @return
	 */
	@RequestMapping(value = "goadd", method = RequestMethod.GET)
	public ModelAndView goAddDomain() {
		ModelAndView mav = new ModelAndView();
		if(isAuthedReq(ResourceTypes.FUNC)){
			mav.addObject(Const.CT_MENU_NAV,"添加域名");
			mav.addObject("slist",DomainStateType.values());
			mav.addObject("tlist",ContentDomainType.values());
		    mav.setViewName("admin/jsp/domain/domain_info");
		}else{
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}
	
	/**
	 * 域名泛解析
	 * @return
	 */
	@RequestMapping(value = "geparse", method = RequestMethod.GET)
	public ModelAndView goGeparseDomain() {
		ModelAndView mav = new ModelAndView();
		if(isAuthedReq(ResourceTypes.FUNC)){
			mav.addObject(Const.CT_MENU_NAV,"域名泛解析");
			mav.addObject("slist",DomainStateType.values());
			mav.addObject("tlist",ContentDomainType.values());
		    mav.setViewName("admin/jsp/domain/geparse_domain");
		}else{
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}
	
	/**
	 * 专属域名
	 * @return
	 */
	@RequestMapping(value = "personal", method = RequestMethod.GET)
	public ModelAndView personalDomain() {
		Dto paramDto = getParamAsDto();
		ModelAndView mav = new ModelAndView();
		if(isAuthedReq(ResourceTypes.FUNC)){
			//获取所有会员
			MemberInfo memberInfo = new MemberInfo();
			memberInfo.setUserState(UserSateType.VALID.getType());
			List<MemberInfo> memberList = memberInfoService.findList(memberInfo);
			
			mav.addObject("memberList",memberList);
			mav.addObject(Const.CT_MENU_NAV,"专属域名");
			mav.addObject("domainId",paramDto.getAsString("domain_id"));
		    mav.setViewName("admin/jsp/domain/member_domain_info");
		}else{
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}
	
	
	/**
	 * 保存专属域名
	 * @param param
	 * @return
	 */
	@RequestMapping(value="personal/save", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes savePersonal(){
		AjaxRes ar=getAjaxRes();
		Dto paramDto = getParamAsDto();
		if(ar.setNoAuth(isAuthedReq(ResourceTypes.FUNC,GO_PERSONAL_URL))){
			try {
				String remark = paramDto.getAsString("remark");
				String domainId = paramDto.getAsString("domainId");
				String memeberId = paramDto.getAsString("memeberId");
				UserInfo userInfo = UserInfoShiroUtil.getCurrentUser();
				if(StringHelper.checkParameter(domainId,memeberId)){
					Integer dmId = null;
					Integer memberId = Integer.valueOf(memeberId);
					SbMemberDomain memberDomain = null;
					String domain_id [] = domainId.split(",");
					for (String dm_id : domain_id) {
						dmId = Integer.valueOf(dm_id);
						memberDomain = new SbMemberDomain();
						memberDomain.setRemark(remark);
						memberDomain.setDomainId(dmId);
						memberDomain.setMemberId(memberId);
						memberDomain.setCreateTime(new Date());
						memberDomain.setUserId(userInfo.getUserId());
						if(CPSUtil.isEmpty(memberContentDomainService.getMemberContentDomain(memberId,dmId))){
						   memberContentDomainService.saveMemberContentDomain(memberDomain);
						   contentDomainInfoService.updateContentDomainPersonal(dm_id,StateType.Valid.getState());
						}
					}
					
					InitSystemCache.updateCache(Const.CT_DOMAIN_LIST);
					InitSystemCache.updateCache(Const.CT_ZS_DOMAIN_LIST);
					HessianClientUtil.updateCache(Const.CT_DOMAIN_LIST);
					HessianClientUtil.updateCache(Const.CT_ZS_DOMAIN_LIST);
					ar.setSucceedMsg(Const.SAVE_SUCCEED);
				}else{
				  ar.setFailMsg(Const.NO_PARAM_ERROR);
				}
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.SAVE_FAIL);
			}
		}
		return ar;
	}
	
	/**
	 * 保存解析
	 * @param param
	 * @return
	 */
	@RequestMapping(value="saveParse", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes saveParseDomain(){
		AjaxRes ar=getAjaxRes();
		Dto paramDto = getParamAsDto();
		if(ar.setNoAuth(isAuthedReq(ResourceTypes.FUNC,GO_PARSE_URL))){
			try {
				SbContentDomain domain = new SbContentDomain();
				domain.setCreateTime(new Date());
				//主域名
				String domainName = paramDto.getAsString("domainName");
				Integer domainType = paramDto.getAsInteger("domainType");//生成类型
				Integer domainPrefix = paramDto.getAsInteger("domainPrefix");//前缀类型
				Integer domainLength = paramDto.getAsInteger("domainLength");//生成长度
				Integer domainNumber = paramDto.getAsInteger("domainNumber");//生成个数
				
				if(CPSUtil.isNotEmpty(domainName) && CPSUtil.isNotEmpty(domainPrefix)){
					String preffix = null;
					String addDomian = null;
					for (int i = 0; i < domainNumber; i++) {
						preffix = UniqueID.getUniqueID(domainLength, domainPrefix);
						addDomian = preffix+"."+domainName.trim();
						domain.setDomainName(addDomian);
						domain.setDomainType(domainType);
						domain.setPersonal(StateType.InValid.getState());
						domain.setEnabled(DomainStateType.Valid.getState());//默认有效
						domain.setOrderNumber(i);
						if(!contentDomainInfoService.checkDomainExist(addDomian)){
						   contentDomainInfoService.saveContentDomainInfo(domain);
						}
					}
					
					InitSystemCache.updateCache(Const.CT_DOMAIN_LIST);
					InitSystemCache.updateCache(Const.CT_ZS_DOMAIN_LIST);
					HessianClientUtil.updateCache(Const.CT_DOMAIN_LIST);
					HessianClientUtil.updateCache(Const.CT_ZS_DOMAIN_LIST);
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
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "goedit", method = RequestMethod.GET)
	public ModelAndView goEditDomain(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		if(isAuthedReq(ResourceTypes.FUNC) || isAuthedReq(ResourceTypes.BUTTON)){
			Dto paramDto = getParamAsDto();
			String domain_id = paramDto.getAsString("domain_id");
			SbContentDomain domain = new SbContentDomain();
			if(CPSUtil.isNotEmpty(domain_id)){
				domain.setDomainId(domain_id);
				domain = contentDomainInfoService.find(domain);
			}
			mav.addObject("domainInfo", domain);
			mav.addObject(Const.CT_MENU_NAV,"修改域名");
			mav.addObject("tlist",ContentDomainType.values());
			mav.addObject("slist",DomainStateType.values());
			mav.setViewName("admin/jsp/domain/domain_info");
		}else{
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}
	
	/**
	 * 增加域名
	 * @param param
	 * @return
	 */
	@RequestMapping(value="save", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes saveDomain(SbContentDomain domain){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(isAuthedReq(ResourceTypes.FUNC,GO_ADD_URL))){
			try {
				domain.setCreateTime(new Date());
				domain.setPersonal(StateType.InValid.getState());
				domain.setDomainName(domain.getDomainName().trim());
				if(contentDomainInfoService.saveContentDomainInfo(domain)){
					InitSystemCache.updateCache(Const.CT_DOMAIN_LIST);
					InitSystemCache.updateCache(Const.CT_ZS_DOMAIN_LIST);
					HessianClientUtil.updateCache(Const.CT_DOMAIN_LIST);
					HessianClientUtil.updateCache(Const.CT_ZS_DOMAIN_LIST);
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
	public AjaxRes editDomain(SbContentDomain domain){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(isAuthedReq(ResourceTypes.BUTTON,GO_EDIT_URL))){
			try {
				SbContentDomain odomain = new SbContentDomain();
				odomain.setDomainId(domain.getDomainId());
				odomain = contentDomainInfoService.find(odomain);
				if(CPSUtil.isNotEmpty(odomain)){
					domain.setLastUpdateTime(new Date());
					domain.setDomainName(domain.getDomainName().trim());
					domain.setPersonal(StateType.InValid.getState());
					domain.setCreateTime(odomain.getCreateTime());
					if(contentDomainInfoService.editContentDomainInfo(domain)){
						InitSystemCache.updateCache(Const.CT_DOMAIN_LIST);
						InitSystemCache.updateCache(Const.CT_ZS_DOMAIN_LIST);
						HessianClientUtil.updateCache(Const.CT_DOMAIN_LIST);
						HessianClientUtil.updateCache(Const.CT_ZS_DOMAIN_LIST);
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
	public AjaxRes trashDomain(String domain_id){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(isAuthedReq(ResourceTypes.FUNC) || isAuthedReq(ResourceTypes.BUTTON))){
			try {
				if(CPSUtil.isNotEmpty(domain_id)){
					if(contentDomainInfoService.trashContentDomainList(domain_id)){
						InitSystemCache.updateCache(Const.CT_DOMAIN_LIST);
						InitSystemCache.updateCache(Const.CT_ZS_DOMAIN_LIST);
						HessianClientUtil.updateCache(Const.CT_DOMAIN_LIST);
						HessianClientUtil.updateCache(Const.CT_ZS_DOMAIN_LIST);
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
