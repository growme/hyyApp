package com.ccnet.api.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ccnet.api.entity.AppResultCode;
import com.ccnet.api.entity.Headers;
import com.ccnet.api.entity.ResultCode.BasicCode;
import com.ccnet.api.enums.HandleType;
import com.ccnet.api.entity.ResultDTO;
import com.ccnet.api.service.ApiContentService;
import com.ccnet.api.service.ApiUserCommentService;
import com.ccnet.api.util.UrlReplaceUtils;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.controller.BaseController;
import com.ccnet.core.dao.base.Page;
import com.ccnet.cps.entity.SbContentInfo;
import com.ccnet.jpz.entity.JpUserCollect;
import com.ccnet.jpz.entity.JpUserComment;
import com.ccnet.jpz.service.JpUserCollectService;

@Controller
@RequestMapping("/api/userCollect/")
public class JpUserCollectController extends BaseController<JpUserCollect> {

	@Autowired
	private JpUserCollectService jpUserCollectService;
	@Autowired
	private ApiUserCommentService apiUserCommentService;
	@Autowired
	private ApiContentService apiContentService;

	/*
	 * 文章收藏点赞阅读列表
	 */
	@ResponseBody
	@RequestMapping("collectList")
	public ResultDTO<?> collectList(Headers header, JpUserCollect jpUserCollect, Page<JpUserCollect> arg1) {
		try {
			if (CPSUtil.isEmpty(jpUserCollect.getContentId())) {
				return ResultDTO.ERROR(BasicCode.参数错误);
			}
			if (CPSUtil.isEmpty(header.getUserid())) {
				return ResultDTO.ERROR(BasicCode.参数错误);
			}
			List<SbContentInfo> list = apiContentService.findTypeByPage(jpUserCollect, arg1);
			return ResultDTO.OK(list);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDTO.ERROR(BasicCode.系统繁忙);
		}
	}

	/*
	 * 文章收藏点赞、取消收藏点赞
	 */
	@ResponseBody
	@RequestMapping("addOrDel")
	public ResultDTO<?> collectAdd(Headers header, JpUserCollect jpUserCollect) {
		try {
			//type 1-收藏 2-点赞 3-阅读
			ResultDTO<?> dto = params(jpUserCollect, header);
			if (dto.ok()) {
				jpUserCollect.setUserId(Integer.valueOf(header.getUserid()));
				JpUserCollect collect = jpUserCollectService.find(jpUserCollect);
				if (collect != null && collect.getId() != null) {
					jpUserCollectService.delete(collect);
					apiContentService.updateContentInfo(jpUserCollect.getContentId(), jpUserCollect.getType(), "1");
				} else {
					jpUserCollect.setCreateDate(new Date());
					jpUserCollectService.insert(jpUserCollect);
					apiContentService.updateContentInfo(jpUserCollect.getContentId(), jpUserCollect.getType(), "0");
				}
				return ResultDTO.OK();
			}
			return dto;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDTO.ERROR(BasicCode.系统繁忙);
		}
	}

	private ResultDTO<?> params(JpUserCollect jpUserCollect, Headers header) {
		if (CPSUtil.isEmpty(jpUserCollect.getContentId())) {
			return ResultDTO.ERROR(BasicCode.参数错误);
		}
		if (CPSUtil.isEmpty(jpUserCollect.getType())) {
			return ResultDTO.ERROR(BasicCode.参数错误);
		}
		if (CPSUtil.isEmpty(header.getUserid())) {
			return ResultDTO.ERROR(BasicCode.参数错误);
		}
		return ResultDTO.OK();
	}

