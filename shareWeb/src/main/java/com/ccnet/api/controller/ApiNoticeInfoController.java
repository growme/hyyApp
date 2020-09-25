package com.ccnet.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ccnet.api.entity.ResultCode.BasicCode;
import com.ccnet.api.entity.ResultDTO;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.controller.BaseController;
import com.ccnet.core.dao.base.Page;
import com.ccnet.cps.entity.SystemNotice;
import com.ccnet.cps.service.SystemNoticeService;
import com.ccnet.jpz.entity.JpNotice;
import com.ccnet.jpz.service.JpNoticeService;

/**
 * 通知控制器
 * 
 * @author JackieWang
 *
 */
@Controller
@RequestMapping("/api/notice/")
public class ApiNoticeInfoController extends BaseController<SystemNotice> {

	@Autowired
	SystemNoticeService systemNoticeService;
	@Autowired
	private JpNoticeService jpNoticeService;

	@RequestMapping("index")
	@ResponseBody
	public ResultDTO<?> noticeIndex(Model model, Page<SystemNotice> arg1) {

		// 获取用户通告列表
		SystemNotice notice = new SystemNotice();
		notice.setState(1);
		notice.setNoticeType(1);
		Page<SystemNotice> noticeList = systemNoticeService.findByPage(notice, arg1);
		return ResultDTO.OK(noticeList.getResults());
	}

	@RequestMapping("detail")
	@ResponseBody
	public ResultDTO<?> noticeDetail(Model model) {
		// 获取参数
		Dto paramDto = getParamAsDto();
		String noticeId = paramDto.getAsString("noticeId");
		SystemNotice notice = new SystemNotice();
		if (CPSUtil.isNotEmpty(noticeId)) {
			notice.setNoticeId(Integer.valueOf(noticeId));
			notice = systemNoticeService.findSystemNoticeByID(notice);
		}
		model.addAttribute("noticeInfo", notice);
		return ResultDTO.OK(notice);
	}

	@RequestMapping(value = "notice")
	@ResponseBody
	public ResultDTO<?> notice(JpNotice jpNotice) {
		try {
			if (CPSUtil.isEmpty(jpNotice.getType())) {
				return ResultDTO.ERROR(BasicCode.参数错误);
			}
			JpNotice table = jpNoticeService.find(jpNotice);
			return ResultDTO.OK(table);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDTO.ERROR(BasicCode.逻辑错误);
		}
	}
}
