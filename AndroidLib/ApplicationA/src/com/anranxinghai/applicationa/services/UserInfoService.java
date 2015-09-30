package com.anranxinghai.applicationa.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.anranxinghai.application.aidl.AIDLService;
import com.anranxinghai.application.aidl.UserInfo;
import com.anranxinghai.applicationa.db.DatabaseHelper;

public class UserInfoService extends Service {

	private static final String TAG = "AIDLService";

	AIDLService.Stub stub = new AIDLService.Stub() {

		@Override
		public List<UserInfo> queryAllUserInfo() throws RemoteException {
			// TODO Auto-generated method stub
			List<UserInfo> userInfos = null;
			try {
				userInfos = new DatabaseHelper(getApplication())
						.getUserInfoDao().queryForAll();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.i(TAG, "queryAllUserInfo() called");
			return userInfos;
		}
	};

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		Log.i(TAG, "onBind() called");

		return stub;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Log.i(TAG, "onUnbind() called");
		return true;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.i(TAG, "onDestroy() called");
	}

}
