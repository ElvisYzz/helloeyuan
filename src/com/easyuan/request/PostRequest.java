/**   
 * Filename:    PostRequest.java   
 * Copyright:   Copyright (c)2013  
 * Company:     easyuan 
 * @version:    1.0   
 * @since:      JDK 1.7.0 
 * Create at:   2013-5-4 下午4:22:44   
 * Description:  
 *   
 * Modification History:   
 * Date    Author      Version     Description   
 * ----------------------------------------------------------------- 
 * 2013-5-4 null        1.0         1.0 Version   
 */  
package com.easyuan.request;    

import org.apache.http.Header;
import org.apache.http.HttpEntity;

import com.easyuan.toolkit.HttpMannager;

import android.content.Context;

/**
 * Filename:    PostRequest.java
 * Copyright:   Copyright (c)2013
 * Company:     easyuan
 * @version:    1.0
 * @since:      JDK 1.7.0
 * Create at:   2013-5-4 下午4:22:44
 * Description:
 *
 * Modification History:
 * Date    Author      Version     Description   
 * ----------------------------------------------------------------- 
 * 2013-5-4 null        1.0         1.0 Version 
 */
public class PostRequest extends BasicRequest {

	protected int mStatus;
	
	public PostRequest(Context context, String url) {
		super(context, url);
		mStatus = -1;
	}
	
	@Override
	public void onSuccess(String t) {
		// TODO Auto-generated method stub
		mStatus = Integer.valueOf(t);
	}
	
	protected void aSyncPost(Header[] headers, HttpEntity entity) {
		HttpMannager.aSyncPostRequest(mUrl, headers, entity, mCallBackHandler);
	}
	
	protected String syncPost(Header[] headers, HttpEntity entity) {
		return (String)HttpMannager.syncPostRequest(mUrl, headers, entity);
	}
	
	public int getmStatus() {
		return mStatus;
	}
	
}
