package com.ai.wocloud.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.wocloud.service.interfaces.AccountService;
import com.ai.wocloud.dao.interfaces.SoftwareDao;


@Service
@Transactional
public class AccountServiceImpl implements AccountService {
	@Autowired
	private SoftwareDao softwareDao;
	
	@Override
	public int getUpSoftNum(String userId) {
		return softwareDao.getUpSoftNum(userId);
	}

}
