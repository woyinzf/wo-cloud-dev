<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,com.ai.wocloud.bean.* "%>
<%@ taglib uri="/WEB-INF/lib/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/lib/fn.tld" prefix="fn"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/jsp/common/common.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>沃云软件商门户</title>
<script src="${_base}/js/caidna.js" type="text/javascript"></script>
<script src="${_base}/js/jquery.form.js" type="text/javascript"></script>
<script src="${_base}/js/utils.js" type="text/javascript"></script>
<script src="${_base}/js/PopupDiv_v1.0.js" type="text/javascript"></script>
<link href="${_base}/css/PopupDiv.css" rel="stylesheet" type="text/css" />
<style type="text/css">
#preview{width:150px;height:150px;border:1px solid gray;overflow:hidden;margin-left:170px;}
#imghead {filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);}
</style>
<script type="text/javascript">
var rowCount = 0;
var colCount = 0;
var addRowCount = 0;
var addColCount = 0;


$(document).ready(function(){
	turnit(6,2);
	$("#menu_spShowAllSoftware").addClass("xunz");
	$("#head_myApp").addClass("shoy");
	initData();
	showTable();
	initrow();
	
	//显示图片
	$("#imghead").attr('src', '${_base}/upload/software/${LOGIN_INFO.loginName }/${isSoftware.softwareImgUrl }');
	$("#preview").show("slow");
});

//修改软件页面初始化数据的方法
function initData(){
	$("#introduceError").hide();
	$("#logoError").hide();
	$("#sellproportionError").hide();
	$("#sellproportionError1").hide();
	$("#chackError").hide();
}

//图片预览
function previewImage(file){
	$("#logoError").hide();
	  var MAXWIDTH  = 150;
	  var MAXHEIGHT = 150;
	  var div = document.getElementById('preview');
	  if (file.files && file.files[0])
	  {
	      div.innerHTML ='<img id=imghead>';
	      var img = document.getElementById('imghead');
	      img.onload = function(){
	        var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
	        img.width  =  rect.width;
	        img.height =  rect.height;
	        if(img.width<MAXWIDTH) img.width=MAXWIDTH;
	        if(img.height<MAXHEIGHT) img.height=MAXHEIGHT;
	        img.style.marginTop = rect.top+'px';
	      };
	      var reader = new FileReader();
	      reader.onload = function(evt){img.src = evt.target.result;};
	      reader.readAsDataURL(file.files[0]);
	  }
	  else //兼容IE
	  {
	    file.select();
	    var src = document.selection.createRange().text;
	    div.innerHTML = '<img id=imghead>';
	    var img = document.getElementById('imghead');
	    img.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = src;
	    var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
	    status =('rect:'+rect.top+','+rect.left+','+rect.width+','+rect.height);
	    div.innerHTML = "<div id=divhead style='width:"+rect.width+"px;height:"+rect.height+"px;margin-top:"+rect.top+"px;"+sFilter+src+"\"'></div>";
	  }
	  
	//修改ie下fakepath问题
	    var str= new Array();
	    str = $("#myfile1").val().split("\\");
	    if (str.length != 0) {
		    $("#softwareImgUrl").val(str[str.length - 1]);
	    } else {
	    	$("#softwareImgUrl").val($("#myfile1").val());
	    }
	    
	  $("#preview").show("slow");
};
function clacImgZoomParam( maxWidth, maxHeight, width, height ){
    var param = {top:0, left:0, width:width, height:height};
    if( width>maxWidth || height>maxHeight )
    {
        rateWidth = width / maxWidth;
        rateHeight = height / maxHeight;
        if( rateWidth > rateHeight )
        {
            param.width =  maxWidth;
            param.height = Math.round(height / rateWidth);
        }else
        {
            param.width = Math.round(width / rateHeight);
            param.height = maxHeight;
        }
    }
    if(param.width<maxWidth)  param.width=maxWidth;
    if(param.height<maxHeight) param.height=maxHeight;
    param.left = Math.round((maxWidth - param.width) / 2);
    param.top = Math.round((maxHeight - param.height) / 2);
    return param;
};

//初始化表格参数
function initrow(){
	var softwareKind = $("#softwareKind").val();
	if(softwareKind =="1"){
		rowCount = $("#softwareData_jichu tr").length;
		colCount =$("#table_head_jichu td").length;
		addColCount = colCount;
		addRowCount = rowCount;
	}else{
		rowCount = $("#softwareData_yewu tr").length;
		colCount =$("#table_head_yewu td").length;
		addColCount = colCount;
		addRowCount = rowCount;
	}
	
}