	/**
	 * 我的阅读
	 * 
	 * @param headers
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping("myReadList")
	public ResultDTO<?> myReadList(Headers headers, Page<T> page) {
		try {
			if (CPSUtil.isEmpty(headers.getUserid())) {
				return ResultDTO.ERROR(AppResultCode.用户不存在);
			}
			JpUserCollect jpUserCollect = new JpUserCollect();
			jpUserCollect.setUserId(Integer.valueOf(headers.getUserid()));
			jpUserCollect.setType("3");
			List<SbContentInfo> list = apiUserCommentService.findJpUserCollectList(jpUserCollect, page);
			for (int i = 0; i < list.size(); i++) {
				SbContentInfo jpUserCollect1 = list.get(i);
				if (CPSUtil.isNotEmpty(jpUserCollect.getImages())) {
					String[] str = jpUserCollect.getImages().split(",");
					String images = "";
					for (int z = 0; z < str.length; z++) {
						if (z == 0) {
							images = UrlReplaceUtils.urlStr(str[z]) + ",";
						} else {
							images = UrlReplaceUtils.urlStr(str[z]);
						}
						jpUserCollect1.setContentPics(images);
					}
				}
			}
			return ResultDTO.OK(list);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDTO.ERROR(BasicCode.系统繁忙);
		}
	}

	/**
	 * 我的收藏
	 * 
	 * @param headers
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping("myCollectList")
	public ResultDTO<?> myCollectList(Headers headers, Page<T> page) {
		try {
			if (CPSUtil.isEmpty(headers.getUserid())) {
				return ResultDTO.ERROR(AppResultCode.用户不存在);
			}
			JpUserCollect jpUserCollect = new JpUserCollect();
			jpUserCollect.setUserId(Integer.valueOf(headers.getUserid()));
			jpUserCollect.setType("1");
			List<SbContentInfo> list = apiUserCommentService.findJpUserCollectList(jpUserCollect, page);
			for (int i = 0; i < list.size(); i++) {
				SbContentInfo jpUserCollect1 = list.get(i);
				if (CPSUtil.isNotEmpty(jpUserCollect.getImages())) {
					String[] str = jpUserCollect.getImages().split(",");
					String images = "";
					for (int z = 0; z < str.length; z++) {
						if (z == 0) {
							images = UrlReplaceUtils.urlStr(str[z]) + ",";
						} else {
							images = UrlReplaceUtils.urlStr(str[z]);
						}
						jpUserCollect1.setContentPics(images);
					}
				}
			}
			return ResultDTO.OK(list);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDTO.ERROR(BasicCode.系统繁忙);
		}
	}

	/**
	 * 我的点赞
	 * 
	 * @param headers
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping("myLikeList")
	public ResultDTO<?> myLikeList(Headers headers, Page<T> page) {
		try {
			if (CPSUtil.isEmpty(headers.getUserid())) {
				return ResultDTO.ERROR(AppResultCode.用户不存在);
			}
			JpUserComment jpUserCollect = new JpUserComment();
			jpUserCollect.setUserId(Integer.valueOf(headers.getUserid()));
			jpUserCollect.setContentType("2");
			List<JpUserComment> list = apiUserCommentService.findJpUserLikeList(jpUserCollect, page);
			for (int i = 0; i < list.size(); i++) {
				jpUserCollect = list.get(i);
				if (CPSUtil.isNotEmpty(jpUserCollect.getImages())) {
					String[] str = jpUserCollect.getImages().split(",");
					String images = "";
					for (int z = 0; z < str.length; z++) {
						if (z == 0) {
							images = UrlReplaceUtils.urlStr(str[z]) + ",";
						} else {
							images = UrlReplaceUtils.urlStr(str[z]);
						}
						jpUserCollect.setImages(images);
					}
				}
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("contentList", list);
			return ResultDTO.OK(map);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDTO.ERROR(BasicCode.系统繁忙);
		}
	}

	/**
	 * 我的点赞
	 * 
	 * @param headers
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping("myCommentList")
	public ResultDTO<?> myCommentList(Headers headers, Page<T> page) {
		try {
			if (CPSUtil.isEmpty(headers.getUserid())) {
				return ResultDTO.ERROR(AppResultCode.用户不存在);
			}
			Map<String, Object> map = new HashMap<String, Object>();
			JpUserComment jpUserComment = new JpUserComment();
			jpUserComment.setUserId(Integer.valueOf(headers.getUserid()));
			List<JpUserComment> list = apiUserCommentService.getMyCommentList(jpUserComment, page);
			for (int i = 0; i < list.size(); i++) {
				jpUserComment = list.get(i);
				if (CPSUtil.isNotEmpty(jpUserComment.getImages())) {
					String[] str = jpUserComment.getImages().split(",");
					String images = "";
					for (int z = 0; z < str.length; z++) {
						if (z == 0) {
							images = UrlReplaceUtils.urlStr(str[z]) + ",";
						} else {
							images = UrlReplaceUtils.urlStr(str[z]);
						}
						jpUserComment.setImages(images);
					}
				}
				if (CPSUtil.isEmpty(jpUserComment.getToUserName())) {
					jpUserComment.setToUserName("super");
				}
			}
			map.put("contentList", list);
			return ResultDTO.OK(map);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDTO.ERROR(BasicCode.系统繁忙);
		}
	}
}
