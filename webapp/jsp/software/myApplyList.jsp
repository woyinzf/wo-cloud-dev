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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>沃云软件商门户</title>
<script src="${_base}/js/caidna.js" type="text/javascript"></script>
<script src="<%=bp%>/js/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script type="text/javascript">
var cond_softName;
var cond_appStatus;
var cond_startDate;
var cond_endDate;
var orderCond;
var orderType;
$(document).ready(function(){
	turnit(6,2);
	$("#menu_newSoftwareApply").addClass("xunz");
	cond_softName="${cond_softName}";
	cond_appStatus="${cond_appStatus}";
	cond_startDate="${cond_startDate}";
	cond_endDate="${cond_endDate}";
	orderCond="${orderCond}";
	orderType="${orderType}";
	
	if(cond_appStatus!=null && cond_appStatus!=''){
		$("#conStatus").val(cond_appStatus);
	}
	if((cond_appStatus!=null && cond_appStatus!='')||(cond_startDate!=null && cond_startDate!='')||(cond_endDate!=null && cond_endDate!='')){
		$("#hideCondition").show();
	}
	
	if(orderCond !=null && orderCond !=''){
		if(orderType=="asc"){
			$("#"+orderCond).children().eq(1).children(".asc_desc").attr("src","<%=bp %>/images/s.png");
		}else{
			$("#"+orderCond).children().eq(1).children(".asc_desc").attr("src","<%=bp %>/images/x.png");
		}
	}
	$("#moreCondition").bind("click",function(){
		$("#hideCondition").toggle();
		if($("#hideCondition").css("display")=="none"){
			$("#conStatus option[value='']").attr("selected", true);
			$("#startDate").val("");
			$("#endDate").val("");
			
		}
	});
	
	$(".orderCond").bind("click",function(){
		var tempOrderCond=$(this).attr("id");
		if(orderCond!=tempOrderCond){
			orderType="desc";
			location.href="${_base}/software/myApplyList?softName="+encodeURI(encodeURI(cond_softName))+"&appStatus="+cond_appStatus+"&startDate="+cond_startDate+"&endDate="+cond_endDate+"&orderCond="+tempOrderCond+"&orderType="+orderType;
		}else{
			if(orderType=="desc"){
				orderType="asc";
				location.href="${_base}/software/myApplyList?softName="+encodeURI(encodeURI(cond_softName))+"&appStatus="+cond_appStatus+"&startDate="+cond_startDate+"&endDate="+cond_endDate+"&orderCond="+tempOrderCond+"&orderType="+orderType;
			}else{
				orderType="desc";
				location.href="${_base}/software/myApplyList?softName="+encodeURI(encodeURI(cond_softName))+"&appStatus="+cond_appStatus+"&startDate="+cond_startDate+"&endDate="+cond_endDate+"&orderCond="+tempOrderCond+"&orderType="+orderType;
			}
		}
	});
	
	/* $(".orderCond").toggle(
			function(){
				$(this).children().eq(1).children(".desc").hide();
				$(this).children().eq(1).children(".asc").show();
				//alert($(this).attr("id"));
				orderCond=$(this).attr("id");
				orderType="asc";
				location.href="${_base}/software/myApplyList?softName="+cond_softName+"&appStatus="+cond_appStatus+"&startDate="+cond_startDate+"&endDate="+cond_endDate+"&orderCond="+orderCond+"&orderType="+orderType;
				
			},
			function(){
				$(this).children().eq(1).children(".desc").show();
				$(this).children().eq(1).children(".asc").hide();
				orderCond=$(this).attr("id");
				orderType="desc";
				location.href="${_base}/software/myApplyList?softName="+cond_softName+"&appStatus="+cond_appStatus+"&startDate="+cond_startDate+"&endDate="+cond_endDate+"&orderCond="+orderCond+"&orderType="+orderType;
			}
	); */
		

	
	/* $(".orderCond").toggle(
		function(){$(".xia").show();}
	); */
		
	
	
	$("#mySearch").bind("click",function(){
		var softName=$("#softName").val();
		var appStatus=$("#conStatus").val();
		var startDate=$("#startDate").val();
		var endDate=$("#endDate").val();
		location.href="${_base}/software/myApplyList?softName="+encodeURI(encodeURI(softName))+"&appStatus="+appStatus+"&startDate="+startDate+"&endDate="+endDate+"&orderCond=application_time&orderType=desc";
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
					var navigatehtml = "<ul><li id=pagepre><A href='${_base}/software/myApplyList?page=<%=abvpage %>&softName="+encodeURI(encodeURI(cond_softName))+"&appStatus="+cond_appStatus+"&startDate="+cond_startDate+"&cond_endDate="+cond_endDate+"&orderCond="+orderCond+"&orderType="+orderType+"'>上一页</A></li>";
					for (var i = 1; i <= pagecount; i++) {
						if(currentpage == i){
							navigatehtml += '<li class="x_shuz"><A href="${_base}/software/myApplyList?page='+i+'&softName='+encodeURI(encodeURI(cond_softName))+'&appStatus='+cond_appStatus+'&startDate='+cond_startDate+'&endDate='+cond_endDate+'&orderCond='+orderCond+'&orderType='+orderType+'">'
								+ i + '</A></li>';
						}else{
							navigatehtml += '<li class="pagenavigate"><A href="${_base}/software/myApplyList?page='+i+'&softName='+encodeURI(encodeURI(cond_softName))+'&appStatus='+cond_appStatus+'&startDate='+cond_startDate+'&endDate='+cond_endDate+'&orderCond='+orderCond+'&orderType='+orderType+'">'
								+ i + '</A></li>';
						}
						
						
					}
					
					navigatehtml += "<li id='pagenext'><A href='${_base}/software/myApplyList?page=<%=nextpage %>&softName="+encodeURI(encodeURI(cond_softName))+"&appStatus="+cond_appStatus+"&startDate="+cond_startDate+"&endDate="+cond_endDate+"&orderCond="+orderCond+"&orderType="+orderType+"'>下一页</A></li>"+
			          "<li>共<%=totalpage%>页</li>"+
			          "<li>到 第</li>"+
			          "<li><input name='x_f_input' type='text' id='x_f_input' class='x_f_input' onkeyup='checkPageNo()' style='text-align:center'/></li>"+
			          "<li>页</li>"+
			          "<li class='x_tzh'><A href='${_base}/software/myApplyList?softName="+encodeURI(encodeURI(cond_softName))+"&appStatus="+cond_appStatus+"&startDate="+cond_startDate+"&endDate="+cond_endDate+"&orderCond="+orderCond+"&orderType="+orderType+"&page=' onclick=this.href=this.href+document.getElementById('x_f_input').value>跳转</A></li>"+
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
 	<!--header -->
	<%@ include file="/jsp/common/header.jsp"%>
    
	<div class="erji_nei">
   		<!--leftMenu-->
	    <%@ include file="/jsp/common/leftMenu.jsp"%>
	       
 		<div class="erji_nei_right">
			<div class="erji_weiz">
				<ul>
					<li><A href="${_base }/loginInfoAction/toIndex">首页</A>&nbsp;></li>
					<li><A href="#">软件管理</A>&nbsp;></li>
					<li><A href="#">我的申请单 </A></li>
				</ul>
			</div>
 
			<div class="erji_jiben_input">
				<ul>
					<li class="chag_li">
					<p class="ej_wzMyApplyList" style="width: ">软件名称：</p>
					<p><input name="" value="${cond_softName}" id="softName" type="text"  class="ej_input"/></p>
					<p class="e_sou"><A  id="mySearch" href="#">搜索</A></p>
					<p class="wat" id="moreCondition"><span style="cursor:pointer;">更多筛选条件</span><span class="tip_s"><img src="<%=bp %>/images/saj.png" /></span>
					</li>				
				</ul>
			
				<ul id="hideCondition" style="display:none;">
					<li>
						<p class="ej_wzMyApplyList">申请单状态：</p>
						<p>
							<select name=""  value="${cond_appStatus}" id="conStatus" class="ej_xiala">
								<option value="" selected="selected">--请选择--</option>
								<option value ="0">审核中</option>
								<option value ="1">审核通过</option>
								<option value ="2">审核未通过</option>
							</select>
						</p>
					</li>
					<li>
						<p class="ej_wzMyApplyList">提交时间：</p>
						<p><input name="" value="${cond_startDate }" type="text"  id="startDate" onClick="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'});"  class="ej_input_dua"/></p>
						<p class="x_g">-</p>
						<p><input name="" value="${cond_endDate }" type="text"  id="endDate" onClick="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'});" class="ej_input_dua"/></p>
					</li>
				</ul>												
			</div>
 
			<div class="erji_table">			 
			<table width="100%" border="1">
			  <tr valign="middle" bgcolor="#f8f8f8">			   
			    <td align="center"><table width="100%" border="0">
			      <tr class="orderCond" id="application_type">
			        <td width="60%" align="right" style="border:none;">申请单类型&nbsp;</td>
			        <td class="orderType" width="40%" align="left" style="border:none;"><img class="asc_desc" src="<%=bp %>/images/s_x.png" style="cursor: pointer;" /></td>
			      </tr>
			    </table></td>
			    <td align="center"><table width="100%" border="0">
			      <tr class="orderCond" id="software_name">
			        <td width="60%" align="right" style="border:none;">软件名称&nbsp;</td>
			        <td class="orderType" width="40%" align="left" style="border:none;"><img class="asc_desc" src="<%=bp %>/images/s_x.png" style="cursor: pointer;" /></td>
			      </tr>
			    </table></td>
			        <td align="center"><table width="100%" border="0">
			      <tr class="orderCond" id="software_type">
			        <td width="60%" align="right" style="border:none;">软件类型&nbsp;</td>
			        <td class="orderType" width="40%" align="left" style="border:none;"><img class="asc_desc" src="<%=bp %>/images/s_x.png" style="cursor: pointer;" /></td>
			      </tr>
			    </table></td>
			       <td align="center"><table width="100%" border="0">
			      <tr class="orderCond" id="software_versions">
			        <td width="60%" align="right" style="border:none;">软件版本&nbsp;</td>
			        <td class="orderType" width="49%" align="left" style="border:none;"><img class="asc_desc" src="<%=bp %>/images/s_x.png" style="cursor: pointer;" /></td>
			      </tr>
			    </table></td>
			        <td align="center"><table width="100%" border="0">
			      <tr class="orderCond" id="application_time">
			        <td width="60%" align="right" style="border:none;">提交时间&nbsp;</td>
			        <td class="orderType" width="40%" align="left" style="border:none;"><img class="asc_desc" src="<%=bp %>/images/s_x.png" style="cursor: pointer;" /></td>
			      </tr>
			    </table></td>
			        <td align="center"><table width="100%" border="0">
			      <tr class="orderCond" id="application_status">
			        <td width="60%" align="right" style="border:none;">状态&nbsp;</td>
			        <td class="orderType" width="40%" align="left" style="border:none;"><img class="asc_desc" src="<%=bp %>/images/s_x.png" style="cursor: pointer;" /></td>
			      </tr>
			    </table></td>
			  </tr>
			  <c:forEach items="${pageModel.dataList }"  var="applyDate">
			  <tr>			   
			    <td align="center" valign="middle"><c:out value="${applyDate.applyType }"></c:out>   </td>
			    <td align="center" valign="middle"><a href="${_base }/software/applyDetail?applyId=${applyDate.applyId }&softId=${applyDate.softId}"><c:out value="${applyDate.softName }"></c:out></a>   </td>
			    <td align="center" valign="middle"><c:out value="${applyDate.softType }"></c:out> </td>
			    <td align="center" valign="middle"><c:out value="${applyDate.softVersion }"></c:out>   </td>
			    <td align="center" valign="middle"><c:out value="${applyDate.applyTime }"></c:out></td>
			    <td align="center" valign="middle">
			    	<c:if test="${applyDate.applyStatus == '0'}">
						<span style="color: #2159d6;">审核中</span>
					</c:if> <c:if test="${applyDate.applyStatus == '1'}">
						<span style="color: #ef3839;">审核通过</span>
					</c:if>
					<c:if test="${applyDate.applyStatus == '2'}">
						<span style="color: #ef3839;">审核未通过</span>
					</c:if>
				</td>
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
<!--footer-->
 <%@ include file="/jsp/common/footer.jsp"%>
</body>
</html>