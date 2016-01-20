package com.ai.wocloud.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ai.wocloud.bean.ApplyOdd;
import com.ai.wocloud.dao.interfaces.ApplyOddDao;


@Repository
public class ApplyOddDaoImpl implements ApplyOddDao {
	
	private final String ADDAPPLYODD ="addapplyodd";
	private final String DELETEAPPLYODD = "deleteApplyOdd";

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	

	@Override
	public void insertApplyOdd(ApplyOdd applyOdd) {
		sqlSessionTemplate.insert(ADDAPPLYODD,applyOdd);
	}


    @Override
    public void deleteApplyOdd(int id) {
        sqlSessionTemplate.delete(DELETEAPPLYODD, id);
    }

}
