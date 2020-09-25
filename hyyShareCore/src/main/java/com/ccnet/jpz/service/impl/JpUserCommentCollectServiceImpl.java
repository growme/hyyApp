package com.ccnet.jpz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.service.impl.BaseServiceImpl;
import com.ccnet.jpz.dao.JpUserCommentCollectDao;
import com.ccnet.jpz.entity.JpUserCommentCollect;
import com.ccnet.jpz.service.JpUserCommentCollectService;

@Service("jpUserCommentCollectService")
public class JpUserCommentCollectServiceImpl extends BaseServiceImpl<JpUserCommentCollect>
		implements JpUserCommentCollectService {

	@Autowired
	private JpUserCommentCollectDao jpUserCommentCollectDao;

	@Override
	protected BaseDao<JpUserCommentCollect> getDao() {
		return jpUserCommentCollectDao;
	}

}
