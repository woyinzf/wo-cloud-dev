package com.ai.wocloud.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.wocloud.bean.SoftwareType;
import com.ai.wocloud.dao.interfaces.SoftwareTypeDao;
import com.ai.wocloud.service.interfaces.SoftwareTypeService;


@Service
@Transactional
public class SoftwareTypeServiceImpl implements SoftwareTypeService {

	
	@Autowired
	private SoftwareTypeDao softwareTypeDao;
	
	public SoftwareTypeDao getSoftwareTypeDao() {
		return softwareTypeDao;
	}

	public void setSoftwareTypeDao(SoftwareTypeDao softwareTypeDao) {
		this.softwareTypeDao = softwareTypeDao;
	}

	@Override
	public List<SoftwareType> findAllSoftwareType() {
		// TODO Auto-generated method stub
		return softwareTypeDao.findAllSoftwareType();
	}

	@Override
	public SoftwareType findOneSoftwareType(String id) {
		// TODO Auto-generated method stub
		return softwareTypeDao.findOneSoftwareType(id);
	}

	/* (non-Javadoc)
	 * @see com.ai.wocloud.service.interfaces.SoftwareTypeService#findSoftwareTypeByParent()
	 */
	@Override
	public List<SoftwareType> findSoftwareTypeByParent(String father_node) {
		// TODO Auto-generated method stub
		return softwareTypeDao.findSoftwareTypeByParent(father_node);
	}

}
