package com.ai.wocloud.dao.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ai.wocloud.bean.SoftwareType;
import com.ai.wocloud.dao.interfaces.SoftwareTypeDao;


@Repository
public class SoftwareTypeDaoImpl implements SoftwareTypeDao {

	private final String FINDALLSOFTWARETYPE ="findallsoftwaretype";
	private final String FINDONESOFTWARETYPE ="findonesoftwaretype";
	private final String FINDBYFATHER="findsoftwaretypeByfather";

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	@Override
	public List<SoftwareType> findAllSoftwareType() {
		return sqlSessionTemplate.selectList(FINDALLSOFTWARETYPE);
	}
	public SqlSessionTemplate getSqlSessionTemplate() {
		return sqlSessionTemplate;
	}
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	@Override
	public SoftwareType findOneSoftwareType(String id) {
		return sqlSessionTemplate.selectOne(FINDONESOFTWARETYPE,id);
	}
	/* (non-Javadoc)
	 * @see com.ai.wocloud.dao.interfaces.SoftwareTypeDao#findSoftwareTypeByParent()
	 */
	@Override
	public List<SoftwareType> findSoftwareTypeByParent(String father_node) {
		return sqlSessionTemplate.selectList(FINDBYFATHER,father_node);
	}

}
