package com.ccnet.jpz.service;

import java.util.List;

import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.base.BaseService;
import com.ccnet.jpz.entity.JpUserComment;

public interface JpUserCommentService extends BaseService<JpUserComment> {
	/**
	 * 分页查询(多过滤)
	 * 
	 * @param Info
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<JpUserComment> findByPage(JpUserComment table, Page<JpUserComment> page, Dto pdDto);

	/**
	 * 根据ID获取
	 * 
	 * @param Ids
	 * @return
	 */
	public List<JpUserComment> getListByIds(List<Integer> Ids);

	/**
	 * 获取单个
	 * 
	 * @param Id
	 * @return
	 */
	public JpUserComment getByID(Integer Id);

	/**
	 * 批量删除里面
	 * 
	 * @param list
	 * @return
	 */
	public boolean trashList(List<JpUserComment> list);

	public boolean pushPingbi(String ids, String pingbi);

	public List<JpUserComment> findCommentByContentIdPage(Dto pdDto);

	public boolean pushPingbiContentId(String ids, String pingbi);
}
