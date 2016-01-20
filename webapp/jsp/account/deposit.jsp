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
	$("#menu_deposit").addClass("xunz");
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
  <li><A href="#">提现</A></li>
  </ul>
  </div>
  
  <div  class="er_tix">
     
     
     <div id="tx_js1">
     <div class="qieh_table">
     <ul>
     <li class="qie_b"><A href="#" onmousedown="a(1)">余额提现</A></li>
     <li><A href="#" onmousedown="a(2)">提现记录</A></li>
     </ul>
     </div>
     
     <div class="erji_jiben_input">
     <ul>
     <li class="qinz">请确认您的提现银行卡信息： </li>
     </ul>
 <ul>
 <li class="chag_li">
 <p class="ej_wz">银行开户名：</p>
 <p><input name="" type="text"  class="ej_input"/></p>
 </li>
 </ul>
 
 <ul>
 <li  class="chag_li">
 <p class="ej_wz">公司银行账号：</p>
 <p><input name="" type="text"  class="ej_input"/></p>
 </li>
 </ul>
  <ul>
 <li  class="chag_li">
 <p class="ej_wz">开户银行支行名称：</p>
 <p><input name="" type="text"  class="ej_input"/></p>
 </li>
 </ul>
  <ul>
 <li  class="chag_li">
 <p class="ej_wz">支行联行号：</p>
 <p><input name="" type="text"  class="ej_input"/></p>
 </li>
 </ul>
  <ul>
 <li  class="chag_li">
 <p class="ej_wz">开户银行所在地：</p>
 <p><input name="" type="text"  class="ej_input"/></p>
 </li>
 </ul>
  <ul>
 <li  class="chag_li">
 <p class="ej_wz">提现金额：</p>
 <p><input name="" type="text"  class="ej_input"/></p>
 </li>
 </ul>
	</div>
 <div class="erji_jiben_button">
 <ul>
 <li class="xiug_bot"><A href="#">提现</A></li>
 </ul>
 </div>
 </div>
 <div id="tx_js2" style=" display:none;">
     <div class="qieh_table">
     <ul>
     <li><A href="#" onmousedown="a(1)">余额提现</A></li>
     <li  class="qie_b"><A href="#" onmousedown="a(2)">提现记录</A></li>
     </ul>
     </div>
     
     <div class="erji_jiben_input">
     <ul>
     <li class="qinz">请确认您的提现银行卡信息2： </li>
     </ul>
 <ul>
 <li class="chag_li">
 <p class="ej_wz">银行开户名：</p>
 <p><input name="" type="text"  class="ej_input"/></p>
 </li>
 </ul>
 
 <ul>
 <li  class="chag_li">
 <p class="ej_wz">公司银行账号：</p>
 <p><input name="" type="text"  class="ej_input"/></p>
 </li>
 </ul>
  <ul>
 <li  class="chag_li">
 <p class="ej_wz">开户银行支行名称：</p>
 <p><input name="" type="text"  class="ej_input"/></p>
 </li>
 </ul>
  <ul>
 <li  class="chag_li">
 <p class="ej_wz">支行联行号：</p>
 <p><input name="" type="text"  class="ej_input"/></p>
 </li>
 </ul>
  <ul>
 <li  class="chag_li">
 <p class="ej_wz">开户银行所在地：</p>
 <p><input name="" type="text"  class="ej_input"/></p>
 </li>
 </ul>
  <ul>
 <li  class="chag_li">
 <p class="ej_wz">提现金额：</p>
 <p><input name="" type="text"  class="ej_input"/></p>
 </li>
 </ul>
	</div>
 <div class="erji_jiben_button">
 <ul>
 <li class="xiug_bot"><A href="#">提现</A></li>
 </ul>
 </div>
 </div>
     

  </div>

     
   
</div>
 </div>
</div>
<%@ include file="/jsp/common/footer.jsp"%>         <!--------------------footer-->
</body>
</html>

