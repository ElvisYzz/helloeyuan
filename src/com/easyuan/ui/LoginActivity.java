package com.easyuan.ui;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;

import com.easyuan.R;
import com.easyuan.request.LoginRequest;
import com.easyuan.toolkit.UrlConstructor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends FinalActivity {
	@ViewInject(id = R.id.login, click = "onLogin")
	Button mbtn_login;
	@ViewInject(id = R.id.register, click = "onRegister")
	TextView mtext_register;
	@ViewInject(id = R.id.nickname)
	EditText medit_nickname;
	@ViewInject(id = R.id.password)
	EditText medit_password;
	@ViewInject(id = R.id.alert)
	TextView mtext_alert;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.title_left);
		Button mbtn_back = (Button) findViewById(R.id.back);
		mbtn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.d("LoginActivity", "started");
	}

	public void onLogin(View v) {
		String nickname = medit_nickname.getText().toString();
		String password = medit_password.getText().toString();
		final LoginRequest mylogin = new LoginRequest(this,
				UrlConstructor.LoginRequestUrl(), nickname, password);
		mylogin.setCallBackHandler(new AjaxCallBack<String>() {

			@Override
			public void onFailure(Throwable t, String strMsg) {
				mylogin.onFailure(t, strMsg);
				Log.d("http failure", strMsg);
				if (strMsg.endsWith("401"))
					mtext_alert.setText("用户名/密码错误");
				else
					mtext_alert.setText("登录失败");
			}

			@Override
			public void onSuccess(String t) {
				Log.v("login", t);
				mylogin.onSuccess(t);
				finish();
			}

		});
		mylogin.start();
	}

	public void onRegister(View v) {
		Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
		startActivity(i);
		finish();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK)
			finish();
		return super.onKeyDown(keyCode, event);
	}

}
