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

import com.ccnet.core.common.ajax.AjaxRes;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.ZHToEN;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.base.ResourceTypes;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.common.utils.idgenerator.IdGenerator;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.entity.Resources;
import com.ccnet.core.service.ResourcesService;
/**
 * 系统资源
 * @author jackie wang
 *
 */
@Controller
@RequestMapping("/backstage/resources/")
public class ResourcesController extends AdminBaseController<Resources>{
	//静态地址
	public static String INDEX_URL = "/backstage/resources/index";
	public static String GO_ADD_URL = "/backstage/resources/goadd";
	public static String GO_EDIT_URL = "/backstage/resources/goedit";
	
	@Autowired
	public ResourcesService service;
	/**
	 * 菜单首页
	 */
	@RequestMapping("index")
	public ModelAndView menuIndex() throws UnsupportedEncodingException{	
		ModelAndView mav = new ModelAndView();
		if(isAuthedReq(ResourceTypes.MENU)){
			Dto paramDto = getParamAsDto();
			Page<Resources> page = newPage(paramDto);
			List<Resources> menuList = service.queryMenuList(new Resources());
			page = service.findResourcesByPage(new Resources(), page, paramDto);
			
			mav.addObject("pm", page);
			mav.addObject(Const.CT_MENU_NAV,"资源管理");
			mav.addObject("pc",paramDto.getAsString("pc"));
			mav.addObject("queryParam",paramDto.getAsString("queryParam"));
			mav.addObject("state",paramDto.getAsString("state"));
			mav.addObject("menu_type",paramDto.getAsString("menu_type"));
			mav.addObject("parent_id",paramDto.getAsString("parent_id"));
			mav.addObject("parent_name",paramDto.getAsString("parent_name"));
			mav.addObject("slist",CPSUtil.getCodeList("MENU_STATE"));
			mav.addObject("tlist",CPSUtil.getCodeList("MENU_TYPE"));
			mav.addObject("permitFBtn", getPermitBtn(ResourceTypes.FUNC));	
			mav.addObject("permitTBtn", getPermitBtn(ResourceTypes.BUTTON));
			//mav.addObject("menuTreeData",getMenuTreeJsonData(menuList,"_self", INDEX_URL,true));
			mav.setViewName("admin/jsp/menu/menu_list");
		}else{
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		
		return mav;
	}
	
	
	/**
	 * 根据上级获取菜单
	 * @return
	 */
	@RequestMapping(value="getMenutree", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes getMenuTree(){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(isAuthedReq(ResourceTypes.MENU,INDEX_URL))){
			Dto paramDto = getParamAsDto();
			String parentId= paramDto.getAsString("parentId");
			try {
				Resources rs = new Resources();
				if(CPSUtil.isNotEmpty(parentId)){
					rs.setParentCode(parentId);
				}
				String menuTreeData = getMenuTreeJsonData(service.queryMenuList(rs), null);
				if(CPSUtil.isNotEmpty(menuTreeData)){
					ar.setSucceed(menuTreeData);
				}else{
					ar.setFailMsg(Const.DATA_FAIL);
				}
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
		}
		return ar;
	}
	
	
	/**
	 * 添加资源
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "goadd", method = RequestMethod.GET)
	public ModelAndView goAddMenu() {
		ModelAndView mav = new ModelAndView();
		if(isAuthedReq(ResourceTypes.FUNC)){	
			mav.addObject("optype", "add");
			mav.addObject("slist",CPSUtil.getCodeList("MENU_STATE"));
			mav.addObject("tlist",CPSUtil.getCodeList("MENU_TYPE"));
			mav.addObject("elist",CPSUtil.getCodeList("EXPANDED"));
			mav.addObject("flist",CPSUtil.getCodeList("LEAF_TYPE"));
			mav.setViewName("admin/jsp/menu/menu_info");
		}else{
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}
	
	/**
	 * 修改资源
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "goedit", method = RequestMethod.GET)
	public ModelAndView goEditMenu(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		if(isAuthedReq(ResourceTypes.FUNC) || isAuthedReq(ResourceTypes.BUTTON)){		
			Dto paramDto = getParamAsDto();
			Integer resource_id = paramDto.getAsInteger("resource_id");
			Resources resource = new Resources();
			Resources presource = new Resources();
			if(CPSUtil.isNotEmpty(resource_id)){
				resource = service.queryResourceByID(resource_id);
				presource = service.queryResourceByCode(resource.getParentCode());
				if(CPSUtil.isNotEmpty(presource)){
					resource.setParentName(presource.getResourceName());
				}
			}
			mav.addObject("menuInfo", resource);
			mav.addObject("optype", paramDto.getAsString("optype"));
			mav.addObject("slist",CPSUtil.getCodeList("MENU_STATE"));
			mav.addObject("tlist",CPSUtil.getCodeList("MENU_TYPE"));
			mav.addObject("elist",CPSUtil.getCodeList("EXPANDED"));
			mav.addObject("flist",CPSUtil.getCodeList("LEAF_TYPE"));
			mav.setViewName("admin/jsp/menu/menu_info");
		}else{
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}
	
	/**
	 * 复制资源
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "gocopy", method = RequestMethod.GET)
	public ModelAndView goCopyMenu(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		if(isAuthedReq(ResourceTypes.FUNC) || isAuthedReq(ResourceTypes.BUTTON)){
			Dto paramDto = getParamAsDto();
			Integer resource_id = paramDto.getAsInteger("resource_id");
			Resources resource = new Resources();
			Resources presource = new Resources();
			if(CPSUtil.isNotEmpty(resource_id)){
				resource = service.queryResourceByID(resource_id);
				presource = service.queryResourceByCode(resource.getParentCode());
				if(CPSUtil.isNotEmpty(presource)){
					resource.setParentName(presource.getResourceName());
				}
			}
			mav.addObject("menuInfo", resource);
			mav.addObject("optype", "copy");
			mav.addObject("slist",CPSUtil.getCodeList("MENU_STATE"));
			mav.addObject("tlist",CPSUtil.getCodeList("MENU_TYPE"));
			mav.addObject("elist",CPSUtil.getCodeList("EXPANDED"));
			mav.addObject("flist",CPSUtil.getCodeList("LEAF_TYPE"));
			mav.setViewName("admin/jsp/menu/menu_info");
		}else{
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}
	
	/**
	 * 获取图标
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "showIcon", method = RequestMethod.GET)
	public ModelAndView goShowIcon() {
		ModelAndView mav = new ModelAndView();
		boolean goaddPermit = isAuthedReq(ResourceTypes.FUNC,GO_ADD_URL);
		boolean goeditPermit = isAuthedReq(ResourceTypes.BUTTON,GO_EDIT_URL);
		if(goaddPermit || goeditPermit){
		   mav.setViewName("admin/jsp/menu/icon_info");
		}else{
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}
	
	/**
	 * 增加资源
	 * @param resources
	 * @return
	 */
	@RequestMapping(value="save", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes saveResources(Resources resources){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(isAuthedReq(ResourceTypes.FUNC,GO_ADD_URL))){
			try {
				String resourceName = resources.getResourceName();
				String parentCode = resources.getParentCode();
				//设置层级code
				String levelCode = "";
				String alisaCode = ZHToEN.getFSpell(resourceName);
				Resources parentRes = service.queryResourceByCode(parentCode);
				if(CPSUtil.isNotEmpty(parentRes)){
					levelCode = parentRes.getLevelCode() + "|" + alisaCode;
				}else{
					levelCode = alisaCode;
				}
				//先判断菜单是否已经存在
				boolean temp = service.checkMenuExist(levelCode);
				if(!temp){//保存菜单数据
					resources.setResourceCode(IdGenerator.getMenuIdGenerator(parentCode));
					resources.setAlisaCode(alisaCode.toLowerCase());
					resources.setLevelCode(levelCode.toLowerCase());
					resources.setCreateTime(new Date());
					temp = service.saveResources(resources);
					if(temp){
						ar.setSucceedMsg(Const.SAVE_SUCCEED);
					}else{
						ar.setFailMsg(Const.SAVE_FAIL);
					}
				}else{
					ar.setFailMsg(Const.DATA_EXIST);
				}
			}catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.SAVE_FAIL);
			}
		}
		return ar;
	}
	
	
	/**
	 * 获取层级code
	 * @param alisaCode
	 * @param parentResc
	 * @return
	 */
	private String getLevelCode(String alisaCode ,Resources parentResc){
		String levelCode = "";
		if(CPSUtil.isNotEmpty(parentResc)){
			levelCode = parentResc.getLevelCode() + "|" + alisaCode;
		}else{
			levelCode = alisaCode;
		}
		return levelCode;
	}
	
