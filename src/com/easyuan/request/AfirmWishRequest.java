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
import android.util.Log;

public class AfirmWishRequest extends PutRequest {

	public AfirmWishRequest(Context context, String url, int status) {
		super(context, url);
		mStatus = status;
	}

	@Override
	public void start() {
		if (mUrl == null)
			return;
		HttpEntity entity = null;
		try {
			entity = new StringEntity("{\"status\":" + mStatus + "}");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (mCallBackHandler != null) {
			aSyncPut(getHeaders(), entity);
		} else {
			String res = syncPut(getHeaders(), entity);
			onSuccess(res);
		}
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
	public void onSuccess(String t) {
		// TODO Auto-generated method stub
		Log.d("afirm wish", t);
	}
}
