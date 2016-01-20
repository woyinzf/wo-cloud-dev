<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<%@ include file="/jsp/common/common.jsp"%>
<title>沃云商城沃云软件商门户</title>
<script type="text/javascript" src="${_base}/js/md5.js" /></script>
<script src="${_base}/js/caidna.js" type="text/javascript"></script>
<script>
var timestamp;
var pwdResetController;
var InterValObj; //timer变量，控制时间  
var count = 120; //间隔函数，1秒执行  
var curCount;//当前剩余秒数 
$(document).ready(function(){
	pwdResetController = new $.PwdResetController();
});
/*定义页面管理类*/
(function(){
	$.PwdResetController  = function(){ 
		this.settings = $.extend(true,{},$.PwdResetController.defaults); 
		this.init();
		
	};
	$.extend($.PwdResetController,{
		defaults : {
			             FORM_ID : "#pwdResetForm",
			         USERNAME_ID : "#username",
			         PASSWORD_ID : "#password",
			         PASSWORD2_ID : "#password2",
			        	IMAGE_ID : "#imgObj",
					   SWITCH_ID : "#switchImage",
			    MESS_VERYCODE_ID : "#mess_verycode"
		},  
		prototype : {
			init : function(){
				var _this = this;
				_this.addRults();
				_this.changeImg();
				_this.bindEvents();				
			},
			bindEvents : function(){
				var _this = this;
				$(_this.settings.SWITCH_ID).bind("click",function(){
					_this.changeImg();
				});
				$(_this.settings.IMAGE_ID).bind("click",function(){
					_this.changeImg();
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
			addRults : function() {
				var _this = this;
				jQuery.validator.addMethod("mobile", function(value, element) {    
				      var length = value.length;    
				      return this.optional(element) || (length == 11 && /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/.test(value));    
				  }, "手机号码格式错误");

				 jQuery.validator.addMethod("isUsername", function(value, element) {       
			         return this.optional(element) || (/^[a-zA-Z]\w{5,30}$/.test(value));       
			     }, "字母开头的6-30个英文与非特殊字符"); 
				$(_this.settings.FORM_ID).validate({
					rules : {
						username : {
							required : true,
							isUsername:true,
							remote:{                                          
					               type:"POST",
					                url:"${_base}/loginInfoAction/userExistorNot2",            
					                data:{
					                	name:function(){return $("#username").val();} 
					                }  
					        }
						},
						phone : {
							required : true,
							mobile:true ,
							remote:{                                          
					               type:"POST",
					                url:"${_base}/loginInfoAction/phoneExistorNot",            
					                data:{
					                	username:function(){return $("#username").val();} ,
					                	phone:function(){return $("#phone").val();}
					                }  
					        }
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
						password : {
							required : true,
							rangelength:[8,16]
						},
						password2 : {
							required:true,
							equalTo : '#password'
						},
						veryCode : {
							required : true,
							remote:{ 
								    type:"POST",
					                url:"${_base}/loginInfoAction/veryCode",            
					                data:{
					                	veryCode :function(){return $("#veryCode").val();},	
					                	timestamp:function(){return timestamp;}
					                }  
					        }
						}
				
					},
					onfocusout : function(element) {
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
							remote : "该用户名不存在"
						},
						phone : {
							required : "请输入手机号",
							remote  : "手机号与用户名不匹配"
						},
						verycodeinput :{
							required : "请输入验证码",
							remote   : "验证码不正确"
						},
						password : {
							required : "请输入新密码",
							rangelength : "请输入8-16位密码,区分大小写"
						},
						password2 : {
							required : "请输入确认密码",
							equalTo : "两次输入的密码不一致"
						},
						veryCode :{
							required :"请输入验证码",
							remote : "验证码不正确"
						}
					},
					submitHandler : function(form) {
					 	$(_this.settings.SUBMIT_ID).attr("disabled", true);
						$(_this.settings.SUBMIT_ID).val("提交中....");
						
						var inputPassword = $(_this.settings.PASSWORD_ID).val();
						var passwordMd5 = hex_md5(inputPassword);
						var username=$(_this.settings.USERNAME_ID).val();
						
						$.ajax({
								type : "POST",
								url : "${_base}/loginInfoAction/pwdReset",
								async : false,
								dataType: 'json',
								data : {											
									password : passwordMd5,
									username : username
								},
								success : function(data) {								
									var $json=eval(data);				    	  
					                  result = $json.result;
							          if (result == "0") {
							        	  info_alert("密码修改成功！点击跳转到登录界面",jump_to_login);
							          }
							          else {
							        	  info_alert("密码修改失败！");
							          }
								}
							}); 
					}
				});
			},
			sendMess :function(){
				var _this = this;
				var phone=$("#phone").val();
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
			changeImg:function (){  
				var _this = this;
			    var imgSrc = $(_this.settings.IMAGE_ID);     
			    var src = imgSrc.attr("src"); 
			    imgSrc.attr("src",_this.chgUrl(src));
			},
			//为了使每次生成图片不一致，即不让浏览器读缓存，所以需要加上时间戳     
			chgUrl:function(url){     
			    timestamp = (new Date()).valueOf(); 
			    var urlurl = url.substring(0, url.indexOf("getImage")) + "getImage";
			    urlurl = urlurl + "?timestamp=" + timestamp;     
			    return urlurl;     
			}
		}
	});
})(jQuery);	

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
    	<div class="zhuce_DJ"><p>沃云软件商密码重置</p></div>
    	<div class="zhuce_A"><p>欢迎您进入沃云软件商门户，请找回密码.</p></div>
    	<form  id="pwdResetForm" name="pwdResetForm" method="post" >
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
	     			<p class="yo_minz"><span>*</span>手机号码：</p>
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
				    <p class="yo_minz"><span>*</span>新密码：</p>
				    <p class="pput">
					    <span><input name="password" id="password" value="8-32位密码" type="text" class="input4" /></span>
					    <span class="pt_img"><img src="<%=bp %>/images/mm.png" /></span>
				    </p>  
				    <p><label class="label1" id="password_error"></label></p>
			    </li>
			    <li>
				    <p class="yo_minz"><span>*</span>确认新密码：</p>
				    <p class="pput">
					    <span><input name="password2" id="password2" value="8-32位密码" type="text" class="input4" /></span>
					    <span class="pt_img"><img src="<%=bp %>/images/mm.png" /></span>
				    </p>
				    <p><label class="label1" id="password2_error"></label></p>
			    </li>
			    <li>
				    <p class="yo_minz"><span>*</span>验证码：</p>
				    <p><input name="veryCode" id="veryCode" type="text" class="input6"  onkeypress="if(event.keyCode==13||event.which==13){$('#submit').click();}"/></p>
				    <p style="margin-top:7px;"><img id="imgObj" src="${_base}/verifyCode/getImage" /></p>
					<p><A href="javascript:void(0)" id ="switchImage">换一张</A></p>
				    <p><label class="label1" id="veryCode_error"></label></p>
			    </li>
			    <li><input type="submit" class="tij" style="margin-left:140px;border-color:#fff;background:url(<%=bp %>/images/tj.png);color:#fff; font-size:16px;repeat-x;" value="提交"  id="submit" name="submit"/></li>
     		</ul>
        </div> 
        </form>  
	</div>
    
    <%@ include file="/jsp/common/footer.jsp"%>         <!--------------------footer-->
</div>
</body>
</html>