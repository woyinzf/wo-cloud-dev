<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<%@ include file="/jsp/common/common.jsp"%>
<title>沃云软件商门户</title>
<script src="${_base}/js/caidna.js" type="text/javascript"></script>
<script>
$(document).ready(function(){
	turnit(6,4);
	$("#menu_accountTotal").addClass("xunz");
	$("#head_accoutBasic").addClass("shoy");
});
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
  <li><A href="#">账户管理</A>&nbsp;></li>
  <li><A href="#">账户总览</A></li>
  </ul>
  </div>
  
  <div class="yu_er">
  <ul>
  <li><img src="<%=bp %>/images/yue_1.png" /></li>
  <li class="yu_bj">
  <p>尊敬的${LOGIN_INFO.loginName }，您好！</p>
  <p>目前您在沃云软件商城上已经上架了<span>${upSoftNum }</span>款软件产品，共计销售出<span>${selloutNums}</span>套软件产品，目前您的可用余额为<span>${Balance }</span>元。</p>
  </li>
  </ul>
  </div>

     
   
</div>
 </div>
</div>
<%@ include file="/jsp/common/footer.jsp"%>         <!--------------------footer-->
</body>
</html>


