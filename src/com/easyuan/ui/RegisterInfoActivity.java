/**   
 * Filename:    RegisterInfoActivity.java   
 * Copyright:   Copyright (c)2013  
 * Company:     easyuan 
 * @version:    1.0   
 * @since:      JDK 1.7.0 
 * Create at:   2013-5-25 下午10:59:31   
 * Description:  
 *   
 * Modification History:   
 * Date    Author      Version     Description   
 * ----------------------------------------------------------------- 
 * 2013-5-25 null        1.0         1.0 Version   
 */
package com.easyuan.ui;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.easyuan.R;
import com.easyuan.entityclass.Province;
import com.easyuan.entityclass.University;
import com.easyuan.entityclass.User;
import com.easyuan.request.RegisterInfoRequest;
import com.easyuan.toolkit.Constant;
import com.easyuan.toolkit.UrlConstructor;
import com.google.gson.Gson;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalDb;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;

/**
 * Filename: RegisterInfoActivity.java Copyright: Copyright (c)2013 Company:
 * easyuan
 * 
 * @version: 1.0
 * @since: JDK 1.7.0 Create at: 2013-5-25 下午10:59:31 Description:
 * 
 *         Modification History: Date Author Version Description
 *         -----------------------------------------------------------------
 *         2013-5-25 null 1.0 1.0 Version
 */
public class RegisterInfoActivity extends FinalActivity {

