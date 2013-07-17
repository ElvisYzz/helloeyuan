/**   
 * Filename:    BasicRequest.java   
 * Copyright:   Copyright (c)2013  
 * Company:     easyuan 
 * @version:    1.0   
 * @since:      JDK 1.7.0 
 * Create at:   2013-5-4 下午4:44:56   
 * Description:  
 *   
 * Modification History:   
 * Date    Author      Version     Description   
 * ----------------------------------------------------------------- 
 * 2013-5-4 null        1.0         1.0 Version   
 */  
package com.easyuan.request;    

import com.easyuan.toolkit.Constant;

import net.tsz.afinal.http.AjaxCallBack;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Filename:    BasicRequest.java
 * Copyright:   Copyright (c)2013
 * Company:     easyuan
 * @version:    1.0
 * @since:      JDK 1.7.0
 * Create at:   2013-5-4 下午4:44:56
 * Description:
 *
 * Modification History:
 * Date    Author      Version     Description   
 * ----------------------------------------------------------------- 
 * 2013-5-4 null        1.0         1.0 Version 
 */
public class BasicRequest implements Request {

	protected String mUrl;
	public Context context;
	protected SharedPreferences mPrefs;
	protected AjaxCallBack<String> mCallBackHandler;
	
	public BasicRequest(Context context, String url) {
		this.context = context;
		mUrl = url;
		mPrefs = context.getSharedPreferences(Constant.TAG, Context.MODE_PRIVATE);
		mCallBackHandler = null;
	}
	
	public void changeUrl(String url) {
		mUrl = url;
	}
	
	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSuccess(String t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFailure(Throwable t, String strMsg) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void setCallBackHandler(AjaxCallBack<String> callBackHandler) {
		mCallBackHandler = callBackHandler;
	}

	@Override
	public boolean savePreferences(String key, int value) {
		return mPrefs.edit().putInt(key, value).commit();
	}

	@Override
	public boolean savePreferences(String key, String value) {
		return mPrefs.edit().putString(key, value).commit();
	}

	@Override
	public boolean savePreferences(String key, boolean value) {
		return mPrefs.edit().putBoolean(key, value).commit();
	}

}
