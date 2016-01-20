<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
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

	String bp = request.getContextPath();
	request.setAttribute("_base", bp);
	request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>沃云软件商门户</title>

<link href="<%=bp %>/css/PopupDiv.css" rel="stylesheet" type="text/css" />
<script src="<%=bp%>/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="<%=bp%>/js/PopupDiv_v1.0.js" type="text/javascript"></script>
<script src="<%=bp%>/js/utils.js" type="text/javascript"></script>
<script src="<%=bp%>/js/caidna.js" type="text/javascript"></script>
<script src="<%=bp%>/js/jquery.textSearch-1.0.js" type="text/javascript"></script>

<script type="text/javascript">
var orderCond; //排序根据的字段名，就是每一列的id
var orderType; //排序的方式，asc or desc
var condValue; //搜索条件
$(document).ready(function(){
	turnit(6,2); //这是左侧菜单栏的打开某一列用的函数
	$("#menu_spShowAllSoftware").addClass("xunz");//左侧菜单栏具体选项高亮显示
	orderCond="${orderCond}";
	orderType="${orderType}";
	condValue="${condValue}";

	//$("#high_area").textSearch(condValue);
	if(orderCond !=null && orderCond !=''){
		if(orderType=="asc"){
			$("#"+orderCond).children().eq(1).children(".asc_desc").attr("src","<%=bp %>/images/s.png");
		}else{
			$("#"+orderCond).children().eq(1).children(".asc_desc").attr("src","<%=bp %>/images/x.png");
		}
	}
	//下架软件
	$("#downsoftware").bind("click",function(){
		if(ifCanBeUse("downsoftware")) {
			confirm_alert("确认下架", coDs);
		} else {
			return;
		}
	});
	
	//取消申请
	$("#cancelapply").bind("click",function(){
		if(ifCanBeUse("cancelapply")) {
			confirm_alert("确认取消申请", cancelapplyCoDs);
		} else {
			return;
		}
	});
	
	$(".orderCond").bind("click",function(){
		var tempOrderCond=$(this).attr("id");
		if(orderCond!=tempOrderCond){
			orderType="desc";
			location.href="${_base}/software/spfindAllSoftware?page=<%=abvpage %>&orderCond="+tempOrderCond+"&orderType="+orderType+"&condValue="+encodeURI(encodeURI(condValue));
		}else{
			if(orderType=="desc"){
				orderType="asc";
				location.href="${_base}/software/spfindAllSoftware?page=<%=abvpage %>&orderCond="+tempOrderCond+"&orderType="+orderType+"&condValue="+encodeURI(encodeURI(condValue));
			}else{
				orderType="desc";
				location.href="${_base}/software/spfindAllSoftware?page=<%=abvpage %>&orderCond="+tempOrderCond+"&orderType="+orderType+"&condValue="+encodeURI(encodeURI(condValue));
			}
		}
	});
	
	$("#my_search").bind("click",function(){
		condValue=$("#search_value").val();
		location.href="${_base}/software/spfindAllSoftware?condValue="+encodeURI(encodeURI(condValue))+"&orderCond=command_time&orderType=desc";
	});
	
	//绑定选中行效果,并判断是否可以删除
	$("input[name=radio]").bind("click", function(){
		$("input[name=radio]").each(function(){
			var cleanId = $(this).attr("id");
			$("#tr" + cleanId).css('background-color','');
		});
		var radioId = $(this).attr("id");
		$("#tr" + radioId).css('background-color','#f3fafe');
		
		//修改按钮权限
		var statusCode = $("#" + radioId + "Status").attr("value");
		var processType=$("input[type='radio']:checked").attr("choiceProcessType");
		switch (statusCode) {
			case '0' :
				buttonAble("modifysoftware", radioId);
				buttonAble("deleteSoftware", radioId);
				buttonDisable("changesoftware", radioId);
				buttonDisable("upgradesoftware", radioId);
				buttonDisable("downsoftware", radioId);
				buttonDisable("cancelapply", radioId);
			break;
			case '1' :
				buttonDisable("modifysoftware", radioId);
				buttonDisable("deleteSoftware", radioId);
				buttonDisable("changesoftware", radioId);
				buttonDisable("upgradesoftware", radioId);
				buttonDisable("downsoftware", radioId);
				buttonAble("cancelapply", radioId);
			break;
			case '2' :
				buttonDisable("modifysoftware", radioId);
				buttonDisable("deleteSoftware", radioId);
				buttonDisable("changesoftware", radioId);
				buttonDisable("upgradesoftware", radioId);
				buttonDisable("downsoftware", radioId);
				buttonDisable("cancelapply", radioId);
			break;
			case '3' :				
				if(processType!="1"){
					buttonDisable("modifysoftware", radioId);
					buttonDisable("deleteSoftware", radioId);
					buttonAble("changesoftware", radioId);
					buttonAble("upgradesoftware", radioId);
				}else{
					buttonAble("modifysoftware", radioId);
					buttonAble("deleteSoftware", radioId);
					buttonDisable("changesoftware", radioId);
					buttonDisable("upgradesoftware", radioId);
				}
				buttonDisable("downsoftware", radioId);
				buttonDisable("cancelapply", radioId);
			break;
			case '4' :
				buttonDisable("modifysoftware", radioId);
				buttonDisable("deleteSoftware", radioId);
				buttonDisable("changesoftware", radioId);
				buttonDisable("upgradesoftware", radioId);
				buttonDisable("downsoftware", radioId);
				buttonDisable("cancelapply", radioId);
			break;
			case '5' :
				buttonDisable("modifysoftware", radioId);
				buttonDisable("deleteSoftware", radioId);
				buttonDisable("changesoftware", radioId);
				buttonDisable("upgradesoftware", radioId);
				buttonDisable("downsoftware", radioId);
				buttonDisable("cancelapply", radioId);
			break;
			case '6' :
				buttonDisable("modifysoftware", radioId);
				buttonDisable("deleteSoftware", radioId);
				buttonAble("changesoftware", radioId);
				buttonAble("upgradesoftware", radioId);
				buttonAble("downsoftware", radioId);
				buttonDisable("cancelapply", radioId);
			break;
			case '7' :
				if(processType!="1"){
					buttonDisable("modifysoftware", radioId);
					buttonDisable("deleteSoftware", radioId);
					buttonAble("changesoftware", radioId);
					buttonAble("upgradesoftware", radioId);
				}else{
					buttonAble("modifysoftware", radioId);
					buttonAble("deleteSoftware", radioId);
					buttonDisable("changesoftware", radioId);
					buttonDisable("upgradesoftware", radioId);
				}
				buttonDisable("downsoftware", radioId);
				buttonDisable("cancelapply", radioId);
			break;
			case '8' :
				buttonDisable("modifysoftware", radioId);
				buttonDisable("deleteSoftware", radioId);
				buttonDisable("changesoftware", radioId);
				buttonDisable("upgradesoftware", radioId);
				buttonDisable("downsoftware", radioId);
				buttonDisable("cancelapply", radioId);
			break;
			case '9' :
				buttonDisable("modifysoftware", radioId);
				buttonDisable("deleteSoftware", radioId);
				buttonDisable("changesoftware", radioId);
				buttonDisable("upgradesoftware", radioId);
				buttonDisable("downsoftware", radioId);
				buttonDisable("cancelapply", radioId);
			break;
		}
	})
});

