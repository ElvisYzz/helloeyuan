/**   
 * Filename:    GetRequest.java   
 * Copyright:   Copyright (c)2013  
 * Company:     easyuan 
 * @version:    1.0   
 * @since:      JDK 1.7.0 
 * Create at:   2013-5-4 下午4:08:00   
 * Description:  
 *   
 * Modification History:   
 * Date    Author      Version     Description   
 * ----------------------------------------------------------------- 
 * 2013-5-4 dai        1.0         1.0 Version   
 */  
package com.easyuan.request;    

import org.apache.http.Header;

import android.content.Context;

import com.easyuan.toolkit.HttpMannager;

/**
 * Filename:    GetRequest.java
 * Copyright:   Copyright (c)2013
 * Company:     easyuan
 * @version:    1.0
 * @since:      JDK 1.7.0
 * Create at:   2013-5-4 下午4:08:00
 * Description:
 *
 * Modification History:
 * Date    Author      Version     Description   
 * ----------------------------------------------------------------- 
 * 2013-5-4 dai        1.0         1.0 Version 
 */
public class GetRequest extends BasicRequest {

	public GetRequest(Context context, String url) {
		super(context, url);
	}
	
	public String syncGet(Header[] headers) {
		return HttpMannager.syncGetRequest(mUrl, headers);
	}
	
	public void aSyncGet(Header[] headers) {
		HttpMannager.aSyncGetRequest(mUrl, headers, null, mCallBackHandler);
	}
}
