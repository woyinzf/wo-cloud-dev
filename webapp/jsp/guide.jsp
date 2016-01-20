<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<%@ include file="/jsp/common/common.jsp"%>
<title>沃云软件商门户</title>
<script src="${_base}/js/caidna.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function(){
	guideController = new $.GuideController();
	$("#head_guide").addClass("shoy");
});
/*定义页面管理类*/
(function(){
	$.GuideController  = function(){ 
		this.settings = $.extend(true,{},$.GuideController.defaults); 
		this.init();
		
	};
	$.extend($.GuideController,{
		defaults : {
		},  
		prototype : {
			init : function(){
				var _this = this;
				_this.bindEvents();				
			},
			bindEvents : function(){
				var _this = this;
				$(".show_hide").bind("mouseenter",function(){
					$(this).children().show();
					
				});
				$(".show_hide").bind("mouseleave",function(){
					$(this).children().hide();
					
				});
			}
		}
	});
})(jQuery);	
</script>
</head>
<body>
<div class="wai_da">   
	<!--header -->
	<%@ include file="/jsp/common/header.jsp"%>    
    <div class="erji_nei">
   		<div class="caozu">
    		<div class="caozu_top"></div>
    		<div class="caozu_bane">
    			<div class="caozu_bane_A">请选择您要操作的功能，点击进入详情界面</div>
    			<div class="caozu_bane_B">       
    				<div class="caozu_bane_banh">
    					<ul>
    						<li class="qv">    							
								<A href="${_base }/software/spfindAllSoftware?orderCond=command_time&orderType=desc" class="show_hide">
							    <!--鼠标触发区域-->
							    <div class="xians" style="display:none;">
							    	<ul>
							    		<li class="cz_sj"><img src="<%=bp %>/images/cz_sj.png" /></li>
							    		<li class="wo_zi">沃云软件商门户为您提供软件从上架到下架的全流程操作，如果您需要在沃云商城上进行软件售卖，请点击“签约申请”并提供软件相关信息，我们的运营人员会尽快进行审批。
										</li>
							    		<li class="wo_an">签约申请
							    		</li>
							    		
							    	</ul>
							    </div><!---->
							    </A>
							</li>
						    <li class="lv">
						    	<A href="#" class="show_hide">
						    	<!--鼠标触发区域-->
						    	<div class="xians" style="display:none;">
						    		<ul>
						    			<li class="cz_sj"><img src="<%=bp %>/images/cz_sj.png" /></li>
						    			<li class="wo_zi">沃云软件商门户为您提供软件从上架到下架的全流程操作，如果您的SAAS库存量减少时，请点击“增加库存”增加您的SAAS库存。
										</li>
						    			<li class="wo_an">增加库存</li>
						    			
						    		</ul>
						    	</div><!---->
						    	</A>
						    </li>
    						<li class="jh">
    							<A href="${_base }/software/spfindAllSoftware?orderCond=command_time&orderType=desc" class="show_hide">
    							<div class="xians" style="display:none;">
						    		<ul>
						    			<li class="cz_sj"><img src="<%=bp %>/images/cz_sj.png" /></li>
						    			<li class="wo_zi">沃云软件商门户为您提供软件从上架到下架的全流程操作，如果您在沃云商城上已经进行售卖的软件需要升级，请点击“升级申请”并提供软件相关信息，我们的运营人员会尽快进行审批。
										</li>
						    			<li class="wo_an">升级申请</li>
						    			
						    		</ul>
						    	</div>
    							</A>
    						</li>
						    <li class="hh">
						    	<A href="${_base }/software/spfindAllSoftware?orderCond=command_time&orderType=desc" class="show_hide">
						    	<div class="xians" style="display:none;">
						    		<ul>
						    			<li class="cz_sj"><img src="<%=bp %>/images/cz_sj.png" /></li>
						    			<li class="wo_zi">沃云软件商门户为您提供软件从上架到下架的全流程操作，如果您在沃云商城上已经进行售卖的软件需要下架，请点击“下架申请”，我们的运营人员会尽快进行审批并下架。
										</li>
						    			<li class="wo_an">下架申请</li>
						    			
						    		</ul>
						    	</div>
						    	</A>
						    </li>
						    <li class="la">
						    	<A href="${_base }/accountInfoAction/toAccountTotal" class="show_hide">
						    	<div class="xians" style="display:none;">
						    		<ul>
						    			<li class="cz_sj"><img src="<%=bp %>/images/cz_sj.png" /></li>
						    			<li class="wo_zi">沃云软件商门户为您提供完整的账户资料记录，您可以详细查看您的软件售卖以及收入情况，并及时将收入进行提现。
										</li>
						    			<li class="wo_an">账户查询</li>
						    			
						    		</ul>
						    	</div>
						    	</A>
						    </li>
    					</ul>
    				</div>
    			</div>    
    		</div>
    	</div>
	</div>
</div>
<%@ include file="/jsp/common/footer.jsp"%>         <!--------------------footer-->
</body>
</html>
