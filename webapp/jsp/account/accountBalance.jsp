<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.* "%>
<%@ taglib uri="/WEB-INF/lib/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/lib/fn.tld" prefix="fn"%>
<%
	String totalPage = String
			.valueOf(request.getAttribute("totalPage"));
	String currentPage = String.valueOf(request
			.getAttribute("currentPage"));

	int totalpage = Integer.parseInt(totalPage);
	int currentpage = Integer.parseInt(currentPage);

	int nextpage = currentpage + 1;
	int abvpage = currentpage - 1;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<%@ include file="/jsp/common/common.jsp"%>
<title>沃云软件商门户</title>
<script src="${_base}/js/caidna.js" type="text/javascript"></script>
<script src="<%=bp%>/js/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script>
var softName; //搜索条件:软件名称
var startDate;//搜索条件：开始时间
var endDate;  //搜索条件：结束时间
$(document).ready(function(){
	turnit(6,4);
	$("#menu_accountBalance").addClass("xunz");
	softName="${softName}";
	startDate="${startDate}";
	endDate="${endDate}";
	
	$("#mySearch").bind("click",function(){
		var cond_softName=$("#softName").val();
		var cond_startDate=$("#startDate").val();
		var cond_endDate=$("#endDate").val();
		location.href="${_base}/accountInfoAction/toAccountBalance?softName="+encodeURI(encodeURI(cond_softName))+"&startDate="+cond_startDate+"&endDate="+cond_endDate;
	});
});

$(function() {
	$.easypage({
		'navigateid' : 'sp_div',
		'navigatecount' :2
	});
});
//contentclass   要分页内容的class名称 默认的为contentlist
//navigateid		 放置导航按钮的位置id 默认为mynavigate
//navigatecount	 导航按钮开始显示多少个，从第二个开始显示为双倍	
(function($) {
	$.extend({
				"easypage" : function(options) {
					options = $.extend({  //参数设置
						//contentclass:"contentlist",//要显示的内容的class
						navigateid : "sp_div",//导航按钮所在的dom的id
						navigatecount : "0"//导航按钮一次显示多少个
					}, options);
					var currentpage =<%=currentpage%>; //当前页	
					var pagecount =<%=totalpage%>; //计算出页数
					
					/* var softName=$("#softName").val();
					var appStatus=$("#conStatus").val();
					var startDate=$("#startDate").val();
					var endDate=$("#endDate").val(); */
					//拼接导航按钮
					var navigatehtml = "<ul><li id=pagepre><A href='${_base}/accountInfoAction/toAccountBalance?page=<%=abvpage %>&softName="+encodeURI(encodeURI(softName))+"&startDate="+startDate+"&endDate="+endDate+"'>上一页</A></li>";
					for (var i = 1; i <= pagecount; i++) {
						if(currentpage == i){
							navigatehtml += '<li class="x_shuz"><A href="${_base}/accountInfoAction/toAccountBalance?page='+i+'&softName='+encodeURI(encodeURI(softName))+'&startDate='+startDate+'&endDate='+endDate+'">'
								+ i + '</A></li>';
						}else{
							navigatehtml += '<li class="pagenavigate"><A href="${_base}/accountInfoAction/toAccountBalance?page='+i+'&softName='+encodeURI(encodeURI(softName))+'&startDate='+startDate+'&endDate='+endDate+'">'
								+ i + '</A></li>';
						}
						
						
					}
					
					navigatehtml += "<li id='pagenext'><A href='${_base}/accountInfoAction/toAccountBalance?page=<%=nextpage %>&softName="+encodeURI(encodeURI(softName))+"&startDate="+startDate+"&endDate="+endDate+"'>下一页</A></li>"+
			          "<li>共<%=totalpage%>页</li>"+
			          "<li>到 第</li>"+
			          "<li><input name='x_f_input' type='text' id='x_f_input' class='x_f_input' onkeyup='checkPageNo()' style='text-align:center'/></li>"+
			          "<li>页</li>"+
			          "<li class='x_tzh'><A href='${_base}/accountInfoAction/toAccountBalance?softName="+encodeURI(encodeURI(softName))+"&startDate="+startDate+"&endDate="+endDate+"&page=' onclick=this.href=this.href+document.getElementById('x_f_input').value>跳转</A></li>"+
			          "</ul>";

					//加载导航按钮
					$("#" + options.navigateid).html(navigatehtml);

					//得到所有按钮
					var navigates = $(".pagenavigate");

					//隐藏所有的导航按钮
					$.extend({
						"hidenavigates" : function() {
							navigates.each(function() {
								$(this).hide();
							})
						}
					});

					//显示导航按钮
					$.extend({
								"shownavigate" : function(currentnavigate) {
									$.hidenavigates();
									var begin = currentnavigate >= options.navigatecount ? currentnavigate
											- parseInt(options.navigatecount) : 0;
									if (begin > navigates.length-2
											* options.navigatecount) {
										begin = navigates.length-2
												* options.navigatecount;
									}
									for (var i = begin; i <= currentnavigate
											+ parseInt(options.navigatecount); i++) {
											
												$(navigates[i]).show();
											
									}
								}
							});

					

					//隐藏前进后退按钮
					$.extend({
						"hidePreNext" : function(page) {
							$("#pagenext").show();
							$("#pagelast").show();
							$("#pagepre").show();
							$("#pagefirst").show();
							if (pagecount == 1) {
								$("#pagenext").hide();
								$("#pagelast").hide();
							}
							if (currentpage == 1) {
								$("#pagepre").hide();
								$("#pagefirst").hide();
							}
							if (currentpage == pagecount) {
								$("#pagelast").hide();
								$("#pagenext").hide();
							}
						}
					});
					//显示指定的导航按钮
					$.shownavigate(currentpage);
					//隐藏所有的内容
// 					$.showPage(0);
					//开始时隐藏后退按钮
					$.hidePreNext(0);
					
				}
			});
})(jQuery);

