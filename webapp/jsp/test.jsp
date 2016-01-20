<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-1.9.1.min.js"></script>
 <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/jquery-easyui-1.3.5/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/jquery-easyui-1.3.5/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/jquery-easyui-1.3.5/demo/demo.css">
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-easyui-1.3.5/jquery.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-easyui-1.3.5/jquery.easyui.min.js"></script>
	
	<script type="text/javascript">
	$(function() {
var tree;
tree = $('#tree').tree({
	checkbox : false,
	url : 'softType',
	onBeforeExpand : function(node, param) {
		// alert(node.id);
		$('#tree').tree('options').url = "/software/softType"
				+ node.id; // change the url
		// param.myattr = 'test'; // or change request parameter
	},
	onClick : function(node) {
		clickTree(node.id);
	}
});
	});
</script>
	
	<div id="tree">
	</div>