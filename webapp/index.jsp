<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String _base = request.getContextPath();
	String bp = _base;
	request.setAttribute("_base", _base);
	response.setHeader("Cache-Control","no-cache");   
	response.setDateHeader("Expires",0);   
	response.setHeader("Pragma","No-cache");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=_base %>/js/jquery-1.9.1.min.js"></script>
<title>沃云软件商门户</title>
</head>
<body>
我是首页
<input type="button" value="登出" onclick="logout()"/>
</body>
<script type="text/javascript">
	function logout() {
		var url = "${_base}/loginInfoAction/logout";
		window.location.href(url);
	}
</script>
</html>