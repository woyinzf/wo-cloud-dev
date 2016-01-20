   <%@ page language="java" pageEncoding="UTF-8"%>
   <link href="<%=bp %>/css/css.css" rel="stylesheet" type="text/css" />
    <div id="bg" class="bg" ></div>
    <div id="show" class="waite" >
 </div>
   <div class="top_wai">
   <div class="top_wai_A">
   <p><span><a href="${_base }/companyInfoAction/toBasicInfo">${LOGIN_INFO.loginName }</a></span>，您好！欢迎来到沃云软件商门户！<a href="${_base }/loginInfoAction/logout"> [退出]</a></p>
   <p class="xiug">
   <span class="dinx"><a href="http://www.wocloud.cn" target="_blank">沃云首页</a></span>
   <span><a href="#">联系我们</a></span>
   </p>
   </div>
   </div>
    <div class="erji_top">
    
    <div class="erji_top_main">
    <div class="erji_top_main_logo"><a href="${_base }/loginInfoAction/toIndex"><img src="<%=bp %>/images/login_1.png" /></a></div>
    <div class="erji_top_main_daoh">
    <ul>
    <li id="head_guide"><a href="${_base }/loginInfoAction/toIndex">操作向导</a></li>
    <li id="head_mySoftware"><a href="${_base }/software/spfindAllSoftware?orderCond=command_time&orderType=desc">我的软件</a></li>
    <li id="head_accoutBasic"><a href="${_base }/accountInfoAction/toAccountTotal">账户总览</a></li>
    <li id="head_systemAnnouncement"><a href="javascript:void(0)" onclick="waite('head_myApp')">系统公告</a></li>
    <li id="head_mesCenter"><a href="javascript:void(0)" onclick="waite('head_mesCenter')">消息中心</a></li>
    <li id="head_help"><a href="javascript:void(0)" onclick="waite('head_help')">系统帮助</a></li>
  
    </ul>
    </div>
    </div>
     </div>
        
  <script type="text/javascript"> 
<!--
function waite(head)
{
	
	removeall();
	document.getElementById("bg").style.display ="block";
	document.getElementById("show").style.display ="block";
	$("#"+head).addClass("shoy");
	setInterval("close()",1000);
	
	
}

function close()
{
	document.getElementById("bg").style.display ='none';
    document.getElementById("show").style.display ='none';
    window.location.reload();
}
function removeall()
{
	$("#head_guide").removeClass("shoy");
	$("#head_myApp").removeClass("shoy");
	$("#head_accoutBasic").removeClass("shoy");
	$("#head_mesCenter").removeClass("shoy");
	$("#head_help").removeClass("shoy");
}
//-->
</script>
     
