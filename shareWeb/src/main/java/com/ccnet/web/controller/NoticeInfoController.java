package com.ccnet.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ccnet.core.common.MessageKey;
import com.ccnet.core.common.NoticeType;
import com.ccnet.core.common.ResponseCode;
import com.ccnet.core.common.StateType;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.controller.BaseController;
import com.ccnet.cps.entity.SystemNotice;
import com.ccnet.cps.service.SystemNoticeService;

/**
 * 通知控制器
 * @author JackieWang
 *
 */
@Controller("noticeInfoController")
@RequestMapping("/notice/")
public class NoticeInfoController extends BaseController<SystemNotice> {

	@Autowired
	SystemNoticeService systemNoticeService;
	
	@RequestMapping("index")
	public String noticeIndex(Model model){
		
		//获取用户通告列表
		SystemNotice notice = new SystemNotice();
		List<SystemNotice> noticeList = systemNoticeService.findSystemNoticeList(notice);
		
		model.addAttribute("noticeList", noticeList);
		return "/user/jsp/notice/notice_list";
	}
	
	@RequestMapping("detail")
	public String noticeDetail(Model model){
		//获取参数
		Dto paramDto = getParamAsDto();
		String noticeId = paramDto.getAsString("nid");
		SystemNotice notice = new SystemNotice();
		if(CPSUtil.isNotEmpty(noticeId)){
			notice.setNoticeId(Integer.valueOf(noticeId));
			notice = systemNoticeService.findSystemNoticeByID(notice);
		}
		model.addAttribute("noticeInfo", notice);
		return "/user/jsp/notice/notice_detail";
	}
	
	/**
	 * 获取置顶公告
	 * @param req
	 * @param res
	 */
	@RequestMapping(value="least/list" ,produces="application/json;charset=UTF-8", method=RequestMethod.POST)
	public void getLeastNoticeList(HttpServletRequest req, HttpServletResponse res){
		//获取参数
		Dto dto = getParamAsDto();
		try {
			
			//获取最新的公告
			SystemNotice notice = new SystemNotice();
			notice.setNoticeType(NoticeType.SITE_NOTICE.getType());
			notice.setState(StateType.Valid.getState());
			notice.setShowTop(StateType.Valid.getState());
			List<SystemNotice> noticeList = systemNoticeService.findSystemNoticeList(notice);
			
			Map<String, Object> cMap = null;
			if(CPSUtil.listNotEmpty(noticeList)){
				cMap = new HashMap<String, Object>();
				cMap.put("nlist", noticeList);
				cMap.put("first", noticeList.get(0));
			}
			
			//处理返回提示
			Map<String, Object> map = new HashMap<String, Object>(0);
			map.put(MessageKey.apicode.name(),ResponseCode.CommSuccess.getCode());
			map.put(MessageKey.msg.name(),"获取数据成功");
			map.put("obj", cMap);
			responseWriter(map, res);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> map = new HashMap<String, Object>(0);
			map.put(MessageKey.apicode.name(),ResponseCode.CommError.getCode());
			map.put(MessageKey.msg.name(),"服务器异常,请稍后再试!");
			responseWriter(map, res);
		}
		
	}
	
	
}
