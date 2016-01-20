<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/jsp/common/common.jsp"%>
<script type="text/javascript" src="${_base}/js/md5.js" /></script>
<title>沃云软件商门户</title>
</head>
<body>
	<div class="dengl">
		<!--header2 -->
	    <%@ include file="/jsp/common/header2.jsp"%>
		<div class="dengl_banner">
			<div class="dengl_banner_main">
				<div class="dengl_banner_login">
					<ul>
						<li class="wenz_d"><p>用户登录</p></li>
						<form id="form">
							<li class="yongh_m">
								<p class="m">用户名：</p>
								<p>
									<input id="loginName" name="loginName" type="text" class="input" /></p>
									<label class="lable" id="loginName_error"></label>
							</li>
							<li class="yongh_m">
								<p class="m">密码：</p>
								<p>
									<input id="loginPasswordPlaintext" name="loginPasswordPlaintext" type="password" class="input" />
									<input id="loginPassword" name="loginPassword" type="hidden" />
								</p>
								<label class="lable" id="loginPasswordPlaintext_error"></label>
							</li>
							<li class="yongh_m">
								<p class="m">验证码：</p>
								<p>
									<input id="veryCode" name="veryCode" type="text" class="input1" onkeypress="if(event.keyCode==13||event.which==13){$('#login').click();}" />
								</p>
								<p>
									<img id="imgObj" src="${_base}/verifyCode/getImage" />
								</p>
								<p>
									<a href="javascript:void(0)" id ="switchImage">换一张</a>
								</p>
								<label class="lable" id="veryCode_error"></label>
							</li>
						</form>
						<li class="dl_zc">
							<p>
								<a id="login" href="javascript:void(0)">登录</a>
							</p>
							<p class="hui">
								<a href="${_base }/loginInfoAction/toRegisterPage">注册</a>
							</p>
						</li>
						<li class="mim"><a href="${_base }/loginInfoAction/toPwdReset">忘记密码？</a></li>
					</ul>
				</div>
			</div>
		</div>
		<div class="dengl_neir">
			<ul>
				<li class="tu"><img src="<%=bp %>/images/01.png" /></li>
				<li class="wenz">
					<p class="menh">平台介绍</p>
					<p>中国联通沃云软件商门户是专门面对广大软件提供方的服务门户，软件提供方可以在门户中配置软件产品信息，与沃云签订软件售卖协议，管理在沃云售卖的软件。</p>
				</li>
			</ul>
			<ul>
				<li class="tu"><img src="<%=bp %>/images/02.png" /></li>
				<li class="wenz">
					<p class="menh">服务优势</p>
					<p>在线销售——节省渠道成本；</p>
					<p>沃云IaaS——经济安全可靠；</p>
					<p>灵活计费——吸引广大用户；</p>
					<p>订单跟踪——获取使用反馈。</p>
				</li>
			</ul>
			<ul>
				<li class="tu"><img src="<%=bp %>/images/03.png" /></li>
				<li class="wenz">
					<p class="menh">服务流程</p>
					<p>入驻申请：提交软件商入驻申请及相关资质；</p>
					<p>签约申请：提交软件产品签约售卖申请；</p>
					<p>产品上架：软件产品与IaaS产品包装，上架销售；</p>
					<p>收入结算：沃云产品的销售收入结算到软件商账户。</p>
				</li>
			</ul>
			<ul>
				<li class="tu"><img src="<%=bp %>/images/04.png" /></li>
				<li class="wenz">
					<p class="menh">软商资质</p>
					<P>  &nbsp;&nbsp;&nbsp;&nbsp;企业执照</P>
					<P>  &nbsp;&nbsp;&nbsp;&nbsp;产品认证</P>
					<P>  &nbsp;&nbsp;&nbsp;&nbsp;企业认证</P>
					<P>  &nbsp;&nbsp;&nbsp;&nbsp;组织编码</P>
				</li>
			</ul>
		</div>
		<%@ include file="/jsp/common/footer.jsp"%>         <!--------------------footer-->
	</div>
	
	<div style = "margin-left: 20px;">
		<span id="success">登录成功！</span>
		<span id="failure">登录失败！</span>
	</div>
	
