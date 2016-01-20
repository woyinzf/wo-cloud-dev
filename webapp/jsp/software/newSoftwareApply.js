$(document).ready(function(){
	turnit(6,2);
	$("#menu_spShowAllSoftware").addClass("xunz");
	$("#head_mySoftware").addClass("shoy");
	
	showTable();
	initrow();
	queryOS();
	
});

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
	      }
	      var reader = new FileReader();
	      reader.onload = function(evt){img.src = evt.target.result;}
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
	var softwareKind = $("#softwareKind option:selected").val();
	if(softwareKind =="1"){
		rowCount = $("#softwareData_jichu tr").length;
		colCount =$("#table_head_jichu td").length;
		addColCount = colCount;
		addRowCount = rowCount;
	}else if(softwareKind=="2"){
		rowCount = $("#softwareData_yewu tr").length;
		colCount =$("#table_head_yewu td").length;
		addColCount = colCount;
		addRowCount = rowCount;
	}else{
		rowCount = $("#softwareData_service tr").length;
		colCount =$("#table_head_service td").length;
		addColCount = colCount;
		addRowCount = rowCount;
	}
	
	addRow(1);
	addRow(2);
	addRow(3);
}

//初始化表格
function showTable(){
	
	
		var softwareKind = $("#softwareKind option:selected").val();
		if(softwareKind == "1"){
			$("#jichu_div").show();
			$("#yewu_div").hide();
			$("#service_div").hide();
			
			$("#sloganUrl").hide();
			$("#functionUl").hide();
			$("#apiParamInfo").hide();
			$("#apiParamInfoInput").hide();
			
			$("#versionOs").show();
			$("#customerService").show();
			$("#separatePercent").show();
			$("#configUl").show();
			$("#imageInfoDiv").show();
			$("#imageInfoInputDiv").show();
		}else if(softwareKind=="2"){
			$("#jichu_div").hide();
			$("#yewu_div").show();
			$("#service_div").hide();
			
			$("#sloganUrl").hide();
			$("#functionUl").hide();
			$("#apiParamInfo").hide();
			$("#apiParamInfoInput").hide();
			
			$("#versionOs").show();
			$("#customerService").show();
			$("#separatePercent").show();
			$("#configUl").show();
			$("#imageInfoDiv").show();
			$("#imageInfoInputDiv").show();
		}else{
			$("#jichu_div").hide();
			$("#yewu_div").hide();
			$("#service_div").show();
			
			$("#versionOs").hide();
			$("#sloganUrl").show();
			$("#functionUl").show();
			$("#customerService").hide();
			$("#separatePercent").hide();
			$("#configUl").hide();
			$("#imageInfoDiv").hide();
			$("#imageInfoInputDiv").hide();
			$("#apiParamInfo").show();
			$("#apiParamInfoInput").show();
		}
		
		
		$("#kindError").hide();
		$("#nameError").hide();
		$("#nameError1").hide();
		$("#offerError").hide();
		$("#typeError").hide();
		$("#versionError").hide();
		$("#systemError").hide();
		$("#introduceError").hide();
		$("#logoError").hide();
		$("#sellproportionError").hide();
		$("#sellproportionError1").hide();
		$("#logoError1").hide();
		$("#woYunNameError").hide();
		$("#jingXiangError").hide();
		$("#chackError").hide();
		$("#functionError").hide();
}
	
	
	//新增软件页面的保存按钮
	function save(){
		var result = verify(true);
		if(result == false){
			return false;
		}else{
		var softwareKind = $("#softwareKind option:selected").val();
		var colCount = 0;
    	var rowCount = 0;
    	var datas = null;
    	var title = null;
    	if($("#logoError1").css("display")=="block"){
    		info_alert("请选择正确的图片类型！");
    		return false;
    	}
    	
    	if(softwareKind == "1"){
    		colCount = $("#table_head_jichu td").length;
    		rowCount = $("#softwareData_jichu tr").length;
    		if(rowCount == "1"){
    			info_alert("价格信息不能为空！");
    			return false;
    		}
    		for(var m=0;m<colCount-1;m++){
    			if(title == null){
    				title = $("#table_head_jichu td:eq("+m+")").text();
    			}else{
    				title = title +","+$("#table_head_jichu td:eq("+m+")").text();
    			}
    		
    	};
    	for(var i=addRowCount;i<rowCount;i++){
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
    		
    	}
    	}else{
    		colCount = $("#table_head_yewu td").length;
    		rowCount = $("#softwareData_yewu tr").length;
    		if(rowCount == "1"){
    			info_alert("价格信息不能为空！");
    			return false;
    		}
    		for(var m=0;m<colCount-1;m++){
    			if(title == null){
    				title = $("#table_head_yewu td:eq("+m+")").text();
    			}else{
    				title = title +","+$("#table_head_yewu td:eq("+m+")").text();
    			}
    		
    	};
    	for(var i=addRowCount;i<rowCount;i++){
    		var data = null;
    		for(var j=0;j<colCount-1;j++){
    			
    			if(data == null){
    				data = $("#softwareData_yewu tr:eq("+i+") input:eq("+j+")").val();
    			}else{
    				if(j==2){
    					data =data +","+$("#softwareData_yewu tr:eq("+i+") select option:selected").val()+":"+$("#softwareData_yewu tr:eq("+i+") select option:selected").text();
    				}else{
    					if(j<2){
    						data =data +","+$("#softwareData_yewu tr:eq("+i+") input:eq("+j+")").val();
    					}else{
    						data =data +","+$("#softwareData_yewu tr:eq("+i+") input:eq("+(j-1)+")").val();
    					}
    				
    				}
    			}
    		}
    		
    		if(datas == null){
    			datas = data;
    		}else{
    			datas = datas +","+data;
    		}
    		
    	}
    	};
    	getMask();
    	var options = {
    			async : true,
    		    type: "POST",
    		    showBusi : false,
    		    modal : true,
                url: '${_base}/software/insertSoftware',
                
                //dataType: 'text',
                data:{
		    	  softwareData:datas,
		    	  tableTitle:title,
		    	  tableRow:rowCount-addRowCount,
		    	  tabelCol:colCount,
		    	  //handleId : "12345678", //修改为用户id
		    	  softwareKind : $("#softwareKind option:selected").val(),
		    	  softwareName : $("#softwareName").val(),
		    	  
		    	  softwareOffer : $("#softwareOffer").val(),
		    	  softwareType : $("#softwareType").val(),
		    	  
		    	  softwareVersions : $("#softwareVersions").val(),
		    	  operateSystem : $("#operateSystem option:selected").val(),
		    	  softwareIntroduce : $("#softwareIntroduce").val().replace(/[ \r\n]/g,""),
		    	  softwareImgUrl : $("#softwareImgUrl").val(),
		    	  serviceData : $("#service").val(),
		    	  softwareSellProportion : $("#softwareSellProportion").val(),
		    	  
		    	  softwareSpecificationUrl : $("#softwareSpecificationUrl").val(),
		    	  softwareConfigSpecificationUrl : $("#softwareConfigSpecificationUrl").val(),
		    	  
		    	  ghoName: $("#ghoName").val()+","+$("#ghoName").find("option:selected").text(),		    	  
		    	  softwareSellAuthorizationUrl : $("#softwareSellAuthorizationUrl").val()
		      }, 
                success: function (data) {
                	document.getElementById("showWait").style.display ="none";
                	var json=$.parseJSON(data);
    		    	  if(json&&json.RES_RESULT=="SUCCESS"){
    		    		 
    		    		 // var url="${_base}/software/spfindAllSoftware";
    		    		  info_alert(json.RES_MSG,jump_to_sp);
      				      //window.location.href=url;
    		    	  }else{
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
	
	function jump_to_sp(){
		location.href="${_base}/software/spfindAllSoftware?orderCond=command_time&orderType=desc";
	}
	
	//新增页面的提交审核按钮
	function submit(){
		
		var result = verify(false);
		if($("#logoError1").css("display")=="block"){
    		info_alert("请选择正确的图片类型！");
    		return false;
    	}
		if(result == false){
			return false;
		}else{
		var softwareKind = $("#softwareKind option:selected").val();
		var colCount = 0;
    	var rowCount = 0;
    	var datas = null;
    	var title = null;
    	if(softwareKind == "1"){
    		colCount = $("#table_head_jichu td").length;
    		rowCount = $("#softwareData_jichu tr").length;
    		for(var m=0;m<colCount-1;m++){
    			if(rowCount == "1"){
        			info_alert("价格信息不能为空！");
        			return false;
        		}
    			if(title == null){
    				title = $("#table_head_jichu td:eq("+m+")").text();
    			}else{
    				title = title +","+$("#table_head_jichu td:eq("+m+")").text();
    			}
    		
    	};
    	for(var i=addRowCount;i<rowCount;i++){
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
    		
    	}
    	}else{
    		colCount = $("#table_head_yewu td").length;
    		rowCount = $("#softwareData_yewu tr").length;
    		if(rowCount == "1"){
    			info_alert("价格信息不能为空！");
    			return false;
    		}
    		for(var m=0;m<colCount-1;m++){
    			if(title == null){
    				title = $("#table_head_yewu td:eq("+m+")").text();
    			}else{
    				title = title +","+$("#table_head_yewu td:eq("+m+")").text();
    			}
    		
    	};
    	for(var i=addRowCount;i<rowCount;i++){
    		var data = null;
    		for(var j=0;j<colCount-1;j++){
    			
    			if(data == null){
    				data = $("#softwareData_yewu tr:eq("+i+") input:eq("+j+")").val();
    			}else{
    				
    				if(j==2){
    					data =data +","+$("#softwareData_yewu tr:eq("+i+") select option:selected").val()+":"+$("#softwareData_yewu tr:eq("+i+") select option:selected").text();
    				}else{
    					if(j<2){
    						data =data +","+$("#softwareData_yewu tr:eq("+i+") input:eq("+j+")").val();
    					}else{
    						data =data +","+$("#softwareData_yewu tr:eq("+i+") input:eq("+(j-1)+")").val();
    					}
    				}
    			}
    		}
    		
    		if(datas == null){
    			datas = data;
    		}else{
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
  		      url: "${_base}/applyOdd/addApplyOdd",
  		      dataType: 'text',
  		      data:{
  		    	  id:"0",
  		    	  softwareData:datas,
  		    	  tableTitle:title,
  		    	  tableRow:rowCount-addRowCount,
  		    	  tabelCol:colCount,
  		    	 // handleId : "12345678", //修改为用户id
  		    	  softwareKind : $("#softwareKind option:selected").val(),
  		    	  softwareName : $("#softwareName").val(),
  		    	  
  		    	  softwareOffer : $("#softwareOffer").val(),
  		    	  softwareType : $("#softwareType").val(),
  		    	  
  		    	  softwareVersions : $("#softwareVersions").val(),
  		    	  operateSystem : $("#operateSystem option:selected").val(),
  		    	  softwareIntroduce : $("#softwareIntroduce").val().replace(/[ \r\n]/g,""),
  		    	  softwareImgUrl : $("#softwareImgUrl").val(),
  		    	  serviceData : $("#service").val(),
  		    	  
  		    	  softwareSellProportion : $("#softwareSellProportion").val(),
  		    	  softwareSpecificationUrl : $("#softwareSpecificationUrl").val(),
  		    	  softwareConfigSpecificationUrl : $("#softwareConfigSpecificationUrl").val(),
  		    	  ghoName: $("#ghoName").val()+","+$("#ghoName").find("option:selected").text(),
  		    	  softwareSellAuthorizationUrl : $("#softwareSellAuthorizationUrl").val()
  		      }, 
  		      success: function(data) {
  		    	  document.getElementById("showWait").style.display ="none";
  		    	  var json=$.parseJSON(data);
  		    	  if(json&&json.RES_RESULT=="SUCCESS"){
  		    		info_alert(json.RES_MSG,jump_to_lsp);
  						//var url="${_base}/software/spfindAllSoftware";
  					   // window.location.href=url;
  					}else{
  						info_alert(json.RES_MSG, disMask);
  					}			    	  
  		       },
  		       error: function(){
  		    	 info_alert("提交审核失败");
  		       }
    	};
    	 $('#form1').ajaxSubmit(options);
	}
	}
	
	function jump_to_lsp(){
		location.href="${_base}/software/spfindAllSoftware?orderCond=command_time&orderType=desc";
	}
	
	//长传按钮执行的方法
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
	
				
	
		var ImgName;
		if(tip==0){
		
			$("#softwareImgUrl").val($("#myfile1").val());
			
			ImgName = $("#myfile1").val();
			if(ImgName==""){ 
				$("#preview").hide();
				
				return false; 
			}else{ 
				if(!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG|bmp|BMP)$/.test(ImgName)){ 
					$("#preview").hide();
					 $("#logoError1").show();
					return false; 
				} 
			}
			 $("#logoError1").hide();
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
	
	
	//删除行
	function delRow(_id){
		var softwareKind = $("#softwareKind option:selected").val();
		if(softwareKind == "1"){
			 $("#softwareData_jichu .tr_"+_id).remove();
		        rowCount--;
		}else if(softwareKind=="2"){
			$("#softwareData_yewu .tr_"+_id).remove();
	        rowCount--;
		}else{
			$("#softwareData_service .tr_"+_id).remove();
	        rowCount--;
		}
        
    };
    function queryOS(){
    	$.ajax({
			  async : false,
		      type: "POST",
		      showBusi : false,
		      modal : true,
		      contentType:'application/x-www-form-urlencoded; charset=UTF-8',
		      url: "${_base}/applyOdd/queryOsData",
		      dataType: 'text',
		      data:null, 
		      success: function(data) {
		    	
		       }
		  });
    }
    
    //沃云门户超链接
    function toCreateUser(){
    	
    	info_alert("您在沃云门户的账号名称为<%=userId%>@softwareProvider,初始密码同您在软件商门户的初始密码 !",jump_to_wocloud);    	
		//window.open ('http://www.wocloud.cn','newwindow','height=350,width=400,top=0,left=0,toolbar=yes,menubar=yes,scrollbars=yes, resizable=yes,location=yes, status=yes');
    }
    function jump_to_wocloud(){
    	save();
    	window.open("http://www.wocloud.cn");
    }
    
    function mouseOver(obj) {
    	$(obj).css('color','blue');
    }
    function mouseOut(obj) {
    	$(obj).css('color','black');
    }
    
    //新增页面的数据校验, ifSave用来确定如果是保存，则不校验镜像
    function verify(ifSave){
    	//控件校验
    	 var softwareKind = $("#softwareKind option:selected").val();
    	 
  		var colCount = 0;
      	var rowCount = 0;
      	var title = null;
      	
      	var fileSize = document.getElementById("myfile1").files[0].size;
	  	if(fileSize>5242880){
	  		info_alert("请上传大小在5MB之内的图片！");
	  	}
      	
      	var softwareName = $("#softwareName").val();
      	if(softwareName == null || softwareName ==""){
  		  $("#nameError").show();
  		$("#nameError1").hide();
  		  $("#softwareName").focus();
  		  return false;
  	  }else{
  		  $("#nameError").hide();
  	  }
      	var softwareOffer =$("#softwareOffer").val();
    	  if(softwareOffer == null || softwareOffer ==""){
    		  $("#offerError").show();
    		  $("#softwareOffer").focus();
    		  return false;
    	  }else{
    		  $("#offerError").hide();
    	  }
    	  var softwareTypeName = $("#softwareTypeName").val();
    	  if(softwareTypeName == null || softwareTypeName ==""){
    		  $("#typeError").show();
    		  $("#softwareTypeName").focus();
    		  return false;
    	  }else{
    		  $("#typeError").hide();
    	  }
    	  var softwareVersions = $("#softwareVersions").val();
    	  if(softwareVersions == null || softwareVersions ==""){
    		  $("#versionError").show();
    		  $("#softwareVersions").focus();
    		  return false;
    	  }else{
    		  $("#versionError").hide();
    	  }
    	  var operateSystem = $("#operateSystem option:selected").val();
    	  if(operateSystem == null || operateSystem ==""){
    		  $("#systemError").show();
    		  $("#operateSystem").focus();
    		  return false;
    	  }else{
    		  $("#systemError").hide();
    	  }
    	  var softwareIntroduce = $("#softwareIntroduce").val().replace(/[ \r\n]/g,"");
    	  if(softwareIntroduce == null || softwareIntroduce ==""){
    		  $("#introduceError").show();
    		  $("#softwareIntroduce").focus();
    		  return false;
    	  }else{
    		  $("#introduceError").hide();
    	  }
    	  var softwareImgUrl = $("#softwareImgUrl").val();
    	  if(softwareImgUrl == null || softwareImgUrl ==""){
    		  $("#logoError").show();
    		  $("#softwareImgUrl").focus();
    		  return false;
    	  }else{
    		  $("#logoError").hide();
    	  }
    	  var softwareSellProportion = $("#softwareSellProportion").val();
    	  if(softwareSellProportion == null || softwareSellProportion ==""){
    		  $("#sellproportionError").show();
    		  $("#softwareSellProportion").focus();
    		  return false;
    	  }else{
    		  $("#sellproportionError").hide();
    	  }
    	  
    	  if(softwareKind == "1"){
        		colCount = $("#table_head_jichu td").length;
        		rowCount = $("#softwareData_jichu tr").length;
        		if(rowCount<2){
        			info_alert("价格信息不能为空！");
        		}else{
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
            					if (j>1) {
            						if(!/^\+?[1-9][0-9]*$/.test(data)){
            								info_alert(title+"必须为正整数！");
            							  return false;
            					    }
            					}
            				}
                				
                		}
                	}
        		}
        		

        	}else{
        		colCount = $("#table_head_yewu td").length;
        		rowCount = $("#softwareData_yewu tr").length;
        		if(rowCount<2){
        			info_alert("价格信息不能为空！");
        		}else{
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
    	  if (!ifSave) {
    		  var ghoName = $("#ghoName").val();
        	  if(ghoName == null || ghoName ==""){
        		  $("#jingXiangError").show();
        		  $("#ghoName").focus();
        		  return false;
        	  }else{
        		  $("#jingXiangError").hide();
        	  }
    	  }
    	  var chackBox = $("input[type='checkbox']").is(':checked');
    	  if(chackBox == true){
    		  
    		  $("#chackError").hide();
    		  return true;
    		  
    	  }else{
    		  $("#chackError").show();
    		  return false;
    	  }
    	  return true;
    	  
    }
    //查询软件名称是否存在
    function checkSoftwareName(){
    	var softwareName = $("#softwareName").val();
    	if(softwareName == null || softwareName ==""){
    		$("#nameError").show();
    		$("#nameError1").hide();
    		 //$("#softwareName").css({ "color": "#ff0011", "border-color": "red" });
    		 return false;
    	}else{
    		$("#nameError").hide();
    		$.ajax({
  			  async : false,
  		      type: "POST",
  		      showBusi : false,
  		      modal : true,
  		      contentType:'application/x-www-form-urlencoded; charset=UTF-8',
  		      url: "${_base}/software/chackSoftwareName",
  		      dataType: 'text',
  		      data:{
  		    	  softwareName : softwareName
  		      }, 
  		      success: function(data) {
  		    	 var json=$.parseJSON(data);
  		    	if(json&&json.RES_RESULT=="SUCCESS"){
  		    		//$("#softwareName").css({ "color": "green", "border-color": "green" });
  		    		$("#nameError1").hide();
  					}else{
  						$("#nameError1").show();
  						//$("#softwareName").css({ "color": "#ff0011", "border-color": "red" });
  						return false;
  					}
  		    	  
  		       }
  		  });
    	}
    	
    }
    
    function LimitTextArea(field){ 
        maxlimit=2000; 
        if (field.value.length > maxlimit) 
         field.value = field.value.substring(0, maxlimit); 
           
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