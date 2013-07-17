package com.easyuan.ui;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;

import com.easyuan.R;
import com.easyuan.request.LoginRequest;
import com.easyuan.request.RegisterRequest;
import com.easyuan.toolkit.UrlConstructor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends FinalActivity {
	@ViewInject(id=R.id.complete,click="onRegister") Button mBtn_complete;
	@ViewInject(id=R.id.nickname) EditText mEdit_nickName;
	@ViewInject(id=R.id.pwd) EditText mEdit_pwd;
	@ViewInject(id=R.id.pwdaffirm) EditText mEdit_pwdAffirm;
	@ViewInject(id=R.id.alert) TextView mText_alert;
	private RegisterRequest registerReq;
	private LoginRequest loginReq;
	private String mEmail;
	private String mPwd;
	private String mPwdAffirm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_left);
		registerReq = null;
		Button btnBack = (Button) findViewById(R.id.back);
		btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	
	public void onRegister(View v) {
		getText();
		boolean emailFlag = isEmailCorrected();
		boolean pwdFlag = isPwdCorrected();
		if(emailFlag && pwdFlag){
			registerReq = new RegisterRequest(this, UrlConstructor.RegisterRequestUrl(), mEmail, mPwd);
			registerReq.setCallBackHandler(new AjaxCallBack<String>(){

				@Override
				public void onFailure(Throwable t, String strMsg) {
					Log.v("res", strMsg);
					if(strMsg.endsWith("400"))
						mText_alert.setText("注册失败，该邮箱已被注册");
					else
						mText_alert.setText("注册失败");
				}

				@Override
				public void onSuccess(String t) {
					Log.v("res", t);
					loginReq.start();
				}
				
			});
			registerReq.start();
			loginReq = new LoginRequest(this, UrlConstructor.LoginRequestUrl(), mEmail, mPwdAffirm);
			loginReq.setCallBackHandler(new AjaxCallBack<String>(){

				@Override
				public void onFailure(Throwable t, String strMsg) {
					loginReq.onFailure(t, strMsg);
					Log.d("http failure", strMsg);
					Toast.makeText(RegisterActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
					finish();
				}

				@Override
				public void onSuccess(String t) {
					Log.v("login", t);
					loginReq.onSuccess(t);
					Intent i = new Intent(RegisterActivity.this, RegisterInfoActivity.class);
					i.putExtra("toke", t);
					startActivity(i);
					finish();
				}	
			});
		} else if(!emailFlag)
			mText_alert.setText("请输入正确格式的邮箱地址");
		else if(!pwdFlag)
			mText_alert.setText("两次输入密码不一致");
	}
	
	private void getText() {
		mEmail = mEdit_nickName.getText().toString();
		mPwd = mEdit_pwd.getText().toString();
		mPwdAffirm = mEdit_pwdAffirm.getText().toString();
	}
	
	private boolean isEmailCorrected() {
		boolean f = true;
		
		return f;
	}
	
	private boolean isPwdCorrected() {
		boolean f = true;
		f = (mPwd != null && !mPwd.equals("") && mPwdAffirm != null && !mPwdAffirm.equals("") && mPwd.equals(mPwdAffirm)) ? true : false;
		return f;
	}
}
