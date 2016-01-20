<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<%@ include file="/jsp/common/common.jsp"%>
<title>沃云软件商门户</title>
<link href="<%=bp %>/css/css.css" rel="stylesheet" type="text/css" />
<style>
	.mask {    
		background: #000;    
		filter: alpha(opacity=50); /* IE的透明度 */    
		opacity: 0.5;  /* 透明度 */    
		-moz-opacity:0.5;  
		display: none;    
		position: absolute;    
		top: 0px;    
		left: 0px;      
		z-index: 100; /* 此处的图层要大于页面 */    
	}
</style>
<script type="text/javascript" src="${_base}/js/md5.js" /></script>
<script src="${_base}/js/jquery.form.js" type="text/javascript"></script>
<script>
var registerController;
var InterValObj; //timer变量，控制时间  
var count = 120; //间隔函数，1秒执行  
var curCount;//当前剩余秒数 
$(document).ready(function(){
	registerController = new $.RegisterController();
});
/*定义页面管理类*/
(function(){
	$.RegisterController  = function(){ 
		this.settings = $.extend(true,{},$.RegisterController.defaults); 
		this.init();
		
	};
	$.extend($.RegisterController,{
		defaults : {
			         FORM_ID : "#registerForm",
			       SUBMIT_ID : "#submit",
			     USERNAME_ID : "#username",
			     PASSWORD_ID : "#password",
			    PASSWORD2_ID : "#password2",
			        EMAIL_ID : "#email",
			        PHONE_ID : "#phone",
	      ENTERPRISE_NAME_ID : "#enterpriseName",
		  ENTERPRISE_CODE_ID : "#enterpriseCode",
	   ENTERPRISE_ADDRESS_ID : "#enterpriseAddress",
	         REGISTERINFO_ID : "#registerInfo",
	       UPLOAD_BUTTON0_ID : "#upload_button_0",
	       UPLOAD_BUTTON1_ID : "#upload_button_1",
	        UPLOAD_INPUT0_ID : "#upload_p_0",
	        UPLOAD_INPUT1_ID : "#upload_p_1",
	        MESS_VERYCODE_ID : "#mess_verycode"
		},
		prototype : {
			init : function(){
				var _this = this;
				_this.addRults();
				_this.bindEvents();
				
			},
			bindEvents : function(){
				var _this = this;
				//给提交按钮绑定事件
				$(_this.settings.UPLOAD_BUTTON1_ID).bind("click",function(){				
					$("#upload1").click();
				});
				$(_this.settings.UPLOAD_BUTTON0_ID).bind("click",function(){				
					$("#upload0").click();				
				});	
				$(_this.settings.UPLOAD_INPUT1_ID).bind("click",function(){				
					$("#upload1").click();
				});
				$(_this.settings.UPLOAD_INPUT0_ID).bind("click",function(){				
					$("#upload0").click();				
				});	
				$(_this.settings.MESS_VERYCODE_ID).bind("click",function(){				
					_this.sendMess();				
				});
				$("input[name='password'] ,input[name='password2']").bind("focus",function(){
					if($(this).val()==this.defaultValue){
						$(this).attr("type","password");
						$(this).val("");
					}
				});
				$("input[name='password'] ,input[name='password2']").bind("blur",function(){				
					if($(this).val()==""){
						$(this).attr("type","text");
						$(this).val(this.defaultValue);
					}
				});
				$("input[type='text']").bind("focus",function(){				
					if($(this).val()==this.defaultValue){
						$(this).val("");
					}
				});
				$("input[type='text']").bind("blur",function(){				
					if($(this).val()==""){
						$(this).val(this.defaultValue);
					}
				});
				
			},
			sendMess :function(){
				var _this = this;
				var phone=$(_this.settings.PHONE_ID).val();
				$.ajax({
				      type: "POST",
				      url: "${_base}/loginInfoAction/sendMessageVeryCode",
				      dataType: 'json',
				      data:{
		                  phone : phone
		               }, 
				      success: function(data) {
				    	  var $json=eval(data);				    	  
		                  result = $json.result;
				          if (result == "0") {
				        	  $("#verycodeinput_error").text("验证码已发送,有效期2分钟!");
				        	  _this.remainTime();
				          }
				          else {
				        	  $("#verycodeinput_error").text("验证码获取失败！请重新获取");
				          }
				       },
				       error: function(data) { $("#verycodeinput_error").text("验证码获取失败！请重新获取"); }
				  });
				
			},
			remainTime :function(){	
				var _this = this;
				curCount = count;
				//$("#verycodeinput").attr("disabled", false); 
				$("#mess_verycode").attr("disabled", true);
				$("#mess_verycode").val("倒计时" + curCount + "秒");  
				InterValObj = window.setInterval(_this.setRemainTime, 1000); //启动计时器，1秒执行一次 
			},
			setRemainTime :function(){
				var _this = this;
				if (curCount == 0) {                  
					window.clearInterval(InterValObj);//停止计时器  
					$("#mess_verycode").removeAttr("disabled");//启用按钮  
					$("#mess_verycode").val("重新发送验证码"); 
					//$("#verycodeinput").attr("disabled", true); 
					$.ajax({
						type : "POST",
						url : "${_base}/loginInfoAction/veryMessClean",
						async : false,						
						success : function(data) {								
							
						}
					}); 
				}  
				else {  
					curCount--;  
					$("#mess_verycode").val("倒计时" + curCount + "秒");  
				}  

			},
			addRults : function() {
				var _this = this;
				 jQuery.validator.addMethod("mobile", function(value, element) {    
				      var length = value.length;    
				      return this.optional(element) || (length == 11 && /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/.test(value));    
				  }, "手机号码格式错误");

				 jQuery.validator.addMethod("isUsername", function(value, element) {       
			         return this.optional(element) || (/^[a-zA-Z]\w{5,30}$/.test(value));       
			     }, "字母开头的6-30个英文与非特殊字符"); 
				 jQuery.validator.addMethod("ep", function(value, element) {       
			         if(value=="100个非特殊字符以内"||value=="10个非特殊字符以内")   {
			        	 return false;
			         }    
			         return true;
			     }, "请输入企业名称"); 
				 
			
				

				$(_this.settings.FORM_ID).validate({
					rules : {
						username : {
							required : true,
							isUsername:true,
							remote:{                                          
					               type:"POST",
					                url:"${_base}/loginInfoAction/userExistorNot",            
					                data:{
					                  username:function(){return $("#username").val();}
					                }  
					        }
						},
						password : {
							required : true,
							rangelength:[8,16]
						},
						password2 : {
							required:true,
							equalTo : '#password'
						},
						email : {
							required : true,
							email : true
						},
						phone : {
							required : true,
							mobile:true 
						},
						verycodeinput : {
							required : true,
							remote:{                                          
					               type:"POST",
					                url:"${_base}/loginInfoAction/veryMessCode",            
					                data:{
					                	verycodeinput :function(){return $("#verycodeinput").val();}
					                }  
					        }
						},
						enterpriseName : {
							ep:true,
							required : true,
							maxlength:  100,
							remote:{                                          
					               type:"POST",
					                url:"${_base}/loginInfoAction/epExistorNot",            
					                data:{
					                	enterpriseName:function(){return $("#enterpriseName").val();}
					                }  
					        }
						},
						enterpriseCode : {
							ep:true,
							required : true,
							maxlength: 100
						},
						enterpriseAddress : {
							ep:true,
							required : true,
							maxlength:  100
						},
						registerInfo : {
							required : true,
							minlength : 1
						}
				
					},
					onkeyup : function(element) {
						jQuery(element).valid();
					},
					success:function(error, element){
						var elementId = $(element).attr("id");
						if(elementId=="phone"){
							$("#mess_verycode").attr("disabled",false);
						}
						if(elementId=="verycodeinput"){
							$("#verycodeinput_error").text("校验成功");
						}else{
						$("#" + elementId + "_error").text($(error).text());
						}
					},
					errorPlacement : function(error, element) {
						var elementId = $(element).attr("id");
						if(elementId=="phone"){
							$("#mess_verycode").attr("disabled",true);
						}
						$("#" + elementId + "_error").text($(error).text());
					},
					messages : {
						username : {
							required : "请输入用户名",
							remote : "该用户名已被注册"
						},
						password : {
							required : "请输入密码",
							rangelength : "请输入8-16位密码,区分大小写"
						},
						password2 : {
							required : "请输入密码",
							equalTo : "两次填写的密码不一致"
						},
						email : {
							required : "请输入邮箱",
							email : "邮箱格式不正确"
						},
						phone : {
							required : "请输入手机号",
							mobile : "手机格式不正确"
						},
						verycodeinput :{
							required : "请输入验证码",
							remote   : "验证码不正确"
						},
						enterpriseName : {
							required : "请输入企业名称",
							maxlength : "100个非特殊字符以内",
							remote   : "该企业已注册，请确认注册信息"
						},
						enterpriseCode : {
							required : "请输入企业组织机构代码",
							maxlength : "10个非特殊字符以内"
						},
						enterpriseAddress : {
							required : "请输入企业地址",
							maxlength : "100个非特殊字符以内"
						},
						registerInfo : {
							required : "请同意用户注册协议"
						}
					},
					submitHandler : function(form) {
					 	$(_this.settings.SUBMIT_ID).attr("disabled", true);
						$(_this.settings.SUBMIT_ID).val("注册中....");
						
						
						//var file1 = $("#upload1").get(0).files[0]; 
                		var username=$(_this.settings.USERNAME_ID).val();
						var inputPassword = $(_this.settings.PASSWORD_ID).val();
						var passwordMd5 = hex_md5(inputPassword);
						var email=$(_this.settings.EMAIL_ID).val();
						var phone=$(_this.settings.PHONE_ID).val();
						var enterprise_name=$(_this.settings.ENTERPRISE_NAME_ID).val();						
						var enterprise_code=$(_this.settings.ENTERPRISE_CODE_ID).val();						
						var enterprise_address=$(_this.settings.ENTERPRISE_ADDRESS_ID).val();
						var path1=$("#upload0").val();
						var path2=$("#upload1").val();
						
						var options = {
							type : "POST",
							url : "${_base}/loginInfoAction/registration",
							async : false,
							 dataType: 'json',
							data : {
										username : username,
									 passwordMd5 : passwordMd5,
										   email : email,
										   phone : phone,
								 enterprise_name : enterprise_name,
								 enterprise_code : enterprise_code,
							  enterprise_address : enterprise_address,	
							  			   path1 : path1,
							  			   path2 : path2
							},
							success : function(data) {								
								if (data == 1) {
									$("#mask").css("height",$(document).height());   
								    $("#mask").css("width",$(document).width()); 

									$("#mask").show();
									$.ajax({
										type : "POST",
										url : "${_base}/loginInfoAction/sendNewCust",
										async : false,
										dataType: 'json',
										data : {											
											username : username,
											password : inputPassword,
										  passwordMd5:passwordMd5,
											   email : email,
											  mobile : phone											  					  			   
										},
										success : function(data) {								
											var $json=eval(data);				    	  
							                  result = $json.result;
									          if (result == "0") {
									        	  info_alert("注册成功，我们为您同步注册了沃云账号，账号为<b>"+username+"@softwareProvider</b>，初始密码与您软件商门户密码相同！",jump_to_login);
									          }
									          else {
									        	 info_alert(result);
									          }
										}
									}); 
									
								} else {									
									$("#username_error").text("注册失败！");
									$(_this.settings.SUBMIT_ID).attr("disabled", false);
									$(_this.settings.SUBMIT_ID).val("立即注册");									
								}
							}
						};
						
						$('#registerForm').ajaxSubmit(options);
					}
				});
			}
		}
	});
})(jQuery);	 
function showFileName(tip){
	if(tip==0){
		$("#upload_input_0").val($("#upload0").val());
	}else{
		$("#upload_input_1").val($("#upload1").val());
	}
}
function jump_to_login(){
	location.href="${_base}/loginInfoAction/toLogin";
}
</script>  
</head>
<body>
<div class="dengl">    
    <!--header2 -->
	<%@ include file="/jsp/common/header2.jsp"%>
        
    <div class="zhuce">
    	<div class="zhuce_DJ"><p>沃云软件商用户注册</p></div>
    	<div class="zhuce_A"><p>欢迎您进入沃云软件商门户，请注册您的帐户.</p></div>
    	<form  id="registerForm" name="registerForm" method="post" enctype="multipart/form-data">
			<div class="zhuce_B">
		    	<ul>
		     		<li>
		     			<p class="yo_minz"><span>*</span>用户名：</p>
		    		 	<p class="pput">
			    			<span><input name="username" value="字母开头的6-30个英文与非特殊字符" id="username" type="text" class="input4"/></span>
			    		    <span class="pt_img"><img src="<%=bp %>/images/yh.png" /></span>
		     			</p>
		    			<p><label class="label1" id="username_error"></label></p>
		     		</li>
		     		<li>
		     			<p class="yo_minz"><span>*</span>密码：</p>
		     			<p class="pput">
						    <span><input name="password" value="8-32位密码" id="password" type="text" class="input4" /></span>
						    <span class="pt_img"><img src="<%=bp %>/images/mm.png" /></span>
		     			</p>
		     			<p><label class="label1" id="password_error"></label></p>
				    </li>
				    <li>
				    	<p class="yo_minz"><span>*</span>确认密码：</p>
				    	<p class="pput">
		     				<span><input name="password2" value="8-32位确认密码" id="password2" type="text" class="input4" /></span>
		     				<span class="pt_img"><img src="<%=bp %>/images/mm.png" /></span>
		     			</p>
		      			<p><label class="label1" id="password2_error"></label></p>
		     		</li>
		     		<li>
					    <p class="yo_minz"><span>*</span>电子邮件：</p>
					    <p><input name="email" value="格式： xx@xx.xx" id="email" type="text" class="input3" /></p>
					    <p><label class="label1" id="email_error"></label></p>
		     		</li>
		     		<li>
		     			<p class="yo_minz"><span>*</span>移动电话：</p>
		    			<p class="pput1">
					    	<span><input name="phone"  value="输入移动电话号码" id="phone" type="text" class="input5" /></span>
					    	<span class="pt_img"><img src="<%=bp %>/images/sj.png" /></span>
		     			</p>
		     			<p><input type="button"  id="mess_verycode" class="huoq" disabled="disabled" value="获取短信验证码"/></p>
		     			<p><label class="label1" id="phone_error"></label></p>
		     		</li>
		     		<li>
		     			<p class="yo_minz"><span>*</span>短信验证码：</p>
		     			<p><input name="verycodeinput" value="请输入动态密码" id="verycodeinput" type="text" class="input6" /></p>
		     			<p class="label1" id="verycodeinput_error"></p>
		     		</li>
		     		<li>
		     			<p class="yo_minz"><span>*</span>企业名称：</p>
		     			<p><input name="enterpriseName" value="100个非特殊字符以内" id="enterpriseName" type="text" class="input3" /></p>
		     			<p><label class="label1" id="enterpriseName_error"></label></p>
		     		</li>
		     		<li>
		     			<p class="yo_minz"><span>*</span>企业组织机构代码：</p>
		     			<p><input name="enterpriseCode" value="10个非特殊字符以内" id="enterpriseCode" type="text" class="input3" /></p>
		     			<p><label class="label1" id="enterpriseCode_error"></label></p>
		     		</li>
		     		<li>
		     			<p class="yo_minz"><span>*</span>企业地址：</p>
		     			<p><input name="enterpriseAddress" value="100个非特殊字符以内" id="enterpriseAddress" type="text" class="input3" /></p>
		     			<p><label class="label1" id="enterpriseAddress_error"></label></p>
		     		</li>		     	
		     		<li>
		     			<p class="yo_minz">营业执照电子版：</p>
		     			<p id="upload_p_0"><input name="" type="text" value="图片大小10M以内" disabled="disabled" id="upload_input_0"  class="input6" /></p>
		     			<p><input type="button"   id="upload_button_0"  class="huoq1" value="浏览"/></p>
		     			<input type="file" id="upload0" onchange="showFileName(0);" style="display:none;" name="upload"  value="浏览">
		     		</li>
		      		<li>
		     			<p class="yo_minz">组织机构代码电子版：</p>
		     			<p id="upload_p_1"><input name="" disabled="disabled" value="图片大小10M以内" id="upload_input_1" type="text" class="input6" /></p>
		     			<p><input type="button"  id="upload_button_1" class="huoq1" value="浏览"/></p>
		     			<input type="file" id="upload1" onchange="showFileName(1);" style="display:none;" name="upload"  value="浏览">		     			
		     		</li>
		     		<li style=" margin-top:0px;">
		     			<p><input name="registerInfo" type="checkbox" id="registerInfo"  class="danx" onkeypress="if(event.keyCode==13||event.which==13){$('#submit').click();}"/></p>
		     			<p>我已阅读并同意注册协议</p>
		     			<p><label class="label1" id="registerInfo_error"></label></p>
		     		</li>
		     		<!-- <li class="tij"><A id="submit" name="submit" href="javascript:void(0);">提&nbsp;交</A></li>  -->
		     		<li><input type="submit" class="tij" style="border-color:#fff;background:url(<%=bp %>/images/tj.png);color:#fff; font-size:16px;repeat-x;" value="立即注册"  id="submit" name="submit"/></li>
		     	</ul>
		    </div>
     	</form>   
    </div>
    
    <%@ include file="/jsp/common/footer.jsp"%>         <!--------------------footer-->
</div>

<div id="mask" class="mask"></div>
</body>
</html>

