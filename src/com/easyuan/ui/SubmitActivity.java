package com.easyuan.ui;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.easyuan.R;
import com.easyuan.request.ReleaseWishRequest;
import com.easyuan.toolkit.UrlConstructor;

public class SubmitActivity extends FinalActivity implements OnClickListener {
	@ViewInject(id = R.id.edit_wish)
	EditText mEditWish;
	private Button mBtn_back;
	private Button mBtn_commit;
	private ReleaseWishRequest mReleaseReq;
	private String mWishContent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.submit);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.title_submit);
		mBtn_back = (Button) findViewById(R.id.back);
		mBtn_commit = (Button) findViewById(R.id.commit);
		mBtn_back.setOnClickListener(this);
		mBtn_commit.setOnClickListener(this);
		mReleaseReq = new ReleaseWishRequest(this,
				UrlConstructor.ReleaseWishUrl());
		mWishContent = null;
	}

	public void onCommit() {
		mWishContent = mEditWish.getText().toString();
		if (mWishContent != null && !mWishContent.equals("")) {
			mReleaseReq.setContent(mWishContent);
			mReleaseReq.setCallBackHandler(new AjaxCallBack<String>() {
				@Override
				public void onFailure(Throwable t, String strMsg) {
					if (strMsg != null)
						Log.d("ReleaseWish", strMsg);
				}

				@Override
				public void onSuccess(String t) {
					if (t != null)
						Log.d("ReleaseWish", t);
					finish();
				}

			});
			mReleaseReq.start();
		}
	}

	public void onBack() {
		finish();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			onBack();
			break;
		case R.id.commit:
			onCommit();
			break;
		default:
			break;
		}
	}
}