	/**
	 * 修改资源
	 * @param resources
	 * @return
	 */
	@RequestMapping(value="edit", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes editResources(Resources resources){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(isAuthedReq(ResourceTypes.BUTTON,GO_EDIT_URL))){
			try {
				String resourceName = resources.getResourceName();
				String resourceCode = resources.getResourceCode();
				String parentCode = resources.getParentCode();
				//获取当前上级
				Resources parentResc = service.queryResourceByCode(parentCode);
				String alisaCode = ZHToEN.getFSpell(resourceName);
				//设置层级code
				String levelCode = getLevelCode(alisaCode, parentResc);
				//生成新的编号
				String menu_id = IdGenerator.getMenuIdGenerator(parentCode);
				//验证是否已经存在(同层级)
				List<Resources> reList = service.getResourcesByLevelCode(levelCode);
				Resources reso = service.queryResourceByCode(resourceCode);
				if(CPSUtil.listEmpty(reList)){
					String oldLevelCode = reso.getLevelCode();
					if(!parentCode.equals(reso.getParentCode())){//切换了上级
						//先删除菜单对应的权限数据
						service.trashResourceFunc(reso);
						//替换下级菜单的code
						String new_code = null;
						String menu_code = null;
						String level_code = null;
						String parent_code = null;
						String newLevelCode = null;
						List<Resources> childList = service.getChildResourcesByCode(resourceCode);
						for (Resources res : childList) {
							menu_code = res.getResourceCode();
							parent_code = res.getParentCode();
							level_code = res.getLevelCode();
							new_code = menu_code.replace(parent_code, menu_id);
							newLevelCode = level_code.replace(oldLevelCode, levelCode);
							res.setResourceCode(new_code);
							res.setParentCode(menu_id);
							res.setLevelCode(newLevelCode);
							res.setUpdateTime(new Date());
							service.editResources(res);
						}
						resources.setResourceCode(menu_id);
					}
					
				}else{
					if(!reso.getResourceCode().equals(resources.getResourceCode())){
					    ar.setFailMsg(Const.DATA_EXIST);
					}
				}
				//修改当前菜单
				resources.setAlisaCode(alisaCode.toLowerCase());
				resources.setLevelCode(levelCode.toLowerCase());
				resources.setUpdateTime(new Date());
				resources.setCreateTime(reso.getCreateTime());
				
				if(service.editResources(resources)){
					ar.setSucceedMsg(Const.UPDATE_SUCCEED);
				}else{
					ar.setFailMsg(Const.UPDATE_FAIL);
				}
				
			}catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.UPDATE_FAIL);
			}
		}
		return ar;
	}
	
	/**
	 * 删除资源
	 * @param param_id
	 * @return
	 */
	@RequestMapping(value="trash", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes trashParam(String resource_id){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(isAuthedReq(ResourceTypes.FUNC) || isAuthedReq(ResourceTypes.BUTTON))){
			try {
				if(service.trashResources(resource_id)){
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