//改变按钮状态子函数
function buttonAble(buttonName, radioId) {
	$("#" + buttonName).attr("disabled", false);
	$("#" + buttonName).css("color", "#00adf3");
	$("#" + buttonName).css("border-color", "#00adf3");
	$("#" + buttonName).css("cursor", "pointer");
	$("#" + buttonName).attr("value", radioId);
}
function buttonDisable(buttonName, radioId) {
	$("#" + buttonName).attr("disabled", true);
	$("#" + buttonName).css("color", "gray");
	$("#" + buttonName).css("border-color", "#d2d2d2");
	$("#" + buttonName).css("cursor", "not-allowed");
	$("#" + buttonName).attr("value", radioId);
}
//判断按钮是否可用
function ifCanBeUse(buttonName) {
	var ifDis = $("#" + buttonName).attr("disabled");
	if (ifDis != "disabled") {
		return true;
	}
}

//修改软件
function modifySoftware(){
	if(ifCanBeUse("modifysoftware")) {
		var selectId=$("input[type='radio']:checked").attr("id");
		var selectStatus=$("input[type='radio']:checked").val();
		if(selectId==undefined){
			//alert("请选择要操作的软件！");
				info_alert("请选择要操作的软件！");
			}else if(selectStatus===0 || selectStatus===3 || selectStatus===9){
				info_alert("该状态无法进行下架操作！");
			}else{
				window.location.href="${_base}/software/modifySoftware?id="+selectId;
			}
	} else {
		return;
	}
}

