package com.ai.wocloud.action;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ai.wocloud.bean.CompanyInfo;
import com.ai.wocloud.bean.LoginInfo;
import com.ai.wocloud.service.interfaces.AccountService;
import com.ai.wocloud.service.interfaces.CompanyInfoService;
import com.ai.wocloud.system.action.BaseAction;
import com.ai.wocloud.system.session.SessionInfo;
import com.ai.wocloud.system.util.PageModel;
import com.ai.wocloud.system.util.RestRequestUtil;

@Transactional(rollbackFor = Exception.class)
@Controller
@RequestMapping("/accountInfoAction")
public class AccountInfoAction extends BaseAction{
	private Logger logger = Logger.getLogger(AccountInfoAction.class);
	@Autowired
	private AccountService accountService;
    @Autowired
    private CompanyInfoService companyInfoService;
	//-------------------------跳转至账户总额
    @RequestMapping("/toAccountTotal")
    public String toAccountTotal(HttpServletRequest request){ 
    	System.out.println("123456");
    	JSONObject json = new JSONObject();
    	String userId=SessionInfo.getLoginInfo(request).getLoginName();
    	
    	LoginInfo loginInfo=new LoginInfo();
    	loginInfo.setId(SessionInfo.getCompanyInfo(request).getLoginId());
    	CompanyInfo companyInfo = companyInfoService.findCompanyInfoByLoginId(loginInfo);
    	String qymc = (companyInfo !=null && !"".equals(companyInfo))?companyInfo.getCompanyName():"";	//因为之前在向运营传递的sellerId是 企业名称
    	int upSoftNum=0;
    	try {
			json.put("sellerId", qymc);
			json.put("loginName", userId);
			String result = RestRequestUtil.getAccoutTotal(json);
			JSONObject resultJson = new JSONObject(result);
			String resultCode = resultJson.getString("result");
			logger.info(resultCode);
			if(resultCode.equals("success")){
				upSoftNum=accountService.getUpSoftNum(userId);
				request.setAttribute("upSoftNum", upSoftNum);
				request.setAttribute("selloutNums", resultJson.getString("selloutNums"));
				request.setAttribute("Balance", 
						(resultJson.getString("Balance") ==null || "".equals(resultJson.getString("Balance"))
						?"":(Double.valueOf(resultJson.getString("Balance"))/1000)));
				return "account/accountTotal";
			}						
		} catch (JSONException e) {
			logger.error(e);
		}catch(Exception e){
			logger.error(e);
		}    	
    	return "common/error";
    }
    
  //-------------------------跳转至销售明细
    @RequestMapping("/toAccountBalance")
    public String toAccountBalance(HttpServletRequest request){ 
    	JSONObject json = new JSONObject();

    	LoginInfo loginInfo=new LoginInfo();
    	loginInfo.setId(SessionInfo.getCompanyInfo(request).getLoginId());
    	CompanyInfo companyInfo = companyInfoService.findCompanyInfoByLoginId(loginInfo);
    	String qymc = (companyInfo !=null && !"".equals(companyInfo))?companyInfo.getCompanyName():"";	//因为之前在向运营传递的sellerId是 企业名称
    	
    	String page = request.getParameter("page");
    	String softName=request.getParameter("softName");
    	String startDate=request.getParameter("startDate");
    	String endDate=request.getParameter("endDate");
    	if(softName!=null && !"".equals(softName)){
    		try {
    			softName= URLDecoder.decode(softName,"UTF-8");
    		} catch (Exception e) {
    			logger.error("softName字符编码转换异常",e);
    		}
		}
    	int pageSize = 5; //每页显示的行数
    	int totalRecord = 0; //总行数
    	
    	String[] billTypeName={"包月","计时","包天","包年","计量","线下后付费用户类"};
    	
    	List<Map<String,String>> accountlist = new ArrayList<Map<String,String>>();
    	try {
			json.put("sellerId", qymc);
			json.put("productName", softName);
	    	json.put("startTime", startDate);
	    	json.put("endTime", endDate);
	    	json.put("countPerPage",pageSize);
	    	json.put("currentPage",page);
	    	String result = RestRequestUtil.getRest(json,"saleDetailUrl");	
	    	JSONObject resultJson = new JSONObject(result);
			JSONArray jsonArray = resultJson.getJSONArray("data");
			totalRecord=resultJson.getInt("totalSize");
			for(int i=0;i<jsonArray.length();++i){
				JSONObject accountJson =jsonArray.getJSONObject(i);
				Map accountMap=new HashMap();
				if(accountJson.has("customerName"))			accountMap.put("customerName", accountJson.getString("customerName").toString());
				if(accountJson.has("createDate"))			accountMap.put("createDate", accountJson.getString("createDate").toString());
				if(accountJson.has("productName"))			accountMap.put("productName", accountJson.getString("productName").toString());
				if(accountJson.has("billType"))				accountMap.put("billType", billTypeName[Integer.parseInt(accountJson.getString("billType").toString())]);
				if(accountJson.has("argv")){
					accountMap.put("price", 
					(accountJson.getString("argv").toString().split(",")[0] ==null || "".equals(accountJson.getString("argv").toString().split(",")[0])
					?"":(Double.valueOf(accountJson.getString("argv").toString().split(",")[0]))/1000));
					accountMap.put("time", accountJson.getString("argv").toString().split(",")[1]);
				}
				if(accountJson.has("feeValue"))				accountMap.put("feeValue", 
					(accountJson.getString("feeValue").toString() ==null || "".equals(accountJson.getString("feeValue").toString())
					?"":(Double.valueOf(accountJson.getString("feeValue").toString())/1000)));
				accountlist.add(accountMap);
			}
			request.setAttribute("accountlist", accountlist);
		} catch (JSONException e) {
			logger.error(e);
		}catch(Exception e){
			logger.error(e);
		} 
    	PageModel pageModel = PageModel.newPageModel(pageSize, page, totalRecord);
    	pageModel.setDataList(accountlist);
		request.setAttribute("pageModel", pageModel);
		request.setAttribute("currentPage",pageModel.getCurrentPage());
		request.setAttribute("totalPage", pageModel.getTotalPage());
		request.setAttribute("softName", softName);
		request.setAttribute("startDate", startDate);
		request.setAttribute("endDate", endDate);
    	return "account/accountBalance";
    }
    
