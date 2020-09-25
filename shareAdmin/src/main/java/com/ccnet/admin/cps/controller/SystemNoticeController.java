package com.ccnet.admin.cps.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
/**
 * 公告管理
 * @author jackie wang
 *
 */
@Controller
@RequestMapping("/backstage/notice/")
public class SystemNoticeController extends AdminBaseController<SystemNotice> {
	
	public static String GO_ADD_URL = "/backstage/notice/add";
	public static String GO_EDIT_URL = "/backstage/notice/edit";
	
	@Autowired
	private SystemNoticeService systemNoticeService;
	
	/**
	 * 公告管理首页
	 */
	@RequestMapping("index")
	public ModelAndView noticeIndex() {
		Dto paramDto = getParamAsDto();
		ModelAndView mav = new ModelAndView();
		UserInfo userInfo = UserInfoShiroUtil.getCurrentUser();
		Page<SystemNotice> page = newPage(paramDto);
		if (isAuthedReq(ResourceTypes.MENU)) {
			SystemNotice notice = new SystemNotice();
			page = systemNoticeService.findSystemNoticeByPage(notice,page, paramDto);
			mav.addObject("pm", page);
			mav.addObject("queryParam", paramDto.getAsString("queryParam"));
			mav.addObject("permitFBtn", getPermitBtn(ResourceTypes.FUNC));
			mav.addObject("permitTBtn", getPermitBtn(ResourceTypes.BUTTON));
			mav.addObject(Const.CT_MENU_NAV, "公告管理");
			mav.setViewName("admin/jsp/notice/notice_list");
		} else {
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}
	
	/**
	 * 添加公告
	 * @return
	 */
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public ModelAndView goAddNotice() {
		ModelAndView mav = new ModelAndView();
		Dto paramDto = getParamAsDto();
		UserInfo userInfo = UserInfoShiroUtil.getCurrentUser();
		if(isAuthedReq(ResourceTypes.FUNC) && userInfo.getUserType()==UserType.SYSTEMADMIN.getType()){
			mav.addObject("noticeTypeList",NoticeType.values());
			mav.addObject(Const.CT_MENU_NAV,"增加公告");
			mav.setViewName("admin/jsp/notice/notice_info");
		}else{
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}
	
	
	/**
	 * 修改公告
	 * @return
	 */
	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public ModelAndView goEditNotice() {
		ModelAndView mav = new ModelAndView();
		Dto paramDto = getParamAsDto();
		UserInfo userInfo = UserInfoShiroUtil.getCurrentUser();
		if(isAuthedReq(ResourceTypes.BUTTON) && userInfo.getUserType()==UserType.SYSTEMADMIN.getType()){
			SystemNotice systemNotice = new SystemNotice();
			String noticeId = paramDto.getAsString("noticeId");
			if(CPSUtil.isNotEmpty(noticeId)){
				systemNotice.setNoticeId(Integer.valueOf(noticeId));
				systemNotice = systemNoticeService.findSystemNoticeByID(systemNotice);
				if(!CPSUtil.isNotEmptyForInteger(systemNotice.getShowTop())){
					systemNotice.setShowTop(0);
				}
			}
			mav.addObject("systemNoticeInfo",systemNotice);
			mav.addObject("noticeTypeList",NoticeType.values());
			mav.addObject(Const.CT_MENU_NAV,"增加公告");
			mav.setViewName("admin/jsp/notice/notice_info");
		}else{
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}
	
	/**
	 * 增加公告
	 * @param systemNotice
	 * @return
	 */
	@RequestMapping(value="save", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes saveNotice(SystemNotice systemNotice){
		AjaxRes ar=getAjaxRes();
		UserInfo userInfo = UserInfoShiroUtil.getCurrentUser();
		if(ar.setNoAuth(isAuthedReq(ResourceTypes.FUNC,GO_ADD_URL) && userInfo.getUserType()==UserType.SYSTEMADMIN.getType())){
			try {
				systemNotice.setCreateTime(new Date());
				//systemNotice.setState(StateType.Valid.getState());//默认有效
				if(!CPSUtil.isNotEmptyForInteger(systemNotice.getShowTop())){
					systemNotice.setShowTop(0);
				}
				if(systemNoticeService.saveSystemNotice(systemNotice)){
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
	 * 更新公告
	 * @param systemNotice
	 * @return
	 */
	@RequestMapping(value="update", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes updateNotice(SystemNotice systemNotice){
		AjaxRes ar=getAjaxRes();
		UserInfo userInfo = UserInfoShiroUtil.getCurrentUser();
		if(ar.setNoAuth(isAuthedReq(ResourceTypes.BUTTON,GO_EDIT_URL) && userInfo.getUserType()==UserType.SYSTEMADMIN.getType())){
			try {
				  SystemNotice notice = new SystemNotice();
				  notice.setNoticeId(systemNotice.getNoticeId());
				  notice = systemNoticeService.findSystemNoticeByID(notice);
				  if(notice!=null){
					  systemNotice.setCreateTime(notice.getCreateTime());
					  //systemNotice.setState(notice.getState());
					  if(systemNoticeService.editSystemNotice(systemNotice)){
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
	
	/**
	 * 撤销公告
	 * @param noticeId
	 * @return
	 */
	@RequestMapping(value="revoke", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes revokeNotice(Integer noticeId){
		AjaxRes ar=getAjaxRes();
		UserInfo userInfo = UserInfoShiroUtil.getCurrentUser();
		if(ar.setNoAuth((isAuthedReq(ResourceTypes.FUNC) || isAuthedReq(ResourceTypes.BUTTON)) && userInfo.getUserType()==UserType.SYSTEMADMIN.getType())){
			try {
				SystemNotice notice = new SystemNotice();
				  notice.setNoticeId(noticeId);
				  notice = systemNoticeService.findSystemNoticeByID(notice);
				  if(notice!=null){
					  notice.setState(StateType.InValid.getState());
					  if(systemNoticeService.editSystemNotice(notice)){
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
	 * 删除公告
	 * @param noticeId
	 * @return
	 */
	@RequestMapping(value="trash", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes trashNotice(Integer noticeId){
		AjaxRes ar=getAjaxRes();
		UserInfo userInfo = UserInfoShiroUtil.getCurrentUser();
		if(ar.setNoAuth((isAuthedReq(ResourceTypes.FUNC) || isAuthedReq(ResourceTypes.BUTTON)) && userInfo.getUserType()==UserType.SYSTEMADMIN.getType())){
			try {
				SystemNotice notice = new SystemNotice();
				notice.setNoticeId(noticeId);
				if(systemNoticeService.trashSystemNotice(notice)){
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
