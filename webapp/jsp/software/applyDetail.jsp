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
<link href="<%=bp %>/css/css.css" rel="stylesheet" type="text/css" />
</head>

<body>   
<script type="text/javascript">
$(document).ready(function(){
	turnit(6,2);
});
//下载
function down(downUrl,downName){

	downName = encodeURI(encodeURI(downName));
	window.location.href = "${_base}/software/download?downName="+downName;

}



</script>
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
  <c:if test="${tag == '1'}">
	  <li><A href="${_base }/software/myApplyList?orderCond=application_time&orderType=desc">我的申请单</A>&nbsp;></li>
  </c:if>
  <c:if test="${tag == '0'}">
	  <li><A href="${_base }/software/spfindAllSoftware?orderCond=command_time&orderType=desc">我的软件</A>&nbsp;></li>
  </c:if>
  <li><A href="#"><c:out value="${softBasic.softwareName }"></c:out></A></li>
  </ul>
  </div>
 
 <div class="erji_jiben">
 <c:if test="${tag == '1'}">
   <div class="dingd_zt">
   <p>申请单号：<c:out value="${applyBasic.applyId }"></c:out></p>
   <p>状态：<c:out value="${applyBasic.applyStatus }"></c:out></p>
   <p>类型：<c:out value="${applyBasic.applyType }"></c:out></p>
   </div>
  </c:if>
   

 
 
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
 	应用软件
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
 <p><c:out value="${softBasic.softwareVersions }"></c:out></p>
 </li>
 <li>
 <p class="ej_wz">操作系统：</p>
 <p><c:out value="${softBasic.operateSystem }"></c:out></p>
 </li>
 </ul>
 
	<ul>
 <li style=" width:870px; margin-right:0px;">
 <p class="ej_wz">软件介绍：</p>
 <p class="zi_c"><c:out value="${softBasic.softwareIntroduce }"></c:out></p>
 </li>
 </ul>
 
 
  <ul>
 <li class="chag_li">
 <p class="ej_wz">图片：</p>
 <p style=" height:150px;wight:150px;"><img height=150 width=150 src="${baseUrl }${softBasic.softwareImgUrl }"/></p>
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
 <p>${softBasic.softwareSellProportion }%</p>
 </li>
 </ul>
   <ul>
 <li class="chag_li">
 <p class="ej_wz">使用说明书：</p>
 <p><a onclick="down('${baseUrl }','${softBasic.softwareSpecificationUrl}')"><c:out value="${softBasic.softwareSpecificationUrl}"></c:out></a></p>
 </li>
 </ul>
  <ul>
 <li class="chag_li">
 <p class="ej_wz">配置说明书：</p>
 <p><a onclick="down('${baseUrl }','${softBasic.softwareConfigSpecificationUrl }')"><c:out value="${softBasic.softwareConfigSpecificationUrl }"></c:out></a></p>

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
  <ul>
 </ul>
  <ul>
 <li class="chag_li">
 <p class="ej_wz">镜像名称：</p>
 <p><c:out value="${softBasic.ghoName }"></c:out></p>
 </li>
 </ul>
 </div>
 
 <div class="erji_jiben_A"><P>认证信息</P></div>
 <div class="erji_jiben_input">
  <ul>
 <li class="chag_li">
 <p class="ej_wz" >软件销售授权书：</p>
 <p><a onclick="down('${baseUrl }','${softBasic.softwareSellAuthorizationUrl }')"><c:out value="${softBasic.softwareSellAuthorizationUrl}"></c:out></a></p>
 </li>
 </ul>
  
 </div>

 <div class="erji_jiben_button">
 <ul>

 <li><A  href="javascript:history.go(-1)">返 回</A></li>
 </ul>
 </div>


</div>
 </div>
</div>
<!--footer-->
<%@ include file="/jsp/common/footer.jsp"%>
</body>
</html>
