/**   
 * Filename:    RegisterRequest.java   
 * Copyright:   Copyright (c)2013  
 * Company:     easyuan 
 * @version:    1.0   
 * @since:      JDK 1.7.0 
 * Create at:   2013-5-11 下午7:29:13   
 * Description:  
 *   
 * Modification History:   
 * Date    Author      Version     Description   
 * ----------------------------------------------------------------- 
 * 2013-5-11 null        1.0         1.0 Version   
 */  
package com.easyuan.request;    

import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;

import com.easyuan.entityclass.User;
import com.google.gson.Gson;

import android.content.Context;
import android.util.Log;

/**
 * Filename:    RegisterRequest.java
 * Copyright:   Copyright (c)2013
 * Company:     easyuan
 * @version:    1.0
 * @since:      JDK 1.7.0
 * Create at:   2013-5-11 下午7:29:13
 * Description:
 *
 * Modification History:
 * Date    Author      Version     Description   
 * ----------------------------------------------------------------- 
 * 2013-5-11 null        1.0         1.0 Version 
 */
public class RegisterRequest extends PostRequest {
	
	private String mEmail;
	private String mPwd;

	public RegisterRequest(Context context, String url, String email, String pwd) {
		super(context, url);
		mEmail = email;
		mPwd = pwd;
	}
	
	@Override
	public void start() {
		try {
			HttpEntity entity;
			User user = new User();
			user.setEmail(mEmail);
			user.setPassword(mPwd);
			Log.v("req json", new Gson().toJson(user));
			entity = new StringEntity(new Gson().toJson(user));
			if(mCallBackHandler == null) {
				onFinish(syncPost(null, entity));
			} else {
				aSyncPost(null, entity);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public void onFinish(String t) {
		Log.v("res", t);
	}
}
