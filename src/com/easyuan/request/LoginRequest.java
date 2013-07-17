package com.easyuan.request;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import net.tsz.afinal.http.AjaxCallBack;

import android.content.Context;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.easyuan.entityclass.Token;
import com.easyuan.toolkit.Constant;
import com.easyuan.toolkit.HttpMannager;
import com.google.gson.Gson;

/**
 * @Title: LoginRequest
 * @Description:
 * @Company: easyuan
 * @author dai
 * @date 2013-5-4
 */
public class LoginRequest extends GetRequest {

	private String mNickName;
	private String mPwd;
	/**
	 * @Title: Login
	 * @Description:构造函数
	 * @param String
	 *            url http请求url
	 * @param String
	 *            nickname 用户名
	 * @param String
	 *            password 密码
	 * @return void
	 * @date 2013年4月28日
	 */
	public LoginRequest(Context context, String url, String nickname, String password) {
		super(context, url);
		mNickName = nickname;
		mPwd = password;
	}

	/**
	 * @Title: start
	 * @Description:开始向服务器验证登录信息
	 * @param void
	 * @return void
	 * @date 2013年4月28日
	 */
	@Override
	public void start() {
		if (mUrl == null || mUrl.equals(""))
			return;
		if (mCallBackHandler == null) {
			mCallBackHandler = new AjaxCallBack<String>() {
				@Override
				public void onSuccess(String t) {
					onSuccess(t);
				}

				@Override
				public void onFailure(Throwable t, String strMsg) {
					onFailure(t, strMsg);
				}

			};
		}
		String s = mNickName + ":"+ mPwd;
		Header[] headers = new Header[1];		
		headers[0] = new BasicHeader("Authorization", "Basic " + Base64.encodeToString(s.getBytes(), Base64.NO_WRAP));	
		HttpMannager.aSyncGetRequest(mUrl, headers, null, mCallBackHandler);
	}

	/**
	 * @Title: onSuccess
	 * @Description:用户认证成功以后解析数据并存入数据库
	 * @param String
	 *            t Json格式数据
	 * @return void
	 * @date 2013年5月2日
	 */
	@Override
	public void onSuccess(String t) {
		Gson gson = new Gson();
		Token token = gson.fromJson(t, Token.class);
		savePreferences(Constant.LOGIN_STATE, Constant.LOGGED);
		savePreferences(Constant.USER_ID, token.getUserId());
		savePreferences(Constant.USER_TOKEN, token.getToken());
		Log.d("logintoken", token.getToken());
		Log.d("logintokenshare", mPrefs.getString(Constant.USER_TOKEN, "asdf"));
		Toast.makeText(context, "登录成功", Toast.LENGTH_LONG).show();
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
		if (mPrefs == null)
			mPrefs = context.getSharedPreferences(Constant.TAG,
					Context.MODE_PRIVATE);
		savePreferences(Constant.LOGIN_STATE, Constant.UNLOGGED);
	}
	
}