<!-- js -->
<script  type="text/javascript">
	var timestamp;
	var loginManager;
	$(document).ready(function(){
		loginManager = new $.LoginManager();
	});
	
	(function(){
		$.LoginManager = function(){
			this.settings = $.extend(true,{},$.LoginManager.defaults);
			this.init();
		};
		$.extend($.LoginManager,{
			defaults:{
				LOGIN_NAME : "#loginName",
				LOGIN_BUTTON : "#login",
				VERYCODE_INPUT : "#veryCode",
				IMAGE_ID : "#imgObj",
				SWITCH_ID : "#switchImage",
				FORM_ID : "#form",
				SUCCESS_ID : "#success",
				FAILURE_ID : "#failure"
			},
			prototype : {
				init:function(){
					var _this = this;
					_this.bindEvents();
					_this.changeImg();
					$(_this.settings.SUCCESS_ID).hide();
					$(_this.settings.FAILURE_ID).hide();
				},
				bindEvents:function(){
					var _this = this;
					$(_this.settings.LOGIN_BUTTON).bind("click",function(){
						_this.logon();
					});
					$(_this.settings.SWITCH_ID).bind("click",function(){
						_this.changeImg();
					});
					$(_this.settings.IMAGE_ID).bind("click",function(){
						_this.changeImg();
					});
					$(".input, .input1").bind("focus",function(){
						var elementId = $(this).attr("id");
						$("#" + elementId + "_error").text("");
						if(elementId=="loginPasswordPlaintext"){
							$("#loginName_error").text("");
						}
					});			

				},
				logon:function(){
					if ($("#loginName").val() == "" || $("#loginPasswordPlaintext").val() == "") {
						$("#loginName_error").text("用户名或密码为空");
						
						return false;
					} else {
						$("#loginPassword").val(hex_md5($("#loginPasswordPlaintext").val()));
					}
					if ($("#veryCode").val() == "") {
						$("#veryCode_error").text("验证码为空");
						$("#loginPassword").val("");
						return false;
					}
					var _this = this;
					var url="${_base}/loginInfoAction/login?timestamp="+timestamp;
					var param=$(_this.settings.FORM_ID).serialize();
					$.ajax({
						async: false,
						type:"POST", 
						url:url,
						modal: true,
						showBusi:false, 
						data:param,
						success:function(data){
							var $json=$.parseJSON(data);
							var status = $json.status;
							if (status == "0"){
								if ($json.result=="success") {
									window.location.href = "${_base}/loginInfoAction/toIndex";
								} else {
									$("#success").hide();
									$("#failure").show();
									$("#failure").html("登录失败错误代码："+status);
								}
				        	} else if(status == '1') {
				        		$("#veryCode_error").text("验证码为空");
				        		$("#loginPasswordPlaintext").val("");
				        		$("#veryCode").val("");
				        		$("#switchImage").click();
				        	} else if(status == "2") {
				        		$("#veryCode_error").text("验证码错误");
				        		$("#loginPasswordPlaintext").val("");
				        		$("#veryCode").val("");
				        		$("#switchImage").click();
				        	} else if(status == "3") {
				        		$("#loginName_error").text("用户名或密码为空");
				        		$("#loginPasswordPlaintext").val("");
				        		$("#veryCode").val("");
				        		$("#switchImage").click();
				        	} else if (status == "4") {
				        		$("#loginName_error").text("用户名密码错误");
				        		$("#loginPasswordPlaintext").val("");
				        		$("#veryCode").val("");
				        		$("#switchImage").click();
				        	}else if (status == "5") {
				        		$("#loginName_error").text("该用户已被冻结");
				        		$("#loginPasswordPlaintext").val("");
				        		$("#veryCode").val("");
				        		$("#switchImage").click();
				        	}
						}
					});
					return false;
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
</script>
</body>
</html>