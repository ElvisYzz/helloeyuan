/**   
 * Filename:    University.java   
 * Copyright:   Copyright (c)2013  
 * Company:     easyuan 
 * @version:    1.0   
 * @since:      JDK 1.7.0 
 * Create at:   2013-6-4 ����12:52:42   
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
 * Filename: University.java Copyright: Copyright (c)2013 Company: easyuan
 * 
 * @version: 1.0
 * @since: JDK 1.7.0 Create at: 2013-6-4 ����12:52:42 Description:
 * 
 *         Modification History: Date Author Version Description
 *         -----------------------------------------------------------------
 *         2013-6-4 null 1.0 1.0 Version
 */
@Table(name="ewish_university")
public class University {
	@Id(column="_id")
	private int _id;
	private int id;
	private int province_id;
	private String name;

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProvice_id() {
		return province_id;
	}

	public void setProvice_id(int provice_id) {
		this.province_id = provice_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
