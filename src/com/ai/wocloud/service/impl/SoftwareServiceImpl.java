package com.ai.wocloud.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.wocloud.bean.ApplyBasic;
import com.ai.wocloud.bean.ApplyInfo;
import com.ai.wocloud.bean.Software;
import com.ai.wocloud.dao.interfaces.SoftwareDao;
import com.ai.wocloud.service.interfaces.SoftwareService;
import com.ai.wocloud.system.util.PageModel;


@Service
@Transactional
public class SoftwareServiceImpl implements SoftwareService {

	@Autowired
	private SoftwareDao softwareDao;
	
	
	
	public void setSoftwareDao(SoftwareDao softwareDao) {
		this.softwareDao = softwareDao;
	}

	@Override
	public void insertSoftware(Software software) {
		softwareDao.insertSoftware(software);
	}

	@Override
	public void updateSoftware(Software software) {
		softwareDao.updateSoftware(software);
	}

	@Override
	public void deleteSoftware(Integer id) {
		softwareDao.deleteSoftware(id);
	}

	@Override
	public Software findSoftwareByid(Integer id) {
		return softwareDao.findSoftwareByid(id);
	}

	@Override
	public List<Software> findAll(String userId) {
		return softwareDao.findAll(userId);
	}

	@Override
	public List<Software> findSoftwareBySoftwareName(String softwareName) {
		return softwareDao.findSoftwareBySoftwareName(softwareName);
	}

	@Override
	public List<Software> findSoftwareBySoftwareType(String softwareType) {
		return softwareDao.findSoftwareBySoftwareType(softwareType);
	}

	@Override
	public List<Software> findSoftwareBySoftwareVersions(String softwareVersions) {
		return softwareDao.findSoftwareBySoftwareVersions(softwareVersions);
	}

	@Override
	public List<Software> findSoftwareBySoftewareCommandTime(
			Timestamp commandTime) {
		return softwareDao.findSoftwareBySoftewareCommandTime(commandTime);
	}

	@Override
	public List<Software> findSoftwareByKeywords(String softwareKeywords) {
		return softwareDao.findSoftwareByKeywords(softwareKeywords);
	}

	@SuppressWarnings("rawtypes")
    @Override
	public List<Software> pageSelect(PageModel pageModel,String userId,String orderCond, String orderType,String condValue) {
		return softwareDao.pageSelect(pageModel.getStartRow(), pageModel.getEndRow(),userId,orderCond,orderType,condValue);
	}

	@Override
	public int selectTotalRecord(String userId,String condValue) {
		return softwareDao.selectTotalRecord(userId,condValue);
	}

	@Override
	public Software findSoftwareByid(String id) {
		return softwareDao.findSoftwareByid(id);
	}

	@Override
	public void updateSoftware(int id) {
		softwareDao.updateSoftware(id);
	}

	@Override
	public void softDown(Software software) {
		softwareDao.softDown(software);
	}
	@SuppressWarnings("rawtypes")
    @Override
	public int sumMyApply(Map paramMap) {
		return softwareDao.sumMyApply(paramMap);
	}

	@SuppressWarnings("rawtypes")
    @Override
	public List<ApplyInfo> applySelect(Map paramMap) {
		return softwareDao.applySelect(paramMap);
	}

	@Override
	public void softSign(Software software) {
		softwareDao.softSign(software);
	}

	@Override
	public ApplyBasic applyDetail(String applyId) {
		return softwareDao.applyDetail(applyId);
	}

	@Override
	public void insertSoftwareHis(Software software) {
		softwareDao.insertSoftwareHis(software);
	}

	@Override
	public Software findSoftwareByid2(int softId) {
		return softwareDao.findSoftwareByid2(softId);
	}

	@Override
	public int queryHistoryId(String softwareId) {
		return softwareDao.queryHistoryId(softwareId);
	}

	@Override
	public void softwareReset(int selectId) {
		softwareDao.softwareReset(selectId);
	}

	@Override
	public String getCustId(String loginName) {
		return softwareDao.getCustId(loginName);
	}


	
}