//变更软件
function changeSoftware(){
	if(ifCanBeUse("changesoftware")) {
		var selectId=$("input[type='radio']:checked").attr("id");
		var selectStatus=$("input[type='radio']:checked").val();
		if(selectId==undefined){
				info_alert("请选择要操作的软件！");
		}else if(selectStatus=="3" || selectStatus =='7'){
			confirm_alert("您只能对已上架的软件进行变更或升级操作，点击“确定”后软件信息恢复为已上架软件信息！", coRe1);
		}else{
			window.location.href="${_base}/software/toUpdateSoftware?id="+selectId;
		}		
	} else {
		return;
	}
}

function jump_to_sp(){
	location.href="${_base}/software/spfindAllSoftware?orderCond=command_time&orderType=desc";
}
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
						//拼接导航按钮
						var navigatehtml = "<ul><li id=pagepre><A href='${_base}/software/spfindAllSoftware?page=<%=abvpage %>&orderCond="+orderCond+"&orderType="+orderType+"&condValue="+encodeURI(encodeURI(condValue))+"'>上一页</A></li>";
						if  (pagecount <= 10) {
							for (var i = 1; i <= pagecount; i++) {
								if(currentpage == i){
									navigatehtml += '<li class="x_shuz"><A href="${_base}/software/spfindAllSoftware?page='+i+'&orderCond='+orderCond+'&orderType='+orderType+'&condValue='+encodeURI(encodeURI(condValue))+'">'
										+ i + '</A></li>';
								}else{
									navigatehtml += '<li class="pagenavigate"><A href="${_base}/software/spfindAllSoftware?page='+i+'&orderCond='+orderCond+'&orderType='+orderType+'&condValue='+encodeURI(encodeURI(condValue))+'">'
										+ i + '</A></li>';
								}
							}
						} else {
								var i = 1;
								if(currentpage == i){
									navigatehtml += '<li class="x_shuz"><A href="${_base}/software/spfindAllSoftware?page='+i+'&orderCond='+orderCond+'&orderType='+orderType+'&condValue='+encodeURI(encodeURI(condValue))+'">'
										+ i + '</A></li>';
								}else{
									navigatehtml += '<li class="pagenavigate"><A href="${_base}/software/spfindAllSoftware?page='+i+'&orderCond='+orderCond+'&orderType='+orderType+'&condValue='+encodeURI(encodeURI(condValue))+'">'
										+ i + '</A></li>';
								}
								var i = (pagecount%2);
						}
						
						
						navigatehtml += "<li id='pagenext'><A href='${_base}/software/spfindAllSoftware?page=<%=nextpage %>&orderCond="+orderCond+"&orderType="+orderType+"&condValue="+encodeURI(encodeURI(condValue))+"'>下一页</A></li>"+
				          "<li>共<%=totalpage%>页</li>"+
				          "<li>到 第</li>"+
				          "<li><input name='x_f_input' type='text' id='x_f_input' class='x_f_input' onkeyup='checkPageNo()' style='text-align:center'/> </li>"+
				          "<li>页</li>"+
				          "<li class='x_tzh'><A href='${_base}/software/spfindAllSoftware?orderCond="+orderCond+"&orderType="+orderType+"&condValue="+encodeURI(encodeURI(condValue))+"&page=' onclick=this.href=this.href+document.getElementById('x_f_input').value>跳转</A></li>"+
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
						//开始时隐藏后退按钮
						$.hidePreNext(0);
						
					}
				});
	})(jQuery);