	@ViewInject(id = R.id.avatar, click = "onAvatar")
	ImageView mAvatar;
	@ViewInject(id = R.id.nickname)
	EditText mEditNicknam;
	@ViewInject(id = R.id.sex_male, click = "onButtonClick")
	ImageView mBtnMale;
	@ViewInject(id = R.id.sex_female, click = "onButtonClick")
	ImageView mBtnFemale;
	@ViewInject(id = R.id.province)
	AutoCompleteTextView mEditProvince;
	@ViewInject(id = R.id.school)
	AutoCompleteTextView mEditSchool;
	@ViewInject(id = R.id.complete, click = "onComplete")
	Button mBtnComplete;
	private final static int DIALOG = 1;
	public static final int NONE = 0;
	public static final int PHOTOHRAPH = 1;// 拍照
	public static final int PHOTOZOOM = 2; // 缩放
	public static final int PHOTORESOULT = 3;// 结果
	public static final String IMAGE_UNSPECIFIED = "image/*";
	private boolean gender;
	private User myInfo;
	private String tokenJson;
	private List<Province> provinces;
	private List<String> provNames;
	private List<University> universities;
	private List<String> univNames;
	private FinalDb mDatabase;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registerinfo);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.title_left);
		gender = true;
		myInfo = new User();
		tokenJson = getIntent().getStringExtra("token");
		Button btnBack = (Button) findViewById(R.id.back);
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		initProvList();
		initAdapter();
	}

	private void initProvList() {
		mDatabase = FinalDb.create(this,"ewish.db");
		provinces = mDatabase.findAll(Province.class);
		if(provinces != null && !provinces.isEmpty()) {
			provNames = new ArrayList<String>();
			for(Province prov : provinces)
				provNames.add(prov.getName());
		}
	}

	private void initAdapter() {
		ArrayAdapter<String> provAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, provNames);
		mEditProvince.setAdapter(provAdapter);
		mEditProvince.setThreshold(1);
		mEditSchool.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				if(arg1) {
					String provName = mEditProvince.getText().toString();
					int provId = 0;
					for(Province prov : provinces) {
						if(prov.getName().equals(provName)) {
							provId = prov.getId();
							break;
						}
					}
					if(provId != 0) {
						Log.d("province_id", provId+"");
						universities = mDatabase.findAllByWhere(University.class, "province_id = "+provId);
					}
					else
						universities = mDatabase.findAll(University.class);
					if(universities == null || universities.isEmpty()) return;
					univNames = new ArrayList<String>();
					for(University univ : universities) {
						Log.d("univerisity", univ.getName());
						univNames.add(univ.getName());
					}
					ArrayAdapter<String> univAdapter = new ArrayAdapter<String>(RegisterInfoActivity.this,android.R.layout.simple_dropdown_item_1line, univNames);
					mEditSchool.setAdapter(univAdapter);
					mEditSchool.setThreshold(1);
				}
			}});
	}

	public void onAvatar(View v) {
		Log.d("registerInfo", "avatar click");
		onCreateDialog(DIALOG).show();
	}

	/**
	 * 创建列表对话框
	 */
	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		switch (id) {
		case DIALOG:
			Builder builder = new android.app.AlertDialog.Builder(this);
			// 设置对话框的图标
			builder.setIcon(R.drawable.ic_launcher);
			// 添加按钮，android.content.DialogInterface.OnClickListener.OnClickListener
			builder.setItems(R.array.choice,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Intent intent = null;
							switch (which) {
							case 0:
								intent = new Intent(Intent.ACTION_GET_CONTENT,
										null);
								intent.setDataAndType(
										MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
										IMAGE_UNSPECIFIED);
								startActivityForResult(intent, PHOTOZOOM);
								break;
							case 1:
								intent = new Intent(
										MediaStore.ACTION_IMAGE_CAPTURE);
								intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri
										.fromFile(new File(Environment
												.getExternalStorageDirectory(),
												"temp.jpg")));
								System.out.println("============="
										+ Environment
												.getExternalStorageDirectory());
								startActivityForResult(intent, PHOTOHRAPH);
								break;
							}
						}

					});
			// 创建一个列表对话框
			dialog = builder.create();
			break;
		}
		return dialog;
	}

	public void onButtonClick(View v) {
		switch (v.getId()) {
		case R.id.sex_male:
			mBtnMale.setBackgroundResource(R.drawable.sexchoice_male_down);
			mBtnFemale.setBackgroundResource(R.drawable.sexchoice_female_up);
			gender = true;
			break;
		case R.id.sex_female:
			mBtnMale.setBackgroundResource(R.drawable.sexchoice_male_up);
			mBtnFemale.setBackgroundResource(R.drawable.sexchoice_female_down);
			gender = false;
			break;
		default:

			break;
		}
	}

	public void onComplete(View v) {
		myInfo.setNickName(mEditNicknam.getText().toString());
		myInfo.setGender(gender);
		String campusName = mEditSchool.getText().toString();
		int campusId = 0;
		for(University univ : universities)
			if(univ.getName().equals(campusName)) {
				campusId = univ.getId();
				break;
			}
		myInfo.setCampusId(campusId);
		String userInfo = new Gson().toJson(myInfo);
		Log.d("register userinfo ", userInfo);
		RegisterInfoRequest registerInfoReq = new RegisterInfoRequest(this,
				UrlConstructor.RegisterInfoRequsetUrl(), userInfo, tokenJson);
		registerInfoReq.setCallBackHandler(new AjaxCallBack<String>() {

			@Override
			public void onSuccess(String t) {
				Log.d("RegisterInfo", t);
				Toast.makeText(RegisterInfoActivity.this, "信息完善成功",
						Toast.LENGTH_SHORT).show();
				setResult(Constant.RESULT_USERINFO,
						(new Intent()).putExtra("userInfo", myInfo));
				finish();
			}

			@Override
			public void onFailure(Throwable t, String strMsg) {
				Log.d("RegisterInfo", strMsg);
				Toast.makeText(RegisterInfoActivity.this, "信息完善失败",
						Toast.LENGTH_SHORT).show();
				finish();
			}
		});
		registerInfoReq.start();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == NONE)
			return;
		// 拍照
		if (requestCode == PHOTOHRAPH) {
			// 设置文件保存路径这里放在跟目录下
			File picture = new File(Environment.getExternalStorageDirectory()
					+ "/temp.jpg");
			System.out.println("------------------------" + picture.getPath());
			startPhotoZoom(Uri.fromFile(picture));
		}

		if (data == null)
			return;

		// 读取相册缩放图片
		if (requestCode == PHOTOZOOM) {
			startPhotoZoom(data.getData());
		}
		// 处理结果
		if (requestCode == PHOTORESOULT) {
			Bundle extras = data.getExtras();
			if (extras != null) {
				Bitmap photo = extras.getParcelable("data");
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);// (0 -
																		// 100)压缩文件
				mAvatar.setImageBitmap(photo);
				byte[] avatarByte = stream.toByteArray();
				myInfo.setAvatar(Base64.encodeToString(avatarByte,
						Base64.NO_WRAP));
			}

		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 64);
		intent.putExtra("outputY", 64);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, PHOTORESOULT);
	}
}
