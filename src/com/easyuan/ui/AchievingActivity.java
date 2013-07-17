package com.easyuan.ui;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;

import com.easyuan.R;
import com.easyuan.entityclass.Message;
import com.easyuan.entityclass.User;
import com.easyuan.entityclass.Wish;
import com.easyuan.request.AfirmWishRequest;
import com.easyuan.request.GetMessageRequest;
import com.easyuan.request.UserInfoRequest;
import com.easyuan.toolkit.Constant;
import com.easyuan.toolkit.UrlConstructor;
import com.google.gson.Gson;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AchievingActivity extends FinalActivity {

	@ViewInject(id = R.id.confirm, click = "onConfirm")
	Button mbtn_confirm;
	@ViewInject(id = R.id.cancel, click = "onCancel")
	Button mbtn_cancel;
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
	private boolean mIsCancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.achievingwish);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.title_left);
		mWish = (Wish) getIntent().getSerializableExtra("wish");
		mWishText.setText(mWish.getContent());
		mUserReq = new UserInfoRequest(this,
				UrlConstructor.UserInfoRequestUrl(), mWish.getOwnerId() + "");
		mUserReq.setCallBackHandler(new AjaxCallBack<String>() {

			@Override
			public void onFailure(Throwable t, String strMsg) {
				Log.d("userinfo", strMsg);
				Toast.makeText(AchievingActivity.this, "用户信息获取失败",
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

	public void onConfirm(View v) {
		String str1 = new String("谢谢你！");
		String str2 = new String("下面就等对方确认你成功帮TA实现愿望了哦~");
		openDialog(str1, str2, false);
	}

	public void onCancel(View v) {
		String str = new String("取消帮TA实现这个愿望吗？");
		openDialog(null, str, true);
	}

	public void onComments(View v) {
		Intent i = new Intent(AchievingActivity.this, CommentActivity.class);
		i.putExtra("message", new Gson().toJson(mMessageList));
		i.putExtra("wish",  mWish);
		startActivity(i);
	}

	private void openDialog(String str1, String str2, boolean isCancel) {
		View menuView = View.inflate(this, R.layout.dialog, null);
		final Dialog dialog = new Dialog(this, R.style.dialog);
		mIsCancel = isCancel;
		TextView textView = (TextView) menuView.findViewById(R.id.text1);
		textView.setText(str1);
		textView = (TextView) menuView.findViewById(R.id.text2);
		textView.setText(str2);
		Button btn = (Button) menuView.findViewById(R.id.iknow);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mIsCancel) {
					// 取消实现

				} else {
					// 确认实现
					AfirmWishRequest afirmWishReq = new AfirmWishRequest(
							AchievingActivity.this, UrlConstructor
									.AfrimWishUrl(mWish.getId()),
							Constant.REALLIZED);
					afirmWishReq.setCallBackHandler(new AjaxCallBack<String>() {

						@Override
						public void onSuccess(String t) {
							Log.d("AfirmWish success", t);
						}

						@Override
						public void onFailure(Throwable t, String strMsg) {
							Toast.makeText(AchievingActivity.this, "确认失败",
									Toast.LENGTH_LONG).show();
						}

					});
					afirmWishReq.start();
				}

				dialog.dismiss();
			}
		});

		dialog.setContentView(menuView);
		dialog.show();
	}

}