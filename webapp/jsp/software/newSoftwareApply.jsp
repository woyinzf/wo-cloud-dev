<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.* ,com.ai.wocloud.system.session.*"%>
<%@ taglib uri="/WEB-INF/lib/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/lib/fn.tld" prefix="fn"%>
<%
	String userId = (String)request.getSession().getAttribute("userId");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/jsp/common/common.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>沃云软件商门户</title>
<style type="text/css">
#preview{width:150px;height:150px;border:1px solid gray;overflow:hidden;margin-left:170px;}
#imghead {filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);}
</style>
<link href="<%=bp %>/css/PopupDiv.css" rel="stylesheet" type="text/css" />
<script src="${_base}/js/PopupDiv_v1.0.js" type="text/javascript"></script>
<script src="${_base}/js/caidna.js" type="text/javascript"></script>
<script src="${_base}/js/jquery.form.js" type="text/javascript"></script>
<script src="${_base}/js/utils.js" type="text/javascript"></script>
<script src="${_base}/jsp/software/newSoftwareApply.js"  type="text/javascript"></script>
<script type="text/javascript">
var rowCount = 0;
var colCount = 0;
var addRowCount = 0;
var addColCount = 0;
//自增行
	function addRow(softwareKind){
		if (softwareKind == 0) {
			softwareKind = $("#softwareKind option:selected").val();
		}
		
		if(softwareKind=="1"){
			rowCount = $("#softwareData_jichu tr").length;
	    	colCount = $("#table_head_jichu td").length;
	        var row = '<tr valign="middle" bgcolor="#f8f8f8" class="tr_'+rowCount+'" id="tr_'+rowCount+'">';
	        for(var i=0;i<colCount-1;i++){
	        	var temp = "";
	        	if(i==0){
	        		temp = '<td align="center" class="td_'+i+'" id="td_'+i+'"><input type="text" maxlength="10" style="text-align:center"  id="input_'+i+'"></input></td>';
	        	}else{
	        		temp = '<td align="center" class="td_'+i+'" id="td_'+i+'"><input type="text" style="text-align:center"  maxlength="10" id="input_'+i+'"></input>￥</td>';
	        	}
				 
				if(i==1){
					temp = '<td align="center" class="td_'+i+'" id="td_'+i+'"><select   id="select_'+i+'">'+
					'<c:forEach  items="${vmData}" var="items">'+
					'<option value="<c:out value='${items.productId }'/>"><c:out value="${items.name }"/>：CPU<c:out value="${items.cpuValue }"/>核、内存<c:out value="${items.value }"/>G、硬盘<c:out value="${items.osDisk }"/>G</option></c:forEach></select></td>';
				}
				row += temp;
			}
	        row = row+'<td align="center" class="cl1" id="cl1"><a href="javascript:delRow('+rowCount+');">删除</a></td></tr>';
	        
			 $("#softwareData_jichu").append(row );
		}else if(softwareKind=="2"){
			rowCount = $("#softwareData_yewu tr").length;
	    	colCount = $("#table_head_yewu td").length;
	        var row = '<tr valign="middle" bgcolor="#f8f8f8" class="tr_'+rowCount+'" id="tr_'+rowCount+'">';
	        for(var i=0;i<colCount-1;i++){
	        	var temp = "";
	        	if(i==0 || i==1){
	        		temp = '<td align="center" class="td_'+i+'" id="td_'+i+'"><input type="text" maxlength="10" style="text-align:center"  id="input_'+i+'"></input></td>';
	        	}else{
	        		temp = '<td align="center" class="td_'+i+'" id="td_'+i+'"><input type="text" style="text-align:center"  maxlength="10" id="input_'+i+'"></input>￥</td>';
	        	}
				if(i==2){
					temp = '<td align="center" class="td_'+i+'" id="td_'+i+'"><select   id="select_'+i+'">'+
					'<c:forEach  items="${vmData}" var="items">'+
					'<option value="<c:out value='${items.productId }'/>"><c:out value="${items.name }"/>：cpu<c:out 	value="${items.cpuValue }"/>核、内存<c:out value="${items.value }"/>G、硬盘<c:out value="${items.osDisk }"/>G</option></c:forEach></select></td>';
				}
				row += temp;
			}
	        row = row+'<td align="center" class="cl1" id="cl1"><a href="javascript:delRow('+rowCount+');">删除</a></td></tr>';
	        
			$("#softwareData_yewu").append(row );
		}else{
			rowCount = $("#softwareData_service tr").length;
	    	colCount = $("#table_head_service td").length;
	        var row = '<tr valign="middle" bgcolor="#f8f8f8" class="tr_'+rowCount+'" id="tr_'+rowCount+'">';
	        for(var i=0;i<colCount-1;i++){
	        	var temp = "";
	        	temp = '<td align="center" class="td_'+i+'" id="td_'+i+'"><input type="text" maxlength="10" style="text-align:center"  id="input_'+i+'"></input></td>';
				row += temp;
			}
	        row = row+'<td align="center" class="cl1" id="cl1"><a href="javascript:delRow('+rowCount+');">删除</a></td></tr>';
	        
			 $("#softwareData_service").append(row );
		}
	};
	
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
  						<li><A href="#">软件新增</A></li>
  					</ul>
  				</div>
  				 <div class="erji_jiben">
 					<div class="erji_jiben_A"><P>基本信息</P></div>
 					<div class="erji_jiben_input">
 						<ul>
 							<li>
 								<p class="ej_wz"><span>*</span>软件种类：</p>
 								<p>
 									<select id="softwareKind"  class="ej_xiala" onmouseup="showTable()">
 										<option value="1">基础软件</option>
 										<option value="2">应用软件</option>
 										<option value="3">服务软件</option>
									</select>
 								</p>
 								<div id="kindError"><p style="margin-left: 120px; color: red">软件种类不能为空</p></div>
 							</li>
 							<li>
 								<p class="ej_wz"><span>*</span>软件名称：</p>
 								<p><input id="softwareName" type="text"  class="ej_input" onblur="checkSoftwareName()"/ maxlength="15"></p>
 								<div id="nameError"><p style="margin-left: 120px; color: red">软件名称不能为空</p></div>
 								<div id="nameError1"><p style="margin-left: 120px; color: red">软件名称已经存在</p></div>
 							</li>
 						</ul>
 						<ul>
 							<li>
 								<p class="ej_wz"><span>*</span>软件提供商：</p>
 								<p><input id="softwareOffer" maxlength="100" type="text"  onfocus="if(this.value=='100个非特殊字符以内'){this.value='';this.style.color='black'}" onblur="if(this.value==''){this.value='100个非特殊字符以内';this.style.color='gray';}" style="color: gray" class="ej_input" value="${COMPANY_INFO.companyName }"/></p>
 								<div id="offerError"><p style="margin-left: 120px; color: red">软件提供方不能为空</p></div>
 							</li>
 							<li>
 								<p class="ej_wz"><span>*</span>软件类型：</p>
 								<p>
 									<input id="softwareTypeName" class="ej_xiala" onclick="buildTree()" readonly="readonly" onblur="desTree()"/>
 									<input id="softwareType" type="hidden"/>
 									<div id="tree" style="position:absolute; ; z-index: 1000; margin-left: 130px; margin-top: 30px; background-color: white; width: 300px" />
 								</p>
 								<div id="typeError"><p style="margin-left: 120px; color: red">软件类型不能为空</p></div>
 							</li>
 						</ul>
 						<ul id="versionOs">
 							<li>
 								<p class="ej_wz"><span>*</span>软件版本号：</p>
 								<p><input id="softwareVersions" type="text"  class="ej_input" maxlength="5"/></p>
 								<div id="versionError"><p style="margin-left: 120px; color: red">软件版本号不能为空</p></div>
 							</li>
 							<li>
 								<p class="ej_wz"><span>*</span>操作系统：</p>
 								<p><select id="operateSystem" class="ej_xiala">
 										<c:forEach  items="${osTemplates}" var="items">
 											<option value="${items.id }"><c:out value="${items.name }"/></option>
 										</c:forEach>	
 									</select>
 								</p>
 								<div id="systemError"><p style="margin-left: 120px; color: red">操作系统不能为空</p></div>
 							</li>
 						</ul>
 						
 						<ul id="sloganUrl">
 							<li>
 								<p class="ej_wz"><span></span>产品slogan：</p>
 								<p><input id="slogan" type="text"  class="ej_input" maxlength="5"/></p>
 							</li>
 							<li>
 								<p class="ej_wz"><span></span>WEB服务地址：</p>
 								<p><input id="WEBUrl" type="text"  class="ej_input" /></p>
 							</li>
 						</ul>
 						
						<ul>
							<li style=" width:870px; margin-right:0px;">
 								<p class="ej_wz"><span>*</span>软件介绍：</p>
 								<p><textarea id="softwareIntroduce"  class="ej_input_gao" onKeyDown="LimitTextArea(this)" onKeyUp="LimitTextArea(this)" maxlength="450" onkeypress="LimitTextArea(this)"></textarea> </p>
 								<div id="introduceError"><p style="margin-left: 120px; color: red">软件介绍不能为空</p></div>
 							</li>
 						</ul>
 						
 						<ul id="functionUl" >
							<li style=" width:870px; margin-right:0px;">
 								<p class="ej_wz"><span>*</span>功能介绍：</p>
 								<p><textarea id="functionIntroduce"  class="ej_input_gao" onKeyDown="LimitTextArea(this)" onKeyUp="LimitTextArea(this)" maxlength="450" onkeypress="LimitTextArea(this)"></textarea> </p>
 								<div id="functionError"><p style="margin-left: 120px; color: red">功能介绍不能为空</p></div>
 							</li>
 						</ul>
 						
  						<ul>
 							<li class="chag_li">
 								<p class="ej_wz"><span>*</span>图片：</p>
 								<p><input name="" disabled="disabled" type="text"  class="ej_input" id="softwareImgUrl"/></p>
 								<p><input type="button" value="浏览"  class="er_botton_a" onclick="fileUpload(0)"/></p>
 								<p><span style="color: blue;text-align: center;margin-left:20px;"> 请上传150x150大小的图片,大小在5MB之内 </span></p>
 								<input type="file" id="myfile1" onchange="showFileName(0);previewImage(this);" style="display:none;" name="myfiles" />
 							</li>
 						</ul>
 						<ul>
 							<li>
 							<div id="preview" style="display:none;">
								<img id="imghead"  />
								</div>
								<div id="logoError"><p style="margin-left: 120px; color: red">图片不能为空</p></div>
								<div id="logoError1"><p style="margin-left: 120px; color: red">请选择正确的图片类型！</p></div>
 							</li>
 						</ul>
 						<ul id="customerService">
 							<li class="chag_li">
 								<p class="ej_wz">售后服务：</p>
 								<p><input name="" type="text" maxlength="15" value="售后服务电话、邮箱或者网络链接"  class="ej_input" id="service" onfocus="if(this.value=='售后服务电话、邮箱或者网络链接'){this.value='';this.style.color='black'}" onblur="if(this.value==''){this.value='售后服务电话、邮箱或者网络链接';this.style.color='gray';}" style="color: gray"/></p>
 							</li>
 						</ul>
 						<ul id="separatePercent">
 							<li class="chag_li">
 								<p class="ej_wz"><span>*</span>分成比例：</p>
 								<p><input id="softwareSellProportion" value="100" type="text"  class="ej_input" onfocus="if(this.value=='100'&& this.style.color=='gray'){this.value='';this.style.color='black'}" onblur="if(this.value==''){this.value='100';this.style.color='gray';}else{checkSoftwareSellProportion()}" style="color: gray"/>% &nbsp;&nbsp;</p>
 								<p><span style="color: blue;text-align: center;">注：软件销售额结算给软件商的比例，100%为销售收入全部归软件商所有 </span></p>
 								<div id="sellproportionError"><p style="margin-left: 120px; color: red">分成比例不能为空</p></div>
 								<div id="sellproportionError1"><p style="margin-left: 120px; color: red">分成比例只能为0~100之间的数字(最多包含一位小数)</p></div>
 							</li>
 						</ul>
						<ul>
 						<li class="chag_li">
 								<p class="ej_wz">使用说明书：</p>
 								<p><input name="" disabled="disabled" type="text"  class="ej_input" id="softwareSpecificationUrl"/></p>
 								<p><input type="button" value="浏览"    class="er_botton_a" onclick="fileUpload(1)"/></p>
 								<p><span style="color: blue;text-align: center;margin-left:20px;">注：支持各种文档格式 </span></p>
 								<input type="file" id="myfile2" onchange="showFileName(1);" style="display:none;" name="myfiles" />
 							</li>
 						</ul>
  						<ul id="configUl">
 							<li class="chag_li">
 								<p class="ej_wz">配置说明书：</p>
 								<p><input name="" disabled="disabled" type="text"  class="ej_input" id="softwareConfigSpecificationUrl"/></p>
 								<p><input type="button" value="浏览"  class="er_botton_a" onclick="fileUpload(2)"/></p>
 								<p><span style="color: blue;text-align: center;margin-left:20px;">注：支持各种文档格式 </span></p>
 								<input type="file" id="myfile3" onchange="showFileName(2);" style="display:none;" name="myfiles" />
 							</li>
 						</ul>
 					</div>
  					<div class="erji_jiben_A"><P>价格信息</P></div>
 					<div class="erji_biaog">
  						<ul>
  							<li class="xz"><A href="javascript:addRow(0);">新增</A></li>
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
					</table>
 				</div>
 				<div class="erji_table" id="service_div">
					<table width="100%" border="1" id="softwareData_service">
  						<tr valign="middle" bgcolor="#f8f8f8" id="table_head_service">
    						<td align="center">版本类型</td>
						    <td align="center">支持账户数</td>
						    <td align="center">包月价格（元）</td>
						    <td align="center">功能概述</td>
						    <td align="center">操作</td>
  						</tr>
					</table>
 				</div>
 				
 				<div class="erji_jiben_A" id="imageInfoDiv"><P>镜像信息</P></div>
 				<div class="erji_jiben_input" id="imageInfoInputDiv">
  					<ul>
 						<li class="chag_li">
							 <p class="ej_wz"><span>*</span>镜像名称：</p>
							 <p><select id="ghoName" name="softwareConfigUrl" class="ej_xiala">
									<c:forEach  items="${mirrorlist}" var="mirror">
										<option value="${mirror.imageid }">${mirror.imagename }</option>
									</c:forEach>	
									</select></p>&nbsp;&nbsp; &nbsp;&nbsp;
									<p style="margin-left:15px;"><a href="javascript:toCreateUser()" style="color: blue;">还没有镜像？请去沃云门户创建！</a></p>
 						</li>
 					</ul>
 				</div>
 				
 				<div class="erji_jiben_A" id="apiParamInfo"><p>API接口参数信息</p></div>
 				<div class="erji_jiben_input" id="apiParamInfoInput">
 					<ul>
 						<li>
 							<p class="ej_wz">创建账号的URL：</p>
 							<p><input type="text" class="ej_input" id="createUrl"/> </p>
 						</li>
 						
 						<li>
 							<p class="ej_wz">请求类型：</p>
 							<p><select id="requestTypeCre"  class="ej_xiala">
 										<option value="1">GET</option>
 										<option value="2">POST</option>
 										<option value="3" >PUT</option>
 										<option value="4" >DELETE</option>
									</select>
									</p>
 						</li>
 					</ul>	
 					
 					<ul>
 						<li >
 							<p class="ej_wz">创建账号的参数：</p>
 							<p><input type="text" class="ej_input" id="createParam"/> </p>
 						</li>
 						
 					</ul>
 					
 					<ul>
 						<li >
 							<p class="ej_wz">删除账号的URL：</p>
 							<p><input type="text" class="ej_input" id="deleteUrl"/> </p>
 						</li>
 						
 						<li >
 							<p class="ej_wz">请求类型：</p>
 							<p><select id="requestTypeDel"  class="ej_xiala">
 										<option value="1">GET</option>
 										<option value="2">POST</option>
 										<option value="3" >PUT</option>
 										<option value="4" >DELETE</option>
									</select>
									</p>
 						</li>
 					</ul>	
 					
 					<ul>
 						<li >
 							<p class="ej_wz">删除账号的参数：</p>
 							<p><input type="text" class="ej_input" id="deleteParam"/> </p>
 						</li>
 						
 					</ul>
 				</div>
 						
				<div class="erji_jiben_A"><P>认证信息</P></div>
 				<div class="erji_jiben_input">
  					<ul>
 						<li class="chag_li">
							 <p class="ej_wz" >软件销售授权书：</p>
							 <p><input name="" disabled="disabled"  type="text"  class="ej_input" id="softwareSellAuthorizationUrl"/></p>
							 <p><input type="button" value="浏览"  class="er_botton_a" onclick="fileUpload(5)"/></p>
							 <p><span style="color: blue;text-align: center;margin-left:20px;">注：支持各种文档格式 </span></p>
							 <input type="file" id="myfile6" onchange="showFileName(5);" style="display:none;" name="myfiles" />
 						</li>
 					</ul>
   					<ul>
 						<li class="chag_li">
 							<p><input name="" id="chackBox" type="checkbox" value=""  class="fanx"/></p>
 							<p onclick="" id="aid" target="_blank" style="color:blue" >我已阅读并同意《中国联通沃云软件商门户服务协议》</p>
 							<div id="chackError"><p style="margin-left: 120px; color: red">请阅读并同意《中国联通沃云软件商门户服务协议》</p></div>
 						</li>
 					</ul>
 				</div>
 				<div class="erji_jiben_button">
 					<ul>
						 <li><A href="javascript:void(0);" onclick="save()">保 存</A></li>
						 <li><A href="javascript:void(0);" onclick="submit()">提交审核</A></li>
						 <li><A href="${_base }/software/spfindAllSoftware?orderCond=command_time&orderType=asc">取 消</A></li>
 					</ul>
 				</div>
			</div>
 		</div>
	</div>
	</form>
	<%@ include file="/jsp/common/footer.jsp"%>
</body>
</html>