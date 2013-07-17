package com.easyuan.ui;

import java.util.LinkedList;
import java.util.List;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;

import com.easyuan.R;
import com.easyuan.entityclass.Wish;
import com.easyuan.request.AfirmWishRequest;
import com.easyuan.request.MyReleaseWishRequest;
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

public class MyWishActivity extends FinalActivity {
	
	@ViewInject(id = R.id.achieving, click = "onAchieving")
	Button mbtn_achieving;
	@ViewInject(id = R.id.achieved, click = "onAchieved")
	Button mbtn_achieved;
	@ViewInject(id = R.id.unachieve, click = "onUnachieve")
	Button mbtn_unachieve;
	@ViewInject(id = R.id.listView, itemClick = "onListItemClick")
	ListView mWishlistView;
	@ViewInject(id = R.id.otherwish_bg)
	LinearLayout mWishBackground;
	private LinkedList<Wish> data_achieving;
	private LinkedList<Wish> data_achieved;
	private LinkedList<Wish> data_unachieve;
	private BaseAdapter adapter_achieving;
	private BaseAdapter adapter_achieved;
	private BaseAdapter adapter_unachieve;
	private WishListRequest mWishRequest;
	private enum ListState{
		achieving, achieved, unachieve;
	}
	private ListState liststate;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mywish);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.title_left);
		mWishRequest = new MyReleaseWishRequest(this, 20, UrlConstructor.MyReleaseWishUrl(), "28 May 2013 06:24:11 GMT");
		mWishRequest.setCallBackHandler(new AjaxCallBack<String>() {

			@Override
			public void onSuccess(String t) {
				Log.d("my release wish", t);
				mWishRequest.onSuccess(t);
				List<Wish> wishs = mWishRequest.getmWishList();
				for (Wish wish : wishs) {
					if (wish.getStatus() == Constant.ADOPTED)
						data_achieving.addLast(wish);
					else if (wish.getStatus() == Constant.REALLIZED)
						data_achieved.addLast(wish);
					else if(wish.getStatus() == Constant.UNADOPT)
						data_unachieve.addLast(wish);
				}
				adapter_achieving.notifyDataSetChanged();
				adapter_achieved.notifyDataSetChanged();
				adapter_unachieve.notifyDataSetChanged();
			}

			@Override
			public void onFailure(Throwable t, String strMsg) {
				Log.d("my release wish", strMsg);
				if(strMsg.endsWith(Constant.REQUEST_WISH_INEXIST))
					Toast.makeText(MyWishActivity.this, "还没有发布愿望",
							Toast.LENGTH_SHORT).show();
				else 
					Toast.makeText(MyWishActivity.this, "获取愿望列表失败",
							Toast.LENGTH_SHORT).show();
			}
			
		});
		mWishRequest.start();
		initListAdapter();
		initWishList();
		Button btn_back = (Button) findViewById(R.id.back);
		btn_back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
			
		});
	}
	
	public void onAchieving(View v) {
		liststate = ListState.achieving;
		mWishBackground.setBackgroundResource(R.drawable.achieving_time_bg);
		mWishlistView.setAdapter(adapter_achieving);
	}

	public void onAchieved(View v) {
		liststate = ListState.achieved;
		mWishBackground.setBackgroundResource(R.drawable.achieved_time_bg);
		mWishlistView.setAdapter(adapter_achieved);
	}
	
	public void onUnachieve(View v) {
		liststate = ListState.unachieve;
		mWishBackground.setBackgroundResource(R.drawable.unachieve_time_bg);
		mWishlistView.setAdapter(adapter_unachieve);
	}

	public void onListItemClick(AdapterView<?> adapter, View v, int i, long l) {

		if(liststate.equals(ListState.achieving)){
			Intent intent = new Intent(MyWishActivity.this, CommentActivity.class);
			intent.putExtra("wish", data_achieving.get(i));
			startActivity(intent);
			finish();
		}else if(liststate.equals(ListState.achieved)) {
			Intent intent = new Intent(MyWishActivity.this, CommentActivity.class);
			intent.putExtra("wish", data_achieved.get(i));
			startActivity(intent);
			finish();
		}

	}

	private void initListAdapter() {
		// TODO Auto-generated method stub
		data_achieving = new LinkedList<Wish>();

	    data_achieved = new LinkedList<Wish>();

	    data_unachieve = new LinkedList<Wish>();
	    
	    adapter_achieving = new BaseAdapter() {
			public View getView(int position, View convertView, ViewGroup parent) {
				 convertView=LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_mywish, null);
				TextView textView = (TextView) convertView.findViewById(R.id.content_wish);
				textView.setText(data_achieving.get(position).getContent());
				final int index = position;
				Button btn_confirm = (Button) convertView.findViewById(R.id.confirm);
				btn_confirm.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						AfirmWishRequest afirmMyWish = new AfirmWishRequest(MyWishActivity.this, UrlConstructor.AfrimWishUrl(data_achieving.get(index).getId()), Constant.REALLIZED );
						afirmMyWish.setCallBackHandler(new AjaxCallBack<String>() {

							@Override
							public void onSuccess(String t) {
								Log.d("afirm my wish", t);
								Toast.makeText(MyWishActivity.this, "确认成功", Toast.LENGTH_SHORT).show();
							}

							@Override
							public void onFailure(Throwable t, String strMsg) {
								Log.d("afirm my wish", strMsg);
								Toast.makeText(MyWishActivity.this, "确认失败",Toast.LENGTH_SHORT).show();
							}});
						afirmMyWish.start();
					}});
				Button btn_cancel = (Button) convertView.findViewById(R.id.cancel);
				btn_cancel.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						AfirmWishRequest afirmMyWish = new AfirmWishRequest(MyWishActivity.this, UrlConstructor.AfrimWishUrl(data_achieving.get(index).getId()), Constant.UNADOPT );
						afirmMyWish.setCallBackHandler(new AjaxCallBack<String>() {

							@Override
							public void onSuccess(String t) {
								Log.d("cancel my wish", t);
								Toast.makeText(MyWishActivity.this, "取消成功", Toast.LENGTH_SHORT).show();
							}

							@Override
							public void onFailure(Throwable t, String strMsg) {
								Log.d("cancel my wish", strMsg);
								Toast.makeText(MyWishActivity.this, "取消失败",Toast.LENGTH_SHORT).show();
							}});
						afirmMyWish.start();
					}});
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
				 convertView=LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_mywish3, null);
				TextView textView = (TextView) convertView.findViewById(R.id.content_wish);
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
		
		adapter_unachieve = new BaseAdapter() {
			public View getView(int position, View convertView, ViewGroup parent) {
				 convertView=LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_mywish2, null);
				TextView textView = (TextView) convertView.findViewById(R.id.content_wish);
				textView.setText(data_unachieve.get(position).getContent());
				return convertView;
			}

			public long getItemId(int position) {
				return position;
			}

			public Object getItem(int position) {
				return data_unachieve.get(position);
			}

			public int getCount() {
				return data_unachieve.size();
			}
		};
	}

	private void initWishList()
	{
		liststate = ListState.achieving;
		mWishlistView.setAdapter(adapter_achieving);
	}
}
