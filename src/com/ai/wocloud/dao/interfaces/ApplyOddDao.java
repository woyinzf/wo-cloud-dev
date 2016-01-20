package com.ai.wocloud.dao.interfaces;

import com.ai.wocloud.bean.ApplyOdd;

public interface ApplyOddDao {
	
	/**
	 * 插入一条记录
	 * @param applyOdd
	 */
	public abstract void insertApplyOdd(ApplyOdd applyOdd);
	
	/**
	 * deleteApplyOdd: <br/>
	 * 删除一条记录<br/>
	 *
	 * @author zhangyichi
	 * @param id
	 * @since JDK 1.6
	 */
	public abstract void deleteApplyOdd(int id);
}
