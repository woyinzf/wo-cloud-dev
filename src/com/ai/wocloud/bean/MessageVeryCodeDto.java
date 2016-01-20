/**
 * Project Name:wo-cloud-dev
 * File Name:MessageVeryCodeDto.java
 * Package Name:com.ai.wocloud.loginInfo.bean
 * Date:2014-11-0211:35:08
 * Copyright (c) 2014
 *
*/

package com.ai.wocloud.bean;

import java.io.Serializable;

/**
 * ClassName:MessageVeryCodeDto <br/>
 * Function: 短信验证dto FUNCTION. <br/>
 * Date:     2014-11-02 11:35:08 <br/>
 * @author   zhangyichi
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class MessageVeryCodeDto  implements Serializable {
    /**
     * @since JDK 1.6
     */
    private static final long serialVersionUID = -2035735843327519532L;
    private String veryCode;
    private String activeTime;
    public String getVeryCode() {
        return veryCode;
    }
    public void setVeryCode(String veryCode) {
        this.veryCode = veryCode;
    }
    public String getActiveTime() {
        return activeTime;
    }
    public void setActiveTime(String activeTime) {
        this.activeTime = activeTime;
    }
}
