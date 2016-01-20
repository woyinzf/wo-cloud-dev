<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<%@ include file="/jsp/common/common.jsp"%>
<title>沃云软件商门户</title>

<script src="${_base}/js/caidna.js" type="text/javascript"></script>
<script>
var myValid;
var basicInfoController;
var InterValObj; //timer变量，控制时间  
var count = 120; //间隔函数，1秒执行  
var curCount;//当前剩余秒数 
$(document).ready(function(){
	turnit(6,5);
	$("#menu_basicInfo").addClass("xunz");
	basicInfoController = new $.BasicInfoController();
	
	//初始化复制插件
	ZeroClipboard.setMoviePath("<%=request.getContextPath() %>/js/ZeroClipboard.swf");
	var clip = new ZeroClipboard.Client();
	$(window).resize(function(){
		   clip.reposition();
	});
	clip.setHandCursor(true);
	clip.glue("copyName");
	clip.addEventListener( "mouseDown", function(client) {
		clip.setText("${LOGIN_INFO.loginName }@softwareProvider");
	});
	//复制成功：
	clip.addEventListener( "complete", function(){
		 info_alert("复制成功！"); 	
	});
	
});
/*定义页面管理类*/
(function(){
	$.BasicInfoController  = function(){ 
		this.settings = $.extend(true,{},$.BasicInfoController.defaults); 
		this.init();
		
	};
	$.extend($.BasicInfoController,{
		defaults : {
			             FORM_ID : "#basicInfoForm",			      
			             EDIT_ID : "#edit",
			             SAVE_ID : "#save",
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
				$(_this.settings.MESS_VERYCODE_ID).bind("click",function(){				
					_this.sendMess();				
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
				$(_this.settings.EDIT_ID).bind("click",function(){
					//$("#save").toggle(function(){},function(){});
					$("#edit").hide();
					$("#save").toggle();
					$("#cancel").show();
					$(".info_text").hide();
					$("#edit_email").show();
					$("#edit_phone").show();
					$("#edit_sendVery").show();
					$("#edit_veryCode").show();
					$("#email").val($("#email")[0].defaultValue);
					$("#phone").val($("#phone")[0].defaultValue);
					$("#verycodeinput").val($("#verycodeinput")[0].defaultValue);
				});
				$("#cancel").bind("click",function(){
					//$("#save").toggle(function(){},function(){});
					$("#edit").show();
					$("#cancel").hide();
					$("#save").hide();
					$(".info_text").show();
					$("#edit_email").hide();
					$("#edit_phone").hide();
					$("#edit_sendVery").hide();
					$("#edit_veryCode").hide();
					$("#phone_error").text("");
					$("#email_error").text("");
					$("#verycodeinput_error").text("");
					
					
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
				        	  $("#verycodeinput_error").text("验证码已发送至您输入的手机号！有效期2分钟");
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
				myValid=$(_this.settings.FORM_ID).validate({
					rules : {
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
						email : {
							required : "请输入邮箱",
							email : "邮箱格式不正确"
						},
						phone : {
							required : "请输入手机号",
							remote  : "手机号与用户名不匹配"
						},
						verycodeinput :{
							required : "请输入验证码",
							remote   : "验证码不正确"
						}
					},
					submitHandler : function(form) {
						var edit_state=$(".info_text").css("display");						
						$(".info_text").show();
						$("#edit_email").hide();
						$("#edit_phone").hide();
						$("#edit_sendVery").hide();
						$("#edit_veryCode").hide();
						var email=$("#email").val();
						var phone=$("#phone").val();
						if(edit_state=="none"){
							//alert(myValid.valid());
							$.ajax({
								type : "POST",
								url : "${_base}/companyInfoAction/basicInfoEdit",
								async : false,
								dataType: 'json',
								data : {											
									email : email,
									phone : phone
								},
								success : function(data) {								
									var $json=eval(data);				    	  
					                  result = $json.result;				                  
							          if (result == "0") {						        	  						        	
							        	  $("#email_text").text(email);
							        	  $("#phone_text").text(phone);
							        	  info_alert("信息修改成功！",refresh); 						
							          }
							          else {
							        	  info_alert("信息修改失败！",refresh);
							          }
								}
							}); 						
						}
					}
				});
			}
		}
	});
})(jQuery);	

function refresh(){
	location.reload();
}
</script>  
</head>
  
<body>
<div class="wai_da">   
	<!--header -->
	<%@ include file="/jsp/common/header.jsp"%>
    
	<div class="erji_nei">
	    <!--leftMenu-->
	    <%@ include file="/jsp/common/leftMenu.jsp"%>
   
	   	<div class="erji_nei_right">
		   	<div class="erji_weiz">
			   	<ul>
				   	<li><A href="#">首页</A>&nbsp;></li>
				   	<li><A href="#">用户中心</A>&nbsp;></li>
				   	<li><A href="#">基本信息</A></li>
			   	</ul>
		  	</div>
		  	
		  	<div class="er_tix">     
     			<!-- <div class="erji_jiben_A_yongh"><p>基本信息</p></div> -->
     			<form  id="basicInfoForm" name="basicInfoForm" method="post" >    
     			<div class="erji_jiben_input">    
					<ul>
						<li class="chag_li">
							<p class="ej_wz">企业名称：</p>
							<p> ${companyInfo.companyName }</p>
						</li>
					</ul>
					<ul>
						<li  class="chag_li">
							<p class="ej_wz">企业地址：</p>
							<p>${companyInfo.companyAddress }</p>
						</li>
					</ul>
					<ul>
						<li  class="chag_li">
							<p class="ej_wz">企业组织机构代码：</p>
							<p>${companyInfo.companyCode }</p>
						</li>
					</ul>
					<ul>
						<li  class="chag_li">
							<p class="ej_wz">用户名：</p>
							<p>${LOGIN_INFO.loginName }</p>
						</li>
					</ul>
					<ul>
						<li  class="chag_li">
							<p class="ej_wz">沃云门户用户名：</p>
							<p>${LOGIN_INFO.loginName }@softwareProvider</p><p id="copyName" style="color: blue; margin-left: 10px;"><u>复制到剪贴板</u></p>
						</li>
					</ul>
					<ul>
						<li  class="chag_li">
							<p class="ej_wz">电子邮件：</p>
							<p class="info_text" id="email_text">${companyInfo.email }</p>
							<p id="edit_email" style="display:none;"><input name="email"  value="${companyInfo.email }" id="email" class="ej_input"  type="text" /></p>
							<p class="lan" style="color:red;" id="email_error"></p>
						</li>
					</ul>
					<ul>
						<li  class="chag_li">
							<p class="ej_wz">移动电话：</p>
							<p class="info_text" id="phone_text">${companyInfo.mobilePhone }</p>
							<p id="edit_phone" class="mima_er" style="display:none;">
								<span><input name="phone"  value="${companyInfo.mobilePhone }" class="mim_input" id="phone"  type="text" /></span>
								<span class="suo_er" style=" margin-top:4px;"><img src="<%=bp %>/images/sj.png" /></span>
							</p>
							<p id="edit_sendVery" style="display:none;"><input type="button"  id="mess_verycode" class="er_botton_a" disabled="disabled" value="获取短信验证码"/></p>
							<p class="lan" style="color:red;" id="phone_error"></p>
						</li>
					</ul>			
					<ul id="edit_veryCode" style="display:none;">
						<li  class="chag_li">
							<p class="ej_wz">短信验证码：</p>
							<p><input name="verycodeinput" value="请输入动态密码" id="verycodeinput" type="text"  class="ej_input" onkeypress="if(event.keyCode==13||event.which==13){$('#save').click();}"/></p>
							<p class="lan" style="color:red;" id="verycodeinput_error"></p>
						</li>
					</ul>				
				</div>
				<div class="erji_jiben_button" style=" margin-left:60px;">
					<ul>
						<input id="edit" name="" value="" type="button" class="z_button_edit" />
						<input id="save" style="display:none;" name="" value="" type="submit" class="z_button_save" />
						<input id="cancel" style="display:none;" name="" value="" type="button" class="z_button_cancel" />
					</ul>
				</div>
				</form>
			</div>	 
		</div>
	</div>
</div>
<!--footer-->
<%@ include file="/jsp/common/footer.jsp"%>
</body>
</html>

