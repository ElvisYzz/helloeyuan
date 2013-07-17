package com.easyuan.ui;

import java.util.ArrayList;
import java.util.List;

import com.easyuan.R;
import com.easyuan.entityclass.Message;
import com.easyuan.entityclass.User;
import com.easyuan.entityclass.Wish;
import com.easyuan.request.GetMessageRequest;
import com.easyuan.request.UserInfoRequest;
import com.easyuan.toolkit.UrlConstructor;
import com.google.gson.Gson;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AchievedActivity extends FinalActivity {
	
	@ViewInject(id = R.id.comment, click = "onComments")
	Button mbtn_comments;
	@ViewInject(id = R.id.wishtext)
	TextView mWishText;
	@ViewInject(id = R.id.avatar)
	ImageView mAvatar;
	@ViewInject(id = R.id.nickname)
	TextView mNickName;
	@ViewInject(id = R.id.sex)
	ImageView mSex;
	@ViewInject(id = R.id.location)
	TextView mLocation;
	@ViewInject(id = R.id.listView)
	ListView mlist;
	private Wish mWish;
	private User mWishOwner;
	private BaseAdapter adapter;
	private List<Message> mMessageList;
	private UserInfoRequest mUserReq;
	private GetMessageRequest mMessageReq;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.achievedwish);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_left);	
		mWish = (Wish) getIntent().getSerializableExtra("wish");
		mWishText.setText(mWish.getContent());
		mUserReq = new UserInfoRequest(this,
				UrlConstructor.UserInfoRequestUrl(), mWish.getOwnerId() + "");
		mUserReq.setCallBackHandler(new AjaxCallBack<String>() {

			@Override
			public void onFailure(Throwable t, String strMsg) {
				Log.d("userinfo", strMsg);
				Toast.makeText(AchievedActivity.this, "用户信息获取失败",
						Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onSuccess(String t) {
				Log.d("userinfo", t);
				mUserReq.onSuccess(t);
				mWishOwner = mUserReq.getUserInfo();
				updateUserInfo();
			}
		});
		mUserReq.start();
		mMessageList = null;
		mMessageReq = new GetMessageRequest(this,
				UrlConstructor.GetMsgUrl(mWish.getId()), mWish.getCreatedTime());
		mMessageReq.setCallBackHandler(new AjaxCallBack<String>() {

			@Override
			public void onSuccess(String t) {
				Log.d("Achieving Message", t);
				mMessageReq.onSuccess(t);
				mMessageList.addAll(mMessageReq.getmMessageList());
				if (mMessageList != null) {
					adapter.notifyDataSetChanged();
				}
			}

			@Override
			public void onFailure(Throwable t, String strMsg) {
				Log.d("Achieving Message", strMsg);
			}
		});
		mMessageReq.start();
		Button btn_back = (Button) findViewById(R.id.back);
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}

		});
		 initAdapter();
	}

	private void updateUserInfo() {
		mNickName.setText(mWishOwner.getNickName());
		mSex.setBackgroundResource(mWishOwner.getGender() ? R.drawable.male
				: R.drawable.female);
		mLocation.setText(mWishOwner.getCampusId());
	}

	private void initAdapter() {
		mMessageList = new ArrayList<Message>();
		adapter = new BaseAdapter() {
			public View getView(int position, View convertView, ViewGroup parent) {
				convertView = LayoutInflater.from(getApplicationContext())
						.inflate(R.layout.item_comment, null);
				TextView textView = (TextView) convertView
						.findViewById(R.id.textView_item);
				textView.setText(mMessageList.get(position).getContent());
				return convertView;
			}

			public long getItemId(int position) {
				return position;
			}

			public Object getItem(int position) {
				return mMessageList.get(position);
			}

			public int getCount() {
				return mMessageList.size();
			}
		};
		mlist.setAdapter(adapter);
	}
	
	public void onComments(View v){
		Intent i = new Intent(AchievedActivity.this, CommentActivity.class);
		i.putExtra("message", new Gson().toJson(mMessageList));
		i.putExtra("wish",  mWish);
		startActivity(i);
	}
}
