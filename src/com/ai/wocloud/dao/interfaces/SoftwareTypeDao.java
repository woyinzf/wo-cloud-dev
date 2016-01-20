package com.ai.wocloud.dao.interfaces;

import java.util.List;

import com.ai.wocloud.bean.SoftwareType;

/**
 * ClassName: SoftwareTypeDao <br/>
 * Function: 软件类型信息 <br/>
 * date: 2014-12-19 10:59:20 <br/>
 *
 * @version 
 * @since JDK 1.6
 */
public interface SoftwareTypeDao {
	/**
	 * findAllSoftwareType: <br/>
	 * 查询所有软件类型<br/>
	 *
	 * @return
	 * @since JDK 1.6
	 */
	public abstract List<SoftwareType> findAllSoftwareType();
	/**
	 * findOneSoftwareType: <br/>
	 * 根据id查询软件类型<br/>
	 *
	 * @param id
	 * @return
	 * @since JDK 1.6
	 */
	public abstract SoftwareType findOneSoftwareType(String id);
	/**
	 * findSoftwareTypeByParent: <br/>
	 * 根据父节点查询软件类型<br/>
	 *
	 * @param father_node
	 * @return
	 * @since JDK 1.6
	 */
	public abstract List<SoftwareType> findSoftwareTypeByParent(String father_node); 
}
