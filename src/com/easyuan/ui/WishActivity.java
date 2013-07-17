package com.easyuan.ui;

import java.util.LinkedList;
import java.util.List;

import com.easyuan.R;
import com.easyuan.entityclass.Wish;
import com.easyuan.request.WishListRequest;
import com.easyuan.toolkit.UrlConstructor;
import com.easyuan.widget.WishListView;
import com.easyuan.widget.WishListView.OnRefreshListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;
import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;

public class WishActivity extends FinalActivity {

	private BaseAdapter adapter;
	private LinkedList<Wish> mWishList;
	private WishListRequest mWishRequest;
	@ViewInject(id=R.id.listView) WishListView listView;
	@ViewInject(id=R.id.loading) LinearLayout loading;
	private boolean mDownRefresh;
	private boolean mIsUp;
//	private FinalBitmap mBitmap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wish);
		mIsUp = true;
		mWishRequest = new WishListRequest(this, 20,
				UrlConstructor.WishNewerRequestUrl());
		mWishRequest.setCallBackHandler(new AjaxCallBack<String>() {

			@Override
			public void onFailure(Throwable t, String strMsg) {
				Toast.makeText(WishActivity.this, " 更新失败", Toast.LENGTH_SHORT)
						.show();
				mDownRefresh = true;
				if(mIsUp) 
					listView.onRefreshComplete();
				else 
					loading.setVisibility(View.GONE);
			}

			@Override
			public void onSuccess(String t) {
				Log.d("wishActivity", t);
				mWishRequest.onSuccess(t);
				List<Wish> wishs = mWishRequest.getmWishList();
				if (mIsUp) {
					if (wishs != null) {
						for (Wish wish : wishs) {
							mWishList.addFirst(wish);
							if(mWishList.size()>=20)
								mWishList.remove(mWishList.size() - 1);
						}
					}
				} else {
					if (wishs != null) {
						for (int i = wishs.size() - 1; i >= 0; --i) {
							mWishList.addLast(wishs.get(i));
							mWishList.remove(mWishList.size() - 1);
						}
					}
					loading.setVisibility(View.GONE);
				}
				if(!mWishList.isEmpty())
				mWishRequest.setUpdateTime(mWishList.get(0).getCreatedTime(),
						mWishList.get(mWishList.size() - 1).getCreatedTime());
				adapter.notifyDataSetChanged();
				listView.onRefreshComplete();
				mDownRefresh = true;
			}
		});
		mWishList = new LinkedList<Wish>();
		loading.setVisibility(View.GONE);
		initWishList();
	}

	private void getInitWishList() {
		String wishJson = getIntent().getStringExtra("wishList");
		if (wishJson == null) {
			mWishRequest.start();
			List<Wish> wishs = mWishRequest.getmWishList();
			for (Wish wish : wishs) {
				mWishList.addLast(wish);			
			}
			if(!mWishList.isEmpty())
				mWishRequest.setUpdateTime(mWishList.get(0).getCreatedTime(), mWishList
						.get(mWishList.size() - 1).getCreatedTime());
			return;
		}
		Log.d("wishlist", wishJson);
		List<Wish> wishs = new Gson().fromJson(wishJson,
				new TypeToken<List<Wish>>() {
				}.getType());
		for (Wish wish : wishs) {
				mWishList.addLast(wish);			
		}
		if(!mWishList.isEmpty())
		mWishRequest.setUpdateTime(mWishList.get(0).getCreatedTime(), mWishList
				.get(mWishList.size() - 1).getCreatedTime());
	}

	private void initWishList() {
		getInitWishList();
		adapter = new BaseAdapter() {
			public View getView(int position, View convertView, ViewGroup parent) {
				convertView = LayoutInflater.from(getApplicationContext())
						.inflate(R.layout.item_wishlist, null);
				if(mWishList.isEmpty() || mWishList == null) return convertView;
				ImageView image = (ImageView) convertView.findViewById(R.id.avatar);
//				mBitmap.display(image, "http://t10.baidu.com/it/u=1113990654,2790217430&fm=59");
				TextView textView = (TextView) convertView
						.findViewById(R.id.wishcontent);
				textView.setText(mWishList.get(position).getContent());
				textView = (TextView) convertView.findViewById(R.id.wishtime);
				String[] times = mWishList.get(position).getCreatedTime().split("T");
				times[1] = times[1].substring(0, 5);
				textView.setText(times[0]+"  "+times[1]);
				return convertView;
			}

			public long getItemId(int position) {
				return position;
			}

			public Object getItem(int position) {
				return mWishList.get(position);
			}

			public int getCount() {
				return mWishList.size();
			}
		};
		listView.setAdapter(adapter);
		listView.setonRefreshListener(new OnRefreshListener() {
			public void onRefresh() {
				mIsUp = true;
				mWishRequest.changeUrl(UrlConstructor.WishNewerRequestUrl());
				mWishRequest.start();
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Log.d("listview", "item" + arg2 + " clicked");
				Intent i = new Intent(WishActivity.this,
						WishContentActivity.class);
				i.putExtra("Wish", mWishList.get(arg2-1));
				startActivity(i);
			}
		});
		mDownRefresh = true;
		listView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				if ((firstVisibleItem + visibleItemCount == totalItemCount)
						&& (totalItemCount >= 20)) {
					if (mDownRefresh) {
						mIsUp = false;
						mDownRefresh = false;
						Log.d("wishlist", "上拉刷新");
						loading.setVisibility(View.VISIBLE);
						mWishRequest.changeUrl(UrlConstructor
								.WishOlderRequestUrl());
						mWishRequest.start();
					}
				}
			}

			@Override
			public void onScrollStateChanged(AbsListView arg0, int arg1) {
				// TODO Auto-generated method stub

			}

		});
//		mBitmap = FinalBitmap.create(this);
//		mBitmap.configLoadingImage(R.drawable.downloading);
//		mBitmap.configBitmapMaxHeight(64);
//		mBitmap.configBitmapMaxWidth(64);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK)
			showExitDialog();
		return super.onKeyDown(keyCode, event);
	}

	public void showExitDialog() {
		final AlertDialog dlg = new AlertDialog.Builder(this).create();
		dlg.show();
		Window window = dlg.getWindow();
		window.setContentView(R.layout.exit_dialog);
		Button btn = (Button) window.findViewById(R.id.exit);
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dlg.cancel();
				WishActivity.this.finish();
			}
		});
		btn = (Button) window.findViewById(R.id.cancle_exit);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dlg.cancel();
			}
		});
		CheckBox chb = (CheckBox) window.findViewById(R.id.stopservice);
		chb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				MainTabActivity.mStopPush = isChecked;
			}
		});
		MainTabActivity.mStopPush = false;
	}
}
