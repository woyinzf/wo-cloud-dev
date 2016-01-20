/**
 * Project Name:wo-cloud-dev
 * File Name:LoginInfoAction.java
 * Package Name:com.ai.wocloud.loginInfo.action
 * Date:2014-10-2810:35:42
 * Copyright (c) 2014
 *
*/

package com.ai.wocloud.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
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
import org.springframework.web.servlet.ModelAndView;

import com.ai.wocloud.bean.CompanyInfo;
import com.ai.wocloud.bean.LoginInfo;
import com.ai.wocloud.bean.MessageVeryCodeDto;
import com.ai.wocloud.service.interfaces.CompanyInfoService;
import com.ai.wocloud.service.interfaces.LoginInfoService;
import com.ai.wocloud.service.interfaces.SoftwareService;
import com.ai.wocloud.system.action.BaseAction;
import com.ai.wocloud.system.constants.Constants;
import com.ai.wocloud.system.session.SessionInfo;
import com.ai.wocloud.system.util.RestRequestUtil;
import com.ai.wocloud.system.util.StringUtil;

/**
 * ClassName:LoginInfoAction <br/>
 * Date:     2014-10-28 10:35:42 <br/>
 * @author   zhangyichi
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
@Transactional(rollbackFor = Exception.class)
@Controller
@RequestMapping(value = "/loginInfoAction")
public class LoginInfoAction extends BaseAction{
    private Logger logger = Logger.getLogger(LoginInfoAction.class);
    @Autowired
    private LoginInfoService loginInfoService;
    @Autowired
    private CompanyInfoService companyInfoService;
    @Autowired
    private SoftwareService softwareService;
    
    /**
     * 
     * registration: <br/>
     * 获取注册信息并注册<br/>
     *
     * @param request
     * @return
     * @throws IOException 
     * @since JDK 1.6
     */
    @RequestMapping("/registration")
    @Transactional
    public void registration(HttpServletRequest request,HttpServletResponse response) throws IOException {
    	request.getSession().removeAttribute("activeTime");
        request.getSession().removeAttribute("messageVeryCode");
        
        String path1=request.getParameter("path1");
        String path2=request.getParameter("path2");
        String picPath1="";
        String picPath2="";
        
        if((path1!=null && !path1.equals(""))||(path2!=null && !path2.equals(""))){
			String softwareBaseUrl = request.getSession().getServletContext().getRealPath("/")+"upload";
			try {
				File file =new File(softwareBaseUrl);
				if(!file.exists() &&!file.isDirectory()) {
		        	file.mkdir();
		        }
				  CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
					if(multipartResolver.isMultipart(request)){
						MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
						List<MultipartFile> files =  multiRequest.getFiles("upload");
						 				
			            // 根据系统时间生成上传后保存的文件名
						if(!files.get(0).isEmpty()){
							 long now = System.currentTimeMillis();
							String t_name=files.get(0).getOriginalFilename();				
							String t_ext = t_name.substring(t_name.lastIndexOf(".") + 1);   
							String prefix = String.valueOf(now);
				            // 保存的最终文件完整路径,保存在web根目录下的upload目录下
				            String u_name = prefix + "." + t_ext;
				            if(path1!=null && !path1.equals("")){
				            	picPath1="upload/"+ prefix + "." + t_ext;
				            }else{
				            	picPath2="upload/"+ prefix + "." + t_ext;
				            }
							FileUtils.copyInputStreamToFile(files.get(0).getInputStream(), new File(softwareBaseUrl, u_name)); 
						}
						
						if((path1!=null && !path1.equals(""))&&(path2!=null && !path2.equals(""))){
							if(!files.get(1).isEmpty()){
								 long now = System.currentTimeMillis();
								String t_name2=files.get(1).getOriginalFilename();				
								String t_ext2 = t_name2.substring(t_name2.lastIndexOf(".") + 1);   
								String prefix2 = String.valueOf(now);
					            // 保存的最终文件完整路径,保存在web根目录下的upload目录下
					            String u_name2 = prefix2 + "." + t_ext2;
					            picPath2="upload/"+ prefix2 + "." + t_ext2;
								FileUtils.copyInputStreamToFile(files.get(1).getInputStream(), new File(softwareBaseUrl, u_name2)); 
							}
						}
					}
			} catch (Exception e) {
				logger.error(e);
			}
        }
        //获取参数
        String loginName = request.getParameter("username");
        String loginPassword = request.getParameter("passwordMd5");
        String companyName = request.getParameter("enterprise_name");
        String companyCode = request.getParameter("enterprise_code");
        String companyAddress = request.getParameter("enterprise_address");
        String email = request.getParameter("email");
        String mobilePhone = request.getParameter("phone");
        
        //封装对象
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setLoginName(loginName);
        loginInfo.setLoginPassword(loginPassword);
        CompanyInfo companyInfo = new CompanyInfo();
        companyInfo.setCompanyName(companyName);
        companyInfo.setCompanyCode(companyCode);
        companyInfo.setCompanyAddress(companyAddress);
        companyInfo.setEmail(email);
        companyInfo.setMobilePhone(mobilePhone);
        companyInfo.setPeasonPicPath(picPath1);
        companyInfo.setWorkPicPath(picPath2);
        PrintWriter out = response.getWriter();
        //入库
        try {
            loginInfoService.insertLoginInfo(loginInfo);
            companyInfoService.registerCompanyInfo(companyInfo, loginInfo);
            out.print(1);
            out.flush();
            out.close();
            
        } catch (Exception e) {
            logger.error("注册失败", e);
            out.print(2);
            out.flush();
            out.close();
        }
    }
    
    /**
     * 
     * registration: <br/>
     * 获取注册信息并注册<br/>
     *
     * @author zhangyichi
     * @param request
     * @return
     * @throws IOException 
     * @throws JSONException 
     * @since JDK 1.6
     */
    @RequestMapping("/fileUpload")
    public void fileUpload(HttpServletRequest request,HttpServletResponse response) {
    	try{
    	JSONObject json = new JSONObject();
    	response.setContentType("text/html");
        // 设置字符编码为UTF-8, 这样支持汉字显示
       response.setCharacterEncoding("UTF-8");
        // 实例化一个硬盘文件工厂,用来配置上传组件ServletFileUpload
       DiskFileItemFactory dfif = new DiskFileItemFactory();
        dfif.setSizeThreshold(1000*4096);// 设置上传文件时用于临时存放文件的内存大小,这里是4K.多于的部分将临时存在硬盘
        File file =new File(request.getSession().getServletContext().getRealPath("/")+"uploadtemp");
        File file2 =new File(request.getSession().getServletContext().getRealPath("/")+"upload");

        if(!file.exists() &&!file.isDirectory()) {
        	file.mkdir();
        }
        if(!file2.exists() &&!file2.isDirectory()) {
        	file2.mkdir();
        }
       dfif.setRepository(file);// 设置存放临时文件的目录,web根目录下的uploadtemp目录
       // 用以上工厂实例化上传组件
       ServletFileUpload sfu = new ServletFileUpload(dfif);

        PrintWriter out = response.getWriter();
        // 从request得到 所有 上传域的列表
       List<?> fileList = null;
        try {
            fileList =  sfu.parseRequest(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 没有文件上传
       if (fileList == null || (fileList).size() == 0) {
    	   json.put("result", "failed");  
    	   out.write(json.toString());
           out.flush();
           out.close();
            return;
        }
        // 得到所有上传的文件
       Iterator<?> fileItr = fileList.iterator();
       String returnPath="";
        // 循环处理所有文件
       while (fileItr.hasNext()) {
            FileItem fileItem = null;
            String path = null;
            long size = 0;
            // 得到当前文件
           fileItem = (FileItem) fileItr.next();
            // 忽略简单form字段而不是上传域的文件域(<input type="text" />等)
           if (fileItem == null || fileItem.isFormField()) {
                continue;
            }
            // 得到文件的完整路径
           path = fileItem.getName();
            // 得到文件的大小
           size = fileItem.getSize();
            if ("".equals(path) || size == 0) {
            	json.put("result", "success");  
         	   out.write(json.toString());
                out.flush();
                out.close();
                return;
            }

            // 得到去除路径的文件名
           String t_name = path.substring(path.lastIndexOf("\\") + 1);
            // 得到文件的扩展名(无扩展名时将得到全名)
           String t_ext = t_name.substring(t_name.lastIndexOf(".") + 1);          
            long now = System.currentTimeMillis();
            // 根据系统时间生成上传后保存的文件名
           String prefix = String.valueOf(now);
            // 保存的最终文件完整路径,保存在web根目录下的upload目录下
           String u_name = request.getSession().getServletContext().getRealPath("/") + "upload/"
                    + prefix + "." + t_ext;
            try {
                // 保存文件
               fileItem.write(new File(u_name));
               returnPath="upload/"+ prefix + "." + t_ext; 
               json.put("result", "success");
               json.put("returnPath", returnPath);
               out.write(json.toString());
               out.flush();
               out.close();
               
            } catch (Exception e) {
        	    json.put("result", "failed");
        	    out.write(json.toString());
                out.flush();
                out.close();
            }
        }
    	}catch(Exception e){
    	    logger.error(e);
    	}
    }
    
    
    //判断短信验证码是否正确----yinxiao
    @RequestMapping("/veryMessCode")
    public void veryMessCode(HttpServletRequest request,HttpServletResponse response) {
    	String verycodeinput = request.getParameter("verycodeinput");
    	try {
    		String verycodereturn=(String) request.getSession().getAttribute("messageVeryCode");
    		if(verycodeinput.equals(verycodereturn)){
    			response.getWriter().print(true);
    		}else{
    			response.getWriter().print(false);
    		}
			
		} catch (IOException e) {
			// Tlogger.error(e);ntStackTrace();
		}                                
    }
    
    //判断图片验证码是否正确
    @RequestMapping("/veryCode")
    public void veryCode(HttpServletRequest request,HttpServletResponse response) {
    	String veryCode = request.getParameter("veryCode"); 
        String timestamp = request.getParameter("timestamp");
        String validateC = (String) request.getSession().getAttribute("validateCode_"+timestamp);
        boolean result=false;
        if(veryCode.equalsIgnoreCase(validateC)){
        	result=true;
        }
        try {
			response.getWriter().print(result);
		} catch (IOException e) {
		    logger.error(e);
		}
    }
    
    
    @RequestMapping("/userExistorNot")
    public void userExistorNot(HttpServletRequest request,HttpServletResponse response) throws IOException {
    	String loginName = request.getParameter("username");
    	boolean result=loginInfoService.getUsername(loginName);
    	response.getWriter().print(result);                                
    }
    
    @RequestMapping("/userExistorNot2")
    public void userExistorNot2(HttpServletRequest request,HttpServletResponse response) throws IOException {
    	String loginName = request.getParameter("name");
    	boolean result=loginInfoService.getUsername(loginName);
    	if(result==true){ 
    		result=false;
    	}else{
    		result=true;
    	}  	
    	response.getWriter().print(result);                                
    }
    
    @RequestMapping("/phoneExistorNot")
    public void phoneExistorNot(HttpServletRequest request,HttpServletResponse response) throws IOException {
    	String loginName = request.getParameter("username");
    	String phone = request.getParameter("phone");
    	Map<String,String> paramMap = new HashMap<String,String>();
    	paramMap.put("loginName", loginName);
    	paramMap.put("phone", phone);
    	boolean result=companyInfoService.getUsernameAndPhone(paramMap);
    	response.getWriter().print(result);                                
    }
    
    @RequestMapping("/epExistorNot")
    public void epExistorNot(HttpServletRequest request,HttpServletResponse response) throws IOException {
    	String enterpriseName = request.getParameter("enterpriseName");
    	boolean result=companyInfoService.getEnterpriseName(enterpriseName);
    	response.getWriter().print(result);                                
    }
    
    @RequestMapping("/toLogin")
    @Transactional
    public ModelAndView toLogin(HttpServletRequest request,HttpServletResponse response) throws IOException {
    	ModelAndView view = new ModelAndView("login");
    	return view;
    }
    
    
    
    /**
     * 
     * login: <br/>
     * 登录认证<br/>
     *
     * @param request
     * @return
     * @since JDK 1.6
     */
	@RequestMapping("/login")
    public void login(HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        //获取参数
        String loginName = request.getParameter("loginName");
        String loginPassword = request.getParameter("loginPassword");
        String veryCode = request.getParameter("veryCode"); 
        String timestamp = request.getParameter("timestamp");
        String validateC = (String) request.getSession().getAttribute("validateCode_"+timestamp);

        //登陆认证
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setLoginName(loginName);
        loginInfo.setLoginPassword(loginPassword);
        try {
            if(veryCode==null||"".equals(veryCode)){         
                json.put("status", Constants.LoginReturnMessage.VERIFY_CODE_IS_BLANK);         
            }else{
                if(validateC.equalsIgnoreCase(veryCode)){         
                    if(!StringUtil.isBlank(loginName) && !StringUtil.isBlank(loginPassword)){
                        LoginInfo loginResultfirst = loginInfoService.validationInfo(loginInfo);
                        
                        //先判断用户是否存在，再判断用户是否冻结
                        if(loginResultfirst!=null){
                        	LoginInfo loginResult = loginInfoService.login(loginInfo);
                        	if(loginResult != null){
                        		  CompanyInfo companyInfo = companyInfoService.findCompanyInfoByLoginId(loginResult);
                                  SessionInfo.setCompanyInfo(request.getSession(), companyInfo);
                                  LoginInfo loginInfoSession=new LoginInfo();
                                  loginInfoSession.setLoginName(loginName);
                                  //登陆信息存到session
                                  SessionInfo.setLoginInfo(request.getSession(), loginInfoSession);
                                  json.put("status", Constants.LoginReturnMessage.SUCCESS);
                                  System.out.println("登录成功！");
                                  json.put("result", "success");
                        	}else{
                        		 json.put("status", Constants.LoginReturnMessage.USERCODE_OR＿PASSWD_IS_FREE);
                        	}
                          
                        }else{
                            json.put("status", Constants.LoginReturnMessage.USERCODE_OR＿PASSWD_IS_ERROR);
                        }
                    }else{
                        json.put("status", Constants.LoginReturnMessage.USERCODE_OR_PASSWD_IS_BLANk);
                    }
                }else{         
                    json.put("status", Constants.LoginReturnMessage.VERIFY_CODE_IS_ERROR);         
                }
            }
        } catch (Exception e) {
            logger.error("登陆失败", e);
        }
        PrintWriter printWriter = null;
        try {
            printWriter = response.getWriter();
            printWriter.write(json.toString());
            printWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            printWriter.close();
        }
    }
    
    /**
     * 
     * toIndex: <br/>
     * 登陆后跳转到首页<br/>
     *
     * @author zhangyichi
     * @param request
     * @return
     * @since JDK 1.6
     */
    @RequestMapping("/toIndex")
    public ModelAndView toIndex(HttpServletRequest request) {
        //如果为session总保存了回跳地址,则跳往登录前地址,否则跳转到首页
        String backUrl = (String) request.getSession().getAttribute("backUrl");
        if (backUrl != null && backUrl != "") {
            String backParam = (String) request.getSession().getAttribute("backParam");
            ModelAndView view = new ModelAndView("redirect:" + backUrl + "?" + backParam);
            //清空session里保存的backUrl
            request.getSession().removeAttribute("backUrl");
            return view;
        } else {
            ModelAndView view = new ModelAndView("guide");
            return view;
        }
    }
    
    /**
     * 
     * toRegisterPage: <br/>
     * 跳转到注册页面<br/>
     *
     * @author zhangyichi
     * @param request
     * @return
     * @since JDK 1.6
     */
    @RequestMapping("/toRegisterPage")
    public ModelAndView toRegisterPage(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("register/register");
        return view;
    }
    
    /**
     * 
     * sendMessageVeryCode: <br/>
     * 查询短信验证码，如果正确，则把验证码存进session<br/>
     *
     * @author zhangyichi
     * @param request
     * @param response
     * @since JDK 1.6
     */
    @RequestMapping("/sendMessageVeryCode")
    public void sendMessageVeryCode(HttpServletRequest request, HttpServletResponse response) {
        String telNumber = request.getParameter("phone");
        JSONObject json = new JSONObject();
        PrintWriter printWriter = null;
        try {
            String result = RestRequestUtil.getMessageVeryCode(telNumber);
            json.append("result", result);
            printWriter = response.getWriter();
            printWriter.write(json.toString());
            printWriter.flush();
            if (result.equals("0")) {
                MessageVeryCodeDto dto = loginInfoService.getMessageVeryCode(telNumber);
                String messageVeryCode = dto.getVeryCode();
                String activeTime = dto.getActiveTime();
                request.getSession().setAttribute("activeTime", activeTime);
                request.getSession().setAttribute("messageVeryCode", messageVeryCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @RequestMapping("/veryMessClean")
    public void veryMessClean(HttpServletRequest request, HttpServletResponse response){
    	request.getSession().removeAttribute("messageVeryCode");
    }
    
    /**
     * 
     * logout: <br/>
     * 登出<br/>
     *
     * @author zhangyichi
     * @param request
     * @param response
     * @return
     * @since JDK 1.6
     */
    @RequestMapping("/logout")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
        SessionInfo.removeLoginCompanyInfo(request);
        ModelAndView view = new ModelAndView("login");
        return view;
    }
    
    /**
     * 
     * unLogin: <br/>
     * 登出，以及未登录跳转的登陆页<br/>
     *
     * @author zhangyichi
     * @param request
     * @return
     * @since JDK 1.6
     */
    @RequestMapping("/unLogin")
    public ModelAndView unLogin(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("login");
        return view;
    }
    
    //---------------------原密码比对
    @RequestMapping("/oldPwdVery")
    public void oldPwdVery(HttpServletRequest request,HttpServletResponse response){
    	String password = request.getParameter("password");
    	String username=(String) SessionInfo.getLoginInfo(request).getLoginName();
    	LoginInfo loginInfo=new LoginInfo();
    	loginInfo.setLoginName(username);
    	loginInfo.setLoginPassword(password);
    	try {
    		boolean result=loginInfoService.oldPwdVery(loginInfo);
    		response.getWriter().print(result);			
		} catch (Exception e) {
		    logger.error(e);
		} 
    }
    
    //-------------------------跳转至密码重置
    @RequestMapping("/toPwdReset")
    public String toPwdChange(HttpServletRequest request){    	
    	return "userInfo/pwdReset";
    }
        
    //---------------------密码重置
    @RequestMapping("/pwdReset")
    public void pwdReset(HttpServletRequest request,HttpServletResponse response){
    	String password = request.getParameter("password");
    	String username=request.getParameter("username");
    	LoginInfo loginInfo=new LoginInfo();
    	loginInfo.setLoginName(username);
    	loginInfo.setLoginPassword(password);
    	JSONObject json = new JSONObject();
    	PrintWriter printWriter = null;
    	try {
    		loginInfoService.pwdChange(loginInfo);	
    		json.put("result", "0");
    		printWriter = response.getWriter();
            printWriter.write(json.toString());
            printWriter.flush();
		} catch (Exception e) {
		    logger.error(e);
		} 
    }
    
    //----------------------向接口传送新用户数据
    @RequestMapping("/sendNewCust")
    public void sendNewCust(HttpServletRequest request,HttpServletResponse response){
    	String password = request.getParameter("password");
    	String passwordMd5 = request.getParameter("passwordMd5");
    	String account=request.getParameter("username");
    	String email=request.getParameter("email");
    	String mobile=request.getParameter("mobile");
    	LoginInfo loginInfo=new LoginInfo();
    	loginInfo.setLoginName(account);
    	loginInfo.setLoginPassword(passwordMd5);
    	LoginInfo loginResult = loginInfoService.login(loginInfo);
    	String scustId=loginResult.getId();
    	JSONObject json = new JSONObject();
    	JSONObject json2 = new JSONObject();
    	PrintWriter printWriter = null;
    	try {
			json.put("account", account);
			json.put("password", password);
			json.put("email", email);
			json.put("mobile", mobile);
			json.put("scustId", scustId);
			String result = RestRequestUtil.sendNewCust(json);
			String customerId = softwareService.getCustId(loginResult.getLoginName()+"@softwareProvider");
			String result_ = RestRequestUtil.modifyQuota(customerId);
			json2.put("result", result_);
            printWriter = response.getWriter();
            printWriter.write(json2.toString());
            printWriter.flush();
		} catch (JSONException e) {
		    logger.error(e);
		}catch(Exception e){
		    logger.error(e);
		}
    	
    }
    
}
