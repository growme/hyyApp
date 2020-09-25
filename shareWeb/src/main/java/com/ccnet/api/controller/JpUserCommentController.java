package com.ccnet.api.controller;

import java.util.Date;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ccnet.api.entity.Headers;
import com.ccnet.api.entity.JpUserAttention;
import com.ccnet.api.entity.ResultCode.BasicCode;
import com.ccnet.api.enums.HandleType;
import com.ccnet.api.entity.ResultDTO;
import com.ccnet.api.service.ApiContentService;
import com.ccnet.api.service.ApiUserCommentService;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.controller.BaseController;
import com.ccnet.core.dao.base.Page;
import com.ccnet.cps.entity.SbContentInfo;
import com.ccnet.cps.service.SbContentInfoService;
import com.ccnet.jpz.entity.JpUserComment;
import com.ccnet.jpz.entity.JpUserCommentCollect;
import com.ccnet.jpz.service.JpUserCommentService;

@Controller
@RequestMapping("/api/userComment/")
public class JpUserCommentController extends BaseController<JpUserComment> {

	@Autowired
	private JpUserCommentService jpUserCommentService;
	@Autowired
	private ApiUserCommentService apiUserCommentService;
	@Autowired
	private ApiContentService apiContentService;
	@Autowired
	private SbContentInfoService sbContentInfoService;

	/*
	 * 评论列表
	 */
	@ResponseBody
	@RequestMapping("commentList")
	public ResultDTO<?> commentList(Headers header, JpUserComment jpUserComment, Page<T> page) {
		try {
			if (CPSUtil.isEmpty(jpUserComment.getContentId())) {
				return ResultDTO.ERROR(BasicCode.参数错误);
			}
			if (CPSUtil.isEmpty(jpUserComment.getReplyType())) {
				return ResultDTO.ERROR(BasicCode.参数错误);
			}
			if (CPSUtil.isNotEmpty(header.getUserid())) {
				jpUserComment.setUserId(Integer.valueOf(header.getUserid()));
			}
			List<JpUserComment> list = apiUserCommentService.findUserCommentList(jpUserComment, page);
			Page<JpUserComment> page1 = new Page<>();
			page1.setResults(list);
			return ResultDTO.OK(page1);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDTO.ERROR(BasicCode.系统繁忙);
		}
	}

	/*
	 * 评论详情
	 */
	@ResponseBody
	@RequestMapping("commentView")
	public ResultDTO<?> commentView(Headers header, JpUserComment jpUserComment, Page<T> page) {
		try {
			if (CPSUtil.isEmpty(jpUserComment.getToCommentId())) {
				return ResultDTO.ERROR(BasicCode.参数错误);
			}
			// JpUserComment comment =
			// jpUserCommentService.getByID(jpUserComment.getToCommentId());
			JpUserComment jpUserComment1 = new JpUserComment();
			jpUserComment1.setId(jpUserComment.getToCommentId());
			if (CPSUtil.isNotEmpty(header.getUserid())) {
				jpUserComment1.setUserId(Integer.valueOf(header.getUserid()));
			}
			JpUserComment comment = apiUserCommentService.findUserCommentList(jpUserComment1, new Page<>()).get(0);
			SbContentInfo content = sbContentInfoService.getSbContentInfoByID(comment.getContentId());
			jpUserComment.setContentId(comment.getContentId());
			Integer zuozheId = null;
			if (comment.getReplyType().equals(1)) {
				comment.setLouzhu(true);
			}
			if (content != null && CPSUtil.isNotEmpty(content.getUserId())) {
				zuozheId = content.getUserId();
			}
			if (zuozheId.equals(comment.getUserId())) {
				comment.setZuozhe(true);
			}
			if (CPSUtil.isNotEmpty(header.getUserid())) {
				JpUserAttention jpUserAttention = new JpUserAttention();
				jpUserAttention.setToUid(comment.getUserId());
				jpUserAttention.setUserId(Integer.valueOf(header.getUserid()));
				jpUserComment.setUserId(Integer.valueOf(header.getUserid()));
			}
			List<JpUserComment> list = apiUserCommentService.findFirstUserCommentList(jpUserComment, page);
			for (int i = 0; i < list.size(); i++) {
				if (zuozheId.equals(list.get(i).getUserId())) {
					list.get(i).setZuozhe(true);
				}
			}
			comment.setCommentList(list);
			return ResultDTO.OK(comment);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDTO.ERROR(BasicCode.系统繁忙);
		}
	}

