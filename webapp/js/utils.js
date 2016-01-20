
function info_alert(info_msg,func){
	$("body").eq(0).append('<div class="x_tc_tanc" id="alert_info_div" style="display:none">'
		     +'<div align="center" style="padding:5px;" >'
		     +'<div style="clear:both;height:10px;"></div>'
		     +'<div id="info_innerHtml_div" ></div>'
		     +'<div ><button style="width:100px;height:30px;background:#46a3ff;border:1px solid #46a3ff;margin:10px;color:#fff;border-radius:4px;" id="info_button" onclick="javascript:$(\'#alert_info_div\').PopupDiv(\'close\');document.getElementById(\'bg\').style.display =\'none\';" >确定</button></div>'
		     +'</div>'
		     +'</div>'); 

	info_msg = "<div class='x_tc_tanc_B' style='margin-top:-20px;'><ul class='xx_tup_A'><li><img src='../images/duig.png'  /></li></ul><ul class='xx_zix_A'><li class='x_ninb_A'>"+info_msg+"</li></ul></div>";
	var scrolltop = $(document).scrollTop();
	var screenHeight = $(window).height();
	 $("#info_innerHtml_div").html(info_msg);
	 $("#alert_info_div").PopupDiv({
		 top:(parseInt(screenHeight)-200)/2 + parseInt(scrolltop) + 'px',
		 close_btn:false,
		 modal:false,
		 minWidth:300
     });
	 if(func){
		 $("#info_button").click(function(){
		      $("#alert_info_div").PopupDiv("close");
		      func();
			});
	 }

}

function confirm_alert(confirm_msg, func){
	var height = document.body.scrollHeight;
	$("#bg").css("height", parseInt(height));
	document.getElementById("bg").style.display ="block";
	$("body").eq(0).append('<div class="x_tc_tanc" id="confirm_info_div" style="display:none">'
		     +'<div align="center" style="padding:5px;" >'
		     +'<div style="clear:both;height:10px;"></div>'
		     +'<div id="confirm_innerHtml_div" ></div>'
		     +'<div ><button style="width:100px;height:30px;background:#46a3ff;border:1px solid #46a3ff;margin:10px;color:#fff;border-radius:4px;" id="confirm_button" onclick="javascript:$(\'#confirm_info_div\').PopupDiv(\'close\');" >确定</button><button style="width:100px;height:30px;background:#46a3ff;border:1px solid #46a3ff;margin:10px;color:#fff;border-radius:4px;" id="cancel_button" onclick="javascript:$(\'#confirm_info_div\').PopupDiv(\'remove\');document.getElementById(\'bg\').style.display =\'none\';$(\'#myWrap\').remove();return" >取消</button></div>'
		     +'</div>'
		     +'</div>'); 

	confirm_msg = "<div class='x_tc_tanc_B' style='margin-top:-20px;'><ul class='xx_tup_A'><li><img src='../images/duig.png'  /></li></ul><ul class='xx_zix_A'><li class='x_ninb_A'>"+confirm_msg+"</li></ul></div>";
	 $("#confirm_innerHtml_div").html(confirm_msg);
	 var scrolltop = $(document).scrollTop();
		var screenHeight = $(window).height();
	 $("#confirm_info_div").PopupDiv({
		 top:(parseInt(screenHeight)-200)/2 + parseInt(scrolltop) + 'px',
		 close_btn:false,
		 modal:false,
		 minWidth:300
     });
	 if(func){
		 $("#confirm_button").click(function(){
		      $("#confirm_info_div").PopupDiv("remove");
		    	  func();
			});
	 }
}
  
  
    