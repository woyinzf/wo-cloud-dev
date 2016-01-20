<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/lib/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/lib/fn.tld" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<%@ include file="/jsp/common/common.jsp"%>
<title>沃云软件商门户</title>
<script src="${_base}/js/caidna.js" type="text/javascript"></script>
<script src="${_base}/js/jquery.form.js" type="text/javascript"></script>
<script src="<%=bp%>/js/PopupDiv_v1.0.js" type="text/javascript"></script>
<script src="<%=bp%>/js/utils.js" type="text/javascript"></script>
<link href="<%=bp %>/css/css.css" rel="stylesheet" type="text/css" />
<link href="<%=bp %>/css/PopupDiv.css" rel="stylesheet" type="text/css" />
<script>
var upgradeController;
$(document).ready(function(){
	turnit(6,2);
	$("#menu_spShowAllSoftware").addClass("xunz");
	upgradeController = new $.UpgradeController();
});
/*定义页面管理类*/
(function(){
	$.UpgradeController  = function(){ 
		this.settings = $.extend(true,{},$.UpgradeController.defaults); 
		this.init();
		
	};
	$.extend($.UpgradeController,{
		defaults : {
			             FORM_ID : "#myForm",			      
			             EDIT_ID : "#edit",
			             SAVE_ID : "#save",
			          UPLOAD1_ID : ".upload1",
			          UPLOAD2_ID : ".upload2",
			          UPLOAD3_ID : ".upload3",
			           SUBMIT_ID : "#submit",
			    VALIDATEINPUT_ID : ".validateInput"
		},
		prototype : {
			init : function(){
				var _this = this;
				_this.addRults();
				_this.bindEvents();				
			},
			bindEvents : function(){
				var _this = this;
				$(_this.settings.UPLOAD1_ID).bind("click",function(){
					$("#myfile1").click();
					
				});
				$(_this.settings.UPLOAD2_ID).bind("click",function(){
					$("#myfile2").click();
					
				});
				$(_this.settings.UPLOAD3_ID).bind("click",function(){
					$("#myfile3").click();
					
				});
				$(_this.settings.VALIDATEINPUT_ID).bind("focus",function(){				
					$("#"+$(this).attr("id")+"_error").text("");
				});
				$(_this.settings.SUBMIT_ID).bind("click",function(){
					if(_this.toValidate()){
						_this.toUpgrade();
					}
				});
				
			},
			addRults : function() {
				var _this = this;				
			},
			toUpgrade : function(){
				var _this = this;
				var params=$(_this.settings.FORM_ID).serialize();				
				getMask();
				var options = {
		    		  async : false,
		  		      type: "POST",
		  		      showBusi : false,
		  		      modal : true,
		  		      contentType:'application/x-www-form-urlencoded; charset=UTF-8',
		  		      url: "${_base}/software/upgradeSoftware",
		  		      dataType: 'text',
		  		      data:params, 		  		   
		  		      success: function (data) {
		            	var json=$.parseJSON(data);
		            		$("#showWait").hide();
				    	  if(json&&json.RES_RESULT=="SUCCESS"){					    		  
				    		  info_alert(json.RES_MSG,jump_to_sp);
				    	  }else{
				    		  info_alert(json.RES_MSG, jump_to_sp);
				    	  }
		           	
		              }
		    	  };
		    	 $(_this.settings.FORM_ID).ajaxSubmit(options);
			},
			toValidate : function(){				
				var softwareVersion_val=$("#softwareVersion").val();
				var softwareIntroduce_val=$("#softwareIntroduce").val();
				if(softwareIntroduce_val ==""||softwareIntroduce_val==null){
					$("#softwareIntroduce_error").text("请输入软件介绍！");
					var scroll_offset = $("#softwareIntroduce").offset(); 
					  $("body,html").animate({
					   scrollTop:scroll_offset.top-100 
					   },300);
					return false;
				}
				if(softwareVersion_val ==""||softwareVersion_val==null){
					$("#softwareVersion_error").text("请输入软件版本！");
					 var scroll_offset = $("#softwareVersion").offset();  
					  $("body,html").animate({
					   scrollTop:scroll_offset.top 
					   },300);
					return false;
				}
				if(!$("#checkbox_input").is(":checked")){
					$("#checkbox_input_error").text("请阅读并同意《中国联通沃云软件商门户服务协议》");
					return false;
				}
				return true;				
			}
		}
	});
})(jQuery);	

