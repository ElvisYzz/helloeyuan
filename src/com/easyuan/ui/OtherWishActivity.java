package com.easyuan.ui;

import java.util.LinkedList;
import java.util.List;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;

import com.easyuan.R;
import com.easyuan.entityclass.Wish;
import com.easyuan.request.MyAdoptWishRequest;
import com.easyuan.request.WishListRequest;
import com.easyuan.toolkit.Constant;
import com.easyuan.toolkit.UrlConstructor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class OtherWishActivity extends FinalActivity {

	@ViewInject(id = R.id.achieving, click = "onAchieving")
	Button mbtn_achieving;
	@ViewInject(id = R.id.achieved, click = "onAchieved")
	Button mbtn_achieved;
	@ViewInject(id = R.id.listView, itemClick = "onListItemClick")
	ListView mWishlistView;
	@ViewInject(id = R.id.otherwish_bg)
	LinearLayout mWishBackground;
	private LinkedList<Wish> data_achieving;
	private LinkedList<Wish> data_achieved;
	private BaseAdapter adapter_achieving;
	private BaseAdapter adapter_achieved;
	private WishListRequest mWishRequest;
	private boolean achieve_flag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.otherwish);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.title_left);
		mWishRequest = new MyAdoptWishRequest(this, 20,
				UrlConstructor.MyAdoptWishUrl(),"28 May 2013 06:24:11 GMT");
		mWishRequest.setCallBackHandler(new AjaxCallBack<String>() {

			@Override
			public void onFailure(Throwable t, String strMsg) {
				Log.d("my adopt wish", strMsg);
				if(strMsg.endsWith(Constant.REQUEST_WISH_INEXIST))
					Toast.makeText(OtherWishActivity.this, "还没有认领愿望",
							Toast.LENGTH_SHORT).show();
				else 
					Toast.makeText(OtherWishActivity.this, "获取愿望列表失败",
							Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onSuccess(String t) {
				mWishRequest.onSuccess(t);
				List<Wish> wishs = mWishRequest.getmWishList();
				for (Wish wish : wishs) {
					if (wish.getStatus() == Constant.ADOPTED)
						data_achieving.addLast(wish);
					else if (wish.getStatus() == Constant.REALLIZED)
						data_achieved.addLast(wish);
				}
				adapter_achieving.notifyDataSetChanged();
				adapter_achieved.notifyDataSetChanged();
			}
		});
		mWishRequest.start();
		initListAdapter();
		initWishList();
		Button btn_back = (Button) findViewById(R.id.back);
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}

		});
	}

	public void onAchieving(View v) {
		achieve_flag = true;
		mWishBackground.setBackgroundResource(R.drawable.achieving_time_bg);
		mWishlistView.setAdapter(adapter_achieving);
	}

	public void onAchieved(View v) {
		achieve_flag = false;
		mWishBackground.setBackgroundResource(R.drawable.achieved_time_bg);
		mWishlistView.setAdapter(adapter_achieved);
	}

	public void onListItemClick(AdapterView<?> adapter, View v, int i, long l) {

		if (achieve_flag) {
			Intent intent = new Intent(OtherWishActivity.this,
					AchievingActivity.class);
			intent.putExtra("wish", data_achieving.get(i));
			startActivity(intent);
			finish();
		} else {
			Intent intent = new Intent(OtherWishActivity.this,
					AchievedActivity.class);
			intent.putExtra("wish", data_achieved.get(i));
			startActivity(intent);
			finish();
		}

	}

	private void initListAdapter() {
		// TODO Auto-generated method stub
		data_achieving = new LinkedList<Wish>();
		data_achieved = new LinkedList<Wish>();
		adapter_achieving = new BaseAdapter() {
			public View getView(int position, View convertView, ViewGroup parent) {
				convertView = LayoutInflater.from(getApplicationContext())
						.inflate(R.layout.item_otherwish, null);
				TextView textView = (TextView) convertView
						.findViewById(R.id.content_wish);
				textView.setText(data_achieving.get(position).getContent());
				return convertView;
			}

			public long getItemId(int position) {
				return position;
			}

			public Object getItem(int position) {
				return data_achieving.get(position);
			}

			public int getCount() {
				return data_achieving.size();
			}
		};

		adapter_achieved = new BaseAdapter() {
			public View getView(int position, View convertView, ViewGroup parent) {
				convertView = LayoutInflater.from(getApplicationContext())
						.inflate(R.layout.item_otherwish2, null);
				TextView textView = (TextView) convertView
						.findViewById(R.id.content_wish);
				textView.setText(data_achieved.get(position).getContent());
				return convertView;
			}

			public long getItemId(int position) {
				return position;
			}

			public Object getItem(int position) {
				return data_achieved.get(position);
			}

			public int getCount() {
				return data_achieved.size();
			}
		};

	}

	private void initWishList() {
		achieve_flag = true;
		mWishlistView.setAdapter(adapter_achieving);
	}
}
