package com.ccnet.admin.bh.controller;

import com.ccnet.admin.bh.entity.SbCommonProblem;
import com.ccnet.admin.bh.service.SbCommonProblemService;
import com.ccnet.admin.controller.AdminBaseController;
import com.ccnet.core.common.NoticeType;
import com.ccnet.core.common.StateType;
import com.ccnet.core.common.UserType;
import com.ccnet.core.common.ajax.AjaxRes;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.base.ResourceTypes;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.common.utils.security.UserInfoShiroUtil;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.entity.UserInfo;
import com.ccnet.cps.entity.SystemNotice;
import com.ccnet.cps.service.SystemNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

/**
 * 常见问题管理
 * @author jackie wang
 *
 */
@Controller
@RequestMapping("/backstage/problem/")
public class SbCommonProblemController extends AdminBaseController<SbCommonProblem> {
	
	public static String GO_ADD_URL = "/backstage/problem/add";
	public static String GO_EDIT_URL = "/backstage/problem/edit";
	
	@Autowired
	private SbCommonProblemService sbCommonProblemService;
	
	/**
	 * 常见问题首页
	 */
	@RequestMapping("index")
	public ModelAndView noticeIndex() {
		Dto paramDto = getParamAsDto();
		ModelAndView mav = new ModelAndView();
		UserInfo userInfo = UserInfoShiroUtil.getCurrentUser();
		Page<SbCommonProblem> page = newPage(paramDto);
		if (isAuthedReq(ResourceTypes.MENU)) {
			SbCommonProblem sbCommonProblem = new SbCommonProblem();
			page = sbCommonProblemService.findProblomByPage(sbCommonProblem,page, paramDto);
			mav.addObject("pm", page);
			mav.addObject("title", paramDto.getAsString("title"));
			mav.addObject("type", paramDto.getAsString("type"));
			mav.addObject("permitFBtn", getPermitBtn(ResourceTypes.FUNC));
			mav.addObject("permitTBtn", getPermitBtn(ResourceTypes.BUTTON));
			mav.addObject(Const.CT_MENU_NAV, "常见问题");
			mav.setViewName("admin/jsp/problem/problem_list");
		} else {
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}
	
	/**
	 * 添加问题
	 * @return
	 */
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public ModelAndView goAddProblem() {
		ModelAndView mav = new ModelAndView();
		UserInfo userInfo = UserInfoShiroUtil.getCurrentUser();
		if(isAuthedReq(ResourceTypes.FUNC) && userInfo.getUserType()==UserType.SYSTEMADMIN.getType()){
//			mav.addObject("problemTypeList",NoticeType.values());
			mav.addObject(Const.CT_MENU_NAV,"增加常见问题");
			mav.setViewName("admin/jsp/problem/problem_info");
		}else{
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}
	
	
	/**
	 * 修改
	 * @return
	 */
	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public ModelAndView goEditProblem() {
		ModelAndView mav = new ModelAndView();
		Dto paramDto = getParamAsDto();
		UserInfo userInfo = UserInfoShiroUtil.getCurrentUser();
		if(isAuthedReq(ResourceTypes.BUTTON) && userInfo.getUserType()==UserType.SYSTEMADMIN.getType()){
			SbCommonProblem sbCommonProblem = new SbCommonProblem();
			String id = paramDto.getAsString("id");
			if(CPSUtil.isNotEmpty(id)){
				sbCommonProblem.setId(Integer.valueOf(id));
				sbCommonProblem = sbCommonProblemService.findProblemByID(sbCommonProblem);
			}
			mav.addObject("systemProblemInfo",sbCommonProblem);
			mav.addObject(Const.CT_MENU_NAV,"修改常见问题");
			mav.setViewName("admin/jsp/problem/problem_info");
		}else{
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}
	
	/**
	 * 增加常见问题
	 * @param systemProblem
	 * @return
	 */
	@RequestMapping(value="save", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes saveProblem(SbCommonProblem sbCommonProblem){
		AjaxRes ar=getAjaxRes();
		UserInfo userInfo = UserInfoShiroUtil.getCurrentUser();
		if(ar.setNoAuth(isAuthedReq(ResourceTypes.FUNC,GO_ADD_URL) && userInfo.getUserType()==UserType.SYSTEMADMIN.getType())){
			try {
				sbCommonProblem.setCreateTime(new Date());
				//systemNotice.setState(StateType.Valid.getState());//默认有效
				if(sbCommonProblemService.saveProblem(sbCommonProblem)){
					ar.setSucceedMsg(Const.SAVE_SUCCEED);
				}else{
					ar.setFailMsg(Const.SAVE_FAIL);
				}
			}catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.SAVE_FAIL);
			}
		}
		return ar;
	}
	
	
	/**
	 * 更新常见问题
	 * @param systemProblem
	 * @return
	 */
	@RequestMapping(value="update", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes updateProblem(SbCommonProblem sbCommonProblem){
		AjaxRes ar=getAjaxRes();
		UserInfo userInfo = UserInfoShiroUtil.getCurrentUser();
		if(ar.setNoAuth(isAuthedReq(ResourceTypes.BUTTON,GO_EDIT_URL) && userInfo.getUserType()==UserType.SYSTEMADMIN.getType())){
			try {
				  SbCommonProblem problem = new SbCommonProblem();
				  problem.setId(sbCommonProblem.getId());
				  problem = sbCommonProblemService.findProblemByID(problem);
				  if(problem!=null){
					  sbCommonProblem.setCreateTime(problem.getCreateTime());
					  //systemNotice.setState(notice.getState());
					  if(sbCommonProblemService.editProblem(sbCommonProblem)){
						ar.setSucceedMsg(Const.UPDATE_SUCCEED);
					  }else{
						ar.setFailMsg(Const.UPDATE_FAIL);
					  }
				  }else{
					  ar.setFailMsg(Const.DATA_UNEXIST);
				  }
			}catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.UPDATE_FAIL);
			}
		}
		return ar;
	}
	
//	/**
//	 * 撤销常见问题
//	 * @param id
//	 * @return
//	 */
//	@RequestMapping(value="revoke", method=RequestMethod.POST)
//	@ResponseBody
//	public AjaxRes revokeProblem(Integer id){
//		AjaxRes ar=getAjaxRes();
//		UserInfo userInfo = UserInfoShiroUtil.getCurrentUser();
//		if(ar.setNoAuth((isAuthedReq(ResourceTypes.FUNC) || isAuthedReq(ResourceTypes.BUTTON)) && userInfo.getUserType()==UserType.SYSTEMADMIN.getType())){
//			try {
//				SystemNotice notice = new SystemNotice();
//				  notice.setid(id);
//				  notice = systemNoticeService.findSystemNoticeByID(notice);
//				  if(notice!=null){
//					  notice.setState(StateType.InValid.getState());
//					  if(systemNoticeService.editSystemNotice(notice)){
//						ar.setSucceedMsg(Const.UPDATE_SUCCEED);
//					  }else{
//						ar.setFailMsg(Const.UPDATE_FAIL);
//					  }
//				  }else{
//					  ar.setFailMsg(Const.DATA_UNEXIST);
//				  }
//			} catch (Exception e) {
//				logger.error(e.toString(),e);
//				ar.setFailMsg(Const.UPDATE_FAIL);
//			}
//		}
//		return ar;
//	}
	
	/**
	 * 删除常见问题
	 * @param id
	 * @return
	 */
	@RequestMapping(value="trash", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes trashProblem(Integer id){
		AjaxRes ar=getAjaxRes();
		UserInfo userInfo = UserInfoShiroUtil.getCurrentUser();
		if(ar.setNoAuth((isAuthedReq(ResourceTypes.FUNC) || isAuthedReq(ResourceTypes.BUTTON)) && userInfo.getUserType()==UserType.SYSTEMADMIN.getType())){
			try {
				SbCommonProblem problem = new SbCommonProblem();
				problem.setId(id);
				if(sbCommonProblemService.trashProblem(problem)){
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
