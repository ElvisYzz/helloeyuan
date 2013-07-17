/**   
 * Filename:    PutRequest.java   
 * Copyright:   Copyright (c)2013  
 * Company:     easyuan 
 * @version:    1.0   
 * @since:      JDK 1.7.0 
 * Create at:   2013-5-4 下午4:03:43   
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
 * Filename: PutRequest.java Copyright: Copyright (c)2013 Company: easyuan
 * 
 * @version: 1.0
 * @since: JDK 1.7.0 Create at: 2013-5-4 下午4:03:43 Description:
 * 
 *         Modification History: Date Author Version Description
 *         -----------------------------------------------------------------
 *         2013-5-4 null 1.0 1.0 Version
 */
public class PutRequest extends BasicRequest {

	protected int mStatus;

	public PutRequest(Context context, String url) {
		super(context, url);
	}

	@Override
	public void onSuccess(String t) {
		// TODO Auto-generated method stub
	}

	public String syncPut(Header[] headers, HttpEntity entity) {
		return HttpMannager.syncPutRequest(mUrl, headers, entity);
	}

	public void aSyncPut(Header[] headers, HttpEntity entity) {
		HttpMannager.aSyncPutRequest(mUrl, headers, entity, mCallBackHandler);
	}

	public int getmStatus() {
		return mStatus;
	}
}
