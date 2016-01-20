package com.ai.wocloud.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ai.wocloud.bean.ApplyBasic;
import com.ai.wocloud.bean.ApplyInfo;
import com.ai.wocloud.bean.Software;
import com.ai.wocloud.dao.interfaces.SoftwareDao;




@Repository
public class SoftwareDaoImpl implements SoftwareDao {

	private final String INSERTSOFTWARE="insertSoftware";
	private final String INSERTSOFTWAREHIS="insertSoftwareHis";
	private final String UPDATESOFTWARE="updateSoftware";
	private final String UPDATESOFTWAREBYID="updateSoftwareByid";
	private final String DELETESOFTWARE="deleteSoftware";
	private final String FINDSOFTWAREBYID="findSoftwareByid";
	private final String FINDSOFTWAREBYSOFTWAREID="findSoftwareBysoftwareId";
	private final String FINDALLSOFTWARE="findAllSoftware";
	private final String FINDSOFTWAREBYSOFTWARENAME="findSoftwareBySoftwareName";
	
	private final String FINDSOFTWAREBYSOFTWARETYPE="findSoftwareBySoftwareType";
	
	private final String FINDSOFTWAREBYSOFTWAREVERSIONS="findSoftwareBySoftwareVersions";
	
	private final String FINDSOFTWAREBYSOFTWARECOMMANDTIME="findSoftwareBySoftewareCommandTime";
	
	private final String FINDSOFTWAREBYKEYWORDS="findSoftwareByKeywords";
	
	private final String FINDSOFTWAREBYSPLITPAGE ="findsoftwarebysplitpage";
	
	private final String SOFTDOWN = "softDown";
	private final String QUERYHISTORYID = "queryHistoryId";
	private final String SUMMYAPPLY="sumMyApply";
	private final String FINDMYAPPLY="findMyApply";
	private final String SOFTSIGN = "softSign";
	private final String APPLYBASIC="applyBasic";
	private final String FINDSOFTWAREBYID2="findSoftwareByid2";
	private final String UP_SOFT_NUM="upSoftNum";
	private final String RESET_SOFTWARE="resetSoftware";
	private final String GET_CUST_ID="getCustId";
	
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public void insertSoftware(Software software) {
		sqlSessionTemplate.insert(INSERTSOFTWARE,software);
	}

	@Override
	public void updateSoftware(Software software) {
		sqlSessionTemplate.update(UPDATESOFTWARE,software);
	}

	@Override
	public void deleteSoftware(Integer id) {
		sqlSessionTemplate.delete(DELETESOFTWARE,id);
	}

	@Override
	public Software findSoftwareByid(Integer id) {
		return sqlSessionTemplate.selectOne(FINDSOFTWAREBYID,id);
	}

	@Override
	public List<Software> findAll(String userId) {
		return sqlSessionTemplate.selectList(FINDALLSOFTWARE,userId);
	}

	@Override
	public List<Software> findSoftwareBySoftwareName(String softwareName) {
		return sqlSessionTemplate.selectList(FINDSOFTWAREBYSOFTWARENAME,softwareName);
	}

	@Override
	public List<Software> findSoftwareBySoftwareType(String softwareType) {
		return sqlSessionTemplate.selectList(FINDSOFTWAREBYSOFTWARETYPE,softwareType);
	}

	@Override
	public List<Software> findSoftwareBySoftwareVersions(String softwareVersions) {
		return sqlSessionTemplate.selectList(FINDSOFTWAREBYSOFTWAREVERSIONS,softwareVersions);
	}

	@Override
	public List<Software> findSoftwareBySoftewareCommandTime(
			Timestamp commandTime) {
		return sqlSessionTemplate.selectList(FINDSOFTWAREBYSOFTWARECOMMANDTIME,commandTime);
	}

	@Override
	public List<Software> findSoftwareByKeywords(String softwareKeywords) {
		return sqlSessionTemplate.selectList(FINDSOFTWAREBYKEYWORDS,softwareKeywords);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
	public List<Software> pageSelect(int startRow, int endRow,String userId,String orderCond, String orderType,String condValue) {
		int rowCount = endRow - startRow;
		List<Software> softwareList = new ArrayList<Software>();
		Map params =new HashMap();
		params.put("rowCount", rowCount);
		params.put("startRow", startRow);
		params.put("userId", userId);
		params.put("orderCond", orderCond);
		params.put("orderType", orderType);
		params.put("condValue", condValue);
		
		softwareList = sqlSessionTemplate.selectList(FINDSOFTWAREBYSPLITPAGE,params);

		return softwareList;
	}

	@Override
	public int selectTotalRecord(String userId,String condValue) {
		Map<String, String> params =new HashMap<String, String>();
		params.put("userId", userId);
		params.put("condValue", condValue);
		return sqlSessionTemplate.selectList(FINDALLSOFTWARE,params).size();
	}

	@Override
	public Software findSoftwareByid(String softwareId) {
		return sqlSessionTemplate.selectOne(FINDSOFTWAREBYSOFTWAREID,softwareId);
	}

	@Override
	public void updateSoftware(int id) {
		sqlSessionTemplate.selectOne(UPDATESOFTWAREBYID,id);
		
	}

	@Override
	public void softDown(Software software) {
		sqlSessionTemplate.update(SOFTDOWN, software);
	}
	@SuppressWarnings("rawtypes")
    @Override
	public int sumMyApply(Map paramMap) {
		return sqlSessionTemplate.selectList(SUMMYAPPLY,paramMap).size();
	}

	@SuppressWarnings("rawtypes")
    @Override
	public List<ApplyInfo> applySelect(Map paramMap) {
		List<ApplyInfo> softwareList = new ArrayList<ApplyInfo>();		

		softwareList = sqlSessionTemplate.selectList(FINDMYAPPLY,paramMap);

		return softwareList;
	}

	@Override
	public void softSign(Software software) {
		sqlSessionTemplate.update(SOFTSIGN, software);
	}

	@Override
	public ApplyBasic applyDetail(String applyId) {
		return sqlSessionTemplate.selectOne(APPLYBASIC,applyId);
	}

	@Override
	public void insertSoftwareHis(Software software) {
		sqlSessionTemplate.insert(INSERTSOFTWAREHIS,software);
	}

	@Override
	public Software findSoftwareByid2(int softId) {
		return sqlSessionTemplate.selectOne(FINDSOFTWAREBYID2,softId);
	}

	@Override
	public int queryHistoryId(String softwareId) {
		return sqlSessionTemplate.selectOne(QUERYHISTORYID, softwareId);
	}
	
	public int getUpSoftNum(String userId){
		return sqlSessionTemplate.selectList(UP_SOFT_NUM, userId).size();
	}

	@Override
	public void softwareReset(int selectId) {
		sqlSessionTemplate.update(RESET_SOFTWARE, selectId);
	}

	@Override
	public String getCustId(String userName) {
		return sqlSessionTemplate.selectOne(GET_CUST_ID, userName);		
	}

}
