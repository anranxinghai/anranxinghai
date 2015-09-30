package com.anranxinghai.applicationb.receiver;

import com.anranxinghai.applicationb.activities.BActivity;
import com.anranxinghai.applicationb.constant.Constants;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class UserInforBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String action = intent.getAction();
		if (action == null || "".equals(action)) {
			return;
		} else if (action.equals(Constants.USERINFOR_TRANSPORT)) {
			intent.setClass(context, BActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
			System.out.println("Broadcast+Intent跳转实现UI刷新");
		}
	}

}
