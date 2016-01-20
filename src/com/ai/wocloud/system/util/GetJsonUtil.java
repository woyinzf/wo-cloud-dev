package com.ai.wocloud.system.util;

import com.ai.wocloud.bean.Software;

//software对象转为json，用于发送运维
public class GetJsonUtil {
	public static String getJson(Object obj){
		Software software = (Software)obj;
		String softwareData = software.getSoftwareData();
		String[] softwareDataAll = softwareData.split("\\|");
		String datas = softwareDataAll[0];
		String systemData = software.getOperateSystem();
		String[] systemDatas = systemData.split(",");
		
		String titleData = softwareDataAll[1]; 
		String service = softwareDataAll[2]; 
		String rowC = softwareDataAll[3]; 
		String colC = softwareDataAll[4];
		int rowCount = Integer.parseInt(rowC);
		int colCount=Integer.parseInt(colC)-1;
		String[] softwareVersionArray = (software.getSoftwareVersions()).split("\\|");
		String[] ghoNameArray = (software.getGhoName()).split("\\|");
		
		//拼接software字段
		StringBuffer softwareDataBuffer = new StringBuffer();
		softwareDataBuffer.append("{\"id\": \"");
		softwareDataBuffer.append(software.getId());
		softwareDataBuffer.append("\",\"provider\": \"");
		softwareDataBuffer.append(software.getSoftwareOffer());
		softwareDataBuffer.append("\",\"software\":{\"type\":\"");
		softwareDataBuffer.append(software.getSoftwareKind());
		softwareDataBuffer.append("\",\"softwareName\":\"");
		softwareDataBuffer.append(software.getSoftwareName());
		softwareDataBuffer.append("\",\"softwareId\":\"");
		softwareDataBuffer.append(software.getId());
		softwareDataBuffer.append("\",\"softwareType\":\"");
		softwareDataBuffer.append(software.getSoftwareType());
		softwareDataBuffer.append("\",\"service\":\"");
		softwareDataBuffer.append(service);
		//version信息
		{
		    softwareDataBuffer.append("\",\"versions\":[");
		    for (int i = 0; i < softwareVersionArray.length; i++) {
		        softwareDataBuffer.append("{\"version\":\"");
		        softwareDataBuffer.append(softwareVersionArray[i]);
		        softwareDataBuffer.append("\"}");
		        if (i != softwareVersionArray.length - 1) {
		            softwareDataBuffer.append(",");
		        }
		    }
		    softwareDataBuffer.append("],");
		}
		//images信息
		{
		    softwareDataBuffer.append("\"images\":[");
		    for (int i = 0; i < ghoNameArray.length; i++) {
		        softwareDataBuffer.append("{\"userid\":\"");
                softwareDataBuffer.append(software.getHandleId());
                softwareDataBuffer.append("\",\"imagename\":\"");
                softwareDataBuffer.append(ghoNameArray[i].split(",")[0]);
                softwareDataBuffer.append("\"}");
                if (i != ghoNameArray.length - 1) {
                    softwareDataBuffer.append(",");
                }
		    }
		    softwareDataBuffer.append("],");
		}
		softwareDataBuffer.append("\"os\":[{\"desc\":\"");
		softwareDataBuffer.append(systemDatas[2]);
		softwareDataBuffer.append("\",\"osDiskSizeMin\":\"");
		softwareDataBuffer.append(systemDatas[1]);
		softwareDataBuffer.append("\",\"value\":\"");
		softwareDataBuffer.append(systemDatas[0]);
		softwareDataBuffer.append("\"}],\"introduction\":\"");
		softwareDataBuffer.append(software.getSoftwareIntroduce());
		softwareDataBuffer.append("\",\"logo\":\"");
		softwareDataBuffer.append(software.getSoftwareImgUrl());
		softwareDataBuffer.append("\",\"sellerId\":\"");
		softwareDataBuffer.append(software.getSoftwareOffer());
		softwareDataBuffer.append("\",\"useinstructions\":\"");
		softwareDataBuffer.append(software.getSoftwareSpecificationUrl());
		softwareDataBuffer.append("\",\"configinstructions\":\"");
		softwareDataBuffer.append(software.getSoftwareConfigSpecificationUrl());
		softwareDataBuffer.append("\",\"authorization\":\"");
		softwareDataBuffer.append(software.getSoftwareSellAuthorizationUrl());
		softwareDataBuffer.append("\",\"applicationtype\":\"");
		softwareDataBuffer.append(software.getProcessType());
		softwareDataBuffer.append("\",\"divideinto\":\"");
		softwareDataBuffer.append(software.getSoftwareSellProportion());
		softwareDataBuffer.append("%\""+"},\"producttype\":[");
		
		
		String[] titleArray = titleData.split(",");
		
		for(int i=0;i<titleArray.length;i++){
			if(titleArray[i].equals("产品类型") ){
				titleArray[i] = "name";
			}
			if(titleArray[i].equals("软件包月价格(元)")){
				titleArray[i] = "monthprice";
			}
			if(titleArray[i].equals("软件包年价格(元)")){
				titleArray[i] = "yearprice";
			}
			if(titleArray[i].equals("软件规格")){
				titleArray[i] = "size";
			}
		}
		
		String[] name = {"desc","name","osDisk","value","productId","value"};//value 内存
		String json="" ;
		String[] dataTemp=datas.split(";");
		for(int i=0;i<rowCount;i++){
			String[] dataArray = dataTemp[i].split("/");
			String[] data = dataArray[0].split(",");
			json = json+"{";
			for(int j=0;j<colCount;j++){
				if(j==colCount-1){
					json = json + "\""+titleArray[j]+"\":\""+data[j]+"\"";
				}else{
					if(software.getSoftwareKind().equals("1")){
						if(j==1){
							json = json + "\"vm\":{";
							String[] value = data[j].split(":");
							String[] temp = value[1].split("：");
							
							String[] tempAttr = temp[1].split("、");
								String attr = "\""+name[0]+"\""+":"+"\""+dataArray[1]+"\","+ "\""+name[1]+"\":\""+temp[0]+"\",\""+name[2]+"\":\""+tempAttr[2]+"\",\""+name[3]+"\":\""+tempAttr[1]+"\",\""+name[4]+"\":\""+value[0]+"\"},\""+name[5]+"\":\""+tempAttr[0]+"\",";
								json = json +attr;
						}else{
							json = json + "\""+titleArray[j]+"\":\""+data[j]+"\""+",";
						}
					}else{
						if(j==2){
							json = json + "\"vm\":{";
							String[] value = data[j].split(":");
							String[] temp = value[1].split("：");
							String[] tempAttr = temp[1].split("、");
							String attr = "\""+name[0]+"\""+":"+"\""+dataArray[1]+"\","+ "\""+name[1]+"\":\""+temp[0]+"\",\""+name[2]+"\":\""+tempAttr[2]+"\",\""+name[3]+"\":\""+tempAttr[1]+"\",\""+name[4]+"\":\""+value[0]+"\"},\""+name[5]+"\":\""+tempAttr[0]+"\",";
							json = json +attr;
						}else{
							json = json + "\""+titleArray[j]+"\":\""+data[j]+"\""+",";
						}
					}
					
				}
			}
			if(i<rowCount-1){
				json = json+"},";
			}else{
				json = json+"}] }";
			}
		}
		softwareDataBuffer.append(json);
		return softwareDataBuffer.toString();
	}
	
}
