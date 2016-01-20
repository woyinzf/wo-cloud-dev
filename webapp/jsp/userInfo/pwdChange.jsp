<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<%@ include file="/jsp/common/common.jsp"%>
<title>沃云软件商门户</title>
<script type="text/javascript" src="${_base}/js/md5.js" /></script>
<script src="${_base}/js/caidna.js" type="text/javascript"></script>
<script>
var pwdChangeController;
$(document).ready(function(){
	turnit(6,5);
	$("#menu_pwdChange").addClass("xunz");
	pwdChangeController = new $.PwdChangeController();
});
/*定义页面管理类*/
(function(){
	$.PwdChangeController  = function(){ 
		this.settings = $.extend(true,{},$.PwdChangeController.defaults); 
		this.init();
		
	};
	$.extend($.PwdChangeController,{
		defaults : {
			             FORM_ID : "#pwdChangeForm",			      
			     OLD_PASSWORD_ID : "#oldPassword",
			     NEW_PASSWORD_ID : "#newPassword",
			    NEW_PASSWORD2_ID : "#newPassword2",
			        
		},
		prototype : {
			init : function(){
				var _this = this;
				_this.addRults();
				_this.bindEvents();				
			},
			bindEvents : function(){
				var _this = this;
				$(".mim_input").bind("focus",function(){
					if($(this).val()==this.defaultValue){
						$(this).attr("type","password");
						$(this).val("");
					}
				});
				$(".mim_input").bind("blur",function(){				
					if($(this).val()==""){
						$(this).attr("type","text");
						$(this).val(this.defaultValue);
					}
				});
				
			},			
			addRults : function() {				
				var _this = this;
				jQuery.validator.addMethod("notEqual",
		                function (value, element, params) {
		                    var oldPwd = $(params).val();
		                    return oldPwd == value ? false : true;
		                }, "新旧密码不能相同");
				$(_this.settings.FORM_ID).validate({
					rules : {
						oldPassword : {
							required : true,
							rangelength:[8,16],
							remote:{                                          
					               type:"POST",
					                url:"${_base}/loginInfoAction/oldPwdVery",            
					                data:{
					                 	password:function(){return hex_md5($(_this.settings.OLD_PASSWORD_ID).val());} 
					                }  
					        }
						},
						newPassword : {
							required : true,
							rangelength:[8,16],
							notEqual:'#oldPassword'
						},
						newPassword2 : {
							required:true,
							equalTo : '#newPassword'
						}
				
					},
					onfocusout : function(element) {
						jQuery(element).valid();
					},
					success:function(error, element){
						var elementId = $(element).attr("id");						
						$("#" + elementId + "_error").text($(error).text());
					},
					errorPlacement : function(error, element) {
						var elementId = $(element).attr("id");						
						$("#" + elementId + "_error").text($(error).text());
					},
					messages : {
						oldPassword : {
							required : "请输入原密码",
							rangelength : "请输入8-16位密码,区分大小写",
							remote : "原密码输入错误"
						},
						newPassword : {
							required : "请输入新密码",
							rangelength : "请输入8-16位密码,区分大小写"
						},
						newPassword2 : {
							required : "请输入确认密码",
							equalTo : "两次输入的密码不一致"
						}
					},
					submitHandler : function(form) {
					 	$(_this.settings.SUBMIT_ID).attr("disabled", true);
						$(_this.settings.SUBMIT_ID).val("修改中....");
						
						var inputPassword = $(_this.settings.NEW_PASSWORD_ID).val();
						var passwordMd5 = hex_md5(inputPassword);	
						
						$.ajax({
							type : "POST",
							url : "${_base}/companyInfoAction/pwdChange",
							async : false,
							dataType: 'json',
							data : {											
								password : passwordMd5											  					  			   
							},
							success : function(data) {								
								var $json=eval(data);				    	  
				                  result = $json.result;
				                  $(".mim_input").attr("type","text");
				                  $("#oldPassword").val($("#oldPassword")[0].defaultValue);
					        	  $("#newPassword").val($("#newPassword")[0].defaultValue);
					        	  $("#newPassword2").val($("#newPassword2")[0].defaultValue);
						          if (result == "0") {	        	  
						        	  info_alert("密码修改成功！");
						          }
						          else {					
						        	  info_alert("密码修改失败！");
						          }
							}
						}); 
					}
				});
			}
		}
	});
})(jQuery);	
</script>  
</head>
  
<body>
	<div class="wai_da">   		
		<%@ include file="/jsp/common/header.jsp"%>              <!-------------------header --> 
    	<div class="erji_nei">  		
   			<%@ include file="/jsp/common/leftMenu.jsp"%>        <!-------------------leftMenu-->   
 			<div class="erji_nei_right">
  				<div class="erji_weiz">
  					<ul>
  						<li><A href="#">首页</A>&nbsp;></li>
						<li><A href="#">用户中心</A>&nbsp;></li>
						<li><A href="#">修改密码</A></li>
  					</ul>
  				</div>

  				
  				 <div class="er_tix">
  				   <form  id="pwdChangeForm" name="pwdChangeForm" method="post" >
					 <div class="erji_jiben_input"> 
					 <ul>
					 <li class="chag_li">
					 <p class="ej_wz">请输入原密码：</p>
					 <p class="mima_er">
					 <span><input name="oldPassword" value="8-16位密码" id="oldPassword" type="text"  class="mim_input"/>
					 </span>
					 <span class="suo_er"><img src="<%=bp %>/images/mm.png" /></span>
					 </p>
					 <p><label class="label1" style="color:red;" id="oldPassword_error"></label></p>
					
					 </li>
					 </ul>
					 
					 <ul>
					 <li class="chag_li">
					 <p class="ej_wz">请输入新密码：</p>
					 <p class="mima_er">
					 <span><input name="newPassword" value="8-16位密码" id="newPassword" type="text"  class="mim_input"/>
					 </span>
					 <span class="suo_er"><img src="<%=bp %>/images/mm.png" /></span>
					 </p>
					 <p><label class="label1" style="color:red;" id="newPassword_error"></label></p>
					 </li>
					 </ul>
					 
					 <ul>
					 <li class="chag_li">
					 <p class="ej_wz">请确认新密码：</p>
					 <p class="mima_er">
					 <span><input name="newPassword2" value="8-16位密码" id="newPassword2" type="text"  class="mim_input"/>
					 </span>
					 <span class="suo_er"><img src="<%=bp %>/images/mm.png" /></span>
					 </p>
					 <p><label class="label1" style="color:red;" id="newPassword2_error"></label></p>
					 </li>
					 </ul>
					 
					 </div>
					  <div class="erji_jiben_button" style=" margin-left:60px;">
						<ul>						
							<input id="save" name="submit" id="submit" value="" type="submit" class="z_button_save" />
						</ul>
					  </div>
	 			</form>
	 			</div>
			</div>
		</div>
	</div>
	<%@ include file="/jsp/common/footer.jsp"%>         <!--------------------footer-->
</body>
</html>