//初始化表格
function showTable(){
		var softwareKind = $("#softwareKind").val();
		if(softwareKind == "1"){
			$("#jichu_div").show();
			$("#yewu_div").hide();
		}else{
			$("#jichu_div").hide();
			$("#yewu_div").show();
		}
}
	
//跳转到软件列表页面
function jump_to_sp(){
	location.href="${_base }/software/spfindAllSoftware?orderCond=command_time&orderType=desc";
}
	
//修改页面的提交审核
function submit(){
	var result = verify();
	if(result == false){
		return false;
	}else{
	var softwareKind = $("#softwareKind").val();
	var colCount = 0;
   	var rowCount = 0;
   	var datas = null;
   	var title = null;
   	if(softwareKind == "1"){
   		colCount = $("#table_head_jichu td").length;
   		rowCount = $("#softwareData_jichu tr").length;
   		for(var m=0;m<colCount-1;m++){
   			if(title == null){
   				title = $("#table_head_jichu td:eq("+m+")").text();
   			}else{
   				title = title +","+$("#table_head_jichu td:eq("+m+")").text();
   			}
   		};
   		for(var i=1;i<rowCount;i++){
   			var data = null;
   			for(var j=0;j<colCount-1;j++){
   				if(data == null){
   					data = $("#softwareData_jichu tr:eq("+i+") input:eq("+j+")").val();
   				}else{
   					if(j==1){
   						data =data +","+$("#softwareData_jichu tr:eq("+i+") select option:selected").val()+":"+$("#softwareData_jichu tr:eq("+i+") select option:selected").text();
   					}else{
   						data =data +","+$("#softwareData_jichu tr:eq("+i+") input:eq("+(j-1)+")").val();
   					}
   				}
   			}
   			if(datas == null){
   				datas = data;
   			}else{
   				datas = datas +","+data;
   			}
   		}} else {
   		colCount = $("#table_head_yewu td").length;
   		rowCount = $("#softwareData_yewu tr").length;
   		for(var m=0;m<colCount-1;m++){
   			if(title == null){
   				title = $("#table_head_yewu td:eq("+m+")").text();
   			}else{
   				title = title +","+$("#table_head_yewu td:eq("+m+")").text();
   			}
   		};
   		for(var i=1;i<rowCount;i++){
   			var data = null;
   			for(var j=0;j<colCount-1;j++){
   				if(data == null){
   					data = $("#softwareData_yewu tr:eq("+i+") input:eq("+j+")").val();
   				} else {
   					if(j==2){
   						data =data +","+$("#softwareData_yewu tr:eq("+i+") select option:selected").val()+":"+$("#softwareData_yewu tr:eq("+i+") select option:selected").text();
   					} else {
   						if(j<2){
   							data =data +","+$("#softwareData_yewu tr:eq("+i+") input:eq("+j+")").val();
   						} else {
   							data =data +","+$("#softwareData_yewu tr:eq("+i+") input:eq("+(j-1)+")").val();
   						}
   					}
   				}
   			}
   			if(datas == null){
   				datas = data;
   			} else {
   				datas = datas +","+data;
   			}
   		}
   	};
   	getMask();
   	var options = {
   			async : false,
			type: "POST",
			showBusi : false,
			modal : true,
			contentType:'application/x-www-form-urlencoded; charset=UTF-8',
			url: "${_base}/software/updateSoftware",
			dataType: 'text',
			data:{
					id: '${isSoftware.id}',
					softwareData:datas,
					tableTitle:title,
					tableRow:rowCount-1,
					tabelCol:colCount,
					softwareName : $("#softwareName").val(),
					softwareOffer : $("#softwareOffer").val(),
					softwareVersions : $("#softwareVersions").val(),
					softwareIntroduce : $("#softwareIntroduce").val().replace(/[ \r\n]/g,""),
					serviceData : $("#service").val(),
					softwareSellProportion : $("#softwareSellProportion").val(),
					softwareSpecificationUrl : $("#softwareSpecificationUrl").val(),
					softwareConfigSpecificationUrl : $("#softwareConfigSpecificationUrl").val(),
					woCloudUserName : $("#woCloudUserName").val(),
					softwareSellAuthorizationUrl : $("#softwareSellAuthorizationUrl").val()
					}, 
			success: function (data) {
				document.getElementById("showWait").style.display ="none";
				var json=$.parseJSON(data);
				if(json&&json.RES_RESULT=="SUCCESS"){
					info_alert(json.RES_MSG,jump_to_sp);
				} else {
					info_alert(json.RES_DATA, disMask);
					return false;
				}
			},
			error:function(){
				info_alert("保存失败");
				return false;
			}
		};
		$('#form1').ajaxSubmit(options);
	}
}

