/**   
 * Filename:    GetMessageRequest.java   
 * Copyright:   Copyright (c)2013  
 * Company:     easyuan 
 * @version:    1.0   
 * @since:      JDK 1.7.0 
 * Create at:   2013-5-4 下午8:15:20   
 * Description:  
 *   
 * Modification History:   
 * Date    Author      Version     Description   
 * ----------------------------------------------------------------- 
 * 2013-5-4 null        1.0         1.0 Version   
 */  
package com.easyuan.request;    

import java.util.List;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import com.easyuan.entityclass.Message;
import com.easyuan.entityclass.Token;
import com.easyuan.entityclass.Wish;
import com.easyuan.toolkit.Constant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Context;
import android.util.Base64;

/**
 * Filename:    GetMessageRequest.java
 * Copyright:   Copyright (c)2013
 * Company:     easyuan
 * @version:    1.0
 * @since:      JDK 1.7.0
 * Create at:   2013-5-4 下午8:15:20
 * Description:
 *
 * Modification History:
 * Date    Author      Version     Description   
 * ----------------------------------------------------------------- 
 * 2013-5-4 null        1.0         1.0 Version 
 */
public class GetMessageRequest extends GetRequest {

	private String modifiedTime;
	protected List<Message> mMessageList;

	public GetMessageRequest(Context context, String url, String time) {
		super(context, url);
		modifiedTime = time;
	}
	
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
	
	@Override
	public void start() {
		// TODO Auto-generated method stub
		if (mUrl == null)
			return;
		if (mCallBackHandler == null) {
			onSuccess(syncGet(getHeaders()));
		}
		aSyncGet(getHeaders());
	}
	
	@Override
	public void onSuccess(String t) {
		Gson gson = new Gson();
		mMessageList = gson.fromJson(t, new TypeToken<List<Wish>>() {
		}.getType());
	}
	
	public List<Message> getmMessageList() {
		return mMessageList;
	}
}
