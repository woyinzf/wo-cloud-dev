<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String _base = request.getContextPath();
	String bp = _base;
	request.setAttribute("_base", _base);
	response.setHeader("Cache-Control","no-cache");   
	response.setDateHeader("Expires",0);   
	response.setHeader("Pragma","No-cache");
%>

<link href="<%=_base %>/css/css.css" rel="stylesheet" type="text/css" />
<link href="<%=bp %>/css/PopupDiv.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=_base %>/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=_base %>/js/jquery.validate.min.js"></script>
<script src="<%=bp%>/js/PopupDiv_v1.0.js" type="text/javascript"></script>
<script src="<%=bp%>/js/utils.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/jquery-easyui-1.3.5/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/jquery-easyui-1.3.5/themes/icon.css">
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-easyui-1.3.5/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/ZeroClipboard.js"></script>
<script>
var webpath = "<%=_base%>";

//遮罩 
function getMask() {
	var scrolltop = $(document).scrollTop();
	var height = document.body.scrollHeight;
	var screenHeight = $(window).height();
	$("#bg").css("height", parseInt(height));
	document.getElementById("bg").style.display ="block";
	$("#showWait").css("top", (parseInt(screenHeight)-200)/2 + parseInt(scrolltop) + 'px');
	document.getElementById("showWait").style.display ="block";
}

//取消遮罩
function disMask() {
	document.getElementById("bg").style.display ="none";
	document.getElementById("showWait").style.display ="none";
}
</script>
<div id="showWait" class="wait" ></div>