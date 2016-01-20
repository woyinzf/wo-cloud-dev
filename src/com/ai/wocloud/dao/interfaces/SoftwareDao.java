package com.ai.wocloud.dao.interfaces;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.ai.wocloud.bean.ApplyBasic;
import com.ai.wocloud.bean.ApplyInfo;
import com.ai.wocloud.bean.Software;

public interface SoftwareDao {
	
	/**
	 * 插入一条记录
	 * 
	 * @param software
	 */
	public abstract void insertSoftware(Software software);
	
	/**
	 * 插入一条记录
	 * 
	 * @param software
	 */
	public abstract void insertSoftwareHis(Software software);

	/**
	 * 更新一条记录
	 * @param software
	 */
	
	public abstract void updateSoftware(Software software);
	/**
	 * 更新一条记录
	 * @param software
	 */
	
	public abstract void updateSoftware(int id);

	/**
	 * 删除一条数据
	 * @param id
	 */
	public abstract void deleteSoftware(Integer id);

	/**
	 * 通过id查找一条数据
	 * @param id
	 * @return Software
	 */
	public abstract Software findSoftwareByid(Integer id);
	
	

	/**
	 * 通过id查找一条数据
	 * @param id
	 * @return Software
	 */
	public abstract Software findSoftwareByid(String softwareId);
	/**
	 * 查找全部数据
	 * @param userId 
	 * @return list
	 * 
	 */
	public abstract List<Software> findAll(String userId);
	
	/**
	 * 通过软件名称查找记录
	 * @return list
	 */
	public abstract List<Software> findSoftwareBySoftwareName(String softwareName);
	
	/**
	 * 通过软件类型查找记录
	 * @return list
	 */
	public abstract List<Software> findSoftwareBySoftwareType(String softwareType);
	
	/**
	 * 通过软件版本查找数据
	 * @return list
	 */
	public abstract List<Software> findSoftwareBySoftwareVersions(String softwareVersions);
	
	/**
	 * 通过提交时间查找数据
	 * @return list
	 */
	public abstract List<Software> findSoftwareBySoftewareCommandTime(Timestamp commandTime);
	
	/**
	 * 通过关键是查找数据
	 * @return list
	 */
	public abstract List<Software> findSoftwareByKeywords(String softwareKeywords);
	
	/**
	 * 分页查询
	 * @param startRow
	 * @param endRow
	 * @param orderType 
	 * @param orderCond 
	 * @param condValue 
	 * @return
	 */
	public abstract List<Software> pageSelect(int startRow,int endRow,String userId, String orderCond, String orderType, String condValue);
	
	/**
	 * 数据总数
	 * @param condValue 
	 * @return
	 */
	public abstract int selectTotalRecord(String userId, String condValue);

	public abstract void softDown(Software software);

	public abstract int sumMyApply(Map<?, ?> paramMap);

	public abstract List<ApplyInfo> applySelect(Map<?, ?> paramMap);

	public abstract void softSign(Software software);

	public abstract ApplyBasic applyDetail(String applyId);

	public abstract Software findSoftwareByid2(int softId);

	public abstract int queryHistoryId(String softwareId);

	public abstract int getUpSoftNum(String userId);

	public abstract void softwareReset(int selectId);

	public abstract String getCustId(String userName);


	
}
