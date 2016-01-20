package com.ai.wocloud.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
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

import com.ai.wocloud.bean.ApplyBasic;
import com.ai.wocloud.bean.ApplyInfo;
import com.ai.wocloud.bean.ApplyOdd;
import com.ai.wocloud.bean.OsTemplate;
import com.ai.wocloud.bean.Software;
import com.ai.wocloud.bean.SoftwareType;
import com.ai.wocloud.bean.TreeNode;
import com.ai.wocloud.bean.VmData;
import com.ai.wocloud.bean.VmInfo;
import com.ai.wocloud.service.interfaces.ApplyOddService;
import com.ai.wocloud.service.interfaces.SoftwareService;
import com.ai.wocloud.service.interfaces.SoftwareTypeService;
import com.ai.wocloud.service.interfaces.VmService;
import com.ai.wocloud.system.action.BaseAction;
import com.ai.wocloud.system.constants.Constants;
import com.ai.wocloud.system.session.SessionInfo;
import com.ai.wocloud.system.util.DateUtil;
import com.ai.wocloud.system.util.DicUtil;
import com.ai.wocloud.system.util.GetInterfacesUrlUtils;
import com.ai.wocloud.system.util.GetJsonUtil;
import com.ai.wocloud.system.util.JsonToObj;
import com.ai.wocloud.system.util.PageModel;
import com.ai.wocloud.system.util.PostJson;
import com.ai.wocloud.system.util.QueryOsData;
import com.ai.wocloud.system.util.RestRequestUtil;
import com.ai.wocloud.system.util.StringUtil;
/**
 * 
 * @author zhang xufeng
 *
 */
@Transactional(rollbackFor = Exception.class)
@Controller
@RequestMapping(value = "/software")
public class SoftwareAction extends BaseAction {
	 private Logger logger = Logger.getLogger(LoginInfoAction.class);
	@Autowired
	private SoftwareService softwareService;
	@Autowired
	private SoftwareTypeService softwareTypeSerivce;
	
	@Autowired
	private VmService vmService;
	
	@Autowired
	private ApplyOddService applyOddService;
	
	/**
	 * 查找全部的software数据
	 * @param request
	 * @param response
	 * @return
	 * @author zhang xufeng
	 */
	@RequestMapping(value = "/findAllSoftware")
	public String findAllSoftware(HttpServletRequest request, HttpServletResponse response){
		String userId=SessionInfo.getLoginInfo(request).getLoginName();
		List<Software> softwares = new ArrayList<Software>();
		softwares = softwareService.findAll(userId);
		for(int i=0;i<softwares.size();i++){
			System.out.println(softwares.get(i).getSoftwareName());
		}
		request.setAttribute("userId", userId);
		return "software/showAllSoftware";
	}
	
	
	/**
	 * 分页查找全部的software数据
	 * @param request
	 * @param response
	 * @return
	 * @author zhang xufeng
	 */
	@SuppressWarnings("all")
	@RequestMapping(value = "/spfindAllSoftware")
	public String spfindAllSoftware(HttpServletRequest request, HttpServletResponse response){
		String userId=SessionInfo.getLoginInfo(request).getLoginName();
		String orderCond=request.getParameter("orderCond");
		String orderType=request.getParameter("orderType");
		String condValue=request.getParameter("condValue");
		if(condValue!=null && !"".equals(condValue)){
    		try {
    			condValue= URLDecoder.decode(condValue,"UTF-8");
    		} catch (Exception e) {
    			logger.error("condValue字符编码转换异常",e);
    		}
		}
		String page = request.getParameter("page");
		int pageSize = 10; //每页显示的行数
		Map<String,String> mapSoftwareType = new HashMap<String,String>();
		mapSoftwareType =  DicUtil.getAllSoftTypeDic();
		Iterator iter = mapSoftwareType.entrySet().iterator(); 
		int totalRecord = 0; //总行数
		totalRecord = softwareService.selectTotalRecord(userId,condValue);
		PageModel pageModel = PageModel.newPageModel(pageSize, page, totalRecord);
		List<Software> dataList = new ArrayList<Software>();
		dataList = softwareService.pageSelect(pageModel,userId,orderCond,orderType,condValue);
		for(int i=0;i<dataList.size();i++){
			dataList.get(i).setSoftwareVersions(StringUtil.versionSplit(dataList.get(i).getSoftwareVersions()));
		}
		while (iter.hasNext()) {
		    Map.Entry entry = (Map.Entry) iter.next(); 
		    String key = entry.getKey().toString(); 
		    String val = entry.getValue().toString();
		    for(int i=0;i<dataList.size();i++){
				if(key.equals(dataList.get(i).getSoftwareType())){
					dataList.get(i).setSoftwareType(val);
			    }
			}
		}	
		request.setAttribute("dataList", dataList);
		pageModel.setDataList(dataList);
		request.setAttribute("pageModel", pageModel);
		request.setAttribute("currentPage",pageModel.getCurrentPage());
		request.setAttribute("totalPage", pageModel.getTotalPage());
		request.setAttribute("orderCond", orderCond);
		request.setAttribute("orderType", orderType);
		return "software/spShowAllSoftware";
	}
	
	
	/**
	 * 用于点击增加按钮跳转到增加数据的页面
	 * @param request
	 * @param response
	 * @return
	 * @author zhang xufeng
	 */
	@RequestMapping(value = "/addSoftware")
	public String addSoftware(HttpServletRequest request, HttpServletResponse response){
		String userId = SessionInfo.getLoginInfo(request).getLoginName();
		request.setAttribute("userId", userId);
		return  "software/showAllSoftware";
	}
	
	/**
	 * 字符串转提时间戳
	 * @param timestamp
	 * @return
	 * @throws Exception
	 */
	 public static String string2Datestamp(String timestamp) throws Exception{
	     if(timestamp==null||timestamp.length()<10)
	         return null;
	     String[] tempTime = timestamp.split("-");
	     String date = tempTime[0] +"."+tempTime[1]+"."+tempTime[2];
	     String ts =  date.trim()+" 00:00:00";
	     return ts;
	 }
	 
