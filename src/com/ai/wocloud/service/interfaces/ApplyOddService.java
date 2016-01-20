package com.ai.wocloud.service.interfaces;

import com.ai.wocloud.bean.ApplyOdd;

public interface ApplyOddService {
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
	public void deleteApplyOdd(int id);
}