  //-------------------------跳转至收入查询
    @RequestMapping("/toBillDetail")
    public String toBillDetail(HttpServletRequest request){ 
    	JSONObject json = new JSONObject();
    	String userId=SessionInfo.getLoginInfo(request).getLoginName();
    	
    	LoginInfo loginInfo=new LoginInfo();
    	loginInfo.setId(SessionInfo.getCompanyInfo(request).getLoginId());
    	CompanyInfo companyInfo = companyInfoService.findCompanyInfoByLoginId(loginInfo);
    	String qymc = (companyInfo !=null && !"".equals(companyInfo))?companyInfo.getCompanyName():"";	//因为之前在向运营传递的sellerId是 企业名称
    	
    	//String userId="提防点";
    	String page = request.getParameter("page");
    	String softName=request.getParameter("softName");
    	String startDate=request.getParameter("startDate");
    	String endDate=request.getParameter("endDate");
    	if(softName!=null && !"".equals(softName)){
    		try {
    			softName= URLDecoder.decode(softName,"UTF-8");
    		} catch (Exception e) {
    			logger.error("softName字符编码转换异常",e);
    		}
		}
    	int pageSize = 5; //每页显示的行数
    	int totalRecord = 0; //总行数    	
    	
    	List<Map<String,String>> billList = new ArrayList<Map<String,String>>();
    	try {
			json.put("sellerId", userId);
			json.put("softwareName", softName);
	    	json.put("startTime", startDate);
	    	json.put("endTime", endDate);
	    	json.put("countPerPage",pageSize);
	    	json.put("currentPage",page);
	    	String result = RestRequestUtil.getRest(json,"billDetailUrl");	
	    	//String result="{'data': [{'argv': '11000,1','billType': 0,'createDate': 1417148925000,'customerName': 'zhanghz','feeValue': 11000,'productName': 'zhang测试123','sellerId': '提防点','version': '0'}],'result':'success','totalSize':'1'}";
	    	JSONObject resultJson = new JSONObject(result);
	    	//String stringArray = resultJson.get("producttype").toString();
			JSONArray jsonArray = resultJson.getJSONArray("data");
			totalRecord=resultJson.getInt("totalSize");
			for(int i=0;i<jsonArray.length();++i){
				JSONObject accountJson =jsonArray.getJSONObject(i);
				Map billMap=new HashMap();				
				if(accountJson.has("softwareName"))    	billMap.put("softwareName", accountJson.getString("softwareName").toString());
				if(accountJson.has("cycleMonth"))      	billMap.put("cycleMonth", accountJson.getString("cycleMonth").toString());
				if(accountJson.has("apportionAmount")) 	billMap.put("apportionAmount", 
				(accountJson.getString("apportionAmount").toString() ==null || "".equals(accountJson.getString("apportionAmount").toString())
						?"":(Double.valueOf(accountJson.getString("apportionAmount").toString())/1000)));
				billList.add(billMap);
			}
			request.setAttribute("billList", billList);
		} catch (JSONException e) {
			logger.error(e);
		}catch(Exception e){
			logger.error(e);
		} 
    	PageModel pageModel = PageModel.newPageModel(pageSize, page, totalRecord);
    	pageModel.setDataList(billList);
		request.setAttribute("pageModel", pageModel);
		request.setAttribute("currentPage",pageModel.getCurrentPage());
		request.setAttribute("totalPage", pageModel.getTotalPage());
		request.setAttribute("softName", softName);
		request.setAttribute("startDate", startDate);
		request.setAttribute("endDate", endDate);
    	return "account/billDetail";
    }
    
  //-------------------------跳转至提现
    @RequestMapping("/toDeposit")
    public String toDeposit(HttpServletRequest request){    	
    	return "account/deposit";
    }
}
