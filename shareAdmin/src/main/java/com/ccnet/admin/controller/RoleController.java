package com.ccnet.admin.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ccnet.core.common.RoleType;
import com.ccnet.core.common.ajax.AjaxRes;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.base.ResourceTypes;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.controller.BaseController;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.entity.Resources;
import com.ccnet.core.entity.RoleInfo;
import com.ccnet.core.service.RoleInfoService;
/**
 * 角色管理
 * @author jackie wang
 *
 */
@Controller
@RequestMapping("/backstage/role/")
public class RoleController extends AdminBaseController<RoleInfo>{
	 
	//静态地址
	public static String INDEX_URL = "/backstage/role/index";
	public static String GO_ADD_URL = "/backstage/role/goadd";
	public static String GO_EDIT_URL = "/backstage/role/goedit";
	public static String GO_PERMIT_URL = "/backstage/role/setpermit";
	@Autowired
	public RoleInfoService roleService;
	
	/**
	 * 角色首页
	 */
	@RequestMapping("index")
	public ModelAndView roleIndex(HttpServletRequest request) throws UnsupportedEncodingException{	
		ModelAndView mav = new ModelAndView();
		if(isAuthedReq(ResourceTypes.MENU)){	
			Dto paramDto = getParamAsDto();
			paramDto.put("request", request);
			Page<RoleInfo> page = newPage(paramDto);
			page = roleService.findAllRoleByPage(new RoleInfo(), page,paramDto);
			
			mav.addObject("pm", page);
			mav.addObject("queryParam",paramDto.getAsString("queryParam"));
			mav.addObject("permitFBtn", getPermitBtn(ResourceTypes.FUNC));	
			mav.addObject("permitTBtn", getPermitBtn(ResourceTypes.BUTTON));
			mav.addObject(Const.CT_MENU_NAV,"角色管理");
			mav.setViewName("admin/jsp/role/role_list");
		}else{
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}
	
	
	/**
	 * 添加角色
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "goadd", method = RequestMethod.GET)
	public ModelAndView goAddRole() {
		ModelAndView mav = new ModelAndView();
		if(isAuthedReq(ResourceTypes.FUNC)){	
			RoleType[] str = RoleType.values();
			mav.addObject("tplist", RoleType.values());
			mav.addObject("enlist", CPSUtil.getCodeList("ENABLED"));
			mav.setViewName("admin/jsp/role/role_info");
		}else{
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}
	
	/**
	 * 修改角色
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "goedit", method = RequestMethod.GET)
	public ModelAndView goEditRole() {
		ModelAndView mav = new ModelAndView();
		if(isAuthedReq(ResourceTypes.FUNC) || isAuthedReq(ResourceTypes.BUTTON)){		
			Dto paramDto = getParamAsDto();
			Integer role_id = paramDto.getAsInteger("role_id");
			RoleInfo roleInfo = new RoleInfo();
			if(CPSUtil.isNotEmpty(role_id)){
				roleInfo.setRoleId(role_id);
				roleInfo = roleService.findRoleInfo(roleInfo);
			}
			mav.addObject("roleInfo", roleInfo);
			mav.addObject("tplist", RoleType.values());
			mav.addObject("enlist", CPSUtil.getCodeList("ENABLED"));
			mav.setViewName("admin/jsp/role/role_info");
		}else{
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}
	
	/**
	 * 判断是否已经存在
	 * @param rList
	 * @param resources
	 * @return
	 */
	private boolean checkResourcesExsit(List<Resources> rList ,Resources resources){
		boolean temp = false;
		if (CPSUtil.listNotEmpty(rList)) {
			for (Resources res : rList) {
				if (res.getResourceId().equals(resources.getResourceId())) {
					temp = true;
					break;
				}
			}
		}
		return temp;
	}
	
	/**
	 * 角色授权
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "setpermit", method = RequestMethod.GET)
	public ModelAndView setRolePermit() {
		ModelAndView mav = new ModelAndView();
		if(isAuthedReq(ResourceTypes.BUTTON)){	
			Dto paramDto = getParamAsDto();
			Integer role_id = paramDto.getAsInteger("role_id");
			List<Resources> menuList = null;
			List<Resources> smenuList = null;
			String jsonMenuData = null;
			if(CPSUtil.isNotEmpty(role_id)){
				//获取所有资源
				menuList = resourcesService.queryMenuList(new Resources());
				//已经授权资源
				smenuList = roleService.queryRoleResourcesByRoleID(role_id);
				if(CPSUtil.listNotEmpty(menuList)){
					for (Resources res : menuList) {
						if(checkResourcesExsit(smenuList,res)){
							res.setChecked(true);
						}else{
							res.setChecked(false);
						}
					}
				}
				//获取json字符串
				jsonMenuData = getMenuTreeJsonData(menuList, null);
			}
			//获取用户已经授于的权限
			mav.addObject("role_id", role_id);
			mav.addObject("listerTreeData", jsonMenuData);
			mav.setViewName("admin/jsp/role/role_permit_info");
		}else{
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}
	
	
	/**
	 * 增加角色
	 * @param role
	 * @return
	 */
	@RequestMapping(value="saverole", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes saveRole(RoleInfo role){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(isAuthedReq(ResourceTypes.FUNC,GO_ADD_URL))){
			try {
				role.setCreateTime(new Date());
				if(roleService.saveRoleInfo(role)){
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
	 * 更新角色
	 * @param o
	 * @return
	 */
	@RequestMapping(value="editrole", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes editRole(RoleInfo role){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(isAuthedReq(ResourceTypes.BUTTON,GO_EDIT_URL))){
			try {
				RoleInfo prole = roleService.findRoleByID(role.getRoleId());
				role.setCreateTime(prole.getCreateTime());
				role.setUpdateTime(new Date());
				if(roleService.editRoleInfo(role)){
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
	
	
	/**
	 * 角色授权
	 * @param o
	 * @return
	 */
	@RequestMapping(value="savepermit", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes saveRolePermit(){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(isAuthedReq(ResourceTypes.BUTTON,GO_PERMIT_URL))){
			try {
				Dto paramDto = getParamAsDto();
				Integer roleID = paramDto.getAsInteger("role_id");
				String resourceID = paramDto.getAsString("resource_id");
				if(roleService.saveRoleInfoResource(roleID, resourceID)){
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
	 * 删除角色
	 * @param role_id
	 * @return
	 */
	@RequestMapping(value="trashrole", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes trashRole(String role_id){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(isAuthedReq(ResourceTypes.FUNC) || isAuthedReq(ResourceTypes.BUTTON))){
			try {
				if(roleService.trashRoleInfoList(role_id)){
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
