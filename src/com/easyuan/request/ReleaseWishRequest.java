/**   
 * Filename:    ReleaseWishRequest.java   
 * Copyright:   Copyright (c)2013  
 * Company:     easyuan 
 * @version:    1.0   
 * @since:      JDK 1.7.0 
 * Create at:   2013-5-4 下午3:59:34   
 * Description:  
 *   
 * Modification History:   
 * Date    Author      Version     Description   
 * ----------------------------------------------------------------- 
 * 2013-5-4 null        1.0         1.0 Version   
 */  
package com.easyuan.request;    

import java.io.UnsupportedEncodingException;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;

import com.easyuan.entityclass.Token;
import com.easyuan.entityclass.Wish;
import com.easyuan.toolkit.Constant;
import com.google.gson.Gson;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

/**
 * Filename:    ReleaseWishRequest.java
 * Copyright:   Copyright (c)2013
 * Company:     easyuan
 * @version:    1.0
 * @since:      JDK 1.7.0
 * Create at:   2013-5-4 下午3:59:34
 * Description:
 *
 * Modification History:
 * Date     Author           Version           Description
 * ------------------------------------------------------------------
 * 2013-5-4 null             1.0               1.0 Version
 */
public class ReleaseWishRequest extends PostRequest {
	
	private String mContent;
	
	public ReleaseWishRequest(Context context, String url) {
		super(context, url);
		mContent = null;
	}
	
	public void setContent(String content) {
		mContent = content;
	}
	
	protected Header[] getHeaders() {
		String token = mPrefs.getString(Constant.USER_TOKEN,
				Constant.UNKNOWN);
		int userId = mPrefs.getInt(Constant.USER_ID, 0);
		Token t = new Token();
		t.setUserId(userId);
		t.setToken(token);
		String s = new Gson().toJson(t);
		Header[] headers = new Header[1];
		headers[0] = new BasicHeader("Authorization", "Token " +  Base64.encodeToString(s.getBytes(), Base64.NO_WRAP));
		return headers;
	}
	
	@Override
	public void start() {
		try {
			StringEntity entity;
			Wish wish = new Wish();
			wish.setContent(mContent);
			entity = new StringEntity(new Gson().toJson(wish), "utf-8");
			
			Log.d("release wish", new Gson().toJson(wish));
			if(mCallBackHandler == null) {
				onSuccess(syncPost(getHeaders(), entity));
			} else {
				aSyncPost(getHeaders(), entity);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onSuccess(String t) {
		// TODO Auto-generated method stub
		super.onSuccess(t);
	}
}