function showFileName(tip){
	$("#upload"+tip).val($("#myfile"+tip).val());	
}
function jump_to_sp(){
	location.href="${_base }/software/spfindAllSoftware?orderCond=command_time&orderType=desc";
}
function getMirrorName(){
	var mirrorName=$("#softwareConfigUrl").find("option:selected").text(); 
	$("#mirrorName").val(mirrorName);
}

</script>  

</script>
</head>

<body> 
<form  id="myForm" name="myForm" method="post" enctype="multipart/form-data">  
<input type="text"  name="softId"  style="display:none" value="${softBasic.id }"/>
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
  <li><A href="#">软件管理</A>&nbsp;></li>
  <li><A href="${_base }/software/spfindAllSoftware?orderCond=command_time&orderType=desc">我的软件</A>&nbsp;></li>
  <li><A href="#"><c:out value="${softBasic.softwareName }"></c:out></A></li>
  </ul>
  </div>
 
 <div class="erji_jiben">    
 
 <div class="erji_jiben_A"><P>基本信息</P></div>
 <div class="erji_jiben_input">
 <ul>
 <li>
 <p class="ej_wz">软件种类：</p>
 <p>
 	<c:if test="${softBasic.softwareKind == '1'}">
 	基础软件
 	</c:if>
 	<c:if test="${softBasic.softwareKind == '2'}">
 	应用软件软件
 	</c:if>
 	<c:if test="${softBasic.softwareKind == '3'}">
 	服务软件
 	</c:if>
 
 </p>
 </li>
 <li>
 <p class="ej_wz">软件名称：</p>
 <p><c:out value="${softBasic.softwareName }"></c:out></p>
 </li>
 </ul>
 
 <ul>
 <li>
 <p class="ej_wz">软件提供方：</p>
 <p><c:out value="${softBasic.softwareOffer }"></c:out></p>
 </li>
 <li>
 <p class="ej_wz">软件类型：</p>
 <p><c:out value="${softBasic.softwareType }"></c:out></p>
 </li>
 </ul>
 
 <ul>
 <li>
 <p class="ej_wz">软件版本号：</p>
 <p><c:out value="${versionSplit }"></c:out></p>
 </li>
 <li>
 <p class="ej_wz">操作系统：</p>
 <p><c:out value="${softBasic.operateSystem }"></c:out></p>
 </li>
 </ul>
 
	<ul>
 <li style=" width:870px; margin-right:0px;">
 <p class="ej_wz">软件介绍：</p>
 <p><textarea id="softwareIntroduce" name="softwareIntroduce"  value="" class="ej_input_gao validateInput" >${softBasic.softwareIntroduce }</textarea> </p>
 </li>
 <p><label style="margin-left:130px;color:red;" id="softwareIntroduce_error"></label></p>
 </ul>
 
 
  <ul>
 <li class="chag_li">
 <p class="ej_wz">图片：</p>
 <p style=" height:150px;wight:150px;"><img height=150 width=150 src="${baseUrl}${softBasic.softwareImgUrl }"/></p>
 </li>
 </ul>
 
 <ul>
	<li class="chag_li">
		<p class="ej_wz">售后服务：</p>
		<p>${serviceData }</p>
	</li>
</ul>
<ul>
	<li class="chag_li">
		<p class="ej_wz">分成比例：</p>
		<p>${softBasic.softwareSellProportion }</p>
	</li>
</ul>
   <ul>
 <li class="chag_li">
 <p class="ej_wz">使用说明书：</p>
 <p class="upload1"><input type="text"  name="upload1" readonly="readonly" class="ej_input" id="upload1" value="${filename1 }"/></p>
 <p><input type="button" value="浏览"  class="er_botton_a upload1"/></p>
 <input type="file" id="myfile1" onchange="showFileName(1);" style="display:none;" name="myfile1"/>
 </li>
 </ul>
  <ul>
 <li class="chag_li">
 <p class="ej_wz">配置说明书：</p>
 <p class="upload2"><input type="text"  name="upload2" readonly="readonly" class="ej_input" id="upload2" value="${filename2 }"/></p>
 <p><input type="button" value="浏览"  class="er_botton_a upload2"/></p>
 <input type="file" id="myfile2" onchange="showFileName(2);" style="display:none;" name="myfile2"/>
 </li>
 </ul>
 
 </div>
  <div class="erji_jiben_A"><P>价格信息</P></div>

 </div>
  <div class="erji_table">
 
