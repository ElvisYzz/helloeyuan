package com.easyuan.ui;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;

import com.easyuan.R;
import com.easyuan.entityclass.User;
import com.easyuan.entityclass.Wish;
import com.easyuan.request.AdoptWishRequest;
import com.easyuan.request.UserInfoRequest;
import com.easyuan.toolkit.Constant;
import com.easyuan.toolkit.UrlConstructor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class WishContentActivity extends FinalActivity {

	@ViewInject(id = R.id.achieve, click = "onAchieve")
	Button mbtn_achieve;
	@ViewInject(id = R.id.relative1)
	RelativeLayout relative;
	@ViewInject(id = R.id.avatar)
	ImageView mAvatar;
	@ViewInject(id = R.id.nickname)
	TextView mNickName;
	@ViewInject(id = R.id.sex)
	ImageView mSex;
	@ViewInject(id = R.id.location)
	TextView mLocation;
	@ViewInject(id = R.id.wishtext)
	TextView mWishText;
	private Wish mWish;
	private User mWishOwner;
	private UserInfoRequest mUserReq;
	private AdoptWishRequest mAdoptReq;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wishcontent);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.title_left);
		mWish = (Wish) getIntent().getSerializableExtra("Wish");
		relative.getBackground().setAlpha(100);
		mWishText.setText(mWish.getContent());
		mUserReq = new UserInfoRequest(this,
				UrlConstructor.UserInfoRequestUrl(), mWish.getOwnerId() + "");
		mUserReq.setCallBackHandler(new AjaxCallBack<String>() {

			@Override
			public void onFailure(Throwable t, String strMsg) {
				Log.d("userinfo", strMsg);
				Toast.makeText(WishContentActivity.this, "用户信息获取失败",
						Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onSuccess(String t) {
				Log.d("userinfo", t);
				mUserReq.onSuccess(t);
				mWishOwner = mUserReq.getUserInfo();
				if (mWishOwner != null)
					updateUserInfo();
			}
		});
		mAdoptReq = new AdoptWishRequest(this, UrlConstructor.AdoptRequestUrl()
				+ mWish.getId(), Constant.ADOPTED);
		mAdoptReq.setCallBackHandler(new AjaxCallBack<String>() {

			@Override
			public void onSuccess(String t) {
				mAdoptReq.onSuccess(t);
				Toast.makeText(WishContentActivity.this, "愿望认领成功",
						Toast.LENGTH_SHORT).show();
				Intent i = new Intent(WishContentActivity.this,
						OtherWishActivity.class);
				startActivity(i);
				finish();
			}

			@Override
			public void onFailure(Throwable t, String strMsg) {
				Log.d("adopt wish", strMsg);
				if (strMsg.endsWith("403"))
					Toast.makeText(WishContentActivity.this, "不能认领自己的愿望",
							Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(WishContentActivity.this, "愿望认领失败",
							Toast.LENGTH_SHORT).show();
			}
		});
		Button btn_back = (Button) findViewById(R.id.back);
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}

		});
	}

	@Override
	protected void onStart() {
		super.onStart();
		mUserReq.start();
	}

	private void updateUserInfo() {
		mNickName.setText(mWishOwner.getNickName());
		mSex.setBackgroundResource(mWishOwner.getGender() ? R.drawable.male
				: R.drawable.female);
		mLocation.setText(mWishOwner.getCampusId() + "");
	}

	public void onAchieve(View v) {
		mAdoptReq.start();
	}
}
