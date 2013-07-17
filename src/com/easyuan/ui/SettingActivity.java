package com.easyuan.ui;

import java.util.LinkedList;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;

import com.easyuan.R;
import com.easyuan.toolkit.Constant;

public class SettingActivity extends FinalActivity {

	@ViewInject(id = R.id.setlist)
	ListView mSetList;
	private BaseAdapter adapter;
	private LinkedList<String> datas;
	protected SharedPreferences mPrefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		mPrefs = getSharedPreferences(Constant.TAG, Context.MODE_PRIVATE);
		datas = new LinkedList<String>();
		// datas.add("清理缓存");
		// datas.add("意见");
		// datas.add("评价");
		datas.add("关于");
		datas.add("退出登录");
		adapter = new BaseAdapter() {
			public View getView(int position, View convertView, ViewGroup parent) {
				convertView = LayoutInflater.from(getApplicationContext())
						.inflate(R.layout.item_set, null);
				TextView textView = (TextView) convertView
						.findViewById(R.id.textView);
				textView.setText(datas.get(position));
				return convertView;
			}

			public long getItemId(int position) {
				return position;
			}

			public Object getItem(int position) {
				return datas.get(position);
			}

			public int getCount() {
				return datas.size();
			}
		};
		mSetList.setAdapter(adapter);
		mSetList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				switch (arg2) {
				case 0:
					openDialog("e愿团队开发","");
					break;
				case 1:
					savePreferences(Constant.LOGIN_STATE, Constant.UNLOGGED);
					break;
				default:

				}

			}
		});
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
				SettingActivity.this.finish();
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

	private void openDialog(String str1, String str2) {
		View menuView = View.inflate(this, R.layout.dialog, null);
		final Dialog dialog = new Dialog(this, R.style.dialog);
		TextView textView = (TextView) menuView.findViewById(R.id.text1);
		textView.setText(str1);
		textView = (TextView) menuView.findViewById(R.id.text2);
		textView.setText(str2);
		Button btn = (Button) menuView.findViewById(R.id.iknow);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				dialog.dismiss();
			}
		});

		dialog.setContentView(menuView);
		dialog.show();
	}
	
	public boolean savePreferences(String key, int value) {
		return mPrefs.edit().putInt(key, value).commit();
	}
}
