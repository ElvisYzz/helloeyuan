package com.easyuan.toolkit;

import java.io.File;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.apache.http.Header;
import org.apache.http.HttpEntity;

import android.util.Log;


/**
 * @Title: HttpMannager
 * @Description: FinalHttp单例模式
 * @Company: easyuan
 * @author dai
 * @date 2013-5-4
 */
/**
 * @Title: HttpMannager
 * @Description:
 * @Company: easyuan
 * @author dai
 * @date 2013-5-4
 */
public class HttpMannager {

	private static FinalHttp mFinalHttp = new FinalHttp();

	/**
	 * @Title: configUserAgent
	 * @Description: 设置User-Agent字段
	 * @param userAgent 客户端发出的请求应为平台名/客户端版本,目前平台名有iphone和android
	 * @date 2013-5-4
	 */
	public static void configUserAgent(String userAgent){
		mFinalHttp.configUserAgent(userAgent);
	}
	
	/**
	 * @Title: configDeviceID
	 * @Description: 
	 * @param deviceID
	 * @date 2013-5-15
	 */
	public static void configDeviceID(String deviceID){
		mFinalHttp.addHeader("Eyuan-Device-ID", deviceID);
	}
	
	
	
	public static void configCharset(String charSet) {
		mFinalHttp.configCharset(charSet);
	}

	/**
	 * @Title: configRequestExecutionRetryCount
	 * @Description: 
	 * @param count
	 * @date 2013-5-15
	 */
	public static void configRequestExecutionRetryCount(int count) {
		mFinalHttp.configRequestExecutionRetryCount(count);
	}

	/**
	 * @Title: syncGetRequest
	 * @Description: 
	 * @param url
	 * @param headers
	 * @param params
	 * @return
	 * @date 2013-5-4
	 */
	public static String syncGetRequest(String url, Header[] headers) {
		String res = (String)mFinalHttp.getSync(url, headers, null);
		return res;
	}
	
	/**
	 * @Title: aSyncGetRequest
	 * @Description: 
	 * @param url
	 * @param headers
	 * @param params
	 * @param callBack
	 * @date 2013-5-4
	 */
	public static void aSyncGetRequest(String url, Header[] headers, AjaxParams params,
			AjaxCallBack<String> callBack) {
		mFinalHttp.get(url, headers, params, callBack);
	}

	/**
	 * @Title: syncPostRequest
	 * @Description:同步Post请求
	 * @param url 请求地址
	 * @param entity post数据
	 * @return String json格式的返回值
	 * @date 2013年4月11日 20:49:11
	 */
	public static String syncPostRequest(String url, Header[] headers, HttpEntity entity) {	   
		String res = null;
		if(headers != null)
			res = (String) mFinalHttp.postSync(url, headers, entity, Constant.contentType); 
		else
			res = (String) mFinalHttp.postSync(url, entity, Constant.contentType);
		return res;
	}
	
	/**
	 * @Title: aSyncPostRequest
	 * @Description:异步Post请求
	 * @param url 请求地址
	 * @param entity post数据
	 * @param callBackHandler 回调接口
	 * @return void
	 * @date 2013年4月11日 20:49:05
	 */
	public static void aSyncPostRequest(String url, Header[] headers, HttpEntity entity, AjaxCallBack<String> callBack) {
	    if(headers != null)
	    	mFinalHttp.post(url, headers, entity, Constant.contentType, callBack);  
	    else
	    	mFinalHttp.post(url, entity, Constant.contentType, callBack);
	}

	/**
	 * @Title: syncPutRequest
	 * @Description: 
	 * @param url
	 * @param headers
	 * @param entity
	 * @param contentType
	 * @return String
	 * @date 2013-5-4
	 */
	public static String syncPutRequest(String url, Header[] headers, HttpEntity entity) {
		return (String) mFinalHttp.putSync(url, headers, entity, Constant.contentType);
	}

	/**
	 * @Title: aSyncPutRequest
	 * @Description: 
	 * @param url
	 * @param headers
	 * @param entity
	 * @return
	 * @date 2013-5-4
	 */
	public static void aSyncPutRequest(String url, Header[] headers, HttpEntity entity, AjaxCallBack<String> callBack) {
		Log.d("async put url", url);
		mFinalHttp.put(url, headers, entity, Constant.contentType, callBack);
	}
	
	/**
	 * @Title: downloadFile
	 * @Description:文件下载
	 * @param url 请求地址
	 * @param filePath 文件存储路径
	 * @param callBackHandler 回调接口
	 * @return void
	 * @date 2013年4月11日 20:49:11
	 */
	public static void downloadFile(String url, String filePath, AjaxCallBack<File> callBackHandler) {
		mFinalHttp.download(url, filePath, callBackHandler);  
	}
	
	public static void addHeader(String header, String value){
		mFinalHttp.addHeader(header, value);
	}
}
