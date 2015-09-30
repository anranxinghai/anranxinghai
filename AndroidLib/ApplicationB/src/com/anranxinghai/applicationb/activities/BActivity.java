package com.anranxinghai.applicationb.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.anranxinghai.application.aidl.AIDLService;
import com.anranxinghai.application.aidl.UserInfo;
import com.anranxinghai.applicationb.R;
import com.anranxinghai.applicationb.constant.Constants;
import com.anranxinghai.applicationb.utils.UserInfoUtils;

public class BActivity extends Activity implements OnClickListener {

	public static final String EDIT_ACTION = "com.anranxinghai.applicationb.activities.EditActivity";

	private ListView listView;
	private List<HashMap<String, Object>> list;
	// private UserInforBroadcastReceiver userInforBroadcastReceiver;
	private UserInfoAdapter userInforAdapter = null;
	private Button contentButton = null;
	private Button aidlButton = null;
	private Button contentInserButton = null;
	private EditText insertUserNumberText;
	private ContentResolver contentResolver = null;

	private AIDLService aidlService;
	private ServiceConnection connection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			Log.i("ServiceConnection", "onServiceConnected() called");
			aidlService = AIDLService.Stub.asInterface(service);
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			Log.i("ServiceConnection", "onServiceDisconnected() called");
		}
	};

	private ContentObserver contentObserver = null;
	private Uri uri = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_b);
		userInforAdapter = new UserInfoAdapter();
		Intent intent = new Intent("android.intent.action.AIDLService");
		bindService(intent, connection, Context.BIND_AUTO_CREATE);

		uri = new Uri.Builder().scheme(ContentResolver.SCHEME_CONTENT)
				.authority(Constants.AUTOHORITY).appendPath("user").build();

		list = (List<HashMap<String, Object>>) getIntent()
				.getSerializableExtra("userInfors");
		contentResolver = getContentResolver();
		contentObserver = new UserInfoContentObser(new Handler());
		System.out.println("仅仅Intent跳转实现UI刷新");
		contentResolver.registerContentObserver(uri, true, contentObserver);
		initView();
		if (list == null) {
			list = new ArrayList<HashMap<String, Object>>();
			/*
			 * for (int i = 0; i < 5; i++) { HashMap<String, Object> map = new
			 * HashMap<String, Object>(); map.put("userName",
			 * "1234567890anranxinghai"); map.put("userPassword",
			 * "anranxinghai1234567890"); list.add(map); }
			 */
		}
		// System.out.println("BActivity_addresss"+ list.toString());
		listView.setAdapter(userInforAdapter);
	}

	private void initView() {
		// TODO Auto-generated method stub
		listView = (ListView) findViewById(R.id.list_userinfo);
		contentButton = (Button) findViewById(R.id.button_content_provider);
		contentButton.setOnClickListener(this);
		aidlButton = (Button) findViewById(R.id.button_aidl_service);
		aidlButton.setOnClickListener(this);
		contentInserButton = (Button) findViewById(R.id.button_insert);
		contentInserButton.setOnClickListener(this);
		insertUserNumberText = (EditText) findViewById(R.id.text_insert);
	}

	class UserInfoAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if (list != null) {
				return list.size();
			}
			return 0;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			if (list != null) {
				return list.get(position);
			}
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.list_item,
						null);
			}
			if (list == null) {
				return convertView;
			}
			TextView userNameText = (TextView) convertView
					.findViewById(R.id.text_user_name);
			userNameText.setText(list.get(position).get("userName").toString());

			TextView userPasswordText = (TextView) convertView
					.findViewById(R.id.text_user_password);
			userPasswordText.setText(list.get(position).get("userPassword")
					.toString());
			Button modify = (Button) convertView
					.findViewById(R.id.button_modify);
			modify.setText("修改");
			modify.setOnClickListener(new ButtonClickListener(position));
			Button delete = (Button) convertView
					.findViewById(R.id.button_delete);
			delete.setText("删除");

			delete.setOnClickListener(new ButtonClickListener(position));
			return convertView;
		}

	}

	class ButtonClickListener implements OnClickListener {

		private int position = -1;

		public ButtonClickListener(int position) {
			// TODO Auto-generated constructor stub
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.button_delete:

				if (contentResolver.delete(uri, "_id = ?", new String[] { list
						.get(position).get("userId").toString() }) > 0) {
					list.remove(position);
				}
				userInforAdapter.notifyDataSetChanged();
				break;
			case R.id.button_modify:
				Intent intent = new Intent();
				intent.putExtra("userInfo", list.get(position));
				intent.putExtra("position", position);
				intent.setAction(EDIT_ACTION);
				int requestCode = 0;
				startActivityForResult(intent, requestCode);

				break;
			default:
				break;
			}
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch (requestCode) {
		case 0:
			HashMap<String, Object> userInfo = null;
			userInfo = (HashMap<String, Object>) data
					.getSerializableExtra("userInfo");
			list.set(data.getIntExtra("position", 0), userInfo);

			userInforAdapter.notifyDataSetChanged();
			break;

		default:
			break;
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// registerBroadcastReceiver();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.b, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/*
	 * class UserInforBroadcastReceiver extends BroadcastReceiver {
	 * 
	 * @Override public void onReceive(Context context, Intent intent) { // TODO
	 * Auto-generated method stub String action = intent.getAction(); if (action
	 * == null || "".equals(action)) { return; } else if
	 * (action.equals(Constants.USERINFOR_TRANSPORT)) { if (list != null) { try
	 * { Thread.sleep(2000); } catch (InterruptedException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } list.clear();
	 * userInforAdapter.notifyDataSetChanged(); System.out.println("广播实现UI刷新");
	 * isIntent = true; try { Thread.sleep(2000); } catch (InterruptedException
	 * e) { // TODO Auto-generated catch block e.printStackTrace(); } } list =
	 * (List<Map<String, Object>>) intent .getSerializableExtra("userInfors");
	 * userInforAdapter.notifyDataSetChanged(); } }
	 * 
	 * }
	 */

	/*
	 * private void registerBroadcastReceiver() { userInforBroadcastReceiver =
	 * new UserInforBroadcastReceiver(); intentFilter = new IntentFilter();
	 * intentFilter.addAction(Constants.USERINFOR_TRANSPORT);
	 * registerReceiver(userInforBroadcastReceiver, intentFilter); }
	 */

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		// unregisterReceiver(userInforBroadcastReceiver);
		super.onDestroy();
		contentResolver.unregisterContentObserver(contentObserver);
		if (aidlDialog != null) {
			aidlDialog.dismiss();
			aidlDialog = null;
		}
		if (providerDialog != null) {
			providerDialog.dismiss();
			providerDialog = null;
		}
		if (aidlService != null) {

		}
	}

	ProgressDialog providerDialog;
	ProgressDialog aidlDialog;
	ProgressDialog providerInsertDialog;

	@Override
	public void onClick(View v) {

		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button_content_provider:

			providerDialog = new ProgressDialog(this);
			providerDialog.setMessage("Provider获取数据中……");
			providerDialog.setCancelable(false);
			providerDialog.show();
			new Thread(new ContentProviderRuannble()).start();
			break;
		case R.id.button_aidl_service:
			aidlDialog = new ProgressDialog(this);
			aidlDialog.setMessage("AIDL获取数据中……");
			aidlDialog.setCancelable(false);
			aidlDialog.show();
			new Thread(new AIDLRuannble()).start();
			break;
		case R.id.button_insert:
			providerInsertDialog = new ProgressDialog(this);
			providerInsertDialog.setMessage("Provider 数据插入中……");
			providerInsertDialog.setCancelable(false);
			providerInsertDialog.show();
			new Thread(new ContentProviderInsertRuannble()).start();
		default:
			break;
		}
	}

	Handler handler = new Handler();

	private class ContentProviderRuannble implements Runnable {
		@Override
		public void run() {
			// TODO Auto-generated method stub

			getUserInfo();
			if (providerDialog != null) {
				providerDialog.cancel();
			}
			handler.post(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					Toast.makeText(BActivity.this, "ContentProvider数据获取成功！",
							Toast.LENGTH_SHORT).show();
					userInforAdapter.notifyDataSetChanged();
				}
			});

		}

	}

	private class AIDLRuannble implements Runnable {
		@Override
		public void run() {
			// TODO Auto-generated method stub

			List<UserInfo> userInfos = null;
			if (aidlService == null) {
				return;
			}
			try {
				userInfos = aidlService.queryAllUserInfo();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (list == null) {
				list = new ArrayList<HashMap<String, Object>>();
			}
			list.clear();
			for (UserInfo userInfo : userInfos) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("userId", userInfo.getUserId());
				map.put("userName", userInfo.getUserName());
				map.put("userPassword", userInfo.getUserPassword());
				list.add(map);
				System.out.println(userInfo);
			}

			if (aidlDialog != null) {
				aidlDialog.cancel();
			}
			handler.post(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					Toast.makeText(BActivity.this, "AIDL数据获取成功！",
							Toast.LENGTH_SHORT).show();
					userInforAdapter.notifyDataSetChanged();
				}
			});

		}

	}

	private class ContentProviderInsertRuannble implements Runnable {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			
			List<UserInfo> userInfos = createNUserInfo();
			if (userInfos == null || userInfos.size() == 0 ) {
				handler.post(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Toast.makeText(BActivity.this, "创建随机用户失败！", Toast.LENGTH_SHORT).show();
					}
				});
				return;
			}
			

				long timeStart = System.currentTimeMillis();
				//这种插入方式还是一条一条的插入，在ContentProvider中仍然是每一条数据建立一个事物
				/*for (UserInfo userInfo : userInfos) {
					if (userInfo != null) {
						ContentValues values = new ContentValues();
						values.put("user_name", userInfo.getUserName());
						values.put("user_password", userInfo.getUserPassword());
						contentResolver.insert(uri, values);
					}
				}*/
				
				
				ContentValues[] contentValuesList = new ContentValues[userInfos.size()];
				
				for (int i = 0; i < contentValuesList.length; i++) {
					ContentValues contentValues = new ContentValues();
					contentValues.put("user_name", userInfos.get(i).getUserName());
					contentValues.put("user_password", userInfos.get(i).getUserPassword());
					contentValuesList[i] = contentValues;
				}

				int size = contentResolver.bulkInsert(uri, contentValuesList);
				
				long timeEnd = System.currentTimeMillis();
				Log.i("provider insert time: ", String.valueOf(timeEnd - timeStart));
			
			providerInsertDialog.cancel();
			
			
			handler.post(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Toast.makeText(BActivity.this, "ContentProvider插入数据成功！", Toast.LENGTH_SHORT).show();
				}
			});
			
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


		private void getUserInfo() {
			Cursor cursor = contentResolver.query(uri, null, null, null, null);
			if (cursor != null) {
				// cursor.moveToFirst();//这一句会使丢失第一个数据
				if (list == null) {
					list = new ArrayList<HashMap<String, Object>>();
				}

				list.clear();
				while (cursor.moveToNext()) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("userId",
							cursor.getInt(cursor.getColumnIndex("_id")));
					map.put("userName", cursor.getString(cursor
							.getColumnIndex("user_name")));
					map.put("userPassword", cursor.getString(cursor
							.getColumnIndex("user_password")));

					list.add(map);
				}
			}
		}

	private class UserInfoContentObser extends ContentObserver {

		@Override
		public void onChange(boolean selfChange) {
			// TODO Auto-generated method stub
			super.onChange(selfChange);
			System.out.println("数据库发生改变");
			getUserInfo();
			userInforAdapter.notifyDataSetChanged();
		}

		public UserInfoContentObser(Handler handler) {
			super(handler);
			// TODO Auto-generated constructor stub
		}

	}

}
