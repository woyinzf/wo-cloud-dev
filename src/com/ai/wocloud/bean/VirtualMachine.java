/**
@Title: VirtualMachine.java 
* @Package com.ai.wocloud.bean 
* @Description: TODO(用一句话描述该文件做什么) 
* @author zbc 
* @date 2014年11月24日 下午5:45:39 
* @version V1.0 
**/
package com.ai.wocloud.bean;

/**
 * @author zhangbc
 *
 */
public class VirtualMachine {
	
	
	private String id;
	
	private String name;
	
	
	private String cpu;
	
	private String memory;
	
	
	private String disk;


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getCpu() {
		return cpu;
	}


	public void setCpu(String cpu) {
		this.cpu = cpu;
	}


	public String getMemory() {
		return memory;
	}


	public void setMemory(String memory) {
		this.memory = memory;
	}


	public String getDisk() {
		return disk;
	}


	public void setDisk(String disk) {
		this.disk = disk;
	}

}
