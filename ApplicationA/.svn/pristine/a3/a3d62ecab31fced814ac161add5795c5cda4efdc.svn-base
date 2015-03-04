package com.anranxinghai.applicationa.activities;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.anranxinghai.application.aidl.UserInfo;
import com.anranxinghai.applicationa.R;
import com.anranxinghai.applicationa.constant.Constants;
import com.anranxinghai.applicationa.constant.UserInfoProviderMetaDada;
import com.anranxinghai.applicationa.db.DatabaseHelper;
import com.anranxinghai.applicationa.utils.UserInfoUtils;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.Dao;

public class AActivity extends OrmLiteBaseActivity<DatabaseHelper> implements
		OnClickListener , Runnable{

	private Dao<UserInfo, Integer> userInfoDao = null;
	
	private EditText insertUserNumberText;
	private Button createButton;
	private Button intentToAppBButton;
	private Button broadcastToAppBButton;
	
	Uri uri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a);
		uri = new Uri.Builder().scheme(ContentResolver.SCHEME_CONTENT)
				.authority(UserInfoProviderMetaDada.AUTOHORITY).appendPath("user").build();
		initView();
		try {
			userInfoDao = getHelper().getUserInfoDao();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initView() {
		insertUserNumberText = (EditText) findViewById(R.id.text_num_user);
		createButton = (Button) findViewById(R.id.button_create);
		createButton.setOnClickListener(this);

		intentToAppBButton = (Button) findViewById(R.id.button_intent_to_app_b);
		intentToAppBButton.setOnClickListener(this);
		
		broadcastToAppBButton = (Button) findViewById(R.id.button_broadcast_to_app_b);
		broadcastToAppBButton.setOnClickListener(this);
	}

	
	ProgressDialog dialog = null;
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		ComponentName componetName ;
		switch (v.getId()) {
		case R.id.button_create:
			
			dialog = new ProgressDialog(this);
			dialog.setMessage("数据插入中……");
			  dialog.setCancelable(false);
			  dialog.show();
			 new Thread(this).start();
			System.out.println("onClick");
			
			break;
		case R.id.button_intent_to_app_b:

			ArrayList<Map<String, Object>> userInfos2 = new ArrayList<Map<String,Object>>();
			List<UserInfo> userInfos3 = null;
			SQLiteDatabase db2 = getHelper().getReadableDatabase();
			db2.beginTransaction();
			try {
				long startTime = System.currentTimeMillis();
				userInfos3 = userInfoDao.queryForAll();
				long endTime = System.currentTimeMillis();
				Log.i("queryForAll time:", String.valueOf(endTime - startTime));
				db2.setTransactionSuccessful();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} finally {
				db2.endTransaction();
			}
			if (userInfos3 != null && userInfos3.size() != 0) {
				for (UserInfo userInfo : userInfos3) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("userId",userInfo.getUserId());
					map.put("userName", userInfo.getUserName());
					map.put("userPassword", userInfo.getUserPassword());
					userInfos2.add(map);
				}
			}
			
			// 这些代码是启动另外的一个应用程序的主Activity，当然也可以启动任意一个Activity
			componetName = new ComponentName(
			// 这个是另外一个应用程序的包名
					"com.anranxinghai.applicationb",
					// 这个参数是要启动的Activity
					"com.anranxinghai.applicationb.activities.BActivity");
			try {
				intent.putExtra("userInfors", userInfos2);
				//System.out.println("AActivity_addresss"+userInfos2);
				intent.setComponent(componetName);
				startActivity(intent);
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(),
						"可以在这里提示用户没有找到应用程序，或者是做其他的操作！", 0).show();
			}
			break;
		case R.id.button_broadcast_to_app_b:
			ArrayList<Map<String, Object>> userInfos4 = new ArrayList<Map<String,Object>>();
			try {
				for (UserInfo userInfo : userInfoDao.queryForAll()) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("userId",userInfo.getUserId());
					map.put("userName", userInfo.getUserName());
					map.put("userPassword", userInfo.getUserPassword());
					userInfos4.add(map);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			intent.putExtra("userInfors", userInfos4);
			intent.setAction(Constants.USERINFOR_TRANSPORT);
			sendBroadcast(intent);
			break;
		default:
			break;
		}

	}

	private List<UserInfo> createNUserInfo() {
		String num = insertUserNumberText.getText().toString();
		if (num == null || "".equals(num)) {
			Toast.makeText(this, "请输入数字", Toast.LENGTH_SHORT).show();
			return null;
		}
		int n = Integer.valueOf(num);
		List<UserInfo> userInfos = new ArrayList<UserInfo>();
		for (int i = 0; i < n; i++) {
			Random random = new Random();
			int length = random.nextInt(19) + 1;

			UserInfo userInfo = new UserInfo();
			userInfo.setUserName(UserInfoUtils.randomString(length));
			length = random.nextInt(19) + 1;
			userInfo.setUserPassword(UserInfoUtils.randomString(length));

			userInfos.add(userInfo);
		}
		if (userInfos != null && userInfos.size() > 0) {
			return userInfos;
		} else {
			return null;
		}
	}

	
	Handler handler = new Handler();
	@Override
	public void run() {
		// TODO Auto-generated method stub
		List<UserInfo> userInfos = createNUserInfo();
		if (userInfos == null || userInfos.size() == 0 ) {
			Toast.makeText(this, "创建随机用户失败！", Toast.LENGTH_SHORT);
			return;
		}
		

			long timeStart = System.currentTimeMillis();
			SQLiteDatabase db = getHelper().getWritableDatabase();
			db.beginTransaction();
			try {
			for (UserInfo userInfo : userInfos) {
				if (userInfo != null) {
					userInfoDao.create(userInfo);
				}
			}
			
			
			
			db.setTransactionSuccessful();
			long timeEnd = System.currentTimeMillis();
			Log.i("insert time: ", String.valueOf(timeEnd - timeStart));
			dialog.cancel();
			handler.post(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub

					Toast.makeText(AActivity.this, "数据插入成功！", Toast.LENGTH_SHORT).show(); 
				}
			});
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			db.endTransaction();
		}
		
		/*long timeStart = System.currentTimeMillis();
		
		
		ContentValues[] contentValuesList = new ContentValues[userInfos.size()];
		
		for (int i = 0; i < contentValuesList.length; i++) {
			ContentValues contentValues = new ContentValues();
			contentValues.put("user_name", userInfos.get(i).getUserName());
			contentValues.put("user_password", userInfos.get(i).getUserPassword());
			contentValuesList[i] = contentValues;
		}

		int size = getContentResolver().bulkInsert(uri, contentValuesList);
		
		long timeEnd = System.currentTimeMillis();
		Log.i("provider insert time: ", String.valueOf(timeEnd - timeStart));
		dialog.cancel();
		handler.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub

				Toast.makeText(AActivity.this, "数据插入成功！", Toast.LENGTH_SHORT).show(); 
			}
		});*/
	}

	

}
