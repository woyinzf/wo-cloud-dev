package com.ai.wocloud.system.constants;

public class Constants {
	/**
	 * 登录返回编码信息
	 */
	public static class LoginReturnMessage {
		// 验证成功
		public static final String SUCCESS = "0";
		// 验证码为空
		public static final String VERIFY_CODE_IS_BLANK = "1";
		// 验证码错误
		public static final String VERIFY_CODE_IS_ERROR = "2";
		// 用户名或密码为空
		public static final String USERCODE_OR_PASSWD_IS_BLANk = "3";
		// 用户名或密码错误
		public static final String USERCODE_OR＿PASSWD_IS_ERROR = "4";
		// 用户名被冻结
		public static final String USERCODE_OR＿PASSWD_IS_FREE = "5";
		
	}
	
	/**
	 * 
	 * @author zhangbc
	 * 软件状态
	 *
	 */
	public static class SoftStatus{
		
		public static final String NEW_INCREASE="0";//保存未提交
		
		public static final String TO_VERIFY="1";//待审核
		
		public static final String TO_CONFIGURE="2";//待配置
		
		public static final String UNPASS_VERIFY="3";//审核未通过
		
		public static final String TO_RELEASE="4";//待发布
		
		public static final String RELEASED="5";//已发布
		
		public static final String ON_SHELF="6";//已上架
		
		public static final String RELEASE_NOT_PASS="7";//发布审核未通过
		
		public static final String WAIT_DOWN_SHELF="8";//待下架
	}
	
	public class APPLICATION_TYPE {
        /**
         * 签约申请 1
         * 下架申请 2
         * 升级申请 3
         * 变更申请 4
         */
        public static final String QYSQ = "1";
        public static final String XJSQ = "2";
        public static final String SJSQ = "3";
        public static final String BGSQ = "4";
    }
	
	
		/**
	     * 申请单审核中 0
	     * 审核成功 1
	     * 审核失败2
	     */
	public class APPLYODD_STATUS{
		public static final String INAPPLY="0";
		public static final String SUCCESSAPPLY="1";
		public static final String FAILAPPLY="2";
	}

}
