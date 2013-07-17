package com.easyuan.ui;

import com.easyuan.R;
import com.easyuan.entityclass.PushData;
import com.easyuan.push.PushService;
import com.easyuan.toolkit.Constant;
import com.easyuan.widget.SwitchButton;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TabHost;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class MainTabActivity extends TabActivity implements
		OnCheckedChangeListener, OnTouchListener, OnClickListener {

	private TabHost mTabHost;
	private Intent mWishIntent;
	private Intent mMessageIntent;
	private Intent mUserInfoIntent;
	private Intent mSettingIntent;
	private Button mTitleBtn_commit;
	private SwitchButton mTitleSwitchButton;
	private RadioButton mRadioBtn_ewish;
	private RadioButton mRadioBtn_message;
	private RadioButton mRadioBtn_me;
	private RadioButton mRadioBtn_set;
	// private PopMenu popMenu;
	public static boolean mStopPush = false;
	public static int mLoginState;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maintabs);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);

		String wishJson = getIntent().getStringExtra("wishList");
		this.mWishIntent = new Intent(this, WishActivity.class);
		this.mWishIntent.putExtra("wishList", wishJson);
		this.mMessageIntent = new Intent(this, MessageActivity.class);
		this.mUserInfoIntent = new Intent(this, UserInfoActivity.class);
		this.mSettingIntent = new Intent(this, SettingActivity.class);

		mTitleBtn_commit = (Button) findViewById(R.id.commit);
		mTitleBtn_commit.setOnClickListener(this);
		mTitleBtn_commit.setOnTouchListener(this);
		mTitleSwitchButton = (SwitchButton) findViewById(R.id.filter_switch);
		
		mRadioBtn_ewish = (RadioButton) findViewById(R.id.eyuan);
		mRadioBtn_ewish.setOnCheckedChangeListener(this);
		mRadioBtn_message = (RadioButton) findViewById(R.id.message);
		mRadioBtn_message.setOnCheckedChangeListener(this);
		mRadioBtn_me = (RadioButton) findViewById(R.id.me);
		mRadioBtn_me.setOnCheckedChangeListener(this);
		mRadioBtn_set = (RadioButton) findViewById(R.id.set);
		mRadioBtn_set.setOnCheckedChangeListener(this);
		setupIntent();
//		if(!PushService.isStarted) {
//			PushService.isStarted = true;
//			startService(new Intent(this, PushService.class));
//		}
//		getPushData();
	}

	private void getPushData() {
		PushData pushData = (PushData) getIntent().getSerializableExtra("push");	
		if(pushData != null) {
			mMessageIntent.putExtra("push", pushData);
			mTabHost.setCurrentTabByTag("message");
		}
	}

	@Override
	protected void onDestroy() {
//		if (mStopPush) {
//			PushService.keepAlive = false;
//			stopService(new Intent(this, PushService.class));
//		}
		super.onDestroy();

	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if(!isLogged()) {
			Log.d("MainTabActivity", "start login");
			Intent i = new Intent(MainTabActivity.this, LoginActivity.class);
			startActivity(i);
			return;
		}
		if (isChecked) {
			switch (buttonView.getId()) {
			case R.id.eyuan:
				this.mTabHost.setCurrentTabByTag("eyuan");
				mTitleSwitchButton.setVisibility(View.VISIBLE);
				
				mRadioBtn_ewish.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.icon_1_d), null, null);
				mRadioBtn_message.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.icon_2_n), null, null);
				mRadioBtn_me.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.icon_3_n), null, null);
				mRadioBtn_set.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.icon_4_n), null, null);
				break;
			case R.id.message:
				this.mTabHost.setCurrentTabByTag("message");
				mTitleSwitchButton.setVisibility(View.GONE);
				
				mRadioBtn_ewish.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.icon_1_n), null, null);
				mRadioBtn_message.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.icon_2_d), null, null);
				mRadioBtn_me.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.icon_3_n), null, null);
				mRadioBtn_set.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.icon_4_n), null, null);
				break;
			case R.id.me:
				this.mTabHost.setCurrentTabByTag("userinfo");
				mTitleSwitchButton.setVisibility(View.GONE);
				mRadioBtn_ewish.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.icon_1_n), null, null);
				mRadioBtn_message.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.icon_2_n), null, null);
				mRadioBtn_me.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.icon_3_d), null, null);
				mRadioBtn_set.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.icon_4_n), null, null);
				break;
			case R.id.set:
				this.mTabHost.setCurrentTabByTag("setting");
				mTitleSwitchButton.setVisibility(View.GONE);
				mRadioBtn_ewish.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.icon_1_n), null, null);
				mRadioBtn_message.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.icon_2_n), null, null);
				mRadioBtn_me.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.icon_3_n), null, null);
				mRadioBtn_set.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.icon_4_d), null, null);
				break;
			}
		}

	}

	private void setupIntent() {
		this.mTabHost = getTabHost();
		TabHost localTabHost = this.mTabHost;

		localTabHost.addTab(buildTabSpec("eyuan", R.string.app_name,
				R.drawable.icon_1_n, this.mWishIntent));

		localTabHost.addTab(buildTabSpec("message", R.string.message,
				R.drawable.icon_2_n, this.mMessageIntent));

		localTabHost.addTab(buildTabSpec("userinfo", R.string.me,
				R.drawable.icon_3_n, this.mUserInfoIntent));

		localTabHost.addTab(buildTabSpec("setting", R.string.set,
				R.drawable.icon_4_n, this.mSettingIntent));

	}

	private TabHost.TabSpec buildTabSpec(String tag, int resLabel, int resIcon,
			final Intent content) {
		return this.mTabHost
				.newTabSpec(tag)
				.setIndicator(getString(resLabel),
						getResources().getDrawable(resIcon))
				.setContent(content);
	}

	private boolean isLogged() {
		SharedPreferences sharePrefs = getSharedPreferences(Constant.TAG,
				Context.MODE_PRIVATE);
		mLoginState = sharePrefs.getInt(Constant.LOGIN_STATE, Constant.UNLOGGED);
		if (mLoginState == Constant.LOGGED)
			return true;
		else
			return false;
	}

	@Override
	public void onClick(View v) {

		// TODO Auto-generated method stub
		if (!isLogged()) {
			Intent i = new Intent(MainTabActivity.this, LoginActivity.class);
			startActivity(i);
			return;
		}
		Intent i = new Intent(MainTabActivity.this, SubmitActivity.class);
		startActivity(i);

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			v.setBackgroundResource(R.drawable.commit_btn_down);
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			v.setBackgroundResource(R.drawable.commit_btn_up);
		}
		return false;
	}
}
