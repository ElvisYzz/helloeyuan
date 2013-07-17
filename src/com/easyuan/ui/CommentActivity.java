package com.easyuan.ui;

import java.util.ArrayList;
import java.util.List;

import com.easyuan.R;
import com.easyuan.entityclass.Message;
import com.easyuan.entityclass.Wish;
import com.easyuan.request.GetMessageRequest;
import com.easyuan.request.LeaveMessageRequest;
import com.easyuan.toolkit.UrlConstructor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CommentActivity extends FinalActivity {

	@ViewInject(id = R.id.comment_commit, click = "onCommentCommit")
	Button mbtn_comment_commit;
	@ViewInject(id = R.id.listView)
	ListView mlist;
	private BaseAdapter adapter;
	private List<Message> mMessageList;
	private Wish mWish;
	private String mLeaveMsgContent;
	private LeaveMessageRequest mLeaveMsgReq;
	private GetMessageRequest mGetMsgReq;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comment_list);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.title_left);
		mWish = (Wish) getIntent().getSerializableExtra("Wish");
		mMessageList = new Gson().fromJson(getIntent()
				.getStringExtra("message"), new TypeToken<List<Wish>>() {
		}.getType());
		if (mMessageList == null)
			mMessageList = new ArrayList<Message>();
		Button btnBack = (Button) findViewById(R.id.back);
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		mLeaveMsgReq = new LeaveMessageRequest(this,
				UrlConstructor.LeaveMsgUrl(mWish.getId()));
		mLeaveMsgReq.setCallBackHandler(new AjaxCallBack<String>() {

			@Override
			public void onSuccess(String t) {
				Log.d("leave message", t);
				updateList(mLeaveMsgContent);
			}

			@Override
			public void onFailure(Throwable t, String strMsg) {
				Log.d("leave message fail", strMsg);
				Toast.makeText(CommentActivity.this, "留言失败", Toast.LENGTH_SHORT).show();
			}
		});
		if (mMessageList.isEmpty()) {
			mGetMsgReq = new GetMessageRequest(this,
					UrlConstructor.GetMsgUrl(mWish.getId()),
					mWish.getCreatedTime());
			mGetMsgReq.setCallBackHandler(new AjaxCallBack<String>() {

				@Override
				public void onSuccess(String t) {
					Log.d("Get message", t);
					mGetMsgReq.onSuccess(t);
					mMessageList.addAll(mGetMsgReq.getmMessageList());
					adapter.notifyDataSetChanged();
				}

				@Override
				public void onFailure(Throwable t, String strMsg) {
					Log.d("Get message fail", strMsg);
					Toast.makeText(CommentActivity.this, "留言更新失败", Toast.LENGTH_SHORT).show();
				}
			});
			mGetMsgReq.start();
		}
		initAdapter();
	}

	public void onCommentCommit(View v) {
		EditText edit = (EditText) findViewById(R.id.edit_comment);
		mLeaveMsgContent = edit.getText().toString();
		edit.setText("");
		mLeaveMsgReq.setMsg(mLeaveMsgContent);
		mLeaveMsgReq.start();

	}

	private void initAdapter() {
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

	private void updateList(String str) {
		Message msg = new Message();
		msg.setContent(str);
		mMessageList.add(msg);
		adapter.notifyDataSetChanged();
	}
}
