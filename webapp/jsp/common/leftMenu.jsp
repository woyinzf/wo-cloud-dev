   <%@ page language="java" pageEncoding="UTF-8"%>
   <link href="<%=bp %>/css/css.css" rel="stylesheet" type="text/css" />
   <style>
   a { 
	outline: none; 
	} 
   
   </style>
<div class="erji_nei_left">
	<ul>
    	<li onClick="turnit(6,2);">
    		<a href="#" hidefocus="true">
    			<p>软件管理</p>
    			<p class="img"><img src="<%=bp %>/images/a.png" id="img2" /></p>
    		</a>
	    	<li class="yinc" id="content2" style=" display:none;">
	    		<p id="menu_spShowAllSoftware"><a hidefocus="true" href="${_base }/software/spfindAllSoftware?orderCond=command_time&orderType=desc"><span>我的软件</span><span class="img1"><img src="<%=bp %>/images/c.png" /></span></a></p>
	    		<p id="menu_newSoftwareApply"><a hidefocus="true" href="${_base }/software/myApplyList?orderCond=application_time&orderType=desc"><span>我的申请单</span><span class="img1"><img src="<%=bp %>/images/c.png" /></span></a></p>
	    		<p><a href="#" hidefocus="true"><span>库存管理</span><span class="img1"><img src="<%=bp %>/images/c.png" /></span></a></p>
	    	</li>
    	</li>
    </ul>
    <ul>
    	<li>
    		<a href="javascript:void(0)" onclick="waite('head_myApp')" hidefocus="true">
			    <p>统计报表</p>
			    <p class="img"><img src="<%=bp %>/images/a.png" id="img3" /></p>
		    </a>
   		 </li>
    </ul>
    <ul>
	    <li onClick="turnit(6,4);">
		    <a href="#" hidefocus="true">
			    <p>账户管理</p>
			    <p class="img"><img src="<%=bp %>/images/a.png" id="img4"/></p>
		    </a>
		    <li class="yinc" id="content4" style=" display:none;">
			    <p id="menu_accountTotal"><a hidefocus="true" href="${_base }/accountInfoAction/toAccountTotal"><span>账户总览</span><span class="img1"><img src="<%=bp %>/images/c.png" /></span></a></p>
			    <p id="menu_accountBalance"><a hidefocus="true" href="${_base }/accountInfoAction/toAccountBalance"><span>销售明细</span><span class="img1"><img src="<%=bp %>/images/c.png" /></span></a></p>
			    <p id="menu_billDetail"><a hidefocus="true" href="${_base }/accountInfoAction/toBillDetail"><span>收入查询</span><span class="img1"><img src="<%=bp %>/images/c.png" /></span></a></p>
			    <p id="menu_deposit"><a hidefocus="true" href="javascript:void(0)" onclick="waite('head_myApp')"><span>提现</span><span class="img1"><img src="<%=bp %>/images/c.png" /></span></a></p>
		    </li>
	    </li>
    </ul>
    <ul>
	    <li onClick="turnit(6,5);">
		    <a href="#" hidefocus="true">
			    <p>用户中心</p>
			    <p class="img"><img src="<%=bp %>/images/a.png" id="img5"/></p>
		    </a>
		    <li class="yinc" id="content5" style=" display:none;">
			    <p id="menu_pwdChange"><a hidefocus="true" href="${_base }/companyInfoAction/toPwdChange"><span>修改密码</span><span class="img1"><img src="<%=bp %>/images/c.png" /></span></a></p>
			    <p id="menu_basicInfo"><a hidefocus="true" href="${_base }/companyInfoAction/toBasicInfo"><span>基本信息</span><span class="img1"><img src="<%=bp %>/images/c.png" /></span></a></p>    
		    </li>
	    </li>
    </ul>
    <ul>
	    <li onClick="turnit(6,6);">
		    <a href="#" hidefocus="true">
			    <p>系统帮助</p>
			    <p class="img"><img src="<%=bp %>/images/a.png" id="img6"/></p>
		    </a>
		    <li class="yinc" id="content6" style=" display:none;">
		   		<p><a hidefocus="true" href="#"><span>系统帮助</span><span class="img1"><img src="<%=bp %>/images/c.png" /></span></a></p>
		    </li>
	    </li>
    </ul>
</div>
