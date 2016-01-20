package com.ai.wocloud.dao.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ai.wocloud.bean.VmInfo;
import com.ai.wocloud.dao.interfaces.VmDao;


@Repository
public class VmDaoImpl implements VmDao {

	private final String FINDALLVMINFO ="findallvminfo";

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	@Override
	public List<VmInfo> findAllVmInfo() {
		return sqlSessionTemplate.selectList(FINDALLVMINFO);
	}

}
