package com.swust.ipmsg.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;

import com.swust.ipmsg.IPMsgApplication;
import com.swust.ipmsg.R;
import com.swust.ipmsg.service.IPmsgService;
import com.swust.ipmsg.util.Person;

public class WelcomeActivity extends Activity {
	
	//private Button button;
	// private static final String TAG = "BindService";
	static int i = 0;
//	Person me = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		IPMsgApplication.getInstance().addActivity(this);
		//me = new Person();
		Intent intent = new Intent(WelcomeActivity.this, IPmsgService.class);
		
		/*这是一种传递对象那个的方法，还有两种就是实现Serializable（Java中的）接口或者实现Parcelable（Google提供）接口
		 * Bundle bundle = new Bundle();
		bundle.putInt("userId",me.getUserId());
		bundle.putString("useName", me.getUseName());
		bundle.putString("hostName", me.getHostName());
		bundle.putString("ipAddress", me.getIpAddress());
		bundle.putString("macAddress", me.getMac());
		intent.putExtras(bundle);*/
		/*Bundle bundle = new Bundle();
		bundle.putSerializable("me", me);
		intent.putExtras(bundle);*/
		startService(intent);

		if (i == 0) {
			setContentView(R.layout.activity_welcome);

			// 实现自动跳转及原Activity淡出、跳转到的Activity淡入
			new CountDownTimer(500, 1000) {// 文本框中显示一个2s倒计时：
				// 其参数：long millisInFuture
				// 从开始调用start()到倒计时完成并onFinish()方法被调用的毫秒数
				// long countDownInterval 接收onTick(long)回调的间隔时间
				// 定时执行在一段时候后停止的倒计时，在倒计时执行过程中会在固定间隔时间得到通知（译者：触发onTick方法）

				@Override
				public void onTick(long millisUntilFinished) {// 固定间隔被调用,其参数：倒计时剩余时间
					// onTick的调用是同步的，保证这次调用不会在之前调用完成前发生
				}
				
				@Override
				public void onFinish() {// 倒计时完成时被调用
					Intent intent = new Intent();
					intent.setClass(WelcomeActivity.this, MenuActivity.class);
				  /*intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
			                | Intent.FLAG_ACTIVITY_SINGLE_TOP
			                | Intent.FLAG_ACTIVITY_CLEAR_TOP);*/
					startActivity(intent);

					int VERSION = Integer
							.parseInt(android.os.Build.VERSION.SDK);
					if (VERSION >= 5) {
						WelcomeActivity.this.overridePendingTransition(
								R.anim.alpha_in, R.anim.alpha_out);
						 /*overridePendingTransition(R.anim.lefttoright,
						R.anim.righttoleft);//自动滑屏
*/					}
					finish();
				}
			}.start();
			i = 1;
		} else {
			WelcomeActivity.this.finish();
			i = 0;
		}
		/*
		 * if (i == 0) { //
		 * 自动跳转Activity——在onCreate里设置个Timer，然后建立Intent指向你要调用Activity。 //
		 * 设置Timer5妙后执行startActivity就行了 final Intent it = new Intent(this,
		 * ProjectActivity.class); Timer timer = new Timer(); TimerTask task =
		 * new TimerTask() { public void run() { startActivity(it); } };
		 * timer.schedule(task, 1000 * 2);
		 * 
		 * setContentView(R.layout.start);
		 * 
		 * i = 1; } if(i == 1){ Timer timer = new Timer(); TimerTask task = new
		 * TimerTask() { public void run() { StartActivity.this.finish(); } };
		 * timer.schedule(task, 1000 * 2);
		 * 
		 * setContentView(R.layout.end); }
		 */

		/*
		 * button = (Button) findViewById(R.id.button);
		 * button.setOnClickListener(new MyListerner());
		 * 
		 * Person person = new Person(this); System.out.println("IP:" +
		 * person.getIpAddress()); System.out.println("MAC:" + person.getMac());
		 * System.out.println("TeleName:" + person.getUseName());
		 */

	}

/*	class MyListerner implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(WelcomeActivity.this, MessageActivity.class);
			startActivity(intent);

		}

	}*/

}
