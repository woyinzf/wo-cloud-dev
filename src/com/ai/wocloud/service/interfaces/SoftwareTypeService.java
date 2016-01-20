package com.ai.wocloud.service.interfaces;

import java.util.List;

import com.ai.wocloud.bean.SoftwareType;

public interface SoftwareTypeService {
	public abstract List<SoftwareType> findAllSoftwareType();
	public abstract SoftwareType findOneSoftwareType(String id);
	
	public abstract List<SoftwareType> findSoftwareTypeByParent(String father_node); 
	
}