<table width="100%" border="1">
  <tr valign="middle" bgcolor="#f8f8f8">
    <td align="center">产品类型</td>
    <td align="center">服务器配置</td>
    <c:if test="${softBasic.softwareKind != '1'}">
    <td align="center">软件规格</td>
    </c:if>
    <td align="center">软件包月价格</td>
    <td align="center">软件包年价格</td>

  </tr>
  <c:forEach items="${vminfolist }"  var="vminfo">
  <tr>
    <td align="center" valign="middle"><c:out value="${vminfo.productType }"></c:out> </td>
    <td align="center" valign="middle"><c:out value="${vminfo.name }"></c:out>：CPU<c:out value="${vminfo.cpuValue }"></c:out>核、内存<c:out value="${vminfo.desc }"></c:out>、硬盘<c:out value="${vminfo.osDisk }"></c:out>G  </td>
    <c:if test="${softBasic.softwareKind != '1'}">
    	<td align="center" valign="middle"><c:out value="${vminfo.productEtalon }"></c:out> </td>
    </c:if>
    <td align="center" valign="middle"><c:out value="${vminfo.monthPrices }"></c:out>（元） </td>
    <td align="center" valign="middle"><c:out value="${vminfo.yearPrices }"></c:out>（元） </td>

  </tr>
  </c:forEach>
</table>
</td>
    <td align="center" valign="middle">&nbsp;</td>
  </tr>
</table>
 </div>
 <div class="erji_jiben_A">
   <P>镜像信息</P>
 </div>
 <div class="erji_jiben_input">
 <c:forEach  items="${versionAndGohlist}" var="versionAndGoh">
  <ul>
 <li>
 <p class="ej_wz">软件版本：</p>
 <p>${versionAndGoh.version }</p>

 </li>
 <li>
 <p class="ej_wz">镜像名称：</p>
 <p>${versionAndGoh.gohName }</p>
 </li>
 </ul>
 </c:forEach>
   <ul>
 <li>
 <p class="ej_wz">软件版本：
 <p><input id="softwareVersion" type="text"  name="softwareVersion" class="ej_input validateInput"/></p>
  <p><label style="margin-left:130px;color:red;" id="softwareVersion_error"></label></p>

 </li>
 <li>
 <p class="ej_wz">镜像名称：</p>
 <p><select id="softwareConfigUrl" onchange="getMirrorName()" name="softwareConfigUrl" class="ej_xiala">
	<c:forEach  items="${mirrorlist}" var="mirror">
		<option value="${mirror.imageid }">${mirror.imagename }</option>
	</c:forEach>	
	</select>
 </p>
 <input type="text"  id="mirrorName" name="mirrorName"  style="display:none" value="${mirrorlist[0].imagename }"/>
 </li>
 </ul>
 </div>
 
 <div class="erji_jiben_A"><P>认证信息</P></div>
 <div class="erji_jiben_input">
  <ul>
 <li class="chag_li">
 <p class="ej_wz" >软件销售授权书：</p>
 <p class="upload3"><input name="upload3" type="text"  readonly="readonly" class="ej_input" id="upload3" value="${filename3}"/></p>
 <p><input type="button" value="浏览"  class="er_botton_a upload3"/></p>
 <input type="file" id="myfile3" onchange="showFileName(3);" style="display:none;" name="myfile3"/>
 </li>
 </ul>
  <ul>
	<li class="chag_li">
		<p><input name="" type="checkbox" id="checkbox_input" value=""  class="fanx validateInput"/></p>
		<p onclick="chackedBox()" id="aid" style="color:blue">我已阅读并同意《中国联通沃云软件商门户服务协议》</p>
		<p><label style="margin-left:130px;color:red;" id="checkbox_input_error"></label></p>
	</li>
</ul>
 </div>

 <div class="erji_jiben_button">
 <ul>
 <li><A id="submit" href="javascript:void(0)">提交审核</A></li>
 <li><A  href="${_base }/software/spfindAllSoftware?orderCond=command_time&orderType=desc">取消</A></li>
 </ul>
 </div>


</div>
 </div>
</div>
</form>
<!--footer-->
<%@ include file="/jsp/common/footer.jsp"%>
</body>
</html>
