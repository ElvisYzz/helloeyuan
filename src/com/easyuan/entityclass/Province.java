/**   
 * Filename:    Provices.java   
 * Copyright:   Copyright (c)2013  
 * Company:     easyuan 
 * @version:    1.0   
 * @since:      JDK 1.7.0 
 * Create at:   2013-6-4 ����12:41:37   
 * Description:  
 *   
 * Modification History:   
 * Date    Author      Version     Description   
 * ----------------------------------------------------------------- 
 * 2013-6-4 null        1.0         1.0 Version   
 */
package com.easyuan.entityclass;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

/**
 * Filename: Provices.java Copyright: Copyright (c)2013 Company: easyuan
 * 
 * @version: 1.0
 * @since: JDK 1.7.0 Create at: 2013-6-4 ����12:41:37 Description:
 * 
 *         Modification History: Date Author Version Description
 *         -----------------------------------------------------------------
 *         2013-6-4 null 1.0 1.0 Version
 */
@Table(name="ewish_province")
public class Province {
	@Id(column="id")
	private int id;
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
