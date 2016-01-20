package com.ai.wocloud.bean;

public class OsTemplate {
	private String id ;
	private String name;
	private String displaytext;
	private String ostypeid;
	private String ostypename;
	private String system;
	

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
	public String getDisplaytext() {
		return displaytext;
	}
	public void setDisplaytext(String displaytext) {
		this.displaytext = displaytext;
	}
	public String getOstypeid() {
		return ostypeid;
	}
	public void setOstypeid(String ostypeid) {
		this.ostypeid = ostypeid;
	}
	public String getOstypename() {
		return ostypename;
	}
	public void setOstypename(String ostypename) {
		this.ostypename = ostypename;
	}
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
	
	@Override  
	public boolean equals(Object obj) {  
	OsTemplate o=(OsTemplate)obj;   
	return id.equals(o.id) && name.equals(o.name) && ostypeid.equals(o.ostypeid) && ostypename.equals(ostypename) && system.equals(system);   
	}  
	@Override  
	public int hashCode() {  
	String in = id + name + ostypeid + ostypename + system;  
	return in.hashCode();  
	}  
}