//删除软件
function deleteApply(){
	if (ifCanBeUse("deleteSoftware")) {
		confirm_alert("确认删除", coDe);
	} else {
		return;
	}
}
function coDe() {
	var softId = $('input:radio[name="radio"]:checked').attr("id");
	$.ajax({
		type : "POST",
		url : "${_base}/software/deleteSoftware",
		async : false,
		data : {softId : softId},
		success : function (data) {
			
			$('input:radio[name="radio"]:checked').attr("checked", false);
			info_alert("删除成功", window.location.href = '${_base }/software/spfindAllSoftware?orderCond=command_time&orderType=desc');
		},
		error : function (data) {
			info_alert("删除失败");
		}
	});
}

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

//确认下架软件
function coDs() {
	$("#addsoftware").parent().parent().children("li").removeClass("xz");
	$("#downsoftware").parent().addClass("xz");
	var selectId=$("input[type='radio']:checked").attr("id");
	var selectStatus=$("input[type='radio']:checked").val();
	if(selectId==undefined){
	//alert("请选择要操作的软件！");
		info_alert("请选择要操作的软件！");
	}else if(selectStatus!="6"){
		info_alert("该状态无法进行下架操作！");
	}else{
		$.ajax({
			type : "POST",
			url : "${_base}/software/softwareDown",
			async : false,
			dataType: 'json',
			data : {											
				selectId : selectId
			},
			success : function(data) {								
				var $json=eval(data);				    	  
                  result = $json.result;				                  
		          if (result == "0") {						        	  						        	
		        	  info_alert("信息修改成功!", location.reload());
		          }
		          else {
		        	  info_alert("信息修改失败");
		          }
			}
		}); 		
	}
}

//确认取消申请
function cancelapplyCoDs() {
	$("#addsoftware").parent().parent().children("li").removeClass("xz");
	$("#cancelapply").parent().addClass("xz");
	var selectId=$("input[type='radio']:checked").attr("id");
	var selectStatus=$("input[type='radio']:checked").val();
	if(selectStatus=='0' || selectStatus=='1'){
		$.ajax({
			type : "POST",
			url : "${_base}/software/softCancelapply",
			async : false,
			dataType: 'json',
			data : {											
				selectId : selectId
			},
			success : function(data) {								
				var $json=eval(data);				    	  
                  result = $json.result;				                  
		          if (result == "0") {						        	  						        	
		        	  info_alert("信息修改成功!", location.reload());
		          }
		          else if (result == "1"){
		        	  info_alert("升级与变更申请不允许取消");
		          } else {
		        	  info_alert("信息修改失败");
		          }
			}
		});  
	}else if(selectId==undefined){
		info_alert("请选择要操作的软件！");
	}else{
		info_alert("该状态无法进行取消申请操作！");
	}
}

//升级软件
function upgradeSoftware(){
	if(ifCanBeUse("changesoftware")) {
		var selectStatus=$("input[type='radio']:checked").val();
		if(selectStatus =='3' || selectStatus =='7'){
			confirm_alert("您只能对已上架的软件进行变更或升级操作，点击“确定”后软件信息恢复为已上架软件信息！", coRe2);
		}else{
			var selectId=$("input[type='radio']:checked").attr("id");
			location.href="${_base}/software/toUpgradeSoftware?softId="+selectId;
		}
	}else{
		return;
	}
}

//变更审核未通过软件恢复到申请前信息
function coRe1(){
	var selectId=$("input[type='radio']:checked").attr("id");
	$.ajax({
		type : "POST",
		url : "${_base}/software/softwareReset",
		async : false,
		dataType: 'json',
		data : {											
			selectId : selectId
		},
		success : function(data) {								
			var $json=eval(data);				    	  
              result = $json.result;				                  
	          if (result == "0") {						        	  						        	
	        	  window.location.href="${_base}/software/toUpdateSoftware?id="+selectId;
	          }
	          else {
	        	  info_alert("软件恢复失败！");
	          }
		}
	}); 		
}

//升级审核未通过软件恢复到申请前信息
function coRe2(){
	var selectId=$("input[type='radio']:checked").attr("id");
	$.ajax({
		type : "POST",
		url : "${_base}/software/softwareReset",
		async : false,
		dataType: 'json',
		data : {											
			selectId : selectId
		},
		success : function(data) {								
			var $json=eval(data);				    	  
              result = $json.result;				                  
	          if (result == "0") {						        	  						        	
	        	  location.href="${_base}/software/toUpgradeSoftware?softId="+selectId;
	          }
	          else {
	        	  info_alert("软件恢复失败！");
	          }
		}
	}); 		
}
</script>
</head>

