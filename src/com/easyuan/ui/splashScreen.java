package com.easyuan.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import net.tsz.afinal.http.AjaxCallBack;

import com.easyuan.R;
import com.easyuan.request.WishListRequest;
import com.easyuan.toolkit.ConnectionDetector;
import com.easyuan.toolkit.Constant;
import com.easyuan.toolkit.HttpMannager;
import com.easyuan.toolkit.UrlConstructor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.PixelFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

public class splashScreen extends Activity {

	private final int BUFFER_SIZE = 200000;
	public static final String DB_NAME = "ewish.db"; //保存的数据库文件名
    public static final String PACKAGE_NAME = "com.easyuan";
    public static final String DB_PATH = "/data"
            + Environment.getDataDirectory().getAbsolutePath() + "/"
            + PACKAGE_NAME+"/databases";  //在手机里存放数据库的位置
	private WishListRequest mWishRequest;
	private Intent mainIntent;
	private String mDeviceID;
	private File mDBFile;
	private boolean isFinished = false;
	
	/**
	 * Called when the activity is first created.
	 */

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		getWindow().setFormat(PixelFormat.RGBA_8888);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);

		setContentView(R.layout.splashscreen);
		mainIntent = new Intent(splashScreen.this, MainTabActivity.class);

		mDeviceID = Secure.getString(this.getContentResolver(),
				Secure.ANDROID_ID);
		SharedPreferences sharePrefs = getSharedPreferences(Constant.TAG,
				Context.MODE_PRIVATE);
		Editor editor = sharePrefs.edit();
		editor.putString(Constant.DEVICE_ID, mDeviceID);
		editor.commit();
		
		
		HttpMannager.configUserAgent("android/" + Constant.EWISH_VERSION);
		HttpMannager.configDeviceID(mDeviceID);
		HttpMannager.configRequestExecutionRetryCount(1);
		HttpMannager.configCharset("utf-8");
		
		ConnectionDetector connectDet = new ConnectionDetector(this);
		if (connectDet.isConnectingToInternet()) {
			mWishRequest = new WishListRequest(this, 20,
					UrlConstructor.WishOlderRequestUrl());
			mWishRequest.setCallBackHandler(new AjaxCallBack<String>() {
				@Override
				public void onSuccess(String t) {
					mainIntent.putExtra("wishList", t);
					Log.d("http", "finished");
					if(isFinished) {
						splashScreen.this.startActivity(mainIntent);
						splashScreen.this.finish();
					}else {
						isFinished = true;
					}
				}

				@Override
				public void onFailure(Throwable t, String strMsg) {
					Log.d("wishList fail", strMsg);
					Toast.makeText(splashScreen.this, "愿望列表获取失败",
							Toast.LENGTH_SHORT).show();
					splashScreen.this.startActivity(mainIntent);
					splashScreen.this.finish();
				}
			});
			mWishRequest.start();
		} else {
			Toast.makeText(this, "网络不可用", Toast.LENGTH_SHORT).show();
		}
		if(! isDataBaseExist()) {
			AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void>(){
				
				@Override
				protected Void doInBackground(Void... arg0) {
					try {
						InputStream is = splashScreen.this.getResources().openRawResource(
		                        R.raw.ewish); //欲导入的数据库
						FileOutputStream fos = new FileOutputStream(DB_PATH + "/" + DB_NAME);
						byte[] buffer = new byte[BUFFER_SIZE];
		                int count = 0;
		                while ((count = is.read(buffer)) > 0) {
		                	Log.d("database", "writting"+count);
		                    fos.write(buffer, 0, count);
		                }
		                fos.close();
		                is.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
					return null;
				}
				
				@Override
				protected void onProgressUpdate(Void... values) {
					super.onProgressUpdate(values);
					Log.d("update database", "update database");
				}

				@Override
				protected void onPostExecute(Void result) {
					super.onPostExecute(result);
					Log.d("database", "finished");
					if(isFinished) {
						splashScreen.this.startActivity(mainIntent);
						splashScreen.this.finish();
					}else {
						isFinished = true;
					}
				}			
			};
			asyncTask.execute();
			Log.d("database", "start");
		} else {
			isFinished = true;
		}
	}
	
	public boolean isDataBaseExist() {
		File filePath = new File(DB_PATH+"/");
		if(!filePath.exists()) filePath.mkdirs();
		return (mDBFile = new File(DB_PATH + "/" + DB_NAME)).exists();
	}
}