//用于长传图片按钮的事件
function fileUpload(temp){
	if(temp ==0){
		$("#myfile1").click();
	}
	if(temp ==1){
		$("#myfile2").click();
	}
	if(temp ==2){
		$("#myfile3").click();
	}
	
	if(temp ==5){
		$("#myfile6").click();
	}
}
function showFileName(tip){
	if(tip==0){
		$("#softwareImgUrl").val($("#myfile1").val());
		if($("#myfile1").val()==''){
			$("#preview").hide();
		}
	}else if(tip==1){
		//修改ie下fakepath问题
	    var str= new Array();
	    str = $("#myfile2").val().split("\\");
	    if (str.length != 0) {
		    $("#softwareSpecificationUrl").val(str[str.length - 1]);
	    } else {
	    	$("#softwareSpecificationUrl").val($("#myfile2").val());
	    }
	}else if(tip==2){
		//修改ie下fakepath问题
	    var str= new Array();
	    str = $("#myfile3").val().split("\\");
	    if (str.length != 0) {
		    $("#softwareConfigSpecificationUrl").val(str[str.length - 1]);
	    } else {
	    	$("#softwareConfigSpecificationUrl").val($("#myfile3").val());
	    }
	}else{
		//修改ie下fakepath问题
	    var str= new Array();
	    str = $("#myfile6").val().split("\\");
	    if (str.length != 0) {
		    $("#softwareSellAuthorizationUrl").val(str[str.length - 1]);
	    } else {
	    	$("#softwareSellAuthorizationUrl").val($("#myfile6").val());
	    }
	}
};

//增加行方法
function addRow(){
	var softwareKind = $("#softwareKind").val();
	if(softwareKind=="1"){
		rowCount = $("#softwareData_jichu tr").length;
    	colCount = $("#table_head_jichu td").length;
        var row = '<tr valign="middle" bgcolor="white" class="tr_'+rowCount+'" id="tr_'+rowCount+'">';
        for(var i=0;i<colCount-1;i++){
			var temp = '<td align="center" class="td_'+i+'" id="td_'+i+'"><input type="text"  id="input_'+i+'"></input></td>';
			if(i==1){
				temp = '<td align="center" class="td_'+i+'" id="td_'+i+'"><select   id="select_'+i+'">'+
				'<c:forEach  items="${vmData}" var="items">'+
				'<option value="<c:out value='${items.productId }'/>"><c:out value="${items.name }"/>：CPU<c:out value="${items.cpuValue }"/>核、内存<c:out value="${items.value }"/>G、硬盘<c:out value="${items.osDisk }"/>G</option></c:forEach></select></td>';
			}
			row += temp;
		}
		row = row+'<td align="center" class="cl1" id="cl1"><a href="javascript:delRow('+rowCount+');">删除</a></td></tr>';
		$("#softwareData_jichu").append(row );
	} else {
		rowCount = $("#softwareData_yewu tr").length;
    	colCount = $("#table_head_yewu td").length;
        var row = '<tr valign="middle" bgcolor="white" class="tr_'+rowCount+'" id="tr_'+rowCount+'">';
        for(var i=0;i<colCount-1;i++){
			var temp = '<td align="center" class="td_'+i+'" id="td_'+i+'"><input type="text"  id="input_'+i+'"></input></td>';
			if(i==2){
				temp = '<td align="center" class="td_'+i+'" id="td_'+i+'"><select   id="select_'+i+'">'+
				'<c:forEach  items="${vmData}" var="items">'+
				'<option value="<c:out value='${items.productId }'/>"><c:out value="${items.name }"/>：CPU<c:out value="${items.cpuValue }"/>核、内存<c:out value="${items.value }"/>G、硬盘<c:out value="${items.osDisk }"/>G</option></c:forEach></select></td>';
			}
			row += temp;
		}
        row = row+'<td align="center" class="cl1" id="cl1"><a href="javascript:delRow('+rowCount+');">删除</a></td></tr>';
		$("#softwareData_yewu").append(row );
	}
};

