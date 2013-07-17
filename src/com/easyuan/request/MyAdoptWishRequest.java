/**   
 * Filename:    MyAdoptWishRequest.java   
 * Copyright:   Copyright (c)2013  
 * Company:     easyuan 
 * @version:    1.0   
 * @since:      JDK 1.7.0 
 * Create at:   2013-5-4 下午6:50:00   
 * Description:  
 *   
 * Modification History:   
 * Date    Author      Version     Description   
 * ----------------------------------------------------------------- 
 * 2013-5-4 null        1.0         1.0 Version   
 */  
package com.easyuan.request;    

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import com.easyuan.entityclass.Token;
import com.easyuan.toolkit.Constant;
import com.google.gson.Gson;

import android.content.Context;
import android.util.Base64;

/**
 * Filename:    MyAdoptWishRequest.java
 * Copyright:   Copyright (c)2013
 * Company:     easyuan
 * @version:    1.0
 * @since:      JDK 1.7.0
 * Create at:   2013-5-4 下午6:50:00
 * Description:
 *
 * Modification History:
 * Date    Author      Version     Description   
 * ----------------------------------------------------------------- 
 * 2013-5-4 null        1.0         1.0 Version 
 */
public class MyAdoptWishRequest extends WishListRequest {

	private String modifiedTime;
	
	public MyAdoptWishRequest(Context context, int count, String url, String time) {
		super(context, count, url);
		modifiedTime = time;
	}
	
	@Override
	protected Header[] getHeaders() {
		String token = mPrefs.getString(Constant.USER_TOKEN, Constant.UNKNOWN);
		int userId = mPrefs.getInt(Constant.USER_ID, 0);
		Token t = new Token();
		t.setUserId(userId);
		t.setToken(token);
		String s = new Gson().toJson(t);
		Header[] headers = new Header[2];
		headers[0] = new BasicHeader("Authorization", "Token "
				+ Base64.encodeToString(s.getBytes(), Base64.NO_WRAP));
		headers[1] = new BasicHeader("Eyuan-If-Modified-Since", modifiedTime);
		return headers;
	}
	
}
