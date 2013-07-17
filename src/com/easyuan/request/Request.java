package com.easyuan.request;

import net.tsz.afinal.http.AjaxCallBack;

public interface Request {
	public void start();
	
	public void onSuccess(String t);
	
	public void onFailure(Throwable t, String strMsg);
	
	public void setCallBackHandler(AjaxCallBack<String> callBackHandler);
	
	public boolean savePreferences(String key, int value);
	
	public boolean savePreferences(String key, String value);
	
	public boolean savePreferences(String key, boolean value);
}
