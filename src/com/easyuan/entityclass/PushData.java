/**   
 * Filename:    PushData.java   
 * Copyright:   Copyright (c)2013  
 * Company:     easyuan 
 * @version:    1.0   
 * @since:      JDK 1.7.0 
 * Create at:   2013-5-30 上午12:36:20   
 * Description:  
 *   
 * Modification History:   
 * Date    Author      Version     Description   
 * ----------------------------------------------------------------- 
 * 2013-5-30 null        1.0         1.0 Version   
 */
package com.easyuan.entityclass;

import java.io.Serializable;

/**
 * Filename: PushData.java Copyright: Copyright (c)2013 Company: easyuan
 * 
 * @version: 1.0
 * @since: JDK 1.7.0 Create at: 2013-5-30 上午12:36:20 Description:
 * 
 *         Modification History: Date Author Version Description
 *         -----------------------------------------------------------------
 *         2013-5-30 null 1.0 1.0 Version
 */
public class PushData implements Serializable{
	/** serialVersionUID*/
	private static final long serialVersionUID = -2089533726200442345L;
	private String name;
	private String[] args;

	public String[] getArgs() {
		return args;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
