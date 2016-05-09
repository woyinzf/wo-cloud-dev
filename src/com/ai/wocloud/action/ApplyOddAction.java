package com.ai.wocloud.action;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.ai.wocloud.bean.ApplyOdd;
import com.ai.wocloud.bean.OsTemplate;
import com.ai.wocloud.bean.Software;
import com.ai.wocloud.bean.SoftwareType;
import com.ai.wocloud.bean.VmData;
import com.ai.wocloud.bean.VmInfo;
import com.ai.wocloud.service.interfaces.ApplyOddService;
import com.ai.wocloud.service.interfaces.SoftwareService;
import com.ai.wocloud.service.interfaces.SoftwareTypeService;
import com.ai.wocloud.service.interfaces.VmService;
import com.ai.wocloud.system.action.BaseAction;
import com.ai.wocloud.system.constants.Constants;
import com.ai.wocloud.system.session.SessionInfo;
import com.ai.wocloud.system.util.GetInterfacesUrlUtils;
import com.ai.wocloud.system.util.GetJsonUtil;
import com.ai.wocloud.system.util.JsonToObj;
import com.ai.wocloud.system.util.PostJson;
import com.ai.wocloud.system.util.QueryOsData;
import com.ai.wocloud.system.util.RestRequestUtil;


@Transactional(rollbackFor = Exception.class)
@Controller
@RequestMapping(value = "/applyOdd")
public class ApplyOddAction extends BaseAction {
	private Logger logger = Logger.getLogger(LoginInfoAction.class);
	
	@Autowired
	private SoftwareService softwareService;
	@Autowired
	private ApplyOddService applyOddService;
	@Autowired
	private VmService vmService;
	
