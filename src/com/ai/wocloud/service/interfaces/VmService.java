package com.ai.wocloud.service.interfaces;

import java.util.List;

import com.ai.wocloud.bean.VmInfo;

public interface VmService {
	public abstract List<VmInfo> findAllVmInfo();
}
