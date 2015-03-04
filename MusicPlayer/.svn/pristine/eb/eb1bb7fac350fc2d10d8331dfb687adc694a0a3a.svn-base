package com.anranxinghai.musicplayer.activity;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.anranxinghai.musicplayer.MusicPlayerApplication;
import com.anranxinghai.musicplayer.R;
import com.anranxinghai.musicplayer.constant.Constant;
import com.anranxinghai.musicplayer.http.RegistedHttp;
import com.anranxinghai.musicplayer.runnable.FileTransThread;
import com.anranxinghai.musicplayer.runnable.RegistedThread;

public class WelcomeActivity extends Activity {

	Intent intent = new Intent();

	Map<String, String> params = new HashMap<String, String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		MusicPlayerApplication.getInstance().addActivity(this);

		TelephonyManager tm = (TelephonyManager) WelcomeActivity.this
				.getSystemService(Context.TELEPHONY_SERVICE);
		String phoneNum = tm.getLine1Number();
		phoneNum = "15881640991";

		params.put("userTel", phoneNum);
		RegistedThread registedThread = new RegistedThread(params, handler);
		registedThread.start();
		/*
		 * handler.postDelayed(this, 1000);
		 */
		// handler.postDelayed(new RegistedThread(params, handler),1000);

		/*
		 * new Thread(new Runnable() {
		 * 
		 * boolean isRegisted = false;
		 * 
		 * @Override public void run() {
		 * 
		 * RegistedHttp registedHttp = new RegistedHttp(); isRegisted =
		 * registedHttp.isRegisted(params);
		 * 
		 * Message message = null; // isRegisted = true; if (isRegisted) {
		 * System.out.println("注册成功"); handler.postDelayed(new Runnable() {
		 * 
		 * @Override public void run() {
		 * 
		 * intent.setClass(WelcomeActivity.this, MusicPlayerActivity.class);
		 * startActivity(intent); finish(); } },1000);
		 * 
		 * } else { System.out.println("注册失败");
		 * 
		 * handler.postDelayed(new Runnable() {
		 * 
		 * @Override public void run() {
		 * 
		 * intent.setClass(WelcomeActivity.this, RegisterActivity.class);
		 * startActivity(intent); finish(); } },1000); }
		 * 
		 * } }).start();
		 */

	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case Constant.REGISTER_SUCCESS:
				intent.setClass(WelcomeActivity.this, MusicPlayerActivity.class);
				break;
			case Constant.REGISTER_FAILED:
				intent.setClass(WelcomeActivity.this, RegisterActivity.class);
				break;
			default:
				intent.setClass(WelcomeActivity.this, RegisterActivity.class);
				break;

			}

			startActivity(intent);
			finish();

			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);
		}
	};

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

		MusicPlayerApplication.getActivities().removeFirst();
	}

	public Handler getHandler() {
		return handler;
	}

}
