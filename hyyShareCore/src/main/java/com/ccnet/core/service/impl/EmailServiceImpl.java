package com.ccnet.core.service.impl;

import org.springframework.stereotype.Service;

import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.entity.Email;
import com.ccnet.core.service.EmailService;


@Service("emailService")
public class EmailServiceImpl extends BaseServiceImpl<Email> implements EmailService {
 
	@Override
	protected BaseDao<Email> getDao() {
		return null;
	}
 
}