//删除行
function delRow(_id){
	var softwareKind = $("#softwareKind").val();
	if(softwareKind == "1"){
		 $("#softwareData_jichu .tr_"+_id).remove();
	        rowCount--;
	} else {
		$("#softwareData_yewu .tr_"+_id).remove();
        rowCount--;
	}
};
    
//沃云门户超链接
function toCreateUser(){
	info_alert("您在沃云门户的账号名称为${userId}@softwareProvider,初始密码同您在软件商门户的初始密码 !",jump_to_login);    	
}
function jump_to_login(){
	location.href="http://www.wocloud.cn";
}
    
//页面控件校验
function verify(){
	//控件校验
	var softwareKind = $("#softwareKind").val();
	var colCount = 0;
  	var rowCount = 0;
  	var title = null;
	var softwareIntroduce = $("#softwareIntroduce").val();
	if(softwareIntroduce == null || softwareIntroduce ==""){
		$("#introduceError").show();
		$("#softwareIntroduce").focus();
		return false;
	} else {
		$("#introduceError").hide();
	}
	var softwareImgUrl = $("#softwareImgUrl").val();
	if(softwareImgUrl == null || softwareImgUrl ==""){
		$("#logoError").show();
		$("#softwareImgUrl").focus();
		return false;
	} else {
		$("#logoError").hide();
	}
	var softwareSellProportion = $("#softwareSellProportion").val();
	if(softwareSellProportion == null || softwareSellProportion ==""){
		$("#sellproportionError").show();
		$("#softwareSellProportion").focus();
		return false;
	} else {
		$("#sellproportionError").hide();
	}
	if(softwareKind == "1"){
		colCount = $("#table_head_jichu td").length;
		rowCount = $("#softwareData_jichu tr").length;
    	for(var i=addRowCount;i<rowCount;i++){
    		var data = null;
    		for(var j=0;j<colCount-1;j++){
    			if(j<1){
    				title = $("#table_head_jichu td:eq("+j+")").text();
    				data = $("#softwareData_jichu tr:eq("+i+") input:eq("+j+")").val();
    			}
    			if(j==1){
  					data =$("#softwareData_jichu tr:eq("+i+") select option:selected").val()+":"+$("#softwareData_jichu tr:eq("+i+") select option:selected").text();
  				}if(j>1){
  					title = $("#table_head_jichu td:eq("+j+")").text();
    				data = $("#softwareData_jichu tr:eq("+i+") input:eq("+(j-1)+")").val();
  				}
  				if(data == null || data == ""){
					
					info_alert(title+"不能为空！");
					return false;
				} else {
					if (j>2) {
						if(!/^\+?[1-9][0-9]*$/.test(data)){
								info_alert(title+"必须为正整数！");
							  return false;
					    }
					}
				}
    		}
    	}
    } else {
		colCount = $("#table_head_yewu td").length;
		rowCount = $("#softwareData_yewu tr").length;
    	for(var i=addRowCount;i<rowCount;i++){
    		var data = null;
    		for(var j=0;j<colCount-1;j++){
    			if(j<2){
    				title = $("#table_head_yewu td:eq("+j+")").text();
    				data = $("#softwareData_yewu tr:eq("+i+") input:eq("+j+")").val();
    			}
				if(j==2){
					data =$("#softwareData_yewu tr:eq("+i+") select option:selected").val()+":"+$("#softwareData_yewu tr:eq("+i+") select option:selected").text();
				}
				if(j>2){
					title = $("#table_head_yewu td:eq("+j+")").text();
    				data = $("#softwareData_yewu tr:eq("+i+") input:eq("+(j-1)+")").val();
  				}
				if(data == null || data == ""){
					info_alert(title+"不能为空！");
					return false;
				} else {
					if (j>2) {
						if(!/^\+?[1-9][0-9]*$/.test(data)){
								info_alert(title+"必须为正整数！");
							  return false;
					    }
					}
				}
			}
		}
	};
	if(!/^[0-9]+(.[0-9]{1})?$/.test(softwareSellProportion)){
		$("#sellproportionError1").show();
		  $("#softwareSellProportion").focus();
		  return false;
    } else{
    	$("#sellproportionError1").hide();
    }
	if(softwareSellProportion<0 || softwareSellProportion>100){
		$("#sellproportionError1").show();
		$("#softwareSellProportion").focus();
		return false;
	}else{
		$("#sellproportionError1").hide();
	}
	var ghoName = $("#ghoName").val();
	if(ghoName == null || ghoName ==""){
		$("#jingXiangError").show();
		$("#ghoName").focus();
		return false;
	}else{
		$("#jingXiangError").hide();
	}
	var chackBox = $("input[type='checkbox']").is(':checked');
	if(chackBox == true){
		$("#chackError").hide();
		return true;
	} else {
		$("#chackError").show();
		return false;
	}
	return true;
}
    
  //做树
    function buildTree(){
    	//软件类型，树结构
    	var tree;
    	tree = $('#tree').tree({
    		checkbox : false,
    		url : "${_base}/software/softType",
    		onBeforeExpand : function(node, param) {
    			// alert(node.id);
    			$('#tree').tree('options').url = "${_base}/software/softType"
    					+ node.id; // change the url
    			// param.myattr = 'test'; // or change request parameter
    		},
    		onClick : function(node) {
    			if (node.children != null && node.children != "") {
    				$("#tree").empty();
    			} else {
    				$("#softwareType").val(node.id);
    				$("#softwareTypeName").val(node.text);
    				$("#tree").empty();
    			}
    		}
    	});
    }
    //清除树
    function desTree() {
    	setTimeout(function() {$("#tree").empty();}, 300);
    }
    
  //分成比例 失去焦点进行的校验
    function checkSoftwareSellProportion() {
    	var softwareSellProportion = $("#softwareSellProportion").val();
    	if(!/^[0-9]+(.[0-9]{1})?$/.test(softwareSellProportion)){
    		$("#sellproportionError1").show();
  		  $("#softwareSellProportion").focus();
  		  return false;
        } else{
        	$("#sellproportionError1").hide();
        }
	  if(softwareSellProportion<0 || softwareSellProportion>100){
		  $("#sellproportionError1").show();
		  $("#softwareSellProportion").focus();
		  return false;
	  }else{
		  $("#sellproportionError1").hide();
	  }
    }
