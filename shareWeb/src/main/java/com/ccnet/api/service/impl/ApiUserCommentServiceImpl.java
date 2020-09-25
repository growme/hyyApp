package com.ccnet.api.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccnet.api.dao.ApiUserCommentDao;
import com.ccnet.api.enums.HandleType;
import com.ccnet.api.service.ApiUserCommentService;
import com.ccnet.core.dao.base.Page;
import com.ccnet.cps.entity.SbContentInfo;
import com.ccnet.jpz.dao.JpUserCommentCollectDao;
import com.ccnet.jpz.entity.JpUserCollect;
import com.ccnet.jpz.entity.JpUserComment;
import com.ccnet.jpz.entity.JpUserCommentCollect;

@Service("apiUserCommentService")
public class ApiUserCommentServiceImpl implements ApiUserCommentService {

	@Autowired
	private ApiUserCommentDao apiUserCommentDao;
	@Autowired
	private JpUserCommentCollectDao jpUserCommentCollectDao;

	@Override
	public List<JpUserComment> findUserCommentList(JpUserComment jpUserComment, Page<T> page) {
		return apiUserCommentDao.findUserCommentList(jpUserComment, page);
	}

	@Override
	public int insertOrDeleteCollect(JpUserCommentCollect collect) {
		if (jpUserCommentCollectDao.find(collect) != null) {
			apiUserCommentDao.updateCommentInfo(collect.getCommentId(), HandleType.click.getid(), "1");
			return jpUserCommentCollectDao.delete(collect);
		} else {
			apiUserCommentDao.updateCommentInfo(collect.getCommentId(), HandleType.click.getid(), "0");
			collect.setCreateDate(new Date());
			return jpUserCommentCollectDao.insert(collect);
		}
	}

	@Override
	public List<JpUserCommentCollect> findUserCommentCollectList(JpUserCommentCollect table, Page<T> page) {
		return jpUserCommentCollectDao.findUserCommentCollectList(table, page);
	}

	@Override
	public int updateCommentInfo(Integer id, String type, String code) {
		return apiUserCommentDao.updateCommentInfo(id, type, code);
	}

	@Override
	public List<JpUserComment> findFirstUserCommentList(JpUserComment jpUserComment, Page<T> page) {
		return apiUserCommentDao.findFirstUserCommentList(jpUserComment, page);
	}

	@Override
	public List<SbContentInfo> findJpUserCollectList(JpUserCollect jpUserCollect, Page<T> page) {
		return apiUserCommentDao.findJpUserCollectList(jpUserCollect, page);
	}

	@Override
	public List<JpUserComment> getMyCommentList(JpUserComment jpUserComment, Page<T> page) {
		return apiUserCommentDao.getMyCommentList(jpUserComment, page);
	}

	@Override
	public List<JpUserComment> findJpUserLikeList(JpUserComment jpUserCollect, Page<T> page) {
		return apiUserCommentDao.findJpUserLikeList(jpUserCollect, page);
	}

}
