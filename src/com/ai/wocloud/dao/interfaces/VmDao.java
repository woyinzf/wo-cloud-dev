package com.ai.wocloud.dao.interfaces;

import java.util.List;

import com.ai.wocloud.bean.VmInfo;

public interface VmDao {
	public abstract List<VmInfo> findAllVmInfo();
}
