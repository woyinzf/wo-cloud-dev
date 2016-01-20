package com.ai.wocloud.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.wocloud.bean.VmInfo;
import com.ai.wocloud.dao.interfaces.VmDao;
import com.ai.wocloud.service.interfaces.VmService;


@Service
@Transactional
public class VmInfoServiceImpl implements VmService {

	
	@Autowired
	private VmDao vmDao;
	@Override
	public List<VmInfo> findAllVmInfo() {
		// TODO Auto-generated method stub
		return vmDao.findAllVmInfo();
	}

}
