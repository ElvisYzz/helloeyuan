package com.easyuan.request;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import android.content.Context;
import android.util.Base64;

import com.easyuan.entityclass.Token;
import com.easyuan.entityclass.User;
import com.easyuan.toolkit.Constant;
import com.google.gson.Gson;

public class UserInfoRequest extends GetRequest {

	protected String id;
	private User mUser;
	
	public UserInfoRequest(Context context, String url, String id) {
		super(context, id==null?url:url+id);
		this.id = id;
		mUser = null;
	}
	
	@Override
	public void start() {
		if (mUrl == null)
			return;
		if (mCallBackHandler == null) {
			onSuccess(syncGet(getHeaders()));
		}
		aSyncGet(getHeaders());
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
	public void onSuccess(String t) {
		// TODO Auto-generated method stub
		if(t == null) return;
		mUser = new Gson().fromJson(t, User.class);
	}

	@Override
	public void onFailure(Throwable t, String strMsg) {
		// TODO Auto-generated method stub
		
	}
	
	public User getUserInfo() {
		return mUser;
	}
}
