package com.easyuan.ui;

import java.util.LinkedList;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.easyuan.R;
import com.easyuan.entityclass.PushData;

public class MessageActivity extends FinalActivity{
	
	@ViewInject(id = R.id.messagelist, itemClick = "onListItemClick")
	ListView mMessagelistView;
	
	private LinkedList<PushData> datas;
	private BaseAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.msglist);
		
		initListAdapter();
		initWishList();
	}

	public void onListItemClick(AdapterView<?> adapter, View v, int i, long l) {
//			Intent intent = new Intent(MessageActivity.this,
//					AchievingActivity.class);
//			startActivity(intent);
	}

	private void initListAdapter() {
		datas = new LinkedList<PushData>();
		PushData pushData = (PushData) getIntent().getSerializableExtra("push");
		if(pushData != null)
			datas.addFirst(pushData);
		adapter = new BaseAdapter() {
			public View getView(int position, View convertView, ViewGroup parent) {
				convertView = LayoutInflater.from(getApplicationContext())
						.inflate(R.layout.item_message, null);
				if(datas.isEmpty()) return convertView;
				TextView textView = (TextView) convertView
						.findViewById(R.id.content);
				textView.setText(datas.get(position).getArgs()[0]);
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



	}

	private void initWishList() {
		mMessagelistView.setAdapter(adapter);
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
				// TODO Auto-generated method stub
				dlg.cancel();
				MessageActivity.this.finish();
			}});
		btn = (Button) window.findViewById(R.id.cancle_exit);
		btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
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