</script>
</head>
<body>
	<form id="form1" method="post" enctype="multipart/form-data">
	<div class="wai_da">
		<%@ include file="/jsp/common/header.jsp"%>
		<div class="erji_nei">
			<%@ include file="/jsp/common/leftMenu.jsp"%>
			<div class="erji_nei_right">
  				<div class="erji_weiz">
  					<ul>
  						<li><A href="#">首页</A>&nbsp;></li>
  						<li><A href="#">我的软件</A>&nbsp;></li>
  						<li><A href="#">软件变更</A></li>
  					</ul>
  				</div>
  				 <div class="erji_jiben">
 					<div class="erji_jiben_A"><P>基本信息</P></div>
 					<div class="erji_jiben_input">
 						<ul>
 							<li>
 								<p class="ej_wz">软件种类：</p>
 								<p>
 								<input type="hidden" id="softwareKind" value="${softwareKind }"/>${isSoftware.softwareKind }
 								</p>
 							</li>
 							<li>
 								<p class="ej_wz">软件名称：</p>
 								<p>${isSoftware.softwareName }<input id="softwareName" type="hidden" value="${isSoftware.softwareName }"/></p>
 							</li>
 						</ul>
 						<ul>
 							<li>
 								<p class="ej_wz">软件提供商：</p>
 								<p>${isSoftware.softwareOffer }<input id="softwareOffer"  type="hidden"  value="${isSoftware.softwareOffer }"/></p>
 							</li>
 							<li>
 								<p class="ej_wz">软件类型：</p>
 								<p>
 									${isSoftware.softwareType }<input id="softwareType" type="hidden" value="${isSoftware.softwareType }"/>
 								</p>
 							</li>
 						</ul>
 						<ul>
 							<li>
 								<p class="ej_wz">软件版本号：</p>
 								<p>${isSoftware.softwareVersions }<input id="softwareVersions" type="hidden" value="${isSoftware.softwareVersions }" maxlength="5"/></p>
 							</li>
 							<li>
 								<p class="ej_wz">操作系统：</p>
 								<p>
 								${isSoftware.operateSystem }<input id="operateSystem" type="hidden" value="${isSoftware.operateSystem }"/>
 								</p>
 							</li>
 						</ul>
						<ul>
							<li style=" width:870px; margin-right:0px;">
 								<p class="ej_wz">软件介绍：</p>
 								<p><textarea id="softwareIntroduce"   class="ej_input_gao" >${isSoftware.softwareIntroduce }</textarea> </p>
 								<div id="introduceError"><p style="margin-left: 120px; color: red">软件介绍不能为空</p></div>
 							</li>
 						</ul>
  						<ul>
 							<li class="chag_li">
 								<p class="ej_wz">图片：</p>
 								<p><input name="" type="text"  class="ej_input" id="softwareImgUrl" value="${isSoftware.softwareImgUrl }" /></p>
 								<p><input type="button" value="浏览"  class="er_botton_a" onclick="fileUpload(0)"/></p>
 								<input value="${isSoftware.softwareImgUrl }" type="file" id="myfile1" onchange="previewImage(this);" style="display:none;" name="myfile1" />
 							</li>
 						</ul>
 						<ul>
 							<li>
 							<div id="preview" style="display:none; margin-left: 200px;">
								<img id="imghead"  />
								</div>
								<div id="logoError"><p style="margin-left: 120px; color: red">图片不能为空</p></div>
 							</li>
 						</ul>
 						<ul>
 							<li class="chag_li">
 								<p class="ej_wz">售后服务：</p>
 								<p><input name="" type="text" value="${serviceData }"  class="ej_input" id="service" onfocus="if(this.value==this.defaultValue){this.value=''}" onblur="if(this.value==''){this.value=this.defaultValue}" /></p>
 							</li>
 						</ul>
 						<ul>
 							<li class="chag_li">
 								<p class="ej_wz"><span>*</span>分成比例：</p>
 								<p><input id="softwareSellProportion" value="${isSoftware.softwareSellProportion }" type="text"  class="ej_input"  onblur="if(this.value==''){this.value='100';this.style.color='gray';}else{checkSoftwareSellProportion()}"/>% &nbsp;&nbsp;</p>
 								<p><span style="color: blue;text-align: center;">注：软件销售额结算给软件商的比例，100%为销售收入全部归软件商所有 </span></p>
 								<div id="sellproportionError"><p style="margin-left: 120px; color: red">分成比例不能为空</p></div>
 								<div id="sellproportionError1"><p style="margin-left: 120px; color: red">分成比例只能为0~100之间的数字(最多包含一位小数)</p></div>
 							</li>
 						</ul>
						<ul>
 							<li class="chag_li">
 								<p class="ej_wz">使用说明书：</p>
 								<p><input name="" type="text"  class="ej_input" id="softwareSpecificationUrl" value="${isSoftware.softwareSpecificationUrl }"/></p>
 								<p><input type="button" value="浏览"  class="er_botton_a" onclick="fileUpload(1)"/></p>
 								<input type="file" id="myfile2" onchange="showFileName(1);" style="display:none;" name="myfile2"  value="${isSoftware.softwareSpecificationUrl }"/>
 							</li>
 						</ul>
  						<ul>
 							<li class="chag_li">
 								<p class="ej_wz">配置说明书：</p>
 								<p><input name="" type="text"  class="ej_input" id="softwareConfigSpecificationUrl" value="${isSoftware.softwareConfigSpecificationUrl }"/></p>
 								<p><input type="button" value="浏览"  class="er_botton_a" onclick="fileUpload(2)"/></p>
 								<input type="file" id="myfile3" onchange="showFileName(2);" style="display:none;" name="myfile3" value="${isSoftware.softwareConfigSpecificationUrl }"/>
 							</li>
 						</ul>
 					</div>
  					<div class="erji_jiben_A"><P>价格信息</P></div>
 					<div class="erji_biaog">
  						<ul>
  							<li class="xz"><A href="javascript:addRow();">新增</A></li>
 						</ul>
 					</div>
 				</div>
  				<div class="erji_table" id="yewu_div">
					<table width="100%" border="1" id="softwareData_yewu">
  						<tr valign="middle" bgcolor="#f8f8f8" id="table_head_yewu">
    						<td align="center">产品类型</td>
    						<td align="center">软件规格</td>
						    <td align="center">服务器配置</td>
						    <td align="center">软件包月价格(元)</td>
						    <td align="center">软件包年价格(元)</td>
						    <td align="center">操作</td>
  						</tr>
  						<c:forEach items="${vmDatas}" var="item">
  							<tr valign="middle" bgcolor="white">
    						<td align="center"><input value="<c:out value='${item.productType }'/>"  style="text-align: center"/></td>
    						<td align="center"><input value="<c:out value='${item.productEtalon }'/>"  style="text-align: center"/></td>
						    <td align="center">
						    	<select >
						    		<c:forEach  items="${vmData}" var="items">
									<option value="<c:out value='${items.productId }'/>" <c:if test="${items.productId == item.productId }">selected="selected"</c:if>><c:out value="${items.name }"/>：CPU<c:out value="${items.cpuValue }"/>核、内存<c:out value="${items.value }"/>G、硬盘<c:out value="${items.osDisk }"/>G</option></c:forEach>
						    	</select>
						    </td>
						    <td align="center"><input value="<c:out value='${item.monthPrices }'/>"  style="text-align: center"/></td>
						    <td align="center"><input value="<c:out value='${item.yearPrices }'/>"  style="text-align: center"/></td>
						    <td align="center"><a href="javascript:delRow();">删除</a></td>
  						</tr>
  						</c:forEach>
					</table>
 				</div>
 				<div class="erji_table" id="jichu_div">
					<table width="100%" border="1" id="softwareData_jichu">
  						<tr valign="middle" bgcolor="#f8f8f8" id="table_head_jichu">
    						<td align="center">产品类型</td>
						    <td align="center">服务器配置</td>
						    <td align="center">软件包月价格(元)</td>
						    <td align="center">软件包年价格(元)</td>
						    <td align="center">操作</td>
  						</tr>
  						<c:forEach items="${vmDatas}" var="item">
  							<tr valign="middle" bgcolor="white">
    						<td align="center"><input value="<c:out value='${item.productType }'/>" style="text-align: center"/></td>
						    <td align="center">
						    	<select >
						    		<c:forEach  items="${vmData}" var="items">
									<option value="<c:out value='${items.productId }'/>" <c:if test="${items.productId == item.productId }">selected="selected"</c:if>><c:out value="${items.name }"/>：CPU<c:out value="${items.cpuValue }"/>核、内存<c:out value="${items.value }"/>G、硬盘<c:out value="${items.osDisk }"/>G</option></c:forEach>
						    	</select>
						    </td>
						    <td align="center"><input value="<c:out value='${item.monthPrices }'/>"  style="text-align: center"/></td>
						    <td align="center"><input value="<c:out value='${item.yearPrices }'/>"  style="text-align: center"/></td>
						    <td align="center"><a href="javascript:delRow();">删除</a></td>
  						</tr>
  						</c:forEach>
					</table>
 				</div>
 				<div class="erji_jiben_A"><P>镜像信息</P></div>
 				<div class="erji_jiben_input">
  					<ul>
 						<li class="chag_li">
							 <p class="ej_wz">镜像名称：</p>
							 <p>${isSoftware.ghoName }<input name="" type="hidden" id="ghoName" value="${isSoftware.ghoName }"/></p>
 						</li>
 					</ul>
 				</div>
				<div class="erji_jiben_A"><P>认证信息</P></div>
 				<div class="erji_jiben_input">
  					<ul>
 						<li class="chag_li">
							 <p class="ej_wz" >软件销售授权书：</p>
							 <p><input name="" type="text"  class="ej_input" id="softwareSellAuthorizationUrl" value="${isSoftware.softwareSellAuthorizationUrl }"/></p>
							 <p><input type="button" value="浏览"  class="er_botton_a" onclick="fileUpload(5)"/></p>
							 <input type="file" id="myfile6" onchange="showFileName(5);" style="display:none;" name="myfile6" value="${isSoftware.softwareSellAuthorizationUrl }"/>
 						</li>
 					</ul>
   					<ul>
 						<li class="chag_li">
 							<p><input name="" type="checkbox" id="checkbox_input" value=""  class="fanx"/></p>
 							<p onclick="" id="aid" target="_blank" style="color:blue">我已阅读并同意《中国联通沃云软件商门户服务协议》</p>
 							<div id="chackError"><p style="margin-left: 120px; color: red">请阅读并同意《中国联通沃云软件商门户服务协议》</p></div>
 						</li>
 					</ul>
 				</div>
 				<div class="erji_jiben_button">
 					<ul>
						 <li><A onclick="submit()">提交审核</A></li>
						 <li><A href="${_base }/software/spfindAllSoftware?orderCond=command_time&orderType=desc">取 消</A></li>
 					</ul>
 				</div>
			</div>
 		</div>
	</div>
	<%@ include file="/jsp/common/footer.jsp"%>
	</form>
</body>
</html>