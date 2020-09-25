package com.ccnet.jpz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.impl.BaseServiceImpl;
import com.ccnet.jpz.dao.JpUserCommentDao;
import com.ccnet.jpz.entity.JpUserComment;
import com.ccnet.jpz.service.JpUserCommentService;

@Service("jpUserCommentService")
public class JpUserCommentServiceImpl extends BaseServiceImpl<JpUserComment> implements JpUserCommentService {

	@Autowired
	private JpUserCommentDao JpUserCommentDao;

	@Override
	protected BaseDao<JpUserComment> getDao() {
		return JpUserCommentDao;
	}

	@Override
	public Page<JpUserComment> findByPage(JpUserComment table, Page<JpUserComment> page, Dto pdDto) {
		return JpUserCommentDao.findByPage(table, page, pdDto);
	}

	@Override
	public List<JpUserComment> getListByIds(List<Integer> Ids) {
		return JpUserCommentDao.getListByIds(Ids);
	}

	@Override
	public JpUserComment getByID(Integer Id) {
		return JpUserCommentDao.getByID(Id);
	}

	@Override
	public boolean trashList(List<JpUserComment> list) {
		return JpUserCommentDao.trashList(list);
	}

	@Override
	public boolean pushPingbi(String ids, String pingbi) {
		boolean temp = false;
		try {
			if (CPSUtil.isNotEmpty(ids)) {
				JpUserComment table = null;
				String cids[] = ids.split(",");
				for (String id : cids) {
					table = getByID(Integer.valueOf(id));
					table.setPingbi(pingbi);
					JpUserCommentDao.update(table, "id");
				}
				temp = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}

	@Override
	public boolean pushPingbiContentId(String contentId, String pingbi) {
		boolean temp = false;
		try {
			if (CPSUtil.isNotEmpty(contentId)) {
				JpUserComment table = new JpUserComment();
				table.setContentId(Integer.valueOf(contentId));
				List<JpUserComment> list = JpUserCommentDao.findList(table);
				if (list.size() > 0)
					for (JpUserComment id : list) {
						id.setPingbi(pingbi);
						JpUserCommentDao.update(id, "id");
					}
				temp = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}

	@Override
	public List<JpUserComment> findCommentByContentIdPage(Dto pdDto) {
		return JpUserCommentDao.findCommentByContentIdPage(pdDto);
	}

}