	@Autowired
	private SoftwareTypeService softwareTypeSerivce;
	
	
	/**
	 * 该方法用于新增软件的提交审核和签约申请，根据前台传过来的id，在数据库中查找数据，如果查找到了数据，则执行的是签约申请
	 * 如果没找到数据则执行的是提交审核
	 * @param software
	 * @param request
	 * @param response
	 * @author 旭锋
	 */
	@SuppressWarnings("unchecked")
    @RequestMapping(value = "/addApplyOdd")
	public void addApplyOdd(Software software,HttpServletRequest request, HttpServletResponse response){
		System.out.println("test 1111");
		String userId=SessionInfo.getLoginInfo(request).getLoginName();
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = dateformat.format(new Date());
		ApplyOdd applyOdd = new ApplyOdd();
		String separator=File.separator;
		JSONObject json = new JSONObject();
		String POST_URL="";
		List<String> fileName = new ArrayList<String>();
		
		try {
			POST_URL = GetInterfacesUrlUtils.getInterfacesUrl("vmUrl");
		} catch (Exception e) {
			logger.error("获取虚拟机接口路径出错",e);
			try {
				json.put("error", POST_URL);
			} catch (JSONException e1) {
				logger.error("json保存数据错误",e);
			}
			this.responseFailed(response, "系统异常请稍后再试！", json);
		}
		
		software.setHandleId(userId);

		
		String basePath = "/upload/software/"+software.getHandleId()+"/";
		
		String backResult = "";
		String softwareBaseUrl = request.getSession().getServletContext().getRealPath(separator+"upload"+separator+"software"+separator+software.getHandleId()+separator);

		/*
		 * 张家龙--------------适配图片在不同的系统下，放在指定位置。
		 * String basePath = "";
		String softwareBaseUrl = "";
		
		  if ("\\".equals(File.separator)) {
			   basePath = "/upload/software/"+software.getHandleId()+"/";
			   softwareBaseUrl = "d:"+separator+"upload"+separator+"software"+separator+software.getHandleId()+separator;
	            // is WINDOWS
	        } else if ("/".equals(File.separator)) {
	           basePath = "/upload/software/"+software.getHandleId()+"/";
	           softwareBaseUrl = "home:"+separator+"upload"+separator+"software"+separator+software.getHandleId()+separator;
	            // is LINUX
	        }
		
		
		
		String backResult = "";*/
		
		String softwareStatus = Constants.SoftStatus.NEW_INCREASE ;  //0:未审核 1：审核中 2：已上架 3：已下架
		String commSoftwareStatus = Constants.SoftStatus.TO_VERIFY;
		int id =software.getId();	//前台传过来的id
		Software isSoftware =  softwareService.findSoftwareByid(id); //根据id获取数据
		SimpleDateFormat softwareIdDate = new SimpleDateFormat("yyyyMMddhhmmss");
		String softwareId = softwareIdDate.format(new Date());
		if(isSoftware != null){
		    String[] urlArray = new String[4];
			//获取到数据，则执行此方法，此方法就是软件列表页面上的签约申请按钮执行的方法
			if( !(isSoftware.getSoftwareConfigSpecificationUrl().trim().equals(""))){
			    urlArray[0] = isSoftware.getSoftwareConfigSpecificationUrl();
			    if(isSoftware.getSoftwareConfigSpecificationUrl()!=null && !"".equals(isSoftware.getSoftwareConfigSpecificationUrl())){
			    	isSoftware.setSoftwareConfigSpecificationUrl(basePath+isSoftware.getSoftwareConfigSpecificationUrl());
			    }
			}
			if(!(isSoftware.getSoftwareImgUrl().trim().equals(""))){
			    urlArray[1] = isSoftware.getSoftwareImgUrl();
			    if(isSoftware.getSoftwareImgUrl()!=null && !"".equals(isSoftware.getSoftwareImgUrl())){
			    	isSoftware.setSoftwareImgUrl(basePath+isSoftware.getSoftwareImgUrl());
			    }
			}
			if(!(isSoftware.getSoftwareSellAuthorizationUrl().trim().equals(""))){
			    urlArray[2] = isSoftware.getSoftwareSellAuthorizationUrl();
			    if(isSoftware.getSoftwareSellAuthorizationUrl()!=null && !"".equals(isSoftware.getSoftwareSellAuthorizationUrl())){
			    	isSoftware.setSoftwareSellAuthorizationUrl(basePath+isSoftware.getSoftwareSellAuthorizationUrl());
			    }
			}
			if(!(isSoftware.getSoftwareSpecificationUrl().trim().equals(""))){
			    urlArray[3] = isSoftware.getSoftwareSpecificationUrl();
			    if(isSoftware.getSoftwareSpecificationUrl()!=null && !"".equals(isSoftware.getSoftwareSpecificationUrl())){
			    	isSoftware.setSoftwareSpecificationUrl(basePath+isSoftware.getSoftwareSpecificationUrl());
			    }
			}
			
			String softwareData = GetJsonUtil.getJson(isSoftware); //将software的数据进行拼装成一个json数据
			
			try {
				backResult = PostJson.readContentFromPost(POST_URL, softwareData);//将拼装好的json数据发送给运营那边
				logger.info(backResult);
				isSoftware.setSoftwareConfigSpecificationUrl(urlArray[0]);
		        isSoftware.setSoftwareImgUrl(urlArray[1]);
		        isSoftware.setSoftwareSellAuthorizationUrl(urlArray[2]);
		        isSoftware.setSoftwareSpecificationUrl(urlArray[3]);
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("调接口出错",e);
				
				try {
					json.put("backResult", backResult);
				} catch (JSONException e1) {
					e1.printStackTrace();
					logger.error("json保存数据异常",e);
				}
				this.responseFailed(response, "系统异常,数据提交审核失败，请在人我的软件列表中再次申请", null);
			}
			isSoftware.setSoftwareStatus(commSoftwareStatus);
			isSoftware.setSoftwareId(softwareId);
			softwareService.updateSoftware(isSoftware); //更新pm_software中的状态值，softwareData的值和softwareId软件id
			isSoftware.setCommandTime(dateStr); //再次设置isSoftware的提交时间，用于向history表中出入数据时，更新时间字段
			softwareService.insertSoftwareHis(isSoftware); //向history表中插入数据
			int historyId = softwareService.queryHistoryId(softwareId); //根据softwareId 获取到插入到history表中的数据的id，用于向appcation表中插入
			applyOdd.setApplyOddType(Constants.APPLICATION_TYPE.QYSQ);  //申请单类型
			applyOdd.setSoftwareId(historyId);
			applyOdd.setApplyOddStatus(Constants.APPLYODD_STATUS.INAPPLY);//申请单状态
			applyOdd.setApplyOddSuggest("");
			applyOdd.setApplyOddTime(dateStr);
			applyOddService.insertApplyOdd(applyOdd);//将申请单存入pm_application表中
			this.responseSuccess(response, "签约申请成功！", null);
		}else{
			//此方法是新增软件页面的提交审核按钮执行的方法
			
			//以下为文件上传
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
			if(multipartResolver.isMultipart(request)){
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
				List<MultipartFile> files =  multiRequest.getFiles("myfiles");
				
				if(files.size()>0){
					for(MultipartFile myfile : files){
						if(myfile.isEmpty()){ 
						    logger.info("文件未上传");
			            }else{ 
			                logger.info("文件长度: " + myfile.getSize());
			                logger.info("文件类型: " + myfile.getContentType());
			                logger.info("文件名称: " + myfile.getName());
			                logger.info("文件原名: " + myfile.getOriginalFilename());
			                fileName.add(myfile.getOriginalFilename());
			                try {
								FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(softwareBaseUrl, myfile.getOriginalFilename()));
							} catch (IOException e) {
								try {
									json.put(myfile.getOriginalFilename(), myfile.getOriginalFilename()+"上传失败！");
								} catch (JSONException e1) {
									logger.error("json 保存数据异常！",e);
								}
								logger.error("文件复制失败,上传文件失败！"+myfile.getOriginalFilename(),e);
								this.responseFailed(response, "系统异常请稍后再试！", json);
							} 
			            } 
					}
				}
			}
			List<VmData> vmDatas = new ArrayList<VmData>();
			vmDatas = (List<VmData>) request.getSession().getAttribute("vmData");
			List<?> osTemplates = (List<?>) request.getSession().getAttribute("osTemplates");
			
			String osDataId = request.getParameter("operateSystem");
			String operateSystem ="";
			for(int i=0;i<osTemplates.size();i++){
				OsTemplate osTemplate = (OsTemplate) osTemplates.get(i);
				if(osDataId.equals(osTemplate.getId())){
					operateSystem = osTemplate.getId()+","+osTemplate.getSystem()+","+osTemplate.getName();
				}
			}
			String title = request.getParameter("tableTitle"); 
			String service = request.getParameter("serviceData"); 
			String rowC = request.getParameter("tableRow"); 
			String colC = request.getParameter("tabelCol"); 
			String softwareData = request.getParameter("softwareData");
			String softwareIntroduce = software.getSoftwareIntroduce();
			String[] regs = {"\"", "'"};
			softwareIntroduce = softwareIntroduce.replaceAll (regs[0], regs[1]);
			software.setSoftwareIntroduce(softwareIntroduce);
			String[] softwareDates = softwareData.split(",");
			for(int i=0;i<softwareDates.length;i++){
				if(softwareDates[i].trim().equals("")){
					softwareDates[i]="0";
				}
			}
			String[] datas = new String[softwareDates.length];
			softwareData="";
			if(colC.equals("5")){				
				for(int i=0;i<Integer.parseInt(rowC);i++){
					datas = softwareDates[1+i*4].split(":");
					String productId = datas[0].toString();
					for(int j=0;j<vmDatas.size();j++){
						VmData vmData = (VmData) vmDatas.get(j);
						if(vmData.getProductId().trim().equals(productId.trim())){
								softwareData+=softwareDates[4*i]+","+vmData.getProductId()+":"+vmData.getName()+"："+vmData.getCpuValue()+"、"+vmData.getValue()+"、"+vmData.getOsDisk()+","+softwareDates[4*i+2]+","+softwareDates[4*i+3]+"/"+ vmData.getDesc()+";";
						}
					}
				}
				
			}else{
				for(int i=0;i<Integer.parseInt(rowC);i++){
					datas = softwareDates[2+i*5].split(":");
					String productId = datas[0].toString();
					for(int j=0;j<vmDatas.size();j++){
						VmData vmData = (VmData) vmDatas.get(j);
						if(vmData.getProductId().trim().equals(productId.trim())){
								softwareData+=softwareDates[5*i]+","+softwareDates[5*i+1]+","+vmData.getProductId()+":"+vmData.getName()+"："+vmData.getCpuValue()+"、"+vmData.getValue()+"、"+vmData.getOsDisk()+","+softwareDates[5*i+3]+","+softwareDates[5*i+4]+"/"+ vmData.getDesc()+";";
						}
					}
				}
				
			}
			softwareData=softwareData.substring(0, softwareData.length()-1);
			softwareData =softwareData+"|"+title+"|"+service+"|"+rowC+"|"+colC;
			
			
			
			software.setOperateSystem(operateSystem);
			software.setCommandTime(dateStr);
			software.setSoftwareStatus(softwareStatus);
			software.setSoftwareId(softwareId);
			software.setSoftwareData(softwareData);
			software.setProcessType(Integer.parseInt(Constants.APPLICATION_TYPE.QYSQ));
			
			softwareService.insertSoftware(software);//先保存数据到pm_software中
			software = softwareService.findSoftwareByid(softwareId);
			String[] urlArray = new String[4];
            //获取到数据，则执行此方法，此方法就是软件列表页面上的签约申请按钮执行的方法
			if( !(software.getSoftwareConfigSpecificationUrl().equals(""))){
			    urlArray[0] = software.getSoftwareConfigSpecificationUrl();
			    if(software.getSoftwareConfigSpecificationUrl()!=null && !"".equals(software.getSoftwareConfigSpecificationUrl())){
			    	software.setSoftwareConfigSpecificationUrl(basePath+software.getSoftwareConfigSpecificationUrl());
			    }
			}
			if(!(software.getSoftwareImgUrl().equals(""))){
			    urlArray[1] = software.getSoftwareImgUrl();
			    if(software.getSoftwareImgUrl()!=null && !"".equals(software.getSoftwareImgUrl())){
			    	software.setSoftwareImgUrl(basePath+software.getSoftwareImgUrl());
			    }
			}
			if(!(software.getSoftwareSellAuthorizationUrl().equals(""))){
			    urlArray[2] =software.getSoftwareSellAuthorizationUrl();
			    if(software.getSoftwareSellAuthorizationUrl()!=null && !"".equals(software.getSoftwareSellAuthorizationUrl())){
			    	software.setSoftwareSellAuthorizationUrl(basePath+software.getSoftwareSellAuthorizationUrl());
			    }
			}
			if(!(software.getSoftwareSpecificationUrl().equals(""))){
			    urlArray[3] = software.getSoftwareSpecificationUrl();
			    if(software.getSoftwareSpecificationUrl()!=null && !"".equals(software.getSoftwareSpecificationUrl())){
			    	software.setSoftwareSpecificationUrl(basePath+software.getSoftwareSpecificationUrl());
			    }
			}
			String softwareJsonData = GetJsonUtil.getJson(software);
			try {
				backResult = PostJson.readContentFromPost(POST_URL, softwareJsonData);
				logger.info(backResult);
				software.setSoftwareConfigSpecificationUrl(urlArray[0]);
                software.setSoftwareImgUrl(urlArray[1]);
                software.setSoftwareSellAuthorizationUrl(urlArray[2]);
                software.setSoftwareSpecificationUrl(urlArray[3]);
			} catch (Exception e) {
				logger.error("调接口出错",e);
				this.responseFailed(response, "调接口出错,数据提交审核失败，请转到我的软件列表中再次申请！", null);
			}
			software.setSoftwareStatus(commSoftwareStatus);
			softwareService.updateSoftware(software); //更新software中的数据
			software.setCommandTime(dateStr);
			softwareService.insertSoftwareHis(software);//向history表中插入数据
			int historyId = softwareService.queryHistoryId(softwareId);
			applyOdd.setApplyOddType(Constants.APPLICATION_TYPE.QYSQ);  //申请单类型
			applyOdd.setSoftwareId(historyId);
			applyOdd.setApplyOddStatus(Constants.APPLYODD_STATUS.INAPPLY);//申请单状态
			applyOdd.setApplyOddSuggest("");
			applyOdd.setApplyOddTime(dateStr);
			applyOddService.insertApplyOdd(applyOdd);//将申请单存入pm_application表中
			this.responseSuccess(response, "提交审核成功！", null);
		}
		
	}
	
	
	/**
	 * @author 旭锋
	 * 此方法仅用于软件修改页面中的提交审核按执行的方法，类似于软件新增页面的提交审核按钮执行的方法
	 */
    @RequestMapping(value = "/addApplyOddModify")
	public void addApplyOddModify(Software software,HttpServletRequest request, HttpServletResponse response){
		String separator=File.separator;
		String POST_URL = GetInterfacesUrlUtils.getInterfacesUrl("vmUrl");
		String userId=SessionInfo.getLoginInfo(request).getLoginName();

		//String userId="123456789";
		software.setHandleId(userId);
		JSONObject json = new JSONObject();
		String basePath = "/upload/software/"+software.getHandleId()+"/";
		String softwareBaseUrl = request.getSession().getServletContext().getRealPath(separator+"upload"+separator+"software"+separator+software.getHandleId()+separator);
		/*
		 * 张家龙---------------适配图片在不同的系统下，放在指定位置。
		 * String basePath = "";
		String softwareBaseUrl = "";
		
		  if ("\\".equals(File.separator)) {
			   basePath = "/upload/software/"+software.getHandleId()+"/";
			   softwareBaseUrl = "d:"+separator+"upload"+separator+"software"+separator+software.getHandleId()+separator;
	            // is WINDOWS
	        } else if ("/".equals(File.separator)) {
	           basePath = "/upload/software/"+software.getHandleId()+"/";
	           softwareBaseUrl = "home:"+separator+"upload"+separator+"software"+separator+software.getHandleId()+separator;
	            // is LINUX
	        }
		*/
		List<String> fileName = new ArrayList<String>();
		ApplyOdd applyOdd = new ApplyOdd();
		String softwareStatus = Constants.SoftStatus.NEW_INCREASE ;  //0:未审核 1：审核中 2：已上架 3：已下架
		String commSoftwareStatus = Constants.SoftStatus.TO_VERIFY;
		int id =software.getId();
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
			if(multipartResolver.isMultipart(request)){
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
				List<MultipartFile> files =  multiRequest.getFiles("myfiles");
				if(files.size()>0){
					for(MultipartFile myfile : files){
						if(myfile.isEmpty()){ 
						    logger.info("文件未上传");
			            }else{ 
			                logger.info("文件长度: " + myfile.getSize());
                            logger.info("文件类型: " + myfile.getContentType());
                            logger.info("文件名称: " + myfile.getName());
                            logger.info("文件原名: " + myfile.getOriginalFilename());
			                fileName.add(myfile.getOriginalFilename());
			                try {
								FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(softwareBaseUrl, myfile.getOriginalFilename()));
							} catch (IOException e) {
								try {
									json.put(myfile.getOriginalFilename(), myfile.getOriginalFilename()+"上传失败！");
								} catch (JSONException e1) {
									logger.error("json 保存数据异常！",e);
								}
								logger.error("文件复制失败,上传文件失败！"+myfile.getOriginalFilename(),e);
								this.responseFailed(response, "系统异常请稍后再试！", json);
							} 
			            } 
					}
					
				}
				
				
			}
			List vmDatas = new ArrayList();
			vmDatas = (List) request.getSession().getAttribute("vmData");
			List osTemplates = (List) request.getSession().getAttribute("osTemplates");
			String osDataId = request.getParameter("operateSystem");
			String operateSystem ="";
			for(int i=0;i<osTemplates.size();i++){
				OsTemplate osTemplate = (OsTemplate) osTemplates.get(i);
				if(osDataId.equals(osTemplate.getId())){
					operateSystem = osTemplate.getId()+","+osTemplate.getSystem()+","+osTemplate.getName();
				}
			}
			String title = request.getParameter("tableTitle"); 
			String service = request.getParameter("serviceData"); 
			String rowC = request.getParameter("tableRow"); 
			String colC = request.getParameter("tabelCol"); 
			String softwareData = request.getParameter("softwareData");
			String softwareIntroduce = software.getSoftwareIntroduce();
			String[] regs = {"\"", "'"};
			softwareIntroduce = softwareIntroduce.replaceAll (regs[0], regs[1]);
			software.setSoftwareIntroduce(softwareIntroduce);
			String[] softwareDates = softwareData.split(",");
			String[] datas = new String[softwareDates.length];
			softwareData="";
			if(colC.equals("5")){
				for(int i=0;i<Integer.parseInt(rowC);i++){
					datas = softwareDates[1+i*4].split(":");
					String productId = datas[0].toString();
					for(int j=0;j<vmDatas.size();j++){
						VmData vmData = (VmData) vmDatas.get(j);
						if(vmData.getProductId().trim().equals(productId.trim())){
							softwareData+=softwareDates[4*i]+","+vmData.getProductId()+":"+vmData.getName()+"："+vmData.getCpuValue()+"、"+vmData.getValue()+"、"+vmData.getOsDisk()+","+softwareDates[4*i+2]+","+softwareDates[4*i+3]+"/"+ vmData.getDesc()+";";							
						}
					}
				}
				
			}else{
				for(int i=0;i<Integer.parseInt(rowC);i++){
					datas = softwareDates[2+i*5].split(":");
					String productId = datas[0].toString();
					for(int j=0;j<vmDatas.size();j++){
						VmData vmData = (VmData) vmDatas.get(j);
						if(vmData.getProductId().trim().equals(productId.trim())){
							softwareData+=softwareDates[5*i]+","+softwareDates[5*i+1]+","+vmData.getProductId()+":"+vmData.getName()+"："+vmData.getCpuValue()+"、"+vmData.getValue()+"、"+vmData.getOsDisk()+","+softwareDates[5*i+3]+","+softwareDates[5*i+4]+"/"+ vmData.getDesc()+";";
						}
					}
				}
				
			}
			softwareData=softwareData.substring(0, softwareData.length()-1);
			softwareData =softwareData+"|"+title+"|"+service+"|"+rowC+"|"+colC;
			
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateStr = dateformat.format(new Date());
			SimpleDateFormat softwareIdDate = new SimpleDateFormat("yyyyMMddhhmmss");
			String softwareId = softwareIdDate.format(new Date());
			software.setOperateSystem(operateSystem);
			software.setSoftwareStatus(softwareStatus);
			software.setSoftwareId(softwareId);
			software.setSoftwareData(softwareData);
			software.setProcessType(Integer.parseInt(Constants.APPLICATION_TYPE.QYSQ));
			String[] urlArray = new String[4];
            //获取到数据，则执行此方法，此方法就是软件列表页面上的签约申请按钮执行的方法
			 //获取到数据，则执行此方法，此方法就是软件列表页面上的签约申请按钮执行的方法
            if( !(software.getSoftwareConfigSpecificationUrl().equals(""))){
                urlArray[0] = software.getSoftwareConfigSpecificationUrl();
                if(software.getSoftwareConfigSpecificationUrl()!=null && !"".equals(software.getSoftwareConfigSpecificationUrl())){
                	software.setSoftwareConfigSpecificationUrl(basePath+software.getSoftwareConfigSpecificationUrl());
                }
            }
            if(!(software.getSoftwareImgUrl().equals(""))){
                urlArray[1] = software.getSoftwareImgUrl();
                if(software.getSoftwareImgUrl()!=null && !"".equals(software.getSoftwareImgUrl())){
                	software.setSoftwareImgUrl(basePath+software.getSoftwareImgUrl());
                }
            }
            if(!(software.getSoftwareSellAuthorizationUrl().equals(""))){
                urlArray[2] =software.getSoftwareSellAuthorizationUrl();
                if(software.getSoftwareSellAuthorizationUrl()!=null && !"".equals(software.getSoftwareSellAuthorizationUrl())){
                	software.setSoftwareSellAuthorizationUrl(basePath+software.getSoftwareSellAuthorizationUrl());
                }
            }
            if(!(software.getSoftwareSpecificationUrl().equals(""))){
                urlArray[3] = software.getSoftwareSpecificationUrl();
                if(software.getSoftwareSpecificationUrl()!=null && !"".equals(software.getSoftwareSpecificationUrl())){
                	software.setSoftwareSpecificationUrl(basePath+software.getSoftwareSpecificationUrl());
                }
            }
            String softwareJsonData = GetJsonUtil.getJson(software);
            try {
                logger.info(PostJson.readContentFromPost(POST_URL, softwareJsonData));
                software.setSoftwareConfigSpecificationUrl(urlArray[0]);
                software.setSoftwareImgUrl(urlArray[1]);
                software.setSoftwareSellAuthorizationUrl(urlArray[2]);
                software.setSoftwareSpecificationUrl(urlArray[3]);
            } catch (Exception e) {
                logger.error("调接口出错",e);
                this.responseFailed(response, "调接口出错,数据提交审核失败，请转到我的软件列表中再次申请！", null);
            }
			software.setSoftwareStatus(commSoftwareStatus);			
			software.setCommandTime(dateStr);
			softwareService.updateSoftware(software);
			softwareService.insertSoftwareHis(software);
			int historyId = softwareService.queryHistoryId(softwareId);
			applyOdd.setApplyOddType(Constants.APPLICATION_TYPE.QYSQ);  //申请单类型
			applyOdd.setSoftwareId(historyId);
			applyOdd.setApplyOddStatus(Constants.APPLYODD_STATUS.INAPPLY);//申请单状态
			applyOdd.setApplyOddSuggest("");
			applyOdd.setApplyOddTime(dateStr);
			applyOddService.insertApplyOdd(applyOdd);//将申请单存入pm_application表中
			this.responseSuccess(response, "提交审核成功！", null);
		
		
	}
	
	
	/**
	 * 此方式用于在软件列表页面点击新增按钮后，执行的查询操作系统的方法
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/queryOsData")
	public String queryOsData(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String userId=SessionInfo.getLoginInfo(request).getLoginName();
		String urlOs = GetInterfacesUrlUtils.getInterfacesUrl("osUrl");
		
		List<VmInfo> vmInfoList = new ArrayList<VmInfo>();
		List<SoftwareType> softwareTypes = new ArrayList<SoftwareType>();
		
		try {
			String result = QueryOsData.readContentFromGet(urlOs);
			JSONObject jsonObject = new JSONObject(result);
			JSONObject jsonData = (JSONObject) jsonObject.get("listtemplatesresponse");
			
			OsTemplate osTemplate = new OsTemplate();
			String template = jsonData.get("template").toString();
			List<?> osTemplates = JsonToObj.jsonUtil(template, osTemplate);
			request.setAttribute("osTemplates", osTemplates);
			request.getSession().setAttribute("osTemplates", osTemplates);
			softwareTypes = softwareTypeSerivce.findAllSoftwareType();
			request.setAttribute("softwareTypes", softwareTypes);
			vmInfoList= vmService.findAllVmInfo();
			String json = vmInfoList.get(0).getConfig();
			logger.info("vmJson"+json);
			JSONObject jsonvms = new JSONObject(json);
			String jsonvm = jsonvms.get("vmConfig").toString();
			JSONArray jsonArr =JSONArray.fromObject(jsonvm);
			List<VmData> vminfolist = new ArrayList<VmData>();
			for(int i=0;i<jsonArr.size();i++){
				
				String memory = jsonArr.getJSONObject(i).toString();
				JSONObject jsb = new JSONObject(memory);
				String cpuValue = jsb.get("value").toString();
				String jsonString = jsb.getString("memory").toString();
				JSONArray jsonArray = JSONArray.fromObject(jsonString);
					for(int j=0;j<jsonArray.size();j++){
						VmData vmData = new VmData();
						String memorydata = jsonArray.getJSONObject(j).toString();
						
						JSONObject jsonMemory = new JSONObject(memorydata);
						String productId = jsonMemory.get("productId").toString();
						if(!(productId.equals(""))){
							vmData.setDesc(jsonMemory.get("desc").toString());
							vmData.setName(jsonMemory.get("name").toString());
							if(jsonMemory.has("osDisk"))
							{
							vmData.setOsDisk(jsonMemory.get("osDisk").toString());
							}
							vmData.setProductId(productId);
							vmData.setVmPrice(jsonMemory.get("vmPrice").toString());
							vmData.setValue(jsonMemory.get("value").toString());
							
							vminfolist.add(vmData);
						}
						vmData.setCpuValue(cpuValue);
						
					
					}
				
			}
			List mirrorlist = new ArrayList();		
			try {
				String customerId=softwareService.getCustId(SessionInfo.getLoginInfo(request).getLoginName()+"@softwareProvider");
				//String customerId=softwareService.getCustId("admin_wo@softwareProvider");
				String result2 = RestRequestUtil.getMirror(customerId);
				JSONObject jsonObject2 = new JSONObject(result2);
				if(jsonObject2.get("code").toString().equals("0")){
					String stringArray = jsonObject2.get("data").toString();
					JSONArray jsonArray = JSONArray.fromObject(stringArray);
					for(int i=0;i<jsonArray.size();i++){
						String stringData=jsonArray.getJSONObject(i).toString();
						JSONObject jsonData2=new JSONObject(stringData);
						Map mirrorMap=new HashMap();
						mirrorMap.put("imageid", jsonData2.get("imageId").toString());
						mirrorMap.put("imagename", jsonData2.get("imageName").toString());
						
						mirrorlist.add(mirrorMap);					
						request.setAttribute("mirrorlist", mirrorlist);
					}
				}			
			} catch (JSONException e) {
				e.printStackTrace();
			}catch(Exception e){
				e.printStackTrace();
			}
			request.setAttribute("vmData", vminfolist);
			request.getSession().setAttribute("vmData", vminfolist);
			request.getSession().setAttribute("userId", userId);
			
		} catch (Exception e) {
			logger.error(e);
		}
		 return "software/newSoftwareApply";
	}
}
