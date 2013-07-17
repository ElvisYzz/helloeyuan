package com.easyuan.ui;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.easyuan.R;
import com.easyuan.entityclass.User;
import com.easyuan.request.UserInfoRequest;
import com.easyuan.toolkit.Constant;
import com.easyuan.toolkit.UrlConstructor;

public class UserInfoActivity extends FinalActivity{
	@ViewInject(id=R.id.mywish,click="onMywish") Button mbtn_mywish;
	@ViewInject(id=R.id.otherwish,click="onOtherwish") Button mbtn_otherwish;
	@ViewInject(id=R.id.avatar) ImageView mAvatar;
	@ViewInject(id=R.id.nickname) TextView mNickName;
	@ViewInject(id=R.id.sex_image) ImageView mSexImage;
	@ViewInject(id=R.id.sex) TextView mSexText;
	@ViewInject(id=R.id.integral) TextView mIntegral;
	@ViewInject(id=R.id.school) TextView mSchool;
	private User mUserInfo;
	private UserInfoRequest mUserReq;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		
		mUserReq = new UserInfoRequest(this, UrlConstructor.UserInfoRequestUrl()+"me", null);
		mUserReq.setCallBackHandler(new AjaxCallBack<String>(){

			@Override
			public void onFailure(Throwable t, String strMsg) {
				Log.v("userinfo fail", strMsg);
			}

			@Override
			public void onSuccess(String t) {
				mUserReq.onSuccess(t);
				Log.v("userinfo success", t);
				mUserInfo = mUserReq.getUserInfo();
				if(mUserInfo == null || mUserInfo.getNickName().equals("unnamed")) {
					Intent i = new Intent(UserInfoActivity.this, RegisterInfoActivity.class);
					startActivityForResult(i, Constant.GET_USERINFO);
					return;
				}
				updateUserInfo();
			}});
	}

	@Override
	protected void onStart() {
		super.onStart();
		mUserReq.start();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == Constant.GET_USERINFO && resultCode == Constant.RESULT_USERINFO) {
			mUserInfo = (User) data.getSerializableExtra("userInfo");
			if(mUserInfo != null) 
				updateUserInfo();
		}
	}

	private void updateUserInfo() {
		mNickName.setText(mUserInfo.getNickName());
		mSexImage.setBackgroundResource(mUserInfo.getGender() ? R.drawable.male : R.drawable.female);
		mSexText.setText(mUserInfo.getGender() ? "男" : "女");
		mIntegral.setText(mUserInfo.getWishChanceCount()+"");
		mSchool.setText(mUserInfo.getCampusId()+"");
	}
	
	public void onMywish(View v){
		Intent i = new Intent(UserInfoActivity.this, MyWishActivity.class);
		startActivity(i);
	}
	
	public void onOtherwish(View v){
		Intent i = new Intent(UserInfoActivity.this, OtherWishActivity.class);
		startActivity(i);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK)
			showExitDialog();
		return super.onKeyDown(keyCode, event);
	}

	public void showExitDialog() {
		final AlertDialog dlg = new AlertDialog.Builder(this).create();
		dlg.show();
		Window window = dlg.getWindow();
		window.setContentView(R.layout.exit_dialog);
		Button btn = (Button) window.findViewById(R.id.exit);
		btn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				dlg.cancel();
				UserInfoActivity.this.finish();
			}});
		btn = (Button) window.findViewById(R.id.cancle_exit);
		btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				dlg.cancel();
			}});
		CheckBox chb = (CheckBox) window.findViewById(R.id.stopservice);
		chb.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				MainTabActivity.mStopPush = isChecked;
			}});
		MainTabActivity.mStopPush = false;
	}
}
