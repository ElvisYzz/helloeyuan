/**   
 * Filename:    LeaveMessageRequest.java   
 * Copyright:   Copyright (c)2013  
 * Company:     easyuan 
 * @version:    1.0   
 * @since:      JDK 1.7.0 
 * Create at:   2013-5-4 下午8:52:47   
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
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;

import com.easyuan.entityclass.Message;
import com.easyuan.entityclass.Token;
import com.easyuan.toolkit.Constant;
import com.easyuan.toolkit.DataBaseMannager;
import com.google.gson.Gson;

import android.content.Context;
import android.util.Base64;

/**
 * Filename:    LeaveMessageRequest.java
 * Copyright:   Copyright (c)2013
 * Company:     easyuan
 * @version:    1.0
 * @since:      JDK 1.7.0
 * Create at:   2013-5-4 下午8:52:47
 * Description:
 *
 * Modification History:
 * Date    Author      Version     Description   
 * ----------------------------------------------------------------- 
 * 2013-5-4 null        1.0         1.0 Version 
 */
public class LeaveMessageRequest extends PostRequest {

	protected Message mMessage;
	protected DataBaseMannager mDBMannager;
	
	public LeaveMessageRequest(Context context, String url) {
		super(context, url);
		mMessage = new Message();
//		mDBMannager = new DataBaseMannager(context, "easyuan_message");
	}
	
	public void setMsg(String content) {
		mMessage.setContent(content);
	}
	
	protected Header[] getHeaders() {
		String token = mPrefs.getString(Constant.USER_TOKEN, Constant.UNKNOWN);
		int userId = mPrefs.getInt(Constant.USER_ID, 0);
		Token t = new Token();
		t.setUserId(userId);
		t.setToken(token);
		String s = new Gson().toJson(t);
		Header[] headers = new Header[1];
		headers[0] = new BasicHeader("Authorization", "Token "
				+ Base64.encodeToString(s.getBytes(), Base64.NO_WRAP));
		return headers;
	}
	
	@Override
	public void start() {
		try {
			HttpEntity entity;
			Message msg = new Message();
			msg.setContent(mMessage.getContent());
			entity = new StringEntity(new Gson().toJson(msg), "utf-8");

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
		Message msg = new Gson().fromJson(t, Message.class);
		mMessage.setId(msg.getId());
		mMessage.setCreateTime(msg.getCreateTime());
		mDBMannager.save(mMessage);
	}
}
