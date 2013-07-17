/**   
 * Filename:    Push.java   
 * Copyright:   Copyright (c)2013  
 * Company:     easyuan 
 * @version:    1.0   
 * @since:      JDK 1.7.0 
 * Create at:   2013-5-30 上午12:10:49   
 * Description:  
 *   
 * Modification History:   
 * Date    Author      Version     Description   
 * ----------------------------------------------------------------- 
 * 2013-5-30 null        1.0         1.0 Version   
 */
package com.easyuan.entityclass;

/**
 * Filename: Push.java Copyright: Copyright (c)2013 Company: easyuan
 * 
 * @version: 1.0
 * @since: JDK 1.7.0 Create at: 2013-5-30 上午12:10:49 Description:
 * 
 *         Modification History: Date Author Version Description
 *         -----------------------------------------------------------------
 *         2013-5-30 null 1.0 1.0 Version
 */
public class PushToken {
	private int userId;
	private String deviceId;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

}
