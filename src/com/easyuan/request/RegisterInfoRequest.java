/**   
 * Filename:    RegisterInfoRequest.java   
 * Copyright:   Copyright (c)2013  
 * Company:     easyuan 
 * @version:    1.0   
 * @since:      JDK 1.7.0 
 * Create at:   2013-5-28 下午4:29:33   
 * Description:  
 *   
 * Modification History:   
 * Date    Author      Version     Description   
 * ----------------------------------------------------------------- 
 * 2013-5-28 null        1.0         1.0 Version   
 */  
package com.easyuan.request;    

import java.io.UnsupportedEncodingException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;

import com.easyuan.entityclass.Token;
import com.easyuan.toolkit.Constant;
import com.google.gson.Gson;

import android.content.Context;
import android.util.Base64;

/**
 * Filename:    RegisterInfoRequest.java
 * Copyright:   Copyright (c)2013
 * Company:     easyuan
 * @version:    1.0
 * @since:      JDK 1.7.0
 * Create at:   2013-5-28 下午4:29:33
 * Description:
 *
 * Modification History:
 * Date    Author      Version     Description   
 * ----------------------------------------------------------------- 
 * 2013-5-28 null        1.0         1.0 Version 
 */
public class RegisterInfoRequest extends PutRequest {

	private String mToken;
	private String mUserInfo;
	
	public RegisterInfoRequest(Context context, String url, String userInfo, String token) {
		super(context, url);
		mToken = token;
		mUserInfo = userInfo;
	}

	protected Header[] getHeaders() {
		if(mToken == null) {
			String token = mPrefs.getString(Constant.USER_TOKEN, Constant.UNKNOWN);
			int userId = mPrefs.getInt(Constant.USER_ID, 0);
			Token t = new Token();
			t.setUserId(userId);
			t.setToken(token);
			mToken = new Gson().toJson(t);
		}
		Header[] headers = new Header[1];
		headers[0] = new BasicHeader("Authorization", "Token "
				+ Base64.encodeToString(mToken.getBytes(), Base64.NO_WRAP));
		return headers;
	}
	
	@Override
	public void start() {
		// TODO Auto-generated method stub
		if (mUrl == null)
			return;
		HttpEntity entity = null;
		try {
			entity = new StringEntity(mUserInfo, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (mCallBackHandler != null) {
			aSyncPut(getHeaders(), entity);
		} else {
			String res = syncPut(getHeaders(), entity);
			onSuccess(res);
		}
	}
}
