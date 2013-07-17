package com.easyuan.request;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import com.easyuan.entityclass.Wish;
import com.easyuan.toolkit.Constant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Context;
import android.widget.Toast;

public class WishListRequest extends GetRequest {

	// 请求头
	private int expectWishCount;
	private String lastestUpdateTime;
	private String oldestUpdateTime;

	protected List<Wish> mWishList;

	/**
	 * @Title: WishListRequest
	 * @Description:构造函数
	 * @param Context
	 *            context上下文
	 * @param int count 请求愿望个数
	 * @param String
	 *            url http请求url
	 * @return void
	 * @date 2013年5月1日
	 */
	public WishListRequest(Context context, int count, String url) {
		super(context, url);
		lastestUpdateTime = getCurrentTime();
		oldestUpdateTime = getCurrentTime();
		expectWishCount = count;
		mWishList = new ArrayList<Wish>();
	}

	/**
	 * @Title: start
	 * @Description:开始向服务器请求愿望
	 * @param void
	 * @return void
	 * @date 2013年4月28日
	 */
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

	protected Header[] getHeaders() {
		Header[] headers = new Header[3];
		headers[0] = new BasicHeader(Constant.latestTime, lastestUpdateTime);
		headers[1] = new BasicHeader(Constant.oldestTime, oldestUpdateTime);
		headers[2] = new BasicHeader(Constant.wishCount, expectWishCount + "");
		return headers;
	}

	/**
	 * @Title: onSuccess
	 * @Description:用户请求成功以后解析数据并存入数据库
	 * @param String
	 *            t Json格式数据
	 * @return void
	 * @date 2013年4月28日
	 */
	@Override
	public void onSuccess(String t) {
		// TODO Auto-generated method stub
		if(t == null) return;
		Gson gson = new Gson();
		mWishList = gson.fromJson(t, new TypeToken<List<Wish>>() {
		}.getType());
	}

	/**
	 * @Title: onFailure
	 * @Description:用户认证失败后默认处理
	 * @param Throwable
	 *            t 抛出出的异常
	 * @param String
	 *            strMsg 异常信息
	 * @return void
	 * @date 2013年4月28日
	 */
	@Override
	public void onFailure(Throwable t, String strMsg) {
		// TODO Auto-generated method stub
		Toast.makeText(context, "愿望列表更新失败", Toast.LENGTH_SHORT).show();
	}
	
	public void setUpdateTime(String latestTime, String oldestTime) {
		lastestUpdateTime = latestTime;
		oldestUpdateTime = oldestTime;
	}

	public String getCurrentTime() {
		Date d = new Date(System.currentTimeMillis());
		return d.toGMTString();
	}

	public List<Wish> getmWishList() {
		return mWishList;
	}
}