function checkPageNo() {
	var insertPage = $("#x_f_input").val();
	if (insertPage != '') {
		if(isNaN(insertPage)) {
			$("#x_f_input").val('');
		} else {
			if (!(0< parseInt(insertPage) && parseInt(insertPage) <= <%=totalpage%>)) {
				$("#x_f_input").val('');
			}
		}
	}
}
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
  <li><A href="#">销售明细</A></li>
  </ul>
  </div>
 
  <div class="erji_jiben_input">
 <ul>
 <li class="chag_li" style="width:900px;">
 <p class="ej_wz">软件名称：</p>
 <p><input name="" value="${softName }" type="text"  id="softName" class="ej_input"/></p>
 
 
 <p class="ej_wz">购买时间：</p>
  	<p><input name="" value="${startDate }" type="text"  id="startDate" onClick="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'});"  class="ej_input_dua"/></p>
	<p class="x_g">-</p>
	<p><input name="" value="${endDate }" type="text"  id="endDate" onClick="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'});" class="ej_input_dua"/></p>
   
   <p class="e_sou" style="margin-left:30px;"><A id="mySearch" href="#">搜索</A></p>
 </li>
 
 </ul>
 
	
 
 </div>
 
 <div class="erji_table">
 
<table width="100%" border="1">
  <tr valign="middle" bgcolor="#f8f8f8">
    <td align="center">购买用户</td>
    <td align="center">产品名称</td>
    <td align="center">购买时间</td>
    <td align="center">计费方式</td>
	<td align="center">产品单价（元）</td>
    <td align="center">购买时长（月）</td>
    <td align="center">订单总价（元）</td>
  </tr>
  <c:forEach items="${accountlist }"  var="accountDate">
  <tr>
  	<td align="center" valign="middle">${accountDate.customerName }</td>
    <td align="center" valign="middle">${accountDate.productName } </td>    
    <td align="center" valign="middle">${accountDate.createDate } </td>
    <td align="center" valign="middle">${accountDate.billType }</td>
    <td align="center" valign="middle">${accountDate.price }</td>
    <td align="center" valign="middle">${accountDate.time }</td>
    <td align="center" valign="middle">${accountDate.feeValue }</td>
  </tr>
  </c:forEach>
</table>
 </div>
 
      <!------分页控件----->
   <div class="main_C_fenye" id="sp_div">
            
   </div>
</div>
 </div>
</div>
<%@ include file="/jsp/common/footer.jsp"%>         <!--------------------footer-->
</body>
</html>


