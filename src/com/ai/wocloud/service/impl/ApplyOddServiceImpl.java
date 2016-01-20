package com.ai.wocloud.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.wocloud.bean.ApplyOdd;
import com.ai.wocloud.dao.interfaces.ApplyOddDao;
import com.ai.wocloud.service.interfaces.ApplyOddService;


@Service
@Transactional
public class ApplyOddServiceImpl implements ApplyOddService {

	@Autowired
	private ApplyOddDao applyOddDao;
	@Override
	public void insertApplyOdd(ApplyOdd applyOdd) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		applyOdd.setApplyOddTime((String)df.format(new Date()));
		applyOddDao.insertApplyOdd(applyOdd);
	}
	
    @Override
    public void deleteApplyOdd(int id) {
        applyOddDao.deleteApplyOdd(id);
    }

}
