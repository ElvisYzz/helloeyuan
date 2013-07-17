package com.easyuan.push;

import java.net.MalformedURLException;

import org.json.JSONObject;

import com.easyuan.R;
import com.easyuan.entityclass.PushData;
import com.easyuan.entityclass.PushToken;
import com.easyuan.toolkit.Constant;
import com.easyuan.toolkit.UrlConstructor;
import com.easyuan.ui.MainTabActivity;
import com.google.gson.Gson;

import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIO;
import io.socket.SocketIOException;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

public class PushService extends Service implements IOCallback {
	protected SocketIO mWebSocket;
	protected static String TAG = "PushService";
	protected SharedPreferences mPrefs;
	protected NotificationManager mNotifyManager;   
	private PushData mData;
	public static boolean isStarted;
	public static boolean keepAlive;
	@Override
	public void onCreate() {
		super.onCreate();
		try {
			mWebSocket = new SocketIO(UrlConstructor.PushUrl());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		mPrefs = getSharedPreferences(Constant.TAG, Context.MODE_PRIVATE);
		mNotifyManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		keepAlive = true;
		isStarted = true;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		isStarted = false;
		if(mWebSocket.isConnected())
			mWebSocket.disconnect();
		if(keepAlive) {
			Intent i = new Intent();
	        i.setClass(this, PushService.class);  //销毁时重新启动Service
	        this.startService(i);
		}
			
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		if(!mWebSocket.isConnected())
			mWebSocket.connect(this);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onDisconnect() {
		Log("push socket disconnect");
	}

	@Override
	public void onConnect() {
		Log("push socket connect");
		int userId = mPrefs.getInt(Constant.USER_ID, 0);
		String deviceId = mPrefs.getString(Constant.DEVICE_ID, Constant.UNKNOWN);
		PushToken pushToken = new PushToken();
		pushToken.setUserId(userId);
		pushToken.setDeviceId(deviceId);
		mWebSocket.emit("login", new Gson().toJson(pushToken));
	}

	@Override
	public void onMessage(String data, IOAcknowledge ack) {
		Log("accept push message: "+data);
		mData = new Gson().fromJson(data, PushData.class);
		String name = mData.getName();
		String[] args = mData.getArgs();
		if(name == null || args == null) return;
		//触发通知栏
		Notification n;
		Intent intent = new Intent(this, MainTabActivity.class);  
		intent.putExtra("push", mData);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT); 
		if(name.equals("system")) {
			n= new Notification(R.drawable.ic_launcher, "收到一条系统消息", System.currentTimeMillis());
			n.setLatestEventInfo(this, args[0], args[1], pendingIntent); 
		} else {
			n = new Notification(R.drawable.ic_launcher, "有人给你留言了", System.currentTimeMillis());
			n.setLatestEventInfo(this, "你有一条新的留言", args[0], pendingIntent); 
		}
		n.flags = Notification.FLAG_AUTO_CANCEL;
		mNotifyManager.notify(1, n);
	}

	@Override
	public void onMessage(JSONObject json, IOAcknowledge ack) {
		// 暂不实现
		
	}

	@Override
	public void on(String event, IOAcknowledge ack, Object... args) {
		Log("Server triggered event '" + event + "'");
	}

	@Override
	public void onError(SocketIOException socketIOException) {
		socketIOException.printStackTrace();
	}

	public void Log(String msg) {
		Log(msg, null);
	}
	
	public void Log(String msg, Throwable error) {
		if(error == null) 
			Log.e(TAG, msg, error);
		else
			Log.d(TAG, msg);
	}
}