	/**
	 * 此方法用于新增软件页面的保存按钮和修改软件页面的保存按钮执行的方法
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * @author zhang xufeng
	 */
    @RequestMapping(value = "/insertSoftware")
	public void insertSoftware( Software software, HttpServletRequest request, HttpServletResponse response){
		String userId=SessionInfo.getLoginInfo(request).getLoginName();
		JSONObject json = new JSONObject();
		int id = software.getId();
		String separator=File.separator;
		software.setHandleId(userId);
		List<String> fileName = new ArrayList<String>();
		Map<String,String> DataMap = new HashMap<String,String>();
		List<String> value = new ArrayList<String>();
	
		String softwareBaseUrl = request.getSession().getServletContext().getRealPath(separator+"upload"+separator+"software"+separator+software.getHandleId()+separator);
		/*
		 * 张家龙-------------适配图片在不同的系统下，放在指定位置。
		 * String softwareBaseUrl = "";
		
		  if ("\\".equals(File.separator)) {
			   softwareBaseUrl = "d:"+separator+"upload"+separator+"software"+separator+software.getHandleId()+separator;
	            // is WINDOWS
	        } else if ("/".equals(File.separator)) {
	           softwareBaseUrl = "home:"+separator+"upload"+separator+"software"+separator+software.getHandleId()+separator;
	            // is LINUX
	        }*/		
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
							this.responseFailed(response, "保存失败，请稍后再试！", json);
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
			if(osDataId.trim().equals(osTemplate.getId().trim())){
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
		String softwareStatus = Constants.SoftStatus.NEW_INCREASE ;  //0:未审核 1：审核中 2：已上架 3：已下架
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = dateformat.format(new Date());
		SimpleDateFormat softwareIdDate = new SimpleDateFormat("yyyyMMddhhmmss");
		String softwareId = softwareIdDate.format(new Date());
		software.setOperateSystem(operateSystem);
		software.setCommandTime(dateStr);
		software.setSoftwareStatus(softwareStatus);
		software.setSoftwareId(softwareId);
		software.setSoftwareData(softwareData);
		software.setProcessType(Integer.parseInt(Constants.APPLICATION_TYPE.QYSQ));
		if(softwareService.findSoftwareByid(id)==null){ //这里根据id查找了一次数据，如果存在数据，则为更新，如果不存在则为新增
			softwareService.insertSoftware(software);
			this.responseSuccess(response, "添加软件成功！", null);
		}else{
			softwareService.updateSoftware(software);
			this.responseSuccess(response, "软件修改成功！", null);
		}
	}
	
	/**
	 * toNewSoftwareApply: <br/>
	 * @param request
	 * @param response
	 * @return
	 * @since JDK 1.6
	 */
	@RequestMapping("toNewSoftwareApply")
	public String toNewSoftwareApply(HttpServletRequest request, HttpServletResponse response) {
	    return "software/newSoftwareApply";
	}
	
	/**
	 * 此方式是用于在软件列表页选中一条记录后点击修改的时候执行的方法
	 * @param software
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping("/modifySoftware")
	public String modifySoftware(Software software,HttpServletRequest request,HttpServletResponse response) {
		String userId=SessionInfo.getLoginInfo(request).getLoginName();
		String urlOs = null;
		try {
			urlOs = GetInterfacesUrlUtils.getInterfacesUrl("osUrl");
		} catch (Exception e) {
			logger.error("操作系统的url不存在或者有误",e);
		}
		OsTemplate osTemplate = new OsTemplate();
		List<VmInfo> vmInfoList = new ArrayList<VmInfo>();
		List<SoftwareType> softwareTypes = new ArrayList<SoftwareType>();
		int id = software.getId();
		Software isSoftware =  softwareService.findSoftwareByid(id);
		softwareTypes = softwareTypeSerivce.findAllSoftwareType();
		
		//获取操作系统
		String[] osDatas = isSoftware.getOperateSystem().split(",");
		String osId = osDatas[0];
		
		String result = QueryOsData.readContentFromGet(urlOs);
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(result);
		} catch (JSONException e) {
			logger.error("new Json对象异常！",e);
		}
		JSONObject jsonData = null;
		try {
			jsonData = (JSONObject) jsonObject.get("listtemplatesresponse");
		} catch (JSONException e) {
			logger.error("未创建以 listtemplatesresponse 的json对象",e);
		}
		String template = null;
		try {
			template = jsonData.get("template").toString();
		} catch (JSONException e) {
			logger.error("未能以 template 创建json对象",e);
		}
		List<OsTemplate> osTemplates = JsonToObj.jsonUtil(template, osTemplate);
		//获取虚拟机信息
		List<VmData> vmDatas = new ArrayList<VmData>();
		String[] softwareData = isSoftware.getSoftwareData().split("\\|");
		String vm = "";
		String productid="";
		String name = "";
		String cpuVal="";
		String value ="";
		String onDisk="";
		String desc="";		
		String[] softwareDataTemp=softwareData[0].split(";");
		int rowCount = softwareDataTemp.length;  //行数
		for(int i=0;i<rowCount;i++){
			String[] vmInfos = softwareDataTemp[i].split("/");
			String[] vmInfo = vmInfos[0].split(",");
			VmData vmd = new VmData();
			if(isSoftware.getSoftwareKind().equals("1")){
				vm=vmInfo[1];
				String[] temp = vm.split(":");
				productid = temp[0];
				String emp = temp[1];	
				String[] amp = emp.split("：");
				cpuVal=amp[1].split("、")[0];
				name=amp[1].split("、")[1];
				onDisk=amp[1].split("、")[2];
				desc=vmInfos[1];
				vmd.setCpuValue(cpuVal);
				vmd.setDesc(desc);
				vmd.setName(name);
				vmd.setOsDisk(onDisk);
				vmd.setProductId(productid);
				vmd.setValue(value);
				vmd.setProductType(vmInfo[0]);
				vmd.setMonthPrices(vmInfo[2]);
				vmd.setYearPrices(vmInfo[3]);
				vmDatas.add(vmd);
			}else{
				vm=vmInfo[2];
				String[] temp = vm.split(":");
				productid = temp[0];
				String emp = temp[1];	
				String[] amp = emp.split("：");
				cpuVal=amp[1].split("、")[0];
				name=amp[1].split("、")[1];
				onDisk=amp[1].split("、")[2];
				desc=vmInfos[1];
				vmd.setCpuValue(cpuVal);
				vmd.setDesc(desc);
				vmd.setName(name);
				vmd.setOsDisk(onDisk);
				vmd.setProductId(productid);
				vmd.setValue(value);
				vmd.setProductType(vmInfo[0]);
				vmd.setProductEtalon(vmInfo[1]);
				vmd.setMonthPrices(vmInfo[3]);
				vmd.setYearPrices(vmInfo[4]);
				vmDatas.add(vmd);
			}
		}
		
		vmInfoList= vmService.findAllVmInfo();
		String json = vmInfoList.get(0).getConfig();
		JSONObject jsonvms = null;
		try {
			jsonvms = new JSONObject(json);
		} catch (JSONException e) {
			logger.error("字符串转json对象异常！",e);
		}
		String jsonvm = null;
		try {
			jsonvm = jsonvms.get("vmConfig").toString();
		} catch (JSONException e) {
			logger.error("获取 vmConfig 异常！",e);
		}
		JSONArray jsonArr =JSONArray.fromObject(jsonvm);
		List<VmData> vminfolist = new ArrayList<VmData>();
		for(int i=0;i<jsonArr.size();i++){
			String memory = jsonArr.getJSONObject(i).toString();
			JSONObject jsb = null;
			try {
				jsb = new JSONObject(memory);
			} catch (JSONException e) {
				logger.error("以字符串vmConfig 创建json对象异常",e);
			}
			String cpuValue = null;
			try {
				cpuValue = jsb.get("value").toString();
			} catch (JSONException e) {
				logger.error("获取 value 字符串异常！",e);
			}
			String jsonString = null;
			try {
				jsonString = jsb.getString("memory").toString();
			} catch (JSONException e) {
				logger.error("以 memory 获取字符串异常！",e);
			}
			JSONArray jsonArray = JSONArray.fromObject(jsonString);
			for(int j=0;j<jsonArray.size();j++){
				VmData vmData = new VmData();
				vmData.setCpuValue(cpuValue);
				String memorydata = jsonArray.getJSONObject(j).toString();
				
				JSONObject jsonMemory = null;
				try {
					jsonMemory = new JSONObject(memorydata);
				} catch (JSONException e) {
					logger.error("以字符串 jsonMemory 创建json对象异常！",e);
				}
				String productId = null;
				try {
					productId = jsonMemory.get("productId").toString();
				} catch (JSONException e) {
					logger.error("获取 productId 字符串异常",e);
				}
				if(!(productId.equals(""))){
					try {
						vmData.setDesc(jsonMemory.get("desc").toString());
					} catch (JSONException e) {
						logger.error("desc 获取字符串异常",e);
					}
					try {
						vmData.setName(jsonMemory.get("name").toString());
					} catch (JSONException e) {
						logger.error("name 获取字符串异常",e);
					}
					try {
						vmData.setOsDisk(jsonMemory.get("osDisk").toString());
					} catch (JSONException e) {
						logger.error("osDisk 获取字符串异常",e);
					}
					vmData.setProductId(productId);
					try {
						vmData.setVmPrice(jsonMemory.get("vmPrice").toString());
					} catch (JSONException e) {
						logger.error("vmPrice 获取字符串异常",e);
					}
					try {
						vmData.setValue(jsonMemory.get("value").toString());
					} catch (JSONException e) {
						logger.error("value 获取字符串异常",e);
					}
					vminfolist.add(vmData);
				}
			}
		}
		List<Map<String, String>> mirrorlist = new ArrayList<Map<String, String>>();		
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
		//取得软件类型显示字符
		for(int i = 0; i < softwareTypes.size(); i++) {
		    if (isSoftware.getSoftwareType().equals(softwareTypes.get(i).getServiceTypeId())) {
		        request.setAttribute("softwareTypeName", softwareTypes.get(i).getServiceTypeName());
		        break;
		    }
		}
		//取得操作系统
		isSoftware.setOperateSystem(isSoftware.getOperateSystem().split(",")[0]);
		isSoftware.setGhoName(isSoftware.getGhoName().split(",")[0]);
		request.setAttribute("serviceData", softwareData[2]);
		request.setAttribute("vmData", vminfolist);
		request.getSession().setAttribute("vmData", vminfolist);
		request.setAttribute("osTemplates", osTemplates);
		request.getSession().setAttribute("userId", userId);
		request.getSession().setAttribute("osTemplates", osTemplates);
		request.setAttribute("softwareTypes", softwareTypes);
		request.setAttribute("vmDatas", vmDatas);
		request.setAttribute("isSoftware", isSoftware);
		request.setAttribute("osId", osId);
		return "software/modifySoftwareApply";
	}
		
		/**
		 * 此方法用于软件新增页面和软件修改页面中的软件名称的查询，当页面被加载的时候会用ajax调用此方法进行查询软件名称是否存在
		 * @param request
		 * @param response
		 */
 	@RequestMapping("/chackSoftwareName")
    public void chackSoftwareName(HttpServletRequest request,HttpServletResponse response){
 	    List<Software> softwareNameList = new ArrayList<Software>();
 	    String softwareName = request.getParameter("softwareName");
 	    softwareNameList = softwareService.findSoftwareBySoftwareName(softwareName);
 	    if(softwareNameList.size()>0){
 	        this.responseFailed(response, "软件名称已经存在！", null);
 	    }
 	        this.responseSuccess(response, "软件名称可以使用！", null);
 	}

	 
 	/**
 	 * softwareDown: <br/>
 	 * 软件下架
 	 * @param software
 	 * @param request
 	 * @param response
 	 * @since JDK 1.6
 	 */
 	@SuppressWarnings("all")
 	@RequestMapping("/softwareDown")
    public void softwareDown(Software software,HttpServletRequest request,HttpServletResponse response){
    	int selectId = Integer.parseInt(request.getParameter("selectId"));
    	software = softwareService.findSoftwareByid(selectId);
    	SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = dateformat.format(new Date());
		SimpleDateFormat softwareIdDate = new SimpleDateFormat("yyyyMMddhhmmss");
		String softwareId = softwareIdDate.format(new Date());
    	JSONObject json = new JSONObject();
    	ApplyOdd applyOdd = new ApplyOdd();
    	PrintWriter printWriter = null;
    	try {
    		software.setSoftwareStatus(Constants.SoftStatus.WAIT_DOWN_SHELF);
    		software.setSoftwareId(softwareId);
    		softwareService.updateSoftware(software);
    		softwareService.insertSoftwareHis(software);
    		int historyId = softwareService.queryHistoryId(softwareId);
    		applyOdd.setApplyOddType(Constants.APPLICATION_TYPE.XJSQ);  //申请单类型
			applyOdd.setSoftwareId(historyId);
			applyOdd.setApplyOddStatus(Constants.APPLYODD_STATUS.INAPPLY);//申请单状态
			applyOdd.setApplyOddSuggest("");
			applyOdd.setApplyOddTime(dateStr);
			applyOddService.insertApplyOdd(applyOdd);//将申请单存入pm_application表中
    		json.put("result", "0");
    		printWriter = response.getWriter();
            printWriter.write(json.toString());
            printWriter.flush();
            printWriter.close();
		} catch (Exception e) {
			logger.error("修改失败", e);
		} 
    }
 	
 	/**
 	 * softwareDown: <br/>
 	 * 软件取消申请
 	 * @param software
 	 * @param request
 	 * @param response
 	 * @since JDK 1.6
 	 */
 	@SuppressWarnings("all")
 	@RequestMapping("/softCancelapply")
 	public void softCancelapply(Software software,HttpServletRequest request,HttpServletResponse response){
 		int selectId = Integer.parseInt(request.getParameter("selectId"));
 		software = softwareService.findSoftwareByid(selectId);
 		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 		String dateStr = dateformat.format(new Date());
 		SimpleDateFormat softwareIdDate = new SimpleDateFormat("yyyyMMddhhmmss");
 		String softwareId = softwareIdDate.format(new Date());
 		JSONObject json = new JSONObject();
 		ApplyOdd applyOdd = new ApplyOdd();
 		PrintWriter printWriter = null;
 		try {
 		    //判断是签约申请还是升级申请,1是签约申请；非1为升级、变更申请
 		    if (software.getProcessType() == Integer.parseInt(Constants.APPLICATION_TYPE.QYSQ)) {
 		        software.setSoftwareStatus(Constants.SoftStatus.NEW_INCREASE);
 		    } else {
 		        json.put("result", "1");
 		        printWriter = response.getWriter();
 	            printWriter.write(json.toString());
 	            printWriter.flush();
 	            return;
 		    }
 			software.setSoftwareId(softwareId);
 			softwareService.updateSoftware(software);
 			softwareService.insertSoftwareHis(software);
 			int historyId = softwareService.queryHistoryId(softwareId);
 			applyOdd.setApplyOddType(Constants.APPLICATION_TYPE.QYSQ);  //申请单类型
 			applyOdd.setSoftwareId(historyId);
 			applyOdd.setApplyOddStatus(Constants.APPLYODD_STATUS.INAPPLY);//申请单状态
 			applyOdd.setApplyOddSuggest("");
 			applyOdd.setApplyOddTime(dateStr);
 			applyOddService.insertApplyOdd(applyOdd);//将申请单存入pm_application表中
 			json.put("result", "0");
 			printWriter = response.getWriter();
 			printWriter.write(json.toString());
 			printWriter.flush();
 		} catch (Exception e) {
 			logger.error("修改失败", e);
 		} 
 	}
	
	/**
	 * 我的申请单
	 * @param request
	 * @param response
	 * @return
	 * @author 尹笑
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/myApplyList")
	public String myApplyList(HttpServletRequest request, HttpServletResponse response){
		String userId=SessionInfo.getLoginInfo(request).getLoginName();
		String page = request.getParameter("page");
		String softName=request.getParameter("softName");
		String startDate=request.getParameter("startDate");
		String endDate=request.getParameter("endDate");
		String appStatus=request.getParameter("appStatus");
		String orderCond=request.getParameter("orderCond");
		String orderType=request.getParameter("orderType");
		if(softName!=null && !"".equals(softName)){
    		try {
    			softName= URLDecoder.decode(softName,"UTF-8");
    		} catch (Exception e) {
    			logger.error("softName字符编码转换异常",e);
    		}
		}
		int pageSize = 5; //每页显示的行数
		int totalRecord = 0; //总行数
		Map paramMap = new HashMap();
    	paramMap.put("userId", userId);
    	paramMap.put("softName", softName);
    	paramMap.put("startDate", startDate);
    	paramMap.put("endDate", endDate);
    	paramMap.put("appStatus", appStatus);
    	paramMap.put("pageSize",pageSize);
    	paramMap.put("orderCond",orderCond);
    	if(orderCond!=null && !"".equals(orderCond)){
			if(orderCond.equals("software_type")){
				paramMap.put("orderCond","b."+orderCond);
			}
		}
    	paramMap.put("orderType",orderType);
    	totalRecord = softwareService.sumMyApply(paramMap);
		PageModel pageModel = PageModel.newPageModel(pageSize, page, totalRecord);
		paramMap.put("startRow", pageModel.getStartRow());
		List<ApplyInfo> dataList = new ArrayList<ApplyInfo>();
		dataList = softwareService.applySelect(paramMap);
		for(int i=0;i<dataList.size();i++){   //从查询结果中获取截取version字段
			dataList.get(i).setSoftVersion(StringUtil.versionSplit(dataList.get(i).getSoftVersion()));
			//if(dataList.get(i).getApplyTime()!=null && !"".equals(dataList.get(i).getApplyTime())){
				//dataList.get(i).setApplyTime(dataList.get(i).getApplyTime().substring(0, 9));
			//}
		}
		if (dataList != null && dataList.size() >0) {
		    for (ApplyInfo applyInfo : dataList) {
		        if (applyInfo.getApplyType().equals(Constants.APPLICATION_TYPE.QYSQ)) {
		            applyInfo.setApplyType("签约申请");
		        } else if (applyInfo.getApplyType().equals(Constants.APPLICATION_TYPE.XJSQ)) {
		            applyInfo.setApplyType("下架申请");
		        } else if (applyInfo.getApplyType().equals(Constants.APPLICATION_TYPE.SJSQ)) {
		            applyInfo.setApplyType("升级申请");
		        } else if (applyInfo.getApplyType().equals(Constants.APPLICATION_TYPE.BGSQ)) {
		            applyInfo.setApplyType("变更申请");
		        }
		    }
		}
		request.setAttribute("dataList", dataList);
		pageModel.setDataList(dataList);
		request.setAttribute("pageModel", pageModel);
		request.setAttribute("currentPage",pageModel.getCurrentPage());
		request.setAttribute("totalPage", pageModel.getTotalPage());
		request.setAttribute("cond_softName", softName);
		request.setAttribute("cond_appStatus", appStatus);
		request.setAttribute("cond_startDate", startDate);
		request.setAttribute("cond_endDate", endDate);
		request.setAttribute("orderCond", orderCond);
		request.setAttribute("orderType", orderType);
		
		return "software/myApplyList";
	}
	
	
	/**
	 * softTypeTree: <br/>
	 *
	 * @author zhangyichi
	 * @param request
	 * @param response
	 * @throws IOException
	 * @since JDK 1.6
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/softType")
	public void softTypeTree(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		List list=new ArrayList();
		List<SoftwareType> templist=softwareTypeSerivce.findSoftwareTypeByParent("-1");
		for(int i=0;i<templist.size();i++)
		{
			SoftwareType software=(SoftwareType)templist.get(i);
			 TreeNode node=new TreeNode();
			 node.setId(String.valueOf(software.getServiceTypeId()));
			 node.setText(software.getServiceTypeName());
			 node.setChildren(getDeptTreeList(software.getServiceTypeId()));
			 list.add(node);
		}
		String str =JSONArray.fromObject(list).toString();
		System.out.println(str);
	    response.setContentType("text/html;charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(str);
		printWriter.flush();
		printWriter.close();
	}
	public List<TreeNode> getDeptTreeList(String father_node)
	{
		List<TreeNode> list=new ArrayList<TreeNode>();
		
		List<SoftwareType> tempList=softwareTypeSerivce.findSoftwareTypeByParent(father_node);
		for(int i=0;i<tempList.size();i++)
		{
			SoftwareType software=(SoftwareType)tempList.get(i);
			 TreeNode node=new TreeNode();
			 node.setId(String.valueOf(software.getServiceTypeId()));
			 node.setText(software.getServiceTypeName());
			 node.setChildren(getDeptTreeList(software.getServiceTypeId()));
			 list.add(node);
		}
		return list;
	}
	
	/**
	 * deleteSoftware: <br/>
	 * 删除软件<br/>
	 *
	 * @author zhangyichi
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * @since JDK 1.6
	 */
    @RequestMapping(value = "/deleteSoftware")
	public void deleteSoftware(HttpServletRequest request, HttpServletResponse response ) throws Exception {
        String softwareId = request.getParameter("softId");
	    try {
            softwareService.deleteSoftware(Integer.parseInt(softwareId));
            applyOddService.deleteApplyOdd(Integer.parseInt(softwareId));
        } catch (Exception e) {
            logger.error(e);
            throw new Exception();
        }
	}
	
    //申请单详情和软件详情
	@SuppressWarnings("rawtypes")
    @RequestMapping(value="/applyDetail")
	public String applyDetail(HttpServletRequest request, HttpServletResponse response)
	{
		String applyId=request.getParameter("applyId");
		String softIdString=request.getParameter("softId");
		String softData;
		String baseUrl;
		
		if(applyId !=null && !"".equals(applyId)){			
			ApplyBasic applyBasic=softwareService.applyDetail(applyId);
			if (applyBasic.getApplyType().equals(Constants.APPLICATION_TYPE.QYSQ)) {
				applyBasic.setApplyType("签约申请");
	        } else if (applyBasic.getApplyType().equals(Constants.APPLICATION_TYPE.XJSQ)) {
	        	applyBasic.setApplyType("下架申请");
	        } else if (applyBasic.getApplyType().equals(Constants.APPLICATION_TYPE.SJSQ)) {
	        	applyBasic.setApplyType("升级申请");
	        } else if (applyBasic.getApplyType().equals(Constants.APPLICATION_TYPE.BGSQ)) {
	        	applyBasic.setApplyType("变更申请");
	        }
			
			if (applyBasic.getApplyStatus().equals(Constants.APPLYODD_STATUS.INAPPLY)) {
				applyBasic.setApplyStatus("审核中");
	        } else if (applyBasic.getApplyStatus().equals(Constants.APPLYODD_STATUS.SUCCESSAPPLY)) {
	        	applyBasic.setApplyStatus("审核成功");
	        } else if (applyBasic.getApplyStatus().equals(Constants.APPLYODD_STATUS.FAILAPPLY)) {
	        	applyBasic.setApplyStatus("审核失败");
	        }
			request.setAttribute("applyBasic", applyBasic);
		}
		Software softBasic;
		if(applyId !=null && !"".equals(applyId)){
			int softId=Integer.parseInt(softIdString);
			softBasic=softwareService.findSoftwareByid2(softId);
			softData=GetJsonUtil.getJson(softBasic);
		}else{
			softBasic=softwareService.findSoftwareByid(softIdString);
			softData=GetJsonUtil.getJson(softBasic);			
		}
		baseUrl=request.getContextPath()+"/upload/software/"+softBasic.getHandleId()+"/";
		softBasic.setOperateSystem(softBasic.getOperateSystem().substring(softBasic.getOperateSystem().lastIndexOf(",")+1));
		softBasic.setGhoName(StringUtil.mirrorNameSplit(softBasic.getGhoName())); //截取镜像字段
		softBasic.setSoftwareVersions(StringUtil.versionSplit(softBasic.getSoftwareVersions()));//截取版本字段
		
		Map<String,String> mapSoftwareType = new HashMap<String,String>();       //软件类型翻译
		mapSoftwareType =  DicUtil.getAllSoftTypeDic();
		Iterator iter = mapSoftwareType.entrySet().iterator(); 
		while (iter.hasNext()) {
		    Map.Entry entry = (Map.Entry) iter.next(); 
		    String key = entry.getKey().toString(); 
		    String val = entry.getValue().toString();
			if(key.equals(softBasic.getSoftwareType())){			    	
				softBasic.setSoftwareType(val);			    	
		    }					    
		}	
		try {
			JSONObject jsonObject = new JSONObject(softData);
			String stringArray = jsonObject.get("producttype").toString();
			JSONArray jsonArray = JSONArray.fromObject(stringArray);
			List<VmData> vminfolist = new ArrayList<VmData>();
			for(int i=0;i<jsonArray.size();i++){
				String stringData=jsonArray.getJSONObject(i).toString();
				JSONObject jsonData=new JSONObject(stringData);
				VmData vmData = new VmData();
				vmData.setProductType(jsonData.get("name").toString());//产品类型
				vmData.setCpuValue(jsonData.get("value").toString());//cpu
				vmData.setMonthPrices(jsonData.get("monthprice").toString());
				vmData.setYearPrices(jsonData.get("yearprice").toString());
				if(!softBasic.getSoftwareKind().equals("1")){  //如果不是基本软件就多一列软件规格
					vmData.setProductEtalon(jsonData.getString("value").toString());
				}
				
				JSONObject  jsonVm=jsonData.getJSONObject("vm");					
				vmData.setName(jsonVm.get("name").toString());
				vmData.setDesc(jsonVm.get("desc").toString());//内存
				vmData.setValue(jsonVm.get("value").toString());//内存					
				vmData.setOsDisk(jsonVm.get("osDisk").toString());//硬盘
				
				vminfolist.add(vmData);	
				
				request.setAttribute("vminfolist", vminfolist);
			}
			
		} catch (JSONException e) {
		    logger.error(e);
		}
		
		String[] softwareData = softBasic.getSoftwareData().split("\\|");
		request.setAttribute("serviceData", softwareData[2]);
		
		request.setAttribute("softBasic", softBasic);
		request.setAttribute("baseUrl", baseUrl);
		if(applyId !=null && !"".equals(applyId)){
			request.setAttribute("tag", "1");
		}else{
			request.setAttribute("tag", "0");
		}
		return "software/applyDetail";
	}
	   /**
     * download: <br/>
     * 文件下载<br/>
     * 
     * @author zhangjl
     * @param request
     * @param response
     * @since JDK 1.6
     */
    @RequestMapping(value = "/download")
    public void download(String downName, HttpServletRequest request,
            HttpServletResponse response) {
        try {
            String separator = File.separator;
            // 登陆名称
            String userId = SessionInfo.getLoginInfo(request).getLoginName();
            // 文件下载路径
            String downUrl = "";
            
            // 判断当前系统为linux还是windows
            if ("\\".equals(File.separator)) {
                downUrl = "d:" + separator + "upload" + separator + "software"+ separator + userId + separator;
                // is WINDOWS
            } else if ("/".equals(File.separator)) {
                downUrl = "home" + separator + "upload" + separator + "software"+ separator + userId + separator;
                // is LINUX
            }
            // 下载文件名称
            downName = URLDecoder.decode(downName, "utf-8");
            String path = downUrl + downName;
            // path是指欲下载的文件的路径。
            File file = new File(path);
            // 取得文件名。
            String filename = new String(file.getName().getBytes("GB2312"), "ISO_8859_1");
            //String filename = file.getName();
            filename = StringUtils.replace(filename, " ", ""); 
            // 取得文件的后缀名。
            String ext = filename.substring(filename.lastIndexOf(".") + 1)
                    .toUpperCase();
            // 设置下载文件的类型为任意类型
            response.setContentType("application/x-msdownload");
            // 添加下载文件的头信息。此信息在下载时会在下载面板上显示，比如：
            // 迅雷下载显示的文件名称，就是此处filiname
            response.addHeader("Content-Disposition", "attachment;filename="+  filename);
            // 添加文件的大小信息
            response.setContentLength((int) file.length());
            // 获得输出网络流
            ServletOutputStream sos = response.getOutputStream();
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int i = 0;
            while ((i = fis.read(buffer)) != -1) {
                sos.write(buffer, 0, i);
                sos.flush();
            }
            sos.close();
            fis.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
	/**
     * toUpdateSoftware: <br/>
     * 跳转到软件变更页面<br/>
     *
     * @author zhangyichi
     * @return
     * @since JDK 1.6
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping("/toUpdateSoftware")
    public String toUpdateSoftware(Software software, HttpServletRequest request, HttpServletResponse response) {
        String userId=SessionInfo.getLoginInfo(request).getLoginName();
        String urlOs = null;
        try {
            urlOs = GetInterfacesUrlUtils.getInterfacesUrl("osUrl");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("操作系统胡url不存在或者有误",e);
        }
        OsTemplate osTemplate = new OsTemplate();
        List<VmInfo> vmInfoList = new ArrayList<VmInfo>();
        List<SoftwareType> softwareTypes = new ArrayList<SoftwareType>();
        int id = software.getId();
        Software isSoftware =  softwareService.findSoftwareByid(id);
        softwareTypes = softwareTypeSerivce.findAllSoftwareType();
        
        //获取操作系统
        String[] osDatas = isSoftware.getOperateSystem().split(",");
        String osId = osDatas[0];
        
        String result = QueryOsData.readContentFromGet(urlOs);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(result);
        } catch (JSONException e) {
            logger.error("new Json对象异常！",e);
        }
        JSONObject jsonData = null;
        try {
            jsonData = (JSONObject) jsonObject.get("listtemplatesresponse");
        } catch (JSONException e) {
            logger.error("未创建以 listtemplatesresponse 的json对象",e);
        }
        String template = null;
        try {
            template = jsonData.get("template").toString();
        } catch (JSONException e) {
            logger.error("未能以 template 创建json对象",e);
        }
        List osTemplates = JsonToObj.jsonUtil(template, osTemplate);
        //获取虚拟机信息
        List<VmData> vmDatas = new ArrayList<VmData>();
        String[] softwareData = isSoftware.getSoftwareData().split("\\|");
        String vm = "";
        String productid="";
        String name = "";
        String cpuVal="";
        String value ="";
        String onDisk="";
        String desc="";
        String[] softwareDataTemp=softwareData[0].split(";");
        int rowCount = softwareDataTemp.length;  //行数
        for(int i=0;i<rowCount;i++){
            String[] vmInfos = softwareDataTemp[i].split("/");
            String[] vmInfo = vmInfos[0].split(",");
            VmData vmd = new VmData();
            if(isSoftware.getSoftwareKind().equals("1")){
                vm=vmInfo[1];
                String[] temp = vm.split(":");
                productid = temp[0];
                String emp = temp[1];   
                String[] amp = emp.split("：");
                //name = amp[0];
                cpuVal=amp[1].split("、")[0];
                name=amp[1].split("、")[1];
                onDisk=amp[1].split("、")[2];
                desc=vmInfos[1];
                vmd.setCpuValue(cpuVal);
                vmd.setDesc(desc);
                vmd.setName(name);
                vmd.setOsDisk(onDisk);
                vmd.setProductId(productid);
                vmd.setValue(value);
                vmd.setProductType(vmInfo[0]);
                vmd.setMonthPrices(vmInfo[2]);
                vmd.setYearPrices(vmInfo[3]);
                vmDatas.add(vmd);
            }else{
                vm=vmInfo[2];
                String[] temp = vm.split(":");
                productid = temp[0];
                String emp = temp[1];   
                String[] amp = emp.split("：");
                //name = amp[0];
                cpuVal=amp[1].split("、")[0];
                name=amp[1].split("、")[1];
                onDisk=amp[1].split("、")[2];
                desc=vmInfos[1];
                vmd.setCpuValue(cpuVal);
                vmd.setDesc(desc);
                vmd.setName(name);
                vmd.setOsDisk(onDisk);
                vmd.setProductId(productid);
                vmd.setValue(value);
                vmd.setProductType(vmInfo[0]);
                vmd.setProductEtalon(vmInfo[1]);
                vmd.setMonthPrices(vmInfo[3]);
                vmd.setYearPrices(vmInfo[4]);
                vmDatas.add(vmd);
            }
        }
        vmInfoList= vmService.findAllVmInfo();
        String json = vmInfoList.get(0).getConfig();
        JSONObject jsonvms = null;
        try {
            jsonvms = new JSONObject(json);
        } catch (JSONException e) {
            logger.error("字符串转json对象异常！",e);
        }
        String jsonvm = null;
        try {
            jsonvm = jsonvms.get("vmConfig").toString();
        } catch (JSONException e) {
            logger.error("获取 vmConfig 异常！",e);
        }
        JSONArray jsonArr =JSONArray.fromObject(jsonvm);
        List<VmData> vminfolist = new ArrayList<VmData>();
        for(int i=0;i<jsonArr.size();i++){
            String memory = jsonArr.getJSONObject(i).toString();
            JSONObject jsb = null;
            try {
                jsb = new JSONObject(memory);
            } catch (JSONException e) {
                logger.error("以字符串vmConfig 创建json对象异常",e);
            }
            String cpuValue = null;
            try {
                cpuValue = jsb.get("value").toString();
            } catch (JSONException e) {
                logger.error("获取 value 字符串异常！",e);
            }
            String jsonString = null;
            try {
                jsonString = jsb.getString("memory").toString();
            } catch (JSONException e) {
                logger.error("以 memory 获取字符串异常！",e);
            }
            JSONArray jsonArray = JSONArray.fromObject(jsonString);
            for(int j=0;j<jsonArray.size();j++){
                VmData vmData = new VmData();
                vmData.setCpuValue(cpuValue);
                String memorydata = jsonArray.getJSONObject(j).toString();
                
                JSONObject jsonMemory = null;
                try {
                    jsonMemory = new JSONObject(memorydata);
                } catch (JSONException e) {
                    logger.error("以字符串 jsonMemory 创建json对象异常！",e);
                }
                String productId = null;
                try {
                    productId = jsonMemory.get("productId").toString();
                } catch (JSONException e) {
                    logger.error("获取 productId 字符串异常",e);
                }
                if(!(productId.equals(""))){
                    try {
                        vmData.setDesc(jsonMemory.get("desc").toString());
                    } catch (JSONException e) {
                        logger.error("desc 获取字符串异常",e);
                    }
                    try {
                        vmData.setName(jsonMemory.get("name").toString());
                    } catch (JSONException e) {
                        logger.error("name 获取字符串异常",e);
                    }
                    try {
                        vmData.setOsDisk(jsonMemory.get("osDisk").toString());
                    } catch (JSONException e) {
                        logger.error("osDisk 获取字符串异常",e);
                    }
                    vmData.setProductId(productId);
                    try {
                        vmData.setVmPrice(jsonMemory.get("vmPrice").toString());
                    } catch (JSONException e) {
                        logger.error("vmPrice 获取字符串异常",e);
                    }
                    try {
                        vmData.setValue(jsonMemory.get("value").toString());
                    } catch (JSONException e) {
                        logger.error("value 获取字符串异常",e);
                    }
                    
                    vminfolist.add(vmData);
                }
            }
        }
        request.setAttribute("softwareKind", isSoftware.getSoftwareKind());
        
        //配置显示数据
        if (isSoftware.getSoftwareKind().equals("1")) {
            isSoftware.setSoftwareKind("基础软件");
        } else if (isSoftware.getSoftwareKind().equals("2")) {
            isSoftware.setSoftwareKind("应用软件");
        } else if (isSoftware.getSoftwareKind().equals("3")) {
            isSoftware.setSoftwareKind("服务软件");
        }
        Map<String,String> mapSoftwareType = new HashMap<String,String>();
        mapSoftwareType =  DicUtil.getAllSoftTypeDic();
        Iterator iter = mapSoftwareType.entrySet().iterator(); 
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next(); 
            String key = entry.getKey().toString(); 
            String val = entry.getValue().toString();
            if(key.equals(isSoftware.getSoftwareType())){                    
                isSoftware.setSoftwareType(val);                 
            }                       
        }
        isSoftware.setOperateSystem(isSoftware.getOperateSystem().substring(isSoftware.getOperateSystem().lastIndexOf(",")+1));
        isSoftware.setSoftwareVersions(isSoftware.getSoftwareVersions().split("\\|")[0]);       
        isSoftware.setGhoName((isSoftware.getGhoName().split("\\|")[0]).split(",")[1]);     
        
        String baseUrl=request.getContextPath()+"/upload/software/"+isSoftware.getHandleId()+"/";
        
        request.setAttribute("baseUrl", baseUrl);
        request.setAttribute("serviceData", softwareData[2]);
        request.setAttribute("vmData", vminfolist);
        request.getSession().setAttribute("vmData", vminfolist);
        request.setAttribute("osTemplates", osTemplates);
        request.getSession().setAttribute("userId", userId);
        request.getSession().setAttribute("osTemplates", osTemplates);
        request.setAttribute("softwareTypes", softwareTypes);
        request.setAttribute("vmDatas", vmDatas);
        request.setAttribute("isSoftware", isSoftware);
        request.setAttribute("osId", osId);
        return "software/updateSoftwareApply";
    }
    
    /**
     * updateSoftware: <br/>
     * 变更软件信息<br/>
     *
     * @author zhangyichi
     * @param request
     * @param response
     * @since JDK 1.6
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/updateSoftware")
    public void updateSoftware(Software software, HttpServletRequest request, HttpServletResponse response) {
        String separator=File.separator;
        String POST_URL = GetInterfacesUrlUtils.getInterfacesUrl("vmUrl");
        String userId=SessionInfo.getLoginInfo(request).getLoginName();
        software.setHandleId(userId);
        String basePath = "/upload/software/"+software.getHandleId()+"/";
        ApplyOdd applyOdd = new ApplyOdd();
        String softwareBaseUrl = request.getSession().getServletContext().getRealPath(separator+"upload"+separator+"software"+separator+software.getHandleId()+separator);
        int id =software.getId();
        Software isSoftware =  softwareService.findSoftwareByid(id);
        //复原部分属性
        software.setProcessType(Integer.parseInt(Constants.APPLICATION_TYPE.BGSQ));
        software.setSoftwareType(isSoftware.getSoftwareType());
        software.setSoftwareVersions(isSoftware.getSoftwareVersions());
        software.setOperateSystem(isSoftware.getOperateSystem());
        software.setGhoName(isSoftware.getGhoName());
        software.setWoCloudUserName(isSoftware.getWoCloudUserName());
        software.setSoftwareKind(isSoftware.getSoftwareKind());
        software.setCommandTime(DateUtil.nowDateEn());
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        String[] urlArray = new String[4];
        if(multipartResolver.isMultipart(request)){
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
            MultipartFile file1 =  (MultipartFile) multiRequest.getFile("myfile1");
            MultipartFile file2 =  (MultipartFile) multiRequest.getFile("myfile2");
            MultipartFile file3 =  (MultipartFile) multiRequest.getFile("myfile3");
            MultipartFile file6 =  (MultipartFile) multiRequest.getFile("myfile6");
            try {
                if(file1!=null){
                    FileUtils.copyInputStreamToFile(file1.getInputStream(), new File(softwareBaseUrl, file1.getOriginalFilename()));
                    software.setSoftwareImgUrl(basePath+file1.getOriginalFilename());
                    urlArray[0] = file1.getOriginalFilename();
                } else {
                	if(isSoftware.getSoftwareImgUrl()!=null && !"".equals(isSoftware.getSoftwareImgUrl())){
                		software.setSoftwareImgUrl(basePath+isSoftware.getSoftwareImgUrl());
                	}
                    urlArray[0] = isSoftware.getSoftwareImgUrl();
                }
                if(file2!=null){
                    FileUtils.copyInputStreamToFile(file2.getInputStream(), new File(softwareBaseUrl, file2.getOriginalFilename()));
                    software.setSoftwareSpecificationUrl(basePath+file2.getOriginalFilename());
                    urlArray[1] = file2.getOriginalFilename();
                } else {
                	if(isSoftware.getSoftwareSpecificationUrl()!=null && !"".equals(isSoftware.getSoftwareSpecificationUrl())){
                		software.setSoftwareSpecificationUrl(basePath+isSoftware.getSoftwareSpecificationUrl());
                	}
                    urlArray[1] = isSoftware.getSoftwareSpecificationUrl();
                }
                if(file3!=null){
                    FileUtils.copyInputStreamToFile(file3.getInputStream(), new File(softwareBaseUrl, file3.getOriginalFilename()));
                    software.setSoftwareConfigSpecificationUrl(basePath+file3.getOriginalFilename());
                    urlArray[2] = file3.getOriginalFilename();
                } else {
                	if(isSoftware.getSoftwareConfigSpecificationUrl()!=null && !"".equals(isSoftware.getSoftwareConfigSpecificationUrl())){
                		software.setSoftwareConfigSpecificationUrl(basePath+isSoftware.getSoftwareConfigSpecificationUrl());
                	}
                    urlArray[2] =isSoftware.getSoftwareConfigSpecificationUrl();
                }
                if(file6!=null){
                    FileUtils.copyInputStreamToFile(file6.getInputStream(), new File(softwareBaseUrl, file6.getOriginalFilename()));
                    software.setSoftwareSellAuthorizationUrl(basePath+file6.getOriginalFilename());
                    urlArray[3] = file6.getOriginalFilename();
                } else {
                	if(isSoftware.getSoftwareSellAuthorizationUrl()!=null && !"".equals(isSoftware.getSoftwareSellAuthorizationUrl())){
                		software.setSoftwareSellAuthorizationUrl(basePath+isSoftware.getSoftwareSellAuthorizationUrl());
                	}
                    urlArray[3] = isSoftware.getSoftwareSellAuthorizationUrl();
                }
            } catch (IOException e) {
                logger.error(e);
            }
        }
        List<VmData> vmDatas = new ArrayList<VmData>();
        vmDatas = (List<VmData>) request.getSession().getAttribute("vmData");
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
        software.setSoftwareStatus(Constants.SoftStatus.NEW_INCREASE);
        software.setSoftwareId(softwareId);
        software.setSoftwareData(softwareData);
        String backResult ="";
        String softwareJsonData = GetJsonUtil.getJson(software);
        try{
            backResult = PostJson.readContentFromPost(POST_URL, softwareJsonData);
            logger.info(backResult);
        }catch(Exception e){
            logger.error(backResult,e);
            this.responseFailed(response, "提交审核失败，请稍后再试！", null);
        }
        
        software.setSoftwareStatus(Constants.SoftStatus.TO_VERIFY);
        software.setSoftwareImgUrl(urlArray[0]);
        software.setSoftwareSpecificationUrl(urlArray[1]);
        software.setSoftwareConfigSpecificationUrl(urlArray[2]);
        software.setSoftwareSellAuthorizationUrl(urlArray[3]);
        
        softwareService.updateSoftware(software);
        software.setCommandTime(dateStr);
        softwareService.insertSoftwareHis(software);
        int historyId = softwareService.queryHistoryId(softwareId);
        applyOdd.setApplyOddType(Constants.APPLICATION_TYPE.BGSQ);  //申请单类型
        applyOdd.setSoftwareId(historyId);
        applyOdd.setApplyOddStatus(Constants.APPLYODD_STATUS.INAPPLY);//申请单状态
        applyOdd.setApplyOddSuggest("");
        applyOdd.setApplyOddTime(dateStr);
        applyOddService.insertApplyOdd(applyOdd);//将申请单存入pm_application表中
        this.responseSuccess(response, "提交审核成功！", null);
    }
    
    /**
     * toUpgradeSoftware: <br/>
     * 跳转到升级软件信息页面<br/>
     *
     * @author yinxiao
     * @param request
     * @param response
     * @since JDK 1.6
     */
    @SuppressWarnings("all")
    @RequestMapping("/toUpgradeSoftware")
    public String toUpgradeSoftware(HttpServletRequest request, HttpServletResponse response) {
    	String softIdString=request.getParameter("softId");
    	int softId=Integer.parseInt(softIdString);
    	Software softBasic=softwareService.findSoftwareByid(softId);
    	String softData=GetJsonUtil.getJson(softBasic); 
    	
    	softBasic.setOperateSystem(softBasic.getOperateSystem().substring(softBasic.getOperateSystem().lastIndexOf(",")+1));
		String versionSplit=StringUtil.versionSplit(softBasic.getSoftwareVersions());
		String filename1=softBasic.getSoftwareSpecificationUrl();
		String filename2=softBasic.getSoftwareConfigSpecificationUrl();		
		String filename3=softBasic.getSoftwareSellAuthorizationUrl();
		
		Map<String,String> mapSoftwareType = new HashMap<String,String>();
		mapSoftwareType =  DicUtil.getAllSoftTypeDic();
		Iterator iter = mapSoftwareType.entrySet().iterator(); 
		while (iter.hasNext()) {
		    Map.Entry entry = (Map.Entry) iter.next(); 
		    String key = entry.getKey().toString(); 
		    String val = entry.getValue().toString();
			if(key.equals(softBasic.getSoftwareType())){			    	
				softBasic.setSoftwareType(val);			    	
		    }					    
		}	
		
		List<Map<String,String>> versionAndGohlist = new ArrayList<Map<String,String>>();
		String[] softwareVersionArray = (softBasic.getSoftwareVersions()).split("\\|");
		String[] ghoNameArray = (softBasic.getGhoName()).split("\\|");
		for(int i=0;i<softwareVersionArray.length;i++){		//从镜像字段提取镜像列表
			Map<String,String> versionAndGohMap=new HashMap<String,String>();
			versionAndGohMap.put("version", softwareVersionArray[i]);
			versionAndGohMap.put("gohName", ghoNameArray[i].split(",")[1]);
			versionAndGohlist.add(versionAndGohMap);					
		}
		
		try {
			JSONObject jsonObject = new JSONObject(softData);
			String stringArray = jsonObject.get("producttype").toString();
			JSONArray jsonArray = JSONArray.fromObject(stringArray);
			List vminfolist = new ArrayList();
			for(int i=0;i<jsonArray.size();i++){
				String stringData=jsonArray.getJSONObject(i).toString();
				JSONObject jsonData=new JSONObject(stringData);
				VmData vmData = new VmData();
				vmData.setProductType(jsonData.get("name").toString());//产品类型
				vmData.setCpuValue(jsonData.get("value").toString());//cpu
				vmData.setMonthPrices(jsonData.get("monthprice").toString());
				vmData.setYearPrices(jsonData.get("yearprice").toString());
				if(!softBasic.getSoftwareKind().equals("1")){
					vmData.setProductEtalon(jsonData.getString("value").toString());
				}
				
				JSONObject  jsonVm=jsonData.getJSONObject("vm");					
				vmData.setName(jsonVm.get("name").toString());
				vmData.setDesc(jsonVm.get("desc").toString());//内存
				vmData.setValue(jsonVm.get("value").toString());//内存					
				vmData.setOsDisk(jsonVm.get("osDisk").toString());//硬盘
				
				vminfolist.add(vmData);	
				
				request.setAttribute("vminfolist", vminfolist);
			}
			
		} catch (JSONException e) {
		    logger.error(e);
			e.printStackTrace();
		}
		
		List mirrorlist = new ArrayList();	//供用户选择的镜像列表	
		try {
			String customerId=softwareService.getCustId(SessionInfo.getLoginInfo(request).getLoginName()+"@softwareProvider");
			//String customerId=softwareService.getCustId("admin_wo@softwareProvider");
			String result = RestRequestUtil.getMirror(customerId);//获取用户可供选择镜像列表
			JSONObject jsonObject = new JSONObject(result);
			if(jsonObject.get("code").toString().equals("0")){
				String stringArray = jsonObject.get("data").toString();
				JSONArray jsonArray = JSONArray.fromObject(stringArray);
				for(int i=0;i<jsonArray.size();i++){
					String stringData=jsonArray.getJSONObject(i).toString();
					JSONObject jsonData=new JSONObject(stringData);
					Map mirrorMap=new HashMap();
					mirrorMap.put("imageid", jsonData.get("imageId").toString());
					mirrorMap.put("imagename", jsonData.get("imageName").toString());
					
					mirrorlist.add(mirrorMap);					
					request.setAttribute("mirrorlist", mirrorlist);
				}
			}
		} catch (JSONException e) {
		    logger.error(e);
			e.printStackTrace();
		}catch(Exception e){
		    logger.error(e);
			e.printStackTrace();
		}
			
		String baseUrl=request.getContextPath()+"/upload/software/"+softBasic.getHandleId()+"/";
        
        request.setAttribute("baseUrl", baseUrl);
		request.setAttribute("softBasic", softBasic);
		request.setAttribute("filename1", filename1);
		request.setAttribute("filename2", filename2);
		request.setAttribute("filename3", filename3);
		request.setAttribute("versionSplit", versionSplit);
		request.setAttribute("versionAndGohlist", versionAndGohlist);
		return "software/upgradeSoftwareApply";
    }
    
    /**
     * upgradeSoftware: <br/>
     * 升级软件信息<br/>
     *
     * @author yinxiao
     * @param request
     * @param response
     * @since JDK 1.6
     */
    @RequestMapping("/upgradeSoftware")
    public void upgradeSoftware(HttpServletRequest request, HttpServletResponse response) {
    	SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = dateformat.format(new Date());
		
    	String userId=SessionInfo.getLoginInfo(request).getLoginName();
    	String softwareIntroduce = request.getParameter("softwareIntroduce");
    	String softwareVersion=request.getParameter("softwareVersion");
    	String softId = request.getParameter("softId");
    	String softwareConfigUrl=request.getParameter("softwareConfigUrl");//镜像id
    	String mirrorName=request.getParameter("mirrorName");//镜像名称
    	
    	Software softBasic=softwareService.findSoftwareByid(Integer.parseInt(softId));
    	String backResult = "";
    	String POST_URL="";
    	JSONObject json = new JSONObject();
    	ApplyOdd applyOdd = new ApplyOdd();
    	
    	String[] urlArray = new String[4];
    	//文件上传
		String separator=File.separator;
		String basePath = "/upload/software/"+userId+"/";
    	String softwareBaseUrl = request.getSession().getServletContext().getRealPath(separator+"upload"+separator+"software"+separator+userId+separator);
    	CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		if(multipartResolver.isMultipart(request)){
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
			MultipartFile file1 =  (MultipartFile) multiRequest.getFile("myfile1");
			MultipartFile file2 =  (MultipartFile) multiRequest.getFile("myfile2");
			MultipartFile file3 =  (MultipartFile) multiRequest.getFile("myfile3");
			
			try {
				if(file1!=null){
					FileUtils.copyInputStreamToFile(file1.getInputStream(), new File(softwareBaseUrl, file1.getOriginalFilename()));
					softBasic.setSoftwareSpecificationUrl(basePath+file1.getOriginalFilename());
					urlArray[0] = file1.getOriginalFilename();
                } else {
                	urlArray[0] = softBasic.getSoftwareSpecificationUrl();
                	if(softBasic.getSoftwareSpecificationUrl()!=null && !"".equals(softBasic.getSoftwareSpecificationUrl())){
                		softBasic.setSoftwareSpecificationUrl(basePath+softBasic.getSoftwareSpecificationUrl());
                	}
                }
				if(file2!=null){
					FileUtils.copyInputStreamToFile(file2.getInputStream(), new File(softwareBaseUrl, file2.getOriginalFilename()));
					softBasic.setSoftwareConfigSpecificationUrl(basePath+file2.getOriginalFilename());
					urlArray[1] = file2.getOriginalFilename();
                } else {
                	urlArray[1] = softBasic.getSoftwareConfigSpecificationUrl();
                	if(softBasic.getSoftwareConfigSpecificationUrl()!=null && !"".equals(softBasic.getSoftwareConfigSpecificationUrl())){
                		softBasic.setSoftwareConfigSpecificationUrl(basePath+softBasic.getSoftwareConfigSpecificationUrl());  
                	}
                }
				if(file3!=null){
					FileUtils.copyInputStreamToFile(file3.getInputStream(), new File(softwareBaseUrl, file3.getOriginalFilename()));
					softBasic.setSoftwareSellAuthorizationUrl(basePath+file3.getOriginalFilename());
					urlArray[2] = file3.getOriginalFilename();
                } else {
                	urlArray[2] = softBasic.getSoftwareSellAuthorizationUrl();
                	if(softBasic.getSoftwareSellAuthorizationUrl()!=null && !"".equals(softBasic.getSoftwareSellAuthorizationUrl())){
                		softBasic.setSoftwareSellAuthorizationUrl(basePath+softBasic.getSoftwareSellAuthorizationUrl());  
                	}	
                }
			} catch (IOException e) {
				e.printStackTrace();
				logger.error(e);
			}				
		}
		urlArray[3] = softBasic.getSoftwareImgUrl();
		if(softBasic.getSoftwareImgUrl()!=null && !"".equals(softBasic.getSoftwareImgUrl())){
			softBasic.setSoftwareImgUrl(basePath+softBasic.getSoftwareImgUrl());
    	}
        
		softBasic.setSoftwareIntroduce(softwareIntroduce);
		softBasic.setSoftwareVersions(softwareVersion+"|"+softBasic.getSoftwareVersions());
		softBasic.setGhoName(softwareConfigUrl+","+mirrorName+"|"+softBasic.getGhoName());
		softBasic.setProcessType(Integer.parseInt(Constants.APPLICATION_TYPE.SJSQ));
		
		SimpleDateFormat softwareIdDate = new SimpleDateFormat("yyyyMMddhhmmss");
		String softwareId = softwareIdDate.format(new Date());
		softBasic.setSoftwareId(softwareId);
		
		String softwareData = GetJsonUtil.getJson(softBasic); //将software的数据进行拼装成一个json数据
		//softBasic.setSoftwareData(softwareData);
		String commSoftwareStatus = Constants.SoftStatus.TO_VERIFY;
		softBasic.setSoftwareStatus(commSoftwareStatus);
		
			
		/*try {
			JSONObject jsonObject = new JSONObject(softwareData);
			String result = RestRequestUtil.sendApply(jsonObject);
			logger.info("接口返回"+result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		try {
			POST_URL = GetInterfacesUrlUtils.getInterfacesUrl("vmUrl");
		} catch (Exception e) {
			logger.error("获取虚拟机接口路径出错",e);
			e.printStackTrace();
			try {
				json.put("error", POST_URL);
			} catch (JSONException e1) {
				e1.printStackTrace();
				logger.error("json保存数据错误",e);
			}
			this.responseFailed(response, "系统异常请稍后再试！", json);
		}
		
		try {
			backResult = PostJson.readContentFromPost(POST_URL, softwareData);//将拼装好的json数据发送给运营那边
			logger.error(backResult);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("调接口出错",e);
			
			try {
				json.put("backResult", backResult);
			} catch (JSONException e1) {
				e1.printStackTrace();
				logger.error("json保存数据异常",e);
			}
			this.responseFailed(response, "系统异常,数据提交审核失败，请在我的软件列表中再次申请", null);
		}
		softBasic.setSoftwareSpecificationUrl(urlArray[0]);
		softBasic.setSoftwareConfigSpecificationUrl(urlArray[1]);		
		softBasic.setSoftwareSellAuthorizationUrl(urlArray[2]);
		softBasic.setSoftwareImgUrl(urlArray[3]);
		
		softwareService.updateSoftware(softBasic);
		softBasic.setCommandTime(dateStr); 
		softwareService.insertSoftwareHis(softBasic);
		int historyId = softwareService.queryHistoryId(softwareId); //根据softwareId 获取到插入到history表中的数据的id，用于向appcation表中插入
		applyOdd.setApplyOddType(Constants.APPLICATION_TYPE.SJSQ);  //申请单类型
		applyOdd.setSoftwareId(historyId);
		applyOdd.setApplyOddStatus(Constants.APPLYODD_STATUS.INAPPLY);//申请单状态
		applyOdd.setApplyOddSuggest("");
		applyOdd.setApplyOddTime(dateStr);
		applyOddService.insertApplyOdd(applyOdd);//将申请单存入pm_application表中
		this.responseSuccess(response, "升级申请成功！", null);
    }
    
    
	/**
 	 * softwareReset: <br/>
 	 * 软件升级变更审核失败后恢复申请前信息
 	 * @param software
 	 * @param request
 	 * @param response
 	 * @since JDK 1.6
 	 */
 	@SuppressWarnings("all")
 	@RequestMapping("/softwareReset")
    public void softwareReset(HttpServletRequest request,HttpServletResponse response){
    	int selectId = Integer.parseInt(request.getParameter("selectId"));
    	JSONObject json = new JSONObject();
    	ApplyOdd applyOdd = new ApplyOdd();
    	PrintWriter printWriter = null;
    	try {
    		softwareService.softwareReset(selectId);   		
    		json.put("result", "0");
    		printWriter = response.getWriter();
            printWriter.write(json.toString());
            printWriter.flush();
            printWriter.close();
		} catch (Exception e) {
			logger.error("恢复失败", e);
			
		} 
    }
}