	/*
	 * 添加评论
	 */
	@ResponseBody
	@RequestMapping("commentAdd")
	public ResultDTO<?> addComment(Headers header, JpUserComment jpUserComment) {
		try {
			ResultDTO<?> dto = params(jpUserComment, header);
			if (dto.ok()) {
				jpUserComment.setUserId(Integer.valueOf(header.getUserid()));
				jpUserComment.setReplyNum(0);
				jpUserComment.setPraiseNum(0);
				jpUserComment.setCreateDate(new Date());
				if (jpUserComment.getReplyType().toString().equals("2")) {
					if (CPSUtil.isEmpty(jpUserComment.getToCommentId())) {
						return ResultDTO.ERROR(BasicCode.参数错误);
					}
					JpUserComment com = jpUserCommentService.getByID(jpUserComment.getToCommentId());
					if (com.getReplyType().toString().equals("1")) {
						jpUserComment.setFirstCommentId(com.getId());
					} else {
						jpUserComment.setFirstCommentId(com.getFirstCommentId());
					}
					jpUserComment.setToUid(com.getUserId());
				}
				jpUserCommentService.insert(jpUserComment);
				if (jpUserComment.getReplyType() == 1) {
					apiContentService.updateContentInfo(jpUserComment.getContentId(), HandleType.comment.getid(), "0");
				} else {
					apiUserCommentService.updateCommentInfo(jpUserComment.getToCommentId(), HandleType.comment.getid(),
							"0");
				}
				return ResultDTO.OK();
			}
			return dto;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDTO.ERROR(BasicCode.系统繁忙);
		}
	}

	/*
	 * 评论点赞
	 */
	@ResponseBody
	@RequestMapping("commentCollect")
	public ResultDTO<?> commentCollect(Headers header, JpUserCommentCollect jpUserCommentCollect) {
		try {
			if (CPSUtil.isEmpty(header.getUserid())) {
				return ResultDTO.ERROR(BasicCode.参数错误);
			}
			if (CPSUtil.isEmpty(jpUserCommentCollect.getCommentId())) {
				return ResultDTO.ERROR(BasicCode.参数错误);
			}
			jpUserCommentCollect.setUserId(Integer.valueOf(header.getUserid()));
			apiUserCommentService.insertOrDeleteCollect(jpUserCommentCollect);
			return ResultDTO.OK();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDTO.ERROR(BasicCode.系统繁忙);
		}
	}

	/*
	 * 评论点赞列表
	 */
	@ResponseBody
	@RequestMapping("commentCollectList")
	public ResultDTO<?> commentCollectList(JpUserCommentCollect jpUserCommentCollect, Page<T> page) {
		try {
			if (CPSUtil.isEmpty(jpUserCommentCollect.getCommentId())) {
				return ResultDTO.ERROR(BasicCode.参数错误);
			}
			return ResultDTO.OK(apiUserCommentService.findUserCommentCollectList(jpUserCommentCollect, page));
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDTO.ERROR(BasicCode.系统繁忙);
		}
	}


	private ResultDTO<?> params(JpUserComment jpUserComment, Headers header) {
		if (CPSUtil.isEmpty(jpUserComment.getContentId())) {
			return ResultDTO.ERROR(BasicCode.参数错误);
		}
		if (CPSUtil.isEmpty(jpUserComment.getReplyType())) {
			return ResultDTO.ERROR(BasicCode.参数错误);
		}
		if (CPSUtil.isEmpty(header.getUserid())) {
			return ResultDTO.ERROR(BasicCode.参数错误);
		}
		return ResultDTO.OK();
	}

}
