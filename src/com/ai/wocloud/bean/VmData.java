package com.ai.wocloud.bean;

public class VmData {
	private String desc;
	private String vmPrice;
	private String name;
	private String value;
	private String osDisk;
	private String productId;  //虚拟机id
	private String cpuValue;
	private String productType ; //列表中的产品类型列
	private String productEtalon;//列表中的产品规格列
	private String monthPrices;//列表中的月价格列
	private String yearPrices;//列表中的年价格
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getProductEtalon() {
		return productEtalon;
	}
	public void setProductEtalon(String productEtalon) {
		this.productEtalon = productEtalon;
	}
	public String getMonthPrices() {
		return monthPrices;
	}
	public void setMonthPrices(String monthPrices) {
		this.monthPrices = monthPrices;
	}
	public String getYearPrices() {
		return yearPrices;
	}
	public void setYearPrices(String yearPrices) {
		this.yearPrices = yearPrices;
	}
	
	
	public String getCpuValue() {
		return cpuValue;
	}
	public void setCpuValue(String cpuValue) {
		this.cpuValue = cpuValue;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getVmPrice() {
		return vmPrice;
	}
	public void setVmPrice(String vmPrice) {
		this.vmPrice = vmPrice;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getOsDisk() {
		return osDisk;
	}
	public void setOsDisk(String osDisk) {
		this.osDisk = osDisk;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	
	
}
