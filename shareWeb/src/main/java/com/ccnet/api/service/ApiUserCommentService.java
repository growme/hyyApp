package com.ccnet.api.service;

import java.util.List;

import org.apache.poi.ss.formula.functions.T;

import com.ccnet.core.dao.base.Page;
import com.ccnet.cps.entity.SbContentInfo;
import com.ccnet.jpz.entity.JpUserCollect;
import com.ccnet.jpz.entity.JpUserComment;
import com.ccnet.jpz.entity.JpUserCommentCollect;

public interface ApiUserCommentService {

	public List<JpUserComment> findUserCommentList(JpUserComment jpUserComment, Page<T> page);

	public int insertOrDeleteCollect(JpUserCommentCollect collect);

	public List<JpUserCommentCollect> findUserCommentCollectList(JpUserCommentCollect table, Page<T> page);

	public int updateCommentInfo(Integer id, String type, String code);

	public List<JpUserComment> findFirstUserCommentList(JpUserComment jpUserComment, Page<T> page);

	public List<SbContentInfo> findJpUserCollectList(JpUserCollect jpUserCollect, Page<T> page);

	public List<JpUserComment> getMyCommentList(JpUserComment jpUserComment, Page<T> page);

	public List<JpUserComment> findJpUserLikeList(JpUserComment jpUserCollect, Page<T> page);

}