<body>

	<div class="wai_da">
	<%@ include file="/jsp/common/header.jsp"%>
    
   <div class="erji_nei">
   <%@ include file="/jsp/common/leftMenu.jsp"%>


		<div class="erji_nei_right">
			<div class="erji_weiz">
				<ul>
					<li><A href="#">首页</A>&nbsp;></li>
					<li><A href="#">软件管理</A>&nbsp;></li>
					<li><A href="#">我的软件</A></li>
				</ul>
			</div>

			<div class="erji_biaog">
				<ul>
					<li class="xz"><A href="${_base }/applyOdd/queryOsData"
						id="addsoftware">新增</A></li>
					<li><A href="javascript:modifySoftware()" id="modifysoftware" disabled="true" style="color: gray;cursor: not-allowed;">修改</A></li>
					<li><A href="javascript:deleteApply()" id="deleteSoftware" disabled="true" style="color: gray;cursor: not-allowed;">删除</A></li>
					<li><A href="javascript:changeSoftware()" id="changesoftware" disabled="true" style="color: gray;cursor: not-allowed;">软件变更</A></li>
					<li><A href="javascript:upgradeSoftware()" id="upgradesoftware" disabled="true" style="color: gray;cursor: not-allowed;">软件升级</A></li>
					<li><A href="#" id="downsoftware" disabled="true" style="color: gray;cursor: not-allowed;">下架申请</A></li>
					<li><A href="#" id="cancelapply" disabled="true" style="color: gray;cursor: not-allowed;">取消申请</A></li>
					<li class="sous">
						<p>
							<input name="" id="search_value" value="${condValue }" type="text" class="sous_input" />
						</p>
						<p>
							<input  id="my_search" style="cursor:pointer;" type="button" value="搜索" class="er_botton" />
						</p>
					</li>
				</ul>
			</div>

			<div id="high_area" class="erji_table">

				<table width="100%" border="1">
					<tr valign="middle" bgcolor="#f8f8f8">
						<td align="center">选择</td>
						<td align="center"><table width="100%" border="0">
								<tr class="orderCond" id="software_name">
									<td width="60%" align="right" style="border: none;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;软件名称&nbsp;</td>
									<td width="40%" align="left" style="border: none;"><img class="asc_desc" src="<%=bp %>/images/s_x.png" style="cursor: pointer;"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								</tr>
							</table></td>
						<td align="center"><table width="100%" border="0">
								<tr class="orderCond" id="software_type">
									<td width="60%" align="right" style="border: none;">软件类型&nbsp;</td>
									<td width="40%" align="left" style="border: none;"><img class="asc_desc" src="<%=bp %>/images/s_x.png" style="cursor: pointer;"/></td>
								</tr>
							</table></td>
						<td align="center"><table width="100%" border="0">
								<tr class="orderCond" id="software_versions">
									<td width="60%" align="right" style="border: none;">软件版本&nbsp;</td>
									<td width="49%" align="left" style="border: none;"><img class="asc_desc" src="<%=bp %>/images/s_x.png" style="cursor: pointer;" /></td>
								</tr >
							</table></td>
						<td align="center"><table width="100%" border="0">
								<tr class="orderCond" id="command_time">
									<td width="60%" align="right" style="border: none;">提交时间&nbsp;</td>
									<td width="40%" align="left" style="border: none;"><img class="asc_desc" src="<%=bp %>/images/s_x.png" style="cursor: pointer;" /></td>
								</tr>
							</table></td>
							<td align="center"><table width="100%" border="0">
								<tr class="orderCond" id="process_type">
									<td width="60%" align="right" style="border: none;">当前操作&nbsp;</td>
									<td width="40%" align="left" style="border: none;"><img class="asc_desc" src="<%=bp %>/images/s_x.png" style="cursor: pointer;" /></td>
								</tr>
							</table></td>
						<td align="center"><table width="100%" border="0">
								<tr class="orderCond" id="software_status">
									<td width="60%" align="right" style="border: none;">状态&nbsp;</td>
									<td width="40%" align="left" style="border: none;"><img class="asc_desc" src="<%=bp %>/images/s_x.png" style="cursor: pointer;" /></td>
								</tr>
							</table></td>
					</tr>
					<c:forEach items="${pageModel.dataList }" var="softwareDate">
						<tr id="tr${softwareDate.id }">
							<td align="center" valign="middle"><label> <input
									type="radio" name="radio" value="${softwareDate.softwareStatus }" id="${softwareDate.id }"  choiceProcessType="${softwareDate.processType }"/>
							</label></td>
							<td align="center" valign="middle"><a href="${_base }/software/applyDetail?softId=${softwareDate.softwareId}&softStatus=${softwareDate.softwareStatus}"><c:out
									value="${softwareDate.softwareName }"></c:out></a></td>
							<td align="center" valign="middle"><c:out
									value="${softwareDate.softwareType }"></c:out></td>
							<td align="center" valign="middle"><c:out
									value="${softwareDate.softwareVersions}"></c:out></td>
							<td align="center" valign="middle"><c:out
									value="${fn:substring(softwareDate.commandTime,0,10) }"></c:out></td>
							<td align="center" valign="middle">
								<c:if test="${softwareDate.processType =='1'}">签约申请</c:if>
								<c:if test="${softwareDate.processType =='2'}">下架申请</c:if>
								<c:if test="${softwareDate.processType =='3'}">升级申请</c:if>
								<c:if test="${softwareDate.processType =='4'}">变更申请</c:if>
							</td>
							<td align="center" valign="middle">
								<c:if test="${softwareDate.softwareStatus == '0'}">
									<span id="${softwareDate.id }Status" style="color: #2159d6;" value="${softwareDate.softwareStatus}">未提交</span>
								</c:if><c:if test="${softwareDate.softwareStatus == '1'}">
									<span id="${softwareDate.id }Status" style="color: #2159d6;" value="${softwareDate.softwareStatus}">待审核</span>
								</c:if><c:if test="${softwareDate.softwareStatus == '2'}">
									<span id="${softwareDate.id }Status" style="color: #2159d6;" value="${softwareDate.softwareStatus}">待配置</span>
								</c:if><c:if test="${softwareDate.softwareStatus == '3'}">
									<span id="${softwareDate.id }Status" style="color: #2159d6;" value="${softwareDate.softwareStatus}">审核未通过</span>
								</c:if><c:if test="${softwareDate.softwareStatus == '4'}">
									<span id="${softwareDate.id }Status" style="color: #2159d6;" value="${softwareDate.softwareStatus}">待发布</span>
								</c:if><c:if test="${softwareDate.softwareStatus == '5'}">
									<span id="${softwareDate.id }Status" style="color: #2159d6;" value="${softwareDate.softwareStatus}">已发布</span>
								</c:if> <c:if test="${softwareDate.softwareStatus == '6'}">
									<span id="${softwareDate.id }Status" style="color: #2159d6;" value="${softwareDate.softwareStatus}">已上架</span>
								</c:if><c:if test="${softwareDate.softwareStatus == '7'}">
									<span id="${softwareDate.id }Status" style="color: #2159d6;" value="${softwareDate.softwareStatus}">上架审核不通过</span>
								</c:if><c:if test="${softwareDate.softwareStatus == '8'}">
									<span id="${softwareDate.id }Status" style="color: #2159d6;" value="${softwareDate.softwareStatus}">待下架</span>
								</c:if><c:if test="${softwareDate.softwareStatus == '9'}">
									<span id="${softwareDate.id }Status" style="color: #2159d6;" value="${softwareDate.softwareStatus}">已下架</span>
								</c:if><c:if test="${softwareDate.softwareStatus == '10'}">
									<span id="${softwareDate.id }Status" style="color: #2159d6;" value="${softwareDate.softwareStatus}">待派单</span>
								</c:if><c:if test="${softwareDate.softwareStatus == '11'}">
									<span id="${softwareDate.id }Status" style="color: #2159d6;" value="${softwareDate.softwareStatus}">待回单</span>
								</c:if></td>
						</tr>
					</c:forEach>


				</table>
			</div>

			<div class="main_C_fenye" id="sp_div">
				
			</div>
	</div>
</div>
 
</div>

 <%@ include file="/jsp/common/footer.jsp"%>

</body>
</html>